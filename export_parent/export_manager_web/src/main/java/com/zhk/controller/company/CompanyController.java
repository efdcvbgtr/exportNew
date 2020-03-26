package com.zhk.controller.company;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.zhk.company_interface.company.CompanyService;
import com.zhk.controller.BaseController;
import com.zhk.domain.company.Company;
import com.zhk.domain.vo.PageBean;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("company")
public class CompanyController extends BaseController {

    @Reference
    private CompanyService companyService;

    @RequestMapping(value = "list",name = "查询所有公司列表")
    @RequiresPermissions("企业管理")
    public String findAll(@RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size" ,defaultValue = "3") Integer size){

        //List<Company> list = companyService.findAll();
        PageInfo<Company> pageInfo = companyService.findPage(page,size);
        request.setAttribute("page",pageInfo);

        return "company/company-list";
    }

    @RequestMapping(value = "toAdd",name = "跳转新增公司页面操作")
    public String toAdd(){

        return "company/company-add";
    }

    @RequestMapping(value = "edit",name = "新增或修改公司操作")
    public String edit(Company company){
        if(StringUtils.isBlank(company.getId())){
            company.setId(UUID.randomUUID().toString());
            companyService.save(company);
        }else {
            companyService.update(company);
        }


        return "redirect:/company/list.do";
    }

    @RequestMapping(value = "toUpdate",name = "进入公司编辑页面")
    public String toUpdate(String id){
        Company company = companyService.findById(id);

        request.setAttribute("company",company);

        return "company/company-add";
    }

    @RequestMapping(value = "delete",name = "删除公司操作")
    public String delete(String id){
        companyService.deleteById(id);

        return "redirect:/company/list.do";
    }
}
