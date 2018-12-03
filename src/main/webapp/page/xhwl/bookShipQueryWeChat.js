$(function(){
    if(!window.localStorage){
        alert("浏览器支持localstorage");
        return false;
    }else{
        //主逻辑业务
    }
});

/**
 * 查询
 */
function query(){
    var shipkey = $.trim($("#shipkey").textbox('getValue'))==""?undefined: $.trim($("#shipkey").textbox('getValue'));
    var caseid = $.trim($("#caseid").textbox('getValue'))==""?undefined:$.trim($("#caseid").textbox('getValue'));
    var status = $.trim($("#status").combobox('getValue'))==""?undefined:$.trim($("#status").combobox('getValue'));
    var formData = {
        "shipkey"   : shipkey,
        "caseid"    : caseid,
        "status"    : status
    };
    $.ajax({
        type:'Post',
        url:'/management/logisticsQuery/bookShipQuery',
        data:formData,
        dataType:'json',
        success:function(res){
            if(res.status == 0){
                var localStorage = window.localStorage;
                localStorage.clear();
                localStorage.setItem("data", JSON.stringify(res.data));
                window.location.href = './bookShipShow.html';
            }else if(res.status == 1) {
                $.messager.alert("提示",res.msg,"error");
            }else if(res.status == 403){
                window.location.href = '../error/403.html';
            }else if(res.status == 10){
                _util.doLogin();
            }

        },
        error:function(){
            $.messager.alert("提示","数据错误，联系管理员","error");
        }
    });
}