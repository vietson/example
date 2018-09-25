package com.effect.tdb.ms.warehouseReport;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.logging.Logger;

/**
 * Created by Admin on 5/15/2018.
 */
@Configuration
@ComponentScan
@EntityScan("com.effect.tdb.ms.warehouseReport")
@EnableJpaRepositories("com.effect.tdb.ms.warehouseReport")
public class WarehouseReportConfiguration {
    protected Logger logger;

    public WarehouseReportConfiguration() {
        logger = Logger.getLogger(getClass().getName());
    }
}
