package com.management.prd1.service.impl;

import com.management.common.ServerResponse;
import com.management.prd1.dao.XsogroupMapper;
import com.management.prd1.dto.RecordSkuDto;
import com.management.prd1.service.ISkuService;
import com.management.util.DataSourceContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SkuServiceImp implements ISkuService {

    @Autowired
    XsogroupMapper xsogroupMapper;

    @Override
    public ServerResponse getSkuByBarcode(String barcode) {
        DataSourceContextHolder. setDbType(DataSourceContextHolder.SESSION_FACTORY_PRD1);
        List<RecordSkuDto> skuList = xsogroupMapper.selectByBarcode(barcode);
        if(skuList != null && skuList.size() > 0){
            return ServerResponse.createBySuccess(skuList);
        }
        return null;
    }
}
