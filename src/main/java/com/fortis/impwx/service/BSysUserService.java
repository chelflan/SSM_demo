package com.fortis.impwx.service;

import com.fortis.impwx.model.BSysUser;

public interface BSysUserService {
    /**
     * 登录验证
     * @param account
     * @return
     */
    BSysUser findByUserAcct(String account);
}
