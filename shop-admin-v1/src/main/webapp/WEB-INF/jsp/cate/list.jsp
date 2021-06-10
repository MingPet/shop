<%--
  Created by IntelliJ IDEA.
  User: ming
  Date: 2021/3/16
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>分类管理</title>
    <jsp:include page="/common/head.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/common/common.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-12" id="cateDiv">
            <%--分类列表--%>
                <table id="cateTable" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th>分类名</th>
                        <th>类型名</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>

                </table>
        </div>
    </div>
</div>

<%--单页面弹框增加子级--%>
<div id="addDiv" style="display:none;">
    <form id="cateForm" class="form-horizontal" style="">
        <div class="form-group">
            <label  class="col-sm-2 control-label">分类名</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="addcateName" placeholder="请输入分类名"/>
            </div>
        </div>




        <div class="form-group">
            <label class="col-sm-2 control-label">类型</label>
            <div class="col-sm-4" id = "addTypeDiv">


            </div>
        </div>


        <div class="form-group">
            <label  class="col-sm-2 control-label">上级分类</label>
            <div class="col-sm-4" id = "addSelectDiv">

            </div>
        </div>

    </form>
</div>

<%--单页面弹框修改--%>
<div id="updateDiv" style="display:none;">
    <form  class="form-horizontal" style="">
        <div class="form-group">
            <label  class="col-sm-2 control-label">分类名</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="updateCateName" placeholder="请输入分类名"/>
            </div>
        </div>




        <div class="form-group">
            <label class="col-sm-2 control-label">类型</label>
            <div class="col-sm-4" id = "updateTypeDiv">


            </div>
        </div>


        <div class="form-group">
            <label  class="col-sm-2 control-label">上级分类</label>
            <div class="col-sm-4" id = "updateSelectDiv">

            </div>
        </div>

    </form>
</div>


