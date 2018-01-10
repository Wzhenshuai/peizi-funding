<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
/* ---- Select ----*/
.selectContainer {
	width : 100%;
}

.selectMenu{
	background-color: white;
	list-style: none;
	border: 1px solid gray;
	position: absolute;
	width: 95%;
	max-height: 20em;
	display: none;
	overflow-x:hidden;
	overflow-y: scroll;
	z-index: 99999 !important;
}

.selectMenu li{
	border-bottom: 1px solid gray;
	margin-left: -3em;
	line-height: 32px;
}

.selectMenu li span{
	padding-left: 2em;
	padding-right: 2em;
}

.selectMenu li:HOVER{
	background-color: gray;
	cursor: pointer;
}

.ico-name{
	width: 100%
}

</style>
<script type="text/javascript">
	$(function() {
		$("#selectMenu").click(function() {
			$("#selectMenus").toggle()
		})
		
		$(".selectMenu li ").click(function(){
			$("#selectMenu").val($(this).text().trim());
			$("#selectMenus").hide();
		})
	});
</script>
<div class="selectContainer">
	<input type="text" name="menuStyle" value="" class="form-control" id="selectMenu" readonly="readonly"
		placeholder=" 图 标 " />
	<ul class="selectMenu" id="selectMenus">
		<li><span class="glyphicon glyphicon-asterisk"> </span> <span
			class="ico-name"> glyphicon glyphicon-asterisk </span></li>
		<li><span class="glyphicon glyphicon-plus"> </span> <span
			class="ico-name"> glyphicon glyphicon-plus </span></li>
		<li><span class="glyphicon glyphicon-euro"> </span> <span
			class="ico-name"> glyphicon glyphicon-euro </span></li>
		<li><span class="glyphicon glyphicon-minus"> </span> <span
			class="ico-name"> glyphicon glyphicon-minus </span></li>
		<li><span class="glyphicon glyphicon-cloud"> </span> <span
			class="ico-name"> glyphicon glyphicon-cloud </span></li>
		<li><span class="glyphicon glyphicon-envelope"> </span> <span
			class="ico-name"> glyphicon glyphicon-envelope </span></li>
		<li><span class="glyphicon glyphicon-pencil"> </span> <span
			class="ico-name"> glyphicon glyphicon-pencil </span></li>
		<li><span class="glyphicon glyphicon-glass"> </span> <span
			class="ico-name"> glyphicon glyphicon-glass </span></li>
		<li><span class="glyphicon glyphicon-music"> </span> <span
			class="ico-name"> glyphicon glyphicon-music </span></li>
		<li><span class="glyphicon glyphicon-search"> </span> <span
			class="ico-name"> glyphicon glyphicon-search </span></li>
		<li><span class="glyphicon glyphicon-heart"> </span> <span
			class="ico-name"> glyphicon glyphicon-heart </span></li>
		<li><span class="glyphicon glyphicon-star"> </span> <span
			class="ico-name"> glyphicon glyphicon-star </span></li>
		<li><span class="glyphicon glyphicon-star-empty"> </span> <span
			class="ico-name"> glyphicon glyphicon-star-empty </span></li>
		<li><span class="glyphicon glyphicon-user"> </span> <span
			class="ico-name"> glyphicon glyphicon-user </span></li>
		<li><span class="glyphicon glyphicon-film"> </span> <span
			class="ico-name"> glyphicon glyphicon-film </span></li>
		<li><span class="glyphicon glyphicon-th-large"> </span> <span
			class="ico-name"> glyphicon glyphicon-th-large </span></li>
		<li><span class="glyphicon glyphicon-th"> </span> <span
			class="ico-name"> glyphicon glyphicon-th </span></li>
		<li><span class="glyphicon glyphicon-th-list"> </span> <span
			class="ico-name"> glyphicon glyphicon-th-list </span></li>
		<li><span class="glyphicon glyphicon-ok"> </span> <span
			class="ico-name"> glyphicon glyphicon-ok </span></li>
		<li><span class="glyphicon glyphicon-remove"> </span> <span
			class="ico-name"> glyphicon glyphicon-remove </span></li>
		<li><span class="glyphicon glyphicon-zoom-in"> </span> <span
			class="ico-name"> glyphicon glyphicon-zoom-in </span></li>
		<li><span class="glyphicon glyphicon-zoom-out"> </span> <span
			class="ico-name"> glyphicon glyphicon-zoom-out </span></li>
		<li><span class="glyphicon glyphicon-off"> </span> <span
			class="ico-name"> glyphicon glyphicon-off </span></li>
		<li><span class="glyphicon glyphicon-signal"> </span> <span
			class="ico-name"> glyphicon glyphicon-signal </span></li>
		<li><span class="glyphicon glyphicon-cog"> </span> <span
			class="ico-name"> glyphicon glyphicon-cog </span></li>
		<li><span class="glyphicon glyphicon-trash"> </span> <span
			class="ico-name"> glyphicon glyphicon-trash </span></li>
		<li><span class="glyphicon glyphicon-home"> </span> <span
			class="ico-name"> glyphicon glyphicon-home </span></li>
		<li><span class="glyphicon glyphicon-file"> </span> <span
			class="ico-name"> glyphicon glyphicon-file </span></li>
		<li><span class="glyphicon glyphicon-time"> </span> <span
			class="ico-name"> glyphicon glyphicon-time </span></li>
		<li><span class="glyphicon glyphicon-road"> </span> <span
			class="ico-name"> glyphicon glyphicon-road </span></li>
		<li><span class="glyphicon glyphicon-download-alt"> </span> <span
			class="ico-name"> glyphicon glyphicon-download-alt </span></li>
		<li><span class="glyphicon glyphicon-download"> </span> <span
			class="ico-name"> glyphicon glyphicon-download </span></li>
		<li><span class="glyphicon glyphicon-upload"> </span> <span
			class="ico-name"> glyphicon glyphicon-upload </span></li>
		<li><span class="glyphicon glyphicon-inbox"> </span> <span
			class="ico-name"> glyphicon glyphicon-inbox </span></li>
		<li><span class="glyphicon glyphicon-play-circle"> </span> <span
			class="ico-name"> glyphicon glyphicon-play-circle </span></li>
		<li><span class="glyphicon glyphicon-repeat"> </span> <span
			class="ico-name"> glyphicon glyphicon-repeat </span></li>
		<li><span class="glyphicon glyphicon-refresh"> </span> <span
			class="ico-name"> glyphicon glyphicon-refresh </span></li>
		<li><span class="glyphicon glyphicon-list-alt"> </span> <span
			class="ico-name"> glyphicon glyphicon-list-alt </span></li>
		<li><span class="glyphicon glyphicon-lock"> </span> <span
			class="ico-name"> glyphicon glyphicon-lock </span></li>
		<li><span class="glyphicon glyphicon-flag"> </span> <span
			class="ico-name"> glyphicon glyphicon-flag </span></li>
		<li><span class="glyphicon glyphicon-headphones"> </span> <span
			class="ico-name"> glyphicon glyphicon-headphones </span></li>
		<li><span class="glyphicon glyphicon-volume-off"> </span> <span
			class="ico-name"> glyphicon glyphicon-volume-off </span></li>
		<li><span class="glyphicon glyphicon-volume-down"> </span> <span
			class="ico-name"> glyphicon glyphicon-volume-down </span></li>
		<li><span class="glyphicon glyphicon-volume-up"> </span> <span
			class="ico-name"> glyphicon glyphicon-volume-up </span></li>
		<li><span class="glyphicon glyphicon-qrcode"> </span> <span
			class="ico-name"> glyphicon glyphicon-qrcode </span></li>
		<li><span class="glyphicon glyphicon-barcode"> </span> <span
			class="ico-name"> glyphicon glyphicon-barcode </span></li>
		<li><span class="glyphicon glyphicon-tag"> </span> <span
			class="ico-name"> glyphicon glyphicon-tag </span></li>
		<li><span class="glyphicon glyphicon-tags"> </span> <span
			class="ico-name"> glyphicon glyphicon-tags </span></li>
		<li><span class="glyphicon glyphicon-book"> </span> <span
			class="ico-name"> glyphicon glyphicon-book </span></li>
		<li><span class="glyphicon glyphicon-bookmark"> </span> <span
			class="ico-name"> glyphicon glyphicon-bookmark </span></li>
		<li><span class="glyphicon glyphicon-print"> </span> <span
			class="ico-name"> glyphicon glyphicon-print </span></li>
		<li><span class="glyphicon glyphicon-camera"> </span> <span
			class="ico-name"> glyphicon glyphicon-camera </span></li>
		<li><span class="glyphicon glyphicon-font"> </span> <span
			class="ico-name"> glyphicon glyphicon-font </span></li>
		<li><span class="glyphicon glyphicon-bold"> </span> <span
			class="ico-name"> glyphicon glyphicon-bold </span></li>
		<li><span class="glyphicon glyphicon-italic"> </span> <span
			class="ico-name"> glyphicon glyphicon-italic </span></li>
		<li><span class="glyphicon glyphicon-text-height"> </span> <span
			class="ico-name"> glyphicon glyphicon-text-height </span></li>
		<li><span class="glyphicon glyphicon-text-width"> </span> <span
			class="ico-name"> glyphicon glyphicon-text-width </span></li>
		<li><span class="glyphicon glyphicon-align-left"> </span> <span
			class="ico-name"> glyphicon glyphicon-align-left </span></li>
		<li><span class="glyphicon glyphicon-align-center"> </span> <span
			class="ico-name"> glyphicon glyphicon-align-center </span></li>
		<li><span class="glyphicon glyphicon-align-right"> </span> <span
			class="ico-name"> glyphicon glyphicon-align-right </span></li>
		<li><span class="glyphicon glyphicon-align-justify"> </span> <span
			class="ico-name"> glyphicon glyphicon-align-justify </span></li>
		<li><span class="glyphicon glyphicon-list"> </span> <span
			class="ico-name"> glyphicon glyphicon-list </span></li>
		<li><span class="glyphicon glyphicon-indent-left"> </span> <span
			class="ico-name"> glyphicon glyphicon-indent-left </span></li>
		<li><span class="glyphicon glyphicon-indent-right"> </span> <span
			class="ico-name"> glyphicon glyphicon-indent-right </span></li>
		<li><span class="glyphicon glyphicon-facetime-video"> </span> <span
			class="ico-name"> glyphicon glyphicon-facetime-video </span></li>
		<li><span class="glyphicon glyphicon-picture"> </span> <span
			class="ico-name"> glyphicon glyphicon-picture </span></li>
		<li><span class="glyphicon glyphicon-map-marker"> </span> <span
			class="ico-name"> glyphicon glyphicon-map-marker </span></li>
		<li><span class="glyphicon glyphicon-adjust"> </span> <span
			class="ico-name"> glyphicon glyphicon-adjust </span></li>
		<li><span class="glyphicon glyphicon-tint"> </span> <span
			class="ico-name"> glyphicon glyphicon-tint </span></li>
		<li><span class="glyphicon glyphicon-edit"> </span> <span
			class="ico-name"> glyphicon glyphicon-edit </span></li>
		<li><span class="glyphicon glyphicon-share"> </span> <span
			class="ico-name"> glyphicon glyphicon-share </span></li>
		<li><span class="glyphicon glyphicon-check"> </span> <span
			class="ico-name"> glyphicon glyphicon-check </span></li>
		<li><span class="glyphicon glyphicon-move"> </span> <span
			class="ico-name"> glyphicon glyphicon-move </span></li>
		<li><span class="glyphicon glyphicon-step-backward"> </span> <span
			class="ico-name"> glyphicon glyphicon-step-backward </span></li>
		<li><span class="glyphicon glyphicon-fast-backward"> </span> <span
			class="ico-name"> glyphicon glyphicon-fast-backward </span></li>
		<li><span class="glyphicon glyphicon-backward"> </span> <span
			class="ico-name"> glyphicon glyphicon-backward </span></li>
		<li><span class="glyphicon glyphicon-play"> </span> <span
			class="ico-name"> glyphicon glyphicon-play </span></li>
		<li><span class="glyphicon glyphicon-pause"> </span> <span
			class="ico-name"> glyphicon glyphicon-pause </span></li>
		<li><span class="glyphicon glyphicon-stop"> </span> <span
			class="ico-name"> glyphicon glyphicon-stop </span></li>
		<li><span class="glyphicon glyphicon-forward"> </span> <span
			class="ico-name"> glyphicon glyphicon-forward </span></li>
		<li><span class="glyphicon glyphicon-fast-forward"> </span> <span
			class="ico-name"> glyphicon glyphicon-fast-forward </span></li>
		<li><span class="glyphicon glyphicon-step-forward"> </span> <span
			class="ico-name"> glyphicon glyphicon-step-forward </span></li>
		<li><span class="glyphicon glyphicon-eject"> </span> <span
			class="ico-name"> glyphicon glyphicon-eject </span></li>
		<li><span class="glyphicon glyphicon-chevron-left"> </span> <span
			class="ico-name"> glyphicon glyphicon-chevron-left </span></li>
		<li><span class="glyphicon glyphicon-chevron-right"> </span> <span
			class="ico-name"> glyphicon glyphicon-chevron-right </span></li>
		<li><span class="glyphicon glyphicon-plus-sign"> </span> <span
			class="ico-name"> glyphicon glyphicon-plus-sign </span></li>
		<li><span class="glyphicon glyphicon-minus-sign"> </span> <span
			class="ico-name"> glyphicon glyphicon-minus-sign </span></li>
		<li><span class="glyphicon glyphicon-remove-sign"> </span> <span
			class="ico-name"> glyphicon glyphicon-remove-sign </span></li>
		<li><span class="glyphicon glyphicon-ok-sign"> </span> <span
			class="ico-name"> glyphicon glyphicon-ok-sign </span></li>
		<li><span class="glyphicon glyphicon-question-sign"> </span> <span
			class="ico-name"> glyphicon glyphicon-question-sign </span></li>
		<li><span class="glyphicon glyphicon-info-sign"> </span> <span
			class="ico-name"> glyphicon glyphicon-info-sign </span></li>
		<li><span class="glyphicon glyphicon-screenshot"> </span> <span
			class="ico-name"> glyphicon glyphicon-screenshot </span></li>
		<li><span class="glyphicon glyphicon-remove-circle"> </span> <span
			class="ico-name"> glyphicon glyphicon-remove-circle </span></li>
		<li><span class="glyphicon glyphicon-ok-circle"> </span> <span
			class="ico-name"> glyphicon glyphicon-ok-circle </span></li>
		<li><span class="glyphicon glyphicon-ban-circle"> </span> <span
			class="ico-name"> glyphicon glyphicon-ban-circle </span></li>
		<li><span class="glyphicon glyphicon-arrow-left"> </span> <span
			class="ico-name"> glyphicon glyphicon-arrow-left </span></li>
		<li><span class="glyphicon glyphicon-arrow-right"> </span> <span
			class="ico-name"> glyphicon glyphicon-arrow-right </span></li>
		<li><span class="glyphicon glyphicon-arrow-up"> </span> <span
			class="ico-name"> glyphicon glyphicon-arrow-up </span></li>
		<li><span class="glyphicon glyphicon-arrow-down"> </span> <span
			class="ico-name"> glyphicon glyphicon-arrow-down </span></li>
		<li><span class="glyphicon glyphicon-share-alt"> </span> <span
			class="ico-name"> glyphicon glyphicon-share-alt </span></li>
		<li><span class="glyphicon glyphicon-resize-full"> </span> <span
			class="ico-name"> glyphicon glyphicon-resize-full </span></li>
		<li><span class="glyphicon glyphicon-resize-small"> </span> <span
			class="ico-name"> glyphicon glyphicon-resize-small </span></li>
		<li><span class="glyphicon glyphicon-exclamation-sign"> </span> <span
			class="ico-name"> glyphicon glyphicon-exclamation-sign
		</span></li>
		<li><span class="glyphicon glyphicon-gift"> </span> <span
			class="ico-name"> glyphicon glyphicon-gift </span></li>
		<li><span class="glyphicon glyphicon-leaf"> </span> <span
			class="ico-name"> glyphicon glyphicon-leaf </span></li>
		<li><span class="glyphicon glyphicon-fire"> </span> <span
			class="ico-name"> glyphicon glyphicon-fire </span></li>
		<li><span class="glyphicon glyphicon-eye-open"> </span> <span
			class="ico-name"> glyphicon glyphicon-eye-open </span></li>
		<li><span class="glyphicon glyphicon-eye-close"> </span> <span
			class="ico-name"> glyphicon glyphicon-eye-close </span></li>
		<li><span class="glyphicon glyphicon-warning-sign"> </span> <span
			class="ico-name"> glyphicon glyphicon-warning-sign </span></li>
		<li><span class="glyphicon glyphicon-plane"> </span> <span
			class="ico-name"> glyphicon glyphicon-plane </span></li>
		<li><span class="glyphicon glyphicon-calendar"> </span> <span
			class="ico-name"> glyphicon glyphicon-calendar </span></li>
		<li><span class="glyphicon glyphicon-random"> </span> <span
			class="ico-name"> glyphicon glyphicon-random </span></li>
		<li><span class="glyphicon glyphicon-comment"> </span> <span
			class="ico-name"> glyphicon glyphicon-comment </span></li>
		<li><span class="glyphicon glyphicon-magnet"> </span> <span
			class="ico-name"> glyphicon glyphicon-magnet </span></li>
		<li><span class="glyphicon glyphicon-chevron-up"> </span> <span
			class="ico-name"> glyphicon glyphicon-chevron-up </span></li>
		<li><span class="glyphicon glyphicon-chevron-down"> </span> <span
			class="ico-name"> glyphicon glyphicon-chevron-down </span></li>
		<li><span class="glyphicon glyphicon-retweet"> </span> <span
			class="ico-name"> glyphicon glyphicon-retweet </span></li>
		<li><span class="glyphicon glyphicon-shopping-cart"> </span> <span
			class="ico-name"> glyphicon glyphicon-shopping-cart </span></li>
		<li><span class="glyphicon glyphicon-folder-close"> </span> <span
			class="ico-name"> glyphicon glyphicon-folder-close </span></li>
		<li><span class="glyphicon glyphicon-folder-open"> </span> <span
			class="ico-name"> glyphicon glyphicon-folder-open </span></li>
		<li><span class="glyphicon glyphicon-resize-vertical"> </span> <span
			class="ico-name"> glyphicon glyphicon-resize-vertical </span>
		</li>
		<li><span class="glyphicon glyphicon-resize-horizontal"> </span>
			<span class="ico-name"> glyphicon
				glyphicon-resize-horizontal </span></li>
		<li><span class="glyphicon glyphicon-hdd"> </span> <span
			class="ico-name"> glyphicon glyphicon-hdd </span></li>
		<li><span class="glyphicon glyphicon-bullhorn"> </span> <span
			class="ico-name"> glyphicon glyphicon-bullhorn </span></li>
		<li><span class="glyphicon glyphicon-bell"> </span> <span
			class="ico-name"> glyphicon glyphicon-bell </span></li>
		<li><span class="glyphicon glyphicon-certificate"> </span> <span
			class="ico-name"> glyphicon glyphicon-certificate </span></li>
		<li><span class="glyphicon glyphicon-thumbs-up"> </span> <span
			class="ico-name"> glyphicon glyphicon-thumbs-up </span></li>
		<li><span class="glyphicon glyphicon-thumbs-down"> </span> <span
			class="ico-name"> glyphicon glyphicon-thumbs-down </span></li>
		<li><span class="glyphicon glyphicon-hand-right"> </span> <span
			class="ico-name"> glyphicon glyphicon-hand-right </span></li>
		<li><span class="glyphicon glyphicon-hand-left"> </span> <span
			class="ico-name"> glyphicon glyphicon-hand-left </span></li>
		<li><span class="glyphicon glyphicon-hand-up"> </span> <span
			class="ico-name"> glyphicon glyphicon-hand-up </span></li>
		<li><span class="glyphicon glyphicon-hand-down"> </span> <span
			class="ico-name"> glyphicon glyphicon-hand-down </span></li>
		<li><span class="glyphicon glyphicon-circle-arrow-right">
		</span> <span class="ico-name"> glyphicon
				glyphicon-circle-arrow-right </span></li>
		<li><span class="glyphicon glyphicon-circle-arrow-left"> </span>
			<span class="ico-name"> glyphicon
				glyphicon-circle-arrow-left </span></li>
		<li><span class="glyphicon glyphicon-circle-arrow-up"> </span> <span
			class="ico-name"> glyphicon glyphicon-circle-arrow-up </span>
		</li>
		<li><span class="glyphicon glyphicon-circle-arrow-down"> </span>
			<span class="ico-name"> glyphicon
				glyphicon-circle-arrow-down </span></li>
		<li><span class="glyphicon glyphicon-globe"> </span> <span
			class="ico-name"> glyphicon glyphicon-globe </span></li>
		<li><span class="glyphicon glyphicon-wrench"> </span> <span
			class="ico-name"> glyphicon glyphicon-wrench </span></li>
		<li><span class="glyphicon glyphicon-tasks"> </span> <span
			class="ico-name"> glyphicon glyphicon-tasks </span></li>
		<li><span class="glyphicon glyphicon-filter"> </span> <span
			class="ico-name"> glyphicon glyphicon-filter </span></li>
		<li><span class="glyphicon glyphicon-briefcase"> </span> <span
			class="ico-name"> glyphicon glyphicon-briefcase </span></li>
		<li><span class="glyphicon glyphicon-fullscreen"> </span> <span
			class="ico-name"> glyphicon glyphicon-fullscreen </span></li>
		<li><span class="glyphicon glyphicon-dashboard"> </span> <span
			class="ico-name"> glyphicon glyphicon-dashboard </span></li>
		<li><span class="glyphicon glyphicon-paperclip"> </span> <span
			class="ico-name"> glyphicon glyphicon-paperclip </span></li>
		<li><span class="glyphicon glyphicon-heart-empty"> </span> <span
			class="ico-name"> glyphicon glyphicon-heart-empty </span></li>
		<li><span class="glyphicon glyphicon-link"> </span> <span
			class="ico-name"> glyphicon glyphicon-link </span></li>
		<li><span class="glyphicon glyphicon-phone"> </span> <span
			class="ico-name"> glyphicon glyphicon-phone </span></li>
		<li><span class="glyphicon glyphicon-pushpin"> </span> <span
			class="ico-name"> glyphicon glyphicon-pushpin </span></li>
		<li><span class="glyphicon glyphicon-usd"> </span> <span
			class="ico-name"> glyphicon glyphicon-usd </span></li>
		<li><span class="glyphicon glyphicon-gbp"> </span> <span
			class="ico-name"> glyphicon glyphicon-gbp </span></li>
		<li><span class="glyphicon glyphicon-sort"> </span> <span
			class="ico-name"> glyphicon glyphicon-sort </span></li>
		<li><span class="glyphicon glyphicon-sort-by-alphabet"> </span> <span
			class="ico-name"> glyphicon glyphicon-sort-by-alphabet
		</span></li>
		<li><span class="glyphicon glyphicon-sort-by-alphabet-alt">
		</span> <span class="ico-name"> glyphicon
				glyphicon-sort-by-alphabet-alt </span></li>
		<li><span class="glyphicon glyphicon-sort-by-order"> </span> <span
			class="ico-name"> glyphicon glyphicon-sort-by-order </span></li>
		<li><span class="glyphicon glyphicon-sort-by-order-alt"> </span>
			<span class="ico-name"> glyphicon
				glyphicon-sort-by-order-alt </span></li>
		<li><span class="glyphicon glyphicon-sort-by-attributes">
		</span> <span class="ico-name"> glyphicon
				glyphicon-sort-by-attributes </span></li>
		<li><span class="glyphicon glyphicon-sort-by-attributes-alt">
		</span> <span class="ico-name"> glyphicon
				glyphicon-sort-by-attributes-alt </span></li>
		<li><span class="glyphicon glyphicon-unchecked"> </span> <span
			class="ico-name"> glyphicon glyphicon-unchecked </span></li>
		<li><span class="glyphicon glyphicon-expand"> </span> <span
			class="ico-name"> glyphicon glyphicon-expand </span></li>
		<li><span class="glyphicon glyphicon-collapse-down"> </span> <span
			class="ico-name"> glyphicon glyphicon-collapse-down </span></li>
		<li><span class="glyphicon glyphicon-collapse-up"> </span> <span
			class="ico-name"> glyphicon glyphicon-collapse-up </span></li>
		<li><span class="glyphicon glyphicon-log-in"> </span> <span
			class="ico-name"> glyphicon glyphicon-log-in </span></li>
		<li><span class="glyphicon glyphicon-flash"> </span> <span
			class="ico-name"> glyphicon glyphicon-flash </span></li>
		<li><span class="glyphicon glyphicon-log-out"> </span> <span
			class="ico-name"> glyphicon glyphicon-log-out </span></li>
		<li><span class="glyphicon glyphicon-new-window"> </span> <span
			class="ico-name"> glyphicon glyphicon-new-window </span></li>
		<li><span class="glyphicon glyphicon-record"> </span> <span
			class="ico-name"> glyphicon glyphicon-record </span></li>
		<li><span class="glyphicon glyphicon-save"> </span> <span
			class="ico-name"> glyphicon glyphicon-save </span></li>
		<li><span class="glyphicon glyphicon-open"> </span> <span
			class="ico-name"> glyphicon glyphicon-open </span></li>
		<li><span class="glyphicon glyphicon-saved"> </span> <span
			class="ico-name"> glyphicon glyphicon-saved </span></li>
		<li><span class="glyphicon glyphicon-import"> </span> <span
			class="ico-name"> glyphicon glyphicon-import </span></li>
		<li><span class="glyphicon glyphicon-export"> </span> <span
			class="ico-name"> glyphicon glyphicon-export </span></li>
		<li><span class="glyphicon glyphicon-send"> </span> <span
			class="ico-name"> glyphicon glyphicon-send </span></li>
		<li><span class="glyphicon glyphicon-floppy-disk"> </span> <span
			class="ico-name"> glyphicon glyphicon-floppy-disk </span></li>
		<li><span class="glyphicon glyphicon-floppy-saved"> </span> <span
			class="ico-name"> glyphicon glyphicon-floppy-saved </span></li>
		<li><span class="glyphicon glyphicon-floppy-remove"> </span> <span
			class="ico-name"> glyphicon glyphicon-floppy-remove </span></li>
		<li><span class="glyphicon glyphicon-floppy-save"> </span> <span
			class="ico-name"> glyphicon glyphicon-floppy-save </span></li>
		<li><span class="glyphicon glyphicon-floppy-open"> </span> <span
			class="ico-name"> glyphicon glyphicon-floppy-open </span></li>
		<li><span class="glyphicon glyphicon-credit-card"> </span> <span
			class="ico-name"> glyphicon glyphicon-credit-card </span></li>
		<li><span class="glyphicon glyphicon-transfer"> </span> <span
			class="ico-name"> glyphicon glyphicon-transfer </span></li>
		<li><span class="glyphicon glyphicon-cutlery"> </span> <span
			class="ico-name"> glyphicon glyphicon-cutlery </span></li>
		<li><span class="glyphicon glyphicon-header"> </span> <span
			class="ico-name"> glyphicon glyphicon-header </span></li>
		<li><span class="glyphicon glyphicon-compressed"> </span> <span
			class="ico-name"> glyphicon glyphicon-compressed </span></li>
		<li><span class="glyphicon glyphicon-earphone"> </span> <span
			class="ico-name"> glyphicon glyphicon-earphone </span></li>
		<li><span class="glyphicon glyphicon-phone-alt"> </span> <span
			class="ico-name"> glyphicon glyphicon-phone-alt </span></li>
		<li><span class="glyphicon glyphicon-tower"> </span> <span
			class="ico-name"> glyphicon glyphicon-tower </span></li>
		<li><span class="glyphicon glyphicon-stats"> </span> <span
			class="ico-name"> glyphicon glyphicon-stats </span></li>
		<li><span class="glyphicon glyphicon-sd-video"> </span> <span
			class="ico-name"> glyphicon glyphicon-sd-video </span></li>
		<li><span class="glyphicon glyphicon-hd-video"> </span> <span
			class="ico-name"> glyphicon glyphicon-hd-video </span></li>
		<li><span class="glyphicon glyphicon-subtitles"> </span> <span
			class="ico-name"> glyphicon glyphicon-subtitles </span></li>
		<li><span class="glyphicon glyphicon-sound-stereo"> </span> <span
			class="ico-name"> glyphicon glyphicon-sound-stereo </span></li>
		<li><span class="glyphicon glyphicon-sound-dolby"> </span> <span
			class="ico-name"> glyphicon glyphicon-sound-dolby </span></li>
		<li><span class="glyphicon glyphicon-sound-5-1"> </span> <span
			class="ico-name"> glyphicon glyphicon-sound-5-1 </span></li>
		<li><span class="glyphicon glyphicon-sound-6-1"> </span> <span
			class="ico-name"> glyphicon glyphicon-sound-6-1 </span></li>
		<li><span class="glyphicon glyphicon-sound-7-1"> </span> <span
			class="ico-name"> glyphicon glyphicon-sound-7-1 </span></li>
		<li><span class="glyphicon glyphicon-copyright-mark"> </span> <span
			class="ico-name"> glyphicon glyphicon-copyright-mark </span></li>
		<li><span class="glyphicon glyphicon-registration-mark"> </span>
			<span class="ico-name"> glyphicon
				glyphicon-registration-mark </span></li>
		<li><span class="glyphicon glyphicon-cloud-download"> </span> <span
			class="ico-name"> glyphicon glyphicon-cloud-download </span></li>
		<li><span class="glyphicon glyphicon-cloud-upload"> </span> <span
			class="ico-name"> glyphicon glyphicon-cloud-upload </span></li>
		<li><span class="glyphicon glyphicon-tree-conifer"> </span> <span
			class="ico-name"> glyphicon glyphicon-tree-conifer </span></li>
		<li><span class="glyphicon glyphicon-tree-deciduous"> </span> <span
			class="ico-name"> glyphicon glyphicon-tree-deciduous </span></li>
	</ul>
</div>
