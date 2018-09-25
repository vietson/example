package com.effect.tdb.ms.warehouseReport;

import com.effect.tdb.bs.common.BaseUtils;
import com.effect.tdb.bs.common.DataTablePaginationRequest;
import com.effect.tdb.bs.common.DataTablePaginationResponse;
import com.effect.tdb.bs.common.FilterObject;
import com.effect.tdb.ms.common.Constants;
import com.effect.tdb.ms.common.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 5/14/2018.
 */

@Component
public class WarehouseReportService {

    private static Logger logger = Logger.getLogger(WarehouseReportService.class);
    @Autowired
    protected WarehousesReportRepository warehousesReportRepository;


    @Autowired
    public WarehouseReportService(WarehousesReportRepository warehousesReportRepository) {
        this.warehousesReportRepository = warehousesReportRepository;
    }


    @Autowired
    protected EntityManager entityManager;


    /***
     * Lấy báo csao phân hệ kho
     * reportName: intOutInventoryDetail báo cáo chi tiết nhập xuất tồn kho, inventoryByProduct tồn kho theo sản phẩm, inventoryReport Báo cáo tồn theo kho
     */
    public DataTablePaginationResponse getInventoryReport(MultiValueMap<String, String> params) {

        String keyReportName = "reportName";
        String keyFromDate = "fromDate";
        String keyToDate = "toDate";
        String keyIsAdminOrKT = "isAdminOrKT";
        String keyStoreId = "stockId";
        DataTablePaginationRequest dtpr = BaseUtils.getDatatableRequestInfo(params);
        List<FilterObject> listFilter = dtpr.getFilter();
//        String searchValue = params.get("search[value]").get(0);
        Long recordsTotal = 0l;
        Long recordsFiltered = 0l;
        Long draw = 0l;

        Long openingBalanceTotal = 0l;
        Long outputPeriodTotal = 0l;
        Long inputPeriodTotal = 0l;
        Long receiptQuantityTotal = 0l;
        try {
            if (params == null || params.isEmpty()) {
                DataTablePaginationResponse dataTablePaginationResponse = null;
                return dataTablePaginationResponse;
            }
            String reportName = listFilter.stream().filter(a -> a.getFieldName().equals(keyReportName)).map(FilterObject::getValue).findFirst().orElse(null);
            String fromDate = listFilter.stream().filter(a -> a.getFieldName().equals(keyFromDate)).map(FilterObject::getValue).findFirst().orElse(null);
            String toDate = listFilter.stream().filter(a -> a.getFieldName().equals(keyToDate)).map(FilterObject::getValue).findFirst().orElse(null);
            Integer storeId = 0;
            Boolean isAdminOrKT = false;
            if (listFilter.stream().filter(a -> a.getFieldName().equals(keyStoreId)).map(FilterObject::getValue).findFirst().orElse(null) != null) {
                storeId = Integer.parseInt(listFilter.stream().filter(a -> a.getFieldName().equals(keyStoreId)).map(FilterObject::getValue).findFirst().orElse(null));
            }
            if (listFilter.stream().filter(a -> a.getFieldName().equals(keyIsAdminOrKT)).map(FilterObject::getValue).findFirst().orElse(null) != null) {
                isAdminOrKT = Boolean.getBoolean(listFilter.stream().filter(a -> a.getFieldName().equals(keyIsAdminOrKT)).map(FilterObject::getValue).findFirst().orElse(null));
            }
            DataTablePaginationResponse dataTablePaginationResponse = new DataTablePaginationResponse(draw, recordsTotal, recordsFiltered, null);
            String sWhere = BaseUtils.generateWhereClause(params);
            String sSort = BaseUtils.generateSortOrder(params);
            draw = Long.parseLong(params.get("draw").get(0));
            Long start = Long.parseLong(params.get("start").get(0));
            Long length = Long.parseLong(params.get("length").get(0));

            switch (reportName) {
                case Constants.INVENTORY_REPORT:
                    List<WarrehouseInvertoryReportEntity> data = new ArrayList<WarrehouseInvertoryReportEntity>();
                    dataTablePaginationResponse = new DataTablePaginationResponse(draw, recordsTotal, recordsFiltered, data);


                    dataTablePaginationResponse.setDraw(draw);

                    StoredProcedureQuery query = entityManager.createStoredProcedureQuery("proc_get_inventory_report", WarrehouseInvertoryReportEntity.class);
                    query.registerStoredProcedureParameter("fromDate", String.class, ParameterMode.IN).setParameter("fromDate", fromDate);
                    query.registerStoredProcedureParameter("toDate", String.class, ParameterMode.IN).setParameter("toDate", toDate);
                    query.registerStoredProcedureParameter("isAdminOrKT", Boolean.class, ParameterMode.IN).setParameter("isAdminOrKT", isAdminOrKT);
                    query.registerStoredProcedureParameter("stockId", Integer.class, ParameterMode.IN).setParameter("stockId", storeId);
                    query.registerStoredProcedureParameter("sort", String.class, ParameterMode.IN).setParameter("sort", sSort);
                    query.registerStoredProcedureParameter("skip", Long.class, ParameterMode.IN).setParameter("skip", start);
                    query.registerStoredProcedureParameter("take", Long.class, ParameterMode.IN).setParameter("take", length);
                    query.registerStoredProcedureParameter("sWhere", String.class, ParameterMode.IN).setParameter("sWhere", sWhere);
                    query.registerStoredProcedureParameter("recordsTotal", Long.class, ParameterMode.INOUT).setParameter("recordsTotal", recordsTotal);
                    query.registerStoredProcedureParameter("recordsFiltered", Long.class, ParameterMode.INOUT).setParameter("recordsFiltered", recordsFiltered);

                    query.registerStoredProcedureParameter("openingBalanceTotal", Long.class, ParameterMode.INOUT).setParameter("openingBalanceTotal", openingBalanceTotal);
                    query.registerStoredProcedureParameter("outputPeriodTotal", Long.class, ParameterMode.INOUT).setParameter("outputPeriodTotal", outputPeriodTotal);
                    query.registerStoredProcedureParameter("inputPeriodTotal", Long.class, ParameterMode.INOUT).setParameter("inputPeriodTotal", inputPeriodTotal);
                    query.registerStoredProcedureParameter("receiptQuantityTotal", Long.class, ParameterMode.INOUT).setParameter("receiptQuantityTotal", receiptQuantityTotal);

                    query.execute();

                    List<WarrehouseInvertoryReportEntity> listWarrehouseInvertoryReport = (List<WarrehouseInvertoryReportEntity>) query.getResultList();
                    dataTablePaginationResponse.setData(listWarrehouseInvertoryReport);


                    recordsTotal = (Long) query.getOutputParameterValue("recordsTotal");
                    recordsFiltered = (Long) query.getOutputParameterValue("recordsFiltered");

                    dataTablePaginationResponse.setRecordsFiltered(recordsFiltered);
                    dataTablePaginationResponse.setRecordsTotal(recordsTotal);

                    WarrehouseInvertoryReportEntity warrehouseInvertoryReportSum = new WarrehouseInvertoryReportEntity();
                    warrehouseInvertoryReportSum.setOpening_balance((Long) query.getOutputParameterValue("openingBalanceTotal"));
                    warrehouseInvertoryReportSum.setOutput_period((Long) query.getOutputParameterValue("outputPeriodTotal"));
                    warrehouseInvertoryReportSum.setInput_period((Long) query.getOutputParameterValue("inputPeriodTotal"));
                    warrehouseInvertoryReportSum.setReceipt_quantity((Long) query.getOutputParameterValue("receiptQuantityTotal"));
                    dataTablePaginationResponse.setSumData(warrehouseInvertoryReportSum);
                    break;
                case Constants.INOUT_INVENTORY_DETAIL:
                    List<IntOutInventoryDetailEntity> dataInOut = new ArrayList<IntOutInventoryDetailEntity>();
                    dataTablePaginationResponse = new DataTablePaginationResponse(draw, recordsTotal, recordsFiltered, dataInOut);


                    dataTablePaginationResponse.setDraw(draw);

                    StoredProcedureQuery queryInOut = entityManager.createStoredProcedureQuery("proc_get_int_out_inventory_detail", IntOutInventoryDetailEntity.class);
                    queryInOut.registerStoredProcedureParameter("fromDate", String.class, ParameterMode.IN).setParameter("fromDate", fromDate);
                    queryInOut.registerStoredProcedureParameter("toDate", String.class, ParameterMode.IN).setParameter("toDate", toDate);
                    queryInOut.registerStoredProcedureParameter("isAdminOrKT", Boolean.class, ParameterMode.IN).setParameter("isAdminOrKT", isAdminOrKT);
                    queryInOut.registerStoredProcedureParameter("stockId", Integer.class, ParameterMode.IN).setParameter("stockId", storeId);
                    queryInOut.registerStoredProcedureParameter("sort", String.class, ParameterMode.IN).setParameter("sort", sSort);
                    queryInOut.registerStoredProcedureParameter("skip", Long.class, ParameterMode.IN).setParameter("skip", start);
                    queryInOut.registerStoredProcedureParameter("take", Long.class, ParameterMode.IN).setParameter("take", length);
                    queryInOut.registerStoredProcedureParameter("sWhere", String.class, ParameterMode.IN).setParameter("sWhere", sWhere);
                    queryInOut.registerStoredProcedureParameter("recordsTotal", Long.class, ParameterMode.INOUT).setParameter("recordsTotal", recordsTotal);
                    queryInOut.registerStoredProcedureParameter("recordsFiltered", Long.class, ParameterMode.INOUT).setParameter("recordsFiltered", recordsFiltered);

                    queryInOut.registerStoredProcedureParameter("outputTotal", Long.class, ParameterMode.INOUT).setParameter("outputTotal", outputPeriodTotal);
                    queryInOut.registerStoredProcedureParameter("inputTotal", Long.class, ParameterMode.INOUT).setParameter("inputTotal", inputPeriodTotal);
                    queryInOut.registerStoredProcedureParameter("convertTotal", Long.class, ParameterMode.INOUT).setParameter("convertTotal", receiptQuantityTotal);

                    queryInOut.execute();
                    List<WarrehouseInvertoryReportEntity> listInOutInvertoryReport = (List<WarrehouseInvertoryReportEntity>) queryInOut.getResultList();
                    dataTablePaginationResponse.setData(listInOutInvertoryReport);


                    recordsTotal = (Long) queryInOut.getOutputParameterValue("recordsTotal");
                    recordsFiltered = (Long) queryInOut.getOutputParameterValue("recordsFiltered");

                    dataTablePaginationResponse.setRecordsFiltered(recordsFiltered);
                    dataTablePaginationResponse.setRecordsTotal(recordsTotal);

                    IntOutInventoryDetailEntity intOutInventoryDetailSum = new IntOutInventoryDetailEntity();
                    intOutInventoryDetailSum.setOut_quantity((Long) queryInOut.getOutputParameterValue("outputTotal"));
                    intOutInventoryDetailSum.setInput_quanity((Long) queryInOut.getOutputParameterValue("inputTotal"));
                    intOutInventoryDetailSum.setQuantity_convert((Long) queryInOut.getOutputParameterValue("convertTotal"));

                    dataTablePaginationResponse.setSumData(intOutInventoryDetailSum);
                    break;
                case Constants.INVENTORY_BY_PRODUCT:

                    List<WarrehouseInvertoryReportEntity> dataProduct = new ArrayList<WarrehouseInvertoryReportEntity>();
                    dataTablePaginationResponse = new DataTablePaginationResponse(draw, recordsTotal, recordsFiltered, dataProduct);


                    dataTablePaginationResponse.setDraw(draw);

                    StoredProcedureQuery queryProduct = entityManager.createStoredProcedureQuery("proc_get_inventory_by_product", WarrehouseInvertoryReportEntity.class);
                    queryProduct.registerStoredProcedureParameter("fromDate", String.class, ParameterMode.IN).setParameter("fromDate", fromDate);
                    queryProduct.registerStoredProcedureParameter("toDate", String.class, ParameterMode.IN).setParameter("toDate", toDate);
                    queryProduct.registerStoredProcedureParameter("isAdminOrKT", Boolean.class, ParameterMode.IN).setParameter("isAdminOrKT", isAdminOrKT);
                    queryProduct.registerStoredProcedureParameter("stockId", Integer.class, ParameterMode.IN).setParameter("stockId", storeId);
                    queryProduct.registerStoredProcedureParameter("sort", String.class, ParameterMode.IN).setParameter("sort", sSort);
                    queryProduct.registerStoredProcedureParameter("skip", Long.class, ParameterMode.IN).setParameter("skip", start);
                    queryProduct.registerStoredProcedureParameter("take", Long.class, ParameterMode.IN).setParameter("take", length);
                    queryProduct.registerStoredProcedureParameter("sWhere", String.class, ParameterMode.IN).setParameter("sWhere", sWhere);
                    queryProduct.registerStoredProcedureParameter("recordsTotal", Long.class, ParameterMode.INOUT).setParameter("recordsTotal", recordsTotal);
                    queryProduct.registerStoredProcedureParameter("recordsFiltered", Long.class, ParameterMode.INOUT).setParameter("recordsFiltered", recordsFiltered);

                    queryProduct.registerStoredProcedureParameter("openingBalanceTotal", Long.class, ParameterMode.INOUT).setParameter("openingBalanceTotal", openingBalanceTotal);
                    queryProduct.registerStoredProcedureParameter("outputPeriodTotal", Long.class, ParameterMode.INOUT).setParameter("outputPeriodTotal", outputPeriodTotal);
                    queryProduct.registerStoredProcedureParameter("inputPeriodTotal", Long.class, ParameterMode.INOUT).setParameter("inputPeriodTotal", inputPeriodTotal);
                    queryProduct.registerStoredProcedureParameter("receiptQuantityTotal", Long.class, ParameterMode.INOUT).setParameter("receiptQuantityTotal", receiptQuantityTotal);
                    queryProduct.execute();

                    dataTablePaginationResponse.setData((List<WarrehouseInvertoryReportEntity>) queryProduct.getResultList());
                    recordsTotal = (Long) queryProduct.getOutputParameterValue("recordsTotal");
                    recordsFiltered = (Long) queryProduct.getOutputParameterValue("recordsFiltered");

                    dataTablePaginationResponse.setRecordsFiltered(recordsFiltered);
                    dataTablePaginationResponse.setRecordsTotal(recordsTotal);

                    InventoryByProductEntity inventoryByProductSum = new InventoryByProductEntity();
                    inventoryByProductSum.setOpening_balance((Long) queryProduct.getOutputParameterValue("openingBalanceTotal"));
                    inventoryByProductSum.setOutput_period((Long) queryProduct.getOutputParameterValue("outputPeriodTotal"));
                    inventoryByProductSum.setInput_period((Long) queryProduct.getOutputParameterValue("inputPeriodTotal"));
                    inventoryByProductSum.setReceipt_quantity((Long) queryProduct.getOutputParameterValue("receiptQuantityTotal"));
                    dataTablePaginationResponse.setSumData(inventoryByProductSum);

                    break;
                case Constants.TRANSFER_INVENTORY_REPORT:
                    List<WarehouseProductTranferEntity> dataTranfer = new ArrayList<WarehouseProductTranferEntity>();
                    dataTablePaginationResponse = new DataTablePaginationResponse(draw, recordsTotal, recordsFiltered, dataTranfer);

                    StoredProcedureQuery queryTranfer = entityManager.createStoredProcedureQuery("proc_get_product_transfer_internal", WarehouseProductTranferEntity.class);
                    queryTranfer.registerStoredProcedureParameter("fromDate", String.class, ParameterMode.IN).setParameter("fromDate", fromDate);
                    queryTranfer.registerStoredProcedureParameter("toDate", String.class, ParameterMode.IN).setParameter("toDate", toDate);
                    queryTranfer.registerStoredProcedureParameter("isAdminOrKT", Boolean.class, ParameterMode.IN).setParameter("isAdminOrKT", isAdminOrKT);
                    queryTranfer.registerStoredProcedureParameter("stockId", Integer.class, ParameterMode.IN).setParameter("stockId", storeId);
                    queryTranfer.registerStoredProcedureParameter("sort", String.class, ParameterMode.IN).setParameter("sort", sSort);
                    queryTranfer.registerStoredProcedureParameter("skip", Long.class, ParameterMode.IN).setParameter("skip", start);
                    queryTranfer.registerStoredProcedureParameter("take", Long.class, ParameterMode.IN).setParameter("take", length);
                    queryTranfer.registerStoredProcedureParameter("sWhere", String.class, ParameterMode.IN).setParameter("sWhere", sWhere);
                    queryTranfer.registerStoredProcedureParameter("recordsTotal", Long.class, ParameterMode.INOUT).setParameter("recordsTotal", recordsTotal);
                    queryTranfer.registerStoredProcedureParameter("recordsFiltered", Long.class, ParameterMode.INOUT).setParameter("recordsFiltered", recordsFiltered);
                    queryTranfer.registerStoredProcedureParameter("outputTotal", Long.class, ParameterMode.INOUT).setParameter("outputTotal", outputPeriodTotal);
                    queryTranfer.registerStoredProcedureParameter("inputTotal", Long.class, ParameterMode.INOUT).setParameter("inputTotal", inputPeriodTotal);
                    queryTranfer.registerStoredProcedureParameter("differenceTotal", Long.class, ParameterMode.INOUT).setParameter("differenceTotal", receiptQuantityTotal);
                    queryTranfer.execute();

                    dataTablePaginationResponse.setData((List<WarrehouseInvertoryReportEntity>) queryTranfer.getResultList());
                    recordsTotal = (Long) queryTranfer.getOutputParameterValue("recordsTotal");
                    recordsFiltered = (Long) queryTranfer.getOutputParameterValue("recordsFiltered");

                    dataTablePaginationResponse.setRecordsFiltered(recordsFiltered);
                    dataTablePaginationResponse.setRecordsTotal(recordsTotal);

                    WarehouseProductTranferEntity inventoryTranferSum = new WarehouseProductTranferEntity();

                    inventoryTranferSum.setOutput_quantity((Long) queryTranfer.getOutputParameterValue("outputTotal"));
                    inventoryTranferSum.setInput_quantity((Long) queryTranfer.getOutputParameterValue("inputTotal"));
                    inventoryTranferSum.setDifference((Long) queryTranfer.getOutputParameterValue("differenceTotal"));
                    dataTablePaginationResponse.setSumData(inventoryTranferSum);

                    break;
                default:
                    break;
            }
            return dataTablePaginationResponse;


        } catch (Exception ex) {
            ex.printStackTrace();
            DataTablePaginationResponse dataTablePaginationResponse = new DataTablePaginationResponse(draw, recordsTotal, recordsFiltered, null);
            dataTablePaginationResponse.setSuccess(false);
            return dataTablePaginationResponse;
        } finally {
            Utils.closeEN(entityManager);
        }
    }


}


