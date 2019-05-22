<%@ page contentType="text/html; charset=UTF-8"%>

<%
	String mangername = (String) request.getAttribute("mangername");

	if (mangername == null || mangername == "") {
		response.sendRedirect("/manager_login");
	}
%>

<div id="toolbar" style="position:relative;background-color: #F0F0F0; width: 100%;height: 40px">
		<div style="position: absolute;right: 35px;top: 8px">
			<div style="font-size:15px;">
				<a href=""><%=mangername%></a>&nbsp;&nbsp; | 
				 &nbsp;&nbsp; <a href="/logout?type=1">登出</a>&nbsp;&nbsp; |
				 &nbsp;&nbsp; <a href="/manager_login">切换账号</a>
			</div>
		</div>
</div>
