package com.demo.config;

import com.demo.util.CustomSystemUtil;
import com.weibo.api.motan.config.springsupport.AnnotationBean;
import com.weibo.api.motan.config.springsupport.BasicServiceConfigBean;
import com.weibo.api.motan.config.springsupport.ProtocolConfigBean;
import com.weibo.api.motan.config.springsupport.RegistryConfigBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * <p>motan服务端配置</p>
 *
 * @author 张峰 zfvip_it@163.com
 * @createTime: 2017/1/16 13:33
 */
@Configuration
public class MotanServerConfiguration implements EnvironmentAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(MotanServerConfiguration.class);

    @Resource
    private Environment environment;

    @Bean
    public AnnotationBean motanAnnotationBean() {
        AnnotationBean motanAnnotationBean = new AnnotationBean();
        motanAnnotationBean.setPackage("com.demo.service");
        return motanAnnotationBean;
    }

    @Bean(name = "motan")
    public ProtocolConfigBean protocolConfig() {
        ProtocolConfigBean config = new ProtocolConfigBean();
        config.setDefault(true);
        config.setName("motan");
        config.setLoadbalance("activeWeightLcn");
        config.setMaxContentLength(5048576);
        return config;
    }

    @Bean(name = "registry")
    public RegistryConfigBean registryConfig() {
        RegistryConfigBean config = new RegistryConfigBean();
        config.setRegProtocol("zookeeper");
        config.setAddress(environment.getProperty("zookeeper.address"));
//        config.setConnectTimeout(200000);
        return config;
    }

    @Bean
    public BasicServiceConfigBean baseServiceConfig() {
        BasicServiceConfigBean config = new BasicServiceConfigBean();
        config.setExport("motan:8002");
        config.setRegistry("registry");
        config.setAccessLog(false);
        config.setShareChannel(true);
        config.setRequestTimeout(500000);
        config.setUsegz(true);
        config.setHost(getPost());
        config.setCheck(false);
        return config;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private String getPost() {
        //内网ip
        String intranetIp = CustomSystemUtil.INTRANET_IP;
        String[] ips = environment.getProperty("motan.server.ip", String[].class);
        if (ips != null && ips.length > 0) {
            for (String ip : ips) {
                String[] serverIp = ip.split(":");
                if (StringUtils.equals(intranetIp, serverIp[0])) {
                    return serverIp[1];
                }
            }
        }
        LOGGER.info("外网ip[{}]", intranetIp);
        return intranetIp;
    }

}
