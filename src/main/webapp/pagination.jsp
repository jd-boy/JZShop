<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div style="text-align:center;">
	<br/>
	<ul class="pagination">
	  <li><a href="javascript:getPage(-1);">«</a></li>
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
				  <li><a <% if(p == count) { %>class="active"<% } %> href="javascript:getPage(<%=count%>);"><%=count%></a></li>
	  <%
	  				break;
		  	  }
	  %>	
		  	<li><a <% if(p == i) { %>class="active"<% } %> href="javascript:getPage(<%=i%>);"><%=i%></a></li>
	  <% } %>
	  <li><a href="javascript:getPage(-2);">»</a></li>
	</ul>
</div>

<script>
	function getPage(page) {
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
		
		window.location.href=url+page;
	}
</script>