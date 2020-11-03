$(function(){
	//定义全局的变量,默认为按userName，desc（降序排列），当发生改变，则修改数组中的值
	var sorts = ["u.userName","desc"];
	//进入页面，查询所有的用户
selAllUser();
//点击清空按钮,清空所有输入框
$("#a_clear").click(function(){
	$("#form_search input").val("");
});
//点击增加按钮弹出增加窗口
$("#a_add").click(function(){

	 showDiv();
	 //清空一次省份下拉框和城市下拉框
	 $("#provinceId").empty();
	 $("#provinceId")[0].add(new Option("请选择省份",""));
	 $("#cityId").empty();
	 $("#cityId")[0].add(new Option("请选择城市",""));
	//查询所有的省份；
	 fillProvince();
	 $("#span_title").html("添加用户");
	//alert(1);
});
//点击删除按钮,则取所选中的值
$("#a_delete").click(function(){
	var values = getAllCk();
	if(values==null||values==""){
		alert("请先选择");
	}else{
		//删除用户
		deleteUser(values);
	}
});
//操作下的删除事件
$("tbody").on("click",".delete_class",function(){
	//console.log($(this).attr("value")+" "+JSON.stringify($(this).attr("value")));
	var values = [];
	values.push($(this).attr("value"));
	deleteUser(values);
});
//点查询按钮，获取页面查询所需要的值，并传入到后端
$("#a_search").click(function(){
	var queryParams = getQueryParams();
	//获取 显示条数 的值
	var pageSize= $("#pageSize option:selected").val();
	//var pageNum = $("#pageNum").text();//获取当前页面
//	var sort = "userName";//对谁进行排序
//	var sortOrder = "desc";//排序选择
		//分页的参数
		var pageParams = {
				"pageSize":pageSize,
				"pageNum":"1",
				"sort":sorts[0],
				"sortOrder":sorts[1]
		};
		//alert();，当翻页过后，进行条件查询时不能获取当前页数，否则不能正常显示
		$("#pageNum").text(1);//当前页数
		//调用条件查询用户的方法
		searchUsers(queryParams,pageParams);
});

//修改按钮,或者操作下的修改
$("#a_update").click(function(){
	//获取复选框选中的值
	 var values = getAllCk();

		if(values.length == 1){
		//调用更新用户的方法
			updateUser("tbody tr td input:checked");
		}else if(values==null||values==""){
			alert("请先选择");
		}else{
			alert("暂不支持多用户修改！");
		}
		
});
//点击操作下的修改,动态添加的数据只能用on事件
//on事件的一个好处是给未创建的后代元素绑定事件
$("tbody").on("click",".update_class",function(){
	updateUser(this);
});
//点击行，则选中复选框
$("tbody").on("click","tr",function(){
	//$(this).find("input:checkbox").prop("checked",true);
	//判断复选框是否被选中，选中则取消选中
	if($(this).find("input:checkbox").is(":checked")){
		$(this).find("input:checkbox").prop("checked",false);
		$(this).removeClass("tr_ckColor");
	}else{
		$(this).find("input:checkbox").prop("checked",true);
		$(this).addClass("tr_ckColor");
	}
});

//鼠标移入移出,高亮显示
$("tbody").on("mouseover","tr",function(){
	// $(this).css('background-color','red');
	$(this).addClass("tr_hover");
	// console.log(1);
});
$("tbody").on("mouseout","tr",function(){
	// $(this).css('background-color','red');
	$(this).removeClass("tr_hover");
});

//当标题的checkbox没有被选中,点击则所有的tr被选中,如果已选中则取消所有tr选中
$("#cjAll").click(function(){
	
	if(this.checked){
		$("tbody tr input:checkbox").each(function(){
			// 使用attr只能执行一次, 使用prop则完美实现全选和反选
			// 对于HTML元素本身就带有的固有属性，在处理时，使用prop方法。
			// 对于HTML元素我们自己自定义的DOM属性，在处理时，使用attr方法。
			$(this).prop("checked",true);
			//全选改变颜色
			$(this).each(function(){
			$(this).parents("tr").addClass("tr_ckColor");
			});
		});
	}else{
		$("tbody input:checkbox").each(function(){
			$(this).prop("checked",false);
			//取消全选删除颜色class
			$(this).each(function(){
				$(this).parents("tr").removeClass("tr_ckColor");
			});
		});
	}
});
//当tr被选中时,则显示不一样的背景颜色,动态数据下使用.on("","",function);方法
$("tbody").on("click","tr input:checkbox",function(){
	if(this.checked){
		//使用$("tbody tr"),会使所有的tr背景色改变,用被选中的父类才改变颜色
		$(this).parents("tr").addClass("tr_ckColor");
	}else{
		$(this).parents("tr").removeClass("tr_ckColor");
		}
	});
//点击标题中的用户名的升降序图片
$("#userName_sort").click(function (){
	//将img对象传进去,返回一个数组，第一个值为排序对象，第二个为排序方式
	sorts = sortPic(this);
	//获取输入框中的值
	var queryParams = getQueryParams();//查询条件
	
		//分页的参数
		var pageParams = {
				"pageSize": $("#pageSize option:selected").val(),
				"pageNum":$("#pageNum").text(),
				"sort":sorts[0],
				"sortOrder":sorts[1]
		};
		//alert();
		//调用条件查询用户的方法
		searchUsers(queryParams,pageParams);
		//alert(sorts[0]+" "+sorts[1]);
});
//点击标题中的省份的升降序图片切换
$("#provinceName_sort").click(function (){
	//将img对象传进去,返回一个数组，第一个值为排序对象，第二个为排序方式
	sorts = sortPic(this);
	//获取输入框中的值
	var queryParams = getQueryParams();//查询条件
	
		//分页的参数
		var pageParams = {
				"pageSize": $("#pageSize option:selected").val(),
				"pageNum":$("#pageNum").text(),
				"sort":sorts[0],
				"sortOrder":sorts[1]
		};
		//alert();
		//调用条件查询用户的方法
		searchUsers(queryParams,pageParams);
		//alert(sorts[0]+" "+sorts[1]);
});

//点击下一页
$("#next").click(function(){
	//获取输入框中的值
	var queryParams = getQueryParams();//查询条件
	
	//获取 显示条数 的值
	var pageSize= $("#pageSize option:selected").val();
	var pageNum = $("#pageNum").text();//获取当前页面
//	var sort = "userName";//对谁进行排序
//	var sortOrder = "desc";//排序选择
	var pageCount = $("#pageCount").text();//获取总页数
	//alert(sort+sortOrder);
	//当前页面小于总页数
	if(pageNum<pageCount){
		$("#pageNum").text(++pageNum);//当前页面+1
		//获取当前页面的最后一条数据，作为下一页的开始索引，当前页码-1
		//var start = (pageNum-1)*pageSize;
		//alert(pageNum+" "+start);
		//分页的参数
		var pageParams = {
				"pageSize":pageSize,
				"pageNum":$("#pageNum").text(),
				"sort":sorts[0],
				"sortOrder":sorts[1]
		};
		//alert();
		//调用条件查询用户的方法
		searchUsers(queryParams,pageParams);
	}else{
		alert("当前是最后一页！");
	}
});
//点击上一页
$("#back").click(function(){
	//获取输入框中的值
	var queryParams = getQueryParams();//查询条件
	
	//获取 显示条数 的值
	var pageSize= $("#pageSize option:selected").val();
	var pageNum = $("#pageNum").text();//获取当前页面
//	var sort = "userName";//对谁进行排序
//	var sortOrder = "desc";//排序选择
	//var pageCount = $("#pageCount").text();//获取总页数
	//alert(sort+sortOrder);
	//当前页面为1的时候，就是第一页，则不能再往前
	if(pageNum>1){
		$("#pageNum").text(--pageNum);//当前页面-1
		//alert(pageNum);
		//分页的参数
		var pageParams = {
				"pageSize":pageSize,
				"pageNum":$("#pageNum").text(),
				"sort":sorts[0],
				"sortOrder":sorts[1]
		};
		//alert();
		//调用条件查询用户的方法
		searchUsers(queryParams,pageParams);
	}else{
		alert("当前是第一页！");
	}
});
//点击首页
$("#first").click(function(){
	//获取输入框中的值
	var queryParams = getQueryParams();//查询条件
	
	//获取 显示条数 的值
	var pageSize= $("#pageSize option:selected").val();
	var pageNum = $("#pageNum").text();//获取当前页面
//	var sort = "userName";//对谁进行排序
//	var sortOrder = "desc";//排序选择
	//var pageCount = $("#pageCount").text();//获取总页数
	//alert(sort+sortOrder);
	//点击首页，设置当前页面为1，
		$("#pageNum").text(1);
		//分页的参数
		var pageParams = {
				"pageSize":pageSize,
				"pageNum":$("#pageNum").text(),
				"sort":sorts[0],
				"sortOrder":sorts[1]
		};
		//alert();
		//调用条件查询用户的方法
		searchUsers(queryParams,pageParams);

});
//点击尾页
$("#last").click(function(){
	//获取输入框中的值
	var queryParams = getQueryParams();//查询条件
	
	//获取 显示条数 的值
	var pageSize= $("#pageSize option:selected").val();
	var pageNum = $("#pageNum").text();//获取当前页面
	var sort = "userName";//对谁进行排序
	var sortOrder = "desc";//排序选择
	var pageCount = $("#pageCount").text();//获取总页数
	//alert(sort+sortOrder);
	//点击首页，设置当前页面为1，
		$("#pageNum").text(pageCount);
		//分页的参数
		var pageParams = {
				"pageSize":pageSize,
				"pageNum":$("#pageNum").text(),
				"sort":sorts[0],
				"sortOrder":sorts[1]
		};
		//alert();
		//调用条件查询用户的方法
		searchUsers(queryParams,pageParams);

});
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
	nameTmpb(nametmp,nError);
});
 
