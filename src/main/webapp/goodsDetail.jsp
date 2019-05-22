<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.shop.model.Goods"%>
<%@ page import="com.shop.model.Comment"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>JZ商城</title>
<link rel="stylesheet" href="css/mr-01.css" type="text/css">
<link rel="stylesheet" href="css/pagination.css" type="text/css">

<script src="js/jsArr01.js" type="text/javascript"></script>
<script src="js/module.js" type="text/javascript"></script>
<script src="js/jsArr02.js" type="text/javascript"></script>
<script src="js/tab.js" type="text/javascript"></script>
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
			<div id="mr-content"
				class="mr-content col-xs-12 col-sm-12 col-md-9 col-md-push-3">
				<div id="mrshop" class="mrshop common-home">
					<div class="container_oc">
						<div class="row">
							<div id="content_oc" class="col-sm-12 view-product">
								<!-- 根据商品ID获取并显示商品信息 -->
								<%
									Goods goods = (Goods)request.getAttribute("goods");
								%>
								
								<!-- 显示商品详细信息 -->
								<div class="row">
									<div class="col-xs-12 col-md-4 col-sm-4">
										<ul class="thumbnails" style="list-style: none">
											<li><a class="thumbnail"> <img src="image/?id=<%=goods.getID()%>"></a></li>
										</ul>
									</div>
									<div class="col-xs-12 col-md-8 col-sm-8">
										<div style="margin-left: 30px; margin-top: 20px">
											<h1 class="product-title"><%=goods.getGoodsName()%></h1>
											<ul class="list-unstyled price"><li><h2><%=goods.getNowPrice()%>元</h2></li></ul>
											<ul class="list-unstyled price"><li>原价: <%=goods.getPrice()%>元</li></ul>
											<div style="font-size:15px">
												<h5>库存：<%=goods.getNum()%></h5>
												<h5>销量：<%=goods.getBuy()%></h5>
											</div>
											<div class="rating"><p>商城活动：全场满99包邮</p></div>
											<div id="product"><hr>
												<div class="form-group">
													<label class="control-label" for="shuliang"> 数量 </label>
													<input type="number" name="quantity" value="1" size="2"
														id="shuliang" class="form-control"> <br>
													<div class="btn-group">
														<button type="button" onclick="addCart();" class="btn btn-primary btn-primary">
															<i class="fa fa-shopping-cart"></i> 添加到购物车</button>
														<button type="button" id="button-wishlist" data-toggle="tooltip" class="btn"
														 title="收藏" data-original-title="收藏"> <i class="fa fa-heart"></i></button>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="col-sm-12 description_oc clearfix">
										<ul class="nav nav-tabs htabs">
											<li class="active" style="width: 150px"><a href="#tab-description" data-toggle="tab"
												aria-expanded="true">商品描述</a></li>
										</ul>
										<div class="tab-content" style="border: 1px solid #eee; overflow: hidden;">
											<div class="tab-pane active" id="tab-description">
												<%=goods.getIntroduce()%>
											</div>
										</div>
										
									</div>
									<div class="col-sm-12 description_oc clearfix">
										<br/>
										<ul class="nav nav-tabs htabs">
											<li class="active" style="width: 150px"><a href="#tab-description" data-toggle="tab"
												aria-expanded="true">商品评论</a></li>
										</ul>
										<%
											ArrayList<Comment> list = (ArrayList<Comment>)request.getAttribute("commentList");
										%>
										<%
											for(Comment c : list) {
										%>
											<br/>
											<div class="evaluation-content">
												<img class="avatar" src="/head?id=<%=c.getUserId()%>">
												
												<div class="evaluation-text" style="left:100px;font-size:15px;padding:5px;">
													<%=c.getText()%>
												</div>
											</div>
										<%
											}
											if(list.size() == 0) {
										%>
												<br/>
												<div class="evaluation-content" style="color:red;font-size:15px;">还没有人评论哦！</div>
										<% } else { %>
											<jsp:include page="pagination.jsp" />
										<%} %>
									</div>
									
								</div>

								<!-- //显示商品详细信息 -->
								<!-- 显示相关商品 -->
								<div class="mr-module related-products">
									<h3 class="module-title ">相关商品</h3>
									<!-- 显示底部相关商品 -->
									<jsp:include page="relatedGoods.jsp">
										<jsp:param name="typeSystem" value="<%=goods.getTypeID()%>" />
									</jsp:include>
									<!-- // 显示底部相关商品 -->
								</div>
								<!-- //显示相关商品 -->
								<!-- //根据商品ID获取并显示商品信息 -->
							</div>
						</div>
					</div>

				</div>
			</div>
			<!-- //页面主体内容 -->
			<!-- 显示左侧热门商品 -->
			<jsp:include page="leftHotGoods.jsp">
				<jsp:param name="typeSystem" value="<%=goods.getTypeID()%>" />
			</jsp:include>
			<!-- // 显示左侧热门商品 -->

		</div>
	</div>
	<!-- 版权栏 -->
	<%@ include file="common-footer.jsp"%>
	<!-- //版权栏 -->
	<script src="js/jquery.1.3.2.js" type="text/javascript"></script>
	<script type="text/javascript">

		var url = "/goodsDetail?ID="+'<%=goods.getID()%>'+"&page=";
	
		function addCart() {
			
			var num = $('#shuliang').val();			//获取输入的商品数量
			//验证输入的数量是否合法
			if (num < 1) {							//如果输入的数量不合法
				alert('数量不能小于1！');
				return;
			}
			var goodsId = <%=goods.getID()%>;
			var date = {"goodsId":goodsId,
						"num":num};
			$.ajax({
		         url: "/cart_add",
		         data: JSON.stringify(date),
		         dataType: 'json',
		         type: "POST",
		         contentType: "application/json;charset=utf-8",
		         success: function(response){
		        	 if(response.code == 0) {
			             alert(response.msg);
		        	 } else if(response.code == 2) {
		        		 window.location.href = "/login";
		        	 } else {
		        		 alert('添加到购物车失败！！');
		        	 }
		     	}
		    })
		}
	</script>
	
</body>
</html>
