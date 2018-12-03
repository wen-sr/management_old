layui.config({
	base : "js/"
}).use(['form','layer','jquery','laypage','table'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		$ = layui.jquery,
		table = layui.table;

	    form.render("select", "user");
    var DEFAULTURL = "/device/findAll";

    function renderTable(requestData, url){
        table.render({
            elem : '#data',
            url 	: _util.getServerUrl(url || DEFAULTURL),
            where : requestData || '',
            // data : usersData.data,
            height  : 'full-45',
            cols:  [[
                {checkbox: true},
                {field: 'deviceId', title: '设备编号', width: 120, sort: true}
                // ,{field: 'deviceTypeId', title: '设备类型编号', width: 120, sort: true}
                ,{field: 'deviceTypeName', title: '设备类型', width: 120, sort: true}
                // ,{field: 'deviceUserId', title: '使用者编号', width: 120, sort: true}
                ,{field: 'deviceUserName', title: '使用者名称', width: 120, sort: true}
                ,{field: 'status', title: '设备当前状态', width: 120, sort: true}
                ,{field: 'adddate', title: '添加日期', width: 180, sort: true}
                ,{field: 'editdate', title: '修改日期', width: 180, sort: true}

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
            "deviceId"          : deviceId,
            "status"            : status,
            "deviceUserId"      : userId
        };
        renderTable(formData, DEFAULTURL);
    });


});