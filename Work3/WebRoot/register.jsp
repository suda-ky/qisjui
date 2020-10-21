<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>注册页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="js/jquery-1.10.2.js"></script>
<script>
$(function(){
	//加载页面，则查询所有的省份；
	fillProvince();
	
	var nametmp = $("#uname");//用户名
	var nError = $("#nameError");//用户名错误提示
	var tName = $("#sName");//真实姓名文本框
	var tNameErr = $("#sNameError");//错误提示
	var tEmail = $("#email");//邮箱
	var tEmailErr = $("#emailError");
	var tpro = $("#provinceId");//省份
	var tproErr = $("#provinceError");
	var tcity = $("#cityId");//城市
	var tcityErr = $("#cityError");
	var tpwd = $("#pwd");//密码
	var tpwdErr = $("#pwdError");
	var tspwd = $("#sPwd");//确认密码
	var tspwdErr = $("#sPwdError");
	
	//用户名失焦事件
	nametmp.blur(function(){
		nameTmpb();
	});
	 nameTmpb = function (){
		 if(nametmp.val()==""||nametmp.val()==null){
				nError.text("用户名不能为空!");
				return false;
			}else{
				//满足正则表达式
				if(/^[a-z]\w{3,14}$/.test(nametmp.val())){
				//查询数据库是否被注册过
				var flag;
				$.ajax({
					type:"post",
					url:"NameAjax.do",
					data:{username:nametmp.val()},
					dataType:"json",
					async:false,//关闭异步
					success:function(response){
						if(response.code == 1){//如果被注册过，那就给出提示，并不能注册
						nError.html("用户名已注册！");
						flag = false;
						}else{
						nError.html("");
						flag = true;
						}
					}
				});
				return flag;
				}else {//不满足正则表达式
					//nError.css("margin-left","50px");
					nError.html("只能使用英文字母和数字,以字母开头,长度为4到15个字符");
					return false;
				}
			}
	};
	//真实姓名失焦事件
	tName.blur(function(){
		tNameb();
	});
	tNameb = function(){
		if(tName.val()==""||tName.val()==null){
			tNameErr.html("真实姓名不能为空！");
			return false;
		}else{
			if(/^[\u4E00-\u9FA5]{2,4}$/.test(tName.val())){
				tNameErr.html("");
				return true;
			}else{
				//tNameErr.css("margin-left","-70px");
				tNameErr.html("真实姓名只能是2-4个中文字符！");
				return false;
			}
		}
	};
	//邮箱失焦事件
	tEmail.blur(function(){
		emailb();
	});
	emailb = function(){
		if(tEmail.val()==""||tEmail.val()==null){
			tEmailErr.html("邮箱不能为空！");
			return false;
		}else{
			var em = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
			if(em.test(tEmail.val())){
				var flag;
				//查询数据库是否被注册过
				$.ajax({
					type:"post",
					url:"EmailAjax.do",
					data:{email:tEmail.val()},
					async:false,//关闭异步，因为异步不能获得返回值，故关闭异步
					dataType:"json",
					success:function(response){
						if(response.code == 1){//如果已存在，那就给出提示，并不能注册
						tEmailErr.html("该邮箱已存在！");
						flag = false;
						}else{
							tEmailErr.html("");
							flag = true;
						}
					}
				});
				return flag;
			}else{
				tEmailErr.html("邮箱格式不正确！");
				return false;
			}
		}
	};
		//省份失焦事件
	tpro.blur(function(){
		prob();
	});
	//省份选择事件
	tpro.change(function(){
		tcity.empty();
		tcity[0].add(new Option("请选择城市",""));
	//选择省份，下面的城市放到城市下拉框
		if(tpro.val()==""){
			tproErr.html("请选择省份！");
		}else{
			fillCityByProvinceid(tpro.val());
			tproErr.html("");
		}
	});
	prob = function(){
		// alert(tpro.val());
		if(tpro.val()==""){
			tproErr.html("必须选择省份");
			return false;
		}else{
			tproErr.html("");
			return true;
		}
	};
	//城市失焦事件
	tcity.blur(function(){
		cityb();
	});
	cityb = function(){
		// alert(tpro.val());
		if(tcity.val()==""){
			tcityErr.html("必须选择城市");
			return false;
		}else{
			tcityErr.html("");
			return true;
		}
	};
	//密码失焦事件
	tpwd.blur(function(){
		pwdb();
	});
	pwdb = function(){
		if(tpwd.val()==""||tpwd.val()==null){
			tpwdErr.html("密码不能为空！");
			return false;
		}else{
			if(tpwd.val().length>=3){
				tpwdErr.html("");
				return true;
			}else{
				tpwdErr.html("密码长度至少为3！");
				return false;
			}
		}
	};
	//确认密码失焦事件
	tspwd.blur(function(){
		spwdb();
	});
	spwdb = function(){
		if(tspwd.val()==""||tspwd.val()==null){
			tspwdErr.html("确认密码不能为空！");
			return false;
		}else{
			if(tspwd.val()==tpwd.val()){
				tspwdErr.html("");
				return true;
			}else{
				tspwdErr.html("密码不一致！");
				return false;
				}
			}
	};
	//注册点击事件
	$("#btnId").click(function(){
		//所有的文本框true，才可以跳转。
		if(nameTmpb()&&tNameb()&&emailb()&&prob()&&cityb()&&pwdb()&&spwdb()){
			$.ajax({
				type:"post",
				url:"RegisterController.do",
				data:{uname:nametmp.val(),chiname:tName.val(),email:tEmail.val(),cityId:tcity.val(),pwd:tpwd.val()},
				dataType:"json",
				success:function(response){
				if(response.code == 1){//如果已存在，那就给出提示，并不能注册
					alert("注册成功！");//弹出提示框，然后跳转到登录页面
					window.location.href = "login.jsp";
					}else{
						alert("注册失败！");
					}
				}
			});
		}else{
			alert("填写有误，请修改后，再注册！");
		}
	});
	
	//查询所有的省份下拉框和
	function fillProvince(){
		$.ajax({
			type:"post",
			url:"ProvinceCityAjax.do",
			data:{},
			dataType:"json",
			success:function(response){
				var provinceSe = $("#provinceId");
				
				for(index = 0;index < response.length;index++){
				
					provinceSe[0].add(new Option(response[index].province,response[index].id));
				}
			}
		});
	}
	
	//查询省份下所有的城市下拉框
	function fillCityByProvinceid(provinceid){
		$.ajax({
			type:"post",
			url:"ProvinceCityAjax.do",
			data:{"provinceId":provinceid},
			dataType:"json",
			success:function(response){
				for(index = 0;index < response.length;index++){
					tcity[0].add(new Option(response[index].city,response[index].id));
				}
			}
		});
	}
});	
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
width: 400px;
height: 600px;
text-align: center;

}
#formId{
/* border: 1px solid red; */
margin-top: 50px;
}
input{
width: 230px;
height: 30px;
border-radius: 4px;
border:1px solid gray;
background: url(img/name.png)4px no-repeat;/* 插入图片不平铺 */
text-indent: 2em;/* 不让文字顶格输入 */
}
#email{
background: url(img/email.png)4px no-repeat;/* 插入图片不平铺 */
}
select{
width: 230px;
height: 30px;
border-radius: 4px;
border:1px solid gray;
text-indent: 2em;/* 不让文字顶格输入 */
background: url(img/city.png)4px no-repeat;/* 插入图片不平铺 */
}
#pwd,#spwd{
background: url(img/pwd.png)4px no-repeat;/* 插入图片不平铺 */
}

