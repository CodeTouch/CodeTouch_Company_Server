package com.tagmaster.codetouch.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.tagmaster.codetouch.repository.customer", // CustomerDB의 JPA Repository 경로
        entityManagerFactoryRef = "customerEntityManagerFactory",
        transactionManagerRef = "customerTransactionManager"
)
public class CustomerDBConfig {

    @Bean(name = "customerDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.customer")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "customerEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("customerDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.tagmaster.codetouch.entity.customer") // CustomerDB 관련 엔티티 경로
                .persistenceUnit("customer")
                .build();
    }

    @Bean(name = "customerTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("customerEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}