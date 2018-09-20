package com.effect.tdb.bs.common;


import org.apache.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.effect.tdb.bs.common.BaseConst.STATUS_COLUMN;
import static com.effect.tdb.bs.common.BaseUtils.isEmpty;

/**
 * Created by datha on 4/9/2018.
 */


public class ControllerUtils<T> {

    private static Logger logger = Logger.getLogger(ControllerUtils.class);

    public DataTablePaginationResponse getPaginationDataWithStatusFilter(JpaSpecificationExecutor baseRepository, MultiValueMap<String, String> params) {
        try {
            logger.info("json: " + params);
            DataTablePaginationRequest dtpr = BaseUtils.getDatatableRequestInfo(params);

            long draw = dtpr.getDraw();
            long start = dtpr.getStart();
            long length = dtpr.getLength();

            Specifications specifications = null;
            Specifications specificationsAnd = null;
            Specifications specificationsOr = null;
            Map<String, KeyValueObj> manyToOneMap = new HashMap<>();

            for (int i = 0; i < dtpr.getFilter().size(); i++) {
                FilterObject filterObject = (FilterObject) dtpr.getFilter().get(i);
                if (filterObject.getValue() != null && !filterObject.getValue().trim().isEmpty()) {
                    System.out.println("filter object: " + filterObject.toString());
                    if (filterObject.getOperator().equals(FilterType.AND)) {
                        Specification specification = dtpr.filterByFieldName(filterObject.getFieldName(), filterObject.getValue(), filterObject.getExpression());
                        if (specificationsAnd == null) {
                            specificationsAnd = Specifications.where(specification);
                        } else {
                            specificationsAnd = specificationsAnd.and(specification);
                        }
                    }
                    if (filterObject.getOperator().equals(FilterType.OR)) {
                        Specification specification = dtpr.filterByFieldName(filterObject.getFieldName(), filterObject.getValue(), filterObject.getExpression());
                        if (specificationsOr == null) {
                            specificationsOr = Specifications.where(specification);
                        } else {
                            specificationsOr = specificationsAnd.or(specification);
                        }
                    }

                } else {
                    System.out.println("filter object MTO: " + filterObject.toString());
                    if (filterObject.getFieldName().contains(".")) {
                        String[] fieldNames = filterObject.getFieldName().split("\\.");
                        StringBuilder mtoFieldBuilder = new StringBuilder("");
                        for (int j = 1; j < fieldNames.length; j++) {
                            if (j != fieldNames.length - 1) {
                                mtoFieldBuilder.append(fieldNames[j]).append(".");
                            } else {
                                mtoFieldBuilder.append(fieldNames[j]);
                            }

                        }
                        manyToOneMap.put(filterObject.getFieldName(), new KeyValueObj(mtoFieldBuilder.toString(), fieldNames[0]));
                    }
                }
            }
            dtpr.setManyToOneMap(manyToOneMap);
            System.out.println("manyToOneMap: " + manyToOneMap);
            System.out.println();

            if (specificationsAnd != null) {
                specifications = Specifications.where(specificationsAnd);
            }


            if (specificationsOr != null) {
                if (specifications == null) {
                    specifications = Specifications.where(specificationsOr);
                } else {
                    specifications = specifications.and(specificationsOr);
                }
            }


            long totalRows = baseRepository.count(specifications);
            long page = start / length;

            logger.info(String.format("draw: %d, start: %d, length: %d, total row: %d, page: %d", draw, start, length, totalRows, page));

            Specifications mtoSpecifications = dtpr.getMTOSpecifications();
            List<T> data;
            long recordsFiltered;
            if (mtoSpecifications == null) {
                data = baseRepository.findAll(specifications, dtpr.getPageRequest()).getContent();
                recordsFiltered = totalRows;
            } else {
                Specifications andSpecifications = mtoSpecifications.and(specifications);
                data = baseRepository.findAll(andSpecifications, dtpr.getPageRequest()).getContent();
                recordsFiltered = baseRepository.count(andSpecifications);
            }

            return new DataTablePaginationResponse(draw, totalRows, recordsFiltered, data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public DataTablePaginationResponse getPaginationDataWithStatusFilter(JpaSpecificationExecutor baseRepository, MultiValueMap<String, String> params, Map<String, KeyValueObj> manyToOneMap, Map<String, String> paramMap) {
        try {
            logger.info("json: " + params);
            DataTablePaginationRequest dtpr = BaseUtils.getDatatableRequestInfo(params);
            dtpr.setManyToOneMap(manyToOneMap);

            long draw = dtpr.getDraw();
            long start = dtpr.getStart();
            long length = dtpr.getLength();
            Specification specificationStatusFilter = dtpr.filterByFieldName(STATUS_COLUMN, BaseStatus.DB_ROW_ACTIVE.getValue());
            Specifications specificationsForFilter = Specifications.where(specificationStatusFilter);
            Specifications specificationsForParamMap = null;
            Specifications specificationsForEqualMTO = null;
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                String valueFind = entry.getValue();

                String[] arrayValueArr = valueFind.split("//");
                Specification sp = dtpr.filterEqualByFieldName(entry.getKey(), arrayValueArr[0]);
                specificationsForParamMap = Specifications.where(sp);

                if (arrayValueArr.length > 1) {
                    for (int i = 1; i < arrayValueArr.length; i++) {
                        Specification spValue = dtpr.filterEqualByFieldName(entry.getKey(), arrayValueArr[i]);
                        specificationsForParamMap = specificationsForParamMap.or(spValue);
                    }
                }
            }

            for (String key : manyToOneMap.keySet()) {
                KeyValueObj keyValueObj = manyToOneMap.get(key);
                if (keyValueObj instanceof FilterObj) {
                    FilterObj filterObj = (FilterObj) keyValueObj;
                    if (filterObj.getType().equals(FilterType.EQUAL)) {
                        Specification spValue = dtpr.filterEqualByMTOFieldName(key, filterObj, filterObj.getFilterValue());
                        if (specificationsForEqualMTO == null) {
                            specificationsForEqualMTO = Specifications.where(spValue);
                        } else {
                            specificationsForEqualMTO = specificationsForEqualMTO.and(spValue);
                        }
                    }
                }
            }
            specificationsForFilter = specificationsForFilter.and(specificationsForParamMap);
            if (specificationsForEqualMTO != null) {
                specificationsForFilter = specificationsForFilter.and(specificationsForEqualMTO);
            }
            long totalRows = baseRepository.count(specificationsForFilter);
            long page = start / length;

            logger.info(String.format("draw: %d, start: %d, length: %d, total row: %d, page: %d", draw, start, length, totalRows, page));

            Specifications specifications = dtpr.getMTOSpecifications();
            List<T> data;
            long recordsFiltered;
            if (specifications == null) {
                data = baseRepository.findAll(specificationsForFilter, dtpr.getPageRequest()).getContent();
                recordsFiltered = totalRows;
            } else {
                Specifications andStatusSpec = specifications.and(specificationsForFilter);
                data = baseRepository.findAll(andStatusSpec, dtpr.getPageRequest()).getContent();
                recordsFiltered = baseRepository.count(andStatusSpec);
            }

            return new DataTablePaginationResponse(draw, totalRows, recordsFiltered, data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
