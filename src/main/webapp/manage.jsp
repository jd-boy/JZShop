<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>注册-JZ商城</title>
<link rel="stylesheet" href="css/manager.css" type="text/css">
<script src="js/jquery-1.12.4.js" type="text/javascript"></script>
</head>

<body style="margin:0px;">
	<jsp:include page="header-manager.jsp" />
	<!-- 主体内容 -->
	
	<%
		Map<Integer, String> bigType = (Map<Integer, String>)request.getAttribute("bigType");
		Map<Integer, String> smallType = (Map<Integer, String>)request.getAttribute("smallType");
	%>
	
	<div class="mainqq">
		<div class="imagedf">
			<a href="/index" title="点击返回首页"><img src="images/jzlogo.png"></a>
		</div>
		<div class="infooo">
			<div style="position:absolute;height:70px;width:100%;
					background-color:#F2F2F2;">
				<h1 style="text-align:center;margin-top:10px">后台管理</h1>
			</div>
			
			<!-- 冻结/解除会员账号 -->
			<div style="position:absolute;height:100px;width:100%;top:80px;">
				<div style="position:absolute;width:50%;height:100%;">
					<div style="position:absolute;width:50%;left:35px;">
						<label style="font-size:23px;color:red;">冻结普通账号：</label>
					</div>
					<div style="position:absolute;top:40%;left:35px;">
						<input type="text" name="username" id="username" value="" class="inputt"
							autocomplete="off" maxlength="50" placeholder="输入需冻结的账号">
					</div>
					<div style="position:absolute;top:40%;left:67%;width:25%;">
						<button style="font-size:22px;" onclick="freeze();">冻结</button>
					</div>
				</div>
				
				<div style="position:absolute;left:50%;width:50%;height:100%;">
					<div style="position:absolute;width:70%;left:35px;">
						<label style="font-size:23px;color:red;">解除普通账号冻结：</label>
					</div>
					<div style="position:absolute;top:40%;left:35px;">
						<input type="text" name="username1" id="username1" value="" autocomplete="off"
							class="inputt" maxlength="50" placeholder="输入需解除冻结的账号">
					</div>
					<div style="position:absolute;top:40%;left:67%;width:25%;">
						<button style="font-size:22px;" onclick="removeFreeze()">解除</button>
					</div>
				</div>
			</div>
			
			<!-- 修添加商品总分类 -->
			<div style="position:absolute;height:100px;width:100%;top:180px;">
				<div style="position:absolute;width:50%;height:100%;">
					<div style="position:absolute;width:50%;left:35px;">
						<label style="font-size:23px;color:red;">添加商品总分类：</label>
					</div>
					<div style="position:absolute;top:40%;left:35px;">
						<input type="text" name="bigtype" id="bigtype" value="" autocomplete="off"
							class="inputt" maxlength="50" placeholder="输入新总分类">
					</div>
					<div style="position:absolute;top:40%;left:67%;width:25%;">
						<button style="font-size:22px;" onclick="addBigType()">添加</button>
					</div>
				</div>
				
				<div style="position:absolute;left:50%;width:50%;height:100%;">
					<div style="position:absolute;width:50%;left:35px;">
						<label style="font-size:23px;color:red;">添加商家权限：</label>
					</div>
					<div style="position:absolute;top:40%;left:35px;">
						<input type="text" name="username2" id="username2" value="" autocomplete="off"
							class="inputt" maxlength="30" placeholder="输入需添加权限的账号">
					</div>
					<div style="position:absolute;top:40%;left:67%;width:25%;">
						<button style="font-size:22px;" onclick="addShop()">添加</button>
					</div>
				</div>
			</div>
			
			<!-- 修添加商品子分类 -->
			<div style="position:absolute;height:100px;width:100%;top:280px;">
				<div style="position:absolute;width:100%;height:100%;">
					<div style="position:absolute;width:50%;left:35px;">
						<label style="font-size:23px;color:red;">添加商品子分类：</label>
					</div>
					<div style="position:absolute;top:43%;left:35px;">
						<label style="font-size:23px;">选择父类：</label>
					</div>
					<div style="position:absolute;top:50%;left:170px;width:25%;">
						<select id="type" name="type" style="height:30px;width:100px;">
							<option value="-1">--请选择--</option>
							<%
								for(Map.Entry<Integer,String> en : bigType.entrySet()) {
							%>
								<option value="<%=en.getKey()%>"><%=en.getValue()%></option>
							<%  } %>
						</select>
					</div>
					
					<div style="position:absolute;top:43%;left:300px;">
						<label style="font-size:23px;">输入新分类：</label>
					</div>
					<div style="position:absolute;top:40%;left:442px;">
						<input type="text" name="newsmalltype" id="newsmalltype" value="" autocomplete="off"
							class="inputt" maxlength="30" placeholder="输入新子分类">
					</div>
					<div style="position:absolute;top:40%;left:673px;width:25%;">
						<button style="font-size:22px;" onclick="addSmallType()">添加</button>
					</div>
				</div>
			</div>
			
			<!-- 修改商品总分类 -->
			<div style="position:absolute;height:100px;width:100%;top:380px;">
				<div style="position:absolute;width:100%;height:100%;">
					<div style="position:absolute;width:50%;left:35px;">
						<label style="font-size:23px;color:red;">修改商品总分类：</label>
					</div>
					<div style="position:absolute;top:43%;left:35px;">
						<label style="font-size:23px;">选择总分类：</label>
					</div>
					<div style="position:absolute;top:50%;left:170px;width:25%;">
						<select id="selectbigtype1" name="selectbigtype1" style="height:30px;width:100px;">
							<option value="-1">--请选择--</option>
							<%
								for(Map.Entry<Integer,String> en : bigType.entrySet()) {
							%>
								<option value="<%=en.getKey()%>"><%=en.getValue()%></option>
							<%  } %>
						</select>
					</div>
					
					<div style="position:absolute;top:43%;left:300px;">
						<label style="font-size:23px;">输入新分类：</label>
					</div>
					<div style="position:absolute;top:40%;left:442px;">
						<input type="text" name="alterbigtype" id="alterbigtype" value="" autocomplete="off"
							class="inputt" maxlength="30" placeholder="输入新的总分类名">
					</div>
					<div style="position:absolute;top:40%;left:673px;width:25%;">
						<button style="font-size:22px;" onclick="alterBigType()">修改</button>
					</div>
				</div>
			</div>
			
			<!-- 修改商品子分类 -->
			<div style="position:absolute;height:100px;width:100%;top:480px;">
				<div style="position:absolute;width:100%;height:100%;">
					<div style="position:absolute;width:50%;left:35px;">
						<label style="font-size:23px;color:red;">修改商品子分类：</label>
					</div>
					<div style="position:absolute;top:43%;left:35px;">
						<label style="font-size:23px;">选择子类：</label>
					</div>
					<div style="position:absolute;top:50%;left:170px;width:25%;">
						<select id="smalltype" name="smalltype" style="height:30px;width:100px;">
							<option value="-1">--请选择--</option>
							<%
								for(Map.Entry<Integer,String> en : smallType.entrySet()) {
							%>
								<option value="<%=en.getKey()%>"><%=en.getValue()%></option>
							<%  } %>
						</select>
					</div>
					
					<div style="position:absolute;top:43%;left:300px;">
						<label style="font-size:23px;">输入新分类：</label>
					</div>
					<div style="position:absolute;top:40%;left:442px;">
						<input type="text" name="altersmalltype" id="altersmalltype" value="" autocomplete="off"
							class="inputt" maxlength="30" placeholder="输入新的子分类名">
					</div>
					<div style="position:absolute;top:40%;left:673px;width:25%;">
						<button style="font-size:22px;" onclick="alterSmallType()">修改</button>
					</div>
				</div>
			</div>
			
			<!-- 删除商品分类 -->
			<div style="position:absolute;height:100px;width:100%;top:580px;">
				<div style="position:absolute;width:50%;height:100%;">
					<div style="position:absolute;width:50%;left:35px;">
						<label style="font-size:23px;color:red;">删除商品总分类：</label>
					</div>
					<div style="position:absolute;top:45%;left:35px;">
						<select id="delSupertype" name="delSupertype" style="height:40px;width:100px;">
							<option value="-1">--请选择--</option>
							<%
								for(Map.Entry<Integer,String> en : bigType.entrySet()) {
							%>
								<option value="<%=en.getKey()%>"><%=en.getValue()%></option>
							<%  } %>
						</select>
					</div>
					<div style="position:absolute;top:45%;left:67%;width:25%;">
						<button style="font-size:22px;" onclick="delBType()">删除</button>
					</div>
				</div>
				
				<div style="position:absolute;left:50%;width:50%;height:100%;">
					<div style="position:absolute;width:50%;left:35px;">
						<label style="font-size:23px;color:red;">删除商品子分类：</label>
					</div>
					<div style="position:absolute;top:45%;left:35px;">
						<select id="delSubtype" name="delSubtype" style="height:40px;width:100px;">
							<option value="-1">--请选择--</option>
							<%
								for(Map.Entry<Integer,String> en : smallType.entrySet()) {
							%>
								<option value="<%=en.getKey()%>"><%=en.getValue()%></option>
							<%  } %>
						</select>
					</div>
					<div style="position:absolute;top:45%;left:67%;width:25%;">
						<button style="font-size:22px;" onclick="delSType()">删除</button>
					</div>
				</div>
			</div>
			
		</div>
		
		<%
			int managerType = (int)request.getAttribute("managerType");
			if(managerType == 1) {
		%>
		<!-- 超级管理员权限 -->
		<div class="infooo" style="top:900px">
			<div style="position:absolute;height:70px;width:100%;
					background-color:#F2F2F2;">
				<h1 style="text-align:center;margin-top:10px">超级管理员权限</h1>
			</div>
			<!-- 添加管理员 -->
			<div style="position:absolute;height:100px;width:100%;top:80px;">
				
				<div style="position:absolute;width:100%;height:100%;">
					<div style="position:absolute;width:50%;left:35px;">
						<label style="font-size:23px;color:red;">添加管理员账号：</label>
					</div>
					<div style="position:absolute;top:40%;left:35px;">
						<label style="font-size:23px;">管理员账号：</label>
					</div>
					<div style="position:absolute;top:40%;left:170px;width:25%;">
						<input type="text" name="adminname" id="adminname" value="" autocomplete="off"
							class="inputt" style="width:140px" maxlength="30" placeholder="输入新的管理员账号">
					</div>
					
					<div style="position:absolute;top:43%;left:328px;">
						<label style="font-size:23px;">账号密码：</label>
					</div>
					<div style="position:absolute;top:40%;left:442px;">
						<input type="text" name="adminpwd" id="adminpwd" value="" autocomplete="off"
							class="inputt" maxlength="16" placeholder="输入新的管理员账号密码">
					</div>
					<div style="position:absolute;top:40%;left:673px;width:25%;">
						<button style="font-size:22px;" onclick="addAdmin()">添加</button>
					</div>
				</div>
				
			</div>
			
			<!-- 冻结/解除管理员账号 -->
			<div style="position:absolute;height:100px;width:100%;top:180px;">
				<div style="position:absolute;width:50%;height:100%;">
					<div style="position:absolute;width:50%;left:35px;">
						<label style="font-size:23px;color:red;">冻结管理员账号：</label>
					</div>
					<div style="position:absolute;top:40%;left:35px;">
						<input type="text" name="managername" id="managername" value="" class="inputt"
							autocomplete="off" maxlength="50" placeholder="输入需冻结的账号">
					</div>
					<div style="position:absolute;top:40%;left:67%;width:25%;">
						<button style="font-size:22px;" onclick="managerFreeze();">冻结</button>
					</div>
				</div>
				
				<div style="position:absolute;left:50%;width:50%;height:100%;">
					<div style="position:absolute;width:70%;left:35px;">
						<label style="font-size:23px;color:red;">解除管理员账号冻结：</label>
					</div>
					<div style="position:absolute;top:40%;left:35px;">
						<input type="text" name="managername1" id="managername1" value="" autocomplete="off"
							class="inputt" maxlength="50" placeholder="输入需解除冻结的账号">
					</div>
					<div style="position:absolute;top:40%;left:67%;width:25%;">
						<button style="font-size:22px;" onclick="removeManagerFreeze()">解除</button>
					</div>
				</div>
			</div>
			
		</div>
		<%
			}
		%>
	</div>
	
	<!-- //主体内容 -->
	
