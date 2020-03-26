package com.zhk.controller.system;


import com.github.pagehelper.PageInfo;
import com.zhk.controller.BaseController;
import com.zhk.domain.system.Dept;
import com.zhk.service.system.DeptService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("system/dept")
public class DeptController extends BaseController {

    @Autowired
    private DeptService deptService;

    @RequestMapping(value = "list",name = "查询所有部门操作")
    public String findAll(@RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size" ,defaultValue = "5") Integer size){

        //List<Dept> list = deptService.findAll();
        PageInfo<Dept> pageInfo = deptService.findPage(page,size,companyId);
        request.setAttribute("page",pageInfo);

        return "system/dept/dept-list";
    }

    @RequestMapping(value = "toAdd",name = "跳转新增部门页面操作")
    @RequiresPermissions("新增部门")
    public String toAdd(){
        List<Dept> list = deptService.findAll(companyId);
        request.setAttribute("deptList",list);

        return "system/dept/dept-add";
    }

    @RequestMapping(value = "edit",name = "新增或修改部门内容操作")
    public String edit(Dept dept){
        if(StringUtils.isBlank(dept.getId())){
            dept.setId(UUID.randomUUID().toString().replace("-",""));
            dept.setCompanyId(companyId);
            dept.setCompanyName(companyName);
            deptService.save(dept);
        }else {
            if(StringUtils.isBlank(dept.getParent().getId())){
                dept.getParent().setId(null);
            }
            deptService.update(dept);
        }

        return "redirect:/system/dept/list.do";
    }

    @RequestMapping(value = "toUpdate",name = "跳转修改部门页面操作")
    public String toUpdate(String id){
        Dept dept = deptService.findById(id);
        List<Dept> list = deptService.findAll(companyId);

        request.setAttribute("dept",dept);
        request.setAttribute("deptList",list);

        return "system/dept/dept-add";
    }

    @RequestMapping(value = "delete",name = "删除部门操作")
    public String delete(String id){
        deptService.deleteById(id);

        return "redirect:/system//dept/list.do";
    }
}
