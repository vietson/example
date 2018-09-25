package com.effect.tdb.ms.warehouseReport;

/**
 * Created by Admin on 5/16/2018.
 */
public interface WarehousesReportRepository extends org.springframework.data.repository.PagingAndSortingRepository<WarrehouseInvertoryReportEntity, Long>, org.springframework.data.jpa.repository.JpaSpecificationExecutor {
}

