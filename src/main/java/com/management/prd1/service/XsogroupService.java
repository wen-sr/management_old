package com.management.prd1.service;

import com.management.common.ServerResponse;
import com.management.prd1.pojo.Xsogroup;

public interface XsogroupService {
    ServerResponse getReceiptAndDelivery(String begin, String end);

    ServerResponse getYield(String begin, String end);
}
