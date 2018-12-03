/**
 * 加载数据
 */
function loadData(pageSize, method, formData){
    $("#data").datagrid({
        url:'/management/user/findAll',
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
            field:"",
            title:"编号",
            checkbox:true,
            width:50
        },{
            field:"id",
            title:"工号",
            width:50
        },{
            field:"name",
            title:"姓名",
            width:50
        },{
            field:"bk1",
            title:"性别",
            width:50
        },{
            field:"organizationName",
            title:"部门",
            width:50
        },{
            field:"status",
            title:"状态",
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
                var organizationId = $("#organizationId").combotree('getValue');
                var formData = {
                    "organizationId"    : organizationId
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
    var organizationId  = $.trim($("#add_OrganizationId").combobox('getValue'));
    var id              = $.trim($("#add_id").textbox('getValue'));
    var name            = $.trim($("#add_name").textbox('getValue'));
    var sex             = $.trim($("#add_sex").combobox('getValue'));
    if(id == ""){
        $.messager.alert("操作提示","工号不能为空！","error");
        return;
    }
    if(name == ""){
        $.messager.alert("操作提示","姓名不能为空！","error");
        return;
    }
    if(organizationId == '') {
        $.messager.alert("操作提示","请选择部门！","error");
        return;
    }
    var formData = {
        "organizationId"    : organizationId,
        "id"                : id,
        "name"              : name,
        "bk1"               : sex
    };
    $.ajax({
        type:'Post',
        url:'/management/user/addUser',
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
    var id = $("#id").textbox('getValue');
    var name = $("#name").textbox('getValue');
    var formData = {
        "organizationId"    : organizationId,
        "id"                : id,
        "name"              : name
    };
    loadData(15,'POST',formData);
}
/**
 * 修改
 */
function editInfo(){
    var organizationId = $("#edit_OrganizationId").combotree('getValue');
    var id = $("#edit_id").textbox('getValue');
    var name = $("#edit_name").textbox('getValue');
    var sex = $("#edit_sex").combobox('getValue');

    if(organizationId == "") {
        $.messager.alert("操作提示","请选择所在部门！","error");
        return;
    }
    if(id == ""){
        $.messager.alert("操作提示","工号不能为空！","error");
        return;
    }
    if(name == ""){
        $.messager.alert("操作提示","姓名不能为空！","error");
        return;
    }
    var formData = {
        "organizationId"    : organizationId,
        "id"                : id,
        "name"              : name,
        "bk1"               : sex
    };
    $.ajax({
        type:'Post',
        url:'/management/user/editUser',
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
        "id"  : id
    };
    $.ajax({
        type:'Post',
        url:'/management/user/deleteUser',
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

function getRole() {
    var row = $("#data").datagrid('getSelected');
    $("#cc").tree({
        url:'/management/user/getRole?id=' + row.id
    });
}

/**
 * 权限设置
 */
function confirmRole() {
    var row = $("#data").datagrid('getSelected');
    var nodes = $('#cc').tree('getChecked');
    var s = '';
    for(var i=0; i<nodes.length; i++){
        if (s != '') s += ',';
        s += nodes[i].id;
    }
    if(!row){
        $.messager.alert("操作提示","没有选中数据","error");
        return;
    }
    $.ajax({
        type:'post',
        url:'/management/user/confirmRole',
        data:'ids=' + s + "&id=" + row.id,
        dataType:'json',
        success:function(res){
            if(res.status == 0){
                $.messager.alert("提示",res.msg,"info",function(){
                    // $("#data").datagrid('reload');
                    $("#w_role").window("close");
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

        $("#edit_OrganizationId").combotree('setValue',row.organizationId);
        $("#edit_id").textbox('setValue',row.id);
        $("#edit_name").textbox('setValue',row.name);
        if(row.bk1 == '男'){
            $("#edit_sex").combobox('setValue','0');
        }else{
            $("#edit_sex").combobox('setValue','1');
        }
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
    role : function (){
        var row = $("#data").datagrid('getSelected');
        if(!row){
            $.messager.alert("操作提示","没有选中数据","error");
            return;
        }
        getRole();
        $("#w_role").window("open");
    }
}


$(function(){
    loadData();
});