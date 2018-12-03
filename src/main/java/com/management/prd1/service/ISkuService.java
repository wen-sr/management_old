package com.management.prd1.service;

import com.management.common.ServerResponse;

public interface ISkuService {
    ServerResponse getSkuByBarcode(String barcode);
}
