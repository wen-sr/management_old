$(function () {
    // getData();
    setInterval("GetTime()", 1000);
    $('body').everyTime('8s', function() {
        switchWindow();
    });
    // $('body').everyTime('6hs', function() {
    //     getData();
    // });
});


function switchWindow() {
    var oneStatus= $("#one").is(":visible");
    var twoStatus= $("#two").is(":visible");
    var threeStatus= $("#three").is(":visible");
    var fourStatus= $("#four").is(":visible");
    var fiveStatus= $("#five").is(":visible");
    if(oneStatus == true){
        two();
        return;
    }
    if(twoStatus == true){
        three();
        return;
    }
    if(threeStatus == true){
        four();
        return;
    }
    if(fourStatus == true){
        five();
        return;
    }
    if(fiveStatus == true){
        one();
        return;
    }

}

function one(){
    var html = $("#one").html();
    if(html == ''){
        // two();
        return;
    }
    $("#one").show();
    $("#two").hide();
    $("#three").hide();
    $("#four").hide();
    $("#five").hide();
}

function two() {
    var html = $("#two").html();
    if(html == ""){
        three();
        return;
    }
    $("#two").show();
    $("#one").hide();
    $("#three").hide();
    $("#four").hide();
    $("#five").hide();
}

function three() {
    var html = $("#three").html();
    if(html == ''){
        four();
        return;
    }
    $("#three").show();
    $("#four").hide();
    $("#two").hide();
    $("#one").hide();
    $("#five").hide();
}

function four() {
    var html = $("#four").html();
    if(html == ''){
        five();
        return;
    }
    $("#four").show();
    $("#two").hide();
    $("#one").hide();
    $("#three").hide();
    $("#five").hide();
}

function five() {
    var html = $("#five").html();
    if(html == ''){
        one();
        return;
    }
    $("#five").show();
    $("#four").hide();
    $("#two").hide();
    $("#one").hide();
    $("#three").hide();
}

