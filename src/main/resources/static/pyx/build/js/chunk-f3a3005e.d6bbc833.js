(window.webpackJsonp=window.webpackJsonp||[]).push([["chunk-f3a3005e"],{"7ac7":function(e,t,r){"use strict";r.r(t);var o=r("ade3"),a={name:"workerList",data:function(){return{listData:[{workerName:"张三",recentJob:"CD-102",recentUpdate:"2022-9-27"}]}},mounted:function(){},methods:{checkInfo:function(e){var t;console.log(e),this.$router.push((t={name:"/workerInfo"},Object(o.a)(t,"name","workerInfo"),Object(o.a)(t,"params",e),t))}}},n=(r("dbbf"),r("2877")),l=Object(n.a)(a,(function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{ref:"deviceWrap",staticClass:"device-wrap"},[r("el-card",{staticClass:"page-header-card",attrs:{shadow:"hover"}},[r("b",[e._v("人员列表")])]),e._v(" "),r("el-card",[r("el-table",{attrs:{data:e.listData,stripe:"","highlight-current-row":""}},[r("el-table-column",{attrs:{label:"操作人姓名",prop:"workerName","show-overflow-tooltip":""}}),e._v(" "),r("el-table-column",{attrs:{label:"最近执行活件",prop:"recentJob","show-overflow-tooltip":""}}),e._v(" "),r("el-table-column",{attrs:{label:"最近更新时间",prop:"recentUpdate","show-overflow-tooltip":""}}),e._v(" "),r("el-table-column",{attrs:{label:"操作","show-overflow-tooltip":""},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-button",{attrs:{type:"primary",size:"mini"},on:{click:function(r){return e.checkInfo(t.row)}}},[e._v("查看员工印刷产出")])]}}])})],1)],1)],1)}),[],!1,null,"5d083db3",null);t.default=l.exports},"7f83":function(e,t,r){},ade3:function(e,t,r){"use strict";function o(e,t,r){return t in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}r.d(t,"a",(function(){return o}))},dbbf:function(e,t,r){"use strict";r("7f83")}}]);