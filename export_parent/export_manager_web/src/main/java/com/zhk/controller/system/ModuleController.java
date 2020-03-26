package com.zhk.controller.system;


import com.github.pagehelper.PageInfo;
import com.zhk.controller.BaseController;
import com.zhk.domain.system.Module;
import com.zhk.service.system.ModuleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/system/module")
public class ModuleController extends BaseController {

    @Autowired
    private ModuleService moduleService;

    @RequestMapping(value = "list",name = "查询所有菜单项操作")
    public String findAll(@RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size" ,defaultValue = "10") Integer size){

        PageInfo<Module> pageInfo = moduleService.findPage(page,size);
        request.setAttribute("page",pageInfo);

        return "system/module/module-list";
    }

    @RequestMapping(value = "toAdd",name = "跳转至新增菜单项操作")
    public String toAdd(){

        List<Module> modules = moduleService.findAll();
        request.setAttribute("menus",modules);

        return "system/module/module-add";
    }

    @RequestMapping(value = "edit",name = "新增或修改菜单项操作")
    public String edit(Module module){
        if(StringUtils.isBlank(module.getId())){
            module.setId(UUID.randomUUID().toString());
            moduleService.save(module);
        }else {
            moduleService.update(module);
        }


        return "redirect:/system/module/list.do";
    }

    @RequestMapping(value = "toUpdate",name = "跳转至修改菜单项操作")
    public String toUpdate(String id){
        Module module = moduleService.findById(id);
        request.setAttribute("module",module);

        List<Module> modules = moduleService.findAll();
        request.setAttribute("menus",modules);

        return "system/module/module-add";
    }

    @RequestMapping(value = "delete",name = "删除菜单项操作")
    public String delete(String id){
        moduleService.deleteById(id);

        return "redirect:/system/module/list.do";
    }
}
