package com.fortis.impwx.service;


import com.fortis.impwx.model.BOverrunStore;

import java.util.List;

public interface BOverrunStoreService {

    List<BOverrunStore> getBosModelList(BOverrunStore bOverrunStore);

    long getBosModelTotal(BOverrunStore bOverrunStore);

    int deleteByMac(BOverrunStore bOverrunStore);

    BOverrunStore queryForUpdate(BOverrunStore bOverrunStore);

    int update(BOverrunStore bOverrunStore);
}
