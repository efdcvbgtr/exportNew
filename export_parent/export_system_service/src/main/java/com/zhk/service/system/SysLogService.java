package com.zhk.service.system;

import com.github.pagehelper.PageInfo;
import com.zhk.domain.system.SysLog;

import java.util.List;

public interface SysLogService {

    public List<SysLog> findAll(String companyId);

    void save(SysLog sysLog);

    PageInfo<SysLog> findPage(Integer page, Integer size, String companyId);


}
