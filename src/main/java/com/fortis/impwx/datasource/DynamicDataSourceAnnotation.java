package com.fortis.impwx.datasource;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DynamicDataSourceAnnotation {
    //dataSource 自定义注解的参数
    String dataSource() default DataSourceContextHolder.DATA_BUSIDB;

}
