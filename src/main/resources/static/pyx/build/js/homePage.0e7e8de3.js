(window.webpackJsonp=window.webpackJsonp||[]).push([["homePage"],{"02ed":function(e,t,a){"use strict";a.r(t);var i=a("c7eb"),n=a("ade3"),r=(a("96cf"),a("1da1")),o=a("5530"),c=(a("7f7f"),a("2909")),s=(a("20d6"),a("6b54"),a("a481"),a("ac6a"),a("7514"),a("d4ec")),l=a("bee2"),d=a("257e"),u=a("262e"),m=a("2caf"),h=a("a91b"),v=a("428c"),f=new(function(e){Object(u.a)(a,e);var t=Object(m.a)(a);function a(e){var i;return Object(s.a)(this,a),i=t.call(this,e),Object(n.a)(Object(d.a)(i),"getDetail",(function(e){var t={machineCode:e};return i.service(i.module.api.detail,{method:"post",data:t}).then((function(t){var a=t.data.list,i={};return Array.isArray(a)&&a.length&&(i=a.find((function(t){return t.machineCode==e}))),i}))})),Object(n.a)(Object(d.a)(i),"getDevicesByWorkDetails",(function(e,t){var a={workDetailIds:e};return i.service(i.module.api.getDevicesByWorkDetails,{method:"post",data:a}).then((function(e){var a={};return(e.data||[]).forEach((function(e){var i=t.find((function(t){return t.machineName==e.machineName}));i&&(e.machineCode=i.machineCode),a[e.workDetailId]=e})),a}))})),i}return Object(l.a)(a,[{key:"demo",value:function(e){return this.service(this.module.api.demo,{method:"post",data:e})}},{key:"getDevicesByType",value:function(e){var t={machineCategoryCode:e};return this.service(this.module.api.getDevicesByType,{method:"post",data:t})}}]),a}(h.a))(v.a),p=a("d2b8"),b=a("8975"),g={name:"PrintDevices",components:{},props:{list:{type:Array,default:function(){return[]}}},mixins:[],data:function(){return{}},computed:{},watch:{},created:function(){},filters:{hdbPrintDeviceEventFormat:b.b},methods:{}},D=a("2877"),w=Object(D.a)(g,(function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-table",{attrs:{data:e.list,stripe:"","highlight-current-row":""}},[a("el-table-column",{attrs:{label:"id",prop:"id","show-overflow-tooltip":""}}),e._v(" "),a("el-table-column",{attrs:{label:"创建时间",prop:"createTime","show-overflow-tooltip":""}}),e._v(" "),a("el-table-column",{attrs:{label:"运行时间",prop:"time","show-overflow-tooltip":""}}),e._v(" "),a("el-table-column",{attrs:{label:"设备运行操作","show-overflow-tooltip":""},scopedSlots:e._u([{key:"default",fn:function(t){var i=t.row;return[a("span",[e._v(e._s(e._f("hdbPrintDeviceEventFormat")(i.EventID)))])]}}])}),e._v(" "),a("el-table-column",{attrs:{label:"设备印刷速度",prop:"Speed","show-overflow-tooltip":""}}),e._v(" "),a("el-table-column",{attrs:{label:"状态",prop:"Status","show-overflow-tooltip":""}}),e._v(" "),a("el-table-column",{attrs:{label:"活件ID",prop:"JobID","show-overflow-tooltip":""}}),e._v(" "),a("el-table-column",{attrs:{label:"操作",width:"100","show-overflow-tooltip":""},scopedSlots:e._u([{key:"default",fn:function(t){var i=t.row;return[i.JobID?a("el-button",{attrs:{type:"primary",size:"mini"},on:{click:function(t){return e.$emit("check",i)}}},[e._v("查看")]):e._e()]}}])})],1)}),[],!1,null,"07dcc268",null).exports,y=a("766a");function _(e){var t=e.toString();return 1==t.length?"0"+t:t}function k(e){"string"==typeof e&&(e=e.replace(/-/g,"/"));var t=new Date(e);return{date:[t.getFullYear(),t.getMonth()+1,t.getDate()],time:[t.getHours(),t.getMinutes(),t.getSeconds()]}}a("358c");var x,S=a("bc3a"),O=a.n(S),T={name:"PrintStatus",components:{PrintDevices:w,Details:y.a},mixins:[],data:function(){return{formdata:{workDate:new Date},printingDevices:[],activeTab:"",currentDevice:{},findDeviceIdx:0,dataFlag:null,nowTime:"",commonDevice:"",machineName:"XL 162-4 (RS)"}},computed:{},watch:{},mounted:function(){this.nowTime=this.formatDateTime(new Date,"YYYY-MM-DD")},created:function(){this.getPrintingDevices()},filters:{},methods:{searchTotal:function(){this.nowTime,this.commonDevice,this.$router.push({path:"/jumpList",name:"jumpList",query:{date:this.nowTime,deviceId:this.commonDevice}})},activeTotal:function(){this.nowTime,this.commonDevice,this.machineName,this.$router.push({path:"/activeTotal",name:"activeTotal",query:{date:this.nowTime,deviceId:this.commonDevice,machineName:this.machineName}})},activeSelect:function(){this.$router.push({path:"/activeSelect",name:"activeSelect"})},dashbordInfo:function(){this.$router.push({path:"/dashbordInfo",name:"dashbordInfo"})},formatDateTime:function(e,t){if(e){"string"==typeof e&&(e=e.replace(/-/g,"/"));var a=(e=new Date(Date.parse(e))).getFullYear(),i=e.getMonth()+1;i=i<10?"0"+i:i;var n=e.getDate();n=n<10?"0"+n:n;var r=e.getHours();r=r<10?"0"+r:r;var o=e.getMinutes();o=o<10?"0"+o:o;var c=e.getSeconds();return c=c<10?"0"+c:c,t||(t="YYYY-MM-DD hh:mm:ss"),(t.indexOf("YYYY")>=0||t.indexOf("yyyy")>=0)&&(t=(t=t.replace(/YYYY/g,a)).replace(/yyyy/g,a)),t.indexOf("MM")>=0&&(t=t.replace(/MM/g,i)),(t.indexOf("DD")>=0||t.indexOf("dd")>=0)&&(t=(t=t.replace(/DD/g,n)).replace(/dd/g,n)),(t.indexOf("HH")>=0||t.indexOf("hh")>=0)&&(t=(t=t.replace(/hh/g,r)).replace(/HH/g,r)),t.indexOf("mm")>=0&&(t=t.replace(/mm/g,o)),(t.indexOf("SS")>=0||t.indexOf("ss")>=0)&&(t=(t=t.replace(/ss/g,c)).replace(/SS/g,c)),t}return""},beforeLeave:function(e){if("CustoBtn"==e)return!1},toPubPage:function(){this.$router.push("/equipmentList")},toWorkerPage:function(){this.$router.push("/workerList")},toNewPage:function(){this.$router.push("/equipmentPage")},getPrintingDevices:function(){var e=this;f.getDevicesByType("2").then((function(t){var a=t.data||[],i="";a.map((function(e){e.details=[],e.finished=!1,e.dataReady=!1,e.currentPage=1,e.recordCount=0,e.factoryId&&(i=e.factoryId.toString())})),e.printingDevices=a,e.activeTab=i,e.commonDevice=i}))},handlerChangeDate:function(){this.nowTime=this.formatDateTime(this.formdata.workDate,"YYYY-MM-DD")},getList:function(e){var t=this,a=this.printingDevices.findIndex((function(e){return e.factoryId==t.activeTab}));if(-1!=a){var i=this.printingDevices[a];if(e||(i.dataReady=!1,i.finished=!1,i.currentPage=1),!i.finished){var n=this.$loading({lock:!0,text:"数据拼命加载中...",spinner:"el-icon-loading",background:"rgba(0, 0, 0, 0.7)"});p.a.getPrintDeviceStatus(function(e,t){if(!e)return"未知";for(var a=[],i=void 0===t?"-":t,n=k(e),r=0;r<n.date.length;r++)a.push(_(n.date[r]));return a.join(i)}(this.formdata.workDate),this.activeTab,i.currentPage).then((function(a){t.dataFlag=a.data.SignalStatus,i.dataReady=!0;var n=a.data.SignalStatus||[],r=a.data.recordCount||0;i.details=e?[].concat(Object(c.a)(i.details),Object(c.a)(n)):n,i.currentPage+=1,i.recordCount=r,n.length||(i.finished=!0)})).finally((function(){n.close()}))}}},handlerChangeTab:function(e){this.machineName=e.label,this.commonDevice=e.name,this.findDeviceIdx=e.index},handlerCheck:function(e,t){this.currentDevice=Object(o.a)(Object(o.a)({},e),{},{machineName:t}),this.$refs.details.showDialog()},handleJump:(x=Object(r.a)(Object(i.a)().mark((function e(t){var a,r,o,c,s,l;return Object(i.a)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return console.log(t),r=t.EventID,o=t.time,c=t.deviceId,e.next=6,O.a.get("/statistics/getStatisticsDate?date=".concat(o,"&deviceId=").concat(c,"&eventId=").concat(r));case 6:s=e.sent,l=s.data,this.$router.push((a={name:"/jumpList"},Object(n.a)(a,"name","jumpList"),Object(n.a)(a,"params",l.data),a));case 9:case"end":return e.stop()}}),e,this)}))),function(e){return x.apply(this,arguments)}),handlerGetMore:function(){this.getList(!0)}}},I=(a("7cbf"),Object(D.a)(T,(function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{ref:"deviceWrap",staticClass:"device-wrap"},[a("el-card",{staticClass:"page-header-card",attrs:{shadow:"hover"}},[a("b",[e._v(e._s(e.$route.meta.name))]),e._v(" "),a("el-button",{on:{click:e.toNewPage}},[e._v("新页面")]),e._v(" "),a("el-form",{attrs:{inline:!0,size:"mini",model:e.formdata,"label-width":"80px"}},[a("el-form-item",{staticClass:"ares-mb-0",attrs:{label:"日期",prop:"workDate"}},[a("el-date-picker",{attrs:{type:"date",clearable:!1,editable:!1,placeholder:"选择日期"},on:{change:e.handlerChangeDate},model:{value:e.formdata.workDate,callback:function(t){e.$set(e.formdata,"workDate",t)},expression:"formdata.workDate"}})],1)],1)],1),e._v(" "),a("div",{staticClass:"i-total"},[a("p",{staticStyle:{width:"200px","margin-top":"5px"}},[e._v("deviceId: "+e._s(e.commonDevice))]),e._v(" "),a("el-button",{staticStyle:{"margin-bottom":"10px"},attrs:{size:"mini"},on:{click:function(t){return e.getList(!1)}}},[e._v("查询")]),e._v(" "),a("el-button",{staticStyle:{"margin-bottom":"10px"},attrs:{size:"mini"},on:{click:e.searchTotal}},[e._v("今日统计")]),e._v(" "),a("el-button",{staticStyle:{"margin-bottom":"10px"},attrs:{size:"mini"},on:{click:e.activeTotal}},[e._v("今日活件统计")]),e._v(" "),a("el-button",{staticStyle:{"margin-bottom":"10px"},attrs:{size:"mini"},on:{click:e.activeSelect}},[e._v("活件查询")]),e._v(" "),a("el-button",{staticStyle:{"margin-bottom":"10px"},attrs:{size:"mini"},on:{click:e.dashbordInfo}},[e._v("大屏数据查询")])],1),e._v(" "),a("el-card",{staticClass:"main-table-card"},[a("el-tabs",{attrs:{"before-leave":e.beforeLeave,type:"card"},on:{"tab-click":e.handlerChangeTab},model:{value:e.activeTab,callback:function(t){e.activeTab=t},expression:"activeTab"}},e._l(e.printingDevices,(function(t,i){return t.factoryId?a("el-tab-pane",{key:i,attrs:{name:t.factoryId.toString(),label:t.machineName}},[a("div",{attrs:{slot:"label"},slot:"label"},[t.recordCount>0?a("el-badge",{attrs:{value:t.recordCount}},[a("span",[e._v(e._s(t.machineName))])]):a("span",[e._v(e._s(t.machineName))])],1),e._v(" "),a("PrintDevices",{attrs:{list:t.details},on:{check:function(a){return e.handlerCheck(a,t.machineName)},jump:function(t){return e.handleJump(t)}}}),e._v(" "),a("div",{staticClass:"ares-text-center"},[!t.finished&&t.dataReady?a("el-button",{attrs:{type:"text"},on:{click:e.handlerGetMore}},[e._v("\n            ---查看更多---\n          ")]):e._e()],1)],1):e._e()})),1)],1),e._v(" "),a("Details",{ref:"details",attrs:{device:e.currentDevice}}),e._v(" "),a("el-backtop",{staticClass:"ares-bg-primary",attrs:{target:".app-main>.el-scrollbar>.el-scrollbar__wrap>.el-scrollbar__view>.device-wrap",right:10,bottom:5}})],1)}),[],!1,null,"75a5cc71",null));t.default=I.exports},"20d6":function(e,t,a){"use strict";var i=a("5ca1"),n=a("0a49")(6),r="findIndex",o=!0;r in[]&&Array(1)[r]((function(){o=!1})),i(i.P+i.F*o,"Array",{findIndex:function(e){return n(this,e,arguments.length>1?arguments[1]:void 0)}}),a("9c6c")(r)},"7cbf":function(e,t,a){"use strict";a("eab0")},eab0:function(e,t,a){}}]);