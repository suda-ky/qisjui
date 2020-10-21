<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>错误页面</title>
		<script type="text/javascript">
			var num = 10;
			var t;
			function daojishi(){
				
				num--;
				if(num<0){
					clearInterval(t);
					window.location.href = "${errorUrl}";
				}else{
				document.getElementById("fnt").innerText = num;
				}
			}
				t = setInterval("daojishi()",1000);
		
		</script>
	</head>
	<body>
		<div style="width: 1000px;">
		<img src="img/error.png"/>
		<div style="margin-left: 380px;margin-top: -240px;">
			<h4 style="color:red;"	>${error }</h4>
			<h4><font style="color: red;" id="fnt">10</font>秒后自动返回到登陆页面</h4>
			<h4>不能跳转，请<a style="color: red;" href="${errorUrl }">点击这儿</a></h4>
			<input id="urlId" type="hidden" value="${errorUrl }"/>
		</div>
		</div>
	</body>
</html>
