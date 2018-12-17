var edit,del;
var width,height,indexclose;
if(document.body.clientWidth<1500){
	width='600px';
	height="400px";
}else{
	width='600px';
	height="400px";
}

layui.use(['table', 'form', 'tree', 'layer'],function(){
	var layer = parent.layer === undefined ? layui.layer : top.layer,
			form = layui.form,
			table = layui.table,
		$ = layui.jquery;
	var nowSysId;
		
	getSysData()
	

    var layout = [
    	{ name: 'id', treeNodes: false, headerClass: 'value_col', colClass: 'value_col',
    		render: function(row) {
	            return row.id; //列渲染
	        }
    	},
	    { name: '菜单名称', treeNodes: true, headerClass: 'value_col', colClass: 'value_col'},
	    { name: '菜单url', treeNodes: false, headerClass: 'value_col', colClass: 'value_col',
	    	render: function(row) {
	    		if(row.menuUrl && row.menuUrl!=null){
		        	   return row.menuUrl
		           }else{
		        	   return '';
		           }
	            return row.menuUrl?row.menuUrl:row.menuUrl; //列渲染
	        }
	    },
	  
	    { name: '授权码', treeNodes: false, headerClass: 'value_col', colClass: 'value_col',
	    	render: function(row) {
	            return row.permissionCode; //列渲染
	        }
	    },
	    { name: '是否内部系统', treeNodes: false, headerClass: 'value_col', colClass: 'value_col',   style: 'text-align: center',
	    	render: function(row) {
	           if(row.isSelfSys==1){
	        	   return '<span style="color:#5FB878">是</span>';
	           }else{
	        	   return '<span style="color:#FF5722">否</span>';
	           }
	        }
	    },
	    { name: '新打开窗口',treeNodes: false, headerClass: 'value_col', colClass: 'value_col',   style: 'text-align: center',
	    	render: function(row) {
	    		if(row.newWindow==1){
	    			  return '<span style="color:#5FB878">是</span>';
		           }else{
		        	   return '<span style="color:#FF5722">否</span>';
		           }
	        }
	    },
	    { name: '是否展开',treeNodes: false, headerClass: 'value_col', colClass: 'value_col',   style: 'text-align: center',
	    	render: function(row) { 
	    		if(row.isOpen==1){
	    			  return '<span style="color:#5FB878">是</span>';
		           }else{
		        	   return '<span style="color:#FF5722">否</span>';
		           }
	        }
	    },
	    {
	        name: '操作',
	        headerClass: 'value_col',
	        colClass: 'value_col',
	        style: 'text-align: center;',
	        render: function(row) {
	        	 return  '<div class="layui-btn-group">'+
	        	 		'<button class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit" onClick="edit('+row.id+ ')">'+
	    					'<i class="layui-icon">&#xe642;</i>'+
	  					'</button>'+
	  					'<button class="layui-btn layui-btn-primary layui-btn-xs" lay-event="del" onClick="del(' + row.id + ')">'+
	  						'<i class="layui-icon">&#xe640;</i></button></div>'
	        }
	    },
	];
	
	del=function(id){
	  	  layer.confirm('确定删除此菜单？',{icon:3, title:'提示信息'},function(index){
	  		var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
	            $.get(realPath+"/api/sys/menu/delete",{
	          	  menuId : id  //将需要删除的newsId作为参数传入
	            },function(data){
	           	 	if(data.code!=0){
	            		layer.msg(data.errorMsg);
	            		return;
	            	}else{
	            		getMenuList(nowSysId);
		                layer.close(index);
		                top.layer.msg("删除成功！");
	            	}
	           	 	
	            })
	       });
	}
  edit=function(menuId){
	  var index =layui.layer.open({
          title : "修改菜单",
          type : 2,
          area:[width,height],
          content : "menuAdd.html",
          //id:'menuAdd',
          success : function(layero, index){
              var body =layui.layer.getChildFrame('body', index);
          		body.find('#addIframIndex').val(index);
          		body.find("#isRechange").val("1");
          		body.find(".updateBtn").show();  
              	body.find(".addBtn").hide();  
              	body.find(".sysMenuId").val(menuId);
                  body.find('.role-input').show();
                  body.find('.role-sel').hide();
                  body.find("#parentId").val(nowSysId);
        			body.find('.role-input').find('input').val(edit.permissionCode).attr("lay-verify","");
        			body.find('#btn').click();
        			setTimeout(function(){
	                    layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
	                        tips: 3
	                    });
	                },500)
          }
      })
}
  
/*  $('.selParentId').click(function(){
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
  })*/

    
	//获取列表数据
	function getSysData(){
		var index = parent.layer.load(2, {shade: false}); //0代表加载的风格，支持0-2
		$.ajax({
			type:'get',
			url:realPath+"/api/sys/all",
			cache: false,
			data:{fresh:Math.random()},
			success:function(obj){
				layer.close(index);
				if(obj.data.length>0){
					nowSysId=obj.data[0].id;
					getMenuList(obj.data[0].id);
				}
			    layui.tree({
			        elem: '#demo-tree',
			        skin: 'as',                     //设定皮肤
			        drag: true,                     //点击每一项时是否生成提示信息
			        click: function(item){          //节点点击事件
			            getMenuList(item.id);
			            nowSysId=item.id;
			            $('#demo-tree').find('li').each(function(p1,p2){
			            	$(this).removeClass("active")
			            	var that=$(this);
			            	console.log($(this).find('cite').html()+' '+item.name)
			            	if($(this).find('cite').html()==item.name){
			            		$(this).addClass("active")
			            	}
			            })
			        },
			        nodes:obj.data,
			    }); 

			}
		})
	}
	var treeTbale1;
	function getMenuList(id){
		$.ajax({
			type:'get',
			url:realPath+"/api/sys/menu/get",
			data:{sysId:id,fresh:Math.random()},
			success:function(obj){
				$('#menu-tree').html('')
				treeTbale1=layui.treeGird({
			        elem: '#menu-tree', //传入元素选择器
			        checkbox : true,
			        nodes: obj.data,
			        layout: layout
			    });
			}
		})
	}

	 $('#collapse').on('click', function() {
	        layui.collapse(treeTbale1);
	    });

    $('#expand').on('click', function() {
        layui.expand(treeTbale1);
    });

    $("#add-btn").click(function(){
    	addFun();
    })
    
    function addFun(){
       // var index = layui.layer.open({ top.layer.open
    	var index = layui.layer.open({
            title : "添加菜单",
            type : 2,
            area:["660px","400px"],
            content : "menuAdd.html",
            id:'menuAdd1',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
            	body.find('#addIframIndex').val(index);
               
                	body.find(".updateBtn").hide();  
                	body.find('#btn').click();
                setTimeout(function(){
                    layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        /*layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(index);
        })*/
        $("#aaa").bind("input propertychange change",function(event){
            console.log($("#aaa").val(),"aaa")
     });
    }
    
})
