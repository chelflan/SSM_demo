package com.fortis.impwx.shiro;

import com.fortis.impwx.model.BSysUser;
import com.fortis.impwx.service.BSysUserService;
import com.fortis.impwx.utils.Global;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private BSysUserService userService;


    /***
     * 提供用户信息返回的权限信息
     */
    //根据自己系统规则的需要编写获取授权信息，这里为了快速入门只获取了用户对应角色的资源url信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String userAcct = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        BSysUser user = userService.findByUserAcct(userAcct);
        // 将角色名称提供给info
        Set<String> roles = new HashSet<String>();
        if(user.getRoleId() != null) {
            roles.add(user.getRoleId().toString());
            authorizationInfo.setRoles(roles);
        }
        // 根据用户名查询当前用户权限
//        Set<BSysMenuEntity> permissions = userService.findPermissions(userAcct);
//
//        Set<String> permissionNames = new HashSet<String>();
//        for (BSysMenuEntity menuEntity : permissions) {
//            if(menuEntity.getMenuAction() != null) {
//                permissionNames.add(menuEntity.getMenuAction());
//            }
//        }
        // 将权限名称提供给info
//        authorizationInfo.setStringPermissions(permissionNames);

        return authorizationInfo;
    }

    /***
     * 提供账户信息返回认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {

        String userAcct = (String) token.getPrincipal();
        BSysUser userModel = userService.findByUserAcct(userAcct);
        if (userModel == null) {
            // 用户名不存在抛出异常
            throw new UnknownAccountException();
        }
        if(userModel.getUserStatus() != null && Global.STATUS_USE.equals(userModel.getUserStatus())) {
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userModel, userModel.getPasswd(), getName());

            // 把当前用户放入到session中
            Session session = SecurityUtils.getSubject().getSession(true);
            session.setAttribute(Global.CURRENT_USER, userModel);

            return authenticationInfo;
        } else {
            //用户被禁用
            throw new LockedAccountException();
        }
    }
}
