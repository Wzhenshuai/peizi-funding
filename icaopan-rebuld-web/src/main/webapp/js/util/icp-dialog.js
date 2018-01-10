function ShowWypzDia(title, amount, circle , interest, duedate,sureButtonText, surecallback, cancelButtonText, cancelcallback){
	this.title=title;
	this.circle=circle;
	this.interest=interest;
	this.duedate=duedate;
	this.sureButtonText=sureButtonText;
	this.cancelButtonText=cancelButtonText;
	this.closeDia = function() {
        $("div.pop").remove();
        if (cancelcallback) {
            cancelcallback();
        }
    };
    closeDia = function() {
        $("div.pop").remove();
        if (cancelcallback) {
            cancelcallback();
        }
    };
	this.sureButtonClick = function() {
		closeDia();
        if (surecallback){
            surecallback();
        }
    };
    sureButtonClick = function() {
    	closeDia();
        if (surecallback) {
            surecallback();
        }
    };
    this.show=function(){
		var html="";
		html += "<div class='pop'>";
		html += "  <div class='pop-con'>";
		html += "    	<h2><span>"+this.title+"</span></h2>";
		html += "        <table width='300' border='0' cellspacing='0' cellpadding='0' class='zhcc-table-2' style='margin-left:40px;'>";
		html += "         <tr>";
		html += "    <td width='131'><span>最大可延周期：</span></td>";
		html += "    <td width='169'>"+this.circle+"个月</td>";
		html += "  </tr>";
		html += "		  <tr>";
		html += "    <td><span>选择周期：</span></td>";
		html += "    <td><label>";
		html += "      <select name='' id='circles' onchange='DF.changeCircle(this , "+amount+");'>";
		for (var i = 1; i <= this.circle; i++) {
				html += "<option value="+i+">" + i + "个月</option>";
		}
		html += "      </select>";
		html += "    </label></td>";
		html += "  </tr>";
		html += "<tr>";
		html += "    <td><span>系统使用费率：</span></td>";
		html += "    <td><span id='interest'>"+this.interest+"</span>分</td>";
		html += "  </tr>";
		html += "  <tr>";
		html += "    <td><span>到期日：</span></td>";
		html += "    <td><span id='duedate_dia'>"+duedate+"</span></td>";
		html += "  </tr>";
		html += "</table>";
		html += "        <div class='pop-btn'>";
		html += "       	<input  type='button' class='inp-btn' value='"+this.sureButtonText+"' onclick='sureButtonClick()' >";
		html += "            <input  type='button' value='"+this.cancelButtonText+"' class='cancel' onclick='closeDia()'/>";
		html += "        </div>";
		html += "    </div> ";
		html += "	</div> ";
		$("body").append(html);
	};
}

