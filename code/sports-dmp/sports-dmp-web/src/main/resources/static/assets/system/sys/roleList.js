var editRole;
layui.use(['table','form','layer','tree', 'laypage','element'],function(){
	var table = layui.table,
		laypage = layui.laypage,
		form = layui.form,
		element = layui.element,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery;
	var pageNumber=1;pageSize=10;
	
	
	//渲染列表表格
	var tableIns=table.render({
        elem: '#roleList',
        cellMinWidth : 95,
        id : "roleList",
        cols : [[
        	{field: 'id',title: '系统ID',width:60,align:"center"},
            {field: 'roleName', title: '角色名',align:"center",width:140},
            {field: 'status',title: '状态 ', align:'center',width:80,templet:'#statusTpl'},
            {title: '操作', templet:'#tableListBar',fixed:"right",align:"center",width:180}
        ]]
	});

	function isDisplay(status){
		if(status=='normal '){
			return 'checked'
		}else{
			return ''
		}
	}

	getData()
	

	//添加
    $(".add_btn").click(function(){
    	addFun();
    })

     editRole=function(elem){
		
		 var type=$(elem).siblings(".hasPermis").val();
		 console.log(elem,type,"elem")
		if(type=='true'){
			 layer.confirm('确定取消授权？', {icon: 3, title: '提示信息'}, function (index) {
				 var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
	                 $.get(realPath+"/api/sys/permission/delete",{
	                	 roleId : $('.roleIdVal').val(),
	                	 roleName : $('.roleName').val(),
	                	 menuId : $(elem).data("id") //将需要删除的newsId作为参数传入
	                 },function(data){
	                	 top.layer.close(index);
	                	 if(data.code!=0){
	                 		layer.msg(data.errorMsg);
	                 		return;
	                 	}
	                	 $(elem).val(false).parent("td").prev().children(".authStatic").text("未授权").attr("style","color:#FF5722;");
	                	 $(elem).text('授权').removeClass("layui-btn-danger").addClass('layui-btn-normal');
	                	 $(elem).siblings(".hasPermis").val('false');
		                 layer.close(index);
		                 
	                 })
	            })
		}else{
			layer.confirm('确定授权？', {icon: 3, title: '提示信息'}, function (index) {
				var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
                $.get(realPath+"/api/sys/permission/add",{
               	 roleId : $('.roleIdVal').val(),
               	 roleName : $('.roleName').val(),
               	 menuIds : $(elem).data("id")  //将需要删除的newsId作为参数传入
                },function(data){
                	top.layer.close(index);
                	if(data.code!=0){
                		layer.msg(data.errorMsg);
                		return;
                	}
                	$(elem).val(false).parent("td").prev().children(".authStatic").text("已授权").attr("style","color:#5FB878;");
                	$(elem).text('取消授权').removeClass("layui-btn-normal").addClass('layui-btn-danger');
                	$(elem).siblings(".hasPermis").val('true');
	               layer.close(index);
                })
           })
		}
	}
     var layout = [
    	{ name: 'id', treeNodes: false, headerClass: 'value_col', colClass: 'value_col',
    		render: function(row) {
	            return row.id; //列渲染
	        }
    	},
	    { name: '菜单名称', treeNodes: true, headerClass: 'value_col', colClass: 'value_col'},
	    { name: '授权状态', treeNodes: false, headerClass: 'value_col', colClass: 'value_col',
	    	render: function(row) {
	    		if(row.hasPermis){
		        	   return '<span class="authStatic" style="color: #5FB878;">已授权</span>'
		        }else{
		        	   return ' <span class="authStatic" style="color: #FF5722;">未授权</span>';
		        }
	  
	        }
	    },
	    { name: '操作', treeNodes: false, headerClass: 'value_col', colClass: 'value_col',
	    	render: function(row) {
	    		if(row.hasPermis){
		        	   return '<input class="hasPermis" type="hidden" value="'+row.hasPermis+'"/><a class="layui-btn auth layui-btn-xs layui-btn-danger" lay-event="role" data-id="'+row.id+'" onClick="editRole(this)">取消授权</a>'
		        }else if(!row.hasPermis){
		        	   return '<input class="hasPermis" type="hidden" value="'+row.hasPermis+'"/><a class="layui-btn auth layui-btn-xs layui-btn-normal" data-id="'+row.id+'" lay-event="role" onClick="editRole(this)">授权</a>';
		        }else{
		        	return ''
		        }
	  
	        }
	    },
	];
     
    /* { name: '操作', treeNodes: false, headerClass: 'value_col', colClass: 'value_col',
	    	render: function(row) {
	    		if(!row.host && row.hasPermis){
		        	   return '<a class="layui-btn layui-btn-xs layui-btn-danger" id="cancelAuth" lay-event="role" onClick="editRole('+row.id+ ','+'1'+')">取消授权</a>'
		        }else if(!row.host && !row.hasPermis){
		        	   return '<a class="layui-btn layui-btn-xs layui-btn-normal" id="auth" lay-event="role" onClick="editRole('+row.id+ ','+'0'+')">授权</a>';
		        }else{
		        	return ''
		        }
	  
	        }
	    },*/
    
 
     //layui-btn-danger,layui-btn-normal
	//监听单元格事件
	  table.on('tool(roleList)', function(obj){
	    var data = obj.data;
	    if(obj.event === 'role'){
	    	//渲染城市列表表格roleNameView
	    	console.log(data)
	    	 $('.roleIdVal').val(data.id);
	    	 $('.roleName').val(data.roleName);
	    	 $('.roleNameView').html('('+data.roleName+')');
	    	 getRoleData(data.id)
	    }else if(obj.event === 'edit'){
	    	addFun(data);
	    }else if(obj.event === 'del'){ //删除
            layer.confirm('确定删除此设备？',{icon:3, title:'提示信息'},function(index){
                $.get(realPath+"/api/sys/role/delete",{
               	 roleId : data.id  //将需要删除的newsId作为参数传入
                },function(data){
               	 if(data.code!=0){
                		layer.msg(data.errorMsg);
                		return;
                	}
                	getData()
                    layer.close(index);;
                })
           });
 
       }
	  });
	//获取列表数据
	function getData(){
		var index = parent.layer.load(2, {shade: false}); //0代表加载的风格，支持0-2
		$.ajax({
			type:'get',
			url:realPath+"/api/sys/role/all",
			cache: false,
			data:{fresh:Math.random()},
			success:function(obj){
				
				 if(obj.code!=0){
					 layer.close(index);
              		layer.msg(obj.errorMsg);
              		
              		return;
              	}else{
              		layer.close(index);
              		$(".roleIdVal").val(obj.data[0].id);
              		$('.roleNameView').text('('+obj.data[0].roleName+')');
              		getRoleData(obj.data[0].id);
    			    tableIns.reload({data: obj.data});
				}
			}
		})
	}
	//获取列表数据
	function getRoleData(id){
		var index = parent.layer.load(2, {shade: false}); //0代表加载的风格，支持0-2
		$.ajax({
			type:'get',
			url:realPath+"/api/sys/permission/get",
			cache: false,
			data:{roleId:id,fresh:Math.random()},
			success:function(obj){
				layer.close(index);
				 if(obj.code!=0){
              		layer.msg(obj.errorMsg);
              		return;
              	}
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
	//添加
    function addFun(edit){
        var index = layui.layer.open({
            title : !edit?"添加角色":"编辑角色",
            type : 2,
            area:["300px","350px"],
            content : "roleAdd.html",
            id:'roleAdd',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
            	body.find('#addIframIndex').val(index);
                if(edit){
                	body.find(".addBtn").hide();    
                    body.find(".roleId").val(edit.id);  
                    body.find(".roleName").val(edit.roleName);  
                    if(edit.status=='normal'){
                		 body.find('.statusBox').attr('checked','checked')
                		 body.find(".status").val('normal'); 
        			}else{
    				 body.find('.statusBox').attr('checked',false)	
               		 	body.find(".status").val('lock'); 
        			}
                    form.render();
                }else{
                	body.find(".updateBtn").hide();  
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返权限管理', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
     /*   layui.layer.full(index);*/
      /*  //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(index);
        })*/
    }

})
