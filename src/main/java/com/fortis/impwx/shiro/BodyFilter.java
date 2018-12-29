package com.fortis.impwx.shiro;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *
 */
public class BodyFilter implements Filter {

    private static final Logger logger = Logger.getLogger(BodyFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(new MyHttpServletRequestWrapper((HttpServletRequest) request), response);
        } catch (Exception e) {
            logger.debug("过滤器异常：", e.getCause());
        }
    }

    public void destroy() {

    }
}
