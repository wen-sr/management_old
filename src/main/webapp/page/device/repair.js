layui.config({
	base : "js/"
}).use(['form','layer','jquery','laypage','table'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		$ = layui.jquery,
		table = layui.table;

	    form.render("select", "user");
	    var DEFAULTURL = "/device/repair";

    function renderTable(requestData, url){
        table.render({
            elem : '#data',
            url 	: _util.getServerUrl(url || DEFAULTURL),
            where : requestData || '',
            // data : usersData.data,
            height  : 'full-45',
            cols:  [[
                {checkbox: true},
                {field: 'deviceId', title: '设备编号', width: 80, sort: true}
                // ,{field: 'deviceTypeId', title: '设备类型编号', width: 120, sort: true}
                ,{field: 'deviceTypeName', title: '设备类型', width: 90, sort: true}
                // ,{field: 'deviceUserId', title: '使用者编号', width: 120, sort: true}
                ,{field: 'deviceUserName', title: '送修者', width: 90, sort: true}
                ,{field: 'status', title: '状态', width: 80, sort: true}
                ,{field: 'cause', title: '故障原因', width: 200, sort: true, event: 'setCause', style:'cursor: pointer;'}
                ,{field: 'bakup', title: '备注', width: 120, sort: true, event: 'setBackup', style:'cursor: pointer;'}
                ,{field: 'adddate', title: '送修日期', width: 150, sort: true}
                ,{field: 'editdate', title: '完成日期', width: 150, sort: true}
                ,{fixed: 'right', width:90, align:'center', toolbar: '#bar'}
            ]]
            ,page: true //开启分页
            ,request: {
                pageName: 'pageNum', //页码的参数名称，默认：page
                limitName: 'pageSize' //每页数据量的参数名，默认：limit
            }
            ,limits: [15,20,100000000]
            ,limit: 15
            ,loading: true
            ,done: function (res, curr, count) {

            }
            ,even: true //开启隔行背景
            ,size: 'sm'
            ,jump: function (obj) {
                alert(obj);
            }
        });
    }

    renderTable();

    getUserId(function(res){
        var html = _util.renderHtml($("#userIdOptions").html(), res.data);
        $("#userId").html(html);
    },function (error) {
        alert("请求使用者信息失败");
    });

    $("#go").click(function () {
        var deviceId = $.trim($("#deviceId").val());
        var status = $("#status").val();
        var userId = $("#userId").val();

        var formData = {
            "deviceId"  : deviceId,
            "status"    : status,
            "deviceUserId"    : userId
        };
        renderTable(formData, DEFAULTURL);
    });


    table.on('tool(clickEvent)', function(obj){
        var data = obj.data;

        if(obj.event === 'setCause'){
            if(data.status == "已维修"){
                return;
            }
            layer.prompt({
                formType: 2
                ,title: '设备编号为 【'+ data.deviceId +'】 的故障原因'
                ,value: data.cause
            }, function(value, index){
                layer.close(index);
                //发送修改的Ajax请求
                var param = {
                    'id'    : data.id,
                    'cause' : value
                };
                $.ajax({
                   type     : 'post',
                   url      : _util.getServerUrl("/device/updateRepair"),
                   data     : param,
                   dataType : 'json',
                    success : function(res){
                        //同步更新表格和缓存对应的值
                        obj.update({
                            cause: value
                        });
                    },
                    error   : function(error){
                        alert("数据更新错误，强联系管理员");
                    }
                });
            });
        }

        else if(obj.event === 'setBackup'){
            layer.prompt({
                formType: 2
                ,title: '设备编号为 【'+ data.deviceId +'】 的备注'
                ,value: data.bakup
            }, function(value, index){
                layer.close(index);
                //发送修改的Ajax请求
                var param = {
                    'id'    : data.id,
                    'bakup' : value
                };
                $.ajax({
                    type     : 'post',
                    url      : _util.getServerUrl("/device/updateRepair"),
                    data     : param,
                    dataType : 'json',
                    success : function(res){
                        //同步更新表格和缓存对应的值
                        obj.update({
                            bakup: value
                        });
                    },
                    error   : function(error){
                        alert("数据更新错误，强联系管理员");
                    }

                });
            });
        }

        else if(obj.event === 'repairDone'){
            layer.confirm('设备编号 【' + data.deviceId +  '】 确定维修完成了么？', function(index){
                if(data.status == "已维修"){
                    layer.open({
                        title: '温馨提示'
                        ,content: '该设备已经是 【已维修】 状态'
                    });
                    layer.close(index);
                    return;
                }

                if(data.cause == "" || data.cause == undefined){
                    layer.open({
                        title: '温馨提示'
                        ,content: '故障原因尚未填写不允许提交'
                    });
                    layer.close(index);
                    return;
                }
                var param = {
                    'id'        : data.id,
                    'status'    : 5,
                    'deviceId'  : data.deviceId
                };
                $.ajax({
                    type     : 'post',
                    url      : _util.getServerUrl("/device/updateRepair"),
                    data     : param,
                    dataType : 'json',
                    success : function(res){
                        //同步更新表格和缓存对应的值
                        obj.update({
                            status: "已维修"
                        });
                    },
                    error   : function(error){
                        alert("数据更新错误，强联系管理员");
                    }

                });

                layer.close(index);
            });

        }



    });

});