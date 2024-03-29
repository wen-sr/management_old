package com.management.common;

public class Constant {
    public static final String CURRENT_USER                 = "currentUser";
    public static final String USERNAME                     = "username";
    public static final String USERID                       = "userId";
    public static final String EMAIL                        = "email";
    public static final String OPENID                       = "openId";
//    public static final String WECHATDOMAIN     = "http://wensr.mynatapp.cc";
    public static final String WECHATDOMAIN                 = "http://www.jxlh56.com";
    public static final String WXMPOAUTH2ACCESSTOKEN        = "wxMpOAuth2AccessToken";
    public static final String DEFAULTPASSWORD              = "123456";
    //public static final String FTPURL                       = "ftp://141.168.1.188/img";
    public static final String FTPURL                       = "ftp://141.168.1.137/img/";
    public static final String DESSOLT                      = "56ag5ahg;agjag/;arga;lg;kre";
    public static final String PERFORMANCE                  = "performance";


    public interface Role{
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1;//管理员
        int ROLE_TECHNOLOGY = 2;
        int ROLE_DEVICE = 3;
    }
    public interface LoginSystemFlag{
        String DEFAULT = "0";
        String XIN_HUA = "1";
        String WCS = "2";
        String PALLECT = "3";
        String TMS = "4";
        String REPORT = "5";
        String TECH = "6";
        String JIAO_CAI = "7";
    }


    public enum UserSexEnum{

        FEMALE(1, "女"),
        MALE(0, "男"),
        ;

        private Integer code;
        private String msg;

        UserSexEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public static UserSexEnum codeOf(int code){
            for(UserSexEnum  userSexEnum: values()){
                if(userSexEnum.getCode() == code){
                    return userSexEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    public enum UserStatusEnum{

        ACTIVE(1, "已激活"),
        UNACTIVE(0, "未激活"),
        ;

        private Integer code;
        private String msg;

        UserStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public static UserStatusEnum codeOf(int code){
            for(UserStatusEnum  userStatusEnum: values()){
                if(userStatusEnum.getCode() == code){
                    return userStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }
    public enum DeviceTransStatusEnum{

        TAKE(1, "已领用"),
        REPAIR(2, "已报修"),
        RETURN(0, "已归还"),
        SCRAPPED(3, "已报废")
        ;

        private Integer code;
        private String msg;

        DeviceTransStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public static DeviceTransStatusEnum codeOf(int code){
            for(DeviceTransStatusEnum  deviceTransStatusEnum: values()){
                if(deviceTransStatusEnum.getCode() == code){
                    return deviceTransStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }


    public enum DeviceListStatusEnum{

        FREE(0, "可用"),
        USING(1, "使用中"),
        REPAIRING(2, "维修中"),
        SCRAPPED(3, "已报废")
        ;

        private Integer code;
        private String msg;

        DeviceListStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public static DeviceListStatusEnum codeOf(int code){
            for(DeviceListStatusEnum deviceListStatusEnum : values()){
                if(deviceListStatusEnum.getCode() == code){
                    return deviceListStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    public enum DeviceRepairEnum{

        REPAIRING(2, "维修中"),
        REPAIRED(4, "已维修"),
        SCRAPPED(3, "已报废")
        ;

        private Integer code;
        private String msg;

        DeviceRepairEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public static DeviceRepairEnum codeOf(int code){
            for(DeviceRepairEnum deviceRepairEnum : values()){
                if(deviceRepairEnum.getCode() == code){
                    return deviceRepairEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }


    public enum DeviceStatusEnum{

        TAKE(0, "领用"),
        RETURN(1, "归还"),
        FIX(2, "维修"),
        QUERY(3, "查询"),
        REPAIRING(4, "维修中"),
        REPAIRED(5, "已维修")
        ;

        private Integer code;
        private String msg;

        DeviceStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public static DeviceStatusEnum codeOf(int code){
            for(DeviceStatusEnum deviceStatusEnum : values()){
                if(deviceStatusEnum.getCode() == code){
                    return deviceStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }


    public enum InforErrorStatusEnum{

        RESOLVING(0, "处理中"),
        RESOLVED(1, "已处理"),
        ;

        private Integer code;
        private String msg;

        InforErrorStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public static InforErrorStatusEnum codeOf(int code){
            for(InforErrorStatusEnum colum : values()){
                if(colum.getCode() == code){
                    return colum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    public enum InforErrorTypeEnum{

        MALFUNCTION(0, "系统故障"),
        REQUEST(1, "系统需求")
        ;

        private Integer code;
        private String msg;

        InforErrorTypeEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public static InforErrorTypeEnum codeOf(int code){
            for(InforErrorTypeEnum colum : values()){
                if(colum.getCode() == code){
                    return colum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }


}