//真实姓名失焦事件
tName.blur(function(){
	tNameb(tName,tNameErr);
});

//邮箱失焦事件
tEmail.blur(function(){
	emailb(tEmail,tEmailErr);
});

//省份失焦事件
tpro.blur(function(){
	prob(tpro,tproErr);
});
//省份选择事件
tpro.change(function(){
	tcity.empty();
	tcity[0].add(new Option("请选择城市",""));
//选择省份，下面的城市放到城市下拉框
	if(tpro.val()==""){
		tproErr.html("请选择省份！");
	}else{
		fillCityByProvinceid(tpro.val(),tcity);
		tproErr.html("");
	}
});

//城市失焦事件
tcity.blur(function(){
	cityb(tcity,tcityErr);
});

//密码失焦事件
tpwd.blur(function(){
	pwdb(tpwd,tpwdErr);
});

//确认密码失焦事件
tspwd.blur(function(){
	spwdb(tspwd,tspwdErr,tpwd);
});

//增加点击事件
$("#btnId").click(function(){
	//所有的文本框true，才可以跳转。
	if($("#btnId").html() == "添加用户"){
		if(nameTmpb(nametmp,nError)&&tNameb(tName,tNameErr)&&emailb(tEmail,tEmailErr)&&prob(tpro,tproErr)&&cityb(tcity,tcityErr)&&pwdb(tpwd,tpwdErr)&&spwdb(tspwd,tspwdErr,tpwd)){	
			//增加新用户
			queryUser("insert",nametmp.val(),tName.val(),tEmail.val(),tcity.val(),tpwd.val());
		}else{
			alert("填写有误，请修改后，再添加！");
		}
	}else{
		//去掉用户名的查重，以及邮箱的查重
		if(tNameb(tName,tNameErr)&&prob(tpro,tproErr)&&cityb(tcity,tcityErr)&&pwdb(tpwd,tpwdErr)&&spwdb(tspwd,tspwdErr,tpwd)){	
			//增加新用户
			queryUser("update",nametmp.val(),tName.val(),tEmail.val(),tcity.val(),tpwd.val());
		}else{
			alert("填写有误，请修改后，再提交！");
		}
	}
	
});
});
	function selAllUser(){
	//分页的参数
	var pageParams = {
			"pageSize":$("#pageSize option:selected").val(),
			"pageNum":"1",
			"sort":"userName",
			"sortOrder":"desc"
	};
	pageParams = JSON.stringify(pageParams);
	//传到后台
	$.ajax({
		type:"post",
		url:"userMangerAjax.do?flag=allUser",
		data:{"queryParams":"","pageParams":pageParams},
		dataType:"json",
		//async:false,//关闭异步， 方可使用jQuery的隔行换色，以及鼠标的移入移出
		success:function(response){
				var rows = response.rows;
				var total = response.total;
				//计算总页数,向上取整
				var pageCount = Math.ceil(total / 10);
				$("#pageNum").text(1);//当前页数
				$("#total").text(total);
				$("#pageCount").text(pageCount);//总页数
				//清空tbody
				$("tbody").empty();
				//填数据到表格tbody
				$.each(rows,function(index,row){
					var s = JSON.stringify(row);
					var str = "<tr data='"+s+"'>";
						str = str + '<td><input type="checkbox" value='+row.name+' /></td>';
						str = str + '<td>'+row.name+'</td>';
						str = str + '<td>'+row.chiName+'</td>';
						str = str + '<td>'+row.email+'</td>';
						str = str + '<td>'+row.city.province.province+'</td>';
						str = str + '<td>'+row.city.city+'</td>';
						str = str + '<td><a href="javascript:void(0);" value='+row.name+' class="delete_class">删除</a>';
						str = str + '&nbsp;&nbsp;<a href="javascript:void(0);" class="update_class">修改</a></td>';
						str = str + '</tr>';
						$("tbody").append(str);
				});
				//奇数行
				$("tbody tr:odd").addClass("tr_odd");
				//偶数行
				$("tbody tr:even").addClass("tr_even");
				
		}
	});
}
// 弹出增加的窗口页面方法
function showDiv(){
	$("#fabe").css("display","block");
	$("#myDiv").css("display","block");
	//设置弹出层居中
	var winHeight = $(window).height();//获取当前页面的高度
	var winWidth = $(window).width();
	var popupHeight = $("#myDiv").height();//获取弹出层的高度
	var popupWidth = $("#myDiv").width();
	//将弹出层设置成页面居中
	var posTop = (winHeight-popupHeight)/2;
	var posLeft = (winWidth-popupWidth)/2;
	$("#myDiv").css({
		"left":posLeft+"px",
		"top":posTop+"px",
		"display":"block"
	});
}

