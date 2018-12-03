/**
 * 加载数据
 */
function loadData(method, formData){
    $("#data").treegrid({
        url: '/management/organization/findTree?id=' + (formData=='undefined'?'0': formData),
        method: method || 'get',
        fitColumns:true,
        lines: true,
        fit:true,
        rownumbers: true,
        idField: 'id',
        treeField: 'name',
        toolbar:'#tb'
    });
}

/**
 * 添加
 */
function addInfo(){
    var fooId   = $("#add_fooId").combotree('getValue');
    var name      = $("#add_name").textbox('getValue');
    if(name == ""){
        $.messager.alert("操作提示","部门名称不能为空！","error");
        return;
    }
    var formData = {
        "fooId"    : fooId,
        "name"     : name
    };
    $.ajax({
        type:'Post',
        url:'/management/organization/add',
        data:formData,
        dataType:'json',
        success:function(res){
            if(res.status == 0){
                $.messager.alert("提示",res.msg,"info",function(){
                    $("#data").treegrid('reload');
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

    var id = $("#id").combotreegrid('getValue');
    // var formData = {
    //     "id"    : id
    // };
    loadData('POST',id);
}
/**
 * 修改
 */
function editInfo(){
    var id   = $("#edit_fooId").combotree('getValue');
    var name      = $("#edit_name").textbox('getValue');
    if(name == ""){
        $.messager.alert("操作提示","部门名称不能为空！","error");
        return;
    }
    var formData = {
        "id"    : id,
        "name"     : name
    };
    $.ajax({
        type:'Post',
        url:'/management/organization/edit',
        data:formData,
        dataType:'json',
        success:function(res){
            if(res.status == 0){
                $.messager.alert("提示",res.msg,"info",function(){
                    $("#data").treegrid('reload');
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
    var row = $('#data').treegrid('getSelected');
    var id = row.id;
    var formData = {
        "id"                : id
    };
    $.ajax({
        type:'Post',
        url:'/management/organization/delete',
        data:formData,
        dataType:'json',
        success:function(res){
            if(res.status == 0){
                $.messager.alert("提示",res.msg,"info",function(){
                    $("#data").treegrid('reload');
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
        var row = $('#data').treegrid('getSelected');
        if(!row){
            $.messager.alert("操作提示","请选择需要添加的组织架构的父节点","error");
        }
        $("#add_name").textbox('setValue','');
        $('#add_fooId').combotree('setValue', row.id);
        $("#w-addInfo").window("open");
    },
    edit : function (){
        var row = $('#data').treegrid('getSelected');
        if(!row || row.length == 0) {
            $.messager.alert("操作提示","没有选中记录","error");
            return;
        }
        $('#edit_fooId').combotree('setValue', row.id);
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
    }
}


$(function(){
    loadData('GET','0');
});