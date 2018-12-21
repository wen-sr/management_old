package com.management.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface IWebService {
    @WebMethod
    public String getMsg();
}
