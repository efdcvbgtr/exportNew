package com.zhk.service.company.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.zhk.company_interface.company.CompanyService;
import com.zhk.dao.company.CompanyDao;
import com.zhk.domain.company.Company;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Override
    public List<Company> findAll() {
        return companyDao.findAll();
    }

    @Override
    public void save(Company company) {

        company.setId(UUID.randomUUID().toString());
        company.setState(0);

        companyDao.save(company);

    }

    @Override
    public void update(Company company) {
        companyDao.update(company);
    }

    @Override
    public Company findById(String id) {
        return companyDao.findById(id);
    }

    @Override
    public void deleteById(String id) {
        companyDao.delete(id);


    }

    @Override
    public PageInfo findPage(Integer page, Integer size) {

        PageHelper.startPage(page,size);
        List<Company> list = companyDao.findAll();

        return new PageInfo(list,5);
    }
}