//关闭弹出层
	function closeDiv(){
		//每次关闭都清除留下的数据
		$("#uname").val("");
		//用户名作为识别用户的唯一标准，不能更改
		$("#uname").removeAttr("disabled");
		$("#sName").val("");
		$("#email").val("");
		$("#pwd").val("");
		$("#sPwd").val("");
		$("#nameError").html("");//用户名错误提示
		$("#sNameError").html("");//错误提示
		$("#emailError").html("");
		$("#provinceError").html("");
		$("#cityError").html("");
		$("#pwdError").html("");
		$("#sPwdError").html("");
		
		$("#fabe").css("display","none");
		$("#myDiv").css("display","none");
	}
	//获取选中的值
	function getAllCk(){
		var names =[];
		$("tbody input:checked").each(function(){
			names.push($(this).attr("value"));
		});
		return names;
}
//条件查询数据
function searchUsers(queryParams,pageParams){
	
	pageParams = JSON.stringify(pageParams);
	//传到后台
	$.ajax({
		type:"post",
		url:"userMangerAjax.do?flag=allUser",
		data:{"queryParams":queryParams,"pageParams":pageParams},
		dataType:"json",
		//async:false,//关闭异步，方可使用jQuery的隔行换色，以及鼠标的移入移出
		success:function(response){
				var rows = response.rows;
				var total = response.total;
				//计算总页数,向上取整
				var pageCount = Math.ceil(total / $("#pageSize option:selected").val());
				$("#total").text(total);
				$("#pageCount").text(pageCount);//总页数
				//填数据到表格tbody
				$("tbody").empty();
				$.each(rows,function(index,row){
					var s = JSON.stringify(row);
					var str = "<tr data='"+s+"'>";
						str = str + '<td><input type="checkbox" value='+row.name+' /></td>';
						str = str + '<td>'+row.name+'</td>';
						str = str + '<td>'+row.chiName+'</td>';
						str = str + '<td>'+row.email+'</td>';
						str = str + '<td>'+row.city.province.province+'</td>';
						str = str + '<td>'+row.city.city+'</td>';
						str = str + '<td><a href="javascript:void(0);" value='+row.name+'>删除</a>';
						str = str + '&nbsp;&nbsp;<a href="javascript:void(0);" class="update_class">修改</a></td>';
						str = str + '</tr>';
						$("tbody").append(str);
				});
				//奇数行
				$("tbody tr:odd").addClass("tr_odd");
				//偶数行
				$("tbody tr:even").addClass("tr_even");
		}
	});
	
}
	//查询所有的省份下拉框和
	function fillProvince(){
		$.ajax({
			type:"post",
			url:"ProvinceCityAjax.do",
			data:{},
			dataType:"json",
			async:false,//关闭异步，才能使更新用户，正常获取下拉框的值
			success:function(response){
				var provinceSe = $("#provinceId");
				for(index = 0;index < response.length;index++){
				
					provinceSe[0].add(new Option(response[index].province,response[index].id));
				}
			}
		});
	}
	
	//查询省份下所有的城市下拉框
	function fillCityByProvinceid(provinceid,tcity){
		$.ajax({
			type:"post",
			url:"ProvinceCityAjax.do",
			data:{"provinceId":provinceid},
			dataType:"json",
			async:false,//关闭异步，才能使更新用户，正常获取下拉框的值
			success:function(response){
				for(index = 0;index < response.length;index++){
					tcity[0].add(new Option(response[index].city,response[index].id));
				}
			}
		});
	}
	//用户名判断
	function nameTmpb(nametmp,nError){
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
	//中文名
	 function tNameb(tName,tNameErr){
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
	//邮箱判断
 function emailb(tEmail,tEmailErr){
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
	//省份
	 function prob(tpro,tproErr){
		// alert(tpro.val());
		if(tpro.val()==""){
			tproErr.html("必须选择省份");
			return false;
		}else{
			tproErr.html("");
			return true;
		}
	};
	//城市
	 function cityb(tcity,tcityErr){
		// alert(tpro.val());
		if(tcity.val()==""){
			tcityErr.html("必须选择城市");
			return false;
		}else{
			tcityErr.html("");
			return true;
		}
	};
	//密码
	 function pwdb(tpwd,tpwdErr){
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
	//密码确认
	 function spwdb(tspwd,tspwdErr,tpwd){
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
	//获取输入框中的值
function getQueryParams(){
		//获取输入框中的值
		var queryParams = {};//查询条件
		var t = $("#form_search").serializeArray();//将form表单转换成数组
		//将每个输入框中的值循环出来
		$.each(t,function(){
			queryParams[this.name] = this.value;
		});
		//再将循环出来的值转换成json字符串
		queryParams = JSON.stringify(queryParams);
		return queryParams;
	}
//切换sort图片
function sortPic(sort){
	var sorts = [];
	if(sort.id == "userName_sort"){
		sorts[0] = "u.userName";
	}else{
		sorts[0] = "p.province";
	}
	//d定义图片数组
	var imgs = [
	   "img/desc.png",
	   "img/d_a.png",
	   "img/asc.png"
	   ];
	//alert(imgs[2]);
	var title = sort.title;
	if(title=="降序"){
		$(sort).attr("src",imgs[1]);
		$(sort).attr("title","降升序");
		sorts[1] = "desc";
	}else if(title=="降升序"){
		$(sort).attr("src",imgs[2]);
		$(sort).attr("title","升序");
		sorts[1] = "asc";
	}else{
		$(sort).attr("src",imgs[0]);
		$(sort).attr("title","降序");
		sorts[1] = "desc";
	}
	return sorts;
}
function queryUser(tmp,nametmp,tName,tEmail,tcity,tpwd){
	$.ajax({
		type:"post",
		url:"RegisterController.do",
		data:{flag:tmp,uname:nametmp,chiname:tName,email:tEmail,cityId:tcity,pwd:tpwd},
		dataType:"json",
		success:function(response){
		if(response.code == 1){//添加或修改成功
			alert(response.info);
			}else{
			alert(response.info);
			}
		}
	});
	//添加成功后关掉增加页面，并刷新列表
	closeDiv();
	window.location.reload();//刷新当前页面.
}
function deleteUser(values){
	$.ajax({
		type:"post",
		url:"userMangerAjax.do?flag=delete",
		data:{names:JSON.stringify(values)},
		dataType:"json",
		success:function(response){
		if(response.code == 1){//删除的状态
			alert("删除成功");
			}else{
			alert("删除失败");
			}
		}
	});
	//删除成功刷新页面
	window.location.reload();
	//alert(values);
}
//修改用户填充窗口中内容的方法
function updateUser(obj){
	 //弹出修改用户窗口
	showDiv();
	 //清空一次省份下拉框和城市下拉框
	 $("#provinceId").empty();
	 $("#provinceId")[0].add(new Option("请选择省份",""));
	 //查询所有省份，并放到option中
	 fillProvince();
	$("#span_title").html("修改用户");
	$("#btnId").text("立即修改");
	//将放在tr data中的json字符串转成json对象
	var d = JSON.parse($(obj).parent().parent().attr("data"));
	$("#uname").val(d.name);
	//用户名作为识别用户的唯一标准，不能更改
	$("#uname").attr("disabled","disabled");
	$("#sName").val(d.chiName);
	$("#email").val(d.email);
	$("#pwd").val(d.passWord);
	$("#sPwd").val(d.passWord);
	//先关闭掉异步，才能正确显示默认的值，不关闭则取得为空
	 $("#provinceId option[value='"+d.city.province.id+"']").prop("selected", true);
	//先清空一遍城市下拉框，再把默认的值填充进去
	 $("#cityId").empty();
	 fillCityByProvinceid(d.city.province.id,$("#cityId"));
	 $("#cityId option[value='"+d.city.id+"']").prop("selected", true);	
}
