package com.zhk.controller.system;


import com.github.pagehelper.PageInfo;
import com.zhk.controller.BaseController;
import com.zhk.domain.system.Dept;
import com.zhk.domain.system.Role;
import com.zhk.domain.system.User;
import com.zhk.service.system.DeptService;
import com.zhk.service.system.RoleService;
import com.zhk.service.system.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("system/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "list",name = "查询所有用户操作")
    public String findAll(@RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size" ,defaultValue = "10") Integer size){

        //List<User> list = userService.findAll();
        PageInfo<User> pageInfo = userService.findPage(page,size,companyId);
        request.setAttribute("page",pageInfo);

        return "system/user/user-list";
    }

    @RequestMapping(value = "toAdd",name = "跳转新增用户界面操作")
    public String toAdd(){
        List<Dept> list = deptService.findAll(companyId);
        request.setAttribute("deptList",list);

        return "system/user/user-add";
    }

    @RequestMapping(value = "edit",name = "新增或修改用户操作")
    public String edit(User user){
        if(StringUtils.isBlank(user.getId())){
            user.setId(UUID.randomUUID().toString().replace("-",""));
            user.setCompanyId(companyId);
            user.setCompanyName(companyName);

            user.setCreateBy(userId);
            user.setCreateDept(deptId);
            user.setCreateTime(new Date());

            userService.save(user);
        }else {
            System.out.println(user.getOrderNo());

            user.setUpdateBy(userId);
            user.setUpdateTime(new Date());

            userService.update(user);
        }

        return "redirect:/system/user/list.do";
    }

    @RequestMapping(value = "toUpdate",name = "跳转修改用户页面操作")
    public String toUpdate(String id){
        User user = userService.findById(id);
        List<Dept> list = deptService.findAll(companyId);

        request.setAttribute("user",user);
        request.setAttribute("deptList",list);

        return "system/user/user-add";
    }

    @RequestMapping(value = "delete",name = "删除用户操作")
    public String delete(String id){
        userService.deleteById(id);

        return "redirect:/system/user/list.do";
    }

    @RequestMapping(value = "roleList",name = "查询用户与角色撇配操作")
    public String roleList(String id){
        User user = userService.findById(id);
        request.setAttribute("user",user);

        List<Role> roles = roleService.findAll(companyId);
        request.setAttribute("roleList",roles);

        List<String> userRoles = roleService.findByUserId(id);
        request.setAttribute("userRoleStr",userRoles);

        return "system/user/user-role";
    }


    @RequestMapping(value = "changeRole",name = "修改用户与角色匹配操作")
    public String changeRole(String userid,String[] roleIds){

        userService.changeRole(userid,roleIds);


        return "redirect:/system/user/list.do";
    }





}
