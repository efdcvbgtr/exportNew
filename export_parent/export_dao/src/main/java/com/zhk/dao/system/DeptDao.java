package com.zhk.dao.system;

import com.zhk.domain.system.Dept;

import java.util.List;

public interface DeptDao {

    public List<Dept> findAll(String companyId);

    void save(Dept dept);

    void update(Dept dept);

    Dept findById(String id);

    void delete(String id);

}
