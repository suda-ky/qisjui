<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
		
	<link rel="stylesheet" type="text/css" href="css/userManger.css"/>
	<script src="js/jquery-1.10.2.js"></script>
	<script src="js/userManger.js" type="text/javascript" charset="utf-8"></script>

  </head>
  
 	<body>
		<div id="div_table">
			<div id="div_header">
				<form id="form_search">
					<input type="text" name="userName" placeholder="请输入用户名"/>
					<input type="text" name="chiName" placeholder="请输入中文名"/>
					<input type="text" name="email" placeholder="请输入邮箱"/>
					<input type="text" name="provinceName" placeholder="请输入省份"/>
				</form>
				<div id="div_btn">
					<a href="javascript:void(0);" id="a_search">查找</a>
					<a href="javascript:void(0);" id="a_clear">清空</a>
					<a href="javascript:void(0);" id="a_add">增加</a>
					<a href="javascript:void(0);" id="a_delete">删除</a>
					<a href="javascript:void(0);" id="a_update">修改</a>
				</div>
			</div>
				<table>
					<thead>
						<tr>
							<th width="5%"><input type="checkbox" id="cjAll" title="选择"/></th>
							<th  width="20%" class="bg" id="sortByUserName" data="userName">用户名
							<img title="降序" src="img/desc.png" width="10px" id="userName_sort" style="position: absolute;margin-left: 50px;"></th>
							
							<th width="10%">中文名</th>
							<th width="30%">邮箱</th>
							<th width="10%" id="sortByProvinceName" data="rpovinceName">省份
							<img title="降序" src="img/desc.png" width="10px" id="provinceName_sort" style="position: absolute;margin-left: 15px;"></th>
							<th width="10%">城市</th>
							<th width="15%">操作</th>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
				<div id="div_bottom">
					<div id="div_size">
						每页
						<select id="pageSize">
							<option>5</option>
							<option selected="selected">10</option>
							<option>20</option>
						</select>,共<span id="total"></span>条数据，
						<span id="pageNum"></span>页/<span id="pageCount"></span>页
					</div>
					<div id="div_nav">
						<a id="first" href="javascript:void(0);">首页</a>
						<a id="back" href="javascript:void(0);">上一页</a>
						<a id="next" href="javascript:void(0);">下一页</a>
						<a id="last" href="javascript:void(0);">尾页</a>
					</div>
				</div>
		</div>
		<!-- 增加与修改页面的div -->
		<div id="fabe" class="black_overlay" onclick="closeDiv()"></div>
			<div id="myDiv" class="white_content">
				<div style="text-align: right;height: 20px;">
					<span style="font-size: 24px;margin-right:120px;" id="span_title"></span>
					<span style="font-size: 24px;margin-right:5px;cursor: pointer;" title="点击关闭" onclick="closeDiv()">x</span>	
			</div>
				<div id="div_in" style="text-align:center;margin-top:30px;">
					<p>
					<input type="text" name="userName" id="uname" placeholder="用户名" /><br />
					<span id="nameError"></span><br/>
					<input type="text" name="chiName" id="sName" placeholder="真实姓名"/><br />
					<span id="sNameError"></span><br/>
					<input type="text" name="email" id="email" placeholder="邮箱"/><br />
					<span id="emailError"></span><br/>
					<select name="province" id="provinceId" placeholder="省份">
					<!-- <option value="">请选择省份</option> -->
					</select><br />
					<span id="provinceError"></span><br />
					<select name="province" id="cityId">
					<option value="">请选择城市</option>
					</select><br />
					<span id="cityError"></span><br/>
					<input type="password" name="password" id="pwd" placeholder="密码"/><br />
					<span id="pwdError"></span><br/>
					<input type="password" name="spassword" id="sPwd" placeholder="确认密码" Error/><br />
					<span id="sPwdError"></span><br/>
					</p>
					<p>
					<button type="button" style="width: 220px;height: 40px;font-size: 18px;color:white;background-color: #8BCC32;border: chartreuse;" id="btnId">添加用户</button><br/>
					<span id="Error"></span>
					</p>
				</div>
				<!-- <input type="text"/> -->
			</div>
	</body>
</html>
