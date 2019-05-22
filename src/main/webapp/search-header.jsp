<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="toolbar" style="background-color: #F0F0F0; width: 100%;">
	<div style="background-color: white; width: 100%">
		<div class="container">
			<div class="row">
				<div class="toolbar-ct col-xs-12 col-md-6  hidden-sm hidden-xs">
					<div class="toolbar-ct-1">
						<img src="images/jzlogo.png">
					</div>
					<div>
						<!-- 搜索条 -->
						<% String name = (String)request.getAttribute("name"); %>
						<div class="search_box">
							<div class="top-nav-search">
								<form method="post" action="/searchResult" onsubmit="return search(1)">
									<input type="text" name="searchword" id="searchword" size="58"
										style="border: 0px;" class="top-nav-search-input"
										<% if(name != null) { %>value="<%=request.getAttribute("name")%>" <% } %>
										placeholder="请输入内容"/>
									<input type="image" src="images/search.png" 
										class="search_box_img" onclick="return search(1)" />
								</form>
							</div>
						</div>
						<!-- //搜索条 -->

					</div>
				</div>

				<div class="toolbar-ct toolbar-ct-right col-xs-12 col-md-3">

					<div class="toolbar-ct-2 "
						style="margin-top: 35px; border: 1px solid #EAEAEA; padding: 5px;">
						<a href="/cart_see" style="color: #E33737; font-size: 20px;">
							<i class="fa fa-cart1"></i> 我的购物车</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	function search(page) {
		var keyword = document.getElementById("searchword").value;
		
		
		if(<%=request.getAttribute("count")%> == null) {
			count = 1;
		} else {
			var count = '<%=request.getAttribute("count")%>';
		}
		
		var reg = new RegExp("(^|&)" + "page" + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		var bp = window.location.search.substr(1).match(reg); //匹配目标参数
		if (bp != null) {
			var beforePage = unescape(bp[2]);
			if(page == -1 && beforePage-1 > 0) {
				page = beforePage-1;
			} else if(page == -2 && parseInt(beforePage)+parseInt(1) <= count) {
				page = parseInt(beforePage) + parseInt(1);
			} else if(page < 1 || page > count){
				return false;
			}
		}
		
		window.location.href="/searchResult?name="+keyword+"&page="+page;
		return false;
	}
</script>