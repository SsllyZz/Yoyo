<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>我的商品</title>
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

<script>
	//注意：导航 依赖 element 模块，否则无法进行功能性操作
	layui.use('element', function(){
		var element = layui.element;

		//…
	});
</script>

<div class="container-fluid" style="padding-left: 200px;padding-right: 200px;">

	<div class="text-right"><a style="width:15%;margin-top:20px;text-align:center;font-size:18px;text-decoration: none;color: white" class="layui-btn layui-btn-warm" href="goodAdd">上架商品</a></div>
	
	<br>
		
<%--	<ul role="tablist" class="nav nav-tabs">--%>
<%--        <li <c:if test='${status==0}'>class="active"</c:if> role="presentation"><a href="goodList">全部商品</a></li>--%>
<%--        <li <c:if test='${status==1}'>class="active"</c:if> role="presentation"><a href="goodList?status=1">条幅推荐</a></li>--%>
<%--        <li <c:if test='${status==2}'>class="active"</c:if> role="presentation"><a href="goodList?status=2">热销推荐</a></li>--%>
<%--        <li <c:if test='${status==3}'>class="active"</c:if> role="presentation"><a href="goodList?status=3">新品推荐</a></li>--%>
<%--    </ul>--%>
    
<%--    <c:if test="${status == 1}"><br><p>首页默认只显示前[1]条记录</p></c:if>--%>
<%--    <c:if test="${status == 2}"><br><p>首页默认只显示前[6]条记录</p></c:if>--%>
<%--    <c:if test="${status == 3}"><br><p>首页默认只显示前[8]条记录</p></c:if>--%>
	
	<br>

	<table class="layui-table" lay-even="" lay-skin="row">

	<tr>
		<th width="5%">ID</th>
		<th width="10%">图片</th>
		<th width="10%">名称</th>
		<th width="20%">介绍</th>
		<th width="10%">价格</th>
		<th width="15%">操作</th>
	</tr>
	
	<c:forEach var="good" items="${goodList}">
         <tr>
         	<td><p>${good.id}</p></td>
         	<td><p><a href="../index/detail?goodid=${good.id}" target="_blank"><img src="../${good.cover}" width="100px" height="100px"></a></p></td>
         	<td><p><a href="../index/detail?goodid=${good.id}" target="_blank">${good.name}</a></p></td>
         	<td><p>${good.intro}</p></td>
         	<td><p>${good.price}</p></td>
			<td>
<%--				<div class="layui-btn-container">--%>
<%--				<c:if test="${good.topScroll}"><a style="text-decoration: none;color: white" class="layui-btn layui-btn-sm layui-btn-warm topDelete" href="javascript:;" type="1" goodid="${good.id}" text="加入条幅">移出条幅</a></c:if>--%>
<%--				<c:if test="${!good.topScroll}"><a style="text-decoration: none;color: white" class="layui-btn layui-btn-sm layui-btn-normal topSave" href="javascript:;" type="1" goodid="${good.id}" text="移出条幅">加入条幅</a></c:if>--%>
<%--				<c:if test="${good.topLarge}"><a style="text-decoration: none;color: white" class="layui-btn layui-btn-sm layui-btn-warm topDelete" href="javascript:;" type="2" goodid="${good.id}" text="加入热销">移出热销</a></c:if>--%>
<%--				<c:if test="${!good.topLarge}"><a style="text-decoration: none;color: white" class="layui-btn layui-btn-sm layui-btn-normal topSave" href="javascript:;" type="2" goodid="${good.id}" text="移出热销">加入热销</a></c:if>--%>
<%--				<c:if test="${good.topSmall}"><a style="text-decoration: none;color: white" class="layui-btn layui-btn-sm layui-btn-warm topDelete" href="javascript:;" type="3" goodid="${good.id}" text="加入新品">移出新品</a></c:if>--%>
<%--				<c:if test="${!good.topSmall}"><a style="text-decoration: none;color: white" class="layui-btn layui-btn-sm layui-btn-normal topSave" href="javascript:;" type="3" goodid="${good.id}" text="移出新品">加入新品</a></c:if>--%>
<%--				</div>--%>
				<div class="layui-btn-container">
<%--						<a class="btn btn-success" href="goodEdit?id=${good.id}">修改</a>--%>
						<a href="goodEdit?id=${good.id}"><button type="button" class="layui-btn layui-btn-sm"><i class="layui-icon">修改</i></button></a>
<%--						<a class="btn btn-danger" href="goodDelete?id=${good.id}">删除</a>--%>
						<a href="goodDelete?id=${good.id}"><button type="button" class="layui-btn layui-btn-sm layui-btn-danger"><i class="layui-icon">下架</i></button></a>
				</div>
			</td>
       	</tr>
     </c:forEach>
     
</table>

</div>

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
$(function(){
	$(document).on("click", ".topSave", function(){
		var type = $(this).attr("type");
		var goodid = $(this).attr("goodid");
		var text = $(this).attr("text");
		var old = $(this).text();
		var obj = this;
		$.post("topSave.action", {"goodId": goodid, "type": type}, function(data){
			if(data=="ok"){
				$(obj).text(text).attr("class", "layui-btn layui-btn-sm layui-btn-warm topDelete").attr("text", old);
			}else{
				alert("操作失败!");
			}
		}, "text");
	});
	$(document).on("click", ".topDelete", function(){
		var type = $(this).attr("type");
		var goodid = $(this).attr("goodid");
		var text = $(this).attr("text");
		var old = $(this).text();
		var obj = this;
		$.post("topDelete.action", {"goodId": goodid, "type": type}, function(data){
			if(data=="ok"){
				$(obj).text(text).attr("class", "layui-btn layui-btn-sm layui-btn-normal topSave").attr("text", old);
			}else{
				alert("操作失败!");
			}
		}, "text");
	});
});
</script>
<jsp:include page="footer.jsp"/>
<script src="layui/layui.js" charset="utf-8"></script>

</body>
</html>