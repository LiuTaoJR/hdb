(window.webpackJsonp=window.webpackJsonp||[]).push([["chunk-e6598d68"],{"480c":function(t,i,e){"use strict";e("b4c8")},b4c8:function(t,i,e){},cde1:function(t,i,e){"use strict";e.r(i);var a={name:"equipmentInfo",data:function(){return{activeNames:["1","2"],chartsOption:{title:{text:"速度曲线",top:"5%",left:"0.4%"},xAxis:{type:"category",data:["Mon","Tue","Wed","Thu","Fri","Sat","Sun"]},yAxis:{type:"value"},series:[{data:[820,932,901,934,1290,1330,1320],type:"line",smooth:!0}]},pickerOptions:{shortcuts:[{text:"最近一周",onClick:function(t){var i=new Date,e=new Date;e.setTime(e.getTime()-6048e5),t.$emit("pick",[e,i])}},{text:"最近一个月",onClick:function(t){var i=new Date,e=new Date;e.setTime(e.getTime()-2592e6),t.$emit("pick",[e,i])}},{text:"最近三个月",onClick:function(t){var i=new Date,e=new Date;e.setTime(e.getTime()-7776e6),t.$emit("pick",[e,i])}}]},dateData:[new Date(new Date((new Date).toLocaleDateString()).getTime()),new Date]}},mounted:function(){},methods:{selectInfo:function(){console.log(this.dateData)},loadCharts:function(){this.$echarts.init(document.getElementById("lineChart")).setOption(this.chartsOption)},toJobList:function(){this.$router.push("/jobList")}}},s=(e("480c"),e("2877")),v=Object(s.a)(a,(function(){var t=this,i=t.$createElement,e=t._self._c||i;return e("div",{ref:"deviceWrap",staticClass:"device-wrap"},[e("el-card",{staticClass:"page-header-card",attrs:{shadow:"hover"}},[e("b",[t._v("设备名称："+t._s(t.$route.params.equipmentName))]),t._v(" "),e("div",[e("el-date-picker",{attrs:{type:"datetimerange","picker-options":t.pickerOptions,"range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期",align:"right"},model:{value:t.dateData,callback:function(i){t.dateData=i},expression:"dateData"}}),t._v(" "),e("el-button",{attrs:{type:"primary"},on:{click:t.selectInfo}},[t._v("查询")])],1)]),t._v(" "),e("el-card",{staticClass:"machine-bc",staticStyle:{"margin-bottom":"20px"}}),t._v(" "),e("el-collapse",{model:{value:t.activeNames,callback:function(i){t.activeNames=i},expression:"activeNames"}},[e("el-collapse-item",{attrs:{title:"印刷时间",name:"1"}},[e("el-card",[e("div",{staticClass:"progress-historgrom"},[e("div",{staticClass:"printing-time",staticStyle:{width:"12%"}},[e("p",[t._v("12%")])]),t._v(" "),e("div",{staticClass:"ready-time",staticStyle:{width:"9%"}},[e("p",[t._v("9%")])]),t._v(" "),e("div",{staticClass:"undefined-time",staticStyle:{width:"79%"}},[e("p",[t._v("79%")])])]),t._v(" "),e("div",{staticClass:"info-item"},[e("div",{staticStyle:{"font-weight":"bold"}},[t._v("总开机时间")]),t._v(" "),e("div",[t._v("5714时")]),t._v(" "),e("div",[t._v("100%")])]),t._v(" "),e("div",{staticClass:"info-item"},[e("div",[e("div",{staticClass:"color-div",staticStyle:{"background-color":"rgb(0, 115, 255)"}}),t._v(" "),e("p",[t._v("印刷时间")])]),t._v(" "),e("div",[t._v("694时34分")]),t._v(" "),e("div",[t._v("12%")])]),t._v(" "),e("div",{staticClass:"info-item"},[e("div",[e("div",{staticClass:"color-div",staticStyle:{"background-color":"orange"}}),t._v(" "),e("p",[t._v("准备时间")])]),t._v(" "),e("div",[t._v("482时30分")]),t._v(" "),e("div",[t._v("9%")])]),t._v(" "),e("div",{staticClass:"info-item"},[e("div",[e("div",{staticClass:"color-div",staticStyle:{"background-color":"gray"}}),t._v(" "),e("p",[t._v("未定义时间")])]),t._v(" "),e("div",[t._v("4537时15分")]),t._v(" "),e("div",[t._v("79%")])])])],1),t._v(" "),e("el-collapse-item",{attrs:{title:"印刷质量",name:"2"}},[e("el-card",[e("div",{staticClass:"progress-historgrom"},[e("div",{staticClass:"printing-time",staticStyle:{width:"78%"}},[e("p",[t._v("78%")])]),t._v(" "),e("div",{staticClass:"ready-time",staticStyle:{width:"24%","background-color":"orange"}},[e("p",[t._v("24%")])])]),t._v(" "),e("div",{staticClass:"info-item"},[e("div",{staticStyle:{"font-weight":"bold"}},[t._v("总印数")]),t._v(" "),e("div",[t._v("6744956")]),t._v(" "),e("div",[t._v("100%")])]),t._v(" "),e("div",{staticClass:"info-item"},[e("div",[e("div",{staticClass:"color-div",staticStyle:{"background-color":"rgb(0, 115, 255)"}}),t._v(" "),e("p",[t._v("良品量")])]),t._v(" "),e("div",[t._v("5100262")]),t._v(" "),e("div",[t._v("78%")])]),t._v(" "),e("div",{staticClass:"info-item"},[e("div",[e("div",{staticClass:"color-div",staticStyle:{"background-color":"orange"}}),t._v(" "),e("p",[t._v("废品量")])]),t._v(" "),e("div",[t._v("1644696")]),t._v(" "),e("div",[t._v("24%")])])])],1)],1),t._v(" "),e("el-card",[e("div",{staticClass:"item-tittle"},[t._v("活件列表")]),t._v(" "),e("div",{staticClass:"times-tittle"},[t._v("\n      印版更换次数：\n      "),e("p",[t._v("37")])]),t._v(" "),e("el-button",{staticStyle:{"margin-top":"10px"},attrs:{type:"primary"},on:{click:t.toJobList}},[t._v("查看活件列表")])],1)],1)}),[],!1,null,"e576b418",null);i.default=v.exports}}]);