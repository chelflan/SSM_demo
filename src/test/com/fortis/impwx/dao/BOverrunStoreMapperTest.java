package com.fortis.impwx.dao;

import com.fortis.impwx.model.BOverrunStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;


public class BOverrunStoreMapperTest {

    private ApplicationContext applicationContext;

    @Autowired
    private BOverrunStoreMapper bOverrunStoreMapper;

    @Before
    public void setUp() throws Exception {
        // 加载spring配置文件
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 导入需要测试的
        bOverrunStoreMapper = applicationContext.getBean(BOverrunStoreMapper.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getBOverrunStoreList() throws Exception {
        BOverrunStore bOverrunStore = new BOverrunStore();
        bOverrunStore.setStart(0);
        bOverrunStore.setLength(10);
        List<BOverrunStore> result = bOverrunStoreMapper.getBOverrunStoreList(bOverrunStore);
        System.out.println(result);

    }
}