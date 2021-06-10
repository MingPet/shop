<%--
  Created by IntelliJ IDEA.
  User: ming
  Date: 2021/3/18
  Time: 20:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户管理</title>
    <jsp:include page="/common/head.jsp"></jsp:include>
</head>
<body>

<jsp:include page="/common/common.jsp"></jsp:include>


            <%--条件查询--%>
                <div class="panel panel-primary" style="border-color: #008080">
                    <div class="panel-heading" style="background-color: #008080">
                        条件查询
                    </div>
                    <div class="panel-body">
                        <form class="form-horizontal" id="searchForm">
                            <div class="container">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">用户</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" name="userName" id="userName" placeholder="请输入用户名">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">真实姓名</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" name="realName" id="realName" placeholder="请输入真实姓名">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">

                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">性别</label>
                                                <div class="col-sm-10">
                                                    <label class="radio-inline">
                                                        <input type="radio" name="sex" value="1"/>男
                                                    </label>
                                                    <label class="radio-inline">
                                                        <input type="radio" name="sex" value="2"/>女
                                                    </label>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">生日</label>
                                            <div class="col-sm-10">
                                                <div class="input-group">
                                                    <input type="text" class="form-control initDate" id="minDate" name="minDate">
                                                    <span class="input-group-addon">--</span>
                                                    <input type="text" class="form-control initDate" id="maxDate" name="maxDate">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="firstname" class="col-sm-2 control-label">适合人群</label>
                                            <div class="col-sm-10">
                                                <label class="checkbox-inline">
                                                <input type="checkbox" name="sperson" value="1">少年
                                                </label>
                                                <label class="checkbox-inline">
                                                <input type="checkbox" name="sperson" value="2">青年
                                                </label>
                                                <label class="checkbox-inline">
                                                <input type="checkbox" name="sperson" value="3">中年
                                                </label>
                                                <label class="checkbox-inline">
                                                <input type="checkbox" name="sperson" value="4">老年
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="firstname" class="col-sm-2 control-label">是否上映</label>
                                            <div class="col-sm-10">
                                                <label class="radio-inline">
                                                <input type="radio" name="sisup" value="1"/>已上映
                                                </label>
                                                <label class="radio-inline">
                                                <input type="radio" name="sisup" value="2"/>未上映
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                 -->
                                <div class="row">
                                    <div style="text-align: center;">
                                        <button type="button" class="btn btn-primary" onclick="search()"><span class="glyphicon glyphicon-search"></span> 查询</button>
                                        <button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-refresh"></span> 重置</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>


    <button type="button" style="background-color: #008080" class="btn btn-info" onclick="toAdd();"><span class="glyphicon glyphicon-plus"></span>弹框增加</button>
    <button type="button" class="btn btn-danger" onclick="deleteBatch();"><span class="glyphicon glyphicon-trash"></span>批量删除</button>
    <button type="button"  class="btn btn-success" onclick="importExcel();"><span class="glyphicon glyphicon-upload"></span>导入Excel</button>

    <br><br>


    <div class="row">
        <div class="col-md-12" >
            <%--列表展示--%>
            <div class="panel panel-default"  style="border-color: #008080">
                <div class="panel-heading" style="background-color: #008080">
                    <h3 class="panel-title" ><font color="#f0f8ff">列表展示</font></h3>
                </div>
                <div class="panel-body">
                    <table id="userTable" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                        <tr>
                            <th>选择</th>
                            <th>用户名</th>
                            <th>真实姓名</th>
                            <th>用户头像</th>
                            <th>性别</th>
                            <th>邮箱</th>
                            <th>生日</th>
                            <th>手机号</th>

                            <th>操作</th>
                        </tr>
                        </thead>

                        <tfoot>
                        <tr>
                            <th>选择</th>
                            <th>用户名</th>
                            <th>真实姓名</th>
                            <th>用户头像</th>
                            <th>性别</th>
                            <th>邮箱</th>
                            <th>生日</th>
                            <th>手机号</th>

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
            <label  class="col-sm-2 control-label">用户名</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="add_UserName" />
            </div>
            <label  class="col-sm-2 control-label">真实名</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="add_realName" />
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">密码</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="add_password" />
            </div>
            <label  class="col-sm-2 control-label">确认密码</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="add_confirmPassword" />
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">生日</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="add_birthDay" />
            </div>
            <label  class="col-sm-2 control-label">邮箱</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="add_mail" />
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">性别</label>
            <div class="col-sm-3">
                <label class="radio-inline">
                    <input type="radio" name="add_sex" value="1"/>男
                </label>
                <label class="radio-inline">
                    <input type="radio" name="add_sex" value="2"/>女
                </label>
            </div>
            <label  class="col-sm-2 control-label">电话</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="add_phone" />
            </div>
        </div>


        <%--<div class="form-group"style="height:400px;">--%>
            <%--<label class="col-sm-2 control-label">logo</label>--%>
            <%--<div class="col-sm-4" style="height: 260px;">--%>
                <%--<input type="file" class="form-control" id="addLogoFile" name="imageFile"/>--%>
                <%--<input type="text" class="form-control" id="addLogo" />--%>
            <%--</div>--%>
        <%--</div>--%>
    </form>
