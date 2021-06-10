<%--
  Created by IntelliJ IDEA.
  User: ming
  Date: 2021/3/7
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>列表展示</title>
    <!-- Bootstrap -->
    <link href="/js/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <![endif]-->
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-md-12">

            <%--条件查询--%>
            <div class="panel panel-default"  style="border-color: #008080">
                <div class="panel-heading" style="background-color: #008080">
                    <h3 class="panel-title" ><font color="#f0f8ff">条件查询</font></h3>
                </div>
                <div class="panel-body">
                    <form class="form-horizontal">
                        <div class="form-group" >
                            <label  class="col-sm-2 control-label">品牌名:</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="brandName" placeholder="请输入品牌名">
                            </div>
                        </div>
                        <div style="text-align: center;">
                            <button type="button" class="btn btn-primary" onclick="search()"><span class="glyphicon glyphicon-search"></span> 查询</button>
                            <button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-refresh"></span> 重置</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>




    <div class="row">
        <div class="col-md-12" >
            <%--列表展示--%>
            <div class="panel panel-default"  style="border-color: #008080">
                <div class="panel-heading" style="background-color: #008080">
                    <h3 class="panel-title" ><font color="#f0f8ff">列表展示</font></h3>
                </div>
                <div class="panel-body">
                    <table id="logTable" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                        <tr>
                            <th>选择</th>
                            <th>用户名</th>
                            <th>真实姓名</th>
                            <th>时间</th>
                            <th>操作信息</th>
                            <th>详情</th>
                        </tr>
                        </thead>

                        <tfoot>
                        <tr>
                            <th>选择</th>
                            <th>用户名</th>
                            <th>真实姓名</th>
                            <th>时间</th>
                            <th>操作信息</th>
                            <th>详情</th>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </div>

</div>





<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="/js/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="/js/bootstrap3/js/bootstrap.min.js"></script>
<script src="/js/bootbox/bootbox.min.js"></script>
<script src="/js/bootbox/bootbox.locales.min.js"></script>
<script src="/js/fileinput5/js/fileinput.min.js"></script>
<script src="/js/fileinput5/js/locales/zh.js"></script>
<script src="/js/DataTables/DataTables-1.10.20/js/jquery.dataTables.min.js"></script>
<script src="/js/DataTables/DataTables-1.10.20/js/dataTables.bootstrap.min.js"></script>
<script>

    //初始化datatable
    $(function(){
        initTable();

    });
    var v_logTable = "";
    function initTable() {
       v_logTable = $('#logTable').DataTable({
            "language": {
                "url": "/js/DataTables/Chinese.json" // 汉化
            },
            // 是否允许检索
            "searching": false,
            "processing": true,
            "lengthMenu": [5,10,15,20],//每页展示条数
            "serverSide": true,
            "ajax": {
                "url": "/log/queryList.jhtml",//url映射地址
                "type": "POST"
            },
            "columns": [
                {  "data": "id",
                    "render": function (data, type, row, meta) {
                        return '<input type="checkbox" name="ids" value="'+data+'"/>';
                    }

                },
                { "data": "userName" },
                { "data": "realName" },
                { "data": "insertTime" },
                { "data": "info" },
                { "data": "paramInfo" },
                { "data": "id",

                    "render":function(data,type,row,meta){


                    }
                }
            ]
        });

    }





    //条件查询
    function search(){
        //获取参数值
        var v_brandName = $("#brandName").val();
        //传递参数
        var v_param = {};
        v_param.brandName = v_brandName;
        v_logTable.settings()[0].ajax.data = v_param;
        v_logTable.ajax.reload();
    }





</script>
</body>
</html>
