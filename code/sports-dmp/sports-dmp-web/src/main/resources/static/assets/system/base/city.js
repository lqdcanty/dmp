layui.use(['table','form','layer','laypage'],function(){
	var table = layui.table,
		laypage = layui.laypage;
		form = layui.form,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery;
	var pageNumber=1,pageSize=10,keyword='';
	
	//渲染省份列表表格
	var tableIns=table.render({
        elem: '#province',
        cellMinWidth : 95,
        height : "full-160",
		even: false ,
		size: 'md',
        id : "province",
        cols : [[
        	{field: 'code', event: 'lookP',title: '编号'},
            {field: 'name', title: '省份',event: 'lookP',style:'cursor: pointer;'},
            {title: '操作',  templet:'#proListBar',fixed:"right",align:"center"}
        ]]
	});
	 	

	//加载省份
	getProvince()
	
	//搜索物业
    $(".search_btn").on("click",function(){
    	  if($(".searchVal").val() != ''){
          	keyword=$(".searchVal").val();
          	getData(1,10,$(".searchVal").val())
          }else{
          	keyword='';
          	getData(1,10)
          }
    });
	
	//搜索物业回车事件
	$(document).keydown(function(e) {  
         if (e.keyCode == 13) {  
        	 if($(".searchVal").val() != ''){
     		 	keyword=$(".searchVal").val();
     		 	getData(1,10,$(".searchVal").val())
              }else{
            	  keyword='';	
              	getData(1,10)
              } 
         } 
	})
	
    //列表操作
    table.on('tool(cityList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
        	
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此城市？',{icon:3, title:'提示信息'},function(index){
                 $.get(realPath+"/api/city/remove",{
                	 code : data.code  //将需要删除的newsId作为参数传入
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
	
	//监听单元格事件
	  table.on('tool(province)', function(obj){
	    var data = obj.data;
	    if(obj.event === 'lookP'){
	    	//渲染城市列表表格
	    	$('.parantCode').val(data.code);
	    	var tableInsCity=table.render({
	            elem: '#cityList',
	            cellMinWidth : 95,
	            size: 'md',
	            height : "full-160",
	            id : "cityList",
	            data:data.children,
	            cols : [[
	            	{field: 'code', title: '城市编号'},
	                {field: 'name', title: '城市'},
	                {title: '删除',  templet:'#cityListBar',fixed:"right",align:"center"}
	            ]]
	    	});
	    }else if(obj.event === 'add'){
	    	addFun(data)
	    }
	  });
	//获取列表数据
	function getData(_pageNumber,_pageSize,_keyword){
		var index = parent.layer.load(2, {shade: false}); //0代表加载的风格，支持0-2
		$.ajax({
			type:'get',
			url:realPath+"/api/city/search",
			data:{pageNumber:_pageNumber,size:_pageSize,keyword:_keyword?_keyword:''},
			success:function(obj){
				layer.close(index);
				var tableInsCity=table.render({
		            elem: '#cityList',
		            cellMinWidth : 95,
		            size: 'md',
		            height : "full-160",
		            id : "cityList",
		            data:obj.data.list,
		            cols : [[
		            	{field: 'code', title: '城市编号'},
		                {field: 'name', title: '城市'},
		                {title: '操作',  templet:'#cityListBar',fixed:"right",align:"center"}
		            ]]
		    	});
				laypage.render({
			        elem: 'pageWrap'
			        ,count: obj.data.allRow
			        ,curr:obj.data.currentPage
			        ,layout: ['count', 'prev', 'page', 'next',  'skip']
			        ,jump: function(obj,first){
			        	if(!first){
		        		 getData(obj.curr,pageSize,keyword)
		        	    }
			        	
			        }
		        });
			}
		})
	}
	
	//获取列表数据
	function getProvince(){
		var index = parent.layer.load(2, {shade: false}); //0代表加载的风格，支持0-2
		$.ajax({
			type:'get',
			url:realPath+"/api/city/all",
            cache: false,
            data:{fresh:Math.random()},
			success:function(obj){
				layer.close(index);
			    tableIns.reload({data: obj.data});
			}
		})
	}
	//添加物业
    function addFun(edit){
        var index = parent.layui.layer.open({
            title : !edit?"添加城市":"编辑城市",
            type : 2,
            area : ["300px","220px"],
            content : "../pages/base/cityAdd.html",
            id:'cityAdd',
            success : function(layero, index){
            	var body = $($(".layui-layer-iframe",parent.document).find("iframe")[0].contentWindow.document.body);
            	body.find('.parantCode').val($('.parantCode').val());
                if(edit){
                	body.find(".parantCode").val(edit.code); 
                }else{
                	body.find(".updateVillage").hide();  
                }
                setTimeout(function(){
                	parent.layui.layer.tips('点击关闭', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(index);
        })
    }

})
