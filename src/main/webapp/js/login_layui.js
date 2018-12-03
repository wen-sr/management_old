layui.use(["form", "layer"], function () {
   var form = layui.form,
       layer = layui.layer;
       form.on('submit(login)', function (data) {
            login();
       });

});

function login(){
    var formData = {
        id : $.trim($("#id").val()),
        pwd : $.trim($("#password").val())
    }
    var validateResult = formValidate(formData);
    if(validateResult.status){
        formError.hide();
        submit(formData, function(res){
            window.location.href = './index.html';
        },function (errMsg) {
            formError.show(errMsg);
        })
    }else{
        formError.show(validateResult.msg);
    }
}

// 表单字段的验证
function formValidate (formData){
    var result = {
        status  : false,
        msg     : ''
    };
    if(!_util.validate(formData.id, 'require')){
        result.msg = '工号不能为空';
        return result;
    }
    if(!_util.validate(formData.pwd, 'require')){
        result.msg = '密码不能为空';
        return result;
    }
    // 通过验证，返回正确提示
    result.status   = true;
    result.msg      = '验证通过';
    return result;
}

// 表单里的错误提示
var formError = {
    show : function(errMsg){
        $('.error-msg').show().html(errMsg);
    },
    hide : function(){
        $('.error-msg').hide().text('');
    }
};

//submit
function submit (formData, resolve, reject ){
    _util.request({
        url     : _util.getServerUrl('/user/login'),
        data    : formData,
        method  : 'POST',
        success : resolve,
        error   : reject
    })
}