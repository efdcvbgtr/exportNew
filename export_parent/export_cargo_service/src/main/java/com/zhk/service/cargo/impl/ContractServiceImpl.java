package com.zhk.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhk.dao.cargo.ContractDao;
import com.zhk.dao.cargo.ContractProductDao;
import com.zhk.dao.cargo.ExtCproductDao;
import com.zhk.domain.cargo.*;
import com.zhk.service.cargo.ContractProductService;
import com.zhk.service.cargo.ContractService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ExtCproductDao extCproductDao;

    @Override
    public Contract findById(String id) {
        return contractDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Contract contract) {
        contractDao.insertSelective(contract);

    }

    @Override
    public void update(Contract contract) {
        contractDao.updateByPrimaryKeySelective(contract);

    }

    @Override
    public void delete(String id) {

        ContractProductExample contractProductExample = new ContractProductExample();
        contractProductExample.createCriteria().andContractIdEqualTo(id);

        List<ContractProduct> contractProducts = contractProductDao.selectByExample(contractProductExample);

        if (contractProducts != null) {
            for (ContractProduct contractProduct : contractProducts) {
                contractProductDao.deleteByPrimaryKey(contractProduct.getId());
            }
        }

        ExtCproductExample extCproductExample = new ExtCproductExample();
        extCproductExample.createCriteria().andContractIdEqualTo(id);

        List<ExtCproduct> extCproducts = extCproductDao.selectByExample(extCproductExample);

        if (extCproductDao != null) {
            for (ExtCproduct extCproduct : extCproducts) {
                extCproductDao.deleteByPrimaryKey(extCproduct.getId());
            }

        }
        contractDao.deleteByPrimaryKey(id);

    }

    @Override
    public PageInfo findAll(ContractExample example, int page, int size) {

        PageHelper.startPage(page, size);
        List<Contract> contracts = contractDao.selectByExample(example);

        return new PageInfo(contracts);
    }
}
