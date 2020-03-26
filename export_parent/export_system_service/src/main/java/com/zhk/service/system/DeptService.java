package com.zhk.service.system;

import com.github.pagehelper.PageInfo;
import com.zhk.domain.system.Dept;

import java.util.List;

public interface DeptService {

    public List<Dept> findAll(String companyId);

    void save(Dept dept);

    void update(Dept dept);

    Dept findById(String id);

    void deleteById(String id);

    PageInfo<Dept> findPage(Integer page, Integer size,String companyId);
}
