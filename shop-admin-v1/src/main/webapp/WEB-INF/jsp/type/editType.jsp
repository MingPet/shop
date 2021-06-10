<%--
  Created by IntelliJ IDEA.
  User: ming
  Date: 2021/3/13
  Time: 19:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>类型修改</title>
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
            <form class="form-horizontal">
                <div class="form-group" >
                    <label  class="col-sm-3 control-label">类型名:</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="typeName" >
                    </div>
                </div>
            </form>

        </div>
    </div>




    <div>
        <div class="row">
            <div class="col-md-6">
                <%--品牌列表--%>
                <div class="panel panel-default"  style="border-color: #008080">
                    <div class="panel-heading" style="background-color: #008080">
                        <h3 class="panel-title" ><font color="#f0f8ff">品牌列表</font></h3>
                    </div>
                    <div class="panel-body">
                        <table id="brandTable" class="table table-striped table-bordered" style="width:100%">
                            <tbody>

                            </tbody>
                        </table>
                    </div>
                </div>

            </div>

            <div class="col-md-6">
                <%--规格列表--%>
                <div class="panel panel-default"  style="border-color: #008080">
                    <div class="panel-heading" style="background-color: #008080">
                        <h3 class="panel-title" ><font color="#f0f8ff">规格列表</font></h3>
                    </div>
                    <div class="panel-body">
                        <table id="specTable" class="table table-striped table-bordered" style="width:100%">
                            <tbody>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>







    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default"  style="border-color: #008080">
                <div class="panel-heading" style="background-color: #008080">
                    <h3 class="panel-title" >
                        <font color="#f0f8ff">属性列表
                            <button type="button"  class="btn btn-success" onclick="addAttr();"><span class="glyphicon glyphicon-plus"></span>增加属性</button>
                        </font></h3>
                </div>
                <div class="panel-body">
                    <table id="attrTable" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                        <tr>
                            <th>属性名</th>
                            <th>属性值</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%--<tr>--%>
                            <%--<td><input type="text" class="form-control" name = "attrName" placeholder="请输入属性名"></td>--%>
                            <%--<td>11</td>--%>
                            <%--<td> <button type="button"  class="btn btn-danger" onclick="delAttr();"><span class="glyphicon glyphicon-trash"></span>删除属性</button>--%>
                                <%--<button type="button"  class="btn btn-success" onclick="updateAttr();"><span class="glyphicon glyphicon-pencil"></span>编辑属性</button></td>--%>
                        <%--</tr>--%>


                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>


    <textarea id = "attrDiv" style="display: none">
    <tr>
        <td><input type="text" class="form-control" name = "attrName" placeholder="请输入属性名"></td>
        <td><input type="text" class="form-control" name = "attrValue" placeholder="请输入属性值"></td>
        <td><button type="button"  class="btn btn-danger" onclick="deleteAttr(this);"><span class="glyphicon glyphicon-trash"></span>删除按钮</button></td>
    </tr>
</textarea>



    <center>
        <button type="button" style="background-color: #008080" class="btn btn-success" onclick="updateType();"><span class="glyphicon glyphicon-ok"></span>提交</button>
    </center>
    <br><br>

</div>





