package com.demo.service.impl;


import com.demo.domain.SysDemo;
import com.demo.service.SysDemoSerice;
import com.demo.writer.SysDemoWriterMapper;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>类说明</p>
 *
 * @author 张峰 zfvip_it@163.com
 * @createTime: 2017/11/13 12:59
 */
@MotanService
public class SysDemoServiceImpl implements SysDemoSerice {

    @Resource
    private SysDemoWriterMapper sysDemoWriterMapper;

    @Transactional
    public void save() {
        SysDemo sysDemo = new SysDemo();
        sysDemo.setName("测试2");
        sysDemo.setCode(new BigDecimal(String.valueOf(Math.random())).intValue() + "");
        sysDemoWriterMapper.insertSelective(sysDemo);
    }

}
