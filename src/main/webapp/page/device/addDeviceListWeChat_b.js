$(function(){
    $.ajax({
        type:'Post',
        url:'/management/sign/getSign',
        data:"myUrl=" + encodeURIComponent(window.location.href),
        dataType:'json',
        success:function(res){
            var timestamp = res.timestamp;//时间戳
            var nonceStr = res.nonceStr;//随机串
            var signature = res.signature;//签名
            wx.config({
                debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。

                appId: 'wx5993aae7e1692dd7', // 必填，公众号的唯一标识

                timestamp: timestamp, // 必填，生成签名的时间戳

                nonceStr: nonceStr, // 必填，生成签名的随机串

                signature: signature,// 必填，签名，见附录1

                jsApiList: ['scanQRCode','qrCode','barCode'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2

            });
        },
        error:function(){
            $.messager.alert("提示","数据错误，联系管理员","error");
        }
    });
});

/**
 * 添加
 */
function addInfo(){
    var deviceTypeId = $.trim($("#add_deviceTypeId").combobox('getValue'));
    var deviceId = $.trim($("#add_deviceId").textbox('getValue'));
    var organizationId = $("#add_organizationId").combotree('getValue');
    var specification = $.trim($("#add_specification").textbox('getValue'));
    if(deviceTypeId == ""){
        $.messager.alert("操作提示","设备类型不能为空！","error");
        return;
    }
    if(deviceId == ""){
        $.messager.alert("操作提示","设备编号不能为空！","error");
        return;
    }
    var formData = {
        "deviceId"          : deviceId,
        "deviceTypeId"      : deviceTypeId,
        "organizationId"    : organizationId,
        "bk2"               : specification
    };
    $.ajax({
        type:'Post',
        url:'/management/deviceList/addDevice',
        data:formData,
        dataType:'json',
        success:function(res){
            if(res.status == 0){
                $.messager.alert("提示",res.msg,"info",function(){
                    $("#add_deviceId").textbox('setValue', '');
                });
            }else {
                $.messager.alert("提示",res.msg,"error");
            }
        },
        error:function(){
            $.messager.alert("提示","数据错误，联系管理员","error");
        }
    });
}