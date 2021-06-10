
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>类型列表</title>
    <!-- Bootstrap -->
    <link href="/js/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/js/fileinput5/css/fileinput.css" />
    <![endif]-->
</head>
<body>
<jsp:include page="/common/common.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <%--查询条件--%>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">类型查询</h3>
                </div>
                <div class="panel-body">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">类型名:</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="typeName" placeholder="请输入规格名">
                            </div>
                        </div>
                        <div style="text-align: center;">
                            <button type="button" onclick="search()" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> 查询</button>
                            <button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-refresh"></span> 重置</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div>
        <button type="button" class="btn btn-primary" onclick="location.href='/type/toAdd.jhtml'"><span class="glyphicon glyphicon-plus"></span>增加</button>
        <button type="button" class="btn btn-primary" onclick="location.href='/type/toAddInfo.jhtml'"><span class="glyphicon glyphicon-plus"></span>rb增加</button>
        <button type="button" class="btn btn-danger" onclick="deleteBatch()"><span class="glyphicon glyphicon-remove"></span>批量删除</button>
    </div>
    <div class="row">
        <div class="col-md-12">
            <%--带有分页的表格--%>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">类型列表</h3>
                </div>
                <div class="panel-body">
                    <table id="typeTable" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                        <tr>
                            <th>选择</th>
                            <th>类型名</th>
                            <th>操作</th>
                        </tr>
                        </thead>

                        <tfoot>
                        <tr>
                            <th>选择</th>
                            <th>类型名</th>
                            <th>操作</th>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


<script>

    $(function () {
        initTable();
    });

    function editType(id) {
        location.href="/type/toEditType.jhtml?id="+id;
    }

    function search(){
        //获取参数值
        var v_typeName = $("#typeName").val();
        //传递参数
        var v_param = {};
        v_param.typeName = v_typeName;
        v_typeTable.settings()[0].ajax.data = v_param;
        v_typeTable.ajax.reload();
    }

    var v_typeTable;
    function initTable() {
        v_typeTable = $('#typeTable').DataTable({
            "language": {
                "url": "/js/DataTables/Chinese.json" // 汉化
            },
            // 是否允许检索
            "searching": false,
            "processing": true,
            "lengthMenu": [5,10,15,20],
            "serverSide": true,
            "ajax": {
                "url": "/type/findList.jhtml",
                "type": "POST"
            },
            "columns": [
                {  "data": "id",
                    "render": function (data, type, row, meta) {
                        return '<input type="checkbox" name="ids" value="'+data+'"/>';
                    }

                },
                { "data": "typeName" },
                { "data": "id",
                    "render": function (data, type, row, meta) {
                        return '<div class="btn-group" role="group" aria-label="...">'+
                            '<button type="button" onclick="editType(\''+data+'\')" class="btn btn-warning"><span class="glyphicon glyphicon-pencil"></span>修改</button>\n'+
                            '<button type="button" onclick="deleteType(\''+data+'\')" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>删除</button>\n'+
                            '</div>';
                    }
                }
            ]
        });
    }


    //删除
    function deleteType(id) {
        bootbox.confirm({
            title:"删除类型",
            message:"您确定要删除吗？",
            callback:function(result){
                if(result){

                    $.post({
                        url:"<%=request.getContextPath()%>/type/delete.jhtml",
                        data:{"id":id},
                        complete:function(aa,bb){
                           // console.log(aa);
                           // console.log(bb);
                            var res =  aa.getResponseHeader("LL-Session-Timeout");
                            //console.log(res);
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
        })



        if(v_idArr.length > 0){
            bootbox.confirm({
                size:"large",
                title:"删除",
                message:"您确定要删除"+v_idArr.length+"条数据吗？",
                callback:function(result){
                    if(result){
                        v_idArr.join(",");
                        $.ajax({
                            type:"post",
                            url:"<%=request.getContextPath()%>/type/deleteBatch.jhtml",
                            data:{"ids":v_idArr},
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





    //斐波

        var n = 5;
        var last = 1;
        var last2 = 0;
        var current = last2;
        for(var i=1;i<=n;i++){
            last2 = last;
            last = current;
            current = last + last2;
            console.log(current);
        }
        //return current;


    // function fibonacci (n) {
    //     if(n==0) return 0;
    //     else if(n==1) return 1;
    //     else return fibonacci(n-1) + fibonacci(n-2);
    // }
    // console.log(fibonacci(5));

</script>
</body>
</html>
