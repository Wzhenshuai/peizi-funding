$(function() {

	function Grid(context, width, height, xLen, yLen, leftTop) {
		this.leftTop = leftTop;
		this.context = context;
		this.width = width;
		this.height = height;
		this.xLen = xLen;
		this.yLen = yLen;
		this.context = context;

	}

	Grid.prototype.draw = function() {
		var xLen = this.xLen,
			yLen = this.yLen,
			xStep = this.height/(xLen-1),
			yStep = this.width/(yLen-1),
			leftTop = this.leftTop,
			width = this.width,
			height = this.height,
			outLine = [],
			xLine = null,
			yLine = null,
			middleLine = [],
			path = [];

		for(var i = 0; i < xLen; i++) {
			xLine = ['M', Math.round(leftTop.x) + .5, Math.round(leftTop.y + xStep*i) + .5, 'H', Math.round(width + leftTop.x) + .5];
			if(i == 0 || i == xLen -1) {
				outLine = outLine.concat(xLine);
			} else if(i == Math.floor((xLen-1)/2)){
				middleLine = middleLine.concat(xLine);
			} else {
				path = path.concat(xLine);
			}
		}

		for(var i = 0; i < yLen; i++) {
			yLine = ['M', Math.round(leftTop.x + yStep*i) + .5, Math.round(leftTop.y) + .5, 'V', Math.round(height + leftTop.y) + .5];
			if(i == 0 || i == yLen -1) {
				outLine = outLine.concat(yLine);
			} else {
				path = path.concat(yLine);
			}
		}
		this.context.path(outLine).attr('stroke', '#ccc').toBack();
		this.context.path(middleLine).attr('stroke', '#ccc').toBack();
		this.context.path(path).attr('stroke', '#ccc').attr("stroke-dasharray", "-").toBack();
	};

	function Line(context, width, height, data, lastDayPrice, minPrice, maxPrice, leftTop, type) {
		this.lastDayPrice = lastDayPrice*1;
		this.minPrice = minPrice*1;
		this.maxPrice = maxPrice*1;

		this.leftTop = leftTop;
		this.data = data;
		this.width = width;
		this.height = height;
		this.context = context;
		this.type = type ? type : 'actual';

		this.config = {
			actualLineColor: '#75b4f5',
			averageLineColor: '#ff5723'
		};
		if(data && data[0] && data[0][0]) {
			this.initBreakPoint(new Date(data[0][0]));
		} else {
			this.initBreakPoint(new Date());
		}
		this.initData();
	}

	Line.prototype.initData = function() {
		var data = this.data,
			len = data.length,
			i = 0,
			path = [],
			bgPath = [],
			closePoint = null,
			lastPointX = null,
			pointX = null,
			pointY = null;


		for(; i < len; i++) {
			pointX = this.getCoorXByTime(data[i][0]);
			if(pointX) {
				pointY = this.getCoorYByPrice(data[i][1]);
				if(path.length > 0) {
					path = path.concat(['L', pointX, pointY]);
				} else {
					path = path.concat(['M', pointX, pointY]);
					closePoint = ['L', pointX, pointY, 'z'];
				}
				lastPointX = pointX;
			}
		}

		//bottomPoint
		bgPath = path.concat(['L', lastPointX, this.leftTop.y + this.height]);
		bgPath = bgPath.concat(['L', this.leftTop.x, this.leftTop.y + this.height]);
		bgPath = bgPath.concat(closePoint);
		this.path = path;
		this.bgPath = bgPath;
		return path;
	};

	Line.prototype.initBreakPoint = function(date) {
		//1. 9:30, 2.11:30, 3.13:00, 4.15:00
		var times = ['9:30', '11:30', '13:00', '15:00'],
			breakPoints = ['morningStart', 'morningEnd', 'afternoonStart', 'afternoonEnd'],
			len = breakPoints.length,
			i = 0,
			timeArr;

		for(; i < len; i++) {
			timeArr = times[i].split(':');
			this[breakPoints[i]] = this.getTimeByDateAndTime(date, timeArr[0], timeArr[1]);
		}
	};

	Line.prototype.getCoorYByPrice = function(price) {
		var maxPrice = this.maxPrice,
			minPrice = this.minPrice,

			unit = (maxPrice - price)/(maxPrice - minPrice);


		return unit*this.height + this.leftTop.y;
	};

	Line.prototype.getCoorXByTime = function(timeStamp) {

		var flag = 0,
			timePoint = new Date(timeStamp),
			morningStart = this.morningStart,
			morningEnd = this.morningEnd,
			afternoonStart = this.afternoonStart,
			afternoonEnd = this.afternoonEnd;

		if(timePoint.getTime() - morningStart.getTime() < 0) {
			return false;
		} else if(timePoint.getTime() - morningStart.getTime() >= 0 && timePoint.getTime() - morningEnd.getTime() <= 0) {
			flag = (timePoint.getTime() - morningStart.getTime())/1000/60;
		} else if(timePoint.getTime() - morningEnd.getTime() > 0 && timePoint.getTime() - afternoonStart.getTime() < 0) {
			return false;
		} else if(timePoint.getTime() - afternoonStart.getTime() >= 0 && timePoint.getTime() - afternoonEnd.getTime() <= 0) {
			flag = (timePoint.getTime() - morningStart.getTime())/1000/60 - 90;
		} else if(timePoint.getTime() - afternoonEnd.getTime() > 0) {
			flag = 120;
			return false;
		}

		return flag/240*this.width + this.leftTop.x;
	};

	Line.prototype.getTimeByDateAndTime = function(date, hour, minute) {
		var dateTime = new Date(date);
		dateTime.setHours(hour);
		dateTime.setMinutes(minute);
		dateTime.setSeconds(0);
		dateTime.setMilliseconds(0);
		return dateTime;
	};

	Line.prototype.draw = function() {
		if(this.type == 'actual') {
			this.context.path(this.path).attr('stroke', this.config.actualLineColor);
			this.context.path(this.bgPath).attr('fill', this.config.actualLineColor).
				attr('opacity', .3).
				attr('stroke', 'none');
		} else {
			this.context.path(this.path).attr('stroke', this.config.averageLineColor);
		}
	};

	function Label(context, data) {
	};

	/*
	 * 1. 最低价
	 * 2. 最高价
	 * 3. 价格线条数
	 */
	function Price(context, lastDayPrice, minPrice, maxPrice, len, height, leftTop, fontSize) {
		this.lastDayPrice = lastDayPrice*1;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.labels = [];
		this.len = len;
		this.height = height;
		this.context = context;
		this.leftTop = leftTop;
		this.fontSize = fontSize;

		this.config = {
			normalColor: '#333',
			highColor: '#fd4e4e',
			lowColor: '#30cc51'
		};

		this.initData();
	}

	Price.prototype.initData = function() {
		var steps = this.len - 1,
			maxPrice = this.maxPrice,
			minPrice = this.minPrice,
			priceStep = (maxPrice - minPrice)/steps,
			i = Math.floor(this.len/2) - 1,
			bottom = -i;

		this.labels.push(Price.format(maxPrice));
		for(; i >= bottom; i--) {
			if(0 == i) {
				this.labels.push(Price.format(this.lastDayPrice));
			} else {
				this.labels.push(Price.format(this.lastDayPrice + priceStep*i));
			}
		}
		this.labels.push(Price.format(minPrice));
	};

	Price.prototype.getWidth = function() {
		var maxLength = 0,
			i = 0,
			labels = this.labels,
			len = labels.length;
		for(; i < len; i++) {
			maxLength = Math.max(maxLength, labels[i].length);
		}
		return maxLength*(this.fontSize/2+1) + Math.floor(this.fontSize/2);
	};

	Price.format = function(num) {
		var num = Math.round(num*100)/100 + '';
		if(/\.\d*/g.test(num)) {
			return num.replace(/(\.\d)$/g, '$10');
		}
		return [num, '00'].join('.');
	};

	Price.prototype.draw = function() {
		var i = 0,
			len = this.len,
			context = this.context,
			step = this.height/(len-1),
			leftTop = this.leftTop,
			someOne = null,
			middle = Math.floor(len/2);
		
		for(; i < len; i++) {
			someOne = context.text(leftTop.x, leftTop.y + step*i, this.labels[i])
				.attr('text-anchor','start')
				.attr('font-size', this.fontSize);

			if(i == middle) {
				someOne.attr('fill', this.config.normalColor);
			} else if (i < middle){
				someOne.attr('fill', this.config.highColor);
			} else {
				someOne.attr('fill', this.config.lowColor);
			}
		}

	};

	/*
	 * 1. 最低价
	 * 2. 最高价
	 * 3. 价格线条数
	 */
	function Percent(context, lastDayPrice, minPrice, maxPrice, len, height, leftTop, fontSize) {
		this.lastDayPrice = lastDayPrice;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.labels = [];
		this.len = len;
		this.height = height;
		this.context = context;
		this.leftTop = leftTop;
		this.fontSize = fontSize;
		this.paddingRight = 5;

		this.config = {
			normalColor: '#333',
			highColor: '#fd4e4e',
			lowColor: '#30cc51'
		};

		this.initData();
	}

	Percent.prototype.initData = function() {
		var steps = this.len - 1,
			maxPrice = this.maxPrice,
			minPrice = this.minPrice,
			lastDayPrice = this.lastDayPrice,
			percent = (maxPrice - lastDayPrice)/lastDayPrice,
			percentStep = percent/Math.floor(steps/2),
			i = Math.floor(steps/2),
			low = -i;
		
		for(; i >= low; i--) {
			if(0 == i) {
				this.labels.push('0.00%');
			} else {
				this.labels.push(Percent.format(percentStep*(i)) + '%');
			}
		}
		//this.labels.push(this.format(minPercent));
	};

	Percent.prototype.getWidth = function() {
		var maxLength = 0,
			i = 0,
			labels = this.labels,
			len = labels.length;
		for(; i < len; i++) {
			maxLength = Math.max(maxLength, labels[i].length);
		}
		return maxLength*(this.fontSize/2 + 1) + Math.floor(this.fontSize/2) + this.paddingRight;
	};

	Percent.format = function(num) {
		var num = Math.round(num*10000)/100 + '';
		if(/\.\d*/g.test(num)) {
			return num.replace(/(\.\d)$/g, '$10');
		}
		return [num, '00'].join('.');
	};

	Percent.prototype.draw = function() {
		var i = 0,
			len = this.len,
			someOne = null,
			middle = Math.floor(len/2),
			context = this.context,
			fontSize = this.fontSize,
			step = this.height/(len-1),
			leftTop = this.leftTop;
		
		for(; i < len; i++) {
			someOne = context.text(leftTop.x-this.paddingRight, leftTop.y + step*i, this.labels[i])
				.attr('text-anchor','end')
				.attr('font-size', fontSize);

			if(i == middle) {
				someOne.attr('fill', this.config.normalColor);
			} else if (i < middle){
				someOne.attr('fill', this.config.highColor);
			} else {
				someOne.attr('fill', this.config.lowColor);
			}
		}

	};

	function PricePercent(context, width, height, lastDayPrice, minPrice, maxPrice, leftTop, fontSize) {
		this.context = context;
		this.width = width;
		this.height = height;
		this.lastDayPrice = lastDayPrice;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.leftTop = leftTop;
		this.fontSize = fontSize;
	}

	PricePercent.prototype.draw = function() {
		var fontSize = this.fontSize,
			height = this.height,
			width = this.width,
			leftTop = this.leftTop,
			maxPrice = Price.format(Math.round(this.maxPrice*100)/100),
			minPrice = Price.format(Math.round(this.minPrice*100)/100),
			maxPercent = Percent.format(Math.round((maxPrice-this.lastDayPrice)/this.lastDayPrice*10000)/10000) + '%',
			minPercent = '-' + maxPercent;

		var maxPricePoint = [leftTop.x + 5, leftTop.y + fontSize/2 + 5];
		var minPricePoint = [leftTop.x + 5, leftTop.y + height - fontSize/2 - 5];
		var maxPercentPoint = [leftTop.x + width - 5, leftTop.y + fontSize/2 + 5];
		var minPercentPoint = [leftTop.x + width - 5, leftTop.y + height - fontSize/2 - 5];

		this.context.text(maxPricePoint[0], maxPricePoint[1], maxPrice).attr('text-anchor', 'start');
		this.context.text(minPricePoint[0], minPricePoint[1], minPrice).attr('text-anchor', 'start');
		this.context.text(minPercentPoint[0], minPercentPoint[1], minPercent).attr('text-anchor', 'end');
		this.context.text(maxPercentPoint[0], maxPercentPoint[1], maxPercent).attr('text-anchor', 'end');
	};

	function XTime(context, times, width, leftTop, fontSize) {
		this.times = times;
		this.context = context;
		this.width = width;
		this.leftTop = leftTop;
		this.fontSize = fontSize;
	}

	XTime.prototype.draw = function() {
		var times = this.times,
			context = this.context,
			len = this.times.length,
			step = this.width/(len - 1),
			leftTop = this.leftTop,
			i = 0;

		for(; i < len; i++) {
			context.text(leftTop.x + step*i, leftTop.y + this.fontSize/2, times[i]).attr('font-size', this.fontSize);
		}
	};

	XTime.prototype.getHeight = function() {
		return this.fontSize;
	};


	function TimeShare(id, width, height, data, config) {
		this.config = $.extend({
			type: 1,
			xLen: 7,
			fontSize: 12,
			yLen: 5
		}, config);
		this.width = width;
		this.height = height;
		this.data = data;
		this.elements = [];
		this.lineHeight = 0;
		this.id = id;
		this.paper = Raphael(id, this.width, this.height);

		this.init();
	}

	TimeShare.prototype.destroy = function() {
		this.paper && this.paper.remove();
		this.blanket && this.blanket.destory();
	};

	TimeShare.prototype.init = function() {
		var config = this.config;
		this.initData();
		if(config.type == 1) {
			//通过计算价格、百分比、时间轴的宽度、高度、leftTop, 得出grid, line leftTop, width, height
			//this.lineHeight = 15;
			this.initLabel();
		} else {
			this.initAngelTip();
		}

		this.initGridLine();
	};

	TimeShare.prototype.draw = function() {
		var i = 0,
			len = this.elements.length;
		for(; i < len; i++) {
			this.elements[i].draw();
		}
		
		if(this.suspended == 'true' || this.minPrice > this.maxPrice) {
			this.drawTipText('停牌');
			//return;
		} else if(this.callAction == '1'){
			this.drawTipText('集合竞价');
			//return;
		} else {
			//需要把蒙层放到最上面
			if(this.config.type == 1) {
				this.blanket = new Blanket(this.paper, this.gridConfig.leftTop, this.gridConfig.width, this.gridConfig.height, this.timesharedata, this.id);
				this.blanket.draw();
			}
		}
	};

	TimeShare.prototype.drawTipText = function(tipText, leftTop) {
		var leftTop = this.gridConfig.leftTop,
			//rightBottom = this.rightBottom,
			paper = this.paper,
			centerCoor = {
				x: Math.round(leftTop.x + this.gridConfig.width/2),
				y: Math.round(leftTop.y + this.gridConfig.height/2)
			};

		var text = paper.text(centerCoor.x, centerCoor.y, tipText);
		text.attr({'font-size': '20px'}).toFront();

		var box = text.getBBox(),
			backgroundWidth = box.width + 20,
			backgroundHeight = box.height + 20,
			backgroundLeftTop = {
				x: centerCoor.x - Math.round(backgroundWidth/2),
				y: centerCoor.y - Math.round(backgroundHeight/2)
			};
		var bg = paper.rect(backgroundLeftTop.x, backgroundLeftTop.y, backgroundWidth, backgroundHeight);
		bg.attr({fill: '#fff', 'stroke': '#fff'}).toFront();
		text.toFront();
	};

	TimeShare.prototype.initGridLine = function() {
		this.grid = new Grid(this.paper, this.gridConfig.width, this.gridConfig.height, this.config.xLen, this.config.yLen, this.gridConfig.leftTop);
		//this.grid.draw();
		this.elements.push(this.grid);

		if(this.suspended != 'true' && this.callAction != '1' && this.minPrice < this.maxPrice) {
			this.actualline = new Line(this.paper, this.gridConfig.width, this.gridConfig.height, this.actualPrice, this.lastDayPrice, this.minPrice, this.maxPrice, this.gridConfig.leftTop);
			//this.actualline.draw();
			this.elements.push(this.actualline);

			this.averageline = new Line(this.paper, this.gridConfig.width, this.gridConfig.height, this.averagePrice, this.lastDayPrice, this.minPrice, this.maxPrice, this.gridConfig.leftTop, 'average');
			//this.averageline.draw();
			this.elements.push(this.averageline);
		}
	};

	//初始化数据，根据状态(停牌、集合竞价、正常开盘) 得出最低价、最高价以及是否需要画线
	TimeShare.prototype.initData = function() {
		var data = this.data,
			stock = data.info.result,
			priceGap  = 0,
			minPrice  = 0,
			MaxPrice  = 0,
			lastDayPrice = stock.s5*1,
			len = stock.s20 ? stock.s20.length : 0,
			i = 0,
			actualPrice = [],
			averagePrice = [];

		this.suspended = stock.s6;
		this.callAction = stock.s7;

		if(this.suspended == 'true' || this.callAction == '1' || stock.s4*1 > stock.s3*1) {
			priceGap = Math.max(stock.s8*1 - lastDayPrice, lastDayPrice - stock.s9*1);
			if(stock.s4*1 > stock.s3*1) {
				this.suspended = 'true';
			}
		} else {
			
			priceGap = Math.max(stock.s3*1 - lastDayPrice, lastDayPrice - stock.s4*1);
		}


		this.minPrice = lastDayPrice - priceGap;
		this.maxPrice = lastDayPrice + priceGap;
		this.lastDayPrice = lastDayPrice;

		for(; i < len; i++) {
			actualPrice.push([stock.s20[i].s15, stock.s20[i].s11]);
			averagePrice.push([stock.s20[i].s15, stock.s20[i].s14]);
		}
		//new line 7.3
		this.timesharedata = stock.s20;

		this.actualPrice = actualPrice;
		this.averagePrice = averagePrice;

	};

	TimeShare.prototype.lineTip = function(leftTop) {
		var height = 15;

		this.paper.rect(leftTop, 2, 20, 11)
			.attr('stroke', 'none')
			.attr('fill', '#75b4f5');

		this.paper.text(leftTop + 40, 7, '分时线')
			.attr('fill', '#75b4f5');

		this.paper.rect(leftTop + 75, 2, 20, 11)
			.attr('stroke', 'none')
			.attr('fill', '#ff5723');

		this.paper.text(leftTop + 110, 7, '均线')
			.attr('fill', '#ff5723');

		return height;
	};
	

	//先计算是否有价格，百分比，时间轴
	TimeShare.prototype.initLabel = function() {
		var xLen = this.config.xLen,
			yLen = this.config.yLen,
			fontSize = this.config.fontSize,
			ctx = this;

		//分时线和均线的提示
		var height = this.lineHeight;

		//2. 计算价格的宽度, leftTop
		this.price = new Price(this.paper, this.lastDayPrice, this.minPrice, this.maxPrice, xLen, this.height - fontSize -fontSize - height, {x: 0, y: fontSize/2 + height}, fontSize);
		this.elements.push(this.price);

		//3. 计算百分比的宽度, leftTop
		this.percent = new Percent(this.paper, this.lastDayPrice, this.minPrice, this.maxPrice, xLen, this.height - fontSize - fontSize -height, {x: this.width, y: fontSize/2 + height}, fontSize);
		this.elements.push(this.percent);


		var gridWidth = ctx.width - ctx.price.getWidth() - ctx.percent.getWidth();


		//1. 计算时间轴的高度, leftTop
		var times = ['9:30', '10:30', '11:30/13:00', '14:00', '15:00'];

		this.xTime = new XTime(this.paper, times, gridWidth, {x: this.price.getWidth(), y: this.height-fontSize}, fontSize);
		this.elements.push(this.xTime);


		//this.lineTip(this.price.getWidth());
		//this.timeTip(this.price.getWidth());


		this.gridConfig = {
			leftTop: {
						 x: ctx.price.getWidth(),
						 y: fontSize/2 + height
					 },
			width: gridWidth,
			height: ctx.height - ctx.xTime.getHeight() - fontSize - height
		};


		//this.blanket = new Blanket(this.paper, this.gridConfig.leftTop, this.gridConfig.width, this.gridConfig.height, this.timesharedata, this.id);
		//this.elements.push(this.blanket);
		//this.blanket.draw();

	};

	TimeShare.prototype.initAngelTip = function() {
		var fontSize = this.config.fontSize,
			width = this.width,
			height = this.height;

		this.gridConfig = {
			leftTop: {
						 x: 1,
						 y: 1
					 },
			width: width - 2,
			height: height - 2
		};

		this.pricePercent = new PricePercent(this.paper, this.gridConfig.width, this.gridConfig.height, this.lastDayPrice, this.minPrice, this.maxPrice, this.gridConfig.leftTop, fontSize);
		//this.pricePercent.draw();
		this.elements.push(this.pricePercent);

	};

	//捕捉鼠标hover事件, 根据鼠标所在位置拿到坐标相应位置
	function Blanket(context, leftTop, width, height, dataList, id) {
		this.context = context;
		this.leftTop = leftTop;
		this.width = width;
		this.height = height;
		this.id = id;
		this.dataList = dataList;

		this.timeShareData = new TimeShareData(dataList);
		//this.initEvent();
	}
	Blanket.prototype.getXbyIndex = function(index) {
		return this.width/this.timeShareData.getLenOfDatas()*index + this.leftTop.x;
	};

	Blanket.prototype.draw = function() {
		var leftTop = this.leftTop,
			width = this.width,
			blanketObj = this,
			timeShareData = this.timeShareData,

			height = this.height;

		this.timeTip(leftTop.x);
		this.rect = this.context.rect(leftTop.x, leftTop.y, width, height)
			.attr({stroke: "none", fill: "#fff", opacity: 0});

		//var timer = null;
		var lastObj = timeShareData.getLastObj(),
			lastIndexOfValid = timeShareData.getLastIndexOfValid();

		if(lastObj) {
			blanketObj.showTip(lastObj);
		} else {
		}
		this.rect.mousemove(function(e) {

			var x = e.layerX ? e.layerX: e.offsetX, 
				index = blanketObj.getIndexByCoor(x - leftTop.x);
			
			if(index > lastIndexOfValid) {
				index = lastIndexOfValid;
			}
			var obj = timeShareData.getObjByIndex(index);
			try {
				blanketObj.showTip(obj);
			} catch(e) {
				console && console.log('there is no data on this minute');
			}
			blanketObj.drawLine(blanketObj.getXbyIndex(index));
			
		});
	};

	Blanket.prototype.drawLine = function(x) {
		if(this.line) {
			this.line.attr({'path': ['M', x, this.leftTop.y, 'V', this.leftTop.y + this.height].join(' ')});
		} else {
			this.line = this.context.path(['M', x, this.leftTop.y, 'V', this.leftTop.y + this.height]).toFront();
			this.line.attr('stroke', '#bcbcbc');
		}
	};

	Blanket.prototype.timeTip = function(leftTop) {
		var container = $('#' + this.id),
			tip = $('.time-tip', container);

		container.css('position', 'relative')
				 .css('padding-top', '15px');
		if(tip.size() == 0) {
			tip = $('<div class="time-tip"></div>').css({'position':'absolute','left':leftTop, 'top':'0'});
			tip.prependTo(container);
		}
		this.tip = tip;
		return this.tip;
	};

	Blanket.prototype.formatTime = function(timestamp) {
		var formatNumber = function(number) {
			return number < 10 ? '0' + number: number+'';
		},
			time = new Date(timestamp);
		return [formatNumber(time.getHours()), formatNumber(time.getMinutes())].join(':');
	};

	Blanket.prototype.showTip = function(obj) {
		if(obj) {
			var time = this.formatTime(obj.s15),
				percent = Math.round(obj.s12*10000)/100,
				volume = Math.floor(obj.s16/100) < 10000 ? Math.floor(obj.s16/100): Math.floor(obj.s16/100/10000*100)/100 + '万',
				volume = volume + '手';
			var html = [
				'<span>时间&nbsp;', time, '</span>',
				'<span><label class="minute">分时价</label>&nbsp;',
				'', obj.s11, '(', percent, '%)', '</span>',
				'<span>成交量&nbsp;',
				'', volume, '</span>',
				'<span class="average">均价&nbsp;',
				'<label>', obj.s14, '</label></span>'
			];
		} else {
			var html = [
				'<span>时间--</span>',
				'<span>分时价&nbsp;',
				'<label class="minute">--(--%)</label>', '</span>',
				'<span>成交量&nbsp;',
				'--</span>',
				'<span>均价&nbsp;',
				'<label class="average">--</label></span>'
			];
		}
		this.tip.html(html.join(''));
		$('.minute', this.tip).css('color', '#75b4f5');
		$('.average', this.tip).css('color', '#ff5723');
		$('span', this.tip).css('margin-right', '5px');
	};
	
	Blanket.prototype.destory = function() {
		this.tip.remove();
	}

	Blanket.prototype.getIndexByCoor = function(x) {
		var unit = this.width/this.timeShareData.getLenOfDatas();
		return Math.round(x/unit);
	};

	//根据时间轴的间隔不同，x轴的点数是固定: 比如：每分钟都作为一个点的话，一天的时间段分别为：9：30 - 11：30， 13：00 - 15：00, 121 + 121 - 1 = 141
	//减去1是因为11:30和13:00是重叠的，所以只取一个点
    function TimeShareData(dataList) {
		this.unit = 1;
		this.rawData = dataList;
		this.timeData = this.concatArr(dataList);
		for(var i = 0; i < this.timeData.length; i++) {
			if(!this.timeData[i]) {
				console && console.log(i);
			}
		}
		
    }

	TimeShareData.prototype.getLenOfDatas = function() {
		//return this.timeData.length;
		return 240/this.unit;
	};

	TimeShareData.prototype.getObjByIndex = function(index) {
		return this.timeData[index] ? this.timeData[index]: null;
	};
	
	TimeShareData.prototype.getLastIndexOfValid = function() {
		return this.lastIndexOfValid;
	}

	//把有效时间段内的数据切分成不同的数组(每天的上午和下午9:30-11:30,13:00-15:00)
	TimeShareData.prototype.getArrs = function(dataList) {
		//获取到第天一下午时间段在大数组中的索引数组
		var indexArr = this.sliceData(dataList),
			i = 0,
			len = indexArr.length,
			arr = [],
			validArrLen = 0;

		//切分各有效时间段的数组
		for(; i < len; i++) {
			var key = indexArr[i].name,
				index = indexArr[i].index,
				subArr = dataList.slice(index[0], index[1]+1);
			arr.push({name:key, arr:subArr});
			validArrLen += subArr.length;
		}
		this.validArrLen = validArrLen;
		return arr;
	};
	//此方法会把各时间段的数组拼接到一个数组，并且决定中间点取上午11:30的点还是下午13：00的点
	TimeShareData.prototype.concatArr = function(dataList) {
		var arrs = this.clearArr(dataList),
			i = 0,
			len = arrs.length,
			tempObj = {},
			cleanedArr = [];

		//把每天上下午的数据分类合并
		for(; i < len; i++) {
			var newKey =  arrs[i].name.split('_')[0];
			if(!tempObj[newKey]) {
				tempObj[newKey] = [];
			}
			tempObj[newKey].push(arrs[i]);
		}

		//根据每天数据的情况，决定中间点取上午的11:30还是13:00
		for(var key in tempObj) {
			if(tempObj[key].length == 2) {
				//只有上午11:30有数据，下午13:00没有数据的情况下，中间点取13:00
				if(tempObj[key][0].arrs[tempObj[key][0].arrs.length-1] && !tempObj[key][1].arrs[0]) {
					var datas = tempObj[key][1].arrs;
					tempObj[key][1].arrs = datas.splice(1);
				} else { //否则取上午11:30时间点的数据
					var datas = tempObj[key][0].arrs;
					tempObj[key][0].arrs = tempObj[key][0].arrs.splice(0, datas.length-1);
				}
			}
		}

		//最后把所有时间段数组合并，并形成索引
		for(var key in tempObj) {
			for(var j = 0; j < tempObj[key].length; j++) {
				cleanedArr = cleanedArr.concat(tempObj[key][j].arrs);
			}
		}

		return cleanedArr;
	};

	//为每天的上午时间段生成相应的数组, 因为x轴时间点的数量是固定的，所以会生成固定长度的数组，不在9:30-11:30 and 13:00-15:00区间的数据会被清掉，重复时间点取最后一个，某时间点没有数据的为undefined
	TimeShareData.prototype.clearArr = function(dataList) {
		var arr = this.getArrs(dataList),
		    len = arr.length,
			unit = this.unit,
			i = 0,
			j = 0
			subArr = null,
			subArrLen = 0,
			//根据时间间隔单位(分钟)计算子数组的长度
			subArrExpectedLen = 120/unit + 1, 
			cleanedArr = [],
			finalArr = [];
		
		//初始化子数组
		for(; i < len; i++) {
			cleanedArr[i] = new Array(subArrExpectedLen);
		}
		for (i = 0; i < len; i++) {
			subArr = arr[i].arr;
			subArrLen = subArr.length;
			for(j = 0; j < subArrLen; j ++) {
				//根据某个时间点得到它所在的索引，如果为有效值，就初始化所在子数组相对应的索引
				actualIndex = this.getIndexByTime(new Date(subArr[j].s15));
				if(actualIndex != null) {
					cleanedArr[i][actualIndex] = subArr[j];
					this.lastIndexOfValid = i*subArrExpectedLen + actualIndex;
				}
			}
			finalArr.push({name: arr[i].name, arrs: cleanedArr[i]});
		}

		return finalArr;
	};

	//根据上下午时间的返回不同的参数, 如果不在有效时间段内返回null
	TimeShareData.prototype.getMoonOrNoon = function(time) {
		var moonStart = 9*60 + 30,
			moonEnd = 11*60 + 30,
			noonStart = 13*60,
			noonEnd = 15*60
			timeMinutes = time.getHours() * 60 + time.getMinutes();

		if(timeMinutes >= moonStart && timeMinutes < moonEnd) {
			return 'moon';
		} else if(timeMinutes >= noonStart && timeMinutes <= noonEnd) {
			return 'noon';
		}
		return null;
	};

	//根据某个时间点得到它所在的索引
	TimeShareData.prototype.getIndexByTime = function(time) {
		//根据上下午时间的返回不同的参数, 如果不在有效时间段内返回null
		var flag = this.getMoonOrNoon(time),
			timeFlag = time.getHours() * 60 + time.getMinutes(),
			unit = this.unit,
			startFlag = null;

		//根据上下午的标识选择不同的参考点
		if(flag == 'moon') {
			startFlag = 9*60 + 30;
		} else if(flag == 'noon') {
			startFlag = 13*60;
		}
		//时间点在有效范围并且是在时间间隔点上(例如：间隔单位是5, 9:30, 9:35), 返回索引
		if(flag && startFlag && (timeFlag - startFlag)%unit == 0) {
			return (timeFlag - startFlag)/unit;
		}
		return null;
	};

	//获取到第天一下午时间段在大数组中的索引数组
	TimeShareData.prototype.sliceData = function(dataList) {
		var i = 0,
			len = dataList.length,
			j = len-1,
			startArr = {},
			endArr = {},
			allArr = [];

		//由于判断取哪个索引为上下午的开始索引，结束索引,原则是：开始索引是大于等于9:30或者13:00的第一个索引，而结束索引是小于等于11:30和13:00的最后一个索引，所以为了降低复杂度
		//要从前到后取首先满足条件的开始索引，而从后往前取首先满足条件的结束索引
		//由于有不同的日期，所以会以日期加上下午作为key来存储索引，最终再把每天的上午开始结束索引与下行开始结束索引相对应拼接到一起
		
		//从前到后取上午和下午的开始索引
		for(; i < len; i++) {
			var datetime = new Date(dataList[i].s15),
				
				key = this.getSliceKey(datetime),
				minutes = datetime.getHours()*60 + datetime.getMinutes();
			if(minutes >= (9*60 + 30) && key && !startArr[key]) {
				startArr[key] = i;
			} else if(minutes >= 13*60 && key  && !startArr[key]) {
				startArr[key] = i;
			}
		}

		//从后到前取上午和下午的结束索引
		for(; j >= 0; j--) {
			var datetime = new Date(dataList[j].s15),
				key = this.getSliceKey(datetime),
				minutes = datetime.getHours()*60 + datetime.getMinutes();
			//取首先满足小于等于15:00和11:30条件的索引
			if(minutes <= 15*60 && key && !endArr[key]) {
				endArr[key] = j;
				//得到最后一个点，用于初始化分时提示文案
				if(!this.lastIndex) {
					this.lastIndex = j;
				}
			} else if(minutes <= (11*60 + 30) && key && !endArr[key]) {
				endArr[key] = j;
				//得到最后一个点，用于初始化分时提示文案
				if(!this.lastIndex) {
					this.lastIndex = j;
				}
			}
		}

		//根据key组合每天上下午的开始结束索引
		for(var key in startArr) {
			allArr.push({name:key, index:[startArr[key], endArr[key]]});
		}

		return allArr;
	};

	TimeShareData.prototype.getLastObj = function() {
		if(this.lastIndex) {
			return this.rawData[this.lastIndex];
		} else {
			return null;
		}
	};

	TimeShareData.prototype.getDate = function(datetime) {
		return [datetime.getFullYear(), datetime.getMonth(), datetime.getDate()].join('');
	};

	//根据时间点生成key
	TimeShareData.prototype.getSliceKey = function(datetime) {
		var dateStr = this.getDate(datetime),
			minutes = datetime.getHours()*60 + datetime.getMinutes();
		if(minutes >= (9*60 + 30) && minutes <= (11*60+30)) {
			return [dateStr, 'moon'].join('_');
		} else if(minutes >= 13*60 && minutes <= 15*60) {
			return [dateStr, 'noon'].join('_');
		}
		return null;
	};
	

	//window.TimeShareData = TimeShareData;

	window.TimeShare = TimeShare;
	window.Grid = Grid;
});
