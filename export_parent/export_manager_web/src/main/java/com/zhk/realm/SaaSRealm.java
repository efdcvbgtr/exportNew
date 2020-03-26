package com.zhk.realm;

import com.zhk.domain.system.Module;
import com.zhk.domain.system.User;
import com.zhk.service.system.ModuleService;
import com.zhk.service.system.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SaaSRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private ModuleService moduleService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principalCollection) {

        AuthorizationInfo info = new SimpleAuthorizationInfo();

        User user = (User) principalCollection.getPrimaryPrincipal();

        List<Module> modules = moduleService.findModuleByUser(user);

        for (Module module : modules) {
            ((SimpleAuthorizationInfo) info).addStringPermission(module.getName());
        }



        return info;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String email = token.getUsername();
        String password = new String(token.getPassword());

        User user = userService.findByEmail(email);
        if (user!=null) {
            if (!password.equals(user.getPassword())) {
                return null;
            }else {

                return new SimpleAuthenticationInfo(user,password,getName());

            }
        }

        return null;
    }
}
