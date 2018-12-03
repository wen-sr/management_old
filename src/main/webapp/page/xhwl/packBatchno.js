/**
 * 加载数据
 */
function loadData(pageSize, method, formData){
    $("#data").datagrid({
        url:'/management/packBatchno',
        method: method || 'GET',
        queryParams:formData || '',
        height:'auto',
        fit:true,
        fitColumns: true,
        striped:true,
        rownumbers:true,
        border:true,
        singleSelect:true,
        // pagination:true,
        // pageSize: pageSize || 15,
        // pageList:[10,15,20,50,pageSize || 0],
        showFooter: true,
        toolbar:'#tb',
        columns:[[{
            field:"id",
            title:"编号",
            checkbox:true,
            width:50
        },{
            field:"dd",
            title:"日期",
            width:50
        },{
            field:"packUser",
            title:"工号 ",
            width:50,
        },{
            field:"usrName",
            title:"姓名",
            width:50
        },{
            field:"batchno",
            title:"批次号",
            width:50
        },{
            field:"caseqty",
            title:"件数",
            width:50
        }]],
        onLoadSuccess:function () {
            var rows = $('#data').datagrid("getRows");
            var sumCaseQty = 0;
            var count = rows.length;
            for(var i = 0; i < count ; i++){
                sumCaseQty += rows[i]["caseqty"];
            }
            $('#data').datagrid('appendRow', {"dd": "合计", "batchno": count, "caseqty": sumCaseQty });
        }

    });
}

function queryInfo() {
    var dd = $("#dd").datebox('getValue');
    var id = $("#id").textbox('getValue');
    if(dd == ''){
        dd = undefined;
    }
    if(id == ''){
        id = undefined;
    }
    var formData = {
        dd : dd,
        id : id
    }
    loadData(1,'POST', formData);
}

$(function(){
    loadData();
});