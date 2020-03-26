package com.zhk.controller.contract;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.zhk.controller.BaseController;
import com.zhk.domain.cargo.*;
import com.zhk.service.cargo.ContractProductService;
import com.zhk.service.cargo.ContractService;
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
@RequestMapping("cargo/contractProduct")
public class ContractProductController extends BaseController {

    @Reference
    private ContractProductService contractProductService;

    @Reference
    private ContractService contractService;

    @Reference
    private FactoryService factoryService;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @RequestMapping(value = "list", name = "查询所有货物操作")
    public String findAll(String contractId,
                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "10") Integer size) {

        ContractProductExample example = new ContractProductExample();
        example.createCriteria().andContractIdEqualTo(contractId);

        PageInfo<ContractProduct> pageInfo = contractProductService.findAll(example, page, size);

        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("货物");

        List<Factory> factories = factoryService.findAll(factoryExample);

        request.setAttribute("contractId", contractId);

        request.setAttribute("factoryList", factories);
        request.setAttribute("page", pageInfo);

        return "cargo/product/product-list";
    }

    @RequestMapping(value = "toAdd", name = "跳转新增货物页面操作")
    public String toAdd() {

        return "cargo/product/product-add";
    }

    @RequestMapping(value = "edit", name = "新增或修改货物内容操作")
    public String edit(MultipartFile productPhoto, ContractProduct contractProduct) {

        String imgPath = null;

        if (productPhoto != null) {
            try {
                imgPath = fileUploadUtil.upload(productPhoto);
                contractProduct.setProductImage(imgPath);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


        if (StringUtils.isBlank(contractProduct.getId())) {
            contractProduct.setId(UUID.randomUUID().toString().replace("-", ""));
            contractProduct.setCompanyId(companyId);
            contractProduct.setCompanyName(companyName);

            contractProduct.setCreateBy(userId);
            contractProduct.setCreateTime(new Date());
            contractProduct.setCreateDept(deptId);

            contractProductService.save(contractProduct);
        } else {
            contractProduct.setUpdateBy(userId);
            contractProduct.setUpdateTime(new Date());

            contractProductService.update(contractProduct);
        }

        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractProduct.getContractId();
    }

    @RequestMapping(value = "toUpdate", name = "跳转修改货物页面操作")
    public String toUpdate(String id ) {
        ContractProduct contractProduct = contractProductService.findById(id);

        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("货物");

        List<Factory> factories = factoryService.findAll(factoryExample);

        request.setAttribute("factoryList", factories);

        request.setAttribute("contractProduct", contractProduct);



        return "cargo/product/product-add";
    }

    @RequestMapping(value = "delete", name = "删除货物操作")
    public String delete(String id) {

        ContractProduct contractProduct = contractProductService.findById(id);
        contractProductService.delete(id);

        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractProduct.getContractId();
    }


}
