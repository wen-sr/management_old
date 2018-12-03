$(function(){
    if(!window.localStorage){
        alert("浏览器支持不支持localstorage，请升级浏览器版本");
        return false;
    }else{
        loadData();
    }
});

/**
 * 获取localStorage数据
 */
function loadData(){
    var data = JSON.parse(localStorage.getItem("data"));
    $("#data").datagrid({
        height:'auto',
        fit:false,
        fitColumns: false,
        striped:true,
        rownumbers:true,
        border:true,
        singleSelect:true,
        showFooter: true,
        data:data
        // frozenColumns:[[{
        //         field:"caseid",
        //         title:"包件号",
        //         width:100,
        //         align:'center'
        //     },{
        //         field:"shortname",
        //         title:"客户简称",
        //         width:100,
        //         align:'center'
        //     },
        // ]],
        // columns:[[{
        //     field:"shipkey",
        //     title:"运号",
        //     width:100,
        //     align:'center'
        // },{
        //     field:"storerkey",
        //     title:"货主 ",
        //     width:100,
        //     formatter:formatterStorerkey,
        //     align:'center'
        // },{
        //     field:"status",
        //     title:"状态",
        //     width:100,
        //     formatter:formatterStatus,
        //     align:'center'
        // },{
        //     field:"adddate",
        //     title:"包装日期",
        //     width:100,
        //     align:'center'
        // },{
        //     field:"caseintime",
        //     title:"交包日期",
        //     width:100,
        //     align:'center'
        // },{
        //     field:"loaddate",
        //     title:"装车日期",
        //     width:100,
        //     align:'center'
        // },{
        //     field:"shipdate",
        //     title:"发运日期",
        //     width:100,
        //     align:'center'
        // }]]frozenColumns:[[{
        //         field:"caseid",
        //         title:"包件号",
        //         width:100,
        //         align:'center'
        //     },{
        //         field:"shortname",
        //         title:"客户简称",
        //         width:100,
        //         align:'center'
        //     },
        // ]],
        // columns:[[{
        //     field:"shipkey",
        //     title:"运号",
        //     width:100,
        //     align:'center'
        // },{
        //     field:"storerkey",
        //     title:"货主 ",
        //     width:100,
        //     formatter:formatterStorerkey,
        //     align:'center'
        // },{
        //     field:"status",
        //     title:"状态",
        //     width:100,
        //     formatter:formatterStatus,
        //     align:'center'
        // },{
        //     field:"adddate",
        //     title:"包装日期",
        //     width:100,
        //     align:'center'
        // },{
        //     field:"caseintime",
        //     title:"交包日期",
        //     width:100,
        //     align:'center'
        // },{
        //     field:"loaddate",
        //     title:"装车日期",
        //     width:100,
        //     align:'center'
        // },{
        //     field:"shipdate",
        //     title:"发运日期",
        //     width:100,
        //     align:'center'
        // }]]
    });
    //下一次使用前清
    // localStorage.clear();
}

/**
 * 格式化状态
 * @param value
 * @param row
 * @param index
 * @returns {*}
 */
function formatterStatus(val,row,index){
    if (val == "11"){
        return '<span style="color:green;">发运</span>';
    } else if(val == "-1"){
        return '<span style="color:red;">包装完成</span>';
    }else if(val == "1"){
        return '<span style="color:blueviolet;">待发</span>';
    }else if(val == "2"){
        return '<span style="color:mediumvioletred;">待运</span>';
    }else if(val == "8"){
        return '<span style="color:mediumvioletred;">装车</span>';
    }else {
        return val;
    }
}

/**
 * 格式化货主
 * @param value
 * @param row
 * @param index
 * @returns {*}
 */
function formatterStorerkey(val,row,index){
    if (val == "H360016"){
        return '<span style="color:green;">一般图书</span>';
    } else if(val == "H360020"){
        return '<span style="color:red;">农家书屋</span>';
    }else if(val == "H360060"){
        return '<span style="color:blueviolet;">大中专</span>';
    }else {
        return val;
    }
}

