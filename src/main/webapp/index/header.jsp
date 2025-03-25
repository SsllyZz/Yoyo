<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
</head>
<body>

	<!--header-->
	<div class="header">
		<div class="container" style="margin-right:10%;width: 100%">
			<nav class="navbar navbar-default" role="navigation"  >
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<h1 class="navbar-brand"><a href="market">LELE</a></h1>
				</div>
				<!--navbar-header-->
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a href="market" style="display: flex; /* 使用 flexbox 让图标和文字在同一行 */
    align-items: center; /* 垂直居中 */">
							<svg t="1742883210037" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="21361" width="20" height="20"><path d="M512 28.444444a384 384 0 0 1 56.888889 763.790223V938.666667a56.888889 56.888889 0 0 1-113.777778 0v-146.375111A384.113778 384.113778 0 0 1 512 28.444444z m0 113.777778a270.222222 270.222222 0 0 0-0.682667 540.444445h1.365334l11.662222-0.284445A270.222222 270.222222 0 0 0 512 142.222222z m0 71.111111a199.111111 199.111111 0 1 1 0 398.222223 199.111111 199.111111 0 0 1 0-398.222223z" fill="#ffffff" p-id="21362"></path></svg>
							 · ${user.address}
						</a></li>

						<li><a href="market" <c:if test="${flag==1}">class="active"</c:if>>商城首页</a></li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle <c:if test="${flag==2}">active</c:if>" data-toggle="dropdown">商品分类<b class="caret"></b></a>
							<ul class="dropdown-menu multi-column columns-2">
								<li>
									<div class="row">
										<div class="col-sm-12">
											<h4>商品分类</h4>
											<ul class="multi-column-dropdown">
												<c:forEach var="type" items="${typeList}">
													<li><a class="list" href="goods?typeid=${type.id}">${type.name}</a></li>
												</c:forEach>
											</ul>
										</div>	
									</div>
								</li>
							</ul>
						</li>
						<li><a href="top?typeid=1" <c:if test="${flag==6}">class="active"</c:if>>全部商品</a></li>
						<li><a href="top?typeid=2" <c:if test="${flag==7}">class="active"</c:if>>热销</a></li>
						<li><a href="top?typeid=3" <c:if test="${flag==8}">class="active"</c:if>>新品</a></li>
							<li><a href="order" <c:if test="${flag==3}">class="active"</c:if>>我的订单</a></li>
							<li><a href="my" <c:if test="${flag==4}">class="active"</c:if>>个人中心</a></li>
						<li><a href="myGoods" <c:if test="${flag==5}">class="active"</c:if>>我的商品</a></li>
							<li><a href="logout">退出</a></li>
						<li><a href="../admin.jsp" target="_blank">后台管理</a></li>
					</ul> 
					<!--/.navbar-collapse-->
				</div>
				<!--//navbar-header-->
			</nav>
			<div class="header-info">
				<div class="header-right search-box">
					<a href="javascript:;"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a>				
					<div class="search" style="position: absolute; right: 13.5%">
						<form class="navbar-form" action="search">
							<input type="text" class="form-control" name="name">
							<button type="submit" class="btn btn-default" aria-label="Left Align">搜索</button>
						</form>
					</div>	
				</div>
				<div class="header-right cart">
					<a href="cart">
						<span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"><span class="card_num">${order.amount==null ? '' :order.amount}</span></span>
					</a>
				</div>

				<div class="header-right login">
					<a href="my"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></a>
				</div>
				<div class="clearfix"> </div>
			</div>
			<div class="clearfix"> </div>
		</div>
	</div>
	<!--//header-->

</body>
</html>