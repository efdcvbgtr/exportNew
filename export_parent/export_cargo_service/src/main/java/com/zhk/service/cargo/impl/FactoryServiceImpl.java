package com.zhk.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhk.dao.cargo.FactoryDao;
import com.zhk.dao.cargo.ExtCproductDao;
import com.zhk.domain.cargo.Factory;
import com.zhk.domain.cargo.FactoryExample;
import com.zhk.domain.cargo.ExtCproduct;
import com.zhk.domain.cargo.ExtCproductExample;
import com.zhk.service.cargo.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class FactoryServiceImpl implements FactoryService {

    @Autowired
    private FactoryDao factoryDao;

    @Override
    public Factory findById(String id) {
        return factoryDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Factory factory) {
        factoryDao.insertSelective(factory);

    }

    @Override
    public void update(Factory factory) {
        factoryDao.updateByPrimaryKeySelective(factory);

    }

    @Override
    public void delete(String id) {

        factoryDao.deleteByPrimaryKey(id);

    }

    @Override
    public List<Factory> findAll(FactoryExample example) {

        List<Factory> factories = factoryDao.selectByExample(example);

        return factories;
    }
}
