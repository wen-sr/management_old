// 1.定义一个用于更新时间的函数
function updateTime() {
    // 2.获取当前系统的时间
    var nowTime = new Date();
    // 3.解析时分秒
    // 获取小时数
    var hour = nowTime.getHours();
    // 获取分钟数
    var minute = nowTime.getMinutes();
    // 获取秒数
    var second = nowTime.getSeconds();
    // 6.对小时，分钟，秒进行判断是否
    // 是两位数，如果是一位数，修改为两位数。
    // if(hour < 10){
    //     // 是一位数，在该数字前添加一个0。
    //     hour = '0' + hour;
    // }
    // else{
    //     // 是两位数，不用修改
    //     hour = hour;
    // }
    // 替换if和else，的简化写法
    // hour < 10 ? ：表示判断条件
    // ':'前边的 '0'+ hour ： 是一种结果（条件成立）
    // ':'后边的 hour ：也是一种结果（条件不成立时使用）
    hour = hour < 10 ? '0' + hour : hour;
    // if(minute < 10){
    //     minute = '0' +minute;
    // }
    // else{
    //     minute = minute;
    // }
    // 替换如下：
    minute = minute < 10 ? '0' + minute : minute;
    // if(second < 10){
    //     second = '0' + second;
    // }
    // else{
    //     second = second;
    // }
    // 替换如下：
    second = second < 10 ? '0' + second : second;

    // 4.获取表示时分秒的div

    var time = hour + ':' + minute + ':' + second;
    // 7.解析年月日
    // 获取年份
    var year = nowTime.getFullYear();
    // 获取月份
    // month的值：0~11,0表示1月份，11表示12月份
    var month = nowTime.getMonth() + 1;
    // 获取日
    var day = nowTime.getDate();
    // 获取周
    // week:返回的值是0~6，0表示周日 6表示周六
    var week = nowTime.getDay();
    // 8.把week对应的数字，转化为汉字
    switch (week) {
        case 0: {
            week = '星期日';
        }
            break;
        case 1: {
            week = '星期一';
        }
            break;
        case 2: {
            week = '星期二';
        }
            break;
        case 3: {
            week = '星期三';
        }
            break;

        case 4: {
            week = '星期四';

        }
            break;
        case 5: {
            week = '星期五';

        }
            break;
        case 6: {
            week = '星期六';
        }
            break;
    }
    return  year + '/' + month + '/' + day + ' ' + time + ' ' + week;
}