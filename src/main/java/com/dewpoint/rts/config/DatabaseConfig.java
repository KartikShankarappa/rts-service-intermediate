package com.dewpoint.rts.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    @Autowired
    private Environment env;

//    @Value("${datasource.jndi}")
//    private String datasourceJndi;

//    @Bean
//    public DataSource dataSource() {
//        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
//        dsLookup.setResourceRef(true);
//        return dsLookup.getDataSource(datasourceJndi);
//    }

    /**
     * DataSource definition for database connection.
     * Settings are read from the application.properties file using the env object
     */
    @Bean
    public DataSource dataSource() throws SQLException {
//        OracleDataSource dataSource = new OracleDataSource();
//        dataSource.setURL(env.getProperty("db.url"));
//        dataSource.setUser(env.getProperty("db.username"));
//        dataSource.setPassword(env.getProperty("db.password"));
//        return dataSource;
        HikariDataSource ds = new HikariDataSource();
        ds.setMaximumPoolSize(10);
        ds.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        ds.addDataSourceProperty("url", "jdbc:mysql://localhost:3306/rts");
        ds.addDataSourceProperty("user", "root");
        ds.addDataSourceProperty("password", "Welcome1");
//        ds.addDataSourceProperty("cachePrepStmts", true);
//        ds.addDataSourceProperty("prepStmtCacheSize", 250);
//        ds.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
//        ds.addDataSourceProperty("useServerPrepStmts", true);
        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
//        entityManagerFactoryBean.setPackagesToScan(new String[]{env.getProperty("entitymanager.packagesToScan")});
        entityManagerFactoryBean.setPackagesToScan(new String[]{"com.dewpoint.rts.model"});


        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        Properties jpaProterties = new Properties();
        jpaProterties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        jpaProterties.put("hibernate.show_sql", "true");
        entityManagerFactoryBean.setJpaProperties(jpaProterties);

        entityManagerFactoryBean.setJpaVendorAdapter(adapter);
        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    /**
     * PersistenceExceptionTranslationPostProcessor is a bean post processor
     * which adds an advisor to any bean annotated with Repository so that any
     * platform-specific exceptions are caught and then rethrown as one
     * Spring's unchecked data access exceptions (i.e. a subclass of DataAccessException).
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}