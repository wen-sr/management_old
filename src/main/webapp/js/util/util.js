/*
* @Author: Rosen
* @Date:   2017-05-15 15:26:38
* @Last Modified by:   wen-sr
* @Last Modified time: 2017-08-30 17:35:35
*/

'use strict';
var conf = {
    serverHost : '/management'
};
var _util = {
    // 网络请求
    request : function(param){
        var _this = this;
        $.ajax({
            type        : param.method  || 'get',
            url         : param.url     || '',
            dataType    : param.type    || 'json',
            data        : param.data    || '',
            success     : function(res){
                // 请求成功
                if(0 === res.status){
                    typeof param.success === 'function' && param.success(res);
                }
                // 没有登录状态，需要强制登录
                else if(10 === res.status){
                    _this.doLogin();
                }
                // 请求数据错误
                else if(1 === res.status){
                    typeof param.error === 'function' && param.error(res.msg);
                }
            },
            error       : function(err){
                typeof param.error === 'function' && param.error(err.statusText);
            }
        });
    },
    // 获取服务器地址
    getServerUrl : function(path){
        return conf.serverHost + path;
    },
    // 获取url参数
    getUrlParam : function(name){
        var reg     = new RegExp('(^|&)' + name + '=([^&]*)(&|$)');
        var result  = window.location.search.substr(1).match(reg);
        return result ? decodeURIComponent(result[2]) : null;
    },
    // 渲染html模板
    renderHtml : function(htmlTemplate, data){
        var template    = Handlebars.compile(htmlTemplate),
            result      = template(data);
        return result;
    },
    // 成功提示
    successTips : function(msg){
        alert(msg || '操作成功！');
    },
    // 错误提示
    errorTips : function(msg){
        alert(msg || '哪里不对了~');
    },
    // 字段的验证
    validate : function(value, type){
        var value = $.trim(value);
        // 非空验证
        if('require' === type){
            return !!value;
        }
    },
    // 统一登录处理
    doLogin : function(){
        // window.location.href = './user/login.html?redirect=' + encodeURIComponent(window.location.href);
        window.top.location = _util.getServerUrl('/login.html?redirect=' + encodeURIComponent(window.location.href));
    },
    goHome : function(){
        window.location.href = '/home.html';
    },
    //检查用户是否登录
    loadUserInfo : function(resolve, reject){
        _util.request({
            url : this.getServerUrl("/user/getUserInfo"),
            method : "post",
            success : resolve,
            error : reject
        })
    },
    // 提交
    submitGo:   function (url, method, formData, resolve, reject) {
        _util.request({
            url     : _util.getServerUrl(url),
            method  : method,
            data    : formData,
            success : resolve,
            error   : reject
        })
    },
    dateFormatter: function (val,row,index) {
        var unixTimestamp = new Date( val ) ;
        return unixTimestamp.getFullYear() + "/" + (unixTimestamp.getMonth() + 1) + "/" + unixTimestamp.getDate() + " " + unixTimestamp.getHours() + ":" + unixTimestamp.getMinutes() + ":" + unixTimestamp.getSeconds();
    }


};