package com.fortis.impwx.dao;

import com.fortis.impwx.model.BOverrunStore;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BOverrunStoreMapper {

    /**
     * 查询列表
     * @param bOverrunStore
     * @return
     */
    List<BOverrunStore> getBOverrunStoreList(BOverrunStore bOverrunStore);

    /**
     * 总条数
     * @param bOverrunStore
     * @return
     */
    Long getBOverrunStoreTotal(BOverrunStore bOverrunStore);

    /**
     * 根据mac删除
     * @param bOverrunStore
     * @return
     */
    int deleteByMac(BOverrunStore bOverrunStore);

    /**
     * 根据mac查询的bOverrunStore
     * @param mac
     * @return
     */
    BOverrunStore bOverrunStore(@Param("mac") String mac);

    /**
     * 更新
     * @param bOverrunStore
     * @return
     */
    int update(BOverrunStore bOverrunStore);
}