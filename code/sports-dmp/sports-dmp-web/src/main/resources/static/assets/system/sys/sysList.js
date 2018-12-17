layui.use(['table','form','layer','laypage','element'],function(){
	var table = layui.table,
		laypage = layui.laypage,
		form = layui.form,
		element = layui.element,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery;
	var pageNumber=1;pageSize=10;
	
	
	//渲染列表表格
	var tableIns=table.render({
        elem: '#tableList',
        cellMinWidth : 95,
        id : "tableList",
        cols : [[
        	{field: 'id', title: '系统ID',width:100,fixed:"left"},
            {field: 'name', title: '系统名称',templet:'#areaType',width:150},
            {field: 'icon', title: '系统icon',width:150,templet:function(d){
                return '<i class="fa '+d.icon +'"></i>'
            }},
            {field: 'host', title: '域名', align:'center',width:180},
            {field: 'createUserName', title: '创建者名称', align:'center',width:140},
            {field: 'gmtCreateStr', title: '创建时间', align:'center',width:180},
            {field: 'gmtModifedStr', title: '修改时间', align:'center',width:180},
            {field: 'dispaly', title: '是否显示 ', align:'center',width:100, templet:function(d){
                return '<input type="checkbox" data-id="'+d.id+'" name="dispaly" lay-filter="sysDispaly" lay-skin="switch" lay-text="是|否" '+isDisplay(d.dispaly)+'>'
            }},
            {title: '操作',  templet:'#tableListBar',fixed:"right",align:"center",width:200}
        ]]
	});

	form.on('switch(sysDispaly)', function(data){
		  $.get(realPath+"/api/sys/update",{
			  sysId : $(data.elem).data('id'),  //将需要删除的newsId作为参数传入
			  dispaly:data.elem.checked?1:0
         },function(data){
        	 if(data.code==0){
         		layer.msg("编辑成功");
         		return;
         	}else{
         		layer.msg(data.errorMsg);
         	}
         })
	});  
	
	function isDisplay(status){
		if(status){
			return 'checked'
		}else{
			return ''
		}
	}
	getData();
	//搜索
    $(".search_btn").on("click",function(){
        if($(this).prev().find(".searchVal").val() != ''){
        	getData($('.searchCode').val(),1,10,$(this).prev().find(".searchVal").val())
        }else{
        	getData($('.searchCode').val(),1,10,'')
        }
    });

	//添加商品
    $(".add_btn").click(function(){
    	addFun();
    })

    //列表操作
    table.on('tool', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
        	addFun(data);
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此系统？',{icon:3, title:'提示信息'},function(index){
                 $.get(realPath+"/api/sys/delete",{
                	 sysname : data.name  //将需要删除的newsId作为参数传入
                 },function(data){
                	 if(data.code!=0){
                 		layer.msg(data.errorMsg);
                 		return;
                 	}
                 	getData(pageNumber,pageSize)
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
			url:realPath+"/api/sys/all",
			cache: false,
			data:{fresh:Math.random()},
			success:function(obj){
				layer.close(index);
			    tableIns.reload({data: obj.data});		  
			}
		})
	}
	
	//添加
    function addFun(edit){
        var index = layui.layer.open({
            title : !edit?"添加系统":"编辑系统",
            type : 2,
            area:["500px","400px"],
            content : "sysAdd.html",
            id:'sysAdd',
            maxmin: true,
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
            	body.find('#addIframIndex').val(index);
                if(edit){
                	body.find(".addBtn").hide();    
                    body.find(".sysId").val(edit.id);  
                    body.find(".sysname").val(edit.name);  
                    body.find(".host").val(edit.host); 
                    body.find(".icon").val(edit.icon).hide();
                    body.find('.icon-view').html('<i class="fa '+edit.icon +'"></i>')
                    if(edit.dispaly==1){
                		 body.find('.displaBox').attr('checked','checked')
                		 body.find(".dispaly").val(1); 
        			}else{
               		 	body.find(".dispaly").val(0); 
        			}
                    form.render();
                }else{
                	body.find(".updateBtn").hide();  
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        /*layui.layer.full(index);*/
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(index);
        })
    }

})
