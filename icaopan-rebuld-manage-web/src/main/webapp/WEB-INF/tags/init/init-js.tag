<script type="text/javascript" charset="utf-8">
	$(function(){
		$("select[multiple='multiple']").multiselect({selectAllValue:'',includeSelectAllOption: true,selectAll:true, allSelected:'\u5168\u90e8',numberDisplayed: 1,nonSelectedText:'\u8bf7\u9009\u62e9',selectAllText:'\u5168\u90e8',checkAllText: '\u5168\u90e8',selectAll:true,selectedList:100,allSelectedText:'\u5168\u90e8'});
		$("select").each(function(){
			var _value=$(this).attr("_value");
			if(_value!=null && _value!=''){
				$(this).val(_value).trigger("change");
			}
		});
		/* $("div.modal").on("show.bs.modal",function(){
			$("div.modal input").val("");
		}); */
	});
</script>
