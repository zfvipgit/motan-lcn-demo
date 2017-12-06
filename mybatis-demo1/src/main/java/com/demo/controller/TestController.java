package com.demo.controller;

import com.demo.service.SysTestSerice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * <p>类说明</p>
 *
 * @author 张峰 zfvip_it@163.com
 * @createTime: 2017/11/17 14:32
 */
@Controller
public class TestController {

    @Resource
    private SysTestSerice sysTestSerice;

    @GetMapping("test")
    @ResponseBody
    public void test() {
        sysTestSerice.test();
    }

    @GetMapping("test1")
    @ResponseBody
    public void test1() {
        sysTestSerice.test1();
    }

    @GetMapping("test2")
    @ResponseBody
    public void test2() {
        sysTestSerice.test2();
    }
}
