<%--
  Created by IntelliJ IDEA.
  User: ming
  Date: 2021/2/28
  Time: 21:53
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


    <button type="button" style="background-color: #008080" class="btn btn-info" onclick="toAdd();"><span class="glyphicon glyphicon-plus"></span>增加</button>
    <button type="button" style="background-color: #008080" class="btn btn-info" onclick="toAddB();"><span class="glyphicon glyphicon-plus"></span>弹框增加</button>
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
                    <table id="brandTable" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                        <tr>
                            <th>选择</th>
                            <th>品牌名</th>
                            <th>logo</th>
                            <th>操作</th>
                        </tr>
                        </thead>

                        <tfoot>
                        <tr>
                            <th>选择</th>
                            <th>品牌名</th>
                            <th>logo</th>
                            <th>操作</th>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </div>

</div>



<%--单页面弹框新增--%>
<div id="addDiv" style="display:none;">
    <form id="addForm" class="form-horizontal" style="">
        <div class="form-group">
            <label  class="col-sm-2 control-label">品牌名</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="addBrandName" />
                <input type="hidden" class="form-control" id="id" />
            </div>
        </div>


        <div class="form-group"style="height:400px;">
            <label class="col-sm-2 control-label">logo</label>
            <div class="col-sm-4" style="height: 260px;">
                <input type="file" class="form-control" id="addLogoFile" name="imageFile"/>
                <input type="text" class="form-control" id="addLogo" />
            </div>
        </div>
    </form>
</div>