function getData(){
    $.ajax({
        type:"POST",
        url:"/project/led/tuopan4",
        success:function (res) {
            if(res.data == null) {
                return;
            }
            $("#one").html("");
            $("#two").html("");
            $("#three").html("");
            $("#four").html("");
            var t1 = "<table id='t1' cellpadding='0' cellspacing='0' style='font-size: 18px;width: 100%;font-weight:normal;border-spacing:0px 1.5px;'><thead><tr><td>作业类型</td><td>应完成</td><td>已完成</td><td>未完成</td><td>完成率</td></tr></thead>" +
                "<tbody>";
            var incomplete_ZanCunRK = res.data.incomplete_ZanCunRK == null ? 0 : res.data.incomplete_ZanCunRK;
            var complete_ZanCunRK = res.data.complete_ZanCunRK == null ? 0 : res.data.complete_ZanCunRK;
            var totalToday_ZanCunRK = incomplete_ZanCunRK + complete_ZanCunRK;



            var complete_ShangJiaRK_t = res.data.complete_ShangJiaRK.countQty == null ? 0 : res.data.complete_ShangJiaRK.countQty;
            var complete_ShangJiaRK_c = res.data.complete_ShangJiaRK.sumQty == null ? 0 : res.data.complete_ShangJiaRK.sumQty;
            var incomplete_ShangJiaRK_t = res.data.incomplete_ShangJiaRK.countQty == null ? 0 : res.data.incomplete_ShangJiaRK.countQty;
            var incomplete_ShangJiaRK_c = res.data.incomplete_ShangJiaRK.sumQty == null ? 0 : res.data.incomplete_ShangJiaRK.sumQty;
            var totalToday_ShangJiaRK_t = complete_ShangJiaRK_t + incomplete_ShangJiaRK_t;
            var totalToday_ShangJiaRK_c = complete_ShangJiaRK_c + incomplete_ShangJiaRK_c;


            var totalToday_case_t = res.data.totalToday_case.countQty == null ? 0 : res.data.totalToday_case.countQty;
            var totalToday_case_c = res.data.totalToday_case.sumQty == null ? 0 : res.data.totalToday_case.sumQty;
            var complete_case_t = res.data.complete_case.countQty == null ? 0 : res.data.complete_case.countQty ;
            var complete_case_c = res.data.complete_case.sumQty == null ? 0 : res.data.complete_case.sumQty;



            var complete_PK_t = res.data.complete_PK.countQty == null ? 0 : res.data.complete_PK.countQty;
            var complete_PK_c = res.data.complete_PK.sumQty == null ? 0 : res.data.complete_PK.sumQty;
            var incomplete_PK_t = res.data.incomplete_PK.countQty == null ? 0 : res.data.incomplete_PK.countQty;
            var incomplete_PK_c = res.data.incomplete_PK.sumQty == null ? 0 : res.data.incomplete_PK.sumQty;
            var totalToday_PK_t = complete_PK_t + incomplete_PK_t;
            var totalToday_PK_c = complete_PK_c + incomplete_PK_c;

            var complete_ZanCunCK = res.data.complete_ZanCunCK == null ? 0 : res.data.complete_ZanCunCK;
            var incomplete_ZanCunCK = res.data.incomplete_ZanCunCK == null ? 0 : res.data.incomplete_ZanCunCK;
            var totalToday_ZanCunCK = complete_ZanCunCK + incomplete_ZanCunCK;

            var complete_BuHuo_t = res.data.complete_BuHuo.countQty == null ? 0 : res.data.complete_BuHuo.countQty;
            var complete_BuHuo_c = res.data.complete_BuHuo.sumQty == null ? 0 : res.data.complete_BuHuo.sumQty;
            var incomplete_BuHuo_t = res.data.incomplete_BuHuo.countQty == null ? 0 : res.data.incomplete_BuHuo.countQty;
            var incomplete_BuHuo_c = res.data.incomplete_BuHuo.sumQty == null ? 0 : res.data.incomplete_BuHuo.sumQty;
            var totaltoday_BuHuo_t = complete_BuHuo_t + incomplete_BuHuo_t;
            var totalToday_BuHuo_c = complete_BuHuo_c + incomplete_BuHuo_c;


            var r_1 = isNaN(Math.round(parseFloat(complete_ZanCunRK)/parseFloat(totalToday_ZanCunRK)*100))?100:Math.round(parseFloat(complete_ZanCunRK)/parseFloat(totalToday_ZanCunRK)*100);
            var r_2 = isNaN(Math.round(parseFloat(complete_ShangJiaRK_c)/parseFloat(totalToday_ShangJiaRK_c)*100))?100:Math.round(parseFloat(complete_ShangJiaRK_c)/parseFloat(totalToday_ShangJiaRK_c)*100);
            var r_3 = isNaN(Math.round(parseFloat(complete_case_c)/parseFloat(totalToday_case_c)*100))?100:Math.round(parseFloat(complete_case_c)/parseFloat(totalToday_case_c)*100);
            var r_4 = isNaN(Math.round(parseFloat(complete_PK_c)/parseFloat(totalToday_PK_c)*100))?100:Math.round(parseFloat(complete_PK_c)/parseFloat(totalToday_PK_c)*100);
            var r_5 = isNaN(Math.round(parseFloat(complete_ZanCunCK)/parseFloat(totalToday_ZanCunCK)*100))?100:Math.round(parseFloat(complete_ZanCunCK)/parseFloat(totalToday_ZanCunCK)*100);
            var r_6 = isNaN(Math.round(parseFloat(complete_BuHuo_c)/parseFloat(totalToday_BuHuo_c)*100))?100:Math.round(parseFloat(complete_BuHuo_c)/parseFloat(totalToday_BuHuo_c)*100);

            t1 += "<tr><td>暂存入库</td><td>"+ totalToday_ZanCunRK +"托</td><td>"+ complete_ZanCunRK +"托</td><td>"+ (parseInt(totalToday_ZanCunRK)-parseInt(complete_ZanCunRK)) +"托</td><td>"+ r_1 +"%</td></tr>"
            t1 += "<tr><td>上架入库</td><td>"+ totalToday_ShangJiaRK_t +"托/" + totalToday_ShangJiaRK_c  +"册</td><td>"+ complete_ShangJiaRK_t +"托/" + complete_ShangJiaRK_c +"册</td><td>"+ (parseInt(totalToday_ShangJiaRK_c)-parseInt(complete_ShangJiaRK_c)) +"册</td><td>"+ r_2  +"%</td></tr>"
            t1 += "<tr><td>暂不发入库</td><td>"+ totalToday_case_t +"托/" + totalToday_case_c  +"件</td><td>"+ complete_case_t +"托/" + complete_case_c +"件</td><td>"+ (parseInt(totalToday_case_c)-parseInt(complete_case_c)) +"册</td><td>"+ r_3  +"%</td></tr>"
            t1 += "<tr><td>拣货</td><td>"+ totalToday_PK_t +"托/" + totalToday_PK_c  +"册</td><td>"+ complete_PK_t +"托/" + complete_PK_c +"册</td><td>"+ (parseInt(totalToday_PK_c)-parseInt(complete_PK_c)) +"册</td><td>"+ r_4 +"%</td></tr>"
            t1 += "<tr><td>暂存出库</td><td>"+ totalToday_ZanCunCK +"托</td><td>"+ complete_ZanCunCK +"托</td><td>"+ (parseInt(totalToday_ZanCunCK)-parseInt(complete_ZanCunCK)) +"托</td><td>"+ r_5  +"%</td></tr>"
            t1 += "<tr><td>补货</td><td>"+ totaltoday_BuHuo_t +"托/" + totalToday_BuHuo_c  +"册</td><td>"+ complete_BuHuo_t +"托/" + complete_BuHuo_c +"册</td><td>"+ incomplete_BuHuo_t +"托/" + incomplete_BuHuo_c +"册</td><td>"+ r_6  +"%</td></tr>"

            t1 += "</tbody></table>";
            $("#one").html(t1);
        },
        error:function(){
            alert("数据错误，联系管理员");
        }
    });
}



//获取时间并设置格式
function GetTime() {
    var mon, day, now, hour, min, ampm, time, str, tz, end, beg, sec;
    /*
    mon = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
            "Sep", "Oct", "Nov", "Dec");
    */
    mon = new Array("一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月",
        "九月", "十月", "十一月", "十二月");
    /*
    day = new Array("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
    */
    day = new Array("周日", "周一", "周二", "周三", "周四", "周五", "周六");
    now = new Date();
    hour = now.getHours();
    min = now.getMinutes();
    sec = now.getSeconds();
    if (hour < 10) {
        hour = "0" + hour;
    }
    if (min < 10) {
        min = "0" + min;
    }
    if (sec < 10) {
        sec = "0" + sec;
    }
    $("#Timer").html(
        now.getFullYear() + "年" + (now.getMonth() + 1) + "月" + now.getDate() + "日" + "  " + hour + ":" + min + ":" + sec
    );
}

