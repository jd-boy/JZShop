<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.shop.model.Goods"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品管理-JZ商城</title>
<link rel="stylesheet" href="css/mr-01.css" type="text/css">

<script src="js/jquery-1.12.4.js" type="text/javascript"></script>
</head>
<body>
	<jsp:include page="index-loginCon.jsp" />
	<%@ include file="header-store.jsp"%>
	
	<div id="mr-mainbody" class="container mr-mainbody">
		<div class="row">
			<!-- 页面主体内容 -->
			<div id="mr-content" class="mr-content col-xs-12">
				<div id="mrshop" class="mrshop common-home">
					<div class="container_oc">
						<div style="margin-left: -150px;margin-right: -15px;">
							<div id="content_oc" class="col-sm-12" style="width:1400px;position:relative;">
								<h1>商品管理</h1>
								
								<form action="/insertGoods" method="post" id="myform" onsubmit="return save();">
									<div class="table-responsive cart-info" style="width:1400px;">
										<table class="table table-bordered">
											<thead>
												<tr>
													<td style="text-align:center;">商品名称</td>
													<td style="text-align:center;">商品简介</td>
													<td style="text-align:center;">商品单价</td>
													<td style="text-align:center;">商品折后价</td>
													<td style="text-align:center;">商品库存</td>
													<td style="text-align:center;">保存</td>
												</tr>
											</thead>
											<tbody>
												
												<!-- 添加一条商品信息 -->
												
												<tr>
													<td style="text-align:center;">
														<textarea style="resize:none;border:none;width:200px;height:100px;"
														id="goodsname" name="goodsname" maxlength="40" placeholder="请输入商品名"></textarea>
													</td>
													
													<td>
														<textarea id="introduce" style="resize:none;border:none;width:600px;height:200px;"
														name="introduce" maxlength="200" placeholder="请输入商品简介"></textarea>
													</td>
													
													<td style="text-align:center;">
														<textarea style="resize:none;border:none;width:90px;height:55px;"
															id="price" name="price" maxlength="11" placeholder="0.00"></textarea>元
													</td>
													
													<td style="text-align:center;position:relative;">
														<label style="position:absolute;top:50px;left:0px;color:red">不为打折商品，请勿填写</label>
														<textarea style="resize:none;border:none;width:90px;height:55px;"
															id="nowprice" name="nowprice" maxlength="11" placeholder="(选填)"></textarea>元
													</td>
													
													<td style="text-align:center;">
														<textarea style="resize:none;border:none;width:90px;height:55px;"
															id="num" name="num" maxlength="9" placeholder="0"></textarea>件
													</td>
													<td style="text-align:center;">
														<input type="submit" value="添加" style="color:red">
													</td>
												</tr>
												<!-- 添加商品信息 -->
												
											</tbody>
										</table>
										<br/><br/><br/><br/><br/><br/>
									</div>
									<div style="position:absolute;left:310px;top:360px;">
										<label id="type" style="font-size:25px">商品类型</label>
									</div>
									<div style="position:absolute;left:420px;top:360px;">
										<select style="width:150px;height:40px;font-size:15px" id="typename" name="typename">
											<option value="-1">--请选择--</option>
											<%
											Map<Integer, String> subtypemap = (Map<Integer, String>)request.getAttribute("subtypemap");
												for(Map.Entry<Integer,String> en : subtypemap.entrySet()) {
											%>
												<option value="<%=en.getKey()%>"><%=en.getValue()%></option>
											<% } %>
										</select>
									</div>
									<input id="img" name="img" value="" type="hidden" />
								</form>
								<div style="position:absolute;left:710px;top:360px;">
									<label style="font-size:25px">商品图片</label>
								</div>
								<div style="position:absolute;left:820px;top:370px;">
									<form method="post" enctype="multipart/form-data"
											name="uploadForm" id="uploadForm">
										<input name="image" type="file" accept="image/gif,image/jpeg,image/png"/>
										<button type="button" id="upload" name="upload" onclick="upimg()">上传</button>
									</form>
								</div>
							</div>
						</div>

						<br />
					</div>
				</div>
			</div>
			<!-- //页面主体内容 -->
		</div>
	</div>
	
	<%@ include file="common-footer.jsp"%>
	
</body>

<script type="text/javascript">
	function upimg() {
		
		var formData = new FormData($("#uploadForm")[0]);
		$.ajax({
			url: '/uploadImage',
			type: 'post', 
			dataType : 'json',
			contentType: false,//必不可少
			processData: false,//必不可少
			data: formData,
			success: function (res) {
				if(res.code == 0) {
					document.getElementById("img").value = res.msg;
					alert('图片上传成功！！');
				} else {
					alert(res.msg);
				}
                
            }
		})
	}
	
	function trimStr(str) {
		return str.replace(/(^\s*)|(\s*$)/g,"");
	}

	function save() {
		var regu = "^[ ]+$";
		var re = new RegExp(regu);
		
		var goodsname = trimStr(document.getElementById("goodsname").value);
		var introduce = trimStr(document.getElementById("introduce").value);
		var price = trimStr(document.getElementById("price").value);
		var nowprice = trimStr(document.getElementById("nowprice").value);
		var num = trimStr(document.getElementById("num").value);
		var img = document.getElementById("img").value;
		var typename = document.getElementById("typename").value;
		
		if(img.length == 0 || re.test(img)) {
			alert("请上传商品图片！！");
			return false;
		}
		if(goodsname.length == 0 || re.test(goodsname)) {
			alert("请填写商品名！！");
			return false;
		}
		if(introduce.length == 0 || re.test(introduce)) {
			alert("请填写商品简介！！");
			return false;
		}
		if(price.length == 0 || re.test(price)) {
			alert("请填写商品价格！！");
			return false;
		}
		if(re.test(nowprice)) {
			alert("商品折后价不能填写空白字符！！");
			return false;
		}
		if(num.length == 0 || re.test(num)) {
			alert("请填写商品库存！！");
			return false;
		}
		if(typename == -1) {
			alert("请选择商品类别！！");
			return false;
		}
		
		
		if(isNaN(price) || isNaN(nowprice) || isNaN(num)){  
		    alert("价格和库存只能为数字！！");
		    return false;
		}
		if(price < 0) {
			alert("商品单价需大于等于0！！");
			return false;
		}
		if(price.length-price.indexOf(".") > 3) {
			alert("商品单价只能保留两位小数！！");
			return false;
		}
		if((nowprice.length > 0 || !re.test(nowprice))&& nowprice < 0) {
			alert("折后价需大于等于0！！");
			return false;
		}
		if(nowprice.length-nowprice.indexOf(".") > 3) {
			alert("折后价只能保留两位小数！！");
			return false;
		}
		if(num < 0) {
			alert("库存需大于等于0！！");
			return false;
		}
		if(num.indexOf(".") != -1) {
			alert("库存只能是整数");
			return false;
		}
		if(nowprice.length == 0) {
			document.getElementById("nowprice").value = -1;
		}
		
		if(window.confirm('确定添加该商品吗？')){
			return true;
        } else {
            return false;
        }
	}
	
</script>
</html>