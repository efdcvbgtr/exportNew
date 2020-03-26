package com.zhk.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhk.dao.system.UserDao;
import com.zhk.domain.system.User;
import com.zhk.service.system.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll(String companyId) {
        return userDao.findAll(companyId);
    }

    @Override
    public void save(User user) {

        String password = user.getPassword();
        password = new Md5Hash(password, user.getEmail(), 1).toString();
        user.setPassword(password);


        userDao.save(user);

    }

    @Override
    public void update(User user) {

        String password = user.getPassword();
        password = new Md5Hash(password, user.getEmail(), 1).toString();
        user.setPassword(password);

        userDao.update(user);
    }

    @Override
    public User findById(String id) {
        return userDao.findById(id);
    }

    @Override
    public void deleteById(String id) {
        userDao.deleteById(id);


    }

    @Override
    public PageInfo findPage(Integer page, Integer size, String companyId) {

        PageHelper.startPage(page,size);
        List<User> list = userDao.findAll(companyId);

        return new PageInfo(list,5);
    }

    @Override
    public void changeRole(String userid, String[] roleIds) {

        userDao.deleterRolesByUserId(userid);

        for (String roleId : roleIds) {
            userDao.changeRole(userid,roleId);
        }


    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
