package com.zhk.service.cargo.impl;

import com.alibaba.druid.sql.PagerUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhk.commons.MethodList;
import com.zhk.dao.cargo.ContractDao;
import com.zhk.dao.cargo.ContractProductDao;
import com.zhk.dao.cargo.ExtCproductDao;
import com.zhk.domain.cargo.*;
import com.zhk.service.cargo.ContractService;
import com.zhk.service.cargo.ExtCproductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExtCproductServiceImpl implements ExtCproductService {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ExtCproductDao extCproductDao;

    @Override
    public ExtCproduct findById(String id) {
        return extCproductDao.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(ExtCproductExample example, int page, int size) {

        PageHelper.startPage(page,size);

        List<ExtCproduct> extCproducts = extCproductDao.selectByExample(example);

        return new PageInfo(extCproducts);
    }

    @Override
    public void save(ExtCproduct extCproduct) {

        Contract contract = this.doubleCheckContract(extCproduct, MethodList.m_save.getMethods());

        contractDao.updateByPrimaryKeySelective(contract);
        extCproductDao.insertSelective(extCproduct);

    }

    @Override
    public void update(ExtCproduct extCproduct) {

        Contract contract = this.doubleCheckContract(extCproduct, MethodList.m_update.getMethods());

        contractDao.updateByPrimaryKeySelective(contract);
        extCproductDao.updateByPrimaryKeySelective(extCproduct);

    }

    @Override
    public void delete(String id) {
        ExtCproduct extCproduct = extCproductDao.selectByPrimaryKey(id);

        Contract contract = doubleCheckContract(extCproduct, MethodList.m_delete.getMethods());

        extCproductDao.deleteByPrimaryKey(id);
        contractDao.updateByPrimaryKeySelective(contract);

    }

    private Contract doubleCheckContract(ExtCproduct extCproduct,String method){

        extCproduct.setAmount(extCproduct.getCnumber()*extCproduct.getPrice());

        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());

        ExtCproductExample extCproductExample = new ExtCproductExample();

        extCproductExample.createCriteria().andContractIdEqualTo(extCproduct.getContractId());

        List<ExtCproduct> extCproducts = extCproductDao.selectByExample(extCproductExample);

        ExtCproduct oldExt = extCproductDao.selectByPrimaryKey(extCproduct.getId());

        ContractProductExample contractProductExample = new ContractProductExample();

        contractProductExample.createCriteria().andContractIdEqualTo(extCproduct.getContractId());

        List<ContractProduct> contractProducts = contractProductDao.selectByExample(contractProductExample);


        Double total = 0.0;
        Integer eNum = 0;
        if (contractProducts!=null) {
            for (ContractProduct product : contractProducts) {
                total += product.getAmount();
            }
        }
        if (extCproducts!=null) {
            for (ExtCproduct ext : extCproducts) {
                total+=ext.getAmount();
                eNum+=1;
            }
        }

        if ("save".equals(method)) {
            contract.setTotalAmount(total+extCproduct.getAmount());
            contract.setExtNum(eNum+1);
        }else if("update".equals(method)){
            contract.setTotalAmount(total-oldExt.getAmount()+extCproduct.getAmount());
        }else if("delete".equals(method)){
            contract.setTotalAmount(total-extCproduct.getAmount());
            contract.setExtNum(eNum-1);
        }

        return contract;
    }

}
