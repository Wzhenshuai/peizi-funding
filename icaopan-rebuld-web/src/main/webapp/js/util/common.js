// 对Date的扩展，将 Date 转化为指定格式的String 
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.format = function(fmt){ //author: meizz 
	  var o = { 
	    "M+" : this.getMonth()+1,                 //月份 
		"d+" : this.getDate(),                    //日 
		"h+" : this.getHours(),                   //小时 
		"m+" : this.getMinutes(),                 //分 
		"s+" : this.getSeconds(),                 //秒 
		"q+" : Math.floor((this.getMonth()+3)/3), //季度 
		"S"  : this.getMilliseconds()             //毫秒 
	  }; 
	  if(/(y+)/.test(fmt)) 
		  fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	  for(var k in o) 
	    if(new RegExp("("+ k +")").test(fmt)) 
	    	fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
	  return fmt; 
};

function formatDateToDouble(data){
	var d=data+"";
	if(d.length>1){
		return d;
	}else{
		return "0"+d;
	}
}

function formateDateAllowString(timestamp){
	  var  str=timestamp.toString();
      str =  str.replace(/-/g,"/");
      str = str.substring(0,str.indexOf("."));
      var nowStr = new Date(str).format("yyyy-MM-dd hh:mm:ss");
      return nowStr;
}
function formateDate(timestatmp) {
	var now = new Date(timestatmp);
	var nowStr = now.format("yyyy-MM-dd hh:mm:ss");
	return nowStr;
}
function formateDateToShowDate(timestatmp){
	var now = new Date(timestatmp);
	var nowStr = now.format("yyyy-MM-dd");
	return nowStr;
}
function formateDateToTime(timestatmp){
	var now = new Date(timestatmp);
	var nowStr = now.format("hh:mm:ss");
	return nowStr;
}
function formateDecimalToPercent(decimalData){
	return (parseFloat(decimalData)*100).toFixed(2)+'%';
}
function getTodayDate(){
	var now=new Date();
	var year=now.getFullYear();
	var month=formatDateToDouble(now.getMonth()+1);
	var day=formatDateToDouble(now.getDate());
	return year+"-"+month+"-"+day;
}
function getLastMonthDate(){
	var now=new Date();
	var year=now.getFullYear();
	var month=formatDateToDouble(now.getMonth());
	var day=formatDateToDouble(now.getDate());
	return year+"-"+month+"-"+day;
}