<script>
    var v_html;
    $(function () {
        //备份
        v_html = $("#cateDiv").html();
        initTreeTable();
    });

    //增加子级
    function addChilds(cateId) {
        //备份
        var v_addDivHtml = $("#addDiv").html();
        //渲染
        $.ajax({
            url:"<%=request.getContextPath()%>/type/findAllTypeList.jhtml",
            type:"post",
            //data:param,
            success:function(result){
                if(result.code==200){
                    var v_typeList = result.data;
                   

                    //拼接单选 并回填
                    buildTypeRadio("addTypeDiv",v_typeList);
                    //拼接下拉 并回填
                    buildCateSelect("addSelect","addSelectDiv",cateId);
                    


                    var v_cateDlg = bootbox.dialog({
                        title: '增加分类',
                        message:$("#addDiv form"),
                        size:"large",
                        buttons: {
                            confirm: {
                                label: '<span class="glyphicon glyphicon-ok"></span>确认',
                                className: 'btn-primary',
                                callback: function(){
                                    //获取信息
                                    var v_param = {};
                                    v_param.cateName = $("#addcateName",v_cateDlg).val();
                                    v_param.pid = cateId;
                                    v_param.typeId = $("input[name='typeIds']:radio:checked",v_cateDlg).val();
                                    v_param.typeName = $("input[name='typeIds']:radio:checked",v_cateDlg).data("typename");

                                    console.log(v_param);

                                    $.post({
                                        url:"/cate/addCate.jhtml",
                                        data:v_param,
                                        success:function(result){
                                            if(result.code==200){
                                                initTreeTable();
                                            }
                                            else {
                                                bootbox.alert("添加失败！")
                                            }
                                        }
                                    });

                                }
                            },
                            cancel: {
                                label: '<span class="glyphicon glyphicon-remove"></span>取消',
                                className: 'btn-danger'
                            }
                        }
                    });
                    //还原
                    $("#addDiv").html(v_addDivHtml);

                }
            }

        })

    }
    
    //封装单选
    function buildTypeRadio(typeDivId,v_typeList) {
        var v_typeHtml = "";
        for (let v_type of v_typeList) {
            // 动态拼接type单选
            v_typeHtml+='<label class="radio-inline"><input type="radio" data-typeName="'+v_type.typeName+'" name="typeIds" value="'+v_type.id+'"   /> '+v_type.typeName+'</label>';
        }
        $("#"+typeDivId).html(v_typeHtml);
        
    }
    //封装下拉
    function buildCateSelect(selectId,selectDivId,cateId) {
        var v_cateHtml = "<select class=\"form-control\" id='"+selectId+"'>";
        for (let cate of sortList){

            var str = "";
            for (let i = 0; i < cate.level; i++) {
                str += "&nbsp;&nbsp;&nbsp;";

            }
            v_cateHtml+='<option  value="'+cate.id+'" >'+str+cate.cateName+'</option>';


        }
        v_cateHtml += "</select>";
        $("#"+selectDivId).html(v_cateHtml);
        //回显下拉列表 被选中
        $("#"+selectId).val(cateId);
    }


    //树形表展示
    var sortList=[];//排好序的list
    function initTreeTable() {
        //还原
        $("#cateDiv").html(v_html);
        $.ajax({
            type:"get",
            url:"/cate/findList.jhtml",
            success:function (result) {
                console.log(result);
                if (result.code == 200) {
                    var v_cateList = result.data;

                    sortList=[];//排好序的list
                    sortCateList(v_cateList,sortList,0,0);

                    var v_html = "";
                    for (let cate of sortList) {
                        v_html += '<tr data-tt-id="'+cate.id+'" data-tt-parent-id="'+cate.pid+'">\n' +
                            '            <td>'+cate.cateName+'<button type="button"  class="btn btn-success" onclick="addChilds(\''+cate.id+'\');"><span class="glyphicon glyphicon-plus"></span>增加属性</button></td>\n' +
                            '            <td>'+cate.typeName+'</td>\n' +
                            '            <td>' +
                            '<button type="button" class="btn btn-warning" onclick="updateCate(\''+cate.id+'\');"><span class="glyphicon glyphicon-pencil"></span>编辑</button>' +
                            '<button type="button" class="btn btn-danger" onclick="deleteCate(\''+cate.id+'\');"><span class="glyphicon glyphicon-trash"></span>删除</button>' +
                            '</td>\n' +
                            '        </tr>';
                    }
                    $("#cateTable tbody").html(v_html);
                    // 渲染
                    $("#cateTable").treetable({expandable: true,initialState:'expanded'})
                }
            }
        })
    }
    
    function sortCateList(cateList,sortList,id,level) {
        //根据当前id作为父id查找下面的子节点
        let childs = getChilds(cateList,id);
        for (let c of childs) {
            c.level = level;
            sortList.push(c);


            sortCateList(cateList, sortList, c.id,level+1);
        }
    }


    function getChilds(cateList,id){
        var childs = [];
        for (let cate of cateList) {
            if (cate.pid == id) {
                childs.push(cate);
            }
        }
        return childs;
    }




    //删除 和该节点下的子节点一块删除
    function deleteCate(id) {

        var chiIdList = [];//临时数组用来存放孩子们的id

        getChildsTree(id,chiIdList);
        chiIdList.push(id);
        console.log(chiIdList);

        bootbox.confirm({
            title:"删除节点",
            message:"您确定要删除该节点和该节点下面所有的子节点吗？",
            callback:function(result){
                if(result){

                    $.post({
                        url:"<%=request.getContextPath()%>/cate/delete.jhtml",
                        data:{"ids":chiIdList.join(",")},
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
                                initTreeTable();
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

    function getChildsTree(id,chiIdList){
        for (let cate of sortList) {
            if(id == cate.pid){
                chiIdList.push(cate.id);

                //有孩子的话  继续循环
                getChildsTree(cate.id,chiIdList);
            }
        }
    }



    //修改
    function updateCate(id) {
        $.post({
            url:"<%=request.getContextPath()%>/cate/findCate.jhtml",
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
                    console.log(result);

                    var v_cate = result.data.cate;
                    var v_typeList = result.data.typeList;
                    //备份
                    v_updateHtml = $("#updateDiv").html();
                    //回填值
                    $("#updateCateName").val(v_cate.cateName);
                    //回填下拉并填充
                    buildCateSelect("updateSelect","updateSelectDiv",v_cate.pid);
                    //拼接单选
                    buildTypeRadio("updateTypeDiv",v_typeList);
                    //回填单选
                    $("input[name='typeIds']:radio").each(function () {
                        if(this.value == v_cate.typeId){
                            this.checked = true;
                        }

                        // this.checked = this.value == v_cate.typeId;
                    });


                    //弹出对话框
                    var v_cateDlg = bootbox.dialog({
                        title: '修改分类',
                        message:$("#updateDiv form"),
                        size:"large",
                        buttons: {
                            confirm: {
                                label: '<span class="glyphicon glyphicon-ok"></span>确认',
                                className: 'btn-primary',
                                callback: function(){
                                    //获取信息
                                    var v_param = {};
                                    v_param["cate.cateName"] = $("#updateCateName",v_cateDlg).val();
                                    v_param["cate.pid"] = $("#updateSelect",v_cateDlg).val();
                                    v_param["cate.typeId"] = $("input[name='typeIds']:radio:checked",v_cateDlg).val();
                                    v_param["cate.typeName"] = $("input[name='typeIds']:radio:checked",v_cateDlg).data("typename");
                                    v_param["cate.id"] = id;
                                    console.log(v_param);
                                    var v_chiIdList = [];
                                    getChildsTree(id,v_chiIdList);
                                    v_param.ids = v_chiIdList.join(",");
                                    $.post({
                                        url:"/cate/updateCate.jhtml",
                                        data:v_param,
                                        success:function(result){
                                            if(result.code==200){
                                                initTreeTable();
                                            }
                                            else {
                                                bootbox.alert("更新失败！")
                                            }
                                        }
                                    });

                                }
                            },
                            cancel: {
                                label: '<span class="glyphicon glyphicon-remove"></span>取消',
                                className: 'btn-danger'
                            }
                        }
                    });
                    //还原
                    $("#updateDiv").html(v_updateHtml);
                }
            },

        })
    }


















</script>
</body>
</html>
