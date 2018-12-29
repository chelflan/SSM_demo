package com.fortis.impwx.service.impl;

import com.fortis.impwx.controller.BOverrunStoreController;
import com.fortis.impwx.dao.BOverrunStoreMapper;
import com.fortis.impwx.datasource.DataSourceContextHolder;
import com.fortis.impwx.datasource.DynamicDataSourceAnnotation;
import com.fortis.impwx.model.BOverrunStore;
import com.fortis.impwx.service.BOverrunStoreService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能描述
 *
 * @author zq
 * @description 接口实现层
 * @date 2018/12/25
 */
@Service
public class BOverrunStoreServiceImpl implements BOverrunStoreService {
    private static final Logger LOGGER = Logger.getLogger(BOverrunStoreServiceImpl.class);

    @Autowired
    private BOverrunStoreMapper bOverrunStoreMapper;

    @DynamicDataSourceAnnotation(dataSource = DataSourceContextHolder.DATA_BUSIDB)
    public List<BOverrunStore> getBosModelList(BOverrunStore bOverrunStore) {
        return bOverrunStoreMapper.getBOverrunStoreList(bOverrunStore);
    }
    @DynamicDataSourceAnnotation(dataSource = DataSourceContextHolder.DATA_BUSIDB)
    public long getBosModelTotal(BOverrunStore bOverrunStore) {
        return bOverrunStoreMapper.getBOverrunStoreTotal(bOverrunStore);
    }
    @DynamicDataSourceAnnotation(dataSource = DataSourceContextHolder.DATA_BUSIDB)
    public int deleteByMac(BOverrunStore bOverrunStore) {
        return bOverrunStoreMapper.deleteByMac(bOverrunStore);
    }
    @DynamicDataSourceAnnotation(dataSource = DataSourceContextHolder.DATA_BUSIDB)
    public BOverrunStore queryForUpdate(BOverrunStore bOverrunStore) {
        String mac = bOverrunStore.getMac();
        return bOverrunStoreMapper.bOverrunStore(mac);
    }
    @DynamicDataSourceAnnotation(dataSource = DataSourceContextHolder.DATA_BUSIDB)
    public int update(BOverrunStore bOverrunStore) {
        return bOverrunStoreMapper.update(bOverrunStore);
    }

}
