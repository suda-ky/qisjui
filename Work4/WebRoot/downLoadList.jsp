<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>下载列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
			body{
				margin: 0 auto;
			}
			#downDiv{
				width: 900px;
				margin: 0 auto;
			}
			#downDiv ul{
				margin-left:150px;
			}
			ul li{
				list-style: none;
			}
			ul li div{
				//position: absolute;
				width: 500px;
				margin-top: -85px;
				margin-left: 80px;
			}
			ul li div h3{
				height: 15px;
			}
			ul li div font{
				margin-top: -20px;
				font-size: 12px;
				color: #A9A9A9;
			}
			ul li div button{
				position: absolute;
				margin-top: -30px;
				width: 100px;
				height: 40px;
				color: #35ff64;
				font-size: 14px;
				border: 1px solid #35ff64;
				background-color: white;
			}
			ul li div p{
				font-size: 12px;
				color: #848484;
				width: 450px;
			}
			ul li hr{
				width: 520px;
				margin-top: 20px;
				margin-left: -70px;
			}
			.stars-bg{
			    position: relative;
			    display: inline-block;
			    height: 18px;
			    width: 157px;
				margin-left: -28px;
				transform: scale(0.7);/* 同比例缩小70% */
				vertical-align: middle;/* 图片与文字居中对齐 */
			    background: url(img/evaluate.jpg) 0 0;
			}
			.star-active{
			    position: absolute;
			    left: 0;
			    top: 0;
			    display: block;
			    height: 100%;
			    background: url(img/star.jpg) 0 -24px;
			}
		</style>
		<script>
			function downloadclick(id){
			//var temp = document.getElementById("inid").value;
				window.location.href="DownLoadController.do?download=download&id="+id;
			}
		</script>
  </head>
  
 <body>
		<div id="downDiv">
			<ul>
			<c:forEach items="${list }" var="dl">
				<li>
					<img src="${dl.image }"/>
					<div>
						<h3>${dl.name }</h3>
						<font>大小：<fmt:formatNumber value="${dl.size/1024/1024 }" pattern="#.##" />MB 时间：2020-08-03 星级：</font>
						<span class="stars-bg">
						       <!-- 宽度由实际数据自行计算 如4.2/5*100% -->
						       <i class="star-active" style="width: ${dl.star/5*100 }%"></i>
						   </span>
						   <!-- <span class="right-txt"><b>4.2</b>分</span> -->
						  <!-- <input id="inid${dl.id }" type="hidden" value="${dl.path }"/> --> 
						<button type="button" onclick="downloadclick('${dl.id }')"><b>立即下载</b></button>   
						<p>
							${dl.description }
						</p>
						<hr color="#d1d1d1"/>
					</div> 
				</li>
				</c:forEach>
			</ul>
		</div>
	</body>
</html>
