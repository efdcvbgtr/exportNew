package com.zhk.controller.contract;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.zhk.controller.BaseController;
import com.zhk.domain.cargo.*;
import com.zhk.service.cargo.ContractProductService;
import com.zhk.service.cargo.ContractService;
import com.zhk.service.cargo.ExtCproductService;
import com.zhk.service.cargo.FactoryService;
import com.zhk.utils.FileUploadUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("cargo/extCproduct")
public class ExtCProductController extends BaseController {

    @Reference
    private ContractProductService contractProductService;

    @Reference
    private ContractService contractService;

    @Reference
    private ExtCproductService extCproductService;

    @Reference
    private FactoryService factoryService;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @RequestMapping(value = "list", name = "查询所有附件操作")
    public String findAll(String contractId,String contractProductId,
                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "10") Integer size) {

        ExtCproductExample example = new ExtCproductExample();
        example.createCriteria().andContractIdEqualTo(contractId);

        PageInfo<ContractProduct> pageInfo = extCproductService.findAll(example, page, size);

        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("附件");

        List<Factory> factories = factoryService.findAll(factoryExample);

        request.setAttribute("contractId", contractId);
        request.setAttribute("contractProductId", contractProductId);

        request.setAttribute("factoryList", factories);
        request.setAttribute("page", pageInfo);

        return "cargo/extc/extc-list";
    }

    @RequestMapping(value = "toAdd", name = "跳转新增附件页面操作")
    public String toAdd() {

        return "cargo/extc/extc-add";
    }

    @RequestMapping(value = "edit", name = "新增或修改附件内容操作")
    public String edit(MultipartFile productPhoto, ExtCproduct extCproduct) {

        String imgPath = null;

        if (productPhoto != null) {
            try {
                imgPath = fileUploadUtil.upload(productPhoto);
                extCproduct.setProductImage(imgPath);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


        if (StringUtils.isBlank(extCproduct.getId())) {
            extCproduct.setId(UUID.randomUUID().toString().replace("-", ""));
            extCproduct.setCompanyId(companyId);
            extCproduct.setCompanyName(companyName);

            extCproduct.setCreateBy(userId);
            extCproduct.setCreateTime(new Date());
            extCproduct.setCreateDept(deptId);

            extCproductService.save(extCproduct);
        } else {
            extCproduct.setUpdateBy(userId);
            extCproduct.setUpdateTime(new Date());

            extCproductService.update(extCproduct);
        }

        return "redirect:/cargo/extCproduct/list.do?contractId=" + extCproduct.getContractId() +
                "&contractProductId=" + extCproduct.getContractProductId();
    }

    @RequestMapping(value = "toUpdate", name = "跳转修改附件页面操作")
    public String toUpdate(String id,String contractId,String contractProductId) {
        ExtCproduct extCproduct = extCproductService.findById(id);


        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("附件");

        List<Factory> factories = factoryService.findAll(factoryExample);

        request.setAttribute("factoryList", factories);

        request.setAttribute("extCproduct", extCproduct);

        request.setAttribute("contractId", contractId);

        request.setAttribute("contractProductId", contractProductId);

        return "cargo/extc/extc-add";
    }

    @RequestMapping(value = "delete", name = "删除附件操作")
    public String delete(String id) {

        ExtCproduct extCproduct = extCproductService.findById(id);
        extCproductService.delete(id);

        return "redirect:/cargo/extCproduct/list.do?contractId=" + extCproduct.getContractId()+
                "&contractProductId=" + extCproduct.getContractProductId();
    }


}
