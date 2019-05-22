<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.shop.model.OrderDetail"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>待发货订单-JZ商城</title>
<link rel="stylesheet" href="css/mr-01.css" type="text/css">
<link rel="stylesheet" href="css/pagination.css" type="text/css">
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
						<div class="row">
							<div id="content_oc" class="col-sm-12">
								<h1>待发货订单</h1>
								<!-- 显示全部订单 -->
								<div class="table-responsive cart-info">
									<table class="table table-bordered">
										<thead>
											<tr>
												<td style="text-align:center;">订单创建时间</td>
												<td style="text-align:center;">宝贝</td>
												<td style="text-align:center;">单价</td>
												<td style="text-align:center;">数量</td>
												<td style="text-align:center;">实付款</td>
												<td style="text-align:center;">状态</td>
											</tr>
										</thead>
										<tbody>
										<!-- 遍历全部订单并显示 -->
											
											<!-- 显示一条订单信息 -->
											<%
												Map<String, String> map = (Map<String, String>)request.getAttribute("map");
												ArrayList<OrderDetail> list = (ArrayList<OrderDetail>) request.getAttribute("orderDetailList");
												
												for(OrderDetail orderDetail : list) {
													BigDecimal price = orderDetail.getPrice().multiply(new BigDecimal(String.valueOf(orderDetail.getNumber())));
											%>
											<tr>
												<td style="text-align:center;"><%=map.get(orderDetail.getOrderID())%></td>
												<td style="text-align:center;" width="20%">
													<a href="/goodsDetail?ID=<%=orderDetail.getGoodsID()%>&page=1">
														<img width="80px" src="/image?id=<%=orderDetail.getGoodsID()%>">
													</a>
													<br/>
													<a href="/goodsDetail?ID=<%=orderDetail.getGoodsID()%>&page=1"> <%=orderDetail.getGoodsName()%></a>
												</td>
												<td style="text-align:center;"><%=orderDetail.getPrice()%>元</td>
												<td style="text-align:center;"><%=orderDetail.getNumber()%>件</td>
												<td style="text-align:center;"><%=price.toString()%>元</td>
												<td style="text-align:center;">
													<label style="color:red;">等待确认收货</label>
												</td>
											</tr>
											<!-- 显示一条订单信息 -->
											
											<% } %>
											<!-- //遍历全部订单并显示 -->
										</tbody>
									</table>
								</div>
								<!-- //显示全部订单 -->
							</div>
						</div>

						<br />
					</div>
				</div>
			</div>
			<!-- //页面主体内容 -->
		</div>
	</div>
	<form id="orderId_form" action="/confirmOrder" method="post">
		<input type="hidden" id="orderId" name="orderId">
	</form>
	<jsp:include page="pagination.jsp" />
	<%@ include file="common-footer.jsp"%>
</body>

<script type="text/javascript">
	
	var url = "/unfinishOrder?page=";

</script>
</html>