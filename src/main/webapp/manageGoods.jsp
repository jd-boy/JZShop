<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.shop.model.Goods"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品管理-JZ商城</title>
<link rel="stylesheet" href="css/mr-01.css" type="text/css">

<script src="js/jsArr01.js" type="text/javascript"></script>
<script src="js/module.js" type="text/javascript"></script>
<script src="js/jsArr02.js" type="text/javascript"></script>
<script src="js/tab.js" type="text/javascript"></script>
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
								<h1>商品管理</h1>
								<!-- 显示全部商品 -->
								<div class="table-responsive cart-info">
									<table class="table table-bordered">
										<thead>
											<tr>
												<td style="text-align:center;">宝贝</td>
												<td style="text-align:center;">商品简介</td>
												<td style="text-align:center;">商品单价</td>
												<td style="text-align:center;">商品折后价</td>
												<td style="text-align:center;">商品库存</td>
												<td style="text-align:center;">管理</td>
											</tr>
										</thead>
										<tbody>
										<!-- 遍历全部商品并显示 -->
											
											<!-- 显示一条商品信息 -->
											<%
												for(Goods goods : (ArrayList<Goods>) request.getAttribute("goodsList")) {
													
											%>
											<tr>
												<td style="text-align:center;" width="20%">
													<a href="/goodsDetail?ID=<%=goods.getID()%>&page=1">
														<img width="80px" src="/image?id=<%=goods.getID()%>">
													</a>
													<br/>
													<a href="/goodsDetail?ID=<%=goods.getID()%>&page=1"> <%=goods.getGoodsName()%></a>
												</td>
												<td><%=goods.getIntroduce()%></td>
												<td style="text-align:center;"><%=goods.getPrice()%>元</td>
												<td style="text-align:center;"><%=goods.getNowPrice()%>元</td>
												<td style="text-align:center;"><%=goods.getNum()%>件</td>
												<td style="text-align:center;">
													<a href="javascript:if(confirm('你确定要删除 '+'<%=goods.getGoodsName()%>'+' 吗？'))location='/delGoods?id=<%=goods.getID()%>'">删除</a>
													<br/><br/>
													<a href="/alterGoods?id=<%=goods.getID()%>">修改</a>
												</td>
											</tr>
											<!-- 显示商品信息 -->
											
											<% } %>
											<!-- //遍历全部商品并显示 -->
										</tbody>
									</table>
								</div>
								<!-- 显示全部商品 -->
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
</html>