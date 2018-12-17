 function btnClick(){
  layui.use(['form','layer'],function(){
    var form = layui.form,layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        areaData=[];
    if(/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
    	areaData=['660px','400px']
    } else {
    	 areaData=['660px','400px']
    }
   
    	
   
    console.log($('.sysMenuId').val())
    
    if($('.sysMenuId').val()){
    	
		$.ajax({
			type:'get',
			url:realPath+"/api/sys/menu/info",
			data:{menuId:$('.sysMenuId').val()},//menuId是参数
			success:function(obj){
			  var row=obj.data;
			  $('.sysId').val(row.sysId);
              $(".menuName").val(row.menuName);   
	    	  $('.parentId').val(row.parentId);
	    	  $('.permissionCode').val(row.permissionCode);
	    	  
	    	  if(row.parentMenuInfo){
	    		  $('.parentIdView').val(row.parentMenuInfo.name).show();
	    	  }
	    	  var $div =$("#menuType").next();  
              var _areaType = row.type;  
              $div.find('dl dd[lay-value="'+_areaType+'"]').click();  
              if(_areaType && _areaType=='url' && row.menuUrl && row.menuUrl!=null){
              	  $(".menuUrl").val(row.menuUrl);   
              	  $('.menuHost').show()
              }
              
              var isRechange=$("#isRechange").val();
      	    console.log(isRechange,"isRechange");
      	    if(isRechange=='1'){
      	    	$("#isRechangeDiv").hide();
      	    	$(".permissionCode").attr("disabled",false)
      	    	$(".permissionDiv").show();
      	    }
             
              if(row.isSelfSys==1){
          		 $('.isSelfSysBox').attr('checked','checked')
          		 $(".isSelfSys").val(1);
				}else{
	       		 	$(".isSelfSys").val(0); 
	       		 	$('.isSelfSysBox').attr('checked',false)
				}
	            if(row.newWindow==1){
	           		 $('.newWindowBox').attr('checked','checked')
	           		 $(".newWindow").val(1); 
	   			}else{
	      		 	 $(".newWindow").val(0); 
	   			}
	            if(row.isOpen==1){
	           		 $('.isOpenBox').attr('checked','checked')
	           		 $(".isOpen").val(1); 
	   			}else{
	      		 	$(".isOpen").val(0); 
	   			}
	        
              form.render();
			}
		})
    
    }
  
    
    form.on("submit(addBtn)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //console.log(data.field)
        
        console.log(data.field,'biao')
        $.get(realPath+"/api/sys/menu/add",data.field,function(res){
        	if(res.code==1){
        		layer.msg(res.errorMsg);
        		return;
        	}
             setTimeout(function(){
                 top.layer.close(index);
                 top.layer.msg("菜单添加成功！");
                 layer.closeAll("iframe");
                 var body = parent.layui.layer.getChildFrame('body',index); 
                 //刷新父页面
                 parent.location.reload();
             },1000);
         })

        return false;
    });
    form.on('switch(newWindowBox)', function(data){
    	  if(data.elem.checked){//开关是否开启，true或者false
    		  $('.newWindow').val(1)
    	  }else{
    		  $('.newWindow').val(0)
    	  }; 
	});
    form.on('switch(isRechangeBox)', function(data){
  	  if(data.elem.checked){//开关是否开启，true或者false
  		$('.isRechange').val(1);
  		$(".permissionCode").attr("disabled",true);
  		$('.permissionCode').attr("lay-verify","");
  		$(".permissionDiv").hide();
  		form.render("checkbox");
  	  }else{
		  $('.isRechange').val(0);
	  	  $(".permissionCode").attr("disabled",false);
	  	$('.permissionCode').attr("lay-verify","required");
	  	$(".permissionDiv").show();
	  	  form.render("checkbox");
  	  }; 
	});
    form.on('switch(isOpenBox)', function(data){
  	  if(data.elem.checked){//开关是否开启，true或者false
  		  $('.isOpen').val(1)
  	  }else{
  		  $('.isOpen').val(0)
  	  }; 
	}); 
    
    form.on('switch(isSelfSysBox)', function(data){
  	  if(data.elem.checked){//开关是否开启，true或者false
  		  $('.isSelfSys').val(1)
  		  $('.role-input').hide()
  			$('.role-input').find('input').attr("lay-verify","")
  			console.log($('.isSelfSys').val(),"内部系统的值")
  	  }else{
  		  $('.isSelfSys').val(0)
  		   $('.role-input').show()
  		   $('.role-input').find('input').attr("lay-verify","required")
  		   console.log($('.isSelfSys').val(),"内部系统的值")
  	  }; 
	}); 
    form.on('select(menuType)', function(data){
    	  if(data.value=='url'){
    		  //$('.menuHost').show()
    		  $(".menuUrl").attr("disabled",false);
    		  $('.menuUrl').attr("lay-verify","required");
    		  $(".menuUrlBox").show();
    		  form.render();
    	  }else{
    		  //$('.menuHost').hide()
    		  $(".menuUrl").attr("disabled",true);
    		  $(".menuUrlBox").hide();
    		  $('.menuUrl').attr("lay-verify","")
    		  form.render();
    	  }
	}); 
    form.on("submit(updateBtn)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        // 实际使用时的提交信息
        console.log(data.field)
         $.get(realPath+"/api/sys/menu/update",data.field,function(res){
        	 if(res.code==0){
        	        setTimeout(function(){
        	            top.layer.close(index);
        	            top.layer.msg("菜单编辑成功！");
        	         
        	            layer.closeAll("iframe");
        	
        	            //刷新父页面
        	           window.parent.location.reload();
        	        },1000);
        	 }else{
        		 top.layer.msg(res.errorMsg);
        		 console.log(res.errorMsg)
        	 }
         })

        return false;
    })

	//添加用户
    $('.selParentId').click(function(){
        var index =parent.layui.layer.open({
            title : "选择父菜单",
            type :2,
            area:['375px','500px'],
            content :"selParentMenu.html",
            success : function(layero, index){
                var body = parent.layui.layer.getChildFrame('body', index);
                body.find('#codeIframIndex').val($('#addIframIndex').val())
            }
        })
    })
    
    function GetValue(obj){
        console.log(obj,"接收的值");
   }
   
})

 }

