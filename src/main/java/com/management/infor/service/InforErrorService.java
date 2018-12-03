package com.management.infor.service;

import com.management.common.ServerResponse;
import com.management.infor.pojo.InforError;

public interface InforErrorService {
    ServerResponse addInforError(InforError inforError);

    ServerResponse findAll(InforError inforError, Integer pageSize, Integer pageNum, String organizationId);

    ServerResponse edit(InforError inforError);

    ServerResponse delete(InforError inforError);
}
