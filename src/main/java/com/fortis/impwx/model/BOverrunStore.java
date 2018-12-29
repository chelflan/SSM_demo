package com.fortis.impwx.model;

public class BOverrunStore {
    private String mac;

    private Long storeId;

    private String storeName;

    private String storeAddr;

    private String limitTime;

    private Boolean isScan;

    private Boolean isVip;

    private Boolean isException;

    private Boolean useChainDoctor;

    private Integer start;

    private Integer length;

    public Boolean getUseChainDoctor() {
        return useChainDoctor;
    }

    public void setUseChainDoctor(Boolean useChainDoctor) {
        this.useChainDoctor = useChainDoctor;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddr() {
        return storeAddr;
    }

    public void setStoreAddr(String storeAddr) {
        this.storeAddr = storeAddr;
    }

    public String getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(String limitTime) {
        this.limitTime = limitTime;
    }

    public Boolean getIsScan() {
        return isScan;
    }

    public void setIsScan(Boolean isScan) {
        this.isScan = isScan;
    }

    public Boolean getIsVip() {
        return isVip;
    }

    public void setIsVip(Boolean isVip) {
        this.isVip = isVip;
    }

    public Boolean getIsException() {
        return isException;
    }

    public void setIsException(Boolean isException) {
        this.isException = isException;
    }
}