function ConfirmDialog(title, text, sureButtonText, surecallback, cancelButtonText, cancelcallback){
	this.title=title;
	this.text=text;
	this.sureButtonText=sureButtonText;
	this.cancelButtonText=cancelButtonText;
	this.closeDia = function() {
        $("div.pop").remove();
        if (cancelcallback) {
            cancelcallback();
        }
    };
    closeDia = function() {
        $("div.pop").remove();
        if (cancelcallback) {
            cancelcallback();
        }
    };
	this.sureButtonClick = function() {
		closeDia();
        if (surecallback) {
            surecallback();
        }
    };
    sureButtonClick = function() {
    	closeDia();
        if (surecallback) {
            surecallback();
        }
    };
	this.show=function(){
		var html="";
		html+='<div class="pop">';
		html+='<div class="pop-con">'
		+'  <h2><span>'+title+'</span></h2>'
		+'    <ul>'
		+         text
		+'    </ul>'
		+'    <div class="pop-btn"><input name=""  type="button" value='+sureButtonText+' onclick="sureButtonClick()">'
		+'<input name="" class="cancel" type="button"  value='+cancelButtonText+' onclick="closeDia()"></div>'
		+' </div>'
		+'</div>';
		$("body").append(html);
	};
}
function SuccessDialog(text,surecallback){
	this.text=text;
	this.show=function(){
		var html="";
		html+='<div class="pop">'
		+'<div class="pop-con">'
		+'<h2><span>提醒！</span></h2>'
		+'    <h4>'
		+         text
		+'    </h4>'
		+'    <div class="pop-btn"><input name="" type="button" value="确认" onclick="sureButtonClick()"></div>'
		+' </div>'
		+'</div>';
		$("body").append(html);
	};
	sureButtonClick = function() {
	    closeDia();
	    if(surecallback){
	    	surecallback();
	    }
	};
	closeDia = function() {
        $("div.pop").remove();
    };
}
function NofifyDialog(text,surecallback){
	this.text=text;
	this.show=function(){
		var html="";
		html+='<div class="pop">'
		+'<div class="pop-con">'
		+'<h2 class="rel"><span>提醒！</span><a href="javascript:;" class="ui-icon i-close" onclick="closeDia()" attr="popup-close"></a></h2>'
		+'    <h4>'
		+         text
		+'    </h4>'
		+'    <div class="pop-btn"><input name="" type="button" value="确认" onclick="sureButtonClick()"></div>'
		+' </div>'
		+'</div>';
		$("body").append(html);
	};
	sureButtonClick = function() {
	    closeDia();
	    if(surecallback){
	    	surecallback();
	    }
	};
	closeDia = function() {
        $("div.pop").remove();
    };
}
function ShowDialogConfirm(title, text, sureButtonText, surecallback, cancelButtonText, cancelcallback){
	var diaConfirm=new ConfirmDialog(title, text, sureButtonText, surecallback, cancelButtonText, cancelcallback);
	diaConfirm.show();
}
function ShowDialogSuccess(text,surecallback){
	var diaSuccess=new SuccessDialog(text,surecallback);
	diaSuccess.show();
}
function ShowDialogFail(text){
	var diaNofity=new NofifyDialog(text);
	diaNofity.show();
}
function ShowDialogNotify(text){
	var diaNofity=new NofifyDialog(text);
	diaNofity.show();
}
function UpdateDialog(title, text, sureButtonText, surecallback,cancelButtonText,cancelcallback){
	this.title=title;
	this.text=text;
	this.sureButtonText=sureButtonText;
	this.cancelButtonText=cancelButtonText;
	this.closeDia = function() {
        $("div.pop").remove();
        if (cancelcallback) {
            cancelcallback();
        }
    };
    closeDia = function() {
        $("div.pop").remove();
        if (cancelcallback) {
            cancelcallback();
        }
    };
	this.sureButtonClick = function() {
        if (surecallback) {
            surecallback();
        }
    };
    sureButtonClick = function() {
        if (surecallback) {
            surecallback();
        }
    };
	this.show=function(){
		var html="";
		html+='<div class="pop">'
		+'<div class="pop-con">'
		+'  <h2><span>'+this.title+'</span></h2>'
		+       this.text
		+'  <div class="pop-btn"><input name="" type="button" value='+this.sureButtonText+' class="inp-btn" onclick="sureButtonClick()"/>'
		+'<input name="" class="cancel" type="button"  value='+cancelButtonText+' onclick="closeDia()"></div>'
		+'</div>'
		+' </div>';
		$("body").append(html);
	};
}
function showTips(content,ele){
	var html="";
	html+='<div id="tipDiv" class="wh_tck_div" style="position:absolute;top:-100px;left:-27px"><div class="wh_tck">'
	  +'<div class="wh_tck_div"> <img src="images/xsj.png">'
	  +'   <div class="wh_tck1">'
	  +'     <p>'+content+'</p>'
	  +'   </div>'
	  +' </div>'
	  +' <p>&nbsp;</p>'
	  +'</div>'
	  +'</div>';
	$(html).insertAfter($(ele));
}
//针对内容是少的弹窗 @Modify lifaxin
function showFixedTips(content,ele ,contentHeight,contentToTop,imgTop){
	var html="";
	html+='<div id="tipDiv" class="wh_tck_div" style="position:absolute;top:-100px;left:-27px;"><div class="wh_tck">'
	  +'<div class="wh_tck_div"> <img src="images/xsj.png" style="top:'+imgTop+'px;">'
	  +'   <div class="wh_tck1" style="margin-top:'+contentToTop+'px;height:'+contentHeight+'px;">'
	  +'     <p>'+content+'</p>'
	  +'   </div>'
	  +' </div>'
	  +' <p>&nbsp;</p>'
	  +'</div>'
	  +'</div>';
	$(html).insertAfter($(ele));
}
function closeTips(){
	$("#tipDiv").remove();
}
function showTick(title, text, sureButtonText, surecallback, cancelButtonText, cancelcallback){
	this.title=title;
	this.text=text;
	this.sureButtonText=sureButtonText;
	this.cancelButtonText=cancelButtonText;
	this.closeDia = function() {
        $("div.pop-500").remove();
        if (cancelcallback) {
            cancelcallback();
        }
    };
    closeDia = function() {
        $("div.pop-500").remove();
        if (cancelcallback) {
            cancelcallback();
        }
    };
	this.sureButtonClick = function() {
		closeDia();
        if (surecallback) {
            surecallback();
        }
    };
    sureButtonClick = function() {
    	closeDia();
        if (surecallback) {
            surecallback();
        }
    };
    this.show=function(){
	var html="";
		html+='<div class="pop-500">'
		+'<div class="pop-con">'
		+'<div class="pop-close" onclick="closeDia()"><img src="images/pop-500_close_w.png"></div>'
		+'<h2><span>'+title+'</span></h2>'
		+'<div class="ta">'
		+'<h1>网站服务协议</h1>'
		+text
		+'</div>'
		+'<div class="pop-btn"><input name="" type="button" value="'+this.sureButtonText+'" class="inp-btn"  onclick="sureButtonClick()"/></div>'
		+'</div>'
		+'</div>';
		$("body").append(html);
	};
}

