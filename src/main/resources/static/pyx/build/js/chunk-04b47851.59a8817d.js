(window.webpackJsonp=window.webpackJsonp||[]).push([["chunk-04b47851"],{"678c":function(t,e,a){},8166:function(t,e,a){"use strict";a.r(e);var i,r=a("c7eb"),c=(a("96cf"),a("1da1")),s=a("bc3a"),n=a.n(s),o={name:"activeTotal",components:{Details:a("766a").a},data:function(){return{tableData:[],activeName:"first",listData:[],currentDevice:{}}},mounted:function(){this.getList()},methods:{backPage:function(){this.$router.back()},getList:(i=Object(c.a)(Object(r.a)().mark((function t(){var e,a,i,c;return Object(r.a)().wrap((function(t){for(;;)switch(t.prev=t.next){case 0:return e=this.$route.query.date,a=this.$route.query.deviceId,t.next=4,n.a.get("/statistics/getJobIdOnly?date=".concat(e,"&deviceId=").concat(a));case 4:i=t.sent,c=i.data,this.listData=c.data;case 7:case"end":return t.stop()}}),t,this)}))),function(){return i.apply(this,arguments)}),showDetail:function(t){this.currentDevice.JobID=t.jobId,this.currentDevice.machineName=this.$route.query.machineName,this.$refs.details.showDialog()}}},l=(a("a6c9"),a("2877")),u=Object(l.a)(o,(function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{ref:"deviceWrap",staticClass:"device-wrap"},[a("el-card",{staticClass:"page-header-card",attrs:{shadow:"hover"}},[a("b",[t._v("今日活件统计")]),t._v(" "),a("div",{staticStyle:{width:"200px",background:"red",height:"100%"}}),t._v(" "),a("el-button",{attrs:{type:"primary",size:"small"},on:{click:t.backPage}},[t._v("返回")])],1),t._v(" "),a("div",{staticClass:"i-total"},[a("p",{staticStyle:{width:"200px","margin-top":"5px"}},[t._v("\n      deviceId: "+t._s(t.$route.query.deviceId)+"\n    ")]),t._v(" "),a("p",{staticStyle:{width:"200px","margin-top":"5px"}},[t._v("\n      日期: "+t._s(t.$route.query.date)+"\n    ")])]),t._v(" "),a("el-card",[a("el-table",{attrs:{data:t.listData,stripe:"","highlight-current-row":""}},[a("el-table-column",{attrs:{label:"活件ID",prop:"jobId","show-overflow-tooltip":""}}),t._v(" "),a("el-table-column",{attrs:{label:"操作",width:"100","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){return[e.row.jobId?a("el-button",{attrs:{type:"primary",size:"mini"},on:{click:function(a){return t.showDetail(e.row)}}},[t._v("查看")]):t._e()]}}])})],1)],1),t._v(" "),a("Details",{ref:"details",attrs:{device:t.currentDevice}})],1)}),[],!1,null,"11902b5d",null);e.default=u.exports},a6c9:function(t,e,a){"use strict";a("678c")}}]);