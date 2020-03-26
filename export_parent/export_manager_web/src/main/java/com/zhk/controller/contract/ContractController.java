package com.zhk.controller.contract;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.zhk.controller.BaseController;
import com.zhk.domain.cargo.Contract;
import com.zhk.domain.cargo.ContractExample;
import com.zhk.service.cargo.ContractService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("cargo/contract")
public class ContractController extends BaseController {

    @Reference
    private ContractService contractService;

    @RequestMapping(value = "list",name = "查询所有购销合同操作")
    public String findAll(@RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size" ,defaultValue = "10") Integer size){

        ContractExample example = new ContractExample();
        example.createCriteria().andCompanyIdEqualTo(companyId);
        example.setOrderByClause("create_time desc");

        PageInfo<Contract> pageInfo = contractService.findAll(example,page,size);
        request.setAttribute("page",pageInfo);

        return "cargo/contract/contract-list";
    }

    @RequestMapping(value = "toAdd",name = "跳转新增购销合同页面操作")
    public String toAdd(){


        return "cargo/contract/contract-add";
    }

    @RequestMapping(value = "edit",name = "新增或修改购销合同内容操作")
    public String edit(Contract contract){
        if(StringUtils.isBlank(contract.getId())){
            contract.setId(UUID.randomUUID().toString().replace("-",""));
            contract.setCompanyId(companyId);
            contract.setCompanyName(companyName);

            contract.setCreateBy(userId);
            contract.setCreateTime(new Date());
            contract.setCreateDept(deptId);
            contract.setState(0);

            contractService.save(contract);
        }else {
            contract.setUpdateBy(userId);
            contract.setUpdateTime(new Date());

            contractService.update(contract);
        }

        return "redirect:/cargo/contract/list.do";
    }

    @RequestMapping(value = "toUpdate",name = "跳转修改购销合同页面操作")
    public String toUpdate(String id){
        Contract contract = contractService.findById(id);

        request.setAttribute("contract",contract);

        return "cargo/contract/contract-add";
    }

    @RequestMapping(value = "delete",name = "删除购销合同操作")
    public String delete(String id){
        contractService.delete(id);

        return "redirect:/cargo/contract/list.do";
    }




}
