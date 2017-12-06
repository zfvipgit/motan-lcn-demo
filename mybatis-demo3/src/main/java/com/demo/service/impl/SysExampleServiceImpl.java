package com.demo.service.impl;


import com.demo.domain.SysExample;
import com.demo.service.SysExampleSerice;
import com.demo.writer.SysExampleWriterMapper;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>类说明</p>
 *
 * @author 张峰 zfvip_it@163.com
 * @createTime: 2017/11/13 12:59
 */
@MotanService
public class SysExampleServiceImpl implements SysExampleSerice {

    @Resource
    private SysExampleWriterMapper sysExampleWriterMapper;

    @Transactional
    public void save() {
        SysExample sysExample = new SysExample();
        sysExample.setName("测试3");
        sysExample.setCode(new BigDecimal(String.valueOf(Math.random())).intValue() + "");
        sysExampleWriterMapper.insertSelective(sysExample);
    }

    @Transactional
    public void update() {
        SysExample sysExample = new SysExample();
        sysExample.setName("update");
        Example example = new Example(SysExample.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("code = '0'");
        sysExampleWriterMapper.updateByExample(sysExample, example);
    }

    @Transactional
    public void save1() {
        SysExample sysExample = new SysExample();
        sysExample.setName("测试3");
        sysExample.setCode(new BigDecimal(String.valueOf(Math.random())).intValue() + "");
        sysExampleWriterMapper.insertSelective(sysExample);
        int a = 100 / 0;
    }


    @Transactional
    public void update1() {
        SysExample sysExample = new SysExample();
        sysExample.setName("update1");
        Example example = new Example(SysExample.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("code = '0'");
        sysExampleWriterMapper.updateByExample(sysExample, example);
    }


}
