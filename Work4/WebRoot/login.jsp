<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8"/>
		<title>登录</title>
		
	<script type="text/javascript">
		var xmlHttp;
		//创建XMLhttp对象
		function createXmlHttp(){
		if(window.XMLHttpRequest){
		 	xmlHttp = new XMLHttpRequest();
		}else{
			//针对IE5，IE6浏览器
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
		}
			function changecode(){
				var codeImg = document.getElementById("imgId");
				
				codeImg.src = "CreateVerfifyCodeImageServlet.do?t="+Math.random();
			}
			function unameBlur(){
				//只能以字母开头，允许输入字母，数字以及下划线。
				var tmp =  /^[a-z]\w{3,14}$/;
				var nametmp = document.getElementById("uname").value;//用户名
					// 
					if(nametmp==""){
						document.getElementById("nameError").innerHTML = "用户名不能为空!";
						return false;
					}else{
						if(tmp.test(nametmp)){//满足正则表达式
							document.getElementById("nameError").innerHTML = "";
							 return true;
						}else {//不满足正则表达式
							document.getElementById("nameError").innerHTML = "不满足正则表达式";
						return false;
						}
					}
					
			}
			function pwdBlur(){
				var pwd = document.getElementById("pwd").value;//密码
				if(pwd==""){
					document.getElementById("pwdError").innerHTML = "密码不能为空!";
					return false;
				}else{
					document.getElementById("pwdError").innerHTML = "";
					return true;
				}
			}
			function vcodeBlur(){
				var vcode = document.getElementById("vId").value;//验证码
				if(vcode==""){
					document.getElementById("vcodeError").innerHTML = "验证码不能为空!";
					return false;
				}else{
					document.getElementById("vcodeError").innerHTML = "";
					return true;
				}
					
			}
			function checkLogin(){
				var userName = document.getElementById("uname").value;//用户名
				var password = document.getElementById("pwd").value;//密码
				var vCode = document.getElementById("vId").value;//验证码
				var checkLogin = document.getElementById("checkId").value;//验证码
				//文本框为空不跳转。
				if(userName != "" &&password !="" &&vCode != ""){
				//传数据，ajax
				createXmlHttp();//调用创建xmlHttp的方法
				xmlHttp.open("post","Login.do",true);
				xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				xmlHttp.send("username="+userName+"&password="+password+"&vimage="+vCode+"&autoLogin="+checkLogin);
				//接受服务器返回的数据并处理，回调函数
				xmlHttp.onreadystatechange = function (){
				//状态200则是成功
					if(xmlHttp.readyState == 4 && xmlHttp.status == 200){
						var response = xmlHttp.responseText;
						var json = JSON.parse(response);//将返回的字符串格式化
						if(json.code == 0){
						//登陆成功
							window.location.href = "main.jsp";
						}else{
						//失败则显示错误信息
							document.getElementById("Error").innerHTML = json.info;
						}
					}
				}
				return true;
				}else{
					return false;
				}
			}
			
		</script>
		<style>
			body{
				background-image: url(img/bg2.jpg);
				background-repeat: no-repeat;
				background-size: cover;/* 自适应窗口，会把图片拉伸足够大，但是部分图片会显示不全 */
			}
			#logDiv{
				margin: auto;
				position: absolute;
				top: 0;
				left: 0;
				right: 0;
				bottom: 0;
				/* border: 1px solid red; */
				border-radius: 20px;
				background: #fffae8;
				width: 350px;
				height: 300px;
				text-align: center;
				
			}
			#formId{
				/* border: 1px solid red; */
				margin-top: 30px;
			}
			#uname{
				width: 200px;
				height: 25px;
				border-radius: 4px;
				border:1px solid gray;
				background: url(img/name.png)4px no-repeat;/* 插入图片不平铺 */
				text-indent: 2em;/* 不让文字顶格输入 */
			}
			#pwd{
				width: 200px;
				height: 25px;
				border-radius: 4px;
				border:1px solid gray;
				background: url(img/pwd.png)4px no-repeat;/* 插入图片不平铺 */
				text-indent: 2em;/* 不让文字顶格输入 */
			}
			#vId{
				width: 90px;
				height: 25px;
				border-radius: 4px;
				border:1px solid gray;
				background: url(./img/vid.png)4px no-repeat;/* 插入图片不平铺 */
				text-indent: 2em;/* 不让文字顶格输入 */
			}
			span{
				font-size: 10px;
				color: red;
				/* margin-top: 20px; */
				margin-left: -120px;
				
			}
			#fontId{
				color: blue;
				font-size: 13px;
				align-items: center;
			}
			#aId{
				margin-left: 40px;
				color: blue;
				font-size: 13px;
				text-decoration: none;
			}
		</style>
	</head>
<body>
		<div id="logDiv">
			<div id="formId">
			<p><!-- 用户名： --><input type="text" name="username" id="uname" placeholder="用户名" onblur="unameBlur()"/><br />
			<span id="nameError"></span><br/>
			<!-- 密&nbsp;&nbsp;&nbsp;码： --><input type="password" name="password" id="pwd" placeholder="密码" onblur="pwdBlur()"/><br />
			<span id="pwdError"></span><br/>
			<input type="text" name="vimage" id="vId" placeholder="验证码"  onblur="vcodeBlur()"/>
			<img src="CreateVerfifyCodeImageServlet.do" style="vertical-align: middle;" onclick="changecode()" id="imgId"/><br />
			<span id="vcodeError"></span><br/>
			<input type="checkbox" name="sevenAutoLogin" value="ok" id="checkId"/><font id="fontId">一周之内免登录</font><a  href="register.jsp" id="aId">免费注册</a>
			</p>
			<p><button type="button" style="width: 200px;height: 40px;font-size: 18px;color:white;background-color: #8BCC32;border: chartreuse;" onclick="checkLogin()">立即登录</button><br/>
			<span id="Error"></span>
			</p>
		  </div>
		</div>
	</body>
</html>

