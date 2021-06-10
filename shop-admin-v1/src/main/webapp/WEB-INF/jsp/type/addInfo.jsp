<html>
<head>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>类型增加</title>
    <!-- Bootstrap -->
    <link href="/js/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/js/fileinput5/css/fileinput.css" />
</head>
<body>
<jsp:include page="/common/common.jsp"></jsp:include>


<div class="container">
    <div class="row">
        <div class="col-md-12">
            <label  class="col-sm-2 control-label">类型名:</label>
            <div class="col-sm-8">
                <input type="text" id="typeName" class="form-control" placeholder="请输入类型名">
            </div>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading"  >
                    <h3 class="panel-title">品牌列表</h3>
                </div>
                <table id="brandTable" class="table table-striped table-bordered" style="width:100%">
                    <tbody>

                    </tbody>
                </table>
            </div>
        </div>
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading"  >
                    <h3 class="panel-title">规格列表</h3>
                </div>
                <table id="specTable" class="table table-striped table-bordered" style="width:100%">
                    <tbody>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">属性列表<button type="button" onclick="addTr()" class="btn btn-primary">增加属性</button></h3>
                </div>
                <table id="attrTableDiv" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th>属性名</th>
                        <th>属性值</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><input type="text" name="attrName" class="form-control"  placeholder="请输入属性名"></td>
                        <td><input type="text" name="attrValue" class="form-control"  placeholder="请输入属性值"></td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <textarea id="attrDiv" style="display: none">
        <tr>
            <td><input type="text" name="attrName" class="form-control"  placeholder="请输入属性名"></td>
            <td><input type="text" name="attrValue" class="form-control"  placeholder="请输入属性值"></td>
            <td>
                <button type="button" class="btn btn-danger" onclick="delTr(this)" ><span calss="glyphicon glyphicon-trash" ></span>删除</button>
            </td>
        </tr>
    </textarea>

    <div style="text-align: center;">
        <button type="button" onclick="submit()" class="btn btn-primary">提交</button>
        <button type="reset" class="btn btn-default">重置</button>
    </div>

</div>




<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->



<script>
    $(function () {
        initInfo();
    })

    function addTr() {
        $("#attrTableDiv tbody").append($("#attrDiv").val());
    }

    function delTr(obj) {
        $(obj).parents("tr").remove();
    }

    function initInfo() {
        $.ajax({
            type:"get",
            url:"/type/findInfo.jhtml",
            success:function (result) {

                console.log(result);
                var brandList = result.data.brandList;
                var specList = result.data.specList;
                var tdCout=4;//每行有多少个数据
                var trCount=Math.ceil(brandList.length/tdCout);//行数
                var trSpecCount=Math.ceil(specList.length/tdCout);//行数
                var brandHtml="";
                for (let i = 0; i <trCount ; i++) {
                    brandHtml+="<tr>";
                    var start=i*tdCout;//每行开始的下标
                    for (let j = 0; j < tdCout; j++) {
                        if(brandList[start+j]){
                            brandHtml+='<td><input type="checkbox" name="brandIds" value="'+brandList[start+j].id+'"/>'+brandList[start+j].brandName+'</td>';
                        }else{
                            brandHtml+='<td></td>';
                        }
                    }
                    brandHtml+="</tr>";
                }

                var specHtml="";
                for (let i = 0; i <trSpecCount ; i++) {
                    specHtml+="<tr>";
                    var start=i*tdCout;//每行开始的下标
                    for (let j = 0; j < tdCout; j++) {
                        if(specList[start+j]){
                            specHtml+='<td><input type="checkbox" name="specIds" value="'+specList[start+j].id+'"/>'+specList[start+j].specName+'</td>';
                        }else{
                            specHtml+='<td></td>';
                        }
                    }
                    specHtml+="</tr>";
                }
                $("#specTable tbody").html(specHtml);


                $("#brandTable tbody").html(brandHtml);
            }
        })

    }

    /*{
        * type：{"typeName":"***"},
        * typeBrandList:[{"brandId":1},{"brandId":2}],
        * typeSpecList:[{"specId":2},{"specId":3}],
        * typeAttrParamList:[
                             {attr:{"attrName":"aaa"},attrValueList:[{"attrValue":"cdcd"},{"attrValue":"dddd"}]},
                             {attr:{"attrName":"bbb"},attrValueList:[{"attrValue":"wwww"},{"attrValue":"rrr"}]},
                             {attr:{"attrName":"ccc"},attrValueList:[{"attrValue":"eee"},{"attrValue":"ttt"}]},
                             ],
        * }
        * */
    function submit() {

        var v_res = {};
        v_res.type = {};
        v_res.type.typeName = $("#typeName").val();
        v_res.typeBrandList = [];



        $("input[name='brandIds']:checkbox:checked").each(function () {
            v_res.typeBrandList.push({"brandId":this.value});
        });

        v_res.typeSpecList = [];

        $("input[name='specIds']:checkbox:checked").each(function () {

            v_res.typeSpecList.push({"specId":this.value});
        });

        v_res.typeAttrParamList = [];

        var v_attrNameList = [];
        $("input[name='attrName']").each(function () {
            v_attrNameList.push(this.value);
        });

        var v_attrValueList = [];
        $("input[name='attrValue']").each(function () {
            v_attrValueList.push(this.value);
        });
        for (var i = 0; i <v_attrNameList.length ; i++) {
            var v_attr = {};
            var v_attrName = v_attrNameList[i];
            v_attr.attr.attrName = v_attrName;
            v_attr.attrvalueList = [];



            //a,b,c,d
            /*
             typeAttrParamList:[
                             {attr:{"attrName":"aaa"},attrValueList:[{"attrValue":"cdcd"},{"attrValue":"dddd"}]},
                             {attr:{"attrName":"bbb"},attrValueList:[{"attrValue":"wwww"},{"attrValue":"rrr"}]},
                             {attr:{"attrName":"ccc"},attrValueList:[{"attrValue":"eee"},{"attrValue":"ttt"}]},
                             ],
             */

            var v_attrValue = v_attrValueList[i];
            var v_attrValueInfoList = v_attrValue.split(",");
            for(let v_attrValueInfo of v_attrValueInfoList){
                v_attr.attrvalueList.push({"attrValue":v_attrValueInfo});
            }
            v_res.typeAttrParamList.push(v_attr);
        }

        console.log(v_res);

        $.ajax({
            type:"POST",
            url:"/type/addTypeInfo.jhtml",
            contentType: "application/json",
            data:JSON.stringify(v_res),
            success:function (result) {
                if(result.code==200){
                    location.href="/type/toList.jhtml";
                } else{
                    bootbox.alert("类型名、品牌和规格都不能为空");
                }
            }
        })

    }

</script>

</body>
</html>
