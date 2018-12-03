/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-03  17:38
 */
$(function(){
    checkUserLogin();

    $('#logOut').click(function () {
        $.messager.confirm('系统提示', '您确定要退出登录吗?', function (r) {
            if (r) {
                $.ajax({
                   type:'Post',
                   url:'/management/user/logout',
                   dataType:'json',
                   success:function(res){
                       if(res.status == 0){
                           _util.doLogin();
                       }else if(res.status == 1) {
                           $.messager.alert("提示",res.msg,"error");
                       }else if(res.status == 10){
                           _util.doLogin();
                       }
                   },
                   error:function(){
                       $.messager.alert("提示","数据错误，联系管理员","error");
                   }
                });
            }
        });
    });
})

function checkUserLogin(){
    _util.loadUserInfo(function(res){
        $(".username").text(res.data.organizationName + ":" + res.data.user.name);
        var targetCookies = res.data.targetCookies;
        $.each(targetCookies,function(i,targetCookie){
            var targetUrl = targetCookie.targetUrl;
            var cookieName = targetCookie.cookieName;
            var cookieValue = targetCookie.cookieValue;
            creat(targetUrl,cookieName,cookieValue);
        });
        InitLeftMenu(res.data.menuList);
    },function(erro){
        window.location.href = _util.getServerUrl('/login.html');
    })
}

/** js利用iframe实现跨域添加cookie */
function creat(targetUrl,cookieName,cookieValue){
    var iframe = document.createElement('iframe');
    var targetSrc = targetUrl+"?"+"cookieName="+cookieName+"&cookieValue="+cookieValue;
    iframe.src=targetSrc;
    document.body.appendChild(iframe);
}


