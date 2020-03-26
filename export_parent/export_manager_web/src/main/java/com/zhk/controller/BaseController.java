package com.zhk.controller;

import com.zhk.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpSession session;

    protected String companyId;

    protected String companyName;

    protected String deptId;

    protected String deptName;

    protected String userId;


    @ModelAttribute
    public void init() {

        User user = (User) session.getAttribute("loginUser");

        if (user != null) {
            this.companyId = user.getCompanyId();
            this.companyName = user.getCompanyName();

            this.deptId = user.getDeptId();

            this.deptName = user.getDeptName();

            this.userId = user.getId();
        }

    }

}
