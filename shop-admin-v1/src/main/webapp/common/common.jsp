  <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>



<%--实训一--%>
  <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
  <script src="/js/jquery.min.js"></script>
  <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
  <script src="/js/bootstrap3/js/bootstrap.min.js"></script>
  <script src="/js/bootbox/bootbox.min.js"></script>
  <script src="/js/bootbox/bootbox.locales.min.js"></script>
  <script src="/js/fileinput5/js/fileinput.js"></script>
  <script src="/js/fileinput5/js/locales/zh.js"></script>
  <script src="/js/DataTables/DataTables-1.10.20/js/jquery.dataTables.min.js"></script>
  <script src="/js/DataTables/DataTables-1.10.20/js/dataTables.bootstrap.min.js"></script>
  <script src="/js/treetable/jquery.treetable.js"></script>
  
<!-- 导航条 -->
  <jsp:include page="/common/nav.jsp"></jsp:include>
<script>

  $.ajaxSetup({
      complete:function(aa,bb) {
          //console.log(aa);
         // console.log(bb);
          var res = aa.getResponseHeader("LL-Session-Timeout");
          //console.log(res);
          if (res && res == "sessionTimeout") {//如果存在 并且==自定义的值
              //ajax超时 跳转到登录页面
              window.location.href = "/index.jsp";
          }

          var result = aa.responseJSON;

          if (result.code == -1) {
              bootbox.alert({
                  message:"<span class='glyphicon glyphicon-exclamation-sign'></span>系统异常！",
                  size:"small",
                  title:"提示信息"
              })

          }
      }
  })
</script>