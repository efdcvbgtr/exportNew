package com.zhk.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhk.commons.MethodList;
import com.zhk.dao.cargo.ContractDao;
import com.zhk.dao.cargo.ContractProductDao;
import com.zhk.dao.cargo.ExtCproductDao;
import com.zhk.domain.cargo.*;
import com.zhk.service.cargo.ContractProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContractProductServiceImpl implements ContractProductService {

    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ExtCproductDao extCproductDao;

    @Autowired
    private ContractDao contractDao;

    @Override
    public ContractProduct findById(String id) {
        return contractProductDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(ContractProduct contractProduct) {

        Contract contract = this.doubleCheckContract(contractProduct, MethodList.m_save.getMethods());

        contractProductDao.insertSelective(contractProduct);
        contractDao.updateByPrimaryKeySelective(contract);

    }

    @Override
    public void update(ContractProduct contractProduct) {

        Contract contract = this.doubleCheckContract(contractProduct, MethodList.m_update.getMethods());

        contractProductDao.updateByPrimaryKeySelective(contractProduct);
        contractDao.updateByPrimaryKeySelective(contract);

    }

    @Override
    public void delete(String id) {


        ContractProduct contractProduct = contractProductDao.selectByPrimaryKey(id);
        Contract contract = this.doubleCheckContract(contractProduct, MethodList.m_delete.getMethods());


        ExtCproductExample extCproductExample = new ExtCproductExample();
        extCproductExample.createCriteria().andContractProductIdEqualTo(id);

        List<ExtCproduct> extCproducts = extCproductDao.selectByExample(extCproductExample);

        Double exTotal = 0.0;
        if (extCproductDao != null) {
            contract.setExtNum(contract.getProNum() - extCproducts.size());
            for (ExtCproduct extCproduct : extCproducts) {
                exTotal += extCproduct.getAmount();
                extCproductDao.deleteByPrimaryKey(extCproduct.getId());
            }
        }

        contract.setTotalAmount(contract.getTotalAmount() - exTotal);

        contractProductDao.deleteByPrimaryKey(id);
        contractDao.updateByPrimaryKeySelective(contract);

    }

    @Override
    public PageInfo findAll(ContractProductExample example, int page, int size) {

        PageHelper.startPage(page, size);
        List<ContractProduct> contractProducts = contractProductDao.selectByExample(example);

        return new PageInfo(contractProducts);
    }


    private Contract doubleCheckContract(ContractProduct contractProduct, String method) {

        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());

        contractProduct.setAmount(contractProduct.getPrice() * contractProduct.getCnumber());

        ContractProductExample contractProductExample = new ContractProductExample();

        contractProductExample.createCriteria().andContractIdEqualTo(contractProduct.getContractId());

        List<ContractProduct> contractProducts = contractProductDao.selectByExample(contractProductExample);

        ContractProduct oldCP = contractProductDao.selectByPrimaryKey(contractProduct.getId());

        ExtCproductExample extCproductExample = new ExtCproductExample();

        extCproductExample.createCriteria().andContractIdEqualTo(contractProduct.getContractId());

        List<ExtCproduct> extCproducts = extCproductDao.selectByExample(extCproductExample);

        Double total = 0.0;
        Integer pNum = 0;
        if (contractProducts!=null) {
            for (ContractProduct product : contractProducts) {
                total += product.getAmount();
                pNum += 1;
            }
        }
        if (extCproducts!=null) {
            for (ExtCproduct extCproduct : extCproducts) {
                total+=extCproduct.getAmount();
            }
        }

        if (method.equals("save")) {
            contract.setProNum(pNum + 1);
            contract.setTotalAmount(total + contractProduct.getAmount());
        } else if (method.equals("delete")) {
            contract.setProNum(pNum - 1);
            contract.setTotalAmount(total - contractProduct.getAmount());
        } else if (method.equals("update")) {
            contract.setTotalAmount(total - oldCP.getAmount() + contractProduct.getAmount());
        }

        return contract;
    }
}
