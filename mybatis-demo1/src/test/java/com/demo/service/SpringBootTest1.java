package com.demo.service;

import com.Application;
import com.demo.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <p>类说明</p>
 *
 * @author 张峰 zfvip_it@163.com
 * @createTime: 2017/1/12 14:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Application.class)// 指定spring-boot的启动类
public class SpringBootTest1 {

    private static Logger LOGGER = LoggerFactory.getLogger(SpringBootTest1.class);


    private ExecutorService pool;

    @Resource
    private SysTestSerice sysTestSerice;


    @Test
    public void test7() throws Exception {

        Long startTime = System.currentTimeMillis();
        List<Future> rowResult = new CopyOnWriteArrayList();
//        pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 50);
        pool = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            rowResult.add(pool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        sysTestSerice.test();
//                        sysTestSerice.test1();
//                        sysTestSerice.test2();
                        System.out.println("执行次数" + finalI);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }));
        }
        //等待处理结果
        for (Future f : rowResult) {
            f.get();
        }
        //启动一次顺序关闭，执行以前提交的任务，但不接受新任务。如果已经关闭，则调用没有其他作用
        pool.shutdown();

        LOGGER.info("测试耗时[{}]", DateUtil.dateDiff(startTime, System.currentTimeMillis()));
    }
}
