package com.zhk.service.system;

import com.github.pagehelper.PageInfo;
import com.zhk.domain.system.User;

import java.util.List;

public interface UserService {

    public List<User> findAll(String companyId);

    void save(User user);

    void update(User user);

    User findById(String id);

    void deleteById(String id);

    PageInfo<User> findPage(Integer page, Integer size, String companyId);

    void changeRole(String userid, String[] roleIds);

    User findByEmail(String email);
}
