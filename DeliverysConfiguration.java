package com.effect.tdb.ms.delivery;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.logging.Logger;


@Configuration
@ComponentScan
@EntityScan("com.effect.tdb.ms.delivery")
@EnableJpaRepositories("com.effect.tdb.ms.delivery")
public class DeliverysConfiguration {
    protected Logger logger;

    public DeliverysConfiguration() {
        logger = Logger.getLogger(getClass().getName());
    }
}
