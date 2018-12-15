/**
 * 加载数据
 */
function loadData(pageSize, method, formData){
    $("#data").datagrid({
        url:'/management/TaskQuery',
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
            field:"tasktype",
            title:"任务类型",
            width:50
        },{
            field:"contNo",
            title:"容器号",
            width:50
        },{
            field:"createDate",
            title:"任务生成时间",
            width:50,
        },{
            field:"sendcode",
            title:"发送状态",
            width:50,
            formatter:formatSendcode
        },{
            field:"sendtime",
            title:"发送时间",
            width:50
        },{
            field:"zt",
            title:"目前状态",
            width:50,
            formatter:formatZt
        },{
            field:"retcode",
            title:"回告代码",
            width:50
        },{
            field:"rettime",
            title:"回告时间",
            width:50
        }]]

    });
}
function formatZt(val,row,index){
    if (val == "1"){
        return '<span style="color:green;">已回告</span>';
    } else if(val == "0"){
        return '<span style="color:red;">未回告</span>';
    }else {
        return val;
    }
}
function formatSendcode(val,row,index){
    if (val == "1"){
        return '<span style="color:green;">发送成功</span>';
    } else if(val == "2"){
        return '<span style="color:red;">发送失败</span>';
    } else if(val == "0"){
        return '<span style="color:blue;">未发送</span>';
    }else {
        return val;
    }
}
function queryInfo() {
    var createDate = $("#createDate").datebox('getValue');
    var contNo = $("#contNo").textbox('getValue');
    var zt = $("#zt").combobox('getValue');
    if(createDate == ''){
        createDate = undefined;
    }
    if(contNo == ''){
        contNo = undefined;
    }
    var formData = {
        createDate  : createDate,
        contNo      : contNo,
        zt          : zt
    };
    loadData(1,'POST', formData);
}

$(function(){
    loadData(1, 'POST', {zt : "0"});
});