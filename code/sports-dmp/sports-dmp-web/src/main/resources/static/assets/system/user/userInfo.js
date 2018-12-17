var form, $,areaData;
layui.use(['form','layer','upload','laydate'],function(){
    form = layui.form;
    $ = layui.jquery;
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        upload = layui.upload,
        laydate = layui.laydate,
        address = layui.address;
    getData()
    
    var app = new Vue({
	  el: '#app',
	  data: {
	    userInfo:"",
	    sex:''
	  },
	  created:function(){
		  form.render();
	  },
	  methods:{
		  getdata:function(obj){
			  this.userInfo=obj.data;
			  this.sex=obj.data.gender
		  },
		  updateInfo:function(){
			  var that=this;
				$.ajax({
					type:'get',
					url:realPath+"/api/user/me/update",
					cache: false,
					data:{
						realName:that.userInfo.userName,
						email:that.userInfo.email,
						avatar:that.userInfo.avatar,
						phone:that.userInfo.phone,
						gender:that.userInfo.gender,
						nickName:that.userInfo.nickName
					},
					success:function(obj){
						if(obj.code==0){
							layer.msg("编辑程功");
							getData()
						}else{
							layer.msg("编辑失败");
						}
						
					}
				})
		  },
			updatePwd:function(){
				layer.open({
					  title:"修改密码",
					  type: 1,
					  skin: 'layui-layer-demo', //样式类名
					  closeBtn: 1, //不显示关闭按钮
			
					  shadeClose: true, //开启遮罩关闭
					  content: $('#pwdmodle')
					});
			}
			
	  }
	})
    
	form.on('radio(sex)', function(data){
		app.userInfo.gender=data.value
	});  
    
	function getData(){
		var index = parent.layer.load(2, {shade: false}); //0代表加载的风格，支持0-2
		$.ajax({
			type:'get',
			url:realPath+"/api/user/me/get",
			cache: false,
			success:function(obj){
				console.log(obj,"obj")
				parent.layer.close(index);
				 $('.userSex input').each(function(p1,p2){
                	if($(p2).attr('value')==obj.data.gender){
                		$(p2).attr('checked',true)//.next().find("i").click();
                	}
                	
                })
				app.getdata(obj)
			   form.render();
			}
		})
	}
    
    $('.updatePassWord').on('click',function(){
    	console.log($('.oldPass').val())
    })

    //上传头像
    upload.render({
        elem: '.userFaceBtn',
        url: realPath+'/api/file/upload',
        method : "post",  
        done: function(res, index, upload){
           if(res.code==0){
        	   $('.userAvatar').attr('src',res.data);
               app.userInfo.avatar=res.data
           }else{
        	   layer.msg("头像编辑失败");
           }
        }
    });

    //修改密码
    form.on("submit(changePwd)",function(data){
        var index = layer.msg('提交中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.close(index);
            layer.msg("密码修改成功！");
            $(".pwd").val('');
        },2000);
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })
})