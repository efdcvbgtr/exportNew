package com.zhk.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhk.dao.system.RoleDao;
import com.zhk.domain.system.Role;
import com.zhk.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> findAll(String companyId) {
        return roleDao.findAll(companyId);
    }

    @Override
    public void save(Role role) {

        roleDao.save(role);

    }

    @Override
    public void update(Role role) {

        roleDao.update(role);
    }

    @Override
    public Role findById(String id) {
        return roleDao.findById(id);
    }

    @Override
    public void deleteById(String id) {
        roleDao.deleteById(id);


    }

    @Override
    public PageInfo findPage(Integer page, Integer size, String companyId) {

        PageHelper.startPage(page,size);
        List<Role> list = roleDao.findAll(companyId);

        return new PageInfo(list,5);
    }

    @Override
    public void updateRoleModule(String roleid, String moduleIds) {
        String[] mIds = moduleIds.split(",");

        for (String mId : mIds) {
            roleDao.updateRoleModule(roleid,mId);
        }



    }

    @Override
    public List findByUserId(String id) {
        return roleDao.findByUserId(id);
    }

}
