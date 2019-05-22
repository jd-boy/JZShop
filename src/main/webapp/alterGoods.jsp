<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.shop.model.Goods"%>
<%@ page import="java.util.ArrayList"%>
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
							<div id="content_oc" class="col-sm-12" style="width:1400px;">
								<h1>商品管理</h1>
								<%
									Goods goods = (Goods) request.getAttribute("goods");
								%>
								<form action="/saveGoodsInfor?id=<%=goods.getID()%>" method="post" id="myform">
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
												
												<!-- 显示一条商品信息 -->
												
												<tr>
													<td style="text-align:center;">
														<textarea style="resize:none;border:none;width:200px;height:100px;"
														id="goodsname" name="goodsName" maxlength="40"
														placeholder="请输入商品名称"><%=goods.getGoodsName()%></textarea>
													</td>
													
													<td>
														<textarea id="introduce" style="resize:none;border:none;width:600px;
														height:200px;" name="introduce" maxlength="200" placeholder="请输入商品简介"
														><%=goods.getIntroduce()%></textarea>
													</td>
													
													<td style="text-align:center;">
														<textarea style="resize:none;border:none;width:90px;height:55px;"
															id="price" name="price" maxlength="11" placeholder="请输入商品单价"
															><%=goods.getPrice()%></textarea>元
													</td>
													
													<td style="text-align:center;position:relative;">
														<label style="position:absolute;top:50px;left:0px;color:red">不为打折商品，请勿填写</label>
														<% if(goods.getSale() == 1) { %>
															<textarea style="resize:none;border:none;width:90px;height:55px;"
																id="nowprice" name="nowPrice" maxlength="11" placeholder="此项选填"
																><%=goods.getNowPrice()%></textarea>元
														<% } else { %>
															<textarea style="resize:none;border:none;width:90px;height:55px;"
																id="nowprice" name="nowPrice" maxlength="11" placeholder="此项选填"
																></textarea>元
														<% } %>
													</td>
													
													<td style="text-align:center;">
														<textarea style="resize:none;border:none;width:90px;height:55px;"
															id="num" name="num" maxlength="9" placeholder="请输入商品库存"
															><%=goods.getNum()%></textarea>件
													</td>
													<td style="text-align:center;">
														<input type="button" value="保存" style="color:red" onclick="save();"/>
													</td>
												</tr>
												<!-- 显示商品信息 -->
												
											</tbody>
										</table>
									</div>
									<div style="position:absolute;left:360px;top:330px;">
										<div >
											<label id="type" style="font-size:20px">商品类型</label>
										</div>
										<div >
											<select style="width:110px;height:35px;font-size:15px;text-align-last: center;"
													id="typename" name="typeID">
												<%
													Map<Integer, String> subtypemap = (Map<Integer, String>)request.getAttribute("subtypemap");
														for(Map.Entry<Integer,String> en : subtypemap.entrySet()) {
												%>
													<option <%if(en.getKey()==goods.getTypeID()){%> selected="selected" <%}%>
														value="<%=en.getKey()%>"><%=en.getValue()%></option>
												<%
													}
												%>
											</select>
										</div>
										<input id="img" name="picture" value="<%=goods.getPicture()%>" type="hidden" />
									</div>
								</form>
								<br/>
								<div style="position:absolute;left:800px;top:330px;">
									<div>
										<label id="img" style="font-size:20px">商品图片</label>
									</div>
									<div>
										<form action="/uploadImage" method="post" enctype="multipart/form-data"
												name="uploadForm" id="uploadForm">
											<input name="image" type="file" accept="image/gif,image/jpeg,image/png"/>
											<input type="button" name="upload" value="上传" onclick="upimg()" />
										</form>
									</div>
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
	<textarea style="display:none;" id="inf"><%=goods.getIntroduce()%></textarea>
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
		var flag = 0;
		var price = trimStr(document.getElementById("price").value);
		var nowprice = trimStr(document.getElementById("nowprice").value);
		var num = trimStr(document.getElementById("num").value);
		var goodsname = trimStr(document.getElementById("goodsname").value);
		var introduce = trimStr(document.getElementById("introduce").value);
		var inf = trimStr(document.getElementById("inf").value);
		var typename = trimStr(document.getElementById("typename").value);
		var img = document.getElementById("img").value;
		
		if(goodsname.length == 0 || re.test(goodsname)) {
			alert("请填写商品名称！！");
			return false;
		}
		if(introduce.length == 0 || re.test(introduce)) {
			alert("请填写商品简介！！");
			return false;
		}
		if(price.length == 0 || re.test(price)) {
			alert("请填写商品单价！！");
			return false;
		}
		if(re.test(nowprice)) {
			alert("商品折后价不能为空白字符！！");
			return false;
		}
		if(num.length == 0 || re.test(num)) {
			alert("请填写商品库存！！");
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
		if((nowprice.length > 0 || !re.test(nowprice)) && nowprice < 0) {
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
			alert("库存只能是正整数");
			return false;
		}
		
		<% if(goods.getSale() == 1) { %>
				if(nowprice.length == 0 || nowprice != '<%=goods.getNowPrice()%>') {
					flag = 1;
				}
		<% } else { %>
				if(nowprice.length > 0) {
					flag = 1;
				}
		<% } %>
		if(price != '<%=goods.getPrice()%>') {
			flag = 1;
		}
		if(nowprice != '<%=goods.getNowPrice()%>') {
			flag = 1;
		}
		if(num != '<%=goods.getNum()%>') {
			flag = 1;
		}
		if(goodsname != '<%=goods.getGoodsName()%>') {
			flag = 1;
		}
		if(introduce != inf) {
			flag = 1;
		}
		if(typename != '<%=goods.getTypeID()%>') {
			flag = 1;
		}
		if(img != '<%=goods.getPicture()%>') {
			flag = 1;
		}
		
		if(flag == 0) {
			alert('商品信息未进行修改！！');
			return false;
		}
		
		if(nowprice.length == 0) {
			document.getElementById("nowprice").value = -1;
		}
		
		if(window.confirm('确定要保存修改吗？')) {
			$.ajax({
				url: "/saveGoodsInfor?id=<%=goods.getID()%>",
				type: 'post', 
				data: $("#myform").serialize(),
				dataType: 'json',
				success: function (res) {
					if(res.code == 0) {
						alert(res.msg);
						window.location.href="/manageGoods";
					} else {
						alert(res.msg);
					}	            
		        }
			})
         }
	}
</script>
</html>