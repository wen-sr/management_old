package com.management.wechat.service;

import com.management.common.ServerResponse;
import com.management.login.pojo.Login;

public interface IWeChatCommonService {
    ServerResponse register(Login login);

    ServerResponse login(Login login);

    Login getUserInfoByIdLike(String id);
}
