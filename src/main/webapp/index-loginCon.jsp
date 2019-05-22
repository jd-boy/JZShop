<%@ page contentType="text/html; charset=UTF-8"%>
<!--jsp:useBean id="chStr" scope="page" class="com.tools.ChStr" / -->
<%
	String username = (String) request.getAttribute("username");

	if (username == null || username == "") {
%>
<div id="toolbar" style="background-color: #F0F0F0; width: 100%;">
	<div class="container">
		<div class="row">
			<div class="toolbar-ct-1">
				<p>
					<i class="fa fa-phone"></i> <span style="margin-right: 15px;">电话：400-675-1066</span><a
						href="/login?type=0">登录</a>&nbsp; ｜ &nbsp;<a href="/register">注册</a>
				</p>
			</div>
			<div class="toolbar-ct-2">
				<a href="/myOrder?page=1">我的订单</a>&nbsp;&nbsp; | &nbsp;&nbsp; <a>我的收藏</a>
			</div>
		</div>
	</div>
</div>
<%
	} else {
		int usertype = (int) request.getAttribute("usertype");
%>
<div id="toolbar" style="background-color: #F0F0F0; width: 100%;">
	<div class="container">
		<div class="row">
			<div class="toolbar-ct-1">
				<p>
					<i class="fa fa-phone"></i> <span style="margin-right: 15px;">电话：400-675-1066</span>您好，<%=username%>
					&nbsp; &nbsp;<a href="/modifyMember">修改</a>&nbsp;&nbsp;
					|&nbsp;&nbsp;<a href="/logout?type=0">退出</a>
				</p>
			</div>
			<div class="toolbar-ct-2">
				<a href="/myOrder?page=1">我的订单</a>&nbsp;&nbsp; | 
				 &nbsp;&nbsp; <a href="">我的收藏</a> 
				 <%if(usertype == 1) {%>
				 	&nbsp;&nbsp; | &nbsp;&nbsp; <a href="/myStore?page=1">我的店铺</a>
				 <%}%>
			</div>
		</div>
	</div>
</div>
<%
	}
%>