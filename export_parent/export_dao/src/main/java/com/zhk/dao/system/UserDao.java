package com.zhk.dao.system;

import com.zhk.domain.system.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    public List<User> findAll(String companyId);

    void save(User user);

    void update(User user);

    User findById(String id);

    void deleteById(String id);

    void changeRole(@Param("userid") String userid, @Param("roleId") String roleId);

    void deleterRolesByUserId(String userid);

    User findByEmail(String email);
}
