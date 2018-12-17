layui.use(['tree', 'layer'],function(){
	var layer = parent.layer === undefined ? layui.layer : top.layer;
		$ = layui.jquery;
	var nowSysId;
		
	getMenuList(1)
  
	var treeTbale1;
	function getMenuList(id){
		var index = parent.layer.load(2, {shade: false}); //0代表加载的风格，支持0-2
		$.ajax({
			type:'get',
			url:realPath+"/api/sys/menu/sys",
			cache: false,
			data:{fresh:Math.random()},
			success:function(obj){
				parent.layer.close(index);
			    layui.tree({
			        elem: '#menuPreTree',
			        skin: 'as',                     //设定皮肤
			        drag: true,                     //点击每一项时是否生成提示信息
			        click: function(item){          //节点点击事件
			           nowSysId=item.id;
			    	   var index = parent.layui.layer.getFrameIndex(window.name); 
			    	   var body = parent.layui.layer.getChildFrame('body', $('#codeIframIndex').val()); 
			    	   body.find('.sysId').val(item.sysId);
			    	   if(!item.host){
			    		   body.find('.parentId').val(nowSysId);
			    	   }
		    	       body.find('.parentIdView').val(item.name).show();
		    		   parent.layui.layer.close(index);
			        },
			        nodes:obj.data,
			    }); 
			}
		})
	}
	
/*	function getMenuList(id){
		var index = parent.layer.load(2, {shade: false}); //0代表加载的风格，支持0-2
		$.ajax({
			type:'get',
			url:realPath+"/api/sys/menu/sys",
			cache: false,
			data:{fresh:Math.random()},
			success:function(obj){
				parent.layer.close(index);
			    layui.tree({
			        elem: '#menuPreTree',
			        skin: 'as',                     //设定皮肤
			        drag: true,                     //点击每一项时是否生成提示信息
			        click: function(item){          //节点点击事件
			           nowSysId=item.id;
			    	   var index = parent.layui.layer.getFrameIndex(window.name); 
			    	   var body = parent.layui.layer.getChildFrame('body', $('#codeIframIndex').val()); 
			    	   body.find('.sysId').val(item.sysId);
			    	   if(!item.host){
			    		   body.find('.parentId').val(nowSysId);
			    	   }
		    	       body.find('.parentIdView').val(item.name).show();
		    		   parent.layui.layer.close(index);
			        },
			        nodes:obj.data,
			    }); 
			}
		})
	}*/
    
})
