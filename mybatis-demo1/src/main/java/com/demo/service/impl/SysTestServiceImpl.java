package com.demo.service.impl;


import com.codingapi.tx.annotation.TxTransaction;
import com.demo.domain.SysTest;
import com.demo.service.SysDemoSerice;
import com.demo.service.SysExampleSerice;
import com.demo.service.SysTestSerice;
import com.demo.writer.SysTestWriterMapper;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
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
public class SysTestServiceImpl implements SysTestSerice {

    @Resource
    private SysTestWriterMapper sysTestWriterMapper;

    @MotanReferer(basicReferer = "basicRefererConfig")
    private SysDemoSerice sysDemoSerice;

    @MotanReferer(basicReferer = "basicRefererConfig")
    private SysExampleSerice sysExampleSerice;


    public void save() {
        SysTest sysTest = new SysTest();
        sysTest.setName("测试1");
        sysTest.setCode(new BigDecimal(String.valueOf(Math.random())).intValue() + "");
        sysTestWriterMapper.insertSelective(sysTest);
    }

    /**
     * 没有错误
     */
    @TxTransaction
    @Transactional
    public void test() {
        save();
        save();
        save();
        sysExampleSerice.save();
        sysExampleSerice.update();
        sysExampleSerice.save();
        sysExampleSerice.update();
    }

    /**
     * sysExampleSerice 出错
     */
    @TxTransaction
    @Transactional
    public void test1() {
        save();
        sysDemoSerice.save();
        sysDemoSerice.save();
        sysDemoSerice.save();
        sysDemoSerice.save();
        sysDemoSerice.save();
        sysExampleSerice.update();
        sysExampleSerice.save1();
        sysExampleSerice.update();
        sysExampleSerice.save1();
    }

    /**
     * sysTestSerice 出错
     */
    @TxTransaction
    @Transactional
    public void test2() {
        save();
        sysDemoSerice.save();
        sysExampleSerice.save();
        sysExampleSerice.update1();
        int a = 100 / 0;
    }
}
