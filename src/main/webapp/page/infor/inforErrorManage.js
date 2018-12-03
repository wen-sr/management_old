/**
 * 加载数据
 */
function loadData(pageSize, method, formData){
    $("#data").datagrid({
        url:'/management/infor/findAll',
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
            field:"type",
            title:"类型",
            width:50
        },{
            field:"imageUrl",
            title:"图片",
            width:50,
            formatter:formatFtpUrl
        },{
            field:"addwhoName",
            title:"报修者",
            width:50
        },{
            field:"organizationName",
            title:"所在部门",
            width:50
        },{
            field:"description",
            title:"描述",
            width:50
        },{
            field:"bk1",
            title:"状态",
            formatter:formatStatus,
            width:50,
            sortable:true,
            sorter:sorterNum
        },{
            field:"processWhoName",
            title:"处理者",
            width:50
        },{
            field:"cause",
            title:"故障原因",
            width:50
        },{
            field:"result",
            title:"处理结果",
            width:50
        },{
            field:"adddate",
            title:"报修时间",
            width:50
        },{
            field:"processDate",
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
    if (val == "处理中"){
        return '<span style="color:red;">'+val+'</span>';
    }else if(val == "已处理"){
        return '<span style="color:green;">'+val+'</span>';
    }
    return val;
}

/**
 * 格式化字段
 * @param val
 * @param row
 * @param index
 * @returns {*}
 */
function formatFtpUrl(val,row,index){
    return '<a href="ftp://' + val + '" target="_blank">截图</a>';
    // return '<a href="javascript: void(0)" onclick=downloadImage("'+ val +'")>截图</a>';
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
}
/**
 * 查询
 */
function queryInfo(){
    var type = $.trim($("#type").combobox('getValue'));
    var status = $.trim($("#status").combobox('getValue'));
    var organizationId = $.trim($("#organizationId").combotree('getValue'));

    var formData = {
        "type"              : type,
        "bk1"               : status,
        "organizationId"    : organizationId
    };
    loadData(15,'POST',formData);
}

/**
 *
 * @param imageUrl
 */
// function downloadImage(imageUrl){
//     $("#img").attr("src",imageUrl);
//     $('#dlg').window("open");
// }

/**
 * 修改
 */
function editInfo(flag){
    var id = $("#edit_id").val();
    var description = $("#edit_description").textbox('getValue');
    var cause = $.trim($("#edit_cause").textbox('getValue'));
    var result = $.trim($("#edit_result").textbox('getValue'));
    var status = $.trim($("#edit_status").combobox('getValue'));

    if(id == ''){
        $.messager.alert("操作提示","要修改的记录id不存在！","error");
        return;
    }

    if(flag == '4'){
        if(id == ''){
            $.messager.alert("操作提示","要修改的记录id不存在！","error");
            return;
        }
        if(cause == ''){
            $.messager.alert("操作提示","故障原因不能为空！","error");
            return;
        }
        if(result == ''){
            $.messager.alert("操作提示","处理结果不能为空！","error");
            return;
        }
        if(status == '0'){
            $.messager.alert("操作提示","需要将处理状态修改为【已处理】才能提交！","error");
            return;
        }
    }
    if(flag == '3'){
        if(id == ''){
            $.messager.alert("操作提示","要修改的记录id不存在！","error");
            return;
        }
        if(cause == ''){
            $.messager.alert("操作提示","故障原因不能为空！","error");
            return;
        }
        if(status == '1'){
            $.messager.alert("操作提示","处理完成请点击【处理完成】提交！","error");
            return;
        }
    }

    var formData = {
        "id"            : id,
        "cause"         : cause,
        "description"   : description,
        "bk1"           : status,
        "result"        : result
    };
    $.ajax({
        type:'Post',
        url:'/management/infor/edit',
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

function removeInfo() {
    var row = $('#data').datagrid('getSelected');
    $.ajax({
        type:'Post',
        url:'/management/infor/delete',
        data:'id=' + row.id,
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
        if(row.bk1 == "已处理" ){
            $.messager.alert("操作提示","已处理的记录不能修改","error");
            return;
        }

        $("#edit_id").val('');
        $("#edit_description").textbox('setValue', '');
        $("#edit_cause").textbox('setValue', '');
        $("#edit_result").textbox('setValue', '');

        $("#edit_id").val(row.id);
        $("#edit_description").textbox('setValue', row.description);
        $("#edit_cause").textbox('setValue', row.cause);
        $("#edit_result").textbox('setValue', row.result);
        if(row.bk1 == '处理中'){
            $("#edit_status").combobox('setValue', '0');
        }else if(row.bk1 == '已处理' ){
            $("#edit_status").combobox('setValue', '1');
        }

        $("#w-editInfo").window("open");
    },
    remove : function(){
        var row = $('#data').datagrid('getSelected');
        if(!row || row.length == 0) {
            $.messager.alert("操作提示","没有选中记录","error");
            return;
        }
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

    // var body_height = $(document.body).height();//浏览器当前窗口文档body的高度
    // $("#d_data").css("height", (body_height - 50) + "px" );
    loadData();
});



