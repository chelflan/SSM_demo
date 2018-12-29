package com.fortis.impwx.service.impl;

import com.fortis.impwx.dao.BSysUserMapper;
import com.fortis.impwx.datasource.DataSourceContextHolder;
import com.fortis.impwx.datasource.DynamicDataSourceAnnotation;
import com.fortis.impwx.model.BSysUser;
import com.fortis.impwx.service.BSysUserService;
import com.fortis.impwx.utils.Log4jUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能描述
 *
 * @author zq
 * @description 登录实现
 * @date 2018/12/27
 */
@Service
public class BSysUserServiceImpl implements BSysUserService {
    private static final Logger LOGGER = Logger.getLogger(BSysUserServiceImpl.class);

    @Autowired
    private BSysUserMapper bSysUserMapper;
    @DynamicDataSourceAnnotation(dataSource = DataSourceContextHolder.DATA_BUSIDB)
    public BSysUser findByUserAcct(String account) {
        BSysUser bSysUser = null;
        try{
            bSysUser = bSysUserMapper.findByUserAcct(account);
        }catch (Exception e){
            LOGGER.error("findByUserAcct请求错误:"+ Log4jUtils.getTrace(e));
        }
        return bSysUser;
    }
}
