package com.zhk.controller.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;



public class MyPermsFilter extends AuthorizationFilter {


    public MyPermsFilter() {

    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = this.getSubject(servletRequest, servletResponse);

        String[] perms = (String[]) ((String[]) o);

        boolean isPermitted = true;

        if (perms != null || perms.length > 0) {
            for (String perm : perms) {
                if (subject.isPermitted(perm)) {
                    return isPermitted;
                }
            }
            return false;
        }

        return true;
    }
}
