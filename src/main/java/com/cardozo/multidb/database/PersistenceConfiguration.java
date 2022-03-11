package com.cardozo.multidb.database;

import com.cardozo.multidb.enums.DatabaseId;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

@AllArgsConstructor
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.cardozo.multidb",
        entityManagerFactoryRef = "multiEntityManager",
        transactionManagerRef = "multiTransactionManager"
)
public class PersistenceConfiguration {
    private final String PACKAGE_SCAN = "com.cardozo.multidb";

    private final JpaProperties jpaProperties;

    @Bean
    public PlatformTransactionManager multiTransactionManager() {
        final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(multiEntityManager().getObject());
        return jpaTransactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean multiEntityManager() {
        final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setPackagesToScan(PACKAGE_SCAN);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setDataSource(multiRoutingDataSource());
        return localContainerEntityManagerFactoryBean;
    }

    private HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        final HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(jpaProperties.isShowSql());
        hibernateJpaVendorAdapter.setGenerateDdl(jpaProperties.isGenerateDdl());
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public DataSource multiRoutingDataSource() {
        return DatabaseConnection.MultiRoutingDataSource.getNewInstance(Map.of(
                DatabaseId.MAIN, mainDataSource(),
                DatabaseId.FIRST, firstDataSource(),
                DatabaseId.SECOND, secondDataSource(),
                DatabaseId.THIRD, thirdDataSource()
        ));
    }

    @ConfigurationProperties("spring.datasource.main")
    @Primary
    @Bean
    public DataSource mainDataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @ConfigurationProperties("spring.datasource.first")
    @Bean
    public DataSource firstDataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @ConfigurationProperties("spring.datasource.second")
    @Bean
    public DataSource secondDataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @ConfigurationProperties("spring.datasource.third")
    @Bean
    public DataSource thirdDataSource() {
        return DataSourceBuilder.create()
                .build();
    }
}
