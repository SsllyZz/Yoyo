package com.yoyo.realm;

import com.yoyo.entity.Admins;
import com.yoyo.service.IAdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author：刘松
 * @Date ：2022/9/28 16:15
 */
//查询数据库，为shiro提供我们数据库数据的
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private IAdminService iAdminService;

    /**
     * 角色权限校验
     * 可以实现给登录过的用户赋予角色和权限的
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        return null;
    }

    /**
     * 认证校验
     * 将页面传输过来的数据，和数据库数据进行对比，判断是否能登录
     * @param authenticationToken  其实就是UsernamePasswordToken，里面存储的就是页面传输的用户名和密码
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = new String(token.getPassword());

        Admins admins = iAdminService.getByUsername(username);
        if (admins == null){
            return null;
        }
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        return new SimpleAuthenticationInfo(admins,admins.getPassword(),this.getName());
    }
}
