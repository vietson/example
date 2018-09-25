package com.effect.tdb.ms.warehouseReport;

import com.effect.tdb.bs.common.BaseUtils;
import com.effect.tdb.bs.common.DataTablePaginationResponse;
import com.effect.tdb.ms.common.Constants;
import com.effect.tdb.ms.common.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 5/14/2018.
 */

@CrossOrigin
@RestController
@RequestMapping(value = Constants.WAREHOUSEREPORT_BASE_URL, method = RequestMethod.GET)
public class WarehouseReportController {

    private static Logger logger = Logger.getLogger(WarehouseReportController.class);
    @Autowired
    protected WarehousesReportRepository warehousesReportRepository;


    @Autowired
    public WarehouseReportController(WarehousesReportRepository warehousesReportRepository) {
        this.warehousesReportRepository = warehousesReportRepository;
    }


    @Autowired
    protected EntityManager entityManager;

    @Autowired
    protected WarehouseReportService warehouseReportService;

    /***
     * Lấy báo csao phân hệ kho
     * reportName: intOutInventoryDetail báo cáo chi tiết nhập xuất tồn kho, inventoryByProduct tồn kho theo sản phẩm, inventoryReport Báo cáo tồn theo kho
     */
    @RequestMapping(value = Constants.WAREHOUSEREPORT_GET_INVENTORY, method = RequestMethod.GET)
    public @ResponseBody
    DataTablePaginationResponse getInventoryReport(@RequestParam MultiValueMap<String, String> params, ModelMap model) {
        return warehouseReportService.getInventoryReport(params);
    }


}


