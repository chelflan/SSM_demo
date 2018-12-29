package com.fortis.impwx.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 *
 */
public class ShiroHandleSessionListener implements SessionListener {

    //会话创建
    public void onStart(Session session) {
    }

    //会话过期：
    public void onStop(Session session) {
    }

    //会话停止
    public void onExpiration(Session session) {
    }
}
