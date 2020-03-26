package com.zhk.controller;


import com.zhk.domain.system.Module;
import com.zhk.domain.system.User;
import com.zhk.service.system.ModuleService;
import com.zhk.service.system.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModuleService moduleService;

    @RequestMapping("/login")
    public String login(String email, String password, HttpServletRequest request, HttpSession session) {

        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            return "forward:/login.jsp";
        }

        //1、创建令牌

        password=new Md5Hash(password,email,1).toString();

        UsernamePasswordToken token = new UsernamePasswordToken(email, password);

        //2、获取主题

        Subject subject = SecurityUtils.getSubject();

        //3、开始认证

        try {
            subject.login(token);

            User user = (User) subject.getPrincipal();

            session.setAttribute("loginUser",user);

            List<Module> moduleList = moduleService.findModuleByUser(user);

            session.setAttribute("modules",moduleList);


        } catch (AuthenticationException e) {
            e.printStackTrace();
            request.setAttribute("error", "邮箱或密码有误");
            return "forward:/login.jsp";

        }


        return "home/main";

        /*User user = userService.findByEmail(email);
        if (user != null) {

            password=new Md5Hash(password, user.getEmail(), 1).toString();
            if (!user.getPassword().equals(password)) {
                request.setAttribute("error", "密码错误");
                return "forward:/login.jsp";
            } else {
                session.setAttribute("loginUser",user);

                List<Module> modules = moduleService.findModuleByUser(user);
                session.setAttribute("modules",modules);

                return "home/main";
            }
        } else {
            request.setAttribute("error", "邮箱有误");
            return "forward:/login.jsp";
        }*/

    }

    //退出
    @RequestMapping(value = "/logout", name = "用户登出")
    public String logout(HttpSession session) {
        SecurityUtils.getSubject().logout();   //登出

        //session.removeAttribute("loginUser");
        return "forward:login.jsp";
    }

    @RequestMapping("/home")
    public String home() {
        return "home/home";
    }
}
