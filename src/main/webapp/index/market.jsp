<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>LELE·易物</title>
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

	<!--banner-->
	<c:forEach var="top" items="${top1List}" end="0">
		<div class="banner">
			<div class="container">
				<h2 class="hdng"><a href="detail?goodid=${top.good.id}">${top.good.name}</a><span></span></h2>
				<p>社区精选推荐</p>
				<a class="banner_a" href="javascript:;" onclick="buy(${top.good.id})">加入购物车</a>
				<div class="banner-text">
					<a href="detail?goodid=${top.good.id}">
						<img src="../${top.good.cover}" alt="${top.good.name}" width="350" height="350">
					</a>
				</div>
			</div>
		</div>
	</c:forEach>
	<!--//banner-->

	<div class="subscribe2"></div>

	<!--gallery-->
	<div class="gallery">
		<div class="container">
	<blockquote class="layui-elem-quote layui-text">热销推荐</blockquote>
	<div class="gallery-grids" style="display: flex; flex-wrap: wrap; justify-content: flex-start;">
		<c:forEach var="top" items="${top2List}" end="5">
			<div class="col-md-4 gallery-grid glry-two" style="flex: 0 0 30%; box-sizing: border-box;">
				<a href="detail?goodid=${top.good.id}">
					<img src="../${top.good.cover}" class="img-responsive" alt="${top.good.name}" width="350" height="350"/>
				</a>
				<div class="gallery-info galrr-info-two">
					<p>
						<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
						<a href="detail?goodid=${top.good.id}">查看详情</a>
					</p>
					<a class="shop" href="javascript:;" onclick="buy(${top.good.id})">加入购物车</a>
					<div class="clearfix"> </div>
				</div>
				<div class="galy-info">
					<p>${top.good.type.name} > ${top.good.name}</p>
					<div class="galry">
						<div class="prices">
							<h5 class="item_price">¥ ${top.good.price}</h5>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
	<div class="clearfix"></div>
	<blockquote class="layui-elem-quote layui-text">新品推荐</blockquote>
			<div class="gallery-grids" style="display: flex; flex-wrap: wrap; justify-content: flex-start;">
				<c:forEach var="top" items="${top3List}" end="7">
					<div class="col-md-3 gallery-grid ">
						<a href="detail?goodid=${top.good.id}">
							<img src="../${top.good.cover}" class="img-responsive" alt="${top.good.name}"/>
						</a>
						<div class="gallery-info">
							<p>
								<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
								<a href="detail?goodid=${top.good.id}">查看详情</a>
							</p>
							<a class="shop" href="javascript:;" onclick="buy(${top.good.id})">加入购物车</a>
							<div class="clearfix"> </div>
						</div>
						<div class="galy-info">
							<p>${top.good.type.name} > ${top.good.name}</p>
							<div class="galry">
								<div class="prices">
									<h5 class="item_price">¥ ${top.good.price}</h5>
								</div>
								<div class="clearfix"></div>
							</div>
						</div>
					</div>
				</c:forEach>

			</div>
		</div>
	</div>
	<!--//gallery-->

	<!--subscribe-->
	<div class="subscribe"></div>
	<!--//subscribe-->


	<jsp:include page="footer.jsp"/>
	<script src="layui/layui.js" charset="utf-8"></script>

</body>
</html>