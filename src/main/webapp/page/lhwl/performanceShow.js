$(function(){
    $.ajax({
        type:'Post',
        url:'/management/performance/getData',
        dataType:'json',
        success:function(res){
            if(res.status == 0){
                $("#dg").datagrid({
                    data : res.data
                })
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


function loadData(formData){
    $("#dg").datagrid({
        url:'/management/performance/getData',
        height:'auto',
        method: method || 'GET',
        queryParams:formData || '',
        fitColumns: true,
        fit:true,
        striped:true,
        rownumbers:true,
        border:true,
        singleSelect:true,
        pagination:false,
        showFooter: true,
        columns:[[{
            field:"",
            title:"编号",
            checkbox:true,
            width:50
        },{
            field:"dd",
            title:"发薪月份",
            width:50
        },{
            field:"name",
            title:"姓名",
            width:50,
            formatter:formatBase64
        },{
            field:"postbonus",
            title:"岗位工资",
            width:50
        },{
            field:"yearsbonus",
            title:"年功工资",
            width:50
        },{
            field:"overtimebonus",
            title:"加班",
            width:50
        },{
            field:"performancebonus",
            title:"绩效",
            formatter:formatStatus,
            width:50,
        },{
            field:"othersaddbonus",
            title:"其他（增项）",
            width:50
        },{
            field:"totaladdbonus",
            title:"应发合计",
            width:50
        },{
            field:"socialsecurity",
            title:"保险",
            width:50
        },{
            field:"medicare",
            title:"医疗险",
            width:50
        },{
            field:"unemploymentinsurance",
            title:"失业险",
            width:50
        },{
            field:"providentfund",
            title:"公积金",
            width:50
        },{
            field:"membershipfee",
            title:"会费",
            width:50
        },{
            field:"hydropower",
            title:"水电费",
            width:50
        },{
            field:"error",
            title:"差错",
            width:50
        },{
            field:"absence",
            title:"缺勤",
            width:50
        },{
            field:"othersminusbonus",
            title:"其他（减项）",
            width:50
        },{
            field:"tax",
            title:"扣税",
            width:50
        },{
            field:"totalminusbonus",
            title:"扣款合计",
            width:50
        },{
            field:"realbonus",
            title:"实发合计",
            width:50
        }]]
    });

}

