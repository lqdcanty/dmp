layui.use(['table','form','layer','laypage'],function(){
	var table = layui.table,
		laypage = layui.laypage;
		form = layui.form,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery;
	var pageNumber=1,pageSize=9,keyword='';
	
	//渲染列表表格
	var tableIns=table.render({
        elem: '#usersList',
        cellMinWidth : 95,
        height : "full-165",
        id : "usersList",
        cols : [[
            {field: 'userName', title: '用户名'},
            {field: 'phone', title: '电话'},
            {field: 'identity', title: '身份', align:'center'},
            {title: '操作',  templet:'#toolListBar',fixed:"right",align:"center"}
        ]]
	});
	 	

	getData(pageNumber,pageSize)
	
	//搜索
    $(".search_btn").on("click",function(){
    	  if($(".searchVal").val() != ''){
    		  keyword=$(".searchVal").val();
    	 	  getData(1,10,$(".searchVal").val())
           }else{
         	  keyword='';	
           	  getData(1,10)
           }
    });
	
	//搜索回车事件
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
    table.on('tool(usersList)', function(obj){
        var layEvent = obj.event,
        data = obj.data;
	    var index = parent.layui.layer.getFrameIndex(window.name); 
	    var body = parent.layui.layer.getChildFrame('body', $('#codeIframIndex').val()); 
	    if(layEvent === 'select'){ //编辑
	       body.find('.managerUserName').val(data.userName)
	       body.find('.managerUserId').val(data.uuid)
		   parent.layui.layer.close(index);
	    }
    });
	
	//获取列表数据
	function getData(_pageNumber,_pageSize,_keyword){
		var index = parent.layer.load(2, {shade: false}); //0代表加载的风格，支持0-2
		$.ajax({
			type:'get',
			url:realPath+"/api/user/list",
			cache: false,
			data:{pageNumber:_pageNumber,size:_pageSize,keyword:_keyword?_keyword:'',fresh:Math.random()},
			success:function(obj){
				layer.close(index);
			    tableIns.reload({data: obj.data.list});
			    laypage.render({
			        elem: 'pageWrap'
			        ,count: obj.data.allRow
			        ,curr:obj.data.currentPage
			        ,limit:_pageSize
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
})