span{
font-size: 12px;
color: red;
/* margin-top: 20px; */
text-align: left;/*覆盖掉之前的文本居中 */
width:330px;
height:25px;
display:inline-block;/* 设置了这个元素，span会被认定为是块状元素，才可以设置width和height */
margin-left:80px;
/*background-color:gray;*/
}
#fontId{
color: blue;
font-size: 13px;
align-items: center;
}
#aaId{
margin-left: 40px;
color: blue;
font-size: 13px;
text-decoration: none;
position: absolute;
margin-top: -40px;
}
h2{
color: grey;
margin-left: -180px;
}
</style>

  </head>
  
  <body>
<div id="logDiv">
	<div id="formId">
		<p><h2>账号注册</h2><a href="login.jsp" id="aaId">已有帐号，请登录</a></p>
		<p><input type="text" name="userName" id="uname" placeholder="用户名" /><br />
		<span id="nameError"></span><br/>
		<input type="text" name="chiName" id="sName" placeholder="真实姓名"/><br />
		<span id="sNameError"></span><br/>
		<input type="text" name="email" id="email" placeholder="邮箱"/><br />
		<span id="emailError"></span><br/>
		<select name="province" id="provinceId" placeholder="省份">
		<option value="">请选择省份</option>
		</select><br />
		<span id="provinceError"></span><br />
		<select name="province" id="cityId"">
		<option value="">请选择城市</option>
		</select><br />
		<span id="cityError"></span><br/>
		<input type="password" name="password" id="pwd" placeholder="密码"/><br />
		<span id="pwdError"></span><br/>
		<input type="password" name="spassword" id="sPwd" placeholder="确认密码" Error/><br />
		<span id="sPwdError"></span><br/>
		</p>
		<p><button type="button" style="width: 220px;height: 40px;font-size: 18px;color:white;background-color: #8BCC32;border: chartreuse;" id="btnId">立即注册</button><br/>
		<span id="Error"></span>
		</p>
	</div>
</div>
</body>
</html>
