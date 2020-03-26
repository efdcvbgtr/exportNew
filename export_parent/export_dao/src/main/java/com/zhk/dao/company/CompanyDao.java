package com.zhk.dao.company;

import com.zhk.domain.company.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyDao {

    public List<Company> findAll();

    void save(Company company);

    void update(Company company);

    Company findById(String id);

    void delete(String id);

    List<Company> findPage(@Param("begin") Integer begin,@Param("size") Integer size);

}
