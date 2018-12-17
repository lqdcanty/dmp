  layui.use(['form', 'layer'], function() {
  	var form = layui.form,
  		layer = parent.layer === undefined ? layui.layer : top.layer,
  		$ = layui.jquery,
  		areaData = [];
  	if(/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
  		areaData = ['375px', '500px']
  	} else {
  		areaData = ['600px', '500px']
  	}

  	form.on("submit(addBtn)", function(data) {
  		//弹出loading
  		var index = top.layer.msg('数据提交中，请稍候', {
  			icon: 1,
  			time: false,
  			shade: 0.8
  		});
  		$.get(realPath + "/api/sys/role/add", data.field, function(res) {
  			if(res.code == 1) {
  				layer.msg(res.errorMsg);
  				return;
  			}
  			setTimeout(function() {
  				top.layer.close(index);
  				top.layer.msg("角色添加成功！");
  				layer.closeAll("iframe");
  				//刷新父页面
  				parent.location.reload();
  			}, 1000);
  		})
  		return false;
  	});
  	form.on('switch(roleStatus)', function(data) {
  		if(data.elem.checked) { //开关是否开启，true或者false
  			$('.status').val('normal ')
  		} else {
  			$('.status').val('lock')
  		};
  	});

  	form.on("submit(updateBtn)", function(data) {
  		//弹出loading
  		var index = top.layer.load(1, {
  			time: false,
  			shade: 0.3
  		});
  		// 实际使用时的提交信息
  		$.get(realPath + "/api/sys/role/update", data.field,function(res,status) {
  			    console.log(status)
  				if(res.code == 0) {
  					setTimeout(function() {
  						top.layer.close(index);
  						top.layer.msg("角色编辑成功！");
  						//刷新父页面
  						parent.location.reload();
  					}, 1000);
  				} else {
  					layer.closeAll("iframe");
  					top.layer.msg(res.errorMsg);
  				}
  			})

  		return false;
  	})

  })