package com.demo.config;

import com.weibo.api.motan.config.springsupport.BasicRefererConfigBean;
import com.weibo.api.motan.config.springsupport.RegistryConfigBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * <p>motan消费端配置</p>
 *
 * @author 张峰 zfvip_it@163.com
 * @createTime: 2017/1/16 13:45
 */
@Configuration
public class MotanClientConfiguration implements EnvironmentAware {

    @Resource
    private Environment environment;

//    @Bean
//    public AnnotationBean motanAnnotationBean() {
//        AnnotationBean motanAnnotationBean = new AnnotationBean();
//        //添加用到motan注解的类的包名
//        motanAnnotationBean.setPackage("com.demo.service");
//        return motanAnnotationBean;
//    }

//    @Bean(name = "motan")
//    public ProtocolConfigBean protocolConfig1() {
//        ProtocolConfigBean config = new ProtocolConfigBean();
//        config.setDefault(true);
//        config.setLoadbalance("activeWeightLcn");
//        config.setName("motan");
//        return config;
//    }

    @Bean(name = "registry")
    public RegistryConfigBean registryConfig() {
        RegistryConfigBean config = new RegistryConfigBean();
        config.setRegProtocol("zookeeper");
        config.setAddress(environment.getProperty("zookeeper.address"));
//        config.setAddress("127.0.0.1:2181");
        return config;
    }

    @Bean(name = "basicRefererConfig")
    public BasicRefererConfigBean basicRefererConfigBean() {
        BasicRefererConfigBean config = new BasicRefererConfigBean();
        config.setProtocol("motan");
        config.setRegistry("registry");
        config.setRequestTimeout(500000);
        config.setCheck(false);
        config.setAccessLog(false);
        config.setShareChannel(true);
        config.setUsegz(true);
        config.setAsync(true);
        return config;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
