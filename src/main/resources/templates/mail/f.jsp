<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>${seller.size()}
	<p>尊敬的用户：${seller.getOrderID()}</p>
	<p style="text-indent: 2em">有用户购买了您的商品 ${seller.getGoodsName()}</p>
	<p style="text-indent: 2em">数量 ${seller.getNumber()}</p>
</body>
</html>
