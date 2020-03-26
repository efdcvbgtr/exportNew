package com.zhk.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhk.dao.system.ModuleDao;
import com.zhk.dao.system.UserDao;
import com.zhk.domain.system.Module;
import com.zhk.domain.system.User;
import com.zhk.service.system.ModuleService;
import com.zhk.service.system.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;
    @Override
    public List<Module> findAll() {
        return moduleDao.findAll();
    }


    @Override
    public void save(Module module) {
        moduleDao.save(module);
    }

    @Override
    public Module findById(String id) {
        return   moduleDao.findById(id);
    }

    @Override
    public void update(Module module) {
        moduleDao.update(module);
    }

    @Override
    public void deleteById(String id) {
        moduleDao.deleteById(id);
    }

    public PageInfo findPage(Integer page, int size) {
        PageHelper.startPage(page,size);
        List<Module> list = moduleDao.findAll();
        return new PageInfo(list,5);
    }

    @Override
    public List<Module> findByRoleId(String roleid) {
        return moduleDao.findByRoleId(roleid);
    }

    @Override
    public void deleteByRoleId(String roleid) {
        moduleDao.deleteByRoleId(roleid);
    }

    @Override
    public List<Module> findModuleByUser(User user) {

        Integer degree = user.getDegree();
        List<Module> modules = new ArrayList<>();

        if (degree==0) {
            modules= moduleDao.findByBelong("0");
        }else if(degree==1){
            modules= moduleDao.findByBelong("1");
        }else {
            modules= moduleDao.findByUserId(user.getId());
        }

        return modules;



    }


}
