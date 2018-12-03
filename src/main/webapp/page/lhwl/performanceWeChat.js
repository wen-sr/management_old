$(function(){

    $("#editPwd").click(function () {
        $("#w").window('open');
    });

    $("#go").click(function () {
        go();
    });

    $("#btnEp").click(function () {
        modifyPwd();
    });
    $("#btnCancel").click(function () {
        $("#w").window('close');
    });

});


function go(){
    var begin = $("#begin").textbox('getValue');
    var end = $("#end").textbox('getValue');
    var pwd = $("#pwd").passwordbox('getValue');
    var formData = {
        "begin"   : begin,
        "end"     : end,
        "pwd"     : pwd
    }
    $.ajax({
        type:'Post',
        url:'/management/performance/query',
        data:formData,
        dataType:'json',
        success:function(res){
            if(res.status == 0){
                window.location.href = "performanceShow.html";
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


function modifyPwd(){
    var oldPwd = $("#txtOldPass").val();
    var newPwd = $("#txtNewPass").val();
    var rePwd = $("#txtRePass").val();

    if(oldPwd == "") {
        $.messager.alert("提示","原口令不能为空","error");
        return;
    }
    if(newPwd == "") {
        $.messager.alert("提示","新口令不能为空","error");
        return;
    }
    if(rePwd == "") {
        $.messager.alert("提示","确认口令不能为空","error");
        return;
    }

    var formData = {
        "oldPwd"    : oldPwd,
        "newPwd"    : newPwd,
        "rePwd"     : rePwd
    }

    $.ajax({
        type:'Post',
        url:'/management/performance/modifyPwd',
        data:formData,
        dataType:'json',
        success:function(res){
            if(res.status == 0){
                $.messager.alert("提示",res.msg,"info");
                $("#w").window('close');
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