<script>
    var v_id = '${param.id}';
    //alert(v_id);


    $(function () {
        initType();
    });

    //增加属性
    function addAttr() {
        $("#attrTable tbody").append($("#attrDiv").val());
    }

    //删除新增的
    function deleteAttr(obj){
        $(obj).parents("tr").remove();
    }

    //删除
    function delAttr2(obj){
        $(obj).parents("tr").remove();
    }

    function initType(){

        $.ajax({
            type:"post",
            url:"/type/findType.jhtml",
            data:{"id":v_id},
            success:function (result) {

                console.log(result);

                var v_typeName = result.data.typeName;
                var v_brandList = result.data.brandList;

                var v_brandIdList = result.data.brandIdList;

                var v_specList = result.data.specList;
                var v_specIdList = result.data.specIdList;

                var v_attrVoList = result.data.attrVoList;
                console.log(v_attrVoList)
                //回显类型信息
                $("#typeName").val(v_typeName);
                //渲染品牌 规格列表表
                //每页有多少条数据
                var v_td_count = 3;
                var v_tr_count = Math.ceil(v_brandList.length/v_td_count);
                var v_spec_tr_count = Math.ceil(v_specList.length/v_td_count);
                //拼接
                var v_brandHtml = "";

                for (let i = 0; i <v_tr_count ; i++) {
                    v_brandHtml += "<tr>";
                    //每行开始位置的下表
                    var v_startIndex = i * v_td_count;

                    for (let j = 0; j <v_td_count ; j++) {
                        if(v_brandList[v_startIndex + j]){
                            v_brandHtml += '<td><input type="checkbox" name = "brandIds" value="'+v_brandList[v_startIndex + j].id+'"/>'+v_brandList[v_startIndex + j].brandName+'</td>';
                        }else{
                            v_brandHtml += '<td></td>';
                        }
                    }
                    v_brandHtml += "</tr>";
                }
                $("#brandTable tbody").html(v_brandHtml);



                //规格列表
                var v_specHtml = "";
                for (let i = 0; i <v_spec_tr_count ; i++) {
                    v_specHtml += "<tr>";
                    //每行开始位置的下表
                    var v_startIndex = i * v_td_count;

                    for (let j = 0; j <v_td_count ; j++) {
                        if(v_specList[v_startIndex + j]){
                            v_specHtml += '<td><input type="checkbox" name = "specIds" value="'+v_specList[v_startIndex + j].id+'"/>'+v_specList[v_startIndex + j].specName+'</td>';
                        }else{
                            v_specHtml += '<td></td>';
                        }
                    }
                    v_specHtml += "</tr>";
                }
                //$("#brandTable tbody").html(v_brandHtml);
                $("#specTable tbody").html(v_specHtml);

                //回填品牌列表
                selected('brandIds',v_brandIdList);
                //回填规格列表
                selected('specIds',v_specIdList);

                //动态渲染属性与属性值
                var v_html = "";
                for (let attrVo of v_attrVoList) {
                    console.log(attrVo.attrName)
                    v_html += '<tr>\n' +
                        '                                <td><input type="text" class="form-control" name = "attrName" value="'+attrVo.attrName+'"></td>\n' +
                        '                                <td>'+attrVo.attrValues+'</td>\n' +
                        '                                <td><button type="button"  class="btn btn-danger" onclick="delAttr2(this);"><span class="glyphicon glyphicon-trash"></span>删除属性</button>' +
                        '                                   <input type="text" class="form-control" name = "attrValue" value="'+attrVo.attrValues+'" >' +
                        '                                   <button type="button"  class="btn btn-success" onclick="updateAttr(this);"><span class="glyphicon glyphicon-pencil"></span>编辑属性</button></td>\n' +
                        '                            </tr>';
                }
                $("#attrTable tbody").html(v_html);



            }
        })
    }
    
    
    //编辑弹框
    function updateAttr(obj) {
        var v_attrValues = $(obj).parent().prev().html();
        console.log(v_attrValues);

        var v_attrValueArr = v_attrValues.split(",")
        var v_html = ' <div></div><button type="button"  class="btn btn-success" onclick="addAttrValue();"><span class="glyphicon glyphicon-plus"></span>增加属性值</button><table id="attrValueTable" class="table table-striped table-bordered" style="width:100%">'

        for(let attrVal of v_attrValueArr ){
            v_html+='<tr>' +
                '<td><input type="text" class="form-control" name = "attrValueInfo" placeholder="请输入属性名" value="'+attrVal+'"></td>' +
                '<td><button type="button"  class="btn btn-danger" onclick="delAttrValue(this);"><span class="glyphicon glyphicon-trash"></span>删除属性值</button></td>' +
                '</tr>'
        }
        v_html +='</table>';

        //弹框编辑
        var v_attrValueConfirm = bootbox.confirm({
            size:"large",
            title:"编辑属性值",
            message:v_html,
            callback:function(){

                var v_attrValArr = [];
                $("input[name='attrValueInfo']").each(function () {
                    v_attrValArr.push(this.value);

                })
                $(obj).parent().prev().html(v_attrValArr.join(","));
                //根据name值查找到 隐藏域  把属性值赋给隐藏域
                $(obj).parent().find("input[name='attrValue']").val(v_attrValArr.join(","));

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


    function addAttrValue() {
        $("#attrValueTable").append(
            '<tr>' +
            '<td><input type="text" class="form-control" name = "attrValueInfo" placeholder="请输入属性值" ></td>' +
            '<td><button type="button"  class="btn btn-danger" onclick="delAttrValue(this);"><span class="glyphicon glyphicon-trash"></span>删除属性值</button></td>' +
            '</tr>'
        )
    }
    function delAttrValue(obj) {
        $(obj).parents("tr").remove();
    }
    function selected(name,idList) {
        $("input[name='"+name+"']:checkbox").each(function () {

            this.checked = isChecked(this.value, idList);
        })
    }
    
    function isChecked(id,idList){
        for (let t of idList) {
            if(t == id){
                return true;
            }
        }
        return false;
    }

    function updateType() {
        var v_typeName = $("#typeName").val();
        var v_brandIdsArr = [];
        var v_specIdsArr = [];
        var v_attrNameArr = [];
        var v_attrValueArr = [];
        $("input[name=brandIds]:checkbox:checked").each(function () {
            v_brandIdsArr.push(this.value);
        });
        $("input[name=specIds]:checkbox:checked").each(function () {
            v_specIdsArr.push(this.value);
        });
        $("input[name=attrName]").each(function () {
            v_attrNameArr.push(this.value);
        });
        $("input[name=attrValue]").each(function () {
            v_attrValueArr.push(this.value);
        });

        if (v_typeName != null || v_brandIdsArr.length > 0 || v_specIdsArr.length > 0) {
            $.ajax({
                type: "post",
                url: "/type/updateType.jhtml",
                data: {
                    "id": v_id,
                    "typeName": v_typeName,
                    "brandIds": v_brandIdsArr.join(","),
                    "specIds": v_specIdsArr.join(","),
                    "attrNames":v_attrNameArr.join(","),
                    "attrValues":v_attrValueArr.join(";"),
                },
                success: function (result) {
                    if (result.code == 200) {
                        location.href = "/type/toList.jhtml";
                    }
                }
            })
        } else {
            bootbox.alert("类型名、品牌和规格都不能为空");
        }
    }

</script>

</body>
</html>
