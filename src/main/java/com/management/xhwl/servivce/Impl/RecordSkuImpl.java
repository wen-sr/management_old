package com.management.xhwl.servivce.Impl;

import com.management.common.ServerResponse;
import com.management.xhwl.dao.RecordSkuMapper;
import com.management.xhwl.pojo.RecordSku;
import com.management.xhwl.servivce.IRecordSku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RecordSkuImpl implements IRecordSku {

    @Autowired
    RecordSkuMapper recordSkuMapper;

    @Override
    public ServerResponse addInfo(RecordSku recordSku) {
        int i = recordSkuMapper.insertSelective(recordSku);
        if(i > 0){
            return ServerResponse.createBySuccessMsg("添加成功");
        }
        return ServerResponse.createByErrorMessage("添加失败");
    }
}
