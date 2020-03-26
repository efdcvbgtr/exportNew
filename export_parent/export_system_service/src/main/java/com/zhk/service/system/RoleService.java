package com.zhk.service.system;

import com.github.pagehelper.PageInfo;
import com.zhk.domain.system.Role;

import java.util.List;

public interface RoleService {

    public List<Role> findAll(String companyId);

    void save(Role role);

    void update(Role role);

    Role findById(String id);

    void deleteById(String id);

    PageInfo<Role> findPage(Integer page, Integer size, String companyId);

    void updateRoleModule(String roleid, String moduleIds);

    List findByUserId(String id);
}
