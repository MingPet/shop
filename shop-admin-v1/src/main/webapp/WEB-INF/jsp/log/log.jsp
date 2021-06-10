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
    <title>日志列表</title>
    <!-- Bootstrap -->
    <link href="/js/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="/js/DataTables/DataTables-1.10.20/css/dataTables.bootstrap.min.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/js/fileinput5/css/fileinput.css" />
    <link rel="stylesheet"
          href="/js/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css">
    <![endif]-->
</head>
<body>

<jsp:include page="/common/nav.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <%--查询条件--%>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">日志查询</h3>
                </div>
                <div class="panel-body">
                    <form class="form-horizontal" id="logForm">
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">登录名:</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="userName" name="userName" placeholder="请输入登录名">
                            </div>
                            <label  class="col-sm-2 control-label">真实姓名:</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="realName" name="realName" placeholder="请输入真实姓名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">操作信息:</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="info" name="info" placeholder="请输入操作信息">
                            </div>
                            <label  class="col-sm-2 control-label">操作时间:</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="minDate" name="minDate" placeholder="请输入开始时间">
                                    <span class="input-group-addon" id="sizing-addon1">=</span>
                                    <input type="text" class="form-control" id="maxDate" name="maxDate" placeholder="请输入结束时间">
                                </div>
                            </div>
                        </div>
                        <div style="text-align: center;">
                            <button type="button" class="btn btn-primary" onclick="search();"><span class="glyphicon glyphicon-search"></span> 查询</button>
                            <button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-refresh"></span> 重置</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div>
        <button type="button" class="btn btn-success" onclick="exportExcel();"><span class="glyphicon glyphicon-download-alt"></span>导出Excel</button>
        <button type="button" class="btn btn-success" onclick="exportPdf();"><span class="glyphicon glyphicon-download-alt"></span>导出Pdf</button>
        <button type="button" class="btn btn-success" onclick="exportWord();"><span class="glyphicon glyphicon-download-alt"></span>导出Word</button>

        <br><br>
    </div>
    <div class="row">
        <div class="col-md-12">
            <%--带有分页的表格--%>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">日志列表</h3>
                </div>
                <div class="panel-body">
                    <table id="logTable" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                        <tr>
                            <th>选择</th>
                            <th>用户名</th>
                            <th>真实姓名</th>
                            <th>操作信息</th>
                            <th>时间</th>
                            <th>详情</th>
                        </tr>
                        </thead>

                        <tfoot>
                        <tr>
                            <th>选择</th>
                            <th>用户名</th>
                            <th>真实姓名</th>
                            <th>操作信息</th>
                            <th>时间</th>
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
<script src="/js/DataTables/DataTables-1.10.20/js/jquery.dataTables.min.js"></script>
<script src="/js/DataTables/DataTables-1.10.20/js/dataTables.bootstrap.min.js"></script>
<script src="/js/bootbox/bootbox.min.js"></script>
<script src="/js/bootbox/bootbox.locales.min.js"></script>
<script src="/js/fileinput5/js/fileinput.min.js"></script>
<script src="/js/fileinput5/js/locales/zh.js"></script>
<script src="/js/bootstrap-datetimepicker/js/moment-with-locales.js"></script>
<script src="/js/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script>
    $(function () {
        initDate();
        initTable();
    });

    //导出Excel
    function exportExcel() {
        var v_logForm = document.getElementById("logForm");
        v_logForm.action="/log/exportExcel.jhtml";//url信息
        v_logForm.method="post";//提交方式
        v_logForm.submit();//提交方法
    }
    //导出Pdf
    function exportPdf() {
        var v_logForm = document.getElementById("logForm");
        v_logForm.action="/log/exportPdf.jhtml";
        v_logForm.method="post";
        v_logForm.submit();
    }

    //导出Word
    function exportWord() {
        var v_logForm = document.getElementById("logForm");
        v_logForm.action="/log/exportWord.jhtml";
        v_logForm.method="post";
        v_logForm.submit();
    }

    //日期格式化
    function initDate() {
        $('#minDate').datetimepicker({
            format: 'YYYY-MM-DD HH:mm',
            locale: 'zh-CN',
            showClear: true
        });

        $('#maxDate').datetimepicker({
            format: 'YYYY-MM-DD HH:mm',
            locale: 'zh-CN',
            showClear: true
        });
    }

    //展示
    var v_logTable;
    function initTable() {
        v_logTable = $('#logTable').DataTable({
            "language": {
                "url": "/js/DataTables/Chinese.json" // 汉化
            },
            // 是否允许检索
            "searching": false,
            "processing": true,
            "lengthMenu": [5,10,15,20],
            "serverSide": true,
            "ajax": {
                "url": "/log/findList.jhtml",
                "type": "POST"
            },
            "columns": [
                {  "data": "id",
                    "render": function (data, type, row, meta) {
                        console.log(data);
                        return '<input type="checkbox" name="ids" value="'+data+'"/>';
                    }

                },
                { "data": "userName" },
                { "data": "realName" },
                { "data": "info" },
                { "data": "insertTime" },
                { "data": "paramInfo",
                    "render": function (data, type, row, meta) {
                        return '<button type="button" class="btn btn-primary" onclick="showParamDlg(\''+data+'\');"><span class="glyphicon glyphicon-search"></span>详情</button>';
                    }
                }
            ]
        });
    }

    function showParamDlg(data) {
        bootbox.alert({
            message: "<span class='glyphicon glyphicon-exclamation-sign'></span>"+data,
            size: 'large',
            title: "提示信息"
        });
    }

    //条件查询
    function search() {
        var param = {};
        param.userName=$("#userName").val();
        param.realName=$("#realName").val();
        param.info=$("#info").val();
        param.minDate=$("#minDate").val();
        param.maxDate=$("#maxDate").val();

        v_logTable.settings()[0].ajax.data = param;
        v_logTable.ajax.reload();
    }
</script>
</body>
</html>