</div>
<%--单页面弹框修改--%>
<div id="updateDiv" style="display:none;">
    <form id="updateForm" class="form-horizontal" style="">
        <div class="form-group">
            <label  class="col-sm-2 control-label">用户名</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="update_UserName" />
            </div>
            <label  class="col-sm-2 control-label">真实名</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="update_realName" />
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">密码</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="update_password" />
            </div>
            <label  class="col-sm-2 control-label">确认密码</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="update_confirmPassword" />
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">生日</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="update_birthDay" />
            </div>
            <label  class="col-sm-2 control-label">邮箱</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="update_mail" />
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">性别</label>
            <div class="col-sm-3">
                <label class="radio-inline">
                    <input type="radio" name="update_sex" value="1"/>男
                </label>
                <label class="radio-inline">
                    <input type="radio" name="update_sex" value="2"/>女
                </label>
            </div>
            <label  class="col-sm-2 control-label">电话</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="update_phone" />
            </div>
        </div>


        <%--<div class="form-group"style="height:400px;">--%>
        <%--<label class="col-sm-2 control-label">logo</label>--%>
        <%--<div class="col-sm-4" style="height: 260px;">--%>
        <%--<input type="file" class="form-control" id="addLogoFile" name="imageFile"/>--%>
        <%--<input type="text" class="form-control" id="addLogo" />--%>
        <%--</div>--%>
        <%--</div>--%>
    </form>
</div>
<%--导入excel--%>
<div id="importExcelDiv" style="display:none;">
    <form  class="form-horizontal" style="">


        <div class="form-group"style="height:400px;">
            <label class="col-sm-2 control-label">请选择要导入的Excel文件</label>
            <div class="col-sm-4" style="height: 260px;">
                <input type="file" class="form-control" id="addExcelFile" name="excelFile"/>
                <input type="text" class="form-control" id="addExcel" />
            </div>
        </div>e
    </form>
</div>


