<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="com.shop.model.Cart"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<title>我的购物车-JZ商城</title>
<style>
	input::-webkit-outer-spin-button,
	input::-webkit-inner-spin-button {
		-webkit-appearance: none !important;
		margin: 0; 
	}
	.flex-box {
		display: flex;
		align-content: center;
	}
</style>
<link rel="stylesheet" href="css/mr-01.css" type="text/css">

<script src="js/jquery-1.12.4.js" type="text/javascript"></script>

</head>

<body>
	<jsp:include page="index-loginCon.jsp" />
	<jsp:include page="search-header.jsp" />
	<!-- 网站头部 -->
	<%@ include file="common-header.jsp"%>
	<!-- //网站头部 -->
	<div id="mr-mainbody" class="container mr-mainbody">
		<div class="row">
			<!-- 页面主体内容 -->
			<div id="mr-content" class="mr-content col-xs-12">
				<div id="mrshop" class="mrshop common-home">
					<div class="container_oc">
						<div class="row">
							<div id="content_oc" class="col-sm-12">
								<h1>我的购物车</h1>
								<!-- 显示购物车中的商品 -->
								<div class="table-responsive cart-info">
									<table class="table table-bordered">
										<thead>
											<tr>
												<td style="text-align:center;">商品图片</td>
												<td style="text-align:center;">商品名称</td>
												<td style="text-align:center;">数量</td>
												<td style="text-align:center;">单价</td>
												<td style="text-align:center;">总计</td>
												<td style="text-align:center;">管理</td>
											</tr>
										</thead>
										<tbody>
										<!-- 遍历购物车中的商品并显示 -->
											
											<!-- 显示一条商品信息 -->
											<%
												ArrayList<Cart> list = (ArrayList<Cart>) request.getAttribute("cartList");
												BigDecimal sumPrice = new BigDecimal("0.00");
												BigDecimal price = new BigDecimal("0.00");
												for(Cart cart : list) {
													price = cart.getPrice().multiply(new BigDecimal(String.valueOf(cart.getNum())));
													sumPrice = sumPrice.add(price);
													
											%>
											<tr>
												<td style="text-align:center;" width="20%">
													<a href="/goodsDetail?ID=<%=cart.getGoodsId()%>&page=1">
													<img width="80px" src="/image?id=<%=cart.getGoodsId()%>"> </a>
												</td>
												<td style="text-align:center;">
													<a href="/goodsDetail?ID=<%=cart.getGoodsId()%>&page=1"> <%=cart.getGoodsName()%></a>
												</td>
												<td style="vertical-align:middle;">
													<div class="flex-box" style="text-align:center;">
									                   	<input type="button" style="background:url(images/less.png) center center no-repeat;
									                    	width:20px;height:40px;border-style:none;display:inline;" 
									                    	onclick="less(<%=cart.getGoodsId()%>)" />
									                    	
									                    <input class="numInput" type="number" value="<%=cart.getNum()%>" size="2" id="<%=cart.getGoodsId()%>" 
															style="border-style:none;width:100px; height:40px;text-align:center;display:inline;" 
															min="1" />
															
									                    <input type="button" style="background:url(images/plus.png) center center no-repeat;
									                    	width:20px;height:40px;border-style:none;display:inline;"
									                    	onclick="plus(<%=cart.getGoodsId()%>)" />
									                </div>
												</td>
												<td style="text-align:center;"><%=cart.getPrice()%>元</td>
												<td style="text-align:center;"><%=price.toString()%>元</td>
												<td style="text-align:center;">
													<a href="/cartDel?id=<%=cart.getGoodsId()%>">删除</a>
												</td>
											</tr>
											<!-- 显示一条商品信息 -->
											
											<% } %>
											<!-- //遍历购物车中的商品并显示 -->
										</tbody>
									</table>
								</div>
								<!-- //显示购物车中的商品 -->
								<!-- 显示总计金额  -->
								<div class="row cart-total">
									<div class="col-sm-4 col-sm-offset-8">
										<table class="table table-bordered">
											<tbody>
												<tr >
												<span>
													<strong>总计:</strong>
													<p><%=sumPrice.toString()%>元</p>
												</span>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								<!-- //显示总计金额  -->
							</div>
						</div>

						<!-- 填写物流信息 -->
						<div class="row">
							<div id="content_oc" class="col-sm-12">
								<h1>物流信息</h1>
								<!-- 填写物流信息的表单 -->
								<form action="/cart_order" method="post" id="myform">
									<div class="table-responsive cart-info">
										<table class="table table-bordered">
											<tbody>
												<tr>
													<td class="text-right" width="20%">收货人姓名：</td>
													<td class="text-left quantity">
														<div class="input-group btn-block" style="max-width: 400px;">
															<input type="text" id="recevieName" name="recevieName" size="10" class="form-control">
														</div>
													</td>
												</tr>
												<tr>
													<td class="text-right">收货人手机：</td>
													<td class="text-left quantity">
														<div class="input-group btn-block" style="max-width: 400px;">
															<input type="text" id="tel" name="tel" size="10" class="form-control">
														</div>
													</td>
												</tr>
												<tr>
													<td class="text-right">收货人地址：</td>
													<td class="text-left quantity">
														<div class="input-group btn-block" style="max-width: 400px;">
															<input type="text" id="address" name="address" size="1" class="form-control">
														</div>
													</td>
												</tr>
												<tr>
													<td class="text-right">备注：</td>
													<td class="text-left quantity">
														<div class="input-group btn-block" style="max-width: 400px;">
															<input type="text" id="bz" name="bz" size="1" class="form-control">
														</div>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</form>
								<!-- //填写物流信息的表单 -->
							</div>
						</div>
						<!-- //填写物流信息 -->
						<br />
						<!-- 显示支付方式 -->
						<div class="row">
							<div id="content_oc" class="col-sm-12">
								<h1>支付方式</h1>
								<div class="table-responsive cart-info">
									<table class="table table-bordered">
										<tbody>
											<tr>
												<td class="text-left"><img src="images/zhifubao.png" /></td>
											</tr>
										</tbody>
									</table>
								</div>
								<br /> <br />
								<div class="buttons">
									<div class="pull-left">
										<a href="/index" class="btn btn-primary btn-default">继续购物</a>
									</div>
									<div class="pull-left">
										<a href="javascript:clearCart()" class="btn btn-primary btn-default">清空购物车</a>
									</div>
									<div class="pull-right">
										<a href="javascript:submitOrder();" class="tigger btn btn-primary btn-primary">提交订单</a>
									</div>
								</div>
							</div>
						</div>
						<!-- //显示支付方式 -->
					</div>
				</div>
			</div>
			<!-- //页面主体内容 -->
		</div>
	</div>
	<!-- type="hidden" -->
	<form id="orderIdForm" name="orderIdForm" action="/confirmOrder" method="post">
		<input type="hidden" id="orderId" name="orderId" value="" />
	</form>
	<!-- 版权栏 -->
	<%@ include file="common-footer.jsp"%>
	<!-- //版权栏 -->

	<script type="text/javascript">
	
		function submitOrder() {
			
			<% if(list.size() == 0) { %>
				alert('请先添加商品到购物车！！');
				return ;
			<% } %>
			
			//验证收货人姓名
			if ($('#recevieName').val() === "") {
				alert('收货人姓名不能为空！');
				return;
			}
			//验证收货人手机
			if ($('#tel').val() === "") {
				alert('收货人手机不能为空！');
				return;
			}
			//验证手机号是否合法
			if (isNaN($('#tel').val())) {
				alert("手机号请输入数字");
				return;
			}
			//验证收货人地址
			if ($('#address').val() === "") {
				alert('收货人地址不能为空！');
				return;
			}
			var info = {recevieName:document.getElementById('recevieName').value,
						tel:document.getElementById('tel').value,
						address:document.getElementById('address').value,
						bz:document.getElementById('bz').value};
			$.ajax({
		         url: "/createOrder",
		         data: JSON.stringify(info),
		         dataType: 'json',
		         type: "POST",
		         contentType: "application/json;charset=utf-8",
		         success: function(response) {
		         //前端调用成功后，可以处理后端传回的json格式数据。
			         if(response.code == 1) {
			             alert(response.msg);
			         } else {
			        	 document.getElementById('orderId').value = response.msg;
			        	 document.getElementById("orderIdForm").submit();
		         	}
		     	}
		    })
		}
		
		function clearCart() {
			
			<% if(list.size() == 0) { %>
				alert('购物车里空空如也！！');
				return ;
			<% } %>
			
			if(window.confirm('确定删除购物车中的所有商品吗？')) {
				$.ajax({
			         url: "/cart_clear",
			         data: {},
			         dataType: 'JSON',
			         type: "POST",
			         contentType: "application/json;charset=utf-8",
			         success: function(response){
			        	 if(response.code == 0) {
				             alert(response.msg);
				        	 window.location.href="/cart_see";
			        	 } else {
			        		 alert(response.msg);
			        	 }
			     	}
			    })
				return true;
			} else {
				return false;
			}
		}
	
		function less(input) {
			var num = parseInt(document.getElementById(input).value);
			num = num-1;
			if(num <= 0) {
				alert('数量不能小于等于0！');
				return;
			}
			window.location.href="/cartLess?id="+input+"&num="+num;
		}
		
		function plus(input) {
			var num = parseInt(document.getElementById(input).value);
			num = num+1;
			window.location.href="/cartLess?id="+input+"&num="+num;
		}
		
	</script>
</body>
</html>

