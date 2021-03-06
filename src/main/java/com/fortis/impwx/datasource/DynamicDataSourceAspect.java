package com.fortis.impwx.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Order(1)
public class DynamicDataSourceAspect {

    @Before("@annotation(com.fortis.impwx.datasource.DynamicDataSourceAnnotation)") //前置通知
    public void testBefore(JoinPoint point){

        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if(method.isAnnotationPresent(DynamicDataSourceAnnotation.class)) {

            DynamicDataSourceAnnotation dataSource = method.getAnnotation(DynamicDataSourceAnnotation.class);
            DataSourceContextHolder.setDbType(dataSource.dataSource());
        } else {
            DataSourceContextHolder.setDbType(DataSourceContextHolder.DATA_BUSIDB);

        }

//        //获得当前访问的class
//        Class<?> className = point.getTarget().getClass();
//        DynamicDataSourceAnnotation dataSourceAnnotation = className.getAnnotation(DynamicDataSourceAnnotation.class);
//        if (dataSourceAnnotation != null ) {
//            //获得访问的方法名
//            String methodName = point.getSignature().getName();
//            //得到方法的参数的类型
//            Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
//            String dataSource = DataSourceContextHolder.DATA_BUSIDB;
//            try {
//                Method method = className.getMethod(methodName, argClass);
//                if (method.isAnnotationPresent(DynamicDataSourceAnnotation.class)) {
//                    DynamicDataSourceAnnotation annotation = method.getAnnotation(DynamicDataSourceAnnotation.class);
//                    dataSource = annotation.dataSource();
//                    System.err.println("DataSource Aop ====> "+dataSource);
//                }
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            DataSourceContextHolder.setDbType(dataSource);
//        }

    }

    @After("@annotation(com.fortis.impwx.datasource.DynamicDataSourceAnnotation)")   //后置通知
    public void testAfter(JoinPoint point){
        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();
        DynamicDataSourceAnnotation dataSourceAnnotation = className.getAnnotation(DynamicDataSourceAnnotation.class);
        if (dataSourceAnnotation != null ) {
            //获得访问的方法名
            String methodName = point.getSignature().getName();
            //得到方法的参数的类型
            Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
            String dataSource = DataSourceContextHolder.DATA_BUSIDB;
            try {
                Method method = className.getMethod(methodName, argClass);
                if (method.isAnnotationPresent(DynamicDataSourceAnnotation.class)) {
                    DynamicDataSourceAnnotation annotation = method.getAnnotation(DynamicDataSourceAnnotation.class);
                    dataSource = annotation.dataSource();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(dataSource != null && !DataSourceContextHolder.DATA_BUSIDB.equals(dataSource)) DataSourceContextHolder.clearDbType();
        }
    }

}