<script>

    //初始化datatable
    $(function(){
        initUserTable();

    });

    //条件查询
    function search(){
        //获取参数值
        //传递参数
        var v_param = {};
        v_param.userName = $("#userName").val();
        v_param.realName = $("#realName").val();
        v_param.minDate = $("#minDate").val();
        v_param.maxDate = $("#maxDate").val();
        v_param.sex = $("input[name='sex']:radio:checked").val();
        v_userTable.settings()[0].ajax.data = v_param;
        v_userTable.ajax.reload();
    }


    //修改
    function updateUser2(id) {
        $.ajax({
            type:"post",
            url:"/user/findUserById.jhtml",
            data:{"id":id},
            success:function (result) {
                if (result.code == 200) {
                    console.log(result);
                    var v_user = result.data;
                    // 回填数据
                    $("#update_userName").val(v_user.userName);
                    $("#update_realName").val(v_user.realName);
                   // $("#update_userName").val(v_user.userName);
                   // $("#update_userName").val(v_user.userName);
                    //var imageArr = [];
                   //imageArr.push(v_brand.logo);
                   // $("#oldLogo").val(v_brand.logo);
                    // 备份
                    var v_updateUserDivHtml = $("#updateDiv").html();
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
                        title: '修改用户',
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
                    $("#updateDiv").html(v_updateUserDivHtml);
                }
            }
        })
    }


    //展示
    var  v_userTable;
    function initUserTable() {
        v_userTable = $('#userTable').DataTable({
            "language": {
                "url": "/js/DataTables/Chinese.json" // 汉化
            },
            // 是否允许检索
            "searching": false,
            "processing": true,
            "lengthMenu": [5,10,15,20],//每页展示条数
            "serverSide": true,
            "ajax": {
                "url": "/user/findList.jhtml",//url映射地址
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
                { "data": "photo",
                    "render": function (data, type, row, meta) {
                    return '<img src="'+data+'" width="50px" height="50px"/>';
                }
                },
                {
                    "data":"sex",
                    "render":function(data){
                        return data==1?'男':data==2?'女':'保密';
                    }
                },
                { "data": "mail" },
                { "data": "birthDay" },
                { "data": "phone" },
                { "data": "id",

                    "render":function(data,type,row,meta){

                        return '<div class="btn-group" role="group">\n'+
                            '<button type="button" onclick="updateUser2(' + data + ')" class="btn btn-primary">'+
                            '<span class="glyphicon glyphicon-pencil"></span>&nbsp;修改'+
                            '</button>'+
                            '<button type="button" onclick="deleteUser(' + data + ')" class="btn btn-danger">'+
                            '<span class="glyphicon glyphicon-trash"></span>&nbsp;删除'+
                            '</button>'+

                            '</div>';
                    }
                }
            ]
        });

    }


    //弹框增加
    function toAdd() {


        //备份
        var v_addDivHtml = $("#addDiv").html();
        // 配置信息
        var setting = {
            language: 'zh',
            uploadUrl: "/file/uploadImage.jhtml", // 后台上传文件的url地址
            showUpload : true,
            showRemove : false
        };
        // 渲染组件
        $("#addLogoFile").fileinput(setting).on("fileuploaded", function (event, r, previewId, index) {
            //console.log(r.response.data);
            $("#addLogo",v_toAdd).val(r.response.data);
        });


        var v_toAdd = bootbox.confirm({
            size:"large",
            title:"新增用户",
            message:$("#addDiv form"),
            callback:function(result){
                if(result){
                    var param = {};
                    // 信息
                    param.userName = $("#add_userName",v_toAdd).val();
                    param.realName = $("#add_realName",v_toAdd).val();
                    param.password= $("#add_password",v_toAdd).val();
                    param.confirmPassword = $("#add_confirmPassword",v_toAdd).val();
                    param.mail = $("#add_mail",v_toAdd).val();
                    param.birthDay = $("#add_birthDay",v_toAdd).val();
                    param.phone = $("#add_phone",v_toAdd).val();
                    param.sex = $("input[name='add_sex']:radio:checked",v_toAdd).val();
                    //动态下拉

                    /*//复选框
                    var arr = [];
                    $("[name=person]:checked").each(function(){
                        arr.push(this.value);
                    })
                    param.cloSeason = arr.toString(); */

                    $.post({
                        url:"<%=request.getContextPath()%>/user/addUser.jhtml",
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



    //删除
    function deleteUser(id) {
        bootbox.confirm({
            title:"删除用户",
            message:"您确定要删除吗？",
            callback:function(result){
                if(result){

                    $.post({
                        url:"<%=request.getContextPath()%>/user/delete.jhtml",
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
                            url:"<%=request.getContextPath()%>/user/deleteBatch.jhtml",
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

    //弹框导入Excel
    function importExcel() {


        //备份
        var v_excelHtml = $("#importExcelDiv").html();
        // 配置信息
        var setting = {
            language: 'zh',
            uploadUrl: "/file/uploadExcel.jhtml", // 后台上传文件的url地址
            showUpload : false,
            showRemove : false
        };
        // 渲染组件
        $("#addExcelFile").fileinput(setting).on("fileuploaded", function (event, r, previewId, index) {
            //console.log(r.response.data);
            $("#addExcel",v_ExcelDlg).val(r.response.data);
        });


        //弹框
        var v_ExcelDlg = bootbox.confirm({
            size:"large",
            title:"导入Excel",
            message:$("#importExcelDiv form"),
            callback:function(result){
                if(result){
                    var v_filePath = $("#addExcel",v_ExcelDlg).val();

                    $.post({
                        url:"<%=request.getContextPath()%>/user/importExcel.jhtml",
                        data:{"filePath":v_filePath},
                        success:function(result){
                            if(result.code==200){
                                //bootbox.alert("添加成功");
                                search();
                            }else{
                                bootbox.alert("导入失败");
                            }
                        }

                    })
                }
                $("#importExcelDiv").html(v_excelHtml);
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
        $("#importExcelDiv").html(v_excelHtml);
    }




















    // 初始化日期插件
    function initDate(){
        $(".initDate").datetimepicker({
            format:"YYYY-MM-DD ",
            locale:"zh-CN",
            showClear:true
        });
    }
</script>

</body>
</html>
