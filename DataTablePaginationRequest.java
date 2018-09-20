package com.effect.tdb.bs.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by datha on 4/6/2018.
 */
public class DataTablePaginationRequest<T> {
    private int draw;
    private int start;
    private int length;
    private String searchValue;
    private Map<Integer, DataTableColumn> columns = new HashMap<Integer, DataTableColumn>();

    private int orderColumnIndex;
    private String orderDirection;
    private PageRequest pageRequest;
    private Map<String, KeyValueObj> manyToOneMap = new HashMap<>();
    private List<FilterObject> filter = new ArrayList<FilterObject>();

    public List<FilterObject> getFilter() {
        return filter;
    }

    public void setFilter(List<FilterObject> filter) {
        this.filter = filter;
    }

    public Map<String, KeyValueObj> getManyToOneMap() {
        return manyToOneMap;
    }

    public void setManyToOneMap(Map<String, KeyValueObj> manyToOneMap) {
        this.manyToOneMap = manyToOneMap;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public Map<Integer, DataTableColumn> getColumns() {
        return columns;
    }

    public void setColumns(Map<Integer, DataTableColumn> columns) {
        this.columns = columns;
    }

    public PageRequest getPageRequest() {
        PageRequest pageRequest = null;
        try {
            if (!this.manyToOneMap.keySet().contains(this.getColumns().get(this.getOrderColumnIndex()).getData())) {
                Sort sort = new Sort(Sort.Direction.fromString(this.getOrderDirection()), this.getColumns().get(this.getOrderColumnIndex()).getData());
                pageRequest = new PageRequest(start / length, length, sort);
            } else {
                pageRequest = new PageRequest(start / length, length);
            }
        } catch (Exception e) {
            System.out.println("Co loi xay ra: " + e.getMessage());
        }
        return pageRequest;
    }

    public void setPageRequest(PageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }

    public int getOrderColumnIndex() {
        return orderColumnIndex;
    }

    public void setOrderColumnIndex(int orderColumnIndex) {
        this.orderColumnIndex = orderColumnIndex;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public Specifications getSpecifications() {
        Specifications specifications = null;
        Specifications specificationsAll = null;
        boolean filterAll = this.getSearchValue() != null && !this.getSearchValue().trim().isEmpty();
        for (int idx : this.getColumns().keySet()) {
            DataTableColumn dtc = this.getColumns().get(idx);
            if (dtc.getSearchValue() != null && !dtc.getSearchValue().trim().isEmpty()) {
                Specification specification = filterByFieldName(dtc.getData(), dtc.getSearchValue());
                Specifications filterSpec = Specifications.where(specification);
                if (specifications == null) {
                    specifications = filterSpec;
                } else {
                    specifications = specifications.and(filterSpec);
                }
            }

            Specification specificationAll = filterByFieldName(dtc.getData(), this.getSearchValue());
            Specifications filterSpecAll = Specifications.where(specificationAll);
            if (specificationsAll == null) {
                specificationsAll = filterSpecAll;
            } else {
                specificationsAll = specificationsAll.or(filterSpecAll);
            }

        }
        if (specifications == null) return specificationsAll;
        if (specificationsAll == null) return specifications;
        return specifications.and(specificationsAll);
    }

    public Specifications getMTOSpecifications() {
        Specifications specifications = null;
        Specifications specificationsAll = null;
        boolean filterAll = this.getSearchValue() != null && !this.getSearchValue().trim().isEmpty();
        for (int idx : this.getColumns().keySet()) {
            DataTableColumn dtc = this.getColumns().get(idx);
            if (dtc.getSearchValue() != null && !dtc.getSearchValue().trim().isEmpty()) {
                Specification specification;
                if (manyToOneMap == null || !manyToOneMap.keySet().contains(dtc.getData())) {
                    specification = filterByFieldName(dtc.getData(), dtc.getSearchValue());
                } else {
                    specification = filterAndSortByManyToOneField(dtc.getData(), manyToOneMap.get(dtc.getData()), dtc.getSearchValue());
                }

                Specifications filterSpec = Specifications.where(specification);
                if (specifications == null) {
                    specifications = filterSpec;
                } else {
                    specifications = specifications.and(filterSpec);
                }
            }

            Specification specificationAll;
            if (manyToOneMap == null || !manyToOneMap.keySet().contains(dtc.getData())) {
                specificationAll = filterByFieldName(dtc.getData(), this.getSearchValue());
            } else {
                specificationAll = filterAndSortByManyToOneField(dtc.getData(), manyToOneMap.get(dtc.getData()), this.getSearchValue());
            }


            Specifications filterSpecAll = Specifications.where(specificationAll);
            if (specificationsAll == null) {
                specificationsAll = filterSpecAll;
            } else {
                specificationsAll = specificationsAll.or(filterSpecAll);
            }

        }
        if (specifications == null) return specificationsAll;
        if (specificationsAll == null) return specifications;
        return specifications.and(specificationsAll);
    }

    public Specification<T> filterByFieldName(final String fieldName, final String filterValue) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                try {
                    if (null != filterValue && !filterValue.isEmpty()) {
                        final Predicate likePredicate = cq.where(cb.like(root.<String>get(fieldName).as(String.class), "%" + filterValue + "%")).getRestriction();
                        return likePredicate;
                    } else {
                        return null;
                    }
                } catch (Exception ex) {
                    System.out.println("co loi xay ra: " + ex.getMessage());
                    return null;
                }
            }
        };
    }

    public Specification<T> filterEqualByFieldName(final String fieldName, final String filterValue) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                try {
                    if (null != filterValue && !filterValue.isEmpty()) {
                        final Predicate likePredicate = cq.where(cb.equal(root.<String>get(fieldName).as(String.class), filterValue)).getRestriction();
                        return likePredicate;
                    } else {
                        return null;
                    }
                } catch (Exception ex) {
                    System.out.println("co loi xay ra: " + ex.getMessage());
                    return null;
                }
            }
        };
    }

    public Specification<T> filterByFieldName(final String fieldName, final String filterValue, final FilterType filterType) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                try {
                    Predicate predicate = null;
                    if (null != filterValue && !filterValue.isEmpty()) {
                        if (!fieldName.contains(".")) {
                            switch (filterType) {
                                case LIKE:
                                    predicate = cq.where(cb.like(root.<String>get(fieldName).as(String.class), "%" + filterValue + "%")).getRestriction();
                                    break;
                                case EQUAL:
                                    predicate = cq.where(cb.equal(root.<String>get(fieldName).as(String.class), filterValue)).getRestriction();
                                    break;
                                case GREATER_THAN_OR_EQUAL_DATE :
                                    LocalDate fromDate = LocalDate.parse(filterValue);
                                    predicate = cq.where(cb.greaterThanOrEqualTo(root.<String>get(fieldName).as(String.class), fromDate.toString())).getRestriction();
                                    break;
                                case LOWER_THAN_OR_EQUAL_DATE :
                                    LocalDate toDate = LocalDate.parse(filterValue);
                                    toDate = toDate.plusDays(1);
                                    predicate = cq.where(cb.lessThanOrEqualTo(root.<String>get(fieldName).as(String.class), toDate.toString())).getRestriction();
                                    break;
                                case GREATER :
                                    predicate = cq.where(cb.greaterThan(root.<String>get(fieldName).as(String.class), filterValue)).getRestriction();
                                    break;
                                case LOWER :
                                    predicate = cq.where(cb.lessThan(root.<String>get(fieldName).as(String.class), filterValue)).getRestriction();
                                    break;
                                case IN :
                                    String[] valArr = filterValue.split("//");
                                    List<String> valList = Arrays.asList(valArr);
                                    predicate = cq.where(root.<String>get(fieldName).in(valList)).getRestriction();
                                    break;
                                default:
                                    return null;

                            }
                        } else {
                            switch (filterType) {
                                case LIKE:
                                    predicate = cq.where(cb.like(joinByMTOFieldName(root, fieldName).as(String.class), "%" + filterValue + "%")).getRestriction();
                                    break;
                                case EQUAL:
                                    predicate = cq.where(cb.equal(joinByMTOFieldName(root, fieldName).as(String.class), filterValue)).getRestriction();
                                    break;
                                case GREATER_THAN_OR_EQUAL_DATE :
                                    predicate = cq.where(cb.greaterThanOrEqualTo(joinByMTOFieldName(root, fieldName).as(String.class), filterValue)).getRestriction();
                                    break;
                                case LOWER_THAN_OR_EQUAL_DATE :
                                    predicate = cq.where(cb.lessThanOrEqualTo(joinByMTOFieldName(root, fieldName).as(String.class), filterValue)).getRestriction();
                                    break;
                                case GREATER :
                                    predicate = cq.where(cb.greaterThan(joinByMTOFieldName(root, fieldName).as(String.class), filterValue)).getRestriction();
                                    break;
                                case LOWER :
                                    predicate = cq.where(cb.lessThan(joinByMTOFieldName(root, fieldName).as(String.class), filterValue)).getRestriction();
                                    break;
                                case IN :
                                    String[] valArr = filterValue.split("//");
                                    List<String> valList = Arrays.asList(valArr);
                                    predicate = cq.where(joinByMTOFieldName(root, fieldName).as(String.class).in(valList)).getRestriction();
                                    break;
                                default:
                                    return null;

                            }

                        }
                        return predicate;
                    } else {
                        return null;
                    }
                } catch (Exception ex) {
                    System.out.println("co loi xay ra: " + ex.getMessage());
                    return null;
                }
            }
        };
    }

    public Specification<T> filterEqualByMTOFieldName(final String fieldName, final KeyValueObj joinTableInfo, final String filterValue) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                try {
                    if (null != filterValue && !filterValue.isEmpty()) {
                        final Predicate likePredicate =
                                cq.where(cb.equal(root.join(joinTableInfo.getValue()).get(joinTableInfo.getKey()).as(String.class), filterValue)).getRestriction();
                        return likePredicate;
                    } else {
                        return null;
                    }
                } catch (Exception ex) {
                    System.out.println("co loi xay ra: " + ex.getMessage());
                    return null;
                }
            }
        };
    }

    public Specification<T> filterGreaterThanByFieldName(final String fieldName, final String filterValue) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                try {
                    if (null != filterValue && !filterValue.isEmpty()) {
                        final Predicate likePredicate = cq.where(cb.greaterThanOrEqualTo(root.<String>get(fieldName).as(String.class), filterValue)).getRestriction();
                        return likePredicate;
                    } else {
                        return null;
                    }
                } catch (Exception ex) {
                    System.out.println("co loi xay ra: " + ex.getMessage());
                    return null;
                }
            }
        };
    }


    public Path joinByMTOFieldName(Root<T> root, final String fieldName, final KeyValueObj joinTableInfo) {
        try {
            System.out.println("join table: " + joinTableInfo.getValue());
            Join join = root.join(joinTableInfo.getValue());
            String fieldName_ = fieldName;
            String[] fieldNameSpls = fieldName_.split("\\.");
            System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiii: " + Arrays.deepToString(fieldNameSpls));
            if (fieldNameSpls.length > 1) {
                System.out.println("eeeeeeeeeeeeeeee: " + Arrays.deepToString(fieldNameSpls));
            }
            int count = 0;
            for (String fieldNameSpl : fieldNameSpls) {
                count++;
                if (count == fieldNameSpls.length) {
                    fieldName_ = fieldNameSpls[count - 1];
                } else {
                    join = join.join(fieldNameSpls[count - 1]);
                }
            }


            return join.get(fieldName_);

        } catch (Exception ex) {
            System.out.println("co loi xay ra: " + ex.getMessage());
            return null;
        }

    }


    public Path joinByMTOFieldName(Root<T> root, final String fieldName) {
        try {
            String fieldName_ = fieldName;
            String[] fieldNameSpls = fieldName_.split("\\.");
            System.out.println("uuuuuuuuuu: " + Arrays.deepToString(fieldNameSpls));

            System.out.println("join table: " + fieldNameSpls[0]);
            Join join = root.join(fieldNameSpls[0]);
            if (fieldNameSpls.length > 1) {
                for (int i = 1; i < fieldNameSpls.length - 1; i++) {
                    join = join.join(fieldNameSpls[i]);
                }
            }
            return join.get(fieldNameSpls[fieldNameSpls.length - 1]);
//            return join.get(fieldName_);

        } catch (Exception ex) {
            System.out.println("co loi xay ra: " + ex.getMessage());
            return null;
        }

    }

    public Specification<T> filterLowerThanByFieldName(final String fieldName, final String filterValue) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                try {
                    if (null != filterValue && !filterValue.isEmpty()) {
                        final Predicate likePredicate = cq.where(cb.lessThanOrEqualTo(root.<String>get(fieldName).as(String.class), filterValue)).getRestriction();
                        return likePredicate;
                    } else {
                        return null;
                    }
                } catch (Exception ex) {
                    System.out.println("co loi xay ra: " + ex.getMessage());
                    return null;
                }
            }
        };
    }


    public Specification<T> filterAndSortByManyToOneField(final String specFieldName, final KeyValueObj joinTableInfo, final String filterValue) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                try {

                    System.out.println("specFieldName: " + specFieldName + ", table: " + joinTableInfo.getValue() + ", fieldName: " + joinTableInfo.getKey());

                    final Predicate filterAndSortPredicate;
                    if (!specFieldName.equals(DataTablePaginationRequest.this.getColumns().get(DataTablePaginationRequest.this.getOrderColumnIndex()).getData())) {
                        if (filterValue != null && !filterValue.isEmpty()) {
                            filterAndSortPredicate = cq.where(cb.like(joinByMTOFieldName(root, joinTableInfo.getKey(), joinTableInfo).as(String.class), "%" + filterValue + "%")).getRestriction();
                        } else {
                            filterAndSortPredicate = null;
                        }
                    } else {
                        Sort.Direction sortDirection = Sort.Direction.fromString(DataTablePaginationRequest.this.getOrderDirection());
                        if (Sort.Direction.ASC.equals(sortDirection)) {
                            if (filterValue != null && !filterValue.isEmpty()) {
                                filterAndSortPredicate = cq.where(cb.like(joinByMTOFieldName(root, joinTableInfo.getKey(), joinTableInfo).as(String.class), "%" + filterValue + "%"))
                                        .orderBy(cb.asc(joinByMTOFieldName(root, joinTableInfo.getKey(), joinTableInfo)))
                                        .getRestriction();
                            } else {
                                filterAndSortPredicate = cq.orderBy(cb.asc(joinByMTOFieldName(root, joinTableInfo.getKey(), joinTableInfo)))
                                        .getRestriction();
                            }
                        } else {
                            if (filterValue != null && !filterValue.isEmpty()) {
                                filterAndSortPredicate = cq.where(cb.like(joinByMTOFieldName(root, joinTableInfo.getKey(), joinTableInfo).as(String.class), "%" + filterValue + "%"))
                                        .orderBy(cb.desc(joinByMTOFieldName(root, joinTableInfo.getKey(), joinTableInfo)))
                                        .getRestriction();
                            } else {
                                filterAndSortPredicate = cq.orderBy(cb.desc(joinByMTOFieldName(root, joinTableInfo.getKey(), joinTableInfo)))
                                        .getRestriction();
                            }

                        }
                    }
                    return filterAndSortPredicate;
                } catch (Exception ex) {
                    System.out.println("co loi xay ra: " + ex.getMessage());
                    return null;
                }
            }
        };
    }

}
