<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
   
   <title>首页</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
<link rel="stylesheet" type="text/css" href="styles.css">
-->
<style type="text/css">
			body{
				margin: 0;
				padding: 0;
				background: url(img/mainbj.jpg) no-repeat top center;
				background-size: 1250px 500px;
			}
			header{
				margin: 0 auto;
				width: 1000px;
				margin-top:20px;
			}
			#picd{
				margin: 0 auto;
				width: 1000px;
				text-align: center;
			}
			#picd img{
				width: 950px;
			}
			header #tip{
				text-align: right;
				height:10px;
			}
			a{
				text-decoration: none;
				color: #848484;
			}
			#dav ul{
				position: absolute;
				margin-top: -45px;
				/* text-align: right; */
				/* right: 0; */
				list-style: none;
				line-height: 30px;
				margin-left: 400px;
				list-style: none;
				font-family: "微软雅黑";
			}
			#dav ul li{
				/* position: absolute; */
				/* text-align: right; */
				/* display: block; */
				float: left;
				margin-left: 30px;
				border-left: 1px solid #A9A9A9;
				/* text-align: center; */
			}
			#dav ul li:first-child{
				border-left: 0px;
			}
			#dav ul li a{
				padding-left: 20px;
				color: black;
			}
		</style>
  </head>
  <body>
		<header>
			<p id="tip">当前用户：<font color="red">${chiName }</font>&nbsp;&nbsp;[<a href="MainController.do?message=logout">安全退出</a>]</p>
			<div id="dav">
				<img src="img/logo.png"/>
				<ul>
					<li><a href="">首页</a></li>
					<li><a href="MainController.do?download=select">资源下载</a></li>
					<li><a href="MainController.do?user=manage">用户管理</a></li>
					<li><a href="MainController.do?download=manage">资源管理</a></li>
					<li><a href="MainController.do?user=center">个人中心</a></li>
				</ul>
				<hr size="10" color="#000000" />
			</div>
			<div id="picd">
			<img src="img/fj.png">
			</div>
		</header>
		
	</body>
</html>
