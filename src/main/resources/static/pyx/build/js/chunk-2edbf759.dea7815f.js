(window.webpackJsonp=window.webpackJsonp||[]).push([["chunk-2edbf759"],{"07ae":function(t,a,e){"use strict";e("593c")},"593c":function(t,a,e){},8166:function(t,a,e){"use strict";e.r(a);var c,i=e("c7eb"),s=(e("96cf"),e("1da1")),r=e("bc3a"),n=e.n(r),o={name:"activeTotal",data:function(){return{tableData:[],activeName:"first",listData:[]}},mounted:function(){this.getList()},methods:{backPage:function(){this.$router.back()},getList:(c=Object(s.a)(Object(i.a)().mark((function t(){var a,e,c,s;return Object(i.a)().wrap((function(t){for(;;)switch(t.prev=t.next){case 0:return a=this.$route.params.date,e=this.$route.params.deviceId,t.next=4,n.a.get("/signalStatus/getJobIdOnly?date=".concat(a,"&deviceId=").concat(e));case 4:c=t.sent,s=c.data,this.listData=s;case 7:case"end":return t.stop()}}),t,this)}))),function(){return c.apply(this,arguments)})}},d=(e("07ae"),e("2877")),l=Object(d.a)(o,(function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{ref:"deviceWrap",staticClass:"device-wrap"},[e("el-card",{staticClass:"page-header-card",attrs:{shadow:"hover"}},[e("b",[t._v("今日活件统计")]),t._v(" "),e("div",{staticStyle:{width:"200px",background:"red",height:"100%"}}),t._v(" "),e("el-button",{attrs:{type:"primary",size:"small"},on:{click:t.backPage}},[t._v("返回")])],1),t._v(" "),e("div",{staticClass:"i-total"},[e("p",{staticStyle:{width:"200px","margin-top":"5px"}},[t._v("\n      deviceId: "+t._s(t.$route.params.deviceId)+"\n    ")]),t._v(" "),e("p",{staticStyle:{width:"200px","margin-top":"5px"}},[t._v("\n      日期: "+t._s(t.$route.params.date)+"\n    ")])]),t._v(" "),e("el-card",[e("el-table",{attrs:{data:t.listData,stripe:"","highlight-current-row":""}},[e("el-table-column",{attrs:{label:"活件ID",prop:"jobId","show-overflow-tooltip":""}})],1)],1)],1)}),[],!1,null,"1b6642fc",null);a.default=l.exports}}]);