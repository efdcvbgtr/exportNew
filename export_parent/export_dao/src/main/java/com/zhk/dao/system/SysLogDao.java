package com.zhk.dao.system;

import com.zhk.domain.system.SysLog;

import java.util.List;

public interface SysLogDao {

    public List<SysLog> findAll(String companyId);

    void save(SysLog sysLog);

}
