package com.demo.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.codingapi.tx.datasource.relational.LCNTransactionDataSource;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * <p>数据源配置</p>
 *
 * @author 张峰 zfvip_it@163.com
 * @createTime: 2017/1/15 11:45
 */
@Configuration
@MapperScan(basePackages = WriterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "writerSqlSessionFactory")
public class WriterDataSourceConfig {

    static final String PACKAGE = "com.demo.writer";

    @Resource
    private Environment environment;

    @Bean(name = "writerDataSource")
    @Primary
    public DataSource writerDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        try {
            dataSource.setDriverClassName(environment.getProperty("writer.jdbc.driverClassName"));
            dataSource.setUrl(environment.getProperty("writer.jdbc.url"));
            dataSource.setUsername(environment.getProperty("writer.jdbc.username"));
            dataSource.setPassword(environment.getProperty("writer.jdbc.password"));
            //打开PSCache，并且指定每个连接上PSCache的大小
            dataSource.setPoolPreparedStatements(true);
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
            //配置监控统计拦截的filters，去掉后监控界面sql无法统计
            dataSource.setFilters("stat");
            LCNTransactionDataSource lcnTransactionDataSource = new LCNTransactionDataSource();
            //数据源
            lcnTransactionDataSource.setDataSource(dataSource);
            //lcn最大连接
            lcnTransactionDataSource.setMaxCount(20);
            return lcnTransactionDataSource;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean(name = "writerTransactionManager")
    @Primary
    public DataSourceTransactionManager writerTransactionManager() {
        return new DataSourceTransactionManager(writerDataSource());
    }

    @Bean(name = "writerSqlSessionFactory")
    @Primary
    public SqlSessionFactory writerSqlSessionFactory(@Qualifier("writerDataSource") DataSource rdsDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(rdsDataSource);
        sessionFactory.setTypeAliasesPackage("com.demo.domain");

        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);

        //添加插件
        sessionFactory.setPlugins(new Interceptor[]{pageHelper});
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            sessionFactory.setMapperLocations(resolver.getResources("classpath:com/demo/writer/*.xml"));
            return sessionFactory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean(name = "writerJdbcTemplate")
    public JdbcTemplate writerJdbcTemplate(
            @Qualifier("writerDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
