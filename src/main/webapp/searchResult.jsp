<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.shop.model.Goods" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>商品搜索-JZ商城</title>
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
	<jsp:include page="common-header.jsp"/>
	
	<!-- //网站头部 -->
	<!-- 轮播广告及热门商品 -->
	<div class="container mr-sl mr-sl-1">
		<div class="mr-spotlight mr-spotlight-1  row">
			<!-- 显示轮播广告 -->
			<div
				class=" col-lg-9 col-md-12  col-sm-3 hidden-sm   col-xs-6 hidden-xs ">
				<div class="mr-module module " id="Mod159">
					<div class="module-inner">
						<div class="module-ct">
							<div class="mijoshop">
								<div class="container_oc">
									<div class="slideshow">
										<div id="slidershow" class="nivoSlider">
											<a href="#" class="nivo-imageLink" style="display: block;">
												<img
												src="images/img1.png" class="img-responsive"
												style="display: none;"> 
											</a> 
											<a href="#" class="nivo-imageLink" style="display: none;"> 
												<img
												src="images/img2.png" class="img-responsive"
												style="display: none;">
											</a>
											<a href="#" class="nivo-imageLink" style="display: none;">
												<img src="images/img3.png" class="img-responsive"
												style="display: none;">
											</a>
											<a href="#" class="nivo-imageLink" style="display: none;">
												<img src="images/img4.png" class="img-responsive"
												style="display: none;">
											</a>
										</div>
									</div>
									<script type="text/javascript">
										//实现调用幻灯片插件轮播广告
										<!--
										jQuery(document).ready(function() {
											jQuery('#slidershow').nivoSlider();
										});
									//-->
									</script>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- 显示热门商品 -->
			<div
				class="col-lg-3  col-md-6 hidden-md   col-sm-3 hidden-sm   col-xs-6 hidden-xs ">
				<div class="mr-module module highlight " id="Mod160">
					<div class="module-inner">
						<h3 class="module-title ">
							<span>热门商品</span>
						</h3>
						<div class="module-ct">
							<div class="mijoshop">
								<div class="container_oc">
									<div class="box_oc">
										<!-- 循环显示热门商品 ：添加两条商品信息-->
										<%
											ArrayList<Goods> hotGoodsList = (ArrayList)request.getAttribute("hotGoods");
											for(Goods hotGoods : hotGoodsList) {
										%>
										
										<div class="box-product product-grid">
											<div>
												<div class="image">
													<a href="/goodsDetail?ID=<%=hotGoods.getID()%>&page=1">
													<img src="/image?id=<%=hotGoods.getID()%>" width="250px"></a>
													
												</div>
												<div class="name"><a href="/goodsDetail?ID=<%=hotGoods.getID()%>&page=1"><%=hotGoods.getGoodsName()%></a></div>
												<!-- 星级评分条 -->
												<div class="rating">
													<span class="fa fa-stack">
														<i class="fa fa-star fa-stack-2x"></i>
														<i class="fa fa-star-o fa-stack-2x"></i>
													</span>
													
													<span class="fa fa-stack">
														<i class="fa fa-star fa-stack-2x"></i>
														<i class="fa fa-star-o fa-stack-2x"></i>
													</span>

													<span class="fa fa-stack">
														<i class="fa fa-star fa-stack-2x"></i>
														<i class="fa fa-star-o fa-stack-2x"></i>
													</span>
													
													<span class="fa fa-stack">
														<i class="fa fa-star fa-stack-2x"></i>
														<i class="fa fa-star-o fa-stack-2x"></i>
													</span>

													<span class="fa fa-stack">
														<i class="fa fa-star fa-stack-2x"></i>
														<i class="fa fa-star-o fa-stack-2x"></i>
													</span>
												</div>
												<!-- // 星级评分条 -->
												<!-- 商品价格 -->
												<div class="price">
													<span class="price-new">价格：<%=hotGoods.getNowPrice()%> 元
													</span>
												</div>
												<!-- // 商品价格 -->
											</div>
										</div>
										<% } %>

										<!-- // 循环显示热门商品 ：添加两条商品信息-->
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- // 显示热门商品 -->
		</div>
	</div>
	<!-- //轮播广告及热门商品  -->
	<!-- 优选商品展示 -->
	<nav class="container mr-masstop  hidden-sm hidden-xs">
	<div class="custom">
		<div>
			<div class="ja-tabswrap default" style="width: 100%;">
				<div id="myTab" class="container">

					<h3 class="index_h3">
						<span class="index_title">优选商品</span>
					</h3>
					<!-- //优选商品选项卡 -->
					<div class="ja-tab-content ja-tab-content col-6 active"
						style="opacity: 1; width: 100%; visibility: visible;">
						<div class="ja-tab-subcontent">
							<div class="mijoshop">
								<div class="container_oc">
									<div class="row">
										<!-- 循环显示优选商品：添加12条商品信息-->
										<%
											ArrayList<Goods> goodsList = (ArrayList)request.getAttribute("goodsList");
											for(Goods goods : goodsList) {
										%>
										
										<div
											class="product-grid col-lg-2 col-md-3 col-sm-6 col-xs-12">
											<div class="product-thumb transition">
												<div class="actions">
													<div class="image">
														<a href="/goodsDetail?ID=<%=goods.getID()%>&page=1">
														<img src="/image?id=<%=goods.getID()%>" alt="<%=goods.getGoodsName()%>" class="img-responsive"></a>
													</div>
													<div class="button-group">
														<div class="cart">
															<button class="btn btn-primary btn-primary" type="button" data-toggle="tooltip"
																onclick='javascript:window.location.href="/cart_add?id=<%=goods.getID()%>&num=1"; '
																style="display: none; width: 33.3333%;" data-original-title="加入到购物车">
																<i class="fa fa-shopping-cart"></i>
															</button>
														</div>
													</div>
												</div>
												<div class="caption">
													<div class="name" style="height: 40px">
														<a href="/goodsDetail?ID=<%=goods.getID()%>&page=1"> <span style="color: #0885B1">商品名：</span>  <%=goods.getGoodsName()%></a>
													</div>
													<div class="name" style="margin-top: 10px"><p class="price">价格：<%=goods.getPrice()%>元</p></div>
												</div>
											</div>
										</div>
										
										<% } %>
										
										<!-- //循环显示优选商品：添加12条商品信息 -->
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- //优选商品选项卡 -->
			</div>
		</div>
	</div>
	<div style="text-align:center;">
		<ul class="pagination">
		  <li><a href="javascript:search(-1);">«</a></li>
		  <% 
		  	  int p = (int)request.getAttribute("page");
		  	  int count = (int)request.getAttribute("count");
			  int i = p;
			  boolean flag = false;
			  if(count-p+1 > 7) {
				  flag = true;
			  }
			  if(count <= 7) {
				  i = 1;
			  }
			  for(int j = 0; i <= count && j < 7; i++,j++) {
				  if(flag && j == 5) {
		  %>
					  <li><a href="javascript:void(0);">···</a></li>
					  <li><a <% if(p == count) { %>class="active"<% } %> href="javascript:search(<%=count%>);"><%=count%></a></li>
		  <%
		  				break;
			  	  }
		  %>	
			  	<li><a <% if(p == i) { %>class="active"<% } %> href="javascript:search(<%=i%>);"><%=i%></a></li>
		  <% } %>
		  <li><a href="javascript:search(-2);">»</a></li>
		</ul>
	</div>
	</nav>
	<!-- //优选商品展示 -->
	<!-- 版权栏 -->
	<%@ include file="common-footer.jsp"%>
	<!-- //版权栏 -->
</body>

</html>