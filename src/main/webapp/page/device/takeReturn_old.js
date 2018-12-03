/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-21 10:02
 */
(function () {
    var _this = this;

    this.getUserId(function(res){
        // var temp = Handlebars.compile($("#userIdOptions").html());
        // $("#userId").html(temp(res.data));
        var html = _util.renderHtml($("#userIdOptions").html(), res.data)
        $("#userId").html(html);
    },function (error) {
        alert("请求使用者信息失败");
    })

    $("#deviceId").keyup(function (e) {
        if(e.keyCode == 13){
            // _this.go();
            return;
        }
    });
})(jQuery)



function go() {
    var deviceId = $.trim($("#deviceId").val());
    var status = $("#status").val();
    var userId = $("#userId").val();

    var formData = {
        "deviceId"  : deviceId,
        "status"    : status,
        "userId"    : userId
    };
    var validateResult = formValidate(formData);
    if(!validateResult.status){
        alert(validateResult.msg);
        return;
    }
    this.submit(formData, function (res) {
        alert(res.msg);
        $("#deviceId").val("");
        $("#status").val("-1");
        $("#userId").val("-1");
    }, function (error) {
        alert(error);
    })

}

// 表单字段的验证
function formValidate (formData){
    var result = {
        status  : false,
        msg     : ''
    };
    if(formData.deviceId == "-1" || formData.deviceId == ""){
        result.msg = '设备编号不能为空';
        return result;
    }
    if(formData.status == "-1"){
        result.msg = '操作类型不能为空';
        return result;
    }
    // 通过验证，返回正确提示
    result.status   = true;
    result.msg      = '验证通过';
    return result;
}


function submit(formData, resolve, reject) {
    _util.request({
        url     : _util.getServerUrl("/device/takeReturn"),
        method  : "post",
        data    : formData,
        success : resolve,
        error   : reject
    })
}

function getUserId(resolve, reject) {
    _util.request({
        url     : _util.getServerUrl("/device/getDeviceUsers"),
        method  : "POST",
        success : resolve,
        error   : reject
    })
}