package com.zhk.service.system;

import com.github.pagehelper.PageInfo;
import com.zhk.domain.system.Module;
import com.zhk.domain.system.User;

import java.util.List;

public interface ModuleService {

    public List<Module> findAll();

    void save(Module module);

    Module findById(String id);

    void update(Module module);

    void deleteById(String id);

    PageInfo findPage(Integer page, int size);

    List<Module> findByRoleId(String roleid);

    void deleteByRoleId(String roleid);

    List<Module> findModuleByUser(User user);
}
