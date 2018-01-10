function queryTimeShare(stockCode, type){
	if(type == 'new' && MS.currentStockCode && stockCode != MS.currentStockCode) {
		clearTimeout(MS.timer);
		MS.timer=null;
		MS.type = 'new';
	} else {
		MS.type = 'old';
	}
	
	if(!/^\d{6}$/ig.test($.trim(stockCode))) {
		return;
	}
	$.ajax({
		type: "get",
		url:Configs.maket_data_time_server+ '?is_web=1&exChangeCode=&stockCode=' + stockCode,
		dataType: 'jsonp'
	});
}
function returnjson(data) {
	if(MS.type == 'new' && MS.currentStockCode == data.info.result.s1) {
		return;
	}
	if(!/^\d{6}$/ig.test($.trim(data.info.result.s1))) {
		if($.trim($('#stockCode').val()) == '') {
		}
		return;
	} 
	if($('#stockCode').val() == data.info.result.s1) {
		if(MS.timeShare){
			MS.timeShare.destroy();
			MS.timeShare = null;
		}
		if(data.info.rescode=="success"){
			MS.currentStockCode = data.info.result.s1;
			//如果是停牌就不发请求了
			if(data.info.result.s6 == 'true') {
				return;
			}
			timek(data.info.result);
			MS.timer = setTimeout($.proxy(queryTimeShare, this, MS.currentStockCode), 1000*30);
		}
	} 
}
function timek(stockData){
var myChart = echarts.init(document.getElementById('time-share'));
var tool = {
        
        /**
         * @description 分时图数据集分离处理
         * @method lineSplitData
         * @param {Object} data,原始数据集
         * @return {Object} 返回处理好数据集对象
         * */
        lineSplitData : function(stockData){
            var avgData = [],
                curData = [],
                traData = [],
                updData = [],
                timeData = [],
                rangZoom = {},
                defaultRang = 242;//一天的采集数据，每分钟一个点
            data=stockData.s20;
            len = data.length;
            for(var i = 0; i < len; i++){
                //时间
                timeData.push(tool.fromatDate(data[i].s15, 'hh:mm'));
                //当前价
                curData.push( tool.toNumber(data[i].s11) );
                //均价
                avgData.push( tool.toNumber(data[i].s14) );
                //成交量
                //traData.push( tool.toNumber(data[i].s16) );
                //成交量
//                if(data[i].s11>data[i-1].s11){
//                    //阳线
//                    traData1.push( tool.toNumber(data[i].s16) );
//                    traData.push("-");
//                }else {
//                    //阴线
//                    traData.push( tool.toNumber(data[i].s16) );
//                    traData1.push("-")
//                }
                traData.push(tool.toNumber(data[i].s16));
                //涨跌幅
                var closeNum = tool.toNumber(stockData.s5),
                    selfNum = tool.toNumber(data[i].s11),
                    updNum = (selfNum - closeNum) / closeNum;
                updData.push( + updNum.toFixed(4) );
            }
            //
            var prec = tool.toNumber(stockData.s5);
            //区域缩放默认值：60天的采集点
            if(len > defaultRang){
                rangZoom.end = len;
                rangZoom.start = len - defaultRang;
            } else {
                rangZoom.end = len;
                rangZoom.start = 0;
            }
            return {
                timeData : timeData,
                curData : curData,
                avgData : avgData,
                traData : traData,
                updData : updData,
                rangZoom : rangZoom,
                prec : prec,
                defaultRang : defaultRang,
                data:data
            }
        },
        
        fromatDate : function(time,type){
            var t = new Date(time),
                _format = type || "yyyy-MM-dd";
            function zeroRepair(num){
                return (num < 10 ? '0' : '') + num;
            }
            return _format.replace(/yyyy|MM|dd|hh|mm|ss/g, function(a){
                switch(a){
                    case 'yyyy':
                        return zeroRepair(t.getFullYear());
                        break;
                    case 'MM':
                        return zeroRepair(t.getMonth() + 1);
                        break;
                    case 'mm':
                        return zeroRepair(t.getMinutes());
                        break;
                    case 'dd':
                        return zeroRepair(t.getDate());
                        break;
                    case 'hh':
                        return zeroRepair(t.getHours());
                        break;
                    case 'ss':
                        return zeroRepair(t.getSeconds());
                        break;
                }
            })
        },
       
        toNumber : function(str){
            var v = '';
            if(!str || str == '0.00' || str == '0.00%'){
                v = '-';
            } else {
                v = Number(str);
            }
            return v;
        },
        getArrayMax : function(arr){
            return Math.max.apply( Math, arr );
        },
        getArrayMin : function(arr){
            return Math.min.apply( Math, arr );
        }
    };

var data = tool.lineSplitData(stockData);
option = {
	    backgroundColor: 'rgba(255,255,255,1)',
	    animation: false,
	    //legend: {show:false},
	    legend: {
	        bottom: -5,
	        //top: '92%',
	        left: 'center',
	        data: ['当前价', '均价',],

	    },
	    tooltip: {
	        trigger: 'axis',
	        showContent:true,
	        transitionDuration:0,
	        axisPointer: {
	            show:true,
	            type: 'line',
	            lineStyle:{
	                shadowOffsetY:170,
	                width:1

	            },
	            axis:'x',
	            crossStyle:{
	                color:'#333',
	                type:'solid',
	                shadowBlur:0,
	                width:1
	            }
	        },
	        position:[0,0],
	        padding:0,
	        backgroundColor:'cleanColor',
	        extraCssText: 'width:85%;line-height:20px;opacity:1;filter:alpha(opacity=100)',
	        textStyle:{
	            color:'#2e333f',
	            fontSize:12
	        },
	        formatter: function (params) {
	            var param = params[0] || params,
	                idx = param.dataIndex;
	            var html = '<div style="width: 85%">';
	            html += '<span style="margin:0 5px">时间:' + param.name + '</span>';
	            if(data.curData[idx] && data.curData[idx] != '-'){
	                html += '<span style="margin:0 5px">当前价:' + data.curData[idx] + '(' + (data.updData[idx] * 100).toFixed(2) + '%)</span>';
	            }
	            if(data.traData[idx] && data.traData[idx] != '-'){
	                html += '<span style="margin:0 5px">成交量:' + data.traData[idx] + '</span>';
	            }
	            if(data.avgData[idx] && data.avgData[idx] != '-'){
	                html += '<span style="margin:0 5px">均价:' + data.avgData[idx] + '</span>';
	            }
	            html += '</div>';
	            return html
	        }
	    },
	    toolbox: {show:false},
	    brush: {show:false},
//	    toolbox: {
//	        feature: {
//	            dataZoom: {
//	                yAxisIndex: false
//	            },
//	            brush: {
//	                type: ['lineX', 'clear']
//	            }
//	        }
//	    },
//	    brush: {
//	        xAxisIndex: 'all',
//	        brushLink: 'all',
//	        outOfBrush: {
//	            //改变图像透明度
//	            colorAlpha: 100
//	        }
//	    },
	    grid: [
	        {
	            left: '10%',
	            right: '8%',
	            top: '10%',
	            height: '50%'
	        },
	        {
	            left: '10%',
	            right: '8%',
	            top: '63%',
	            height: '16%'
	        }
	    ],
	    xAxis:[

	        {type: 'category',
	        data: data.timeData,
	        scale: true,
	        axisLine: {onZero: false,lineStyle:{color:'#ddd'}},
	        splitLine: {show: false},
	        z:3,
	        splitNumber:3,
	        axisLabel: {
	            textStyle:{color:'#2e333f'},
	            /*interval:function(index,value){
	                console.log(index);
	                console.log(value);
	                return [index,value]
	            },*/
	            formatter: function (value) {
	                //console.log(value);
	                return value;
	                //return value.toFixed(2)
	            }
	        },

	        axisTick: {show: false}
	        },
	        {
	            type: 'category',
	            gridIndex: 1,
	            data: data.timeData,
	            scale: true,
	            boundaryGap : false,
	            axisLine: {onZero: false},
	            axisTick: {show: false},
	            splitLine: {show: false},
	            axisLabel: {show: false},
	            splitNumber: 3,
	            silent:true
	        }

	    ],
	    yAxis:[
	        //{
	        //    scale: true,
	        //
	        //},
	        {
	            scale: true,
	            boundaryGap : [0.1,0.1],
	            splitNumber:5,

	            /*min:curMin,
	            max:curMax,
	            interval:(curMax-curMin)/5,*/
	            z:3,
	            axisLabel: {
	                inside: true,
	                textStyle:{color:'#2e333f'},
	                formatter: function (value) {
	                    return value.toFixed(2)
	                }
	            },
	            axisTick: {show: false,inside: true},
	            axisLine:{show: true},
	            splitLine: {show: true,lineStyle:{color:'#E3E3E3'}},
	            splitArea: {show: true}
	        },
	        {
	            scale: true,
	            boundaryGap : [0.1,0.1],
	            splitNumber:5,
	            z:3,
	            /*min:updMin,
	            max:updMax,
	            interval:(updMax-updMin)/5,*/
	            axisLabel: {
	                inside: true,
	                textStyle:{color:'#2e333f'},
	                formatter: function (value) {
	                    //var avg = (value - data.prec) / data.prec;
	                    return (value * 100).toFixed(2) + '%'
	                }
	            },
	            axisTick: {show: false,inside: true},
	            axisLine:{show: true},
	            splitLine: {show: false,lineStyle:{color:'#F0F0F0'}},
	            //splitArea: {show: true}
	        },
	        {
	            scale: true,
	            gridIndex: 1,
	            splitNumber: 2,
	            axisLabel: {show: false},
	            axisLine: {show: false},
	            axisTick: {show: false},
	            splitLine: {show: false}
	        }
	    ],
	    dataZoom:[{
	        show: true,
	        xAxisIndex: [0, 1],
	        type: 'slider',
	        top: '85%',
	        filterMode: 'empty',
	        startValue:data.rangZoom.start,
	        endValue:data.rangZoom.end,
	        zoomLock:false
	    },
	    ],
	    series: [
	        {
	            name: '当前价',
	            type: 'line',
	            data: data.curData,
	            smooth:true,
	            yAxisIndex:0,
	            symbol:'emptyCircle',
	            showSymbol:true,
	            z:3,
	            //线和区域的颜色
	            lineStyle: {
	                normal: {
	                    width:0.7
	                },

	            },
	            itemStyle:{
	                normal: {
	                    color:'#00f',

	                },
	            },
	            //areaStyle: {normal:{color:'#ffffff'}}
	        },
	        {
	            name: '均价',
	            type: 'line',
	            data: data.avgData,
	            smooth:true,
	            yAxisIndex:0,
	            //color:'green',
	            symbol:'emptyCircle',
	            showSymbol:true,
	            z:3,
	            //线和区域的颜色
	            lineStyle: {
	                normal: {
	                    //color:'green',
	                    width:1
	                }
	            },
	            itemStyle:{
	                normal: {
	                    color:'#fec660',

	                },
	            },
	            //areaStyle: {normal:{color:'#ffffff'}}
	        },
	        {
	            name: '涨跌幅',
	            type: 'line',
	            data: data.updData,
	            smooth:true,
	            symbol: 'none',
	            yAxisIndex:1,
	            showSymbol:false,
	            lineStyle: {
	                normal: {
	                    color:'#f40',
	                    width:0
	                }
	            }
	        },
	        //阳线设置
	        {
	            name: '成交量',
	            type: 'bar',
	            xAxisIndex: 1,
	            yAxisIndex: 2,
	            data: data.traData,

	            itemStyle:{
	                normal:{
	                	color:function(params){
	                		var d=data.data;
	                		var p=d[params.dataIndex];
	                		var n=d[params.dataIndex-1];
	                		//console.log(n);
	                		//console.log(params.dataIndex);
	                		if(params.dataIndex==0){
	                			return "red";
	                		}
							if(p.s11>n.s11){
								return "red";
							}else{
								return "green";
							}
	                	}
	                }

	            }
	        }
//	        ,
	        //阴线设置
//	        {
//	            name: '成交量',
//	            type: 'bar',
//	            xAxisIndex: 1,
//	            yAxisIndex: 2,
//	            data: data.traData,
//
//	            itemStyle:{
//	                normal:{
//	                    color:'white',
//	                    borderColor: 'green',
//	                }
//
//	            }
//	        },
	    ]
	};
	myChart.setOption(option)
	}