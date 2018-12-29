package com.fortis.impwx.dao;

import com.fortis.impwx.model.BSysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BSysUserMapper {

    BSysUser findByUserAcct(@Param("userAcct") String userAcct);

}