<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="表格ID"%>
<%@ attribute name="ajaxURI" type="java.lang.String" required="true" description="请求服务器地址"%>
<%@ attribute name="headerNames" type="java.lang.String" required="true" description="列名称列表"%>
<%@ attribute name="columnNames" type="java.lang.String" required="true" description="列字段名称"%>
<%@ attribute name="makeDefsHtmlFunc" type="java.lang.String" required="false" description="扩展列内容函数"%>
<%@ attribute name="makeHeadDefsHtmlFunc" type="java.lang.String" required="false" description="扩展首列内容函数"%>
<%@ attribute name="searchInputNames" type="java.lang.String" required="false" description="条件过滤表单name"%>
<%@ attribute name="pageLength" type="java.lang.String" required="false" description="分页大小数组"%>
<%@ attribute name="loadEnd" type="java.lang.String" required="false" description="重绘回调函数"%>
<%@ attribute name="showProgress" type="java.lang.String" required="false" description="显示加载中"%>
<%@ attribute name="rowCallBack" type="java.lang.String" required="false" description="行创建回调"%>

<table id="${id }" class="table table-hover table-striped table-bordered table-responsive" aria-busy="false">
</table>
<script type="text/javascript">
//组装header
var headerHtml="<thead><tr>";
var headerNames="${headerNames}".split(",");
var columnNames="${columnNames}".split(",");
var exportId="${exportId}";
var aLengthMenu="${pageLength}";
var le="${loadEnd}";
var progress="${showProgress}";
if(progress!=null&&progress=="false"){
	progress=false;
}else{
	progress=true;
}
if(headerNames.length>0){
	for(i=0;i<headerNames.length;i++){
		headerHtml+="<th style='max-width:150px'>"+headerNames[i]+"</th>";
	}
}
headerHtml+="</tr></thead>";
//组装字段
var aoColumns=[];
var columnLen=columnNames.length;
var headerLen=headerNames.length;
var showProgress=true;
for(i=0;i<columnLen;i++){
	aoColumns.push({"mData":columnNames[i],"sClass":"center"});
}
columnDefsA=[]
if("${makeHeadDefsHtmlFunc}"!=""){
	columnDefsA.push({
		"targets":0,
		"render":function(data, type, full, meta) {
          	     return eval("${makeHeadDefsHtmlFunc}")(full);
		}
	});
} 
if("${makeDefsHtmlFunc}"!=""){
	columnDefsA.push({
		"targets":[headerLen-1],
		"render":function(data, type, full, meta) {
          	     return eval("${makeDefsHtmlFunc}")(full);
		}
	});
} 
//设置分页大小
if(aLengthMenu){
	aLengthMenu=aLengthMenu.split(',');
}else{
	aLengthMenu=[50];
}
$("#${id }").html(headerHtml);
 var showTable = $("#${id }").dataTable({
	"bFilter" : false,// 搜索栏
	"bLengthChange" : false,// 每行显示记录数
	"aLengthMenu":aLengthMenu,
	"colResizable":true,
	"bDeferRender":true,
	"scrollX" : true,
	"bAutoWidth": false, //自适应宽度 
	"bSort" : false,// 排序
	"bInfo" : true,// Showing 1 to 10 of 23 entries 总记录数没也显示多少等信息
	"bScrollCollapse" : true,
	"sPaginationType" : "full_numbers", // 分页，一共两种样式 另一种为two_button // 是datatables默认
	"bProcessing" : progress,
	"sLoadingRecords":"数据正在加载中...",
	"bServerSide" : true,
	"sAjaxSource" : '${ajaxURI}',
	"aoColumns" : aoColumns,
	"columnDefs": columnDefsA, 
    "fnServerData" : function(sSource, aoData, fnCallback) {
	   var data={};
	   var dataa={};
	   aoData=JSON.stringify(aoData);
	   data["aoData"]=aoData;
	   var extraData="${searchInputNames}";
	   if(extraData!=null){
		   paras=extraData.split(",");
		   for(var i=0;i<paras.length;i++){
			   var inputName=paras[i];
			   value=$(":input[name='"+inputName+"']").val();
			   if(value){
				   if(value instanceof Array){
					   value=value.join(",");
				   }
				   data[inputName] = value;
			   }
		   }
	   }
		$.ajax({
			"type" : 'post',
			"url" : sSource,
			"dataType" : "json",
			"data" : data,
			"success" : function(resp) {
				fnCallback(resp);
			},"error" : function (result){
				if(result.status!=0){
					alertx("登录超时或没权限...",function(){
						 var url=parent.document.location;
						 url=url.replace("home","");
						 url=url.substring(0,url.length-1);
						 url=url.replace("user","login.jsp");
						 window.parent.loaction.href =url;
					});
				}
			}
		});
	},
	"fnDrawCallback":function(){
		if(le){
			eval(le);
		}
	},
	"fnRowCallback":function(nRow,aData,iDisplayIndex,iDisplayIndexFull){
		if("${rowCallBack}"){
			//eval(rowCallBack(nRow,aData));
			eval("${rowCallBack}")(nRow,aData);
		}
	}
}); 
 
function reloadTableData(){
	var oSettings = showTable.fnSettings();  
    oSettings._iDisplayStart = 0;  
	showTable.fnDraw(false);
}

function reloadTableById(tableId){
	var showTable = $("#"+tableId).dataTable();
	var oSettings = showTable.fnSettings();
	oSettings._iDisplayStart = 0;
	showTable.fnDraw(false);
}


function exportExcel(url){
	var pms=[];
	var extraData="${searchInputNames}";
  	if(extraData!=null){
	   paras=extraData.split(",");
	   for(var i=0;i<paras.length;i++){
		   var inputName=paras[i];
		   value=$(":input[name='"+inputName+"']").val();
		   if(value){
		 	  pms.push(inputName+"="+value);
		   }
	   }
	   pms=pms.join("&");
	   url=url+"?"+pms;
   	}
  	location.href=url;
}
</script>
