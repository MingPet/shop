<%--
  Created by IntelliJ IDEA.
  User: ming
  Date: 2021/3/10
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>规格修改</title>
    <!-- Bootstrap -->
    <link href="/js/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/js/fileinput5/css/fileinput.css" />
    <![endif]-->
</head>
<body>
<jsp:include page="/common/common.jsp"></jsp:include>

<div class="container">
    <div class="row">
        <div class="col-md-12" id="specInfoDiv">
            <button type="button"  class="btn btn-info" onclick="addSpec();"><span class="glyphicon glyphicon-plus"></span>增加规格</button>
            <button type="button"  class="btn btn-info" onclick="submit1();"><span class="glyphicon glyphicon-plus"></span>提交</button>
            <table id="specTable" class="table table-striped table-bordered" style="width:100%">

                <tr style="background-color: #28a4c9">
                    <td>规格名：</td>
                    <td><input type="text" class="form-control" id = "specName" name="specName" placeholder="请输入规格名"/></td>
                    <td>规格排序：</td>
                    <td><input type="text" class="form-control" id = "specNameSort" name="specNameSort" placeholder="请输入规格排序"/></td>
                    <td><button type="button"  class="btn btn-info" onclick="addSpecValue(this);"><span class="glyphicon glyphicon-plus"></span>增加规格值</button></td>
                </tr>
            </table>
        </div>
    </div>
</div>


<textarea id="specValueDiv" style="display: none">


        <tr>
            <td>规格值：</td>
            <td><input type="text" class="form-control" name="specValue" placeholder="请输入规格值"/></td>
            <td>规格值排序：</td>
            <td><input type="text" class="form-control" name="specValueSort" placeholder="请输入规格值排序"/></td>
            <td><button type="button"  class="btn btn-danger" onclick="deleteSpecValue(this);"><span class="glyphicon glyphicon-trash"></span>删除规格值</button></td>
        </tr>
</textarea>


<textarea id = "specDiv" style="display: none">
    <table  class="table table-striped table-bordered" style="width:100%">

        <tr>
            <td><button type="button"  class="btn btn-danger" onclick="deleteSpec(this);"><span class="glyphicon glyphicon-trash"></span>删除规格</button></td>
        </tr>
                <tr>
                    <td>规格名：</td>
                    <td><input type="text" class="form-control" name="specName" placeholder="请输入规格名"/></td>
                    <td>规格排序：</td>
                    <td><input type="text" class="form-control" name="specNameSort" placeholder="请输入规格排序"/></td>
                    <td><button type="button"  class="btn btn-info" onclick="addSpecValue(this);"><span class="glyphicon glyphicon-plus"></span>增加规格值</button></td>
                </tr>
            </table>
</textarea>




<script>
    var v_id = '${param.id}';
    //alert(v_id);
    
    $(function () {
        initSpec();
    });

    //增加规格
    function addSpec(){
        $("#specInfoDiv").append($("#specDiv").val());
    }

    //删除规格
    function deleteSpec(obj) {
        $(obj).parents("table").remove();
    }

    //添加规格值
    function addSpecValue(obj) {
        $(obj).parents("tbody").append($("#specValueDiv").val());
    }

    //删除规格值
    function deleteSpecValue(obj) {
        $(obj).parents("tr").remove();
    }

    //回显
    function initSpec() {
        $.ajax({
            type:"post",
            url:"/spec/findSpec.jhtml?id="+v_id,
            success:function (result) {
               console.log(result);

               if(result.code==200){
                   var v_spec = result.data.spec;
                   var v_specValueList = result.data.specValueList;
                   $("#specName").val(v_spec.specName);
                   $("#specNameSort").val(v_spec.sort);

                   //动态拼接tr

                  var v_html = "";
                  //使用js 的 for  each 循环
                   for(let specVal of v_specValueList){
                       v_html += '<tr>\n' +
                           '            <td>规格值：</td>\n' +
                           '            <td><input type="text" class="form-control" value="'+specVal.specValue+'" name="specValue" /></td>\n' +
                           '            <td>规格值排序：</td>\n' +
                           '            <td><input type="text" class="form-control" value="'+specVal.sort+'" name="specValueSort" /></td>\n' +
                           '            <td><button type="button"  class="btn btn-danger" onclick="deleteSpecValue(this);"><span class="glyphicon glyphicon-trash"></span>删除规格值</button></td>\n' +
                           '        </tr>';
                   }
                   $("#specTable tbody").append(v_html);
               }
               }

        })
    }

    //更新
    function submit1(){
        //获取规格名的集合
        var v_specNameArr = [];
        $("input[name = 'specName']").each(function (){
            v_specNameArr.push(this.value);
        });

        //获取规格名排序的规格
        var v_specNameSortArr = [];
        $("input[name = 'specNameSort']").each(function (){
            v_specNameSortArr.push(this.value);
        });


        //我们想要的组装规格结果 黑色=1，白色=2；8G=3，16G=2，64G=1；
        //每个表的数据就是一个集合


        var v_specValueInfoArr = [];

        $("table").each(function () {

            var v_specValueArr = [];
            var v_specValueSortArr = [];
            //规格值 [白色，黑色]
            $(this).find("input[name='specValue']").each(function () {
                v_specValueArr.push(this.value);
            });

            //规格值排序 [1,2]
            $(this).find("input[name='specValueSort']").each(function () {
                v_specValueSortArr.push(this.value);
            });

            var temp = "";
            for (var i = 0; i < v_specValueArr.length; i++) {
                //临时数组
                temp += ","+v_specValueArr[i]+"="+v_specValueSortArr[i];
            }
            if(temp.length > 0){
                temp = temp.substring(1);//把第一个;截取
            }
            v_specValueInfoArr.push(temp);
        });
        //组装最终的规格值信息  字符串
        //var v_specValueInfos = v_specValueInfoArr.join(";");



        $.ajax({
            type:"post",
            url:"/spec/update.jhtml",
            data:{"id":v_id,"specNames":v_specNameArr[0],"specNameSorts":v_specNameSortArr[0],"specValueInfos":v_specValueInfoArr[0]},
            success:function (result) {
                if(result.code == 200){
                    location.href="/spec/toList.jhtml";
                }
            }
        })
    }
</script>
</body>
</html>
