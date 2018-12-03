function getUserId(resolve, reject) {
    _util.request({
        url     : _util.getServerUrl("/device/getDeviceUsers"),
        method  : "POST",
        success : resolve,
        error   : reject
    })
}

