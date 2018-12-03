/**
 * 加载数据
 */
function loadData(pageSize, method, formData){
    $("#data").datagrid({
        url:'/management/deviceType/findAll',
        method: method || 'GET',
        formData:formData || '',
        height:'auto',
        fit:true,
        fitColumns: true,
        striped:true,
        rownumbers:true,
        border:true,
        singleSelect:true,
        pagination:true,
        pageSize: pageSize || 15,
        pageList:[10,15,20,50,pageSize || 0],
        showFooter: true,
        toolbar:'#tb',
        columns:[[{
            field:"deviceTypeId",
            title:"编号",
            checkbox:true,
            width:50
        },{
            field:"deviceTypeName",
            title:"设备类型",
            width:50
        },{
            field:"addwho",
            title:"添加人",
            width:50
        },{
            field:"adddate",
            title:"添加时间",
            width:50,
        }]]
    });

    var p = $('#data').datagrid('getPager');
    $(p).pagination({
        beforePageText: '第',
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onBeforeRefresh:function(){
            $(this).pagination('loading');
            $(this).pagination('loaded');
        },
        buttons: [{
            iconCls:'icon-sum',
            value:'显示所有',
            handler:function(){
                var d = $("#data").datagrid('getData');
                var deviceTypeId = $.trim($("#deviceTypeId").combobox('getValue'));
                var formData = {
                    "deviceTypeId"    : deviceTypeId
                };
                loadData(d.total,'POST',formData);
            }
        }]
    });
}

/**
 * 添加
 */
function addInfo(){
    var deviceTypeName = $.trim($("#add_deviceTypeName").textbox('getValue'));
    if(deviceTypeName == ""){
        $.messager.alert("操作提示","设备类型名称不能为空！","error");
        return;
    }
    var formData = {
        "deviceTypeName"    : deviceTypeName
    };
    $.ajax({
        type:'Post',
        url:'/management/deviceType/addDeviceType',
        data:formData,
        dataType:'json',
        success:function(res){
            if(res.status == 0){
                $.messager.alert("提示",res.msg,"info",function(){
                    loadData();
                    $("#w-addInfo").window("close");
                });
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
/**
 * 查询
 */
function queryInfo(){

    var deviceTypeId = $.trim($("#deviceTypeId").combobox('getValue'));
    var formData = {
        "deviceTypeId"    : deviceTypeId
    };
    loadData(15,'POST',formData);
}
/**
 * 修改
 */
function editInfo(){
    var id = $("#edit_id").val();
    var deviceTypeName = $.trim($("#edit_deviceTypeName").textbox('getValue'));
    if(deviceTypeName == ""){
        $.messager.alert("操作提示","设备类型名称不能为空！","error");
        return;
    }
    var formData = {
        "deviceTypeId"                : id,
        "deviceTypeName"    : deviceTypeName
    };
    $.ajax({
        type:'Post',
        url:'/management/deviceType/editDeviceType',
        data:formData,
        dataType:'json',
        success:function(res){
            if(res.status == 0){
                $.messager.alert("提示",res.msg,"info",function(){
                    $("#data").datagrid('reload');
                    $("#w-editInfo").window("close");
                });
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

/**
 * 删除
 */
function removeInfo(){
    var row = $('#data').datagrid('getSelected');
    var id = row.deviceTypeId;
    var formData = {
        "deviceTypeId"                : id
    };
    $.ajax({
        type:'Post',
        url:'/management/deviceType/deleteDeviceType',
        data:formData,
        dataType:'json',
        success:function(res){
            if(res.status == 0){
                $.messager.alert("提示",res.msg,"info",function(){
                    $("#data").datagrid('reload');
                    $("#w-editInfo").window("close");
                });
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
/**
 * 操作工具
 */
tool = {
    query : function (){
        $("#w-queryInfo").window("open");
    },
    add : function (){
        $("#w-addInfo").window("open");
    },
    edit : function (){
        var row = $('#data').datagrid('getSelected');
        if(!row || row.length == 0) {
            $.messager.alert("操作提示","没有选中记录","error");
            return;
        }
        $("#edit_id").val(row.deviceTypeId);
        $("#edit_deviceTypeName").textbox('setValue',row.deviceTypeName);
        $("#w-editInfo").window("open");
    },
    remove : function(){
        $.messager.confirm("操作提示", "您确定要删除这条记录吗？", function (data) {
            if (data) {
                removeInfo();
            }else {
                return;
            }
        });
    },
    yes : function (){
        var row = $("#c_subcode").datagrid('getSelected');
        if(row == null){
            $.messager.alert("操作提示","没有选中的记录","error");
            return;
        }
        $("#subcode").textbox('setValue',row.subcode);
        $("#showSubcode").window("close");
    },
    no : function (){
        $("#showSubcode").window("close");
    }
}


$(function(){
    loadData();
});