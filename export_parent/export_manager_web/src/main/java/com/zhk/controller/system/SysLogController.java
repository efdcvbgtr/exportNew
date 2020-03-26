package com.zhk.controller.system;



import com.github.pagehelper.PageInfo;
import com.zhk.controller.BaseController;

import com.zhk.domain.system.SysLog;
import com.zhk.service.system.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("system/log")
public class SysLogController extends BaseController {

    @Autowired
    private SysLogService sysLogService;

    @RequestMapping(value = "list",name = "展示日志数据列表")
    public String findAll(@RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size" ,defaultValue = "10") Integer size){

        PageInfo<SysLog> pageList = sysLogService.findPage(page, size, companyId);

        request.setAttribute("page",pageList);

        return "system/log/log-list";

    }



}
