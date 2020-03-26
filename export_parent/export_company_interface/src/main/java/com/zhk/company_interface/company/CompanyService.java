package com.zhk.company_interface.company;

import com.github.pagehelper.PageInfo;
import com.zhk.domain.company.Company;

import java.util.List;

public interface CompanyService {

    public List<Company> findAll();

    void save(Company company);

    void update(Company company);

    Company findById(String id);

    void deleteById(String id);

    PageInfo<Company> findPage(Integer page, Integer size);
}
