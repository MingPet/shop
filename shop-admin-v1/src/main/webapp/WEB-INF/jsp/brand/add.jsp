<%--
  Created by IntelliJ IDEA.
  User: ming
  Date: 2021/2/28
  Time: 20:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>品牌增加</title>
    <!-- Bootstrap -->
    <link href="/js/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/js/fileinput5/css/fileinput.css" />
    <![endif]-->
</head>
<body>
<jsp:include page="/common/nav.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <form class="form-horizontal" action="/brand/addBrand.jhtml" method="post">
                <div class="form-group">
                    <label  class="col-sm-2 control-label">品牌名:</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="brandName" name="brandName" placeholder="请输入品牌名">
                    </div>
                </div>

                <div class="form-group" style="height: 400px;">
                    <label  class="col-sm-2 control-label">Logo:</label>
                    <div class="col-sm-4" style="height: 280px;">
                        <input type="file" id="logoFile" name="imageFile">
                        <input type="text" id="logo" name="logo">
                    </div>
                </div>


                <div style="text-align: center;">
                    <button type="submit" class="btn btn-primary">提交</button>
                    <button type="reset" class="btn btn-default">重置</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="/js/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="/js/bootstrap3/js/bootstrap.min.js"></script>
<script src="/js/fileinput5/js/fileinput.min.js"></script>
<script src="/js/fileinput5/js/locales/zh.js"></script>

<script>

    //onReady 初始化
    $(function () {
        initImage();
    })


    function initImage(){
        // 配置信息
        var setting = {
            language: 'zh',
            uploadUrl: "/ossfile/upload.jhtml", // 后台上传文件的url地址
            showUpload : true,
            showRemove : false
        };
        // 渲染组件
        $("#logoFile").fileinput(setting).on("fileuploaded", function (event, r, previewId, index) {
            console.log(r.response.data);
            $("#logo").val(r.response.data);
        });
    }
</script>
</body>
</html>
