package com.zhk.dao.system;

import com.zhk.domain.system.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao {

    public List<Role> findAll(String companyId);

    void save(Role role);

    void update(Role role);

    Role findById(String id);

    void deleteById(String id);

    void updateRoleModule(@Param("roleid") String roleid, @Param("mId") String mId);

    List findByUserId(String id);
}
