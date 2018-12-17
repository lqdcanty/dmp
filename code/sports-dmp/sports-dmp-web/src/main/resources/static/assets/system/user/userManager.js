layui.use(['table', 'form', 'layer', 'laypage'], function() {
	var table = layui.table,
		laypage = layui.laypage;
	form = layui.form,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery;
	var pageNumber = 1,
		pageSize = 10,
		keyword = '',
	areaData=[],
	roles=[];
	var uuid='';
	//渲染列表表格
	var tableIns = table.render({
		elem: '#dataList',
		id: "userList",
		cols: [
			[{
					field: 'id',
					title: 'ID',
					fixed: "left",
					width:80
				},
				{
					field: 'userName',
					title: '用户名',
					width:120
				},
				{
					field: 'realName',
					title: '真实姓名',
					width:130
				},
				{
					field: 'email',
					title: 'Email',
					width:180
				},
				{
					field: 'roles',
					title: '角色 ',
					templet: '#rolesTpl',
					width:200
				},
				{
					field: 'phone',
					title: '电话',
					align: 'center',
					width:130
				},
				{
					field: 'status',
					title: '可使用',
					align: 'center',
					templet: function(d) {
						return '<input type="checkbox" data-id="' + d.uuid + '" name="dispaly" lay-filter="sysDispaly" lay-skin="switch" lay-text="是|否" ' + isDisplay(d.status) + '>'
					},
					width:100
				},
				{
					field: 'lastLoginTimeStr',
					title: '最后登录时间',
					align: 'center',
					width:160
				},
				
				{
					title: '操作',
					templet: '#btnBar',
					fixed: "right",
					align: "center",
					width:230
				}
			]
		]
	});

	form.on('switch(sysDispaly)', function(data) {
		layer.open({
	        type: 1
	        ,content: '<div style="padding: 20px 100px;">确定此操作吗？</div>'
	        ,btn: ['确定','取消']
	        ,btnAlign: 'c' //按钮居中
	        ,shade: 0 //不显示遮罩
			,success: function(layero){
	          var btn = layero.find('.layui-layer-btn');
	          btn.find('.layui-layer-btn1').click(function(){
	        	  data.elem.checked=!data.elem.checked;
	        	  form.render('checkbox');
	        	  layer.closeAll();
	          })
	          btn.find('.layui-layer-btn0').click(function(){
	        	  layer.closeAll();
	        	  $.get(realPath + "/api/user/lock", {
	      			uuid: $(data.elem).data('id'), //将需要删除的newsId作为参数传入
	      			status: data.elem.checked ? 1 : 0
	      		}, function(data) {
	      			if(data.code == 0) {
	      				form.render('checkbox');
	      				layer.msg("编辑成功");
	      				return;
	      			} else {
	      				data.elem.checked=!data.elem.checked;
			        	form.render('checkbox');
	      				layer.msg(data.errorMsg);
	      			}
	      		})
	          });
	        },
	        cancel: function(){ 
        	    //右上角关闭回调
        	    data.elem.checked=!data.elem.checked;
        	    form.render('checkbox');
        	  }
	      });
	});

	function isDisplay(status) {
		if(status == '1') {
			return 'checked'
		} else {
			return ''
		}
	}

	getData(pageNumber, pageSize)

	//搜索
	$(".search_btn").on("click", function() {
		if($(".searchVal").val() != '') {
			keyword = $(".searchVal").val();
			getData(1, 10, $(".searchVal").val())
		} else {
			keyword = '';
			getData(1, 10)
		}
	});

	//搜索回车事件
	$(document).keydown(function(e) {
		if(e.keyCode == 13) {
			if($(".searchVal").val() != '') {
				keyword = $(".searchVal").val();
				getData(1, 10, $(".searchVal").val())
			} else {
				keyword = '';
				getData(1, 10)
			}
		}
	})

	//添加
	$(".addNews_btn").click(function() {
		addProperty();
	})

	//批量删除
	$(".delAll_btn").click(function() {
		var checkStatus = table.checkStatus('userListTable'),
			data = checkStatus.data,
			newsId = [];
		if(data.length > 0) {
			for(var i in data) {
				newsId.push(data[i].newsId);
			}
			layer.confirm(realPath + '确定删除选中的用户？', {
				icon: 3,
				title: '提示信息'
			}, function(index) {
				tableIns.reload();
				layer.close(index);
			})
		} else {
			layer.msg("请选择需要删除的用户");
		}
	})

	//列表操作
	table.on('tool(userList)', function(obj) {
		var layEvent = obj.event,
			data = obj.data;
		if(layEvent === 'edit') { //编辑
			addProperty(data);
		}
		if(layEvent === 'upPass') { //修改密码
			upPass(data);
		}
		if(layEvent === 'detail'){
			seeDetail(data);
		}
	});
	
	//展开详情
	function seeDetail(detail){
		console.log(detail,"sxian")
		var index = layui.layer.open({
			title: "用户详情",
			type: 2,
			content: "userDetail.html",
			id: 'userData',
			area: [width, height],
			maxmin: true,
			scrollbar: false,
			zIndex: layer.zIndex,
			success: function(layero, index) {
				var body = layui.layer.getChildFrame('body', index);
				//body.find('#addIframIndex').val(index);
				if(detail) {
					//编辑
					body.find('#userData').val(JSON.stringify(detail));
					//body.find(".addBtn").hide();
					body.find('#btn').click();
				} else {
					//添加
					body.find(".updateBtn").hide();
					body.find('#btn').click();
				}
				setTimeout(function() {
					layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
						tips: 3
					});
				}, 500)
			}
		})
		/*   layui.layer.full(index);  */
		//改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
		$(window).on("resize", function() {
			layui.layer.full(index);
		})
	}

	//修改密码
	function upPass(data) {
		var passLayer = layui.layer.open({
			title: '重置密码',
			type: 1,
			area: ['350px', '230px'],
			content: $('#changePass').html(),
			maxmin: true
		})

		//校验规则
		form.verify({
			password: function(value, item) { //value：表单的值、item：表单的DOM对象
				if(value.length < 6) {
					return '密码不能小于6位数';
				}
				if(value.length > 18) {
					return '密码不能大于18位数';
				}
			},
			confirmPwd: function(value, item) { //value：表单的值、item：表单的DOM对象
				console.log($("#password").val())
				if(!value===$("#password").val()) {
					return "两次密码输入不一致";
				}
			}
		});

		//监听提交
		form.on('submit(changePw)', function(data) {
			console.log("校验通过!");
			changePassword();
			return false;
		});

		function changePassword() {
			var load =layer.load(1, {shade: false});
			var passObj = {};
			passObj.uuid = data.uuid;
			passObj.password = $("#twoPassword").val();
			$.ajax({
				type: 'POST',
				url: "/api/user/update/pass",
				cache: false,
				data: passObj,
				success: function(obj) {
					layer.close(load);
					if(obj.code == 0) {
						layer.alert("修改成功");
						layui.layer.close(passLayer);
					} else {
						layer.alert(obj.errorMsg);
					}
				},
				err: function() {
					layer.alert("网络异常,请稍后再试");
				},
				complete:function(){
					layer.close(load);
				}
			})
		}
	};

	//获取列表数据
	function getData(_pageNumber, _pageSize, _keyword) {
		var index = layer.load(1, {shade: false}); 
		$.ajax({
			type: 'get',
			url: realPath + "/api/user/list",
			cache: false,
			data: {
				pageNumber: _pageNumber,
				size: _pageSize,
				keyword: _keyword ? _keyword : '',
				fresh: Math.random()
			},
			success: function(obj) {
				parent.layer.close(index);
				tableIns.reload({
					data: obj.data.list
				});
				laypage.render({
					elem: 'pageWrap',
					count: obj.data.allRow,
					curr: obj.data.currentPage,
					layout: ['count', 'prev', 'page', 'next', 'skip'],
					jump: function(obj, first) {
						if(!first) {
							pageNumber=obj.curr;
							getData(obj.curr, pageSize, keyword)
						}
					}
				});
			}
		})
	};

	//添加
	function addProperty(edit) {
		roles=[];
		if(edit){
			uuid=edit.uuid;
			$("#uuid").val(edit.uuid)
		}else{
			uuid='';
			$("#uuid").val('')
		}
		$("#showLayer").show();
		if(edit) {
			$(".addBtn").hide();
			$(".userName").val(edit.userName).attr('disabled', 'disabled');
			$(".nickName").val(edit.nickName);
			$('#passDiv').hide();
			$('.realName').val(edit.realName);
			$("#password").attr("lay-verify",'');
			$('.avatar').val(edit.avatar);
			$('.email').val(edit.email);
			$('.phone').val(edit.phone);
			$('.remark').val(edit.remark);
			$("#titleName").text("编辑");
			$(".updateBtn").show();
		} else {
			$(".updateBtn").hide();
			$(".addBtn").show();
			$(".userName").val('').attr('disabled', false);
			$(".nickName").val('');
			$("#password").attr("lay-verify",'required');
			$('#passDiv').show();
			$('.realName').val('');
			$('.email').val('');
			$('.phone').val('');
			$('.remark').val('');
			$("#titleName").text("添加");
		}
		getRoleData()
	};
	
	
	 form.verify({
	  	  username: function(value, item){ //value：表单的值、item：表单的DOM对象
	  	    if(!new RegExp("[^\u4e00-\u9fa5]").test(value)){
	  	      return '用户名不能为中文';
	  	    }
	  	  }
	  	});
	    
	    //获取角色
		function gerUserInfo(id){
			var index =layer.load(1, {shade: false}); //0代表加载的风格，支持0-2
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
	                })
	                var rolesStr=roles,s='';
					$('.roleBox').find('input').each(function(p1,p2){
						$(p2).attr('checked',false);
						var sum=0;
						for(var i=0;i<rolesStr.length;i++){
							if(rolesStr[i]==$(p2).attr('value')){
								console.log($(p2).attr('value'),"99",rolesStr[i])
								s+='<input type="checkbox" checked value="'+$(p2).attr('value')+'" title="'+$(p2).attr('title')+'"> ';
								break;
							}else{
								sum++;
							}
						}
						if(sum==rolesStr.length){
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
	            	 $(".layerLqd").scrollTop(0);
	                 top.layer.close(index);
	                 top.layer.msg("用户添加添加成功！");
	                 $("#showLayer").hide();
	                 getData(pageNumber, pageSize, $(".searchVal").val())
	                /* layer.closeAll("iframe");
	                 //刷新父页面
	                 parent.location.reload();*/
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
	        		 $(".layerLqd").scrollTop(0);
					layer.msg("修改成功"); 
					$("#showLayer").hide();
					getData(pageNumber, pageSize, $(".searchVal").val())
					
	        	 }else{
	        		 layer.msg(res.errorMsg);
	        	 }
	         })
		  
	        return false;
	    })
	    $("#closeLayer").click(function(){
	    	$("#showLayer").hide();
	    	
	    })
});