</body>
<!-- 验证输入的注册信息是否合法 -->

<script>

	var regu = "^[ ]+$";
	var re = new RegExp(regu);
	
	function removeManagerFreeze() {
		var user = document.getElementById("managername1").value;
		
		if(user.length == 0 || re.test(user)) {
			alert("请输入新管理员账号！！");
			return ;
		}
		
		if (/^[\u4e00-\u9fa5]+$/.test($('#managername1').val())) {
			alert("账号不能输入汉字！");
			return ;
		}
		
		if(window.confirm('确定此管理员账号的冻结吗？')) {
			var info = {admin:document.getElementById("managername1").value};
			$.ajax({
		         url: "/removeManagerFreeze",
		         data: JSON.stringify(info),
		         dataType: "JSON",
		         type: "POST",
		         contentType: "application/json;charset=utf-8",
		         success: function(response) {
			     	alert(response.msg);
		         }
		    })
        }
		
	}
	
	function managerFreeze() {
		var user = document.getElementById("managername").value;
		
		if(user.length == 0 || re.test(user)) {
			alert("请输入新管理员账号！！");
			return ;
		}
		
		if (/^[\u4e00-\u9fa5]+$/.test($('#managername').val())) {
			alert("账号不能输入汉字！");
			return ;
		}
		
		if(window.confirm('确定冻结此管理员账号吗？')) {
			var info = {admin:document.getElementById("managername").value};
			$.ajax({
		         url: "/freezeManager",
		         data: JSON.stringify(info),
		         dataType: "JSON",
		         type: "POST",
		         contentType: "application/json;charset=utf-8",
		         success: function(response) {
			     	alert(response.msg);
		         }
		    })
        }
		
	}
	
	function addAdmin() {
		var user = document.getElementById("adminname").value;
		var pwd = document.getElementById("adminpwd").value;
		
		if(user.length == 0 || re.test(user)) {
			alert("请输入新管理员账号！！");
			return ;
		}
		
		if (/^[\u4e00-\u9fa5]+$/.test($('#adminname').val())) {
			alert("账号不能输入汉字！");
			return ;
		}
		
		if(pwd.length == 0 || re.test(pwd)) {
			alert("请填写密码！！");
			return ;
		}
		if (/^[\u4e00-\u9fa5]+$/.test($('#adminpwd').val())) {
			alert("密码不能输入汉字！");
			return ;
		}
		if(pwd.length < 8 || pwd.length > 16) {
			alert("密码长度需大于等于8，小于等于16");
			return ;
		}
		
		if(window.confirm('确定添加此管理员账号吗？')) {
			var info = {admin:document.getElementById("adminname").value,
						pwd:document.getElementById("adminpwd").value};
			$.ajax({
		         url: "/addManager",
		         data: JSON.stringify(info),
		         dataType: "JSON",
		         type: "POST",
		         contentType: "application/json;charset=utf-8",
		         success: function(response) {
			     	alert(response.msg);
		         }
		    })
        }
		
	}
	
	function freeze() {
		var user = document.getElementById("username").value;
		
		if(user.length == 0 || re.test(user)) {
			alert("请填写用户名！！");
			return ;
		}
		
		if (/^[\u4e00-\u9fa5]+$/.test($('#username').val())) {
			alert("账号不能输入汉字！");
			return ;
		}
		
		if(window.confirm('确定冻结此账号吗？')) {
			var info = {username:document.getElementById("username").value};
			$.ajax({
		         url: "/freezeMember",
		         data: JSON.stringify(info),
		         dataType: "JSON",
		         type: "POST",
		         contentType: "application/json;charset=utf-8",
		         success: function(response) {
			     	alert(response.msg);
		         }
		    })
        }
	}
	
	function removeFreeze() {
		var user = document.getElementById("username1").value;
		
		if(user.length == 0 || re.test(user)) {
			alert("请填写用户名！！");
			return ;
		}
		
		if (/^[\u4e00-\u9fa5]+$/.test($('#username1').val())) {
			alert("账户不能输入汉字！");
			return false;
		}
		
		if(window.confirm('确定将此账号解除冻结吗？')) {
			var info = {username:document.getElementById("username1").value};
			$.ajax({
		         url: "/removeMemberFreeze",
		         data: JSON.stringify(info),
		         dataType: "JSON",
		         type: "POST",
		         contentType: "application/json;charset=utf-8",
		         success: function(response) {
			     	alert(response.msg);
		         }
		    })
        }
	}
	
	function addShop() {
		var user = document.getElementById("username2").value;
		
		if(user.length == 0 || re.test(user)) {
			alert("请填写用户名！！");
			return ;
		}
		
		if (/^[\u4e00-\u9fa5]+$/.test($('#username2').val())) {
			alert("账户不能输入汉字！");
			return false;
		}
		
		if(window.confirm('确定给此账号注册店铺吗？')) {
			var info = {username:document.getElementById("username2").value};
			$.ajax({
		         url: "/addSeller",
		         data: JSON.stringify(info),
		         dataType: "JSON",
		         type: "POST",
		         contentType: "application/json;charset=utf-8",
		         success: function(response) {
			     	alert(response.msg);
		         }
		    })
        }
	}
	
	function delBType() {
		var type = document.getElementById("delSupertype").value;
		if(type == -1) {
			alert('请先选择要删除的商品总类别！！');	
			return ;
		}
		
		if(window.confirm('确定删除此商品类别吗？')) {
			var info = {supertype:document.getElementById("delSupertype").value};
			$.ajax({
		         url: "/delSupertype",
		         data: JSON.stringify(info),
		         dataType: "JSON",
		         type: "POST",
		         contentType: "application/json;charset=utf-8",
		         success: function(response) {
			     	alert(response.msg);
		         }
		    })
        }
		
	}
	
	function delSType() {
		var type = document.getElementById("delSubtype").value;
		
		if(type == -1) {
			alert('请先选择要删除的商品子类别！！');	
			return ;
		}
		
		if(window.confirm('确定删除此商品类别吗？')) {
			var info = {subtype:document.getElementById("delSubtype").value};
			$.ajax({
		         url: "/delSubtype",
		         data: JSON.stringify(info),
		         dataType: "JSON",
		         type: "POST",
		         contentType: "application/json;charset=utf-8",
		         success: function(response) {
			     	alert(response.msg);
		         }
		    })
        }
	}
	
	function addSmallType() {
		var type = document.getElementById("type").value;
		var newsmalltype = document.getElementById("newsmalltype").value;
		
		if(type == -1) {
			alert('请先选择该商品类别所属的父类别！！');	
			return ;
		}
		
		if(newsmalltype.length == 0 || re.test(newsmalltype)) {
			alert("请填写新类别的名称！！");
			return ;
		}
		
		if(window.confirm('确定添加此商品类别吗？')) {
			var info = {supertype:document.getElementById("type").value,
						subtype:document.getElementById("newsmalltype").value};
			$.ajax({
		         url: "/addSubtype",
		         data: JSON.stringify(info),
		         dataType: "JSON",
		         type: "POST",
		         contentType: "application/json;charset=utf-8",
		         success: function(response) {
			     	alert(response.msg);
		         }
		    })
        }
		
	}
	
	function alterSmallType() {
		var smalltype = document.getElementById("smalltype").value;
		var altersmalltype = document.getElementById("altersmalltype").value;
		
		if(smalltype == -1) {
			alert('请选择要修改的商品类别！！');	
			return ;
		}
		
		if(altersmalltype.length == 0 || re.test(altersmalltype)) {
			alert("请填写新的子商品类别名称！！");
			return ;
		}
		
		if(window.confirm('确定修改此商品类别的名称吗？')) {
			var info = {subtype:document.getElementById("smalltype").value,
						subtypename:document.getElementById("altersmalltype").value};
			$.ajax({
		         url: "/alterSubtypeName",
		         data: JSON.stringify(info),
		         dataType: "JSON",
		         type: "POST",
		         contentType: "application/json;charset=utf-8",
		         success: function(response) {
			     	alert(response.msg);
		         }
		    })
	    }
	}
	
	function alterBigType() {
		var selectbigtype1 = document.getElementById("selectbigtype1").value;
		var alterbigtype = document.getElementById("alterbigtype").value;
		
		if(selectbigtype1 == -1) {
			alert('请选择要修改的商品类别！！');	
			return ;
		}
		
		if(alterbigtype.length == 0 || re.test(alterbigtype)) {
			alert("请填写新的总商品类别名称！！");
			return ;
		}
		
		if(window.confirm('确定修改此商品类别的名称吗？')) {
			var info = {supertype:document.getElementById("selectbigtype1").value,
						supertypename:document.getElementById("alterbigtype").value};
			$.ajax({
		         url: "/alterSupertypeName",
		         data: JSON.stringify(info),
		         dataType: "JSON",
		         type: "POST",
		         contentType: "application/json;charset=utf-8",
		         success: function(response) {
			     	alert(response.msg);
		         }
		    })
	    }
	}
	
	function addBigType() {
		var bigtype = document.getElementById("bigtype").value;
		
		if(bigtype.length == 0 || re.test(bigtype)) {
			alert("请输入要添加的总类别名！！");
			return ;
		}
		
		if(window.confirm('确定添加此总分类吗？')) {
			var info = {supertype:document.getElementById("bigtype").value};
			$.ajax({
		         url: "/addSupertype",
		         data: JSON.stringify(info),
		         dataType: "JSON",
		         type: "POST",
		         contentType: "application/json;charset=utf-8",
		         success: function(response) {
			     	alert(response.msg);
		         }
		    })
        }
	}
	
</script>
<!-- //验证输入的信息是否合法 -->
</html>