package com.fortis.impwx.datasource;

public class DataSourceContextHolder {

    /**
     * TODO:配置数据源别名，有多个就设置多个
     */
    public static final String DATA_BUSIDB = "busidbDataSource";

    // 线程本地环境
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    // 设置数据源类型
    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }

    // 获取数据源类型
    public static String getDbType() {
        return (contextHolder.get());
    }
    // 清除数据源类型
    public static void clearDbType() {
        contextHolder.remove();
    }


}
