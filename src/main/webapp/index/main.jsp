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

<jsp:include page="header0.jsp"/>

<script>
    //注意：导航 依赖 element 模块，否则无法进行功能性操作
    layui.use('element', function(){
        var element = layui.element;

        //…
    });
</script>

<!--banner-->

    <div class="banner">
        <div class="container">
            <h2 class="hdng" style="text-align: center;font-size: 90px"><a >L E L E ，让二手交易更简单</a></h2>
            <p style="text-align: center;font-size:50px;margin-bottom: 80px">每日社区精选，发现价值，享受便捷。</p>
            <a class="banner_a" href="login?flag=-1" <c:if test="${flag==6}">class="active"</c:if> style="margin-left: 60%;font-size: 35px">立即登录</a>
       </div>
    </div>

<!--//banner-->

<div class="subscribe2"></div>

<!--gallery-->
<div class="banner" style="padding-top: 50px;">
    <div class="container"><p style="text-align: center;font-size:50px;margin-bottom: 50px">
        LELE为每一件旧物赋予新生命，二手交易不仅是环保，更是让生活更加美好的选择。
    </p>
        <h2 class="hdng" style="width:60%;margin-left:40px;font-size: 30px;line-height: 2;letter-spacing: 5px;">
            <a >-------------LELE专注于提供最贴心的服务和最安全的交易体验。我们确保每一笔交易都通过严格的审核，确保您交易的物品质量可靠，交易过程透明安全。---------------</a></h2>

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