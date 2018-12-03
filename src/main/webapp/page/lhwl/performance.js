$(function () {
    $('#btn').click(function(){
        if(checkData()){
            $('#form1').ajaxSubmit({
                url:'/management/performance/ajaxUploadPerformanceExcel',
                dataType: 'json',
                success: function(res){
                    if(res){
                        $.messager.alert("操作提示",res.msg,"info");
                        $("#upfile").filebox('clear');
                    }else{
                        errorMsg();
                    }
                },
                error: errorMsg
            });
            function errorMsg(){
                alert("导入excel出错！");
            }
        }
    });
})



function checkData(){
    var fileDir = $("#upfile").filebox('getValue');
    var suffix = fileDir.substr(fileDir.lastIndexOf("."));
    if("" == fileDir){
        alert("选择需要导入的Excel文件！");
        return false;
    }
    if(".xls" != suffix && ".xlsx" != suffix ){
        alert("选择Excel格式的文件导入！");
        return false;
    }
    return true;
}


