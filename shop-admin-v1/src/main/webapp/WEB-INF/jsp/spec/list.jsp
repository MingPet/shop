<%--
  Created by IntelliJ IDEA.
  User: ming
  Date: 2021/3/10
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>品牌展示</title>
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

            <%--条件查询--%>
            <div class="panel panel-default"  style="border-color: #008080">
                <div class="panel-heading" style="background-color: #008080">
                    <h3 class="panel-title" ><font color="#f0f8ff">条件查询</font></h3>
                </div>
                <div class="panel-body">
                    <form class="form-horizontal">
                        <div class="form-group" >
                            <label  class="col-sm-2 control-label">规格名:</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="specName" placeholder="请输入规格名">
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


    <button type="button" style="background-color: #008080" class="btn btn-info" onclick="toAdd();"><span class="glyphicon glyphicon-plus"></span>增加</button>
    <button type="button" class="btn btn-danger" onclick="deleteBatch();"><span class="glyphicon glyphicon-trash"></span>批量删除</button>

    <br><br>


    <div class="row">
        <div class="col-md-12" >
            <%--列表展示--%>
            <div class="panel panel-default"  style="border-color: #008080">
                <div class="panel-heading" style="background-color: #008080">
                    <h3 class="panel-title" ><font color="#f0f8ff">列表展示</font></h3>
                </div>
                <div class="panel-body">
                    <table id="specTable" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                        <tr>
                            <th>选择</th>
                            <th>规格名</th>
                            <th>操作</th>
                        </tr>
                        </thead>

                        <tfoot>
                        <tr>
                            <th>选择</th>
                            <th>规格名</th>
                            <th>操作</th>
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




    $(function () {
        initSpecTable();
    });

    function toAdd(){
        location.href="/spec/toAdd.jhtml";
    }

    function search(){
        //获取参数值
        var v_specName = $("#specName").val();
        //传递参数
        var v_param = {};
        v_param.specName = v_specName;
        v_specTable.settings()[0].ajax.data = v_param;
        v_specTable.ajax.reload();
    }

    // 查询
    var v_specTable;
    function initSpecTable() {
        v_specTable = $('#specTable').DataTable({
            "language": {
                "url": "/js/DataTables/Chinese.json" // 汉化
            },
            // 是否允许检索
            "searching": false,
            "processing": true,
            "lengthMenu": [5,10,15,20],
            "serverSide": true,
            "ajax": {
                "url": "/spec/findList.jhtml",
                "type": "POST"
            },
            "columns": [
                {  "data": "id",
                    "render": function (data, type, row, meta) {
                        return '<input type="checkbox" name="ids" value="'+data+'"/>';
                    }

                },
                { "data": "specName" },
                { "data": "id",
                    "render": function (data, type, row, meta) {
                        return '<div class="btn-group" role="group" aria-label="...">'+
                            '<button type="button" onclick="updateSpec(\''+data+'\')" class="btn btn-warning"><span class="glyphicon glyphicon-pencil"></span>修改</button>\n'+
                            '<button type="button" onclick="deleteSpec(\''+data+'\')" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>删除</button>\n'+
                            '</div>';
                    }
                }
            ]
        });
    }

    //修改跳转
    function updateSpec(id){
        location.href="/spec/toUpdate.jhtml?id="+id;
    }

    //删除
    function deleteSpec(id) {
        bootbox.confirm({
            title:"删除规格",
            message:"您确定要删除吗？",
            callback:function(result){
                if(result){

                    $.post({
                        url:"<%=request.getContextPath()%>/spec/delete.jhtml",
                        data:{"id":id},
                        complete:function(aa,bb){
                            console.log(aa);
                            console.log(bb);
                            var res =  aa.getResponseHeader("LL-Session-Timeout");
                            console.log(res);
                            if(res && res == "sessionTimeout"){//如果存在 并且==自定义的值
                                //跳转到登录页面
                                window.location.href = "/index.jsp";

                            }
                        },
                        success:function(result){
                            if(result.code==200){
                                bootbox.alert("删除成功");
                                search();
                            }else if(result.code==100){
                                bootbox.alert("删除失败");
                            }
                        },
                        error:function(){
                            bootbox.alert("删除异常");
                        }
                    })
                }
            },
            buttons:{
                confirm:{
                    label:"确认",
                    className:"btn-success"
                },
                cancel:{
                    label:"取消",
                    className:"btn-danger"
                }
            }
        })
    }

    //批量删除
    function deleteBatch(){
        //获取被选中的复选框的id集合
        var v_idArr = [];
        $("input[name='ids']:checkbox:checked").each(function () {
            console.log(this.value);
            v_idArr.push(this.value);
        });



        if(v_idArr.length > 0){
            bootbox.confirm({
                size:"large",
                title:"删除",
                message:"您确定要删除"+v_idArr.length+"条数据吗？",
                callback:function(result){
                    if(result){
                        var ids = v_idArr.join(",");
                        $.ajax({
                            type:"post",
                            url:"<%=request.getContextPath()%>/spec/deleteBatch.jhtml",
                            data:{"ids":ids},
                            traditional:true,
                            success:function(result){
                                if(result.code == 200){
                                    bootbox.alert("删除成功");
                                    search();
                                }else{
                                    bootbox.alert("删除失败");
                                }
                            }
                        })
                    }
                },
                buttons:{
                    confirm:{
                        label:"确认",
                        className:"btn-danger"
                    },
                    cancel:{
                        label:"取消",
                        className:"btn-success"
                    }
                }
            })

        }else{
            bootbox.alert("请选择要删除的数据！");
        }
    }
</script>
</body>
</html>