function showXYTick(title, subTitle,text, sureButtonText, surecallback, cancelButtonText, cancelcallback){
	this.title=title;
	this.text=text;
	this.sureButtonText=sureButtonText;
	this.cancelButtonText=cancelButtonText;
	this.closeDia = function() {
        $("div.pop-500").remove();
        if (cancelcallback) {
            cancelcallback();
        }
    };
    closeDia = function() {
        $("div.pop-500").remove();
        if (cancelcallback) {
            cancelcallback();
        }
    };
	this.sureButtonClick = function() {
		closeDia();
        if (surecallback) {
            surecallback();
        }
    };
    sureButtonClick = function() {
    	closeDia();
        if (surecallback) {
            surecallback();
        }
    };
    this.show=function(){
	var html="";
		html+='<div class="pop-500">'
		+'<div class="pop-con">'
		+'<div class="pop-close" onclick="closeDia()"><img src="images/pop-500_close_w.png"></div>'
		+'<h2><span>'+title+'</span></h2>'
		+'<div class="ta">'
		+'<h1>'+subTitle+'</h1>'
		+text
		+'</div>'
		+'<div class="pop-btn"><input name="" type="button" value="'+this.sureButtonText+'" class="inp-btn"  onclick="sureButtonClick()"/></div>'
		+'</div>'
		+'</div>';
		$("body").append(html);
	};
}

function chargeTick(title, subTitle,sureButtonText, surecallback, cancelButtonText, cancelcallback){
	this.title=title;
	this.subTitle=subTitle;
	this.sureButtonText=sureButtonText;
	this.cancelButtonText=cancelButtonText;
	this.closeDia = function() {
        $("div.pop").remove();
        if (cancelcallback) {
            cancelcallback();
        }
    };
    closeDia = function() {
        $("div.pop").remove();
        if (cancelcallback) {
            cancelcallback();
        }
    };
	this.sureButtonClick = function() {
		closeDia();
        if (surecallback) {
            surecallback();
        }
    };
    sureButtonClick = function() {
    	closeDia();
        if (surecallback) {
            surecallback();
        }
    };
    this.show=function(){
	var html="";
		html+='<div class="pop">'
		+'<div class="pop-con">'
		+'<h2><span>'+title+'</span></h2>'
		+'<h3>'+subTitle+'</h3>'
		+'<ul>'
    	+'<li class="li-c"><input name="" type="button" class="inp-btn-n" value="'+this.sureButtonText+'" onclick="sureButtonClick()"></li>'
        +'<li class="li-c"><input name="" type="button" value="'+this.cancelButtonText+'" class="inp-btn-n-h" onclick="closeDia()" /></li>'
        +'</ul>'
		+'</div>'
		+'</div>';
		$("body").append(html);
	};
}