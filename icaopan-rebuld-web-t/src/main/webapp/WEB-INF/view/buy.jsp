<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<title>投资管理平台</title>
<link href="<%=basePath%>css/main.css"
	rel="stylesheet">
<style type="text/css">
@
keyframes resizeanim {from { opacity:0;
	
}

to {
	opacity: 0;
}

}
.resize-triggers {
	animation: 1ms resizeanim;
	visibility: hidden;
	opacity: 0;
}

.resize-triggers, .resize-triggers>div, .contract-trigger:before {
	content: " ";
	display: block;
	position: absolute;
	top: 0;
	left: 0;
	height: 100%;
	width: 100%;
	overflow: hidden;
}

.resize-triggers>div {
	background: #eee;
	overflow: auto;
}

.contract-trigger:before {
	width: 200%;
	height: 200%;
}
</style>
</head>
<body style="">
	<div id="app">
		<div class="main-page">
			<div data-v-829b50d8="" class="client-head">
				<div data-v-829b50d8="" class="client-header">
					<!---->
					<div data-v-829b50d8="" class="client-title">投资管理平台</div>
					<div data-v-829b50d8="" class="login-tip">交易已登录</div>
					<div data-v-829b50d8="" class="tool-bar">
						<div data-v-829b50d8="" class="minimize-btn"></div>
						<div data-v-829b50d8="" class="maximize-btn"></div>
						<div data-v-829b50d8="" class="close-client-btn"></div>
					</div>
				</div>
				<!---->
			</div>
			<div class="navigation-menu">
				<ul class="el-menu">
					<li class="el-menu-item is-active" style="padding-left: 20px;"><img
						src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABUAAAAUCAYAAABiS3YzAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6M0FBMzE3OUJBMDNEMTFFNjg2RjhEQ0YxRDM2NDM0RjciIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6M0FBMzE3OUNBMDNEMTFFNjg2RjhEQ0YxRDM2NDM0RjciPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDozQUEzMTc5OUEwM0QxMUU2ODZGOERDRjFEMzY0MzRGNyIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDozQUEzMTc5QUEwM0QxMUU2ODZGOERDRjFEMzY0MzRGNyIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/Pu6YTbMAAAMuSURBVHjarFRNTBNBFP622/+WreVgaKiUSDWRGExBbm20CEQE4SA3PXAxFq8kcCFemnDgLBgSNIToGRMSEiAQTnDACKXykyohoaAp/ZES6O9219mhXSU2MYBv8nZmX2a+fO+9bwYAFKIokgnfiYuX9LiEpSQfgWGYT/V36y0upwuMgsFFjCFjemaaI1g7ynzsdsfjDpwkTsAq2POBMQzUGjU0Gg06n3TC/8VfWQBNt7S2aLRaLd10Hkun0ljzrWE3uAue52msAAr7DTs4jrtQ6gaDAdvvt+V/GTS5lwSn4TBeNY6IL4JOWydKxVIkM0lMfJxAmaUM7e3t8Pv9mJ2dpQTc992oslfBUm5BvtlnQUeORqBT6dBLBqYJ8zY7XGYXEqkEfKs+Wm/J5ubm0NPTQ2s49m6MgmYz2TPMZVBvtRc8x+fzIToTFDBbzJDG0Jsh+UChRGazGTqdjq7/ZHkG1HRoQpSP5qsPKq0cn0NOyCEcDkOv14NlWRwfH8Nut8NqtYJVsggGg+BKuOKghg8GJLIJQpEoLgnoOT2ie1HETmLoftmN5ofNNOWpqSkEAgHKbnJyEg6HA5FIpDjo8otlmEwcBFYNlghZReUMXCVjfmGeSm1wcJDW1Gg0UtBUKoXq6uq/1KAoLL7tR/H5Zxq6pkeYqLqJ4OIi9g7CCGwF0Nbaht7eXng8HgwMDCCRSBACJgy/HqZKiB/GizOt8DyHkdQLa2s4INf4OBbD9SsmCESDXq8XxhIjbZLFYqH7pSbZbDb6H4vGioNyvlVwJ6eyURMXiGvVZEW8tq5WPpDNZuWOZzIZ+aoWBV0gG6RbL4lqh7hbEBA5PEKGsB99O4oKWwW6urpo6pKFQiFaU8lUalVx0L2nz8ATAOs1K+4RduU1NeBVSogqFVSS5w86nU709fXBXGqGo85BYz/2f1C2Bb0y0oIExK/7IaQJ21uVVto9XqS5Er0qqB4Lj4dGq/ldCnKTJHmt+9fpg5JOp9H/ql8GnWlwNzQ1PmhEls+eFvQfpmAViMfj2NjcgEFvoJmsrKxgc2tziZEpM8wSmcpwOTsieHcKTKVZxH+yXwIMAGRWV8FaBZiXAAAAAElFTkSuQmCC"
						height="18" width="18" alt="" class="navigation-icon">
						<div class="navigation-menu-item">买入</div></li>
					<li class="el-menu-item" style="padding-left: 20px;"><img
						src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NTg3NkQzQkVBMDNEMTFFNkEzRTFDMUUyQjFFM0VDQUQiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6NTg3NkQzQkZBMDNEMTFFNkEzRTFDMUUyQjFFM0VDQUQiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo1ODc2RDNCQ0EwM0QxMUU2QTNFMUMxRTJCMUUzRUNBRCIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo1ODc2RDNCREEwM0QxMUU2QTNFMUMxRTJCMUUzRUNBRCIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PpmN6SAAAAGqSURBVHjavFXLTsJQEJ0+7KYgKVZCorDAED+gbgm48AfcmLgyceFfaOLaHzBu3fkLJEpIWMEHuACjxAU+WsMrAkmvMy0thZaqYDzJtHPnzj2dx82Uq5QrPADsolyjJGExlFEOUZ5FfKyinEWiclKSVmAznQZZln/EMhwO4bXVguZTM8cY10TTKRFeqOtqLrud9R3gOM5nY4y5uiRJsJFKwedgAKZpwtvL+zmle5xIJmBZ8DxvvcV5DhQdq9p6qXsH+UgBoDaJ2hup6dHFUDLNdswTaQlJoQDs0uMzJhqNRuGEFmoUWSnIHAoxbDN/X7AVbcKkBdXP0zwe/hiBEVJtqEbVcb202iTVnRP/9XE6HNoUXddBURRrbRgGKCg2tnz+wneEDgyXBCxyWtfrdV+XQwm9jo1Gw7WT7v0AZeD4CoLwz00pFougaRpkMhk3dVp7I5yqoSiGE9Lh34DhYJhL6FyZ2asxi3g87u5TPacIe90exGIx34gKGl9BY2xNVcHQP0i9oabsPz48GkS6KPq9PrTbnQ6qRxz+AqKoHKBcLdFcKuIeyu2XAAMAzduoEwnn+/UAAAAASUVORK5CYII="
						height="18" width="18" alt="" class="navigation-icon">
						<div class="navigation-menu-item">卖出</div></li>
					<li class="el-menu-item" style="padding-left: 20px;"><img
						src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAATCAYAAACQjC21AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6ODU2MTBENDFBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6ODU2MTBENDJBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo4NTYxMEQzRkEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo4NTYxMEQ0MEEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PgqT9WYAAAGMSURBVHjatFQ9S8RAEJ0cC+6CxS1YJGBhKjlsvErPTiz1D4j/4f6FvX9CO0uxELUTOxHkIijEQjDdpTjIFoE4M/kw5uMMeDewl9vL7Js3b96tlSTJCgBsei/eMz7BGMOrS0iBS0oYbA/PcHuO69VCwGMEu6CXFM6Gy0mdIgYIggD8T5+3o93RkTXxJgkxchGIQ3QEK8U0+OKnmYXQgwWHQMFQCwnTcDo3UWF7kUzbbAvqVJD8MqZBzBMfCw4OQN6j7radHo7rBwwW67VVlKUF2WGzfwJcmbpqbbmBjRmfAkPc3FUYoCzDwzTPu2XQKtMlDCWzisztgr+Y60vc6wp1fD/7adSQpm/EUs5nqLht3TRC+ii2GsFUI0OR8czVFAq0/1A3r7vHhTRql+dFcVSbgsgnqoT6Ux/9dAWwqoq/XdOZ5Q2lbqDfnnRIBlFSsSVfsO/QSxpUl9KNhjZxbgQJwlnT4H/g9dPX/7ht/AKQ7sOdMAgeGbRUrWsQiLvu8Pe+bW/RUN4XOBPzLcAAvJmIa5xd/QkAAAAASUVORK5CYII="
						height="18" width="18" alt="" class="navigation-icon">
						<div class="navigation-menu-item">撤单</div></li>
					<li class="el-submenu is-opened query"><div
							class="el-submenu__title" style="padding-left: 20px;">
							查询<i class="el-submenu__icon-arrow el-icon-arrow-down"></i>
						</div>
						<ul class="el-menu" style="">
							<li class="el-menu-item-group"><div
									class="el-menu-item-group__title" style="padding-left: 30px;">查询</div>
								<ul>
									<li class="el-menu-item" style="padding-left: 40px;"><img
										src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAATCAYAAACQjC21AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6ODU2MTBENDFBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6ODU2MTBENDJBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo4NTYxMEQzRkEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo4NTYxMEQ0MEEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PgqT9WYAAAGMSURBVHjatFQ9S8RAEJ0cC+6CxS1YJGBhKjlsvErPTiz1D4j/4f6FvX9CO0uxELUTOxHkIijEQjDdpTjIFoE4M/kw5uMMeDewl9vL7Js3b96tlSTJCgBsei/eMz7BGMOrS0iBS0oYbA/PcHuO69VCwGMEu6CXFM6Gy0mdIgYIggD8T5+3o93RkTXxJgkxchGIQ3QEK8U0+OKnmYXQgwWHQMFQCwnTcDo3UWF7kUzbbAvqVJD8MqZBzBMfCw4OQN6j7radHo7rBwwW67VVlKUF2WGzfwJcmbpqbbmBjRmfAkPc3FUYoCzDwzTPu2XQKtMlDCWzisztgr+Y60vc6wp1fD/7adSQpm/EUs5nqLht3TRC+ii2GsFUI0OR8czVFAq0/1A3r7vHhTRql+dFcVSbgsgnqoT6Ux/9dAWwqoq/XdOZ5Q2lbqDfnnRIBlFSsSVfsO/QSxpUl9KNhjZxbgQJwlnT4H/g9dPX/7ht/AKQ7sOdMAgeGbRUrWsQiLvu8Pe+bW/RUN4XOBPzLcAAvJmIa5xd/QkAAAAASUVORK5CYII="
										height="18" width="18" alt="" class="navigation-icon">
										<div class="navigation-menu-item sub-menu-item">资金持仓</div></li>
									<li class="el-menu-item" style="padding-left: 40px;"><img
										src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAATCAYAAACQjC21AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6ODU2MTBENDFBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6ODU2MTBENDJBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo4NTYxMEQzRkEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo4NTYxMEQ0MEEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PgqT9WYAAAGMSURBVHjatFQ9S8RAEJ0cC+6CxS1YJGBhKjlsvErPTiz1D4j/4f6FvX9CO0uxELUTOxHkIijEQjDdpTjIFoE4M/kw5uMMeDewl9vL7Js3b96tlSTJCgBsei/eMz7BGMOrS0iBS0oYbA/PcHuO69VCwGMEu6CXFM6Gy0mdIgYIggD8T5+3o93RkTXxJgkxchGIQ3QEK8U0+OKnmYXQgwWHQMFQCwnTcDo3UWF7kUzbbAvqVJD8MqZBzBMfCw4OQN6j7radHo7rBwwW67VVlKUF2WGzfwJcmbpqbbmBjRmfAkPc3FUYoCzDwzTPu2XQKtMlDCWzisztgr+Y60vc6wp1fD/7adSQpm/EUs5nqLht3TRC+ii2GsFUI0OR8czVFAq0/1A3r7vHhTRql+dFcVSbgsgnqoT6Ux/9dAWwqoq/XdOZ5Q2lbqDfnnRIBlFSsSVfsO/QSxpUl9KNhjZxbgQJwlnT4H/g9dPX/7ht/AKQ7sOdMAgeGbRUrWsQiLvu8Pe+bW/RUN4XOBPzLcAAvJmIa5xd/QkAAAAASUVORK5CYII="
										height="18" width="18" alt="" class="navigation-icon">
										<div class="navigation-menu-item sub-menu-item">当日委托</div></li>
									<li class="el-menu-item" style="padding-left: 40px;"><img
										src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAATCAYAAACQjC21AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6ODU2MTBENDFBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6ODU2MTBENDJBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo4NTYxMEQzRkEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo4NTYxMEQ0MEEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PgqT9WYAAAGMSURBVHjatFQ9S8RAEJ0cC+6CxS1YJGBhKjlsvErPTiz1D4j/4f6FvX9CO0uxELUTOxHkIijEQjDdpTjIFoE4M/kw5uMMeDewl9vL7Js3b96tlSTJCgBsei/eMz7BGMOrS0iBS0oYbA/PcHuO69VCwGMEu6CXFM6Gy0mdIgYIggD8T5+3o93RkTXxJgkxchGIQ3QEK8U0+OKnmYXQgwWHQMFQCwnTcDo3UWF7kUzbbAvqVJD8MqZBzBMfCw4OQN6j7radHo7rBwwW67VVlKUF2WGzfwJcmbpqbbmBjRmfAkPc3FUYoCzDwzTPu2XQKtMlDCWzisztgr+Y60vc6wp1fD/7adSQpm/EUs5nqLht3TRC+ii2GsFUI0OR8czVFAq0/1A3r7vHhTRql+dFcVSbgsgnqoT6Ux/9dAWwqoq/XdOZ5Q2lbqDfnnRIBlFSsSVfsO/QSxpUl9KNhjZxbgQJwlnT4H/g9dPX/7ht/AKQ7sOdMAgeGbRUrWsQiLvu8Pe+bW/RUN4XOBPzLcAAvJmIa5xd/QkAAAAASUVORK5CYII="
										height="18" width="18" alt="" class="navigation-icon">
										<div class="navigation-menu-item sub-menu-item">当日成交</div></li>
									<li class="el-menu-item" style="padding-left: 40px;"><img
										src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAATCAYAAACQjC21AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6ODU2MTBENDFBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6ODU2MTBENDJBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo4NTYxMEQzRkEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo4NTYxMEQ0MEEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PgqT9WYAAAGMSURBVHjatFQ9S8RAEJ0cC+6CxS1YJGBhKjlsvErPTiz1D4j/4f6FvX9CO0uxELUTOxHkIijEQjDdpTjIFoE4M/kw5uMMeDewl9vL7Js3b96tlSTJCgBsei/eMz7BGMOrS0iBS0oYbA/PcHuO69VCwGMEu6CXFM6Gy0mdIgYIggD8T5+3o93RkTXxJgkxchGIQ3QEK8U0+OKnmYXQgwWHQMFQCwnTcDo3UWF7kUzbbAvqVJD8MqZBzBMfCw4OQN6j7radHo7rBwwW67VVlKUF2WGzfwJcmbpqbbmBjRmfAkPc3FUYoCzDwzTPu2XQKtMlDCWzisztgr+Y60vc6wp1fD/7adSQpm/EUs5nqLht3TRC+ii2GsFUI0OR8czVFAq0/1A3r7vHhTRql+dFcVSbgsgnqoT6Ux/9dAWwqoq/XdOZ5Q2lbqDfnnRIBlFSsSVfsO/QSxpUl9KNhjZxbgQJwlnT4H/g9dPX/7ht/AKQ7sOdMAgeGbRUrWsQiLvu8Pe+bW/RUN4XOBPzLcAAvJmIa5xd/QkAAAAASUVORK5CYII="
										height="18" width="18" alt="" class="navigation-icon">
										<div class="navigation-menu-item sub-menu-item">历史委托</div></li>
									<li class="el-menu-item" style="padding-left: 40px;"><img
										src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAATCAYAAACQjC21AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6ODU2MTBENDFBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6ODU2MTBENDJBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo4NTYxMEQzRkEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo4NTYxMEQ0MEEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PgqT9WYAAAGMSURBVHjatFQ9S8RAEJ0cC+6CxS1YJGBhKjlsvErPTiz1D4j/4f6FvX9CO0uxELUTOxHkIijEQjDdpTjIFoE4M/kw5uMMeDewl9vL7Js3b96tlSTJCgBsei/eMz7BGMOrS0iBS0oYbA/PcHuO69VCwGMEu6CXFM6Gy0mdIgYIggD8T5+3o93RkTXxJgkxchGIQ3QEK8U0+OKnmYXQgwWHQMFQCwnTcDo3UWF7kUzbbAvqVJD8MqZBzBMfCw4OQN6j7radHo7rBwwW67VVlKUF2WGzfwJcmbpqbbmBjRmfAkPc3FUYoCzDwzTPu2XQKtMlDCWzisztgr+Y60vc6wp1fD/7adSQpm/EUs5nqLht3TRC+ii2GsFUI0OR8czVFAq0/1A3r7vHhTRql+dFcVSbgsgnqoT6Ux/9dAWwqoq/XdOZ5Q2lbqDfnnRIBlFSsSVfsO/QSxpUl9KNhjZxbgQJwlnT4H/g9dPX/7ht/AKQ7sOdMAgeGbRUrWsQiLvu8Pe+bW/RUN4XOBPzLcAAvJmIa5xd/QkAAAAASUVORK5CYII="
										height="18" width="18" alt="" class="navigation-icon">
										<div class="navigation-menu-item sub-menu-item">历史成交</div></li>
									<li class="el-menu-item" style="padding-left: 40px;"><img
										src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAATCAYAAACQjC21AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6ODU2MTBENDFBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6ODU2MTBENDJBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo4NTYxMEQzRkEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo4NTYxMEQ0MEEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PgqT9WYAAAGMSURBVHjatFQ9S8RAEJ0cC+6CxS1YJGBhKjlsvErPTiz1D4j/4f6FvX9CO0uxELUTOxHkIijEQjDdpTjIFoE4M/kw5uMMeDewl9vL7Js3b96tlSTJCgBsei/eMz7BGMOrS0iBS0oYbA/PcHuO69VCwGMEu6CXFM6Gy0mdIgYIggD8T5+3o93RkTXxJgkxchGIQ3QEK8U0+OKnmYXQgwWHQMFQCwnTcDo3UWF7kUzbbAvqVJD8MqZBzBMfCw4OQN6j7radHo7rBwwW67VVlKUF2WGzfwJcmbpqbbmBjRmfAkPc3FUYoCzDwzTPu2XQKtMlDCWzisztgr+Y60vc6wp1fD/7adSQpm/EUs5nqLht3TRC+ii2GsFUI0OR8czVFAq0/1A3r7vHhTRql+dFcVSbgsgnqoT6Ux/9dAWwqoq/XdOZ5Q2lbqDfnnRIBlFSsSVfsO/QSxpUl9KNhjZxbgQJwlnT4H/g9dPX/7ht/AKQ7sOdMAgeGbRUrWsQiLvu8Pe+bW/RUN4XOBPzLcAAvJmIa5xd/QkAAAAASUVORK5CYII="
										height="18" width="18" alt="" class="navigation-icon">
										<div class="navigation-menu-item sub-menu-item">交割单</div></li>
									<li class="el-menu-item" style="padding-left: 40px;"><img
										src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAATCAYAAACQjC21AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6ODU2MTBENDFBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6ODU2MTBENDJBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo4NTYxMEQzRkEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo4NTYxMEQ0MEEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PgqT9WYAAAGMSURBVHjatFQ9S8RAEJ0cC+6CxS1YJGBhKjlsvErPTiz1D4j/4f6FvX9CO0uxELUTOxHkIijEQjDdpTjIFoE4M/kw5uMMeDewl9vL7Js3b96tlSTJCgBsei/eMz7BGMOrS0iBS0oYbA/PcHuO69VCwGMEu6CXFM6Gy0mdIgYIggD8T5+3o93RkTXxJgkxchGIQ3QEK8U0+OKnmYXQgwWHQMFQCwnTcDo3UWF7kUzbbAvqVJD8MqZBzBMfCw4OQN6j7radHo7rBwwW67VVlKUF2WGzfwJcmbpqbbmBjRmfAkPc3FUYoCzDwzTPu2XQKtMlDCWzisztgr+Y60vc6wp1fD/7adSQpm/EUs5nqLht3TRC+ii2GsFUI0OR8czVFAq0/1A3r7vHhTRql+dFcVSbgsgnqoT6Ux/9dAWwqoq/XdOZ5Q2lbqDfnnRIBlFSsSVfsO/QSxpUl9KNhjZxbgQJwlnT4H/g9dPX/7ht/AKQ7sOdMAgeGbRUrWsQiLvu8Pe+bW/RUN4XOBPzLcAAvJmIa5xd/QkAAAAASUVORK5CYII="
										height="18" width="18" alt="" class="navigation-icon">
										<div class="navigation-menu-item sub-menu-item">资金流水</div></li>
								</ul></li>
						</ul></li>
					<li class="el-menu-item" style="padding-left: 20px;"><img
						src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAATCAYAAACQjC21AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6ODU2MTBENDFBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6ODU2MTBENDJBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo4NTYxMEQzRkEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo4NTYxMEQ0MEEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PgqT9WYAAAGMSURBVHjatFQ9S8RAEJ0cC+6CxS1YJGBhKjlsvErPTiz1D4j/4f6FvX9CO0uxELUTOxHkIijEQjDdpTjIFoE4M/kw5uMMeDewl9vL7Js3b96tlSTJCgBsei/eMz7BGMOrS0iBS0oYbA/PcHuO69VCwGMEu6CXFM6Gy0mdIgYIggD8T5+3o93RkTXxJgkxchGIQ3QEK8U0+OKnmYXQgwWHQMFQCwnTcDo3UWF7kUzbbAvqVJD8MqZBzBMfCw4OQN6j7radHo7rBwwW67VVlKUF2WGzfwJcmbpqbbmBjRmfAkPc3FUYoCzDwzTPu2XQKtMlDCWzisztgr+Y60vc6wp1fD/7adSQpm/EUs5nqLht3TRC+ii2GsFUI0OR8czVFAq0/1A3r7vHhTRql+dFcVSbgsgnqoT6Ux/9dAWwqoq/XdOZ5Q2lbqDfnnRIBlFSsSVfsO/QSxpUl9KNhjZxbgQJwlnT4H/g9dPX/7ht/AKQ7sOdMAgeGbRUrWsQiLvu8Pe+bW/RUN4XOBPzLcAAvJmIa5xd/QkAAAAASUVORK5CYII="
						height="18" width="18" alt="" class="navigation-icon">
						<div class="navigation-menu-item">密码修改</div></li>
					<li class="el-menu-item" style="padding-left: 20px;"><img
						src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAATCAYAAACQjC21AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6ODU2MTBENDFBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6ODU2MTBENDJBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo4NTYxMEQzRkEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo4NTYxMEQ0MEEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PgqT9WYAAAGMSURBVHjatFQ9S8RAEJ0cC+6CxS1YJGBhKjlsvErPTiz1D4j/4f6FvX9CO0uxELUTOxHkIijEQjDdpTjIFoE4M/kw5uMMeDewl9vL7Js3b96tlSTJCgBsei/eMz7BGMOrS0iBS0oYbA/PcHuO69VCwGMEu6CXFM6Gy0mdIgYIggD8T5+3o93RkTXxJgkxchGIQ3QEK8U0+OKnmYXQgwWHQMFQCwnTcDo3UWF7kUzbbAvqVJD8MqZBzBMfCw4OQN6j7radHo7rBwwW67VVlKUF2WGzfwJcmbpqbbmBjRmfAkPc3FUYoCzDwzTPu2XQKtMlDCWzisztgr+Y60vc6wp1fD/7adSQpm/EUs5nqLht3TRC+ii2GsFUI0OR8czVFAq0/1A3r7vHhTRql+dFcVSbgsgnqoT6Ux/9dAWwqoq/XdOZ5Q2lbqDfnnRIBlFSsSVfsO/QSxpUl9KNhjZxbgQJwlnT4H/g9dPX/7ht/AKQ7sOdMAgeGbRUrWsQiLvu8Pe+bW/RUN4XOBPzLcAAvJmIa5xd/QkAAAAASUVORK5CYII="
						height="18" width="18" alt="" class="navigation-icon">
						<div class="navigation-menu-item long-menu-item">客户端下载</div></li>
				</ul>
				<!---->
				<!---->
				<div class="exit-client">
					<img
						src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAATCAYAAACQjC21AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6ODU2MTBENDFBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6ODU2MTBENDJBMDNEMTFFNjgxMDA4NDgzRkQzMjI3MEEiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo4NTYxMEQzRkEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo4NTYxMEQ0MEEwM0QxMUU2ODEwMDg0ODNGRDMyMjcwQSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PgqT9WYAAAGMSURBVHjatFQ9S8RAEJ0cC+6CxS1YJGBhKjlsvErPTiz1D4j/4f6FvX9CO0uxELUTOxHkIijEQjDdpTjIFoE4M/kw5uMMeDewl9vL7Js3b96tlSTJCgBsei/eMz7BGMOrS0iBS0oYbA/PcHuO69VCwGMEu6CXFM6Gy0mdIgYIggD8T5+3o93RkTXxJgkxchGIQ3QEK8U0+OKnmYXQgwWHQMFQCwnTcDo3UWF7kUzbbAvqVJD8MqZBzBMfCw4OQN6j7radHo7rBwwW67VVlKUF2WGzfwJcmbpqbbmBjRmfAkPc3FUYoCzDwzTPu2XQKtMlDCWzisztgr+Y60vc6wp1fD/7adSQpm/EUs5nqLht3TRC+ii2GsFUI0OR8czVFAq0/1A3r7vHhTRql+dFcVSbgsgnqoT6Ux/9dAWwqoq/XdOZ5Q2lbqDfnnRIBlFSsSVfsO/QSxpUl9KNhjZxbgQJwlnT4H/g9dPX/7ht/AKQ7sOdMAgeGbRUrWsQiLvu8Pe+bW/RUN4XOBPzLcAAvJmIa5xd/QkAAAAASUVORK5CYII="
						height="18" width="18" alt="" class="navigation-icon">
					<div class="exist-client-btn">退出系统</div>
				</div>
			</div>
			<div class="main-page-content">
				<div class="transaction-content">
					<div class="navigation-bar">
						<div class="el-row bar-panel">
							<div class="el-col el-col-4 selected"
								data-navitype="/main/purchase">买入</div>
							<div class="el-col el-col-4" data-navitype="/main/sell">卖出</div>
							<div class="el-col el-col-4" data-navitype="/main/revoke">撤单</div>
							<div class="el-col el-col-4"
								data-navitype="/main/todayFinishedDelegationRecords">成交</div>
							<div class="el-col el-col-4" data-navitype="/main/assetHolding">持仓</div>
							<div class="refresh-btn el-col el-col-4">刷新</div>
						</div>
						<div class="selected-navigation">买入</div>
						<div class="user-info">
							<input type="text" disabled="disabled"> <span
								class="user-id">31241811</span> <span class="user-name"></span>
						</div>
					</div>
					<div class="stock-transaction-component">
						<form class="el-form el-form--label-left">
							<div class="el-form-item">
								<label class="el-form-item__label" style="width: 68px;">
									证券代码 </label>
								<div class="el-form-item__content" style="margin-left: 68px;">
									<div class="el-tooltip">
										<div class="el-tooltip__rel">
											<input class="stock-code-input">
										</div>
										<div class="el-tooltip__popper is-dark" style="display: none;">
											<div>请输入证券代码</div>
										</div>
									</div>
									<!---->
								</div>
							</div>
							<div class="el-form-item">
								<label class="el-form-item__label" style="width: 68px;">
									证券名称 </label>
								<div class="el-form-item__content" style="margin-left: 68px;">
									<div class="el-input is-disabled">
										<!---->
										<!---->
										<input type="text" disabled="disabled" autocomplete="off"
											class="el-input__inner">
										<!---->
										<!---->
									</div>
									<!---->
								</div>
							</div>
							<div class="el-form-item">
								<label class="el-form-item__label" style="width: 68px;">
									买入价格 </label>
								<div class="el-form-item__content" style="margin-left: 68px;">
									<div class="input-counter">
										<input type="text" placeholder="请输入委托价格">
										<div class="increase-btn"></div>
										<div class="decrease-btn"></div>
									</div>
									<!---->
								</div>
							</div>
							<div class="el-form-item">
								<label class="el-form-item__label" style="width: 68px;">
									可买股数 </label>
								<div class="el-form-item__content" style="margin-left: 68px;">
									<div class="max-transaction-amount el-input is-disabled">
										<!---->
										<!---->
										<input type="text" disabled="disabled" autocomplete="off"
											class="el-input__inner">
										<!---->
										<!---->
									</div>
									<div class="ratio-btn">
										<div class="all-transaction-btn">全部</div>
										<!---->
									</div>
									<!---->
								</div>
							</div>
							<div class="el-form-item">
								<label class="el-form-item__label" style="width: 68px;">
									买入数量 </label>
								<div class="el-form-item__content" style="margin-left: 68px;">
									<div class="input-counter">
										<input type="text" placeholder="请输入委托数量">
										<div class="increase-btn"></div>
										<div class="decrease-btn"></div>
									</div>
									<!---->
								</div>
							</div>
							<div class="el-form-item">
								<label class="el-form-item__label" style="width: 68px;">
									预计金额 </label>
								<div class="el-form-item__content" style="margin-left: 68px;">
									<div class="el-input is-disabled">
										<!---->
										<!---->
										<input type="text" disabled="disabled" autocomplete="off"
											class="el-input__inner">
										<!---->
										<!---->
									</div>
									<!---->
								</div>
							</div>
							<div class="submit-btn purchase-submit-btn">买入</div>
						</form>
						<div
							class="el-dialog__wrapper delegation-confirmation-dialog dialog-large"
							style="display: none;">
							<div class="el-dialog el-dialog--small"
								style="margin-bottom: 50px; top: 20%;">
								<div class="el-dialog__header">
									<span class="el-dialog__title">委托买入确认</span>
									<div class="el-dialog__headerbtn">
										<i class="el-dialog__close el-icon el-icon-close"></i>
									</div>
								</div>
								<!---->
								<div class="el-dialog__footer">
									<div>
										<button type="button" class="el-button el-button--primary">
											<!---->
											<!---->
											<span>确定</span>
										</button>
										<button type="button" class="el-button el-button--default">
											<!---->
											<!---->
											<span>取消</span>
										</button>
									</div>
								</div>
							</div>
						</div>
						<div class="el-dialog__wrapper dialog-large"
							style="display: none;">
							<div class="el-dialog el-dialog--small"
								style="margin-bottom: 50px; top: 20%;">
								<div class="el-dialog__header">
									<span class="el-dialog__title">提示</span>
									<div class="el-dialog__headerbtn">
										<i class="el-dialog__close el-icon el-icon-close"></i>
									</div>
								</div>
								<!---->
								<div class="el-dialog__footer">
									<span class="dialog-footer"><button type="button"
											class="el-button el-button--primary">
											<!---->
											<!---->
											<span>确 定</span>
										</button></span>
								</div>
							</div>
						</div>
					</div>
					<div class="asset-info-panel vertical">
						<div class="total-net-asset asset-info-row">
							<div class="asset-info-name">余额：</div>
							<div class="asset-info-value">0.410</div>
						</div>
						<div class="total-market-value asset-info-row">
							<div class="asset-info-name">可用：</div>
							<div class="asset-info-value">0.410</div>
						</div>
						<div class="operate-amount asset-info-row">
							<div class="asset-info-name">市值：</div>
							<div class="asset-info-value">0.000</div>
						</div>
						<div class="trade-locked-amount asset-info-row">
							<div class="asset-info-name">资产：</div>
							<div class="asset-info-value">0.410</div>
						</div>
						<div class="limit-amount asset-info-row">
							<div class="asset-info-name">盈亏：</div>
							<div class="asset-info-value">0.000</div>
						</div>
						<div class="floating-profit-loss asset-info-row">
							<div class="asset-info-name">买入预付：</div>
							<div class="asset-info-value">0.000</div>
						</div>
						<div class="floating-profit-loss asset-info-row">
							<div class="asset-info-name">卖出冻结：</div>
							<div class="asset-info-value">0.000</div>
						</div>
						<div class="floating-profit-loss asset-info-row">
							<div class="asset-info-name">限制金额：</div>
							<div class="asset-info-value">0.000</div>
						</div>
					</div>
					<div class="real-time-transaction-info">
						<!---->
						<div class="sale-info">
							<div class="sale-info-row">
								<div class="sale-info-label">卖十</div>
								<div class="sale-price-col same-price">--</div>
								<div class="sale-amount-col">--</div>
							</div>
							<div class="sale-info-row">
								<div class="sale-info-label">卖九</div>
								<div class="sale-price-col same-price">--</div>
								<div class="sale-amount-col">--</div>
							</div>
							<div class="sale-info-row">
								<div class="sale-info-label">卖八</div>
								<div class="sale-price-col same-price">--</div>
								<div class="sale-amount-col">--</div>
							</div>
							<div class="sale-info-row">
								<div class="sale-info-label">卖七</div>
								<div class="sale-price-col same-price">--</div>
								<div class="sale-amount-col">--</div>
							</div>
							<div class="sale-info-row">
								<div class="sale-info-label">卖六</div>
								<div class="sale-price-col same-price">--</div>
								<div class="sale-amount-col">--</div>
							</div>
							<div class="sale-info-row">
								<div class="sale-info-label">卖五</div>
								<div class="sale-price-col same-price">--</div>
								<div class="sale-amount-col">--</div>
							</div>
							<div class="sale-info-row">
								<div class="sale-info-label">卖四</div>
								<div class="sale-price-col same-price">--</div>
								<div class="sale-amount-col">--</div>
							</div>
							<div class="sale-info-row">
								<div class="sale-info-label">卖三</div>
								<div class="sale-price-col same-price">--</div>
								<div class="sale-amount-col">--</div>
							</div>
							<div class="sale-info-row">
								<div class="sale-info-label">卖二</div>
								<div class="sale-price-col same-price">--</div>
								<div class="sale-amount-col">--</div>
							</div>
							<div class="sale-info-row">
								<div class="sale-info-label">卖一</div>
								<div class="sale-price-col same-price">--</div>
								<div class="sale-amount-col">--</div>
							</div>
						</div>
						<div class="latest-price-info">
							<div class="price-label">最新</div>
							<div class="latest-price same-price">--</div>
							<div class="latest-price-change same-price">--</div>
						</div>
						<div class="purchase-info">
							<div class="purchase-info-row">
								<div class="purchase-info-label">买一</div>
								<div class="purchase-price-col same-price">--</div>
								<div class="purchase-amount-col">--</div>
							</div>
							<div class="purchase-info-row">
								<div class="purchase-info-label">买二</div>
								<div class="purchase-price-col same-price">--</div>
								<div class="purchase-amount-col">--</div>
							</div>
							<div class="purchase-info-row">
								<div class="purchase-info-label">买三</div>
								<div class="purchase-price-col same-price">--</div>
								<div class="purchase-amount-col">--</div>
							</div>
							<div class="purchase-info-row">
								<div class="purchase-info-label">买四</div>
								<div class="purchase-price-col same-price">--</div>
								<div class="purchase-amount-col">--</div>
							</div>
							<div class="purchase-info-row">
								<div class="purchase-info-label">买五</div>
								<div class="purchase-price-col same-price">--</div>
								<div class="purchase-amount-col">--</div>
							</div>
							<div class="purchase-info-row">
								<div class="purchase-info-label">买六</div>
								<div class="purchase-price-col same-price">--</div>
								<div class="purchase-amount-col">--</div>
							</div>
							<div class="purchase-info-row">
								<div class="purchase-info-label">买七</div>
								<div class="purchase-price-col same-price">--</div>
								<div class="purchase-amount-col">--</div>
							</div>
							<div class="purchase-info-row">
								<div class="purchase-info-label">买八</div>
								<div class="purchase-price-col same-price">--</div>
								<div class="purchase-amount-col">--</div>
							</div>
							<div class="purchase-info-row">
								<div class="purchase-info-label">买九</div>
								<div class="purchase-price-col same-price">--</div>
								<div class="purchase-amount-col">--</div>
							</div>
							<div class="purchase-info-row">
								<div class="purchase-info-label">买十</div>
								<div class="purchase-price-col same-price">--</div>
								<div class="purchase-amount-col">--</div>
							</div>
						</div>
						<!---->
					</div>
					<div class="transaction-operation-panel">
						<div class="panel-navigation-bar">
							<div data-type="position"
								class="stock-position-btn navigation-bar-btn selected">持仓</div>
							<div class="navigation-splitter">|</div>
							<div data-type="todayDelegation"
								class="today-delegation-btn navigation-bar-btn">委托</div>
							<div class="navigation-splitter">|</div>
							<div data-type="todayFinishedDelegation"
								class="today-finished-delegation-btn navigation-bar-btn">成交</div>
						</div>
						<div class="panel-body">
							<div class="stock-position">
								<div id="risk-close" class="risk-close clearfix" style="">
									<div class="clearfix own-title">风控信息</div>
									<div class="own-content floatLeft">
										<div class="clearfix totalAssets">
											<div id="current-box" style="left: 435px;">
												<span class="market-name ">总资产</span> <span
													id="cur-market-value" class="market-value ">0.41</span>
											</div>
										</div>
										<div class="clearfix bar">
											<div id="red-bar" class="floatLeft close-out"></div>
											<div id="yellow-bar" class="floatLeft"></div>
											<div id="green-bar" class="floatLeft "></div>
										</div>
										<div id="value-bar" class="clearfix value-bar green"
											style="left: 468px;"></div>
										<div class="clearfix warnAssets">
											<span class="market-name floatLeft close-market-name">平仓线</span>
											<span id="close-market-value" class="market-value floatLeft">0.00</span>
											<span class="market-name floatLeft warnning-market-name">预警线</span>
											<span id="warnning-market-value"
												class="market-value floatLeft">0.00</span>
										</div>
									</div>
								</div>
								<div
									class="el-table el-table--fit el-table--border el-table--enable-row-hover el-table--enable-row-transition">
									<div class="hidden-columns">
										<div></div>
										<div></div>
										<div></div>
										<div></div>
										<div></div>
										<div></div>
										<div></div>
										<div></div>
										<div></div>
									</div>
									<div class="el-table__header-wrapper">
										<table cellspacing="0" cellpadding="0" border="0"
											class="el-table__header" style="width: 864px;">
											<col name="el-table_1_column_199" width="92">
											<col name="el-table_1_column_200" width="100">
											<col name="el-table_1_column_201" width="77">
											<col name="el-table_1_column_202" width="76">
											<col name="el-table_1_column_203" width="82">
											<col name="el-table_1_column_204" width="82">
											<col name="el-table_1_column_205" width="126">
											<col name="el-table_1_column_206" width="124">
											<col name="el-table_1_column_207" width="105">
											<col name="gutter" width="">
											<thead>
												<tr>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_199 is-leaf"><div
															class="cell">证券代码</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_200 is-leaf"><div
															class="cell">证券名称</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_201 is-leaf"><div
															class="cell">证券数量</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_202 is-leaf"><div
															class="cell">可用数量</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_203 is-leaf"><div
															class="cell">成本价</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_204 is-leaf"><div
															class="cell">市价</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_205 is-leaf"><div
															class="cell">市值</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_206 is-leaf"><div
															class="cell">浮动盈亏</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_207 is-leaf"><div
															class="cell">盈亏比例</div></th>
													<th class="gutter" style="width: 0px;"></th>
												</tr>
											</thead>
										</table>
									</div>
									<div class="el-table__body-wrapper el-scrollbar">
										<div class="el-scrollbar__wrap"
											style="margin-bottom: -17px; margin-right: -17px;">
											<div class="el-scrollbar__view">
												<table cellspacing="0" cellpadding="0" border="0"
													class="el-table__body" style="width: 864px;">
													<col name="el-table_1_column_199" width="92">
													<col name="el-table_1_column_200" width="100">
													<col name="el-table_1_column_201" width="77">
													<col name="el-table_1_column_202" width="76">
													<col name="el-table_1_column_203" width="82">
													<col name="el-table_1_column_204" width="82">
													<col name="el-table_1_column_205" width="126">
													<col name="el-table_1_column_206" width="124">
													<col name="el-table_1_column_207" width="105">
													<tbody></tbody>
												</table>
												<div class="el-table__empty-block">
													<span class="el-table__empty-text">暂无数据</span>
												</div>
											</div>
										</div>
									</div>
									<!---->
									<!---->
									<!---->
									<div class="el-table__column-resize-proxy"
										style="display: none;"></div>
									<div class="resize-triggers">
										<div class="expand-trigger">
											<div style="width: 865px; height: 57px;"></div>
										</div>
										<div class="contract-trigger"></div>
									</div>
								</div>
								<div class="pagination-btn-group">
									<div class="custom-btn previous-page-btn disabled">上一页</div>
									<div class="custom-btn next-page-btn disabled">下一页</div>
								</div>
							</div>
							<div class="operational-delegation-component"
								style="display: none;">
								<div class="operation-panel">
									<div class="normal-btn large">全部选中</div>
									<div class="normal-btn">撤单</div>
									<div class="normal-btn">全撤</div>
									<label class="el-checkbox"><span
										class="el-checkbox__input"><span
											class="el-checkbox__inner"></span><input type="checkbox"
											class="el-checkbox__original" value="仅显示未完结"></span><span
										class="el-checkbox__label">仅显示未完结</span></label>
								</div>
								<div
									class="el-table el-table--fit el-table--border el-table--enable-row-hover el-table--enable-row-transition">
									<div class="hidden-columns">
										<div></div>
										<div></div>
										<div></div>
										<div></div>
										<div></div>
										<div></div>
										<div></div>
										<div></div>
										<div></div>
										<div></div>
										<div></div>
										<div></div>
									</div>
									<div class="el-table__header-wrapper">
										<table cellspacing="0" cellpadding="0" border="0"
											class="el-table__header" style="width: 972px;">
											<col name="el-table_1_column_208" width="48">
											<col name="el-table_1_column_209" width="75">
											<col name="el-table_1_column_210" width="133">
											<col name="el-table_1_column_211" width="91">
											<col name="el-table_1_column_212" width="100">
											<col name="el-table_1_column_213" width="62">
											<col name="el-table_1_column_214" width="64">
											<col name="el-table_1_column_215" width="82">
											<col name="el-table_1_column_216" width="82">
											<col name="el-table_1_column_217" width="76">
											<col name="el-table_1_column_218" width="76">
											<col name="el-table_1_column_219" width="83">
											<col name="gutter" width="">
											<thead>
												<tr>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_208 el-table-column--selection is-leaf"><div
															class="cell">
															<label class="el-checkbox"><span
																class="el-checkbox__input"><span
																	class="el-checkbox__inner"></span><input
																	type="checkbox" class="el-checkbox__original" value=""></span>
																<!----></label>
														</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_209 is-leaf"><div
															class="cell">订单编号</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_210 is-leaf"><div
															class="cell">委托时间</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_211 is-leaf"><div
															class="cell">证券代码</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_212 is-leaf"><div
															class="cell">证券名称</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_213 is-leaf"><div
															class="cell">操作</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_214 is-leaf"><div
															class="cell">状态</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_215 is-leaf"><div
															class="cell">委托价格</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_216 is-leaf"><div
															class="cell">当前价格</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_217 is-leaf"><div
															class="cell">委托数量</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_218 is-leaf"><div
															class="cell">成交数量</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_219 is-leaf"><div
															class="cell">成交均价</div></th>
													<th class="gutter" style="width: 0px;"></th>
												</tr>
											</thead>
										</table>
									</div>
									<div class="el-table__body-wrapper el-scrollbar">
										<div class="el-scrollbar__wrap"
											style="margin-bottom: -17px; margin-right: -17px;">
											<div class="el-scrollbar__view">
												<table cellspacing="0" cellpadding="0" border="0"
													class="el-table__body" style="width: 972px;">
													<col name="el-table_1_column_208" width="48">
													<col name="el-table_1_column_209" width="75">
													<col name="el-table_1_column_210" width="133">
													<col name="el-table_1_column_211" width="91">
													<col name="el-table_1_column_212" width="100">
													<col name="el-table_1_column_213" width="62">
													<col name="el-table_1_column_214" width="64">
													<col name="el-table_1_column_215" width="82">
													<col name="el-table_1_column_216" width="82">
													<col name="el-table_1_column_217" width="76">
													<col name="el-table_1_column_218" width="76">
													<col name="el-table_1_column_219" width="83">
													<tbody></tbody>
												</table>
												<div class="el-table__empty-block">
													<span class="el-table__empty-text">暂无数据</span>
												</div>
											</div>
										</div>
									</div>
									<!---->
									<!---->
									<!---->
									<div class="el-table__column-resize-proxy"
										style="display: none;"></div>
									<div class="resize-triggers">
										<div class="expand-trigger">
											<div style="width: 1px; height: 1px;"></div>
										</div>
										<div class="contract-trigger"></div>
									</div>
								</div>
								<div class="pagination-btn-group">
									<div class="custom-btn previous-page-btn disabled">上一页</div>
									<div class="custom-btn next-page-btn disabled">下一页</div>
								</div>
							</div>
							<div class="finished-delegation-component" style="display: none;">
								<div
									class="el-table el-table--fit el-table--border el-table--enable-row-hover el-table--enable-row-transition">
									<div class="hidden-columns">
										<div></div>
										<div></div>
										<div></div>
										<div></div>
										<div></div>
										<div></div>
										<div></div>
										<div></div>
										<div></div>
										// 新表格
									</div>
									<div class="el-table__header-wrapper">
										<table cellspacing="0" cellpadding="0" border="0"
											class="el-table__header" style="width: 871px;">
											<col name="el-table_1_column_220" width="75">
											<col name="el-table_1_column_221" width="133">
											<col name="el-table_1_column_222" width="91">
											<col name="el-table_1_column_223" width="100">
											<col name="el-table_1_column_224" width="62">
											<col name="el-table_1_column_225" width="126">
											<col name="el-table_1_column_226" width="76">
											<col name="el-table_1_column_227" width="82">
											<col name="el-table_1_column_228" width="126">
											<col name="gutter" width="">
											<thead>
												<tr>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_220 is-leaf"><div
															class="cell">订单编号</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_221 is-leaf"><div
															class="cell">成交时间</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_222 is-leaf"><div
															class="cell">证券代码</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_223 is-leaf"><div
															class="cell">证券名称</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_224 is-leaf"><div
															class="cell">操作</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_225 is-leaf"><div
															class="cell">发生金额</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_226 is-leaf"><div
															class="cell">成交数量</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_227 is-leaf"><div
															class="cell">成交均价</div></th>
													<th colspan="1" rowspan="1"
														class="el-table_1_column_228 is-leaf"><div
															class="cell">成交金额</div></th>
													<th class="gutter" style="width: 0px;"></th>
												</tr>
											</thead>
										</table>
									</div>
									<div class="el-table__body-wrapper el-scrollbar">
										<div class="el-scrollbar__wrap"
											style="margin-bottom: -17px; margin-right: -17px;">
											<div class="el-scrollbar__view">
												<table cellspacing="0" cellpadding="0" border="0"
													class="el-table__body" style="width: 871px;">
													<col name="el-table_1_column_220" width="75">
													<col name="el-table_1_column_221" width="133">
													<col name="el-table_1_column_222" width="91">
													<col name="el-table_1_column_223" width="100">
													<col name="el-table_1_column_224" width="62">
													<col name="el-table_1_column_225" width="126">
													<col name="el-table_1_column_226" width="76">
													<col name="el-table_1_column_227" width="82">
													<col name="el-table_1_column_228" width="126">
													<tbody></tbody>
												</table>
												<div class="el-table__empty-block">
													<span class="el-table__empty-text">暂无数据</span>
												</div>
											</div>
										</div>
									</div>
									<!---->
									<!---->
									<!---->
									<div class="el-table__column-resize-proxy"
										style="display: none;"></div>
									<div class="resize-triggers">
										<div class="expand-trigger">
											<div style="width: 1px; height: 1px;"></div>
										</div>
										<div class="contract-trigger"></div>
									</div>
								</div>
								<div class="pagination-btn-group">
									<div class="custom-btn previous-page-btn disabled">上一页</div>
									<div class="custom-btn next-page-btn disabled">下一页</div>
								</div>
								<div class="el-dialog__wrapper" style="display: none;">
									<div class="el-dialog el-dialog--small"
										style="margin-bottom: 50px; top: 20%;">
										<div class="el-dialog__header">
											<span class="el-dialog__title">手续费详细信息</span>
											<div class="el-dialog__headerbtn">
												<i class="el-dialog__close el-icon el-icon-close"></i>
											</div>
										</div>
										<!---->
										<div class="el-dialog__footer">
											<div>
												<button type="button" class="el-button el-button--primary">
													<!---->
													<!---->
													<span>确定</span>
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="test-tool-choose-btn">选择</div>
			<div class="el-dialog__wrapper test-tool-setting-dialog dialog-large"
				style="display: none;">
				<div class="el-dialog el-dialog--small"
					style="margin-bottom: 50px; top: 20%;">
					<div class="el-dialog__header">
						<span class="el-dialog__title">测试服务器设置</span>
						<div class="el-dialog__headerbtn">
							<i class="el-dialog__close el-icon el-icon-close"></i>
						</div>
					</div>
					<!---->
					<div class="el-dialog__footer">
						<div>
							<button type="button" class="el-button el-button--primary">
								<!---->
								<!---->
								<span>确定</span>
							</button>
							<button type="button" class="el-button el-button--default">
								<!---->
								<!---->
								<span>取消</span>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>