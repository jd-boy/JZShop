<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.shop.model.Order"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="com.shop.model.OrderDetail"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<title>订单确认-JZ商城</title>
<link rel="stylesheet" href="css/mr-01.css" type="text/css">

<script src="js/jquery-1.12.4.js" type="text/javascript"></script>
</head>

<body">
	<jsp:include page="index-loginCon.jsp" />
	<!-- 网站头部 -->
	<%@ include file="common-header.jsp"%>
	<!-- //网站头部 -->
	
	<%
		Order order = (Order) request.getAttribute("order");
	%>
	
	<div id="mr-mainbody" class="container mr-mainbody">
		<div class="row">
			<!-- 页面主体内容 -->
			<div id="mr-content" class="mr-content col-xs-12">
				<div id="mrshop" class="mrshop common-home">
					<div class="container_oc">
						<div class="row">
							<div id="content_oc" class="col-sm-12">
								<h1>订单确认</h1>
								<!-- 显示购物车中的商品 -->
								<div class="table-responsive cart-info">
									<form id="order_form" action="/alipay/goAlipay" method="post">
										<div style="position:relative;">
											<label style="color:red;font-size:15px;">订单号：</label>
											<input id="orderId" name="orderId" style="position:absolute;left:55px;font-size:15px;
												border:none;outline:medium;" value="<%=order.getOrderID()%>" />
										</div>
									</form>
									<table class="table table-bordered">
										<thead>
											<tr>
												<td style="text-align:center;">商品图片</td>
												<td style="text-align:center;">商品名称</td>
												<td style="text-align:center;">数量</td>
												<td style="text-align:center;">单价</td>
												<td style="text-align:center;">总计</td>
											</tr>
										</thead>
										<tbody>
										<!-- 遍历购物车中的商品并显示 -->
											
											<!-- 显示一条商品信息 -->
											<%
												for(OrderDetail orderDetail : (ArrayList<OrderDetail>) request.getAttribute("orderDetailList")) {
													BigDecimal price = orderDetail.getPrice().multiply(new BigDecimal(String.valueOf(orderDetail.getNumber())));
											%>
											<tr>
												<td style="text-align:center;" width="20%">
													<a href="/goodsDetail?ID=<%=orderDetail.getGoodsID()%>&page=1">
													<img width="80px" src="/image?id=<%=orderDetail.getGoodsID()%>"> </a>
												</td>
												<td style="text-align:center;">
													<a href="/goodsDetail?ID=<%=orderDetail.getGoodsID()%>&page=1"> <%=orderDetail.getGoodsName()%></a>
												</td>
												<td style="text-align:center;"><%=orderDetail.getNumber()%>件</td>
												<td style="text-align:center;"><%=orderDetail.getPrice()%>元</td>
												<td style="text-align:center;"><%=price.toString()%>元</td>
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
														<p><%=order.getSumPrice()%>元</p>
													</span>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								<!-- //显示总计金额  -->
							</div>
						</div>
						
						<!-- 显示物流信息 -->
						<div class="row">
							<div id="content_oc" class="col-sm-12">
								<h1>物流信息</h1>
								<div class="table-responsive cart-info">
									<table class="table table-bordered">
										<tbody>
											<tr>
												<td class="text-right" width="20%">收货人姓名：</td>
												<td class="text-left quantity">
													<div class="input-group btn-block" style="max-width: 400px;">
														<label style="font-size:15px;"><%=order.getRecevieName()%></label>
													</div>
												</td>
											</tr>
											<tr>
												<td class="text-right">收货人手机：</td>
												<td class="text-left quantity">
													<div class="input-group btn-block" style="max-width: 400px;">
														<label style="font-size:15px;"><%=order.getTel()%></label>
													</div>
												</td>
											</tr>
											<tr>
												<td class="text-right">收货人地址：</td>
												<td class="text-left quantity">
													<div class="input-group btn-block" style="max-width: 400px;">
														<label style="font-size:15px;"><%=order.getAddress()%></label>
													</div>
												</td>
											</tr>
											<tr>
												<td class="text-right">备注：</td>
												<td class="text-left quantity">
													<div class="input-group btn-block" style="max-width: 400px;">
														<label style="font-size:15px;"><%=order.getBz()%></label>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								<br/>
								<div class="pull-right">
									<button type="button" class="tigger btn btn-primary btn-primary"
										onclick="pay()">确定付款</button>
								</div>
								<div class="pull-right">
									<p>&nbsp;&nbsp;</p>
								</div>	
								<div class="pull-right">
									<button type="button" class="tigger btn btn-primary btn-primary"
										onclick="cancelOrder()">取消订单</button>
								</div>
							</div>
						</div>
						<!-- 显示物流信息 -->
						
					</div>
				</div>
			</div>
			<!-- //页面主体内容 -->
		</div>
	</div>
	<!-- 版权栏 -->
	<%@ include file="common-footer.jsp"%>
	<!-- //版权栏 -->

	<script type="text/javascript">
		
		function pay() {
			var info = {"orderId":'<%=order.getOrderID()%>'};
			$.ajax({
		         url: "/testOrder",
		         data: JSON.stringify(info),
		         dataType: 'json',
		         type: "POST",
		         contentType: "application/json;charset=utf-8",
		         success: function(response) {
			     	if(response.code == 1) {
			     		alert(response.msg);
			     	} else {
			     		document.getElementById("order_form").submit();
			     	}
		         }
		    })
		}
		
		function cancelOrder() {
			var info = {"orderId":'<%=order.getOrderID()%>'};
			$.ajax({
		         url: "/cancelOrder",
		         data: JSON.stringify(info),
		         dataType: 'json',
		         type: "POST",
		         contentType: "application/json;charset=utf-8",
		         success: function(response) {
			     	if(response.code == 1) {
			     		alert(response.msg);
			     	} else {
			     		alert(response.msg);
			     		window.location.href="/myOrder?page=1";
			     	}
		         }
		    })
		}
	</script>
</body>
</html>

