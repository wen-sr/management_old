/**
 * 加载数据
 */
function loadData(pageSize, method, formData){
    $("#data").datagrid({
        url:'/management/role/findAll',
        method: method || 'GET',
        queryParams:formData || '',
        height:'auto',
        fit:true,
        fitColumns: true,
        striped:true,
        rownumbers:true,
        border:true,
        singleSelect:true,
        pagination:false,
        showFooter: true,
        toolbar:'#tb',
        columns:[[{
            field:"id",
            title:"编号",
            checkbox:true,
            width:50
        },{
            field:"orderBy",
            title:"角色编号",
            width:50
        },{
            field:"name",
            title:"名称",
            width:50
        }]]
    });

}

/**
 * 添加
 */
function addInfo(){
    var name = $.trim($("#add_name").textbox('getValue'));
    if(name == ""){
        $.messager.alert("操作提示","角色名称不能为空！","error");
        return;
    }
    var formData = {
        "name"  : name
    };
    $.ajax({
        type:'Post',
        url:'/management/role/addRole',
        data:formData,
        dataType:'json',
        success:function(res){
            if(res.status == 0){
                $.messager.alert("提示",res.msg,"info",function(){
                    $("#data").datagrid('reload');
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

    var organizationId = $("#organizationId").combotree('getValue');
    var formData = {
        "organizationId"    : organizationId
    };
    loadData(15,'POST',formData);
}
/**
 * 修改
 */
function editInfo(){
    var id = $.trim($("#edit_id").val());
    var name = $.trim($("#edit_name").textbox('getValue'));
    if(name == ""){
        $.messager.alert("操作提示","角色名称不能为空！","error");
        return;
    }
    var formData = {
        "id"        : id,
        "name"      : name
    };
    $.ajax({
        type:'Post',
        url:'/management/role/editRole',
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
    var id = row.id;
    var formData = {
        "id"                : id
    };
    $.ajax({
        type:'Post',
        url:'/management/role/deleteRole',
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
 * 权限管理
 */
function auth(){
    var row = $("#data").datagrid('getSelected');
    $("#cc").tree({
        url:'/management/role/getAuth.controller?id=' + row.id
    });
}

/**
 * 提交权限
 */
function confirmAuth(){
    var row = $("#data").datagrid('getSelected');
    var nodes = $('#cc').tree('getChecked');
    var s = '';
    for(var i=0; i<nodes.length; i++){
        if (s != '') s += ',';
        s += nodes[i].id;
    }
    $.ajax({
        type:'post',
        url:'/management/role/confirmAuth',
        data:'ids=' + s + "&id=" + row.id,
        dataType:'json',
        success:function(res){
            if(res.status == 0){
                $.messager.alert("提示",res.msg,"info",function(){
                    $("#data").datagrid('reload');
                    $("#w_auth").window("close");
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
        $("#edit_id").val(row.id);
        $("#edit_name").textbox('setValue',row.name);
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
    },
    auth : function (){
        var row = $("#data").datagrid('getSelected');
        if(!row){
            $.messager.alert("操作提示","没有选中数据","error");
            return;
        }
        auth();
        $("#w_auth").window("open");
    }

}



$(function(){
    loadData();
});