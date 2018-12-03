$(function(){
    $("input",$("#barcode").next("span")).blur(function(){
        var barcode = $.trim($("#barcode").textbox('getValue'));
        if(barcode == ''){
            return ;
        }
        var formData = {
            "barcode"   : barcode
        };
        $.ajax({
            type:'Post',
            url:'/management/sku/getSkuByBarcode',
            data:formData,
            dataType:'json',
            success:function(res){
                if(res.status == 0){
                    $("#descr").textbox('setValue',res.data[0].descr);
                    $("#price").textbox('setValue',res.data[0].price);
                    $("#qty").textbox('setValue','1');
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
    });

});

/**
 * 添加
 */
function addInfo(){
    var barcode = $.trim($("#barcode").textbox('getValue'));
    var descr = $.trim($("#descr").textbox('getValue'));
    var price = $.trim($("#price").textbox('getValue'));
    var qty = $.trim($("#qty").textbox('getValue'));
    if(barcode == ""){
        $.messager.alert("操作提示","条码不能为空！","error");
        return;
    }
    if(descr == ""){
        $.messager.alert("操作提示","书名不能为空！","error");
        return;
    }
    if(price == ""){
        $.messager.alert("操作提示","定价不能为空！","error");
        return;
    }
    if(qty == ""){
        $.messager.alert("操作提示","数量不能为空！","error");
        return;
    }
    var formData = {
        "barcode"             : barcode,
        "descr"               : descr,
        "price"               : price,
        "qty"                 : qty
    };
    $.ajax({
        type:'Post',
        url:'/management/temp/recodeSku/addInfo',
        data:formData,
        dataType:'json',
        success:function(res){
            if(res.status == 0){
                $.messager.alert("提示",res.msg,"info",function(){
                    $("#barcode").textbox('setValue','');
                    $("#descr").textbox('setValue','');
                    $("#price").textbox('setValue','');
                    $("#qty").textbox('setValue','1');
                });
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