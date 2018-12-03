/**
 * 加载数据
 */
function loadData(pageSize, method, formData){
    $("#data").datagrid({
        url:'/management/repair/findAll',
        height:'auto',
        method: method || 'GET',
        queryParams:formData || '',
        fitColumns: true,
        fit:true,
        striped:true,
        rownumbers:true,
        border:true,
        singleSelect:true,
        pagination:true,
        pageSize: pageSize || 15,
        pageList:[10,15,20,50,pageSize || 0],
        showFooter: true,
        // loadMsg:"请稍等。。。",
        toolbar:'#tb',
        columns:[[{
            field:"id",
            title:"编号",
            checkbox:true,
            width:50
        },{
            field:"deviceTypeName",
            title:"设备类型",
            width:50
        },{
            field:"deviceId",
            title:"设备编号",
            width:50,
            sortable:true,
            sorter:sorterNum
        },{
            field:"deviceUserName",
            title:"报修者",
            width:50
        },{
            field:"organizationName",
            title:"所在部门",
            width:50
        },{
            field:"questionDescription",
            title:"故障描述",
            width:50
        },{
            field:"status",
            title:"状态",
            formatter:formatStatus,
            width:50,
            sortable:true,
            sorter:sorterNum
        },{
            field:"repairUserId",
            title:"维修员",
            width:50
        },{
            field:"cause",
            title:"故障原因",
            width:50
        },{
            field:"component",
            title:"更换零部件",
            width:50
        },{
            field:"cost",
            title:"维修费用",
            width:50,
            editor:"text"
        },{
            field:"result",
            title:"维修结果",
            width:50
        },{
            field:"adddate",
            title:"送修时间",
            width:50
        },{
            field:"editdate",
            title:"完成时间",
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
            handler:function(){
                var d = $("#data").datagrid('getData');
                loadData(d.total);
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
    if (val == "维修中"){
        return '<span style="color:red;">'+val+'</span>';
    }else if(val == "已维修"){
        return '<span style="color:green;">'+val+'</span>';
    } else if(val == "已报废"){
        return '<span style="color:darkred;">'+val+'</span>';
    }{
        return val;
    }
}

/**
 * 排序
 * @param a
 * @param b
 * @returns {number}
 */
function sorterNum(a,b){
   return a>b?a:b;
}


/**
 * 添加
 */
function addInfo(){
    var deviceId = $.trim($("#add_deviceId").combobox('getValue'));
    if(deviceId == ""){
        $.messager.alert("操作提示","设备编号不能为空！","error");
        return;
    }
    var questionDescription = $.trim($("#add_questionDescription").textbox('getValue'));
    if(questionDescription == ""){
        $.messager.alert("操作提示","故障描述不能为空！","error");
        return;
    }
    var formData = {
        "deviceId"              : deviceId,
        "questionDescription"   : questionDescription
    };
    $.ajax({
        type:'Post',
        url:'/management/repair/addRepair',
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
        "organizationId"    : organizationId
    };
    loadData(15,'POST',formData);
}
/**
 * 修改
 */
function editInfo(status){
    var id = $("#edit_id").val();
    var deviceId = $("#edit_deviceId").textbox('getValue');
    var cause = $.trim($("#edit_cause").textbox('getValue'));
    var component = $.trim($("#edit_component").textbox('getValue'));
    var cost = $("#edit_cost").textbox('getValue');
    var result = $.trim($("#edit_result").textbox('getValue'));

    if(id == ''){
        $.messager.alert("操作提示","要修改的记录id不存在！","error");
        return;
    }

    if(status == 4){
        if(id == ''){
            $.messager.alert("操作提示","要修改的记录id不存在！","error");
            return;
        }
        if(cause == ''){
            $.messager.alert("操作提示","故障原因不能为空！","error");
            return;
        }
        if(component == ''){
            $.messager.alert("操作提示","更换零部件不能为空！","error");
            return;
        }
        if(cost == ''){
            $.messager.alert("操作提示","费用不能为空！","error");
            return;
        }
        if(result == ''){
            $.messager.alert("操作提示","维修结果不能为空！","error");
            return;
        }
    }
    if(status == 3){
        if(id == ''){
            $.messager.alert("操作提示","要修改的记录id不存在！","error");
            return;
        }
        if(cause == ''){
            $.messager.alert("操作提示","故障原因不能为空！","error");
            return;
        }
        if(result == ''){
            $.messager.alert("操作提示","维修结果不能为空！","error");
            return;
        }
    }
    var reg2 = new RegExp("^\\d+(\\.\\d+)?$");
    if(cost != '' && !reg2.test(cost)){
        $.messager.alert("操作提示","费用只能输入数字！","error");
        return;
    }

    var formData = {
        "id"            : id,
        "deviceId"      : deviceId,
        "cause"         : cause,
        "component"     : component,
        "cost"          : cost,
        "result"        : result,
        "status"        : status
    };
    $.ajax({
        type:'Post',
        url:'/management/repair/editRepair',
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
        if(row.status == "已维修" || row.status == "已报废" ){
            $.messager.alert("操作提示","已维修完成或已报废的记录不能修改","error");
            return;
        }

        $("#edit_id").val(row.id);
        $("#edit_deviceTypeId").combobox('setValue', row.deviceTypeId);
        $("#edit_deviceId").textbox('setValue', row.deviceId);
        $("#edit_cause").textbox('setValue', row.cause);
        $("#edit_component").textbox('setValue', row.component);
        $("#edit_cost").textbox('setValue', row.cost);
        $("#edit_result").textbox('setValue', row.result);

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
    }
}


$(function(){
    loadData();
});