<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>小区编辑</title>
<meta charset="utf-8"/>
<link rel="stylesheet" href="/admin/css/bootstrap.css"/>
	<link rel="stylesheet" href="/admin/layui/css/layui.css"  media="all">
</head>
<body>
<div class="container-fluid">

	<%@include file="header.jsp"%>

	<br><br>
	
	<form class="form-horizontal" action="communitiesUpdate" method="post">
		<input type="hidden" name="id" value="${communities.id}">
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">小区名称</label>
			<div class="col-sm-6">
				<input type="text" class="layui-input" id="input_name" name="name" value="${communities.name}" required="required">
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