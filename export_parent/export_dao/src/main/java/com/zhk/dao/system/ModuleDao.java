package com.zhk.dao.system;

import com.zhk.domain.system.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModuleDao {

    public List<Module> findAll();

    void save(Module module);

    void update(Module module);

    Module findById(String id);

    void deleteById(String id);

    List<Module> findPage(@Param("begin") Integer begin, @Param("size") Integer size);

    List<Module> findByRoleId(String roleId);

    void deleteByRoleId(String roleid);

    List<Module> findByBelong(String i);

    List<Module> findByUserId(String id);

}
