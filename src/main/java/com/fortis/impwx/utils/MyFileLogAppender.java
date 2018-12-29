package com.fortis.impwx.utils;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;

/**
 * 功能描述
 *
 * @author zq
 * @description 日志记录
 * @date 2018/12/26
 */
public class MyFileLogAppender extends DailyRollingFileAppender {

    @Override
    public boolean isAsSevereAsThreshold(Priority priority) {

        //return super.isAsSevereAsThreshold(priority)

        //只判断是否相等，而不判断优先级
        return this.getThreshold() == priority;
    }

}
