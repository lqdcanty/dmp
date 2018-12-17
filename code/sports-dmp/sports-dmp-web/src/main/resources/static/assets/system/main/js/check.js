var isOpenTag=false;
$(function() {
   var intCheckLogin=setInterval(function() {
		checkUserLogin();
	}, 60*1000);
   
   var checkUserLogin = function() {
		$.ajax({
			url :"/api/user/status?timestamp="+(new Date()).valueOf(),
			success : function(result) {
				if (result.code != 0||result.data==false) {
					 window.clearInterval(intCheckLogin);
					    layer.alert(result.errorMsg, {
							icon : 5,
							end:function(){
								window.location = realPath;
							}
						});
				}
			}
		});
	}
});

