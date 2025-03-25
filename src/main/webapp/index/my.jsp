<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>个人中心</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link type="text/css" rel="stylesheet" href="css/bootstrap.css">
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="layer/layer.js"></script>
	<script type="text/javascript" src="js/cart.js"></script>
	<script type="text/javascript" src="layui/layui.js"></script>

	<link rel="stylesheet" href="layui/css/layui.css"  media="all">
</head>
<body>

	<jsp:include page="header.jsp"/>
	
	<!--account-->
	<div class="account">
		<div class="container">
			<div class="register">
				<c:if test="${msg!=null}"><div class="alert alert-danger">${msg}</div></c:if>
				<form action="my" method="post"> 
					<input type="hidden" name="id" value="${user.id}">
					<div class="register-top-grid">
						<h3>个人中心</h3>
						
						<h4>收货信息</h4>
						<div class="input">
							<span>收货人<label></label></span>
							<input type="text" name="name" value="${user.name}" placeholder="请输入收货人">
						</div>
						<div class="input">
							<span>收货电话</span>
							<input type="text" name="phone" value="${user.phone}" placeholder="请输入收货电话"> 
						</div>
						<div class="input" style="padding: 0">
							<span>居住社区</span>
							<div class="col-sm-6" style="padding: 0 19px 0 0;margin:0;width: 100%;">
								<div class="layui-form-item" style="margin:0;width: 100%;">
									<div class="layui-input-block"  style="margin:0;width: 100%;">
										<select name="address" id="input_name" class="layui-input" required="required">
											<option value="address">${user.address}</option>
											<!-- 使用 JSTL 渲染下拉项 -->
											<c:forEach var="communities"  items="${communitiesList}">
												<option value="${communities.name}" >${communities.name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>

						</div>
						<div class="register-but text-center">
						   <input type="submit" value="提交">
						</div>	
						<hr>
						<h4>安全信息</h4>
						<div class="input">
							<span>原密码</span>
							<input type="text" name="password" placeholder="请输入原密码"> 
						</div>
						<div class="input">
							<span>新密码</span>
							<input type="text" name="passwordNew" placeholder="请输入新密码"> 
						</div>
						<div class="clearfix"> </div>
						<div class="register-but text-center">
						   <input type="submit" value="提交">
						</div>	
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