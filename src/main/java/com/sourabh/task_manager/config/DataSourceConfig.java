package com.sourabh.task_manager.config;
import javax.sql.DataSource;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DataSourceConfig {
    @Bean
    @Profile("dev")
    public DataSource devDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl("jdbc:postgresql://localhost:5432/dev_db");
        ds.setUsername("devuser");
        ds.setPassword("devpass");
        return ds;
    }

    @Bean
    @Profile("prod")
    public DataSource prodDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl("jdbc:postgresql://prod-db:5432/prod_db");
        ds.setUsername("produser");
        ds.setPassword("prodpass");
        return ds;
    }
}
