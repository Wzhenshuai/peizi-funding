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
function getDay(day){  
    var today = new Date();  
      
    var targetday_milliseconds=today.getTime() + 1000*60*60*24*day;          

    today.setTime(targetday_milliseconds); //注意，这行是关键代码    
      
    var tYear = today.getFullYear();  
    var tMonth = today.getMonth();  
    var tDate = today.getDate();  
    tMonth = doHandleMonth(tMonth + 1);  
    tDate = doHandleMonth(tDate);  
    return tYear+"-"+tMonth+"-"+tDate;  
}  
function doHandleMonth(month){  
    var m = month;  
    if(month.toString().length == 1){  
       m = "0" + month;  
    }  
    return m;  
}  
var DF=dateFarmat={};

DF.formatDateToDouble = function (data){
	var d=data+"";
	if(d.length>1){
		return d;
	}else{
		return "0"+d;
	}
};

DF.formateDateAllowString = function (timestamp){
	  var  str=timestamp.toString();
      str =  str.replace(/-/g,"/");
      str = str.substring(0,str.indexOf("."));
      var nowStr = new Date(str).format("yyyy-MM-dd hh:mm:ss");
      return nowStr;
};

//配资业务不现实 时分秒
DF.formateDateAllowStringForPeizi = function (timestamp){
	  var  str=timestamp.toString();
    str =  str.replace(/-/g,"/");
    str = str.substring(0,str.indexOf("."));
    var nowStr = new Date(str).format("yyyy-MM-dd");
    return nowStr;
};

DF.formateDate = function (timestatmp) {
	var now = new Date(timestatmp);
	var nowStr = now.format("yyyy-MM-dd hh:mm:ss");
	return nowStr;
};
DF.formateDateToShowDate = function (timestatmp){
	var now = new Date(timestatmp);
	var nowStr = now.format("yyyy-MM-dd");
	return nowStr;
};
DF.formateDateToShowDateMMDD = function (timestatmp){
	var now = new Date(timestatmp);
	var nowStr = now.format("MM-dd");
	return nowStr;
};
DF.formateDateToTime = function (timestatmp){
	var now = new Date(timestatmp);
	var nowStr = now.format("hh:mm:ss");
	return nowStr;
};
DF.formateDecimalToPercent = function (decimalData){
	return (parseFloat(decimalData)*100).toFixed(2)+'%';
};
DF.getTodayDate = function (){
	var now=new Date();
	var year=now.getFullYear();
	var month=DF.formatDateToDouble(now.getMonth()+1);
	var day=DF.formatDateToDouble(now.getDate());
	return year+"-"+month+"-"+day;
};
DF.getYesterdayDate = function (){
	return getDay(-1);
};
DF.GetDateStr=function(AddDayCount){
	var dd = new Date();
    dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
    var y = dd.getFullYear();
    var m = DF.formatDateToDouble(dd.getMonth()+1);//获取当前月份的日期
    var d = DF.formatDateToDouble(dd.getDate());
    return y+"-"+m+"-"+d;
}
DF.getLastMonthDate = function (){
	var now=new Date();
	var year=now.getFullYear();
	var month=DF.formatDateToDouble(now.getMonth());
	if(month==0){
		month=12;
		year=year-1;
	}
	var day=DF.formatDateToDouble(now.getDate());
	return year+"-"+month+"-"+day;
};
DF.changeCircle = function (obj,lever ,amount){
	var date = DF.getNewDate(duedate, obj.value);
	interest = DF.calInterest(obj.value , lever,amount);
	$('#circle').val($('#circles').val());
	$("#interest").html(interest);
	$("#duedate_dia").html(date);
};
DF.getNewDate = function (date , amount){
	date = date.replace(/-/g, '/');
	var d = new Date(date);
	//计算 第amount月 的最后时间
	//得到下个月的第一天
	var y = d.getFullYear();
	var m = d.getMonth() +  parseInt(amount) +1;
	if(m > 12){
		m = m-12;
		y = y + 1;
	}
	var day = 1;
	var nextDate = y+"/"+m+"/"+day;
	var nextMonthDate = DF.getCurrentMonthLastDay(new Date(nextDate));
	//判断当前日期的天数 是否小于 下个月天数  ，如果小于 月份正常+1  否则当前日期 则作为最后日期
	if(d.getDate() <= nextMonthDate.getDate()){
		d.setMonth(d.getMonth() + parseInt(amount));
		//d.setDate(d.getDate()-1);
		time =DF.dateFormat(d);
	}else {
		//nextMonthDate.setDate(nextMonthDate.getDate()-1);
		time = DF.dateFormat(nextMonthDate);
	}
	return time;
};

//格式化日期
DF.dateFormat = function (d){
	var y = d.getFullYear();
	var m = d.getMonth() + 1;
	var day = d.getDate();
	if (m < 10){
		m = "0"+m;
	}
	if(day < 10){
		day = "0" + day;
	}
	return y+"-" + m+"-" + day;
};

//获取 d 月的最后一天的日期
DF.getCurrentMonthLastDay = function (d){
     var current=d;
     var currentMonth=current.getMonth();
     var nextMonth=++currentMonth;
 	 var nextMonthDayOne =new Date(current.getFullYear(),nextMonth,1);
     var minusDate=1000*60*60*24;
     return new Date(nextMonthDayOne.getTime()-minusDate);
};