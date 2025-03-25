<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>客户修改</title>
<meta charset="utf-8"/>
<link rel="stylesheet" href="/admin/css/bootstrap.css"/>
	<link rel="stylesheet" href="/admin/layui/css/layui.css"  media="all">
</head>
<body>
<div class="container-fluid">

	<%@include file="header.jsp"%>

	<br><br>
	
	<form class="form-horizontal" action="userUpdate" method="post">
		<input type="hidden" name="id" value="${user.id}">
		<input type="hidden" name="username" value="${user.username}">
		<input type="hidden" name="password" value="${user.password}">
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">用户名</label>
			<div class="col-sm-5">${user.username}</div>
		</div>
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">姓名</label>
			<div class="col-sm-6">
				<input type="text" class="layui-input" id="input_name" name="name" value="${user.name}">
			</div>
		</div>
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">电话</label>
			<div class="col-sm-6">
				<input type="text" class="layui-input" id="input_name" name="phone" value="${user.phone}">
			</div>
		</div>
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">小区</label>
			<div class="col-sm-6">
				<div class="layui-form-item" >
					<div class="layui-input-block" style="margin-left:0px;" >
						<select name="address" id="input_name" class="layui-input" required="required">
							<option value="${user.address}">${user.address}</option>
							<!-- 使用 JSTL 渲染下拉项 -->
							<c:forEach var="communities"  items="${communitiesList}">
								<option value="${communities.name}" >${communities.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>

		</div>
		<div class="form-group">
			<div class="col-sm-offset-1 col-sm-10">
				<button type="submit" class="layui-btn">提交修改</button>
			</div>
		</div>
	</form>
	
	<span style="color:red;">${msg}</span>
	
</div>
<script src="/admin/layui/layui.js" charset="utf-8"></script>
</body>
</html>