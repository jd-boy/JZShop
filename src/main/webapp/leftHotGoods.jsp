<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.shop.util.SpringUtil"%>
<%@ page import="com.shop.model.Goods"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="com.shop.serviceImpl.GoodsServiceImpl"%>
<%@ page import="java.util.ArrayList" %>
<%
	int hot_ID = 0;
	String hot_goodsName = null;
	BigDecimal hot_nowprice = new BigDecimal("0.00");
	String hot_picture = null;
	ArrayList<Goods> hotGoodsList = null;
	GoodsServiceImpl goodsServiceImpl = (GoodsServiceImpl)SpringUtil.getBean(GoodsServiceImpl.class);
	if(request.getParameter("type")!=null && request.getParameter("type")!=""){
		int type=Integer.parseInt(request.getParameter("type"));
		hotGoodsList = goodsServiceImpl.getHotGoodsBySuperID(type);
	}else if(request.getParameter("typeSystem")!=null && request.getParameter("typeSystem")!=""){
		int typeID = Integer.parseInt(request.getParameter("typeSystem"));
		hotGoodsList = goodsServiceImpl.getHotGoodsByTypeID(typeID);
	}else{
		hotGoodsList = goodsServiceImpl.getHotGoods();
	}
%>
<div
	class="mr-sidebar mr-sidebar-left col-xs-12 col-sm-4 col-sm-pull-8 col-md-3 col-md-pull-9  hidden-sm hidden-xs">

	<div class="mr-module module " id="Mod157">
		<div class="module-inner">
			<h3 class="module-title ">
				<span>热门商品</span>
			</h3>

			<div class="module-ct">
				<div class="mrshop">
					<div class="container_oc">
						<div class="box_oc">
							<div>
								<div class="box-product product-grid">

									<%
										for(Goods hotGoods : hotGoodsList) {
											hot_ID = hotGoods.getID();
											hot_goodsName = hotGoods.getGoodsName();
											hot_nowprice = hotGoods.getNowPrice();
											hot_picture = hotGoods.getPicture();
									%>
									<div>
										<div class="image">
											<a href="/goodsDetail?ID=<%=hot_ID%>&page=1"><img
												src="/image?id=<%=hot_ID%>" width="80px">
											</a>
										</div>
										<div class="name">
											<a href="/goodsDetail?ID=<%=hot_ID%>&page=1"> <%=hot_goodsName%>
											</a>
										</div>
										<div class="rating">
											<span class="fa fa-stack"><i
												class="fa fa-star fa-stack-2x"></i><i
												class="fa fa-star-o fa-stack-2x"></i></span> <span
												class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i
												class="fa fa-star-o fa-stack-2x"></i></span> <span
												class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i
												class="fa fa-star-o fa-stack-2x"></i></span> <span
												class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i
												class="fa fa-star-o fa-stack-2x"></i></span> <span
												class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i
												class="fa fa-star-o fa-stack-2x"></i></span>
										</div>
										<div class="price">
											<%=hot_nowprice%>
											元
										</div>

									</div>
									<%
										}
									%>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>