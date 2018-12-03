/**
 * 加载数据
 */
function loadData(pageSize, method, formData){
    $("#data").datagrid({
        url:'/management/deviceList/findAll',
        method: method || 'GET',
        queryParams:formData || '',
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
            field:"id",
            title:"编号",
            checkbox:true,
            width:50
        },{
            field:"deviceId",
            title:"设备编号",
            width:50
        },{
            field:"deviceTypeName",
            title:"设备类型",
            width:50
        },{
            field:"bk2",
            title:"规格型号",
            width:50
        },{
            field:"status",
            title:"设备状态 ",
            width:50,
            formatter:formatStatus
        },{
            field:"organizationName",
            title:"使用部门",
            width:50
        },{
            field:"addwho",
            title:"添加人",
            width:50
        },{
            field:"adddate",
            title:"添加时间",
            width:50
        },{
            field:"bk1",
            title:"当前操作人",
            width:50
        },{
            field:"editdate",
            title:"操作时间",
            width:50
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
 * 格式化字段
 * @param val
 * @param row
 * @param index
 * @returns {*}
 */
function formatStatus(val,row,index){
    if (val == "可用"){
        return '<span style="color:green;">'+val+'</span>';
    } else if(val == "使用中"){
        return '<span style="color:red;">'+val+'</span>';
    }else if(val == "维修中"){
        return '<span style="color:blueviolet;">'+val+'</span>';
    }else if(val == "已报废"){
        return '<span style="color:mediumvioletred;">'+val+'</span>';
    }else {
        return val;
    }
}

/**
 * 添加
 */
function addInfo(){
    var deviceTypeId = $.trim($("#add_deviceTypeId").combobox('getValue'));
    var deviceId = $.trim($("#add_deviceId").textbox('getValue'));
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
    var status = $.trim($("#status").combobox('getValue'));
    var deviceId = $.trim($("#deviceId").textbox('getValue'));
    var organizationId = $.trim($("#organizationId").combotree('getValue'));

    var formData = {
        "deviceTypeId"      : deviceTypeId,
        "status"            : status,
        "deviceId"          : deviceId,
        "bk3"               : organizationId
    };
    loadData(15,'POST',formData);
}
/**
 * 修改
 */
function editInfo(){
    var deviceId = $.trim($("#edit_deviceId").textbox('getValue'));
    var deviceTypeId = $.trim($("#edit_deviceTypeId").combobox('getValue'));
    var specification = $.trim($("#edit_specification").textbox('getValue'));
    if(deviceId == ""){
        $.messager.alert("操作提示","设备类型不能为空！","error");
        return;
    }
    if(deviceTypeId == ""){
        $.messager.alert("操作提示","设备编号不能为空！","error");
        return;
    }
    var formData = {
        "deviceTypeId"  : deviceTypeId,
        "deviceId"      : deviceId,
        "bk2"           : specification
    };
    $.ajax({
        type:'Post',
        url:'/management/deviceList/editDevice',
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

/**
 * 删除
 */
function removeInfo(){
    var row = $('#data').datagrid('getSelected');
    var id = row.deviceId;
    var formData = {
        "deviceId"                : id
    };
    $.ajax({
        type:'Post',
        url:'/management/deviceList/deleteDevice',
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
        $("#edit_deviceId").textbox('setValue',row.deviceId);
        $("#edit_deviceTypeId").combobox('setValue',row.deviceTypeId);
        $("#edit_specification").textbox('setValue',row.bk2);
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