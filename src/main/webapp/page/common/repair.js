layui.config({
	base : "js/"
}).use(['form','layer','jquery','laypage','table','upload','layedit'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer
        ,layedit = layui.layedit
		laypage = layui.laypage,
		$ = layui.jquery,
		table = layui.table
        ,upload = layui.upload;
	    form.render("select", "user");
	    // var DEFAULTURL = "/oa/repair/needRepair";
        var DEFAULTURL = "/device/repair";
        var editIndex = layedit.build('LAY_demo_editor');

	 //    //多图片上传
    // upload.render({
    //     elem: '#test2'
    //     ,url: _util.getServerUrl(DEFAULTURL)
    //     ,multiple: true
    //     ,before: function(obj){
    //         //预读本地文件示例，不支持ie8
    //         obj.preview(function(index, file, result){
    //             $('#demo2').append('<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img">')
    //         });
    //     }
    //     ,done: function(res){
    //         //上传完毕
    //     }
    // });

    //页面层-自定义
    $("#add").click(function(){
        layer.open({
            type: 2,
            title:'新增报修单',
            area: ['700px', '450px'],
            fixed: false, //不固定
            maxmin: true,
            content: './page/common/inforErrorManage.html'
        //     type: 1,
        //     shade: false,
        //     title: false, //不显示标题
        //     closeBtn: 1,
        //     offset: '100px',
        //     content: $('#addForm'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
        //     cancel: function(){
        //         layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', {time: 5000, icon:6});
        //     }
        });

    });

    //多文件列表示例
    var demoListView = $('#demoList')
        ,uploadListIns = upload.render({
        elem: '#testList'
        ,url:  _util.getServerUrl(DEFAULTURL)
        ,accept: 'file'
        ,multiple: true
        ,auto: false
        ,bindAction: '#testListAction'
        ,choose: function(obj){
            var files = obj.pushFile(); //将每次选择的文件追加到文件队列
            //读取本地文件
            obj.preview(function(index, file, result){
                var tr = $(['<tr id="upload-'+ index +'">'
                    ,'<td>'+ file.name +'</td>'
                    ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
                    ,'<td>等待上传</td>'
                    ,'<td>'
                    ,'<button class="layui-btn layui-btn-mini demo-reload layui-hide">重传</button>'
                    ,'<button class="layui-btn layui-btn-mini layui-btn-danger demo-delete">删除</button>'
                    ,'</td>'
                    ,'</tr>'].join(''));

                //单个重传
                tr.find('.demo-reload').on('click', function(){
                    obj.upload(index, file);
                });

                //删除
                tr.find('.demo-delete').on('click', function(){
                    delete files[index]; //删除对应的文件
                    tr.remove();
                });

                demoListView.append(tr);
            });
        }
        ,done: function(res, index, upload){
            if(res.code == 0){ //上传成功
                var tr = demoListView.find('tr#upload-'+ index)
                    ,tds = tr.children();
                tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                tds.eq(3).html(''); //清空操作
                delete files[index]; //删除文件队列已经上传成功的文件
                return;
            }
            this.error(index, upload);
        }
        ,error: function(index, upload){
            var tr = demoListView.find('tr#upload-'+ index)
                ,tds = tr.children();
            tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
            tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
        }
    });


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