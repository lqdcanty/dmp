/**
 * Created by zhaozijun on 2017/11/28.
 */

var config = {
//  url: ''
};

/*
 * 公共的数据获取接口
 */
function getResultMethods(url, prams, fn) {
    $.ajax({
        url:  url,
        data: prams,
        type: 'get',
        dataType: 'json',
        success: function(d) { if('function' === typeof fn) fn(d)}
    });
};

/*复选框*/

function checkGroupInit(checkGroupId,permitCancel,exeFun){
	var idStr="#"+checkGroupId;
	var check = false;
	$(idStr).on("click",".choose",function () {
		if($(this).hasClass('choose-after')){
			//被选中的不做任何操作
			if(permitCancel && permitCancel==true){
				$(this).removeClass('choose-after');
				if($(this).data("target")){
	    			 $($(this).data("target")).attr("checked",false);
	    		}
			}
			
			check = false;
		}else {
			//没有被选中的，就选中，并且取消其他选中
			$(idStr).find('.choose').each(function(i){
				$(this).removeClass('choose-after');
				if($(this).data("target")){
					$($(this).data("target")).attr("checked", false);
				}
		    });
			$(this).addClass("choose-after");
			if($(this).data("target")){
				$($(this).data("target")).attr("checked",true);
			}
			
			check = true;
		}
		
		if(exeFun && typeof exeFun === 'function'){
			exeFun($(this), check);
		}
	});
};

/*单选框*/
function checkBoxGroupInit(checkBoxGroupId,exeFun,type){
	var idStr="#"+checkBoxGroupId;
	var check = false;
		if(type){
			$(idStr).off('click','.choose')
		}
	$(idStr).on("click",".choose",function () {
		var canApply = $(this).attr('canapply');
		var isValid = $(this).attr('isvalid');
		console.log(canApply, isValid)
		if(canApply == 'true' && isValid == 'true') {
			if($(this).hasClass('choose-after')){
				//是删除
				$(this).removeClass('choose-after');
				var check = false;
			}else{
				$(this).addClass('choose-after');
				var check = true;
			}
			if(typeof exeFun == "function"){
				exeFun($(this), check);
			}
		}else {
			console.log('禁用，不能点击');
			
		}
	});
}

