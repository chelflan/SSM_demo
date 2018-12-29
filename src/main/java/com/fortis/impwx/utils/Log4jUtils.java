package com.fortis.impwx.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 功能描述
 *
 * @author zq
 * @description 日志工具类
 * @date 2018/12/26
 */
public class Log4jUtils {

    public static String getTrace(Throwable t) {
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
    }
}
