<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>用户注册</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link type="text/css" rel="stylesheet" href="css/bootstrap.css">
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/simpleCart.min.js"></script>
</head>
<body>

	<jsp:include page="header.jsp"/>
	
	<!--account-->
	<div class="account">
		<div class="container">
			<div class="register">
				<c:if test="${msg!=null}"><div class="alert alert-danger">${msg}</div></c:if>
				<form action="register" method="post"> 
					<div class="register-top-grid">
						<h3>注册新用户</h3>
						<div class="input" style="margin-left: 160px">
							<span>用户名 <label style="color:red;">*</label></span>
							<input type="text" name="username" placeholder="请输入用户名" required="required" style="width: 350px;height: 32px">
						</div>
						<div class="input" style="margin-left: 160px">
							<span>密码 <label style="color:red;">*</label></span>
							<input type="password" name="password" placeholder="请输入密码" required="required" style="width: 350px;height: 32px">
						</div>
						<div class="input" style="margin-left: 160px">
							<span>收货人<label></label></span>
							<input type="text" name="name" placeholder="请输入收货人" style="width: 350px;height: 32px">
						</div>
						<div class="input" style="margin-left: 160px">
							<span>收货电话<label></label></span>
							<input type="text" name="phone" placeholder="请输入收货电话" style="width: 350px;height: 32px">
						</div>
						<div class="input" style="margin-left: 160px">
							<span>收货地址<label></label></span>
							<input type="text" name="address" placeholder="请输入收货宿舍" style="width: 350px;height: 32px">
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="register-but text-center">
					   <input type="submit" value="提交">
					   <div class="clearfix"> </div>
					</div>
				</form>
				<div class="clearfix"> </div>
			</div>
	    </div>
	</div>
	<!--//account-->

	<jsp:include page="footer.jsp"/>
	
</body>
</html>