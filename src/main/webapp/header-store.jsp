<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<header id="mr-header" class="wrap mr-header">
	<div class="container">
		<div class="row">
			<!-- 店铺管理航条 -->
			<nav id="mr-mainnav"
				class="col-xs-12 col-md-6 mr-mainnav navbar navbar-default">
				<div class="mr-navbar navbar-collapse collapse">
					<div class="mr-megamenu animate slide" data-duration="400"
						data-responsive="true">
						<ul class="nav navbar-nav level0">
							<li itemprop="name" data-id="435" data-level="1">
								<a id="index" itemprop="url" class=""
								href="/index" data-target="#">首页 </a></li>
								
							<li itemprop="name" data-id="435" data-level="1">
								<a id="myStore" itemprop="url" class=""
								href="/myStore?page=1" data-target="#">店铺商品 </a></li>
								
							<li itemprop="name" data-id="510" data-level="1">
								<a id="add" itemprop="url" class=""
								href="/addGoods" data-target="#">商品添加</a></li>

							<li itemprop="name" data-id="510" data-level="1">
								<a id="manage" itemprop="url" class=""
								href="/manageGoods" data-target="#">商品管理</a></li>
								
							<li itemprop="name" data-id="510" data-level="1">
								<a id="handleOrder" itemprop="url" class=""
								href="/handleOrder?page=1" data-target="#">待发货订单</a></li>
								
							<li itemprop="name" data-id="510" data-level="1">
								<a id="unfinishOrder" itemprop="url" class=""
								href="/unfinishOrder?page=1" data-target="#">未收货订单</a></li>
								
							<li itemprop="name" data-id="510" data-level="1">
								<a id="fulfilOrder" itemprop="url" class=""
								href="/fulfilOrder?page=1" data-target="#">已完成订单</a></li>
							
						</ul>
					</div>

				</div>
			</nav>
			<!-- 店铺管理航条 -->
		</div>
	</div>
</header>
<script>
	// 获取页面参数
	function GetString(name) {
		var url = window.location.href;
		if(url.indexOf(name) != -1) {
			return 1;
		}
		return 0;
	}
	
	if (GetString('myStore') == 1) {
		var myStore = document.getElementById('myStore');
		myStore.style.backgroundColor = "#9E2626";
		flag = 1;
	}
	if (GetString('addGoods') == 1) {
		var add = document.getElementById('add');
		add.style.backgroundColor = "#9E2626";
		flag = 1;
	}
	if (GetString('manageGoods') == 1 || GetString('alterGoods')) {
		var del = document.getElementById('manage');
		del.style.backgroundColor = "#9E2626";
		flag = 1;
	}
	if (GetString('handleOrder') == 1) {
		var handleOrder = document.getElementById('handleOrder');
		handleOrder.style.backgroundColor = "#9E2626";
		flag = 1;
	}
	if (GetString('unfinishOrder') == 1) {
		var unfinishOrder = document.getElementById('unfinishOrder');
		unfinishOrder.style.backgroundColor = "#9E2626";
		flag = 1;
	}
	if (GetString('fulfilOrder') == 1) {
		var finishOrder = document.getElementById('fulfilOrder');
		finishOrder.style.backgroundColor = "#9E2626";
		flag = 1;
	}
	
</script>