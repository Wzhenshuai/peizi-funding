var DM = dealmoney = {};

DM.utils={
	/*将数字金额转化成逗号格式(111000003.22 → 111,000,003.22)*/
	farmatMoney: function(num, n){
		num=new Number(num);
		num = String(num.toFixed(n?n:2));
	    var re = /(-?\d+)(\d{3})/;
	    while(re.test(num)) num = num.replace(re,"$1,$2");
	    return n?num:num.replace(/^([0-9,]+\.[1-9])0$/,"$1").replace(/^([0-9,]+)\.00$/,"$1");
	},
	/*将逗号金额转化成数字格式12,300 → 12300*/
	formateMoneyToDecimal: function (moneyValue){
		var pstr=$.trim(moneyValue);
		pstr=pstr.replace(/,/g,"");
		if(valid.isNumber(pstr, true)){
			var p = parseFloat(pstr);
			return p;
		}
		return 0;
	},
	/*判断是否是 n 的整数倍*/
	isNumberByN:function(ssn, n){
		var re = /^[0-9]*[0-9]$/i;       //校验是否为数字
		 if(re.test(ssn) && ssn % n==0) {
		  return true;
		 }else {
		  return false;
		 }
	},
	/*消除n*10位以后的部分，转化成n的倍数*/
	formateNumberDivideN: function (ssn, n){
		var default_n=100;
		if(n){
			default_n = n;
		}
		var tem=ssn/default_n;
		tem=parseInt(tem);
		tem=tem*default_n;
		return tem;
	},
	/*保留两位数字*/
	toFixedNumber:function (number) {
		return (number-0).toFixed(2);
	},
	/*平台利息计算 */
	calcInterest: function (financeAmount,cycle,mode){
		if(mode=="fix_mode"){
	        if(financeAmount<100000){
	            if(cycle<=2){
	            	if(lever <= 1){
	            		return 1.5;
	            	}else if(lever <= 2){
	            		return 1.5;
	            	}else if(lever <= 3){
	            		return 1.5;
	            	}
	            	//return 1.5;
	            }else{
	            	if(lever <= 1){
	            		return 1.4;
	            	}else if(lever <= 2){
	            		return 1.4;
	            	}else if(lever <= 3){
	            		return 1.4;
	            	}
	            	//return 1.4;
	            }
	            return null;
	        }else if(financeAmount>=100000&&financeAmount<500000){
	        	if(cycle<=2){
	        		if(lever <= 1){
	            		return 1.3;
	            	}else if(lever <= 2){
	            		return 1.3;
	            	}else if(lever <= 3){
	            		return 1.3;
	            	}
	        		//return 1.3;
	            }else{
	            	if(lever <= 1){
	            		return 1.2;
	            	}else if(lever <= 2){
	            		return 1.2;
	            	}else if(lever <= 3){
	            		return 1.2;
	            	}
	            	//return 1.2;
	            }
	        }else if(financeAmount>=500000&&financeAmount<=1500000){
	        	if(cycle<=2){
	        		if(lever <= 1){
	            		return 1.1;
	            	}else if(lever <= 2){
	            		return 1.1;
	            	}else if(lever <= 3){
	            		return 1.1;
	            	}
	        		//return 1.1;
	            }else{
	            	if(lever <= 1){
	            		return 1;
	            	}else if(lever <= 2){
	            		return 1;
	            	}else if(lever <= 3){
	            		return 1;
	            	}
	            	//return 1;
	            }
	        }
	    }
	    return 0;
	}
};