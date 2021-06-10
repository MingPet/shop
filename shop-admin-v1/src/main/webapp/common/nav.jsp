<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <script>

      </script>
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">飞狐后台管理平台</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div  class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li ><a href="/user/toList.jhtml">用户管理 </a></li>
        <li ><a href="/brand/toList.jhtml">品牌管理 </a></li>
        <li ><a href="/log/index.jhtml">日志管理 </a></li>
        <li ><a href="/spec/toList.jhtml">规格管理 </a></li>
        <li ><a href="/type/toList.jhtml">类型管理 </a></li>
        <li ><a href="/cate/toList.jhtml">分类管理 </a></li>
        <li ><a href="/spu/toList.jhtml">商品管理 </a></li>

      </ul>
      <ul class="nav navbar-nav navbar-right">
        <a href="#">欢迎${user.realName}登录</a>
        <a href="/user/logout.jhtml">注销</a>
        今天是第${user.loginCount}次登录&nbsp;&nbsp;&nbsp;</br>
        上次登录时间为<f:formatDate value="${user.loginLastTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
          </br>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>



<%--<%@ page language="java" contentType="text/html; charset=UTF-8"--%>
         <%--pageEncoding="UTF-8"%>--%>
<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
  <%--<meta charset="UTF-8">--%>
  <%--<title>Insert title here</title>--%>
  <%--<jsp:include page="/common/script.jsp"></jsp:include>--%>
<%--</head>--%>
<%--<body>--%>
<%--<nav class="navbar navbar-inverse">--%>
  <%--<div class="container-fluid">--%>
    <%--<!-- Brand and toggle get grouped for better mobile display -->--%>
    <%--<div class="navbar-header">--%>
      <%--<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">--%>
        <%--<span class="sr-only">Toggle navigation</span>--%>
        <%--<span class="icon-bar"></span>--%>
        <%--<span class="icon-bar"></span>--%>
        <%--<span class="icon-bar"></span>--%>
      <%--</button>--%>
      <%--<a class="navbar-brand" href="#">飞狐后台管理平台</a>--%>
    <%--</div>--%>

    <%--<!-- Collect the nav links, forms, and other content for toggling -->--%>
    <%--<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">--%>
      <%--<ul class="nav navbar-nav" id="item-ul">--%>
        <%--&lt;%&ndash;<li id="item1" class="active"><a href="/pre/movieList.jsp#1">商品管理</a></li>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<li id="item2" ><a href="/pre/typeList.jsp#2">分类管理</a></li>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<li id="item3"><a href="/pre/areaList.jsp#3">地区管理</a></li>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<li><a href="#">日志管理</a></li>&ndash;%&gt;--%>

          <%--<li ><a href="#">用户管理 </a></li>--%>
          <%--<li ><a href="/brand/toList.jhtml">品牌管理 </a></li>--%>
          <%--<li ><a href="/log/index.jhtml">日志管理 </a></li>--%>
          <%--<li ><a href="/spec/toList.jhtml">规格管理 </a></li>--%>
          <%--<li ><a href="/type/toList.jhtml">类型管理 </a></li>--%>
          <%--<li ><a href="/cate/toList.jhtml">分类管理 </a></li>--%>
        <%--&lt;%&ndash;<li class="dropdown">&ndash;%&gt;--%>
          <%--&lt;%&ndash;<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">java课程 <span class="caret"></span></a>&ndash;%&gt;--%>
          <%--&lt;%&ndash;<ul class="dropdown-menu">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<li><a href="#">大数据</a></li>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<li><a href="#">java基础</a></li>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<li><a href="#">测试</a></li>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<li role="separator" class="divider"></li>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<li><a href="#">人工智能</a></li>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<li role="separator" class="divider"></li>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<li><a href="#">机器学习</a></li>&ndash;%&gt;--%>
          <%--&lt;%&ndash;</ul>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</li>&ndash;%&gt;--%>
      <%--</ul>--%>
      <%--<form class="navbar-form navbar-left">--%>
        <%--<div class="form-group">--%>
          <%--<input type="text" class="form-control" placeholder="Search">--%>
        <%--</div>--%>
        <%--<button type="submit" class="btn btn-default">Submit</button>--%>
      <%--</form>--%>
      <%--<ul class="nav navbar-nav navbar-right">--%>
        <%--<li><a href="#">退出</a></li>--%>

      <%--</ul>--%>
    <%--</div><!-- /.navbar-collapse -->--%>
  <%--</div><!-- /.container-fluid -->--%>
<%--</nav>--%>

<%--</body>--%>
<%--<script type="text/javascript">--%>
    <%--$(function(){--%>
        <%--var url = window.location.hash;--%>
        <%--if(url.length>0){--%>
            <%--$("#item-ul >li").removeClass("active")--%>
            <%--var id = url.substring(1);--%>
            <%--$("#item"+id).addClass("active")--%>
        <%--}--%>

    <%--})--%>

<%--</script>--%>
<%--</html>--%>