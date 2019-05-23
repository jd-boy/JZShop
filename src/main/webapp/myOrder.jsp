<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.shop.model.OrderDetail"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="java.util.HashMap"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的订单-JZ商城</title>
<link rel="stylesheet" href="css/mr-01.css" type="text/css">
<link rel="stylesheet" href="css/pagination.css" type="text/css">

<style type="text/css">
table thead tr {

</style>

<script src="js/jquery-1.12.4.js" type="text/javascript"></script>
</head>
<body>
	<jsp:include page="index-loginCon.jsp" />
	<%@ include file="common-header.jsp"%>
	
	<div id="mr-mainbody" class="container mr-mainbody">
		<div class="row">
			<!-- 页面主体内容 -->
			<div id="mr-content" class="mr-content col-xs-12">
				<div id="mrshop" class="mrshop common-home">
					<div class="container_oc">
						<div class="row">
							<div id="content_oc" class="col-sm-12">
								<h1>我的订单</h1>
								<!-- 显示全部订单 -->
								<div class="table-responsive cart-info">
									<table class="table table-bordered">
										<thead style="">
											<tr>
												<td style="text-align:center;">订单创建时间</td>
												<td style="text-align:center;">宝贝</td>
												<td style="text-align:center;">单价</td>
												<td style="text-align:center;">数量</td>
												<td style="text-align:center;">实付款</td>
												<td style="text-align:center;">评论</td>
												<td style="text-align:center;">保存评论</td>
												<td style="text-align:center;">状态</td>
											</tr>
										</thead>
										<tbody>
										<!-- 遍历全部订单并显示 -->
											
											<!-- 显示一条订单信息 -->
											<%
												HashMap<String, HashMap<Integer, String>> map = (HashMap<String, HashMap<Integer, String>>) request.getAttribute("commentMap");
												
												for(OrderDetail orderDetail : (ArrayList<OrderDetail>) request.getAttribute("orderDetailList")) {
													BigDecimal price = orderDetail.getPrice().multiply(new BigDecimal(String.valueOf(orderDetail.getNumber())));
											%>
											<tr>
												<td style="text-align:center;"><%=orderDetail.getTime()%></td>
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
												<td style="text-align:center;padding-left:30px;">
													<% if(map.containsKey(orderDetail.getOrderID()) && map.get(orderDetail.getOrderID()).containsKey(orderDetail.getGoodsID())) { %>
														<textarea readonly="readonly" style="resize:none;border:none;width:300px;height:100px;
																margin-left:0;padding:0;"name="comment" maxlength="500"><%=map.get(orderDetail.getOrderID()).get(orderDetail.getGoodsID())%></textarea>
													<% } else { %>
														<textarea id="comment<%=orderDetail.getGoodsID()%>" style="resize:none;border:none;width:300px;height:100px;
															margin-left:0;padding:0;"name="comment" maxlength="500" placeholder="您还没有评价该商品哦(评论后将无法修改)"
															></textarea>
													<% } %>
												</td>
												<td style="text-align:center;">
													<% if(orderDetail.getStatus() == 3) { %>
														<input type="button" value="评论" style="color:red" onclick="submit('<%=orderDetail.getGoodsID()%>','<%=orderDetail.getOrderID()%>');">
													<% } else { %>
														<input type="button" value="评论" style="color:red;border-style:none;outline:none;"
															onclick="prompt(<%=orderDetail.getStatus()%>)">
													<% } %>
												</td>
												<td style="text-align:center;">
													<% 
														if(orderDetail.getStatus() == 1) {
													%>
															<label style="color:red;">待发货</label>
													<%	} else if(orderDetail.getStatus() == 2) { %>
															<input type="button" value="确认收货" style="color:red;margin-bottom:10px;" 
																   onclick="confirmReceipt('<%=orderDetail.getOrderID()%>','<%=orderDetail.getGoodsID()%>');">
													<%	} else if(orderDetail.getStatus() == 3) { %>
															<label style="color:red;">已收货</label>
													<% 	} else if(orderDetail.getStatus() == 0) { %>
														<div>
															<input type="button" value="现在支付" style="color:red;margin-bottom:10px;" 
																   onclick="pay('<%=orderDetail.getOrderID()%>');">
															<br>
															<input type="button" value="取消订单" style="color:red" onclick="pay('<%=orderDetail.getOrderID()%>');">
														</div>
													<% } %>
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
	
	var url = "/myOrder?page=";
	
	function confirmReceipt(orderId, goodsId) {
		
		if(!window.confirm('请确保已收到商品，否则可能钱货两空？')) {
			return ;
        }
		
		var info = {"orderId":orderId,"goodsId":goodsId};
		$.ajax({
	         url: "/confirmReceipt",
	         data: JSON.stringify(info),
	         dataType: 'json',
	         type: "POST",
	         contentType: "application/json;charset=utf-8",
	         success: function(response) {
		     	alert(response.msg);
		     	if(response.code == 0) {
		     		window.location.href="myOrder?page=1";
		     	}
	         }
	    })
	}
	
	function pay(data) {
		var info = {"orderId":data};
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
		     		document.getElementById('orderId').value = data;
		     		document.getElementById('orderId_form').submit();
		     	}
	         }
	    })
	}
	
	function prompt(status) {
		if(status == 3) {
			alert('评论保存后无法修改！！');
		} else if(status == 0){
			alert('订单支付并确认收货后才能评论哦！！');
		} else {
			alert('确认收货后才能评论哦！！')
		}
		
	}

	function submit(goodsId, orderId) {
		var regu = "^[ ]+$";
		var re = new RegExp(regu);
		
		var comment = document.getElementById("comment"+goodsId).value;
		
		if(comment.length == 0 || re.test(comment)) {
			alert("请填写商品评论！！");
			return false;
		}
		
		if(!window.confirm('评论保存后无法修改，确定保存评论吗？')){
			return false;
        }
		
		var date = {"comment":comment,
					"goodsId":goodsId,
					"orderId":orderId};
		$.ajax({
	         url: "/save_comment",
	         data: JSON.stringify(date),
	         dataType: 'JSON',
	         type: "POST",
	         contentType: "application/json;charset=utf-8",
	         success: function(response){
	        	 if(response.code == 0) {
		             alert('评论成功');
		        	 window.location.href="/myOrder?page=<%=request.getAttribute("page")%>";
	        	 } else {
	        		 if(response.msg == "没有登录") {
	        			 alert('请先登录再进行评论');
	        			 window.location.href="/login";
	        		 }
	        	 }
	     	}
	    })
	}
</script>
</html>