package com.zhk.controller.system;


import com.github.pagehelper.PageInfo;
import com.zhk.controller.BaseController;
import com.zhk.domain.system.Dept;
import com.zhk.domain.system.Module;
import com.zhk.domain.system.Role;
import com.zhk.service.system.DeptService;
import com.zhk.service.system.ModuleService;
import com.zhk.service.system.RoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("system/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModuleService moduleService;

    @RequestMapping(value = "list",name = "查询所有角色操作")
    public String findAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "10") Integer size) {

        PageInfo<Role> pageInfo = roleService.findPage(page, size, companyId);
        request.setAttribute("page", pageInfo);

        return "system/role/role-list";
    }

    @RequestMapping(value = "toAdd",name = "跳转新增角色操作")
    public String toAdd() {

        return "system/role/role-add";
    }

    @RequestMapping(value = "edit",name = "新增或修改角色操作")
    public String edit(Role role) {
        if (StringUtils.isBlank(role.getId())) {
            role.setId(UUID.randomUUID().toString().replace("-", ""));
            role.setCompanyId(companyId);
            role.setCompanyName(companyName);

            role.setCreateBy(userId);
            role.setCreateDept(deptId);
            role.setCreateTime(new Date());

            roleService.save(role);
        } else {
            System.out.println(role.getOrderNo());

            role.setUpdateBy(userId);
            role.setUpdateTime(new Date());

            roleService.update(role);
        }

        return "redirect:/system/role/list.do";
    }

    @RequestMapping(value = "toUpdate",name = "跳转修改角色操作")
    public String toUpdate(String id) {
        Role role = roleService.findById(id);

        request.setAttribute("role", role);

        return "system/role/role-add";
    }

    @RequestMapping(value = "delete",name = "删除角色操作")
    public String deleteById(String id) {
        roleService.deleteById(id);

        return "redirect:/system/role/list.do";
    }

    @RequestMapping(value = "roleModule",name = "查询角色与菜单项匹配操作")
    public String roleModule(String roleid) {
        Role role = roleService.findById(roleid);

        request.setAttribute("role", role);

        return "system/role/role-module";
    }

    @RequestMapping(value = "getZtreeNodes",name = "获取ztree操作")
    @ResponseBody  //转json
    public List<Map> getZtreeNodes(String roleid) {
        List<Module> modules = moduleService.findAll();

        List<Module> roleModuleList = moduleService.findByRoleId(roleid);

        List<Map> moduleMapList = new ArrayList<>();
        for (Module module : modules) {
            Map map = new HashMap<>();
            map.put("id", module.getId());
            map.put("pId", module.getParentId());
            map.put("name", module.getName());
            if (roleModuleList.contains(module)) {
                map.put("checked", true);
            }

            moduleMapList.add(map);

        }

        Role role = roleService.findById(roleid);

        request.setAttribute("role", role);

        return moduleMapList;
    }

    @RequestMapping(value = "updateRoleModule",name = "修改角色与菜单项匹配操作")
    public String updateRoleModule(String roleid, String moduleIds) {

        moduleService.deleteByRoleId(roleid);

        roleService.updateRoleModule(roleid, moduleIds);

        return "redirect:/system/role/list.do";
    }

}
