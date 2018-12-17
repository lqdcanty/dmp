  function btnClick() {
layui.use(['form','layer','upload'],function(){
    var form = layui.form,layer = parent.layer === undefined ? layui.layer : top.layer,
    		upload = layui.upload,
        $ = layui.jquery,
        areaData=[],
    	roles=[];
  layer.ready(function(){
	  getRoleData();
  }); 
  var uuid=$("#uuid").val();
  
   /* function GetQueryString(name) {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null)return  unescape(r[2]); return null;
   }*/
    console.log('uuid',$("#uuid").val())

    //用户名校验规则
    form.verify({
  	  username: function(value, item){ //value：表单的值、item：表单的DOM对象
  	    if(!new RegExp("[^\u4e00-\u9fa5]").test(value)){
  	      return '用户名不能为中文';
  	    }
  	  }
  	});
    
    //获取角色
	function gerUserInfo(id){
		var index =layer.load(2, {shade: false}); //0代表加载的风格，支持0-2
		$.ajax({
			type:'get',
			url:realPath+"/api/user/get",
			data:{uuid:id},
			success:function(obj){
				layer.close(index);
				$(".status").val(obj.data.status); 
                if(obj.data.status==1){
           		 	$('.statusBox').attr('checked',true);
       			}else{
       				$('.statusBox').attr('checked',false);
       			}
		        obj.data.roles.forEach(function(p1){
		        	roles.push(p1.roleId)
		        	console.log(p1.roleId,"角色");
                })
                var rolesStr=roles.join(","),s='';
		        	console.log(rolesStr,"rolesStr")
				$('.roleBox').find('input').each(function(p1,p2){
					$(p2).attr('checked',false);
					console.log("cun")
					if(rolesStr.indexOf($(p2).attr('value'))>-1){
						
						s+='<input type="checkbox" checked value="'+$(p2).attr('value')+'" title="'+$(p2).attr('title')+'"> ';
					}else{
						s+='<input type="checkbox"  value="'+$(p2).attr('value')+'" title="'+$(p2).attr('title')+'"> ';
					}
				});
	            $('.roles').val(rolesStr);
				$('.roleBox').html('').html(s);
                $('.genderRadios').find('input').each(function(p1,p2){
                	$(p2).attr('checked',false);
                	if($(p2).attr('value')==obj.data.gender){
                		$(this).attr('checked',true).next().find("i").click();
                	}
                })
				form.render();
			}
		})
	}
	
	//获取角色列表
	function getRoleData(){
		$.ajax({
			type:'get',
			url:realPath+"/api/sys/role/all",
			success:function(obj){
				var s=''
				obj.data.forEach(function(p1){
					s+='<input type="checkbox" value="'+p1.id+'" title="'+p1.roleName+'"> ';
				})
				$('.roleBox').html(s);
				if(uuid){
			    	gerUserInfo(uuid)
			    }
				form.render();
			}
		})
	}
	
	//增加数组移除元素方法
	Array.prototype.indexOf = function (val) {
	    for (var i = 0; i < this.length; i++) {
	        if (this[i] == val) return i;
	    }
	    return -1;
	};

	Array.prototype.remove = function (val) {
	    var index = this.indexOf(val);
	    if (index > -1) {
	        this.splice(index, 1);
	    }
	};
	
	//角色复选

	form.on('checkbox()', function(data){
		  console.log(data.value); //复选框value值，也可以通过data.elem.value得到
		  if(data.elem.checked){
			  roles.push(data.value);
		  }else{
			  roles.remove(data.value); 
		  };
		  $('.roles').val(roles.join(","))
		  
	});  

	//状态选择
    form.on('switch(statusBox)', function(data){
    	  if(data.elem.checked){//开关是否开启，true或者false
    		  $('.status').val(1)
    	  }else{
    		  $('.status').val(0)
    	  }; 
    	  
	}); 
    
    //增加
    form.on("submit(addBtn)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        console.log(data.field)
        $.get(realPath+"/api/user/add",data.field,function(res){
        	if(res.code==1){
        		layer.msg(res.errorMsg);
        		return;
        	}
             setTimeout(function(){
                 top.layer.close(index);
                 top.layer.msg("用户添加添加成功！");
                 layer.closeAll("iframe");
                 //刷新父页面
                 parent.location.reload();
             },1000);
         })
        return false;
    });
    
    //修改
    
  form.on("submit(updateBtn)",function(data){
        //弹出loading
        var index = layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        // 实际使用时的提交信息
        $.get(realPath+"/api/user/update",data.field,function(res){
        	self.layer.close(index);
        	 if(res.code==0){ 
				layer.msg("修改成功"); 
				//parent.location.reload();
				var layerS=$("#addIframIndex").val();
				var indexClose = parent.layer.getFrameIndex(window.name);
				console.log(indexClose,layerS,"indexClose");
				parent.layer.close(indexClose);
				parent.layer.close(layerS);
        	 }else{
        		 layer.msg(res.errorMsg);
        	 }
         })
	  
        return false;
    })
  })

  }
