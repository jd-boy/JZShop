<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>注册-JZ商城</title>
<link rel="stylesheet" href="css/mr-01.css" type="text/css">
<script src="js/jquery-1.12.4.js" type="text/javascript"></script>
</head>

<body>
	<!-- 主体内容 -->
	<div class="mainqq">
		<div class="imagedf">
			<a href="/index" title="点击返回首页"><img src="images/jzlogo.png"></a>
		</div>
		<div class="infooo">
			<div style="position:absolute;height:70px;width:100%;
					background-color:#F2F2F2;">
				<h1 style="position:absolute;top:25%;left:38%;">修改用户信息</h1>
			</div>
			
				<div style="position:absolute;height:70px;width:100%;top:110px;">
					<div style="position:absolute;width:50%;top:5%;">
						<div style="width:25%;">
							<label style="font-size:20px;float:right;">密码：</label>
						</div>
						<div style="position:absolute;top:0;left:25%">
							<input type="password" name="pwd" id="pwd" value="" autocomplete="off"
								size="38" maxlength="90">
						</div>
					</div>
					
					<div style="position:absolute;width:50%;top:5%;left:50%">
						<div style="width:25%;">
							<label style="font-size:20px;float:right;">确认密码：</label>
						</div>
						<div style="position:absolute;top:0;left:25%">
							<input type="password" name="pwd2" id="pwd2" value="" autocomplete="off"
								size="38" maxlength="90">
						</div>
					</div>
				</div>

				<div style="position:absolute;height:70px;width:100%;top:180px;">
					<div style="position:absolute;width:50%;top:5%;">
						<div style="width:25%;">
							<label style="font-size:20px;float:right;">邮箱：</label>
						</div>
						<div style="position:absolute;top:0;left:25%">
							<input type="email" name="email" id="email" value="<%=request.getAttribute("email")%>"
							autocomplete="off" size="38" maxlength="90">
						</div>
					</div>
					
					<div style="position:absolute;width:50%;top:5%;left:50%">
						<div style="width:25%;">
							<label style="font-size:20px;float:right;">联系电话：</label>
						</div>
						<div style="position:absolute;top:0;left:25%">
							<input type="text" name="tel" id="tel" value="<%=request.getAttribute("tel")%>"
								autocomplete="off" size="38" maxlength="90">
						</div>
					</div>
				</div>
				
				<div style="position:absolute;height:70px;width:100%;top:250px;">
					<div style="position:absolute;width:50%;top:5%;">
						<div style="width:25%;">
							<label style="font-size:20px;float:right;">邮编：</label>
						</div>
						<div style="position:absolute;top:0;left:25%">
							<input type="text" name="postal" id="postal" value="<%=request.getAttribute("postal")%>"
								autocomplete="off" size="38" maxlength="90">
						</div>
					</div>
					
					<div style="position:absolute;width:50%;top:5%;left:50%">
						<div style="width:25%;">
							<label style="font-size:20px;float:right;">常用地址：</label>
						</div>
						<div style="position:absolute;top:0;left:25%">
							<input type="text" name="address" id="address" value="<%=request.getAttribute("address")%>"
								autocomplete="off" size="38" maxlength="90" placeholder="请输入常用地址">
						</div>
					</div>
				</div>
				
				<div style="position:absolute;height:70px;width:100%;top:320px;">
					<div style="position:absolute;width:50%;top:5%;">
						<div style="width:25%;">
							<label style="font-size:20px;float:right;">验证码：</label>
						</div>
						<div style="position:absolute;top:0;left:25%">
							<input type="text" name="check" id="check" value=""
								size="38" maxlength="90" required="required" 
								placeholder="请输入验证码" >
						</div>
					</div>
			
					<div style="position:absolute;width:50%;top:5%;left:50%">
						<div style="position:absolute;top:0;left:5%">
							<input type="button" class="btnca" id="code" value="获取验证码"  onclick="codeButton();">
							<input type="text" id="rcheck" style="display:none;">
						</div>
					</div>
				</div>
				
				<div style="position:absolute;width:50%;top:400px;left:12%">
					<input type="submit" class="btn-primary login" value="保存修改" onclick="regis()">
				</div>
			
		</div>
	</div>
	<!-- //主体内容 -->
	
</body>
<!-- 验证输入的注册信息是否合法 -->
<script src="js/jquery.1.3.2.js" type="text/javascript"></script>
<script>

	function codeButton() {
		
		/* $.post("/sendCheckCode", function(data){
			document.getElementById("rcheck").value = data;
		}); */
		var url = "/sendCheckCode";
		var request = new XMLHttpRequest();
		request.open("POST",url);
		request.send(null);
		request.onload = function() {
            if(request.status == 200) {
            	document.getElementById("rcheck").value = request.responseText;
            }
		}
		
		var code = $("#code");
		code.attr("disabled","disabled");
		setTimeout(function(){ code.css("opacity","0.8");},1000)
		var time = 60;
		var set=setInterval(function(){
			code.val("("+--time+")秒后重新获取");	    }, 1000);
		setTimeout(function(){
			code.attr("disabled",false).val("重新获取验证码");
			clearInterval(set);	    }, 60000);
		
	}
	
	function regis() {
		var regu = "^[ ]+$";
		var re = new RegExp(regu);
		
		/* ----------- 验证输入的联系电话是否合法 --------------------- */
		if (isNaN($('#tel').val())) {
			alert("联系电话请输入数字！！");
			return ;
		}
		/* ----------- 验证两次输入的密码是否一致 --------------------- */
		var pwd = document.getElementById("pwd").value;
		var pwd2 = document.getElementById("pwd2").value;
		
		if (pwd !== pwd2) {
			alert('密码前后不一致！！');
			return ;
		}
		
		if(re.test(document.getElementById("address").value)) {
			alert('常用地址不能为空白字符');
			return ;
		}
		
		var check = document.getElementById("check").value;
		if(check.length == 0) {
			alert('请输入验证码！！')
		}
		
		var rcheck = document.getElementById("rcheck").value;
		
		if(rcheck != check) {
			alert('验证码错误！！');
			return ;
		}
		
		if(window.confirm('确定修改信息吗？')){
			var info = {pwd:document.getElementById("pwd").value,
						tel:document.getElementById("tel").value,
						postal:document.getElementById('postal').value,
						email:document.getElementById('email').value,
						address:document.getElementById('address').value};
			
			$.ajax({
		         url: "/saveMember",
		         data: JSON.stringify(info),
		         dataType: "JSON",
		         type: "POST",
		         contentType: "application/json;charset=utf-8",
		         success: function(response) {
		        	 //alert(response.msg);
		        	 alert('修改个人信息成功');
		     	 }
		    })
        }
	}
</script>
<!-- //验证输入的信息是否合法 -->
</html>