<%--
  Created by IntelliJ IDEA.
  User: ming
  Date: 2021/3/9
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>增加规格</title>
    <!-- Bootstrap -->
    <link href="/js/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/js/fileinput5/css/fileinput.css" />
    <![endif]-->
</head>
<body>
<jsp:include page="/common/nav.jsp"></jsp:include>

<div class="container">
    <div class="row">
        <div class="col-md-12" id="specInfoDiv">
            <button type="button"  class="btn btn-info" onclick="addSpec();"><span class="glyphicon glyphicon-plus"></span>增加规格</button>
            <button type="button"  class="btn btn-info" onclick="submit1();"><span class="glyphicon glyphicon-plus"></span>提交</button>
            <table  class="table table-striped table-bordered" style="width:100%">

                <tr>
                    <td>规格名：</td>
                    <td><input type="text" class="form-control" name="specName" placeholder="请输入规格名"/></td>
                    <td>规格排序：</td>
                    <td><input type="text" class="form-control" name="specNameSort" placeholder="请输入规格排序"/></td>
                    <td><button type="button"  class="btn btn-info" onclick="addSpecValue(this);"><span class="glyphicon glyphicon-plus"></span>增加规格值</button></td>
                </tr>
            </table>
        </div>
    </div>
</div>

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


<textarea id="specValueDiv" style="display: none">


        <tr>
            <td>规格值：</td>
            <td><input type="text" class="form-control" name="specValue" placeholder="请输入规格值"/></td>
            <td>规格值排序：</td>
            <td><input type="text" class="form-control" name="specValueSort" placeholder="请输入规格值排序"/></td>
            <td><button type="button"  class="btn btn-danger" onclick="deleteSpecValue(this);"><span class="glyphicon glyphicon-trash"></span>删除规格值</button></td>
        </tr>
</textarea>



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

    //添加规格值
    function addSpecValue(obj) {
        $(obj).parents("tbody").append($("#specValueDiv").val());
    }

    //删除规格值
    function deleteSpecValue(obj) {
        $(obj).parents("tr").remove();
    }

    //增加规格
    function addSpec(){
        $("#specInfoDiv").append($("#specDiv").val());
    }

    //删除规格
    function deleteSpec(obj) {
        $(obj).parents("table").remove();
    }

    //新增
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

        //转换为，分割的字符串
        var v_specNames = v_specNameArr.join(",");
        var v_specNameSorts = v_specNameSortArr.join(",");

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
        var v_specValueInfos = v_specValueInfoArr.join(";");

        console.log(v_specNames);
        console.log(v_specNameSorts);
        console.log(v_specValueInfos);


        $.ajax({
            type:"post",
            url:"/spec/add.jhtml",
            data:{"specNames":v_specNames,"specNameSorts":v_specNameSorts,"specValueInfos":v_specValueInfos},
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
