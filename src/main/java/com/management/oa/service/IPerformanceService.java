package com.management.oa.service;

import com.management.common.ServerResponse;

import java.io.IOException;
import java.util.List;

public interface IPerformanceService {
    ServerResponse saveRecords(List<List<Object>> listob) throws Exception;

    ServerResponse query(String begin, String end, String pwd, String id) throws IOException;

    ServerResponse modifyPwd(String oldPwd, String newPwd, String rePwd, String id);
}
