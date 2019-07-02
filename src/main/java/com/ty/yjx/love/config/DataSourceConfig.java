package com.ty.yjx.love.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/11
 */
@Configuration
@MapperScan(basePackages = DataSourceConfig.PACKAGE, sqlSessionFactoryRef = "commonSqlSessionFactory")
public class DataSourceConfig {

    static final String PACKAGE = "com.ty.yjx.love.dao";
    private static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    @Value("${datasource.driver-class-name}")
    private String driverClass;

    @Value("${datasource.url}")
    private String url;

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.password}")
    private String password;

    @Bean(name = "commonDataSource")
    @Primary
    public DataSource commonDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "commonTransactionManager")
    @Primary
    public DataSourceTransactionManager commonTransactionManager() {
        return new DataSourceTransactionManager(commonDataSource());
    }

    @Bean(name = "commonSqlSessionFactory")
    @Primary
    public SqlSessionFactory commonSqlSessionFactory(@Qualifier("commonDataSource") DataSource commonDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(commonDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(DataSourceConfig.MAPPER_LOCATION));
        // 打印sql 撤回
        org.apache.ibatis.session.Configuration conf = new org.apache.ibatis.session.Configuration();
        conf.setLogImpl(StdOutImpl.class);
        sessionFactory.setConfiguration(conf);
        return sessionFactory.getObject();
    }
}
