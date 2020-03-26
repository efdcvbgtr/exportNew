package com.zhk.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhk.dao.system.SysLogDao;
import com.zhk.domain.system.SysLog;
import com.zhk.service.system.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public List<SysLog> findAll(String companyId) {
        return sysLogDao.findAll(companyId);
    }

    @Override
    public void save(SysLog sysLog) {

        sysLogDao.save(sysLog);

    }

    @Override
    public PageInfo findPage(Integer page, Integer size, String companyId) {

        PageHelper.startPage(page, size);
        List<SysLog> list = sysLogDao.findAll(companyId);

        return new PageInfo(list, 5);
    }
    

}
