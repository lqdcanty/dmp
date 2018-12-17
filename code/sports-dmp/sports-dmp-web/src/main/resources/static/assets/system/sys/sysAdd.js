  layui.use(['form','layer'],function(){
    var form = layui.form,layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        areaData=[];
    if(/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
    	 areaData=['375px','500px']
    } else {
    	 areaData=['600px','500px']
    }

    form.on("submit(addBtn)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        console.log(data.field)
        $.get(realPath+"/api/sys/add",data.field,function(res){
        	if(res.code==1){
        		layer.msg(res.errorMsg);
        		return;
        	}
             setTimeout(function(){
                 top.layer.close(index);
                 top.layer.msg("系统配置添加成功！");
                 layer.closeAll("iframe");
                 //刷新父页面
                 parent.location.reload();
             },1000);
         })

        return false;
    });
    form.on('switch(sysDispaly)', function(data){
    	  if(data.elem.checked){//开关是否开启，true或者false
    		  $('.dispaly').val(1)
    	  }else{
    		  $('.dispaly').val(0)
    	  }; 
    	  

    	}); 
    
    form.on("submit(updateBtn)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        // 实际使用时的提交信息
        console.log(data.field,"提交数据")
         $.get(realPath+"/api/sys/update",data.field,function(res){
        	 if(res.code==0){
        	        setTimeout(function(){
        	            top.layer.close(index);
        	            top.layer.msg("系统配置编辑成功！");
        	            layer.closeAll("iframe");
        	            //刷新父页面
        	            parent.location.reload();
        	        },1000);
        	 }else{
        		 top.layer.msg(res.errorMsg);
        	 }
         })

       // return false;
    })

    //选择类型
    form.on('select(areaTypeSel)', function(data){
    	if(data.value=='common'){
    		$('#housingCodeWrap').hide();
    		$('#cityCodeWrapp').hide();
    		$('.cityName').val('');
    		$('.cityCode').val('');
    		$('.housingEstateName').val('');
    		$('.housingEstateId').val('')
    	}else if(data.value=='city'){
    		$('#housingCodeWrap').hide();
    		$('#cityCodeWrapp').show();
    		$('.housingEstateName').val('');
    		$('.housingEstateId').val('')
    	}else if(data.value=='cell'){
    		$('#cityCodeWrapp').hide();
    		$('#housingCodeWrap').show();
    		$('.cityName').val('');
    		$('.cityCode').val('')
    	}	
	});  
    
	//添加用户
    $('.selIcon').click(function(){
        var index =parent.layui.layer.open({
            title : "选择图标",
            type :2,
            content :"../base/icon.html",
            success : function(layero, index){
                var body = parent.layui.layer.getChildFrame('body', index);
                body.find('#codeIframIndex').val($('#addIframIndex').val());
                console.log($('#addIframIndex').val(),"图标")
            }
        })
        parent.layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
        	parent.layui.layer.full(index);
        })
    })
})