<%--单页面弹框修改--%>
<div id="updateDiv" style="display:none;">
    <form id="updateForm" class="form-horizontal" style="">
        <div class="form-group">
            <label  class="col-sm-2 control-label">品牌名</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="updateBrandName" />
                <input type="hidden" class="form-control" id="brandId" />
            </div>
        </div>


        <div class="form-group"style="height:400px;">
            <label class="col-sm-2 control-label">logo</label>
            <div class="col-sm-4" style="height: 260px;">
                <input type="file" class="form-control" id="updateLogoFile" name="imageFile"/>
                <input type="text" class="form-control" id="updateLogo" />
                <input type="text" class="form-control" id="oldLogo" />
            </div>
        </div>
    </form>
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
        initBrandTable();

    });


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
                            url:"<%=request.getContextPath()%>/brand/deleteBatch.jhtml",
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


    //修改2

    function updateBrand(id) {
        $.ajax({
            type:"get",
            url:"/brand/findBrandById.jhtml",
            data:{"id":id},
            success:function (result) {
                if (result.code == 200) {
                    console.log(result);
                    var v_brand = result.data;
                    // 回填数据
                    $("#updateBrandName").val(v_brand.brandName);
                    var imageArr = [];
                    imageArr.push(v_brand.logo);
                    $("#oldLogo").val(v_brand.logo);
                    // 备份
                    var v_updateBrandDivHtml = $("#updateDiv").html();
                    // 渲染
                    // 配置信息
                    var setting = {
                        language: 'zh',
                        uploadUrl: "/file/uploadImage.jhtml", // 后台上传文件的url地址
                        showUpload : true,
                        showRemove : false,
                        initialPreview:imageArr,
                        initialPreviewAsData: true
                    };
                    // 渲染组件
                    $("#updateLogoFile").fileinput(setting).on("fileuploaded", function (event, r, previewId, index) {
                        console.log(r.response.data);
                        $("#updateLogo", v_findBrandById).val(r.response.data);
                    });


                    // 弹框
                    var v_findBrandById = bootbox.dialog({
                        title: '修改品牌',
                        message:$("#updateDiv form"),
                        size:"large",
                        buttons: {
                            confirm: {

                                label:"确认",
                                className:"btn-success",
                                callback: function(){
                                    // 更新的操作
                                    //获取参数
                                    var v_brandName = $("#updateBrandName", v_findBrandById).val();
                                    var v_logo = $("#updateLogo", v_findBrandById).val();
                                    var v_old_logo = $("#oldLogo", v_findBrandById).val();
                                    var param = {};

                                    param.id = v_brand.id;
                                    param.brandName = v_brandName;
                                    param.logo = v_logo;
                                    param.oldLogo = v_old_logo;

                                    console.log(param);
                                    $.ajax({
                                        type:"post",
                                        url:"/brand/update.jhtml",
                                        data:param,
                                        success:function (result) {
                                            if (result.code == 200) {
                                                // 刷新
                                                search();
                                            }
                                        }
                                    })

                                }
                            },
                            cancel: {
                                label:"取消",
                                className:"btn-danger"
                            }
                        }
                    });
                    // 还原
                    $("#updateDiv").html(v_updateBrandDivHtml);
                }
            }
        })
    }


    //修改
    <%--function updateBrand(id) {--%>
        <%--$.ajax({--%>
            <%--type:"get",--%>
            <%--url:"/brand/findBrandById.jhtml",--%>
            <%--data:{"id":id},--%>
            <%--success:function(result){--%>
                <%--//成功回调函数--%>
                <%--if(result){--%>
                    <%--console.log(result);--%>
                    <%--var v_brand = result.data;--%>

                    <%--//回显数据--%>

                    <%--$("#updateBrandName").val(v_brand.brandName);--%>
                    <%--var imageArr = [];--%>
                    <%--imageArr.push(v_brand.logo);--%>

                    <%--$("#oldLogo").val(v_brand.logo);--%>
                    <%--//备份--%>
                    <%--var v_updateBrandDivHtml = $("#updateDiv").html();--%>
                    <%--//渲染--%>
                    <%--// 配置信息--%>

                    <%--var setting = {--%>
                        <%--language: 'zh',--%>
                        <%--uploadUrl: "/file/uploadImage.jhtml", // 后台上传文件的url地址--%>
                        <%--showUpload : true,--%>
                        <%--showRemove : false,--%>
                        <%--initialPreview:imageArr,--%>
                        <%--initialPreviewAsData:true--%>
                    <%--};--%>
                    <%--// 渲染组件--%>
                    <%--$("#updateLogoFile").fileinput(setting).on("fileuploaded", function (event, r, previewId, index) {--%>
                        <%--//console.log(r.response.data);--%>
                        <%--$("#updateLogo",v_findBrandById).val(r.response.data);--%>
                    <%--});--%>


                    <%--var v_findBrandById = bootbox.confirm({--%>
                        <%--size:"large",--%>
                        <%--title:"修改品牌",--%>
                        <%--message:$("#updateDiv form"),--%>
                        <%--callback:function(){--%>

                                <%--//更新--%>
                                <%--//获取参数--%>
                                <%--var v_brandName = $("#updateBrandName",v_findBrandById).val();--%>
                                <%--var v_logo = $("#updateLogo",v_findBrandById).val();--%>
                                <%--var v_oldLogo = $("#oldLogo",v_findBrandById).val();--%>

                                <%--var param={};--%>
                                <%--param.brandName = v_brandName;--%>
                                <%--param.logo = v_logo;--%>
                                <%--param.oldLogo = v_oldLogo;--%>
                                <%--param.id = v_brand.id;--%>
                                <%--console.log(param);--%>

                                <%--$.post({--%>
                                    <%--url:"<%=request.getContextPath()%>/brand/update.jhtml",--%>
                                    <%--data:param,--%>
                                    <%--success:function(result){--%>
                                        <%--if(result.code==200){--%>
                                            <%--search();--%>
                                        <%--}else {--%>
                                            <%--bootbox.alert("修改失败");--%>
                                        <%--}--%>
                                    <%--}--%>

                                <%--})--%>

                            <%--//$("#updateDiv").html(v_updateBrandDivHtml);--%>
                        <%--},--%>
                        <%--buttons:{--%>
                            <%--confirm:{--%>
                            //     label:"确认",
                            //     className:"btn-success"
                            <%--},--%>
                            <%--cancel:{--%>
                            //     label:"取消",
                            //     className:"btn-danger"
                            <%--}--%>
                        <%--}--%>
                    <%--})--%>
                    <%--//还原--%>
                    <%--$("#updateDiv").html(v_updateBrandDivHtml);--%>
                <%--}--%>
            <%--}--%>
        <%--});--%>
    <%--}--%>




    //弹框增加
    function toAddB() {


        //备份
        var v_addDivHtml = $("#addDiv").html();
        // 配置信息
        var setting = {
            language: 'zh',
            uploadUrl: "/file/uploadOss.jhtml", // 后台上传文件的url地址
            showUpload : true,
            showRemove : false
        };
        // 渲染组件
        $("#addLogoFile").fileinput(setting).on("fileuploaded", function (event, r, previewId, index) {
            console.log(r.response.data);
            $("#addLogo",v_toAddB).val(r.response.data);
        });


        var v_toAddB = bootbox.confirm({
            size:"large",
            title:"新增品牌",
            message:$("#addDiv form"),
            callback:function(result){
                if(result){
                    var param = {};
                    // 信息
                    param.brandName = $("#addBrandName",v_toAddB).val();
                    param.logo = $("#addLogo",v_toAddB).val();
                    //动态下拉

                    /*//复选框
                    var arr = [];
                    $("[name=person]:checked").each(function(){
                        arr.push(this.value);
                    })
                    param.cloSeason = arr.toString(); */

                    $.post({
                        url:"<%=request.getContextPath()%>/brand/addBrandB.jhtml",
                        data:param,
                        success:function(result){
                            if(result.code==200){
                                bootbox.alert("添加成功");
                                search();
                            }else{
                                bootbox.alert("添加失败");
                            }
                        }

                    })
                }
                $("#addDiv").html(v_addDivHtml);
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

        //还原
        $("#addDiv").html(v_addDivHtml);
    }

    //条件查询
    function search(){
        //获取参数值
        var v_brandName = $("#brandName").val();
        //传递参数
        var v_param = {};
        v_param.brandName = v_brandName;
        v_brandTable.settings()[0].ajax.data = v_param;
        v_brandTable.ajax.reload();
    }


    //删除
    function deleteBrand(id) {
        bootbox.confirm({
            title:"删除角色",
            message:"您确定要删除吗？",
            callback:function(result){
                if(result){

                    $.post({
                        url:"<%=request.getContextPath()%>/brand/delete.jhtml",
                        data:{"id":id},

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

    //新增跳转
    function toAdd() {
        window.location.href="/brand/toAdd.jhtml";
    }


    var  v_brandTable;
    function initBrandTable() {
       v_brandTable = $('#brandTable').DataTable({
            "language": {
                "url": "/js/DataTables/Chinese.json" // 汉化
            },
            // 是否允许检索
            "searching": false,
            "processing": true,
            "lengthMenu": [5,10,15,20],//每页展示条数
            "serverSide": true,
            "ajax": {
                "url": "/brand/findList.jhtml",//url映射地址
                "type": "POST"
            },
            "columns": [
                {  "data": "id",
                    "render": function (data, type, row, meta) {
                        return '<input type="checkbox" name="ids" value="'+data+'"/>';
                    }

                },
                { "data": "brandName" },
                { "data": "logo",
                    "render": function (data, type, row, meta) {
                        return '<img src="'+data+'" width="50px" height="50px"/>';
                    }
                },
                { "data": "id",

                    "render":function(data,type,row,meta){

                        return '<div class="btn-group" role="group">\n'+
                            '<button type="button" onclick="updateBrand(' + data + ')" class="btn btn-primary">'+
                            '<span class="glyphicon glyphicon-pencil"></span>&nbsp;修改'+
                            '</button>'+
                            '<button type="button" onclick="deleteBrand(' + data + ')" class="btn btn-danger">'+
                            '<span class="glyphicon glyphicon-trash"></span>&nbsp;删除'+
                            '</button>'+

                            '</div>';
                    }
                }
            ]
        });

    }
</script>
</body>
</html>
