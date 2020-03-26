package com.zhk.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhk.dao.system.DeptDao;
import com.zhk.domain.system.Dept;
import com.zhk.service.system.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptDao deptDao;

    @Override
    public List<Dept> findAll(String companyId) {
        return deptDao.findAll(companyId);
    }

    @Override
    public void save(Dept dept) {
        deptDao.save(dept);

    }

    @Override
    public void update(Dept dept) {
        deptDao.update(dept);
    }

    @Override
    public Dept findById(String id) {
        return deptDao.findById(id);
    }

    @Override
    public void deleteById(String id) {
        deptDao.delete(id);


    }

    @Override
    public PageInfo findPage(Integer page, Integer size, String companyId) {

        PageHelper.startPage(page,size);
        List<Dept> list = deptDao.findAll(companyId);

        return new PageInfo(list,5);
    }
}
