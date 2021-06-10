<%--
  Created by IntelliJ IDEA.
  User: ming
  Date: 2021/3/22
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商品管理</title>
    <jsp:include page="/common/head.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/common/common.jsp"></jsp:include>

<style>
    td.details-control{
        background: url('/js/DataTables/DataTables-1.10.20/images/details_open.png') no-repeat center center;
        cursor: pointer;
    }
    tr.shown td.details-control{
        background: url('/js/DataTables/DataTables-1.10.20/images/details_close.png') no-repeat center center;
    }
</style>

<div class="container">
    <div class="row">
        <div class="col-md-12">

            <%--条件查询--%>
            <%--<div class="panel panel-default"  style="border-color: #008080">--%>
                <%--<div class="panel-heading" style="background-color: #008080">--%>
                    <%--<h3 class="panel-title" ><font color="#f0f8ff">条件查询</font></h3>--%>
                <%--</div>--%>
                <%--<div class="panel-body">--%>
                    <%--<div class="form-horizontal">--%>
                        <%--<div class="col-md-6">--%>
                            <%--<div class="form-group" >--%>
                                <%--<label  class="col-sm-2 control-label">商品名:</label>--%>
                                <%--<div class="col-sm-8">--%>
                                    <%--<input type="text" class="form-control" id="spuName" placeholder="请输入品牌名">--%>
                                <%--</div>--%>
                            <%--</div>--%>

                        <%--</div>--%>
                        <%--<div class="col-md-6">--%>
                            <%--<div class="form-group" id="">--%>
                                <%--<label  class="col-sm-2 control-label">品牌:</label>--%>


                            <%--</div>--%>
                        <%--</div>--%>



                        <%--<div class="col-md-6">--%>
                            <%--<div class="form-group">--%>
                                <%--<label class="col-sm-2 control-label">价格</label>--%>
                                <%--<div class="col-sm-8">--%>
                                    <%--<div class="input-group">--%>
                                        <%--<input type="text" class="form-control " id="minPrice" name="minPrice">--%>
                                        <%--<span class="input-group-addon">--</span>--%>
                                        <%--<input type="text" class="form-control " id="maxPrice" name="maxPrice">--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>

                        <%--<div class="col-md-6">--%>
                            <%--<div class="form-group">--%>
                                <%--<label class="col-sm-2 control-label">库存</label>--%>
                                <%--<div class="col-sm-8">--%>
                                    <%--<div class="input-group">--%>
                                        <%--<input type="text" class="form-control " id="minStock" name="minStock">--%>
                                        <%--<span class="input-group-addon">--</span>--%>
                                        <%--<input type="text" class="form-control " id="maxStock" name="maxStock">--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>




                        <%--<div class="col-md-6">--%>
                            <%--<div class="form-group" id="cateDiv">--%>
                                <%--<label  class="col-sm-2 control-label">分类:</label>--%>
                                <%--<div class="col-sm-8">--%>
                                <%--</div>--%>

                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%----%>


                        <%--<div style="text-align: center;">--%>
                            <%--<button type="button" class="btn btn-primary" onclick="search()"><span class="glyphicon glyphicon-search"></span> 查询</button>--%>
                            <%--<button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-refresh"></span> 重置</button>--%>
                        <%--</div>--%>
                    <%--</form>--%>
                <%--</div>--%>
            <%--</div>--%>




                <!-- 查询条件面板 -->
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
                                            <label class="col-sm-2 control-label">商品名称</label>
                                            <div class="col-sm-8">
                                                <input type="text" class="form-control" name="spuName" id="spuName" placeholder="请输入商品名称">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">商品价格</label>
                                            <div class="col-sm-8">
                                                <div class="input-group">
                                                    <input type="text" class="form-control" id="minPrice" name="minPrice">
                                                    <span class="input-group-addon">--</span>
                                                    <input type="text" class="form-control" id="maxPrice" name="maxPrice">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">

                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">品牌</label>
                                            <div class="col-sm-8" id ="brandDiv">
                                                <%--<select class="form-control" name="areaId">--%>
                                                    <%--<option value="-1">请选择</option>--%>
                                                <%--</select>--%>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">商品库存</label>
                                            <div class="col-sm-8">
                                                <div class="input-group">
                                                    <input type="text" class="form-control " id="minStock" name="minStock">
                                                    <span class="input-group-addon">--</span>
                                                    <input type="text" class="form-control " id="maxStock" name="maxStock">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-8" >
                                        <div class="form-group" >
                                            <label  class="col-sm-4 control-label">分类</label>
                                            <div class="col-md-8" id="cateDiv">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%--<div class="row">--%>
                                    <%--<div class="col-md-6">--%>
                                        <%--<div class="form-group">--%>
                                            <%--<label for="firstname" class="col-sm-2 control-label">适合人群</label>--%>
                                            <%--<div class="col-sm-10">--%>
                                                <%--<label class="checkbox-inline">--%>
                                                    <%--<input type="checkbox" name="sperson" value="1">少年--%>
                                                <%--</label>--%>
                                                <%--<label class="checkbox-inline">--%>
                                                    <%--<input type="checkbox" name="sperson" value="2">青年--%>
                                                <%--</label>--%>
                                                <%--<label class="checkbox-inline">--%>
                                                    <%--<input type="checkbox" name="sperson" value="3">中年--%>
                                                <%--</label>--%>
                                                <%--<label class="checkbox-inline">--%>
                                                    <%--<input type="checkbox" name="sperson" value="4">老年--%>
                                                <%--</label>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <%--<div class="col-md-6">--%>
                                        <%--<div class="form-group">--%>
                                            <%--<label for="firstname" class="col-sm-2 control-label">是否上映</label>--%>
                                            <%--<div class="col-sm-10">--%>
                                                <%--<label class="radio-inline">--%>
                                                    <%--<input type="radio" name="sisup" value="1"/>已上映--%>
                                                <%--</label>--%>
                                                <%--<label class="radio-inline">--%>
                                                    <%--<input type="radio" name="sisup" value="2"/>未上映--%>
                                                <%--</label>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                                <div class="row">
                                    <div style="padding-left:120px">
                                        <button type="button" onclick="search()" class="btn btn-primary">
                                            <span class="glyphicon glyphicon-search"></span>查询
                                        </button>
                                        &nbsp;
                                        <button type="reset" class="btn btn-danger">
                                            <span class="glyphicon glyphicon-refresh"></span>重置
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>



















        </div>
    </div>



    <button type="button" style="background-color: #008080" class="btn btn-info" onclick="toAdd();"><span class="glyphicon glyphicon-plus"></span>增加</button>
    <button type="button" class="btn btn-danger" onclick="deleteBatch();"><span class="glyphicon glyphicon-trash"></span>批量删除</button>
    <button type="button" class="btn btn-danger" onclick="clearCache();"><span class="glyphicon glyphicon-trash"></span>刷新商品缓存</button>

    <br><br>


    <div class="panel panel-default"  style="border-color: #008080">
        <div class="panel-heading" style="background-color: #008080">
            <h3 class="panel-title"><font color="#f0f8ff">列表展示</font></h3>
        </div>
        <div class="panel-body">
            <table id="goodsTable" class="table table-striped table-bordered" style="width:100%">
                <thead>
                <tr>
                    <th></th>
                    <th>选择</th>
                    <th>商品名</th>
                    <th>价格</th>
                    <th>库存</th>
                    <th>品牌名</th>
                    <th>分类名</th>
                    <th>上下架</th>
                    <th>是否新品</th>
                    <th>是否推荐</th>

                    <th>操作</th>
                </tr>
                </thead>

                <tfoot>
                <tr>
                    <th></th>
                    <th>选择</th>
                    <th>商品名</th>
                    <th>价格</th>
                    <th>库存</th>
                    <th>品牌名</th>
                    <th>分类名</th>
                    <th>上下架</th>
                    <th>是否新品</th>
                    <th>是否推荐</th>
                    <th>操作</th>
                </tr>
                </tfoot>
            </table>
        </div>
    </div>

</div>
<script>
    //初始化datatable
    $(function(){
        initTable();
        initCate(0);
        initBrand();

    });

    //刷新商品缓存
    function clearCache(){
        $.ajax({
            type:"get",
            url:"/spu/clearCache.jhtml",
            success:function (result) {
                if(result.code == 200){
                    alert("刷新成功");
                }
            }
        });
    }

    //新增跳转
    function toAdd() {
        window.location.href="/spu/toAdd.jhtml";
    }

    //条件查询
    function search(){
        //获取参数值
        //var v_spuName = $("#spuName").val();
        //传递参数
        var v_param = {};
        v_param.spuName = $("#spuName").val();
        v_param.minPrice = $("#minPrice").val();
        v_param.maxPrice = $("#maxPrice").val();
        v_param.minStock = $("#minStock").val();
        v_param.maxStock = $("#maxStock").val();
        v_param.brandId = $("#brandSelect").val();

        var v_length = $("select[name='cateSelect']").length;
        for (let i = 0; i <v_length ; i++) {
            v_param["cate"+(i+1)] = $($("select[name='cateSelect']")[i]).val();
        }
        // v_param.cate1 = $($("select[name='cateSelect']")[0]).val();
        // v_param.cate2 = $($("select[name='cateSelect']")[1]).val();
        // v_param.cate3 = $($("select[name='cateSelect']")[2]).val();
        v_goodsTable.settings()[0].ajax.data = v_param;
        console.log(v_param)
        v_goodsTable.ajax.reload();
    }

    //列表展示
    var v_goodsTable;
    function initTable() {
        v_goodsTable = $('#goodsTable').DataTable({
            "language": {
                "url": "/js/DataTables/Chinese.json" // 汉化
            },
            // 是否允许检索
            "searching": false,
            "processing": true,
            "lengthMenu": [5,10,15,20],
            "serverSide": true,
            "ajax": {
                "url": "/spu/findList.jhtml",
                "type": "POST"
            },
            "columns": [
                {
                    "className":      'details-control',
                    "orderable":      false,
                    "data":           null,
                    "defaultContent": ''
                },
                {  "data": "spu.id",
                    "render": function (data, type, row, meta) {
                        return '<input type="checkbox" name="ids" value="'+data+'"/>';
                    }
                },

                { "data": "spu.spuName" },
                { "data": "spu.price" },
                { "data": "spu.stock" },
                { "data": "spu.brandName" },
                { "data": "spu.cateName" },
                { "data": "spu.isUp" ,
                    "render": function (data, type, row, meta) {
                    return data == '1'?"下架":"上架";
                    }
                },
                { "data": "spu.isNew" ,
                    "render": function (data, type, row, meta) {
                        return data == '1'?"非新品":"新品";
                    }
                },
                { "data": "spu.isRec" ,
                    "render": function (data, type, row, meta) {
                        return data == '1'?"不推荐":"推荐";
                    }
                },

                { "data": "spu.id",
                    "render": function (data, type, row, meta) {
                        console.log(row);
                        var str = '';
                        if(row.spu.isUp == '1'){
                            str+= '<button type="button" onclick="updateStatus(\''+data+'\',\'isUp\',\'2\')" class="btn btn-success"><span class="glyphicon glyphicon-upload"></span>上架</button>'
                        }else{
                            str += '<button type="button" onclick="updateStatus(\''+data+'\',\'isUp\',\'1\')" class="btn btn-danger"><span class="glyphicon glyphicon-download"></span>下架</button>'
                        }
                        if(row.spu.isNew == '1'){
                            str+= '<button type="button" onclick="updateStatus(\''+data+'\',\'isNew\',\'2\')" class="btn btn-success"><span class="glyphicon glyphicon-star"></span>新品</button>'
                        }else{
                            str += '<button type="button" onclick="updateStatus(\''+data+'\',\'isNew\',\'1\')" class="btn btn-danger"><span class="glyphicon glyphicon-star-empty"></span>非新品</button>'
                        }
                        if(row.spu.isRec == '1'){
                            str+= '<button type="button" onclick="updateStatus(\''+data+'\',\'isRec\',\'2\')" class="btn btn-success"><span class="glyphicon glyphicon-heart-empty"></span>推荐</button>'
                        }else{
                            str += '<button type="button" onclick="updateStatus(\''+data+'\',\'isRec\',\'1\')" class="btn btn-danger"><span class="glyphicon glyphicon-heart"></span>不推荐</button>'
                        }
                        return '<div class="btn-group" role="group" aria-label="...">'+
                            '<button type="button" onclick="editSpu(\''+data+'\')" class="btn btn-warning"><span class="glyphicon glyphicon-pencil"></span>修改</button>\n'+
                            '<button type="button" onclick="deleteSpu(\''+data+'\')" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>删除</button>\n'+str+
                            '</div>';
                    }
                }
            ],

        });





        $('#goodsTable tbody').on('click', 'td.details-control', function () {
            var tr = $(this).closest('tr');
            var row = v_goodsTable.row( tr );

            if ( row.child.isShown() ) {
                // This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
            }
            else {
                // Open this row
                row.child( format(row.data()) ).show();
                tr.addClass('shown');
            }
        } );
    }


    function updateStatus(id,flag,status){
        $.post({
            url:"/spu/updateStatus.jhtml",

            data:{"spuId":id,"flag":flag,"status":status},

            success:function(result){
                if(result.code==200){
                   // bootbox.alert(isup==1?"上架成功":"下架成功");
                    search();
                }else{
                   // bootbox.alert(isup==1?"上架失败":"下架失败");
                }
            }
        })
    }

    function format ( d ) {

        var v_skuList = d.skuList;
        var v_html = '<div>'
        // `d` is the original data object for the row
        for(let skuList of v_skuList){
            v_html += '<table class="table table-striped table-bordered" style="width:100%">'+
                '<tr>'+
                '<td>SKU:</td>'+
                '<td>'+skuList.skuName+'</td>'+
                '</tr>'+
                '<tr>'+
                '<td>价格:</td>'+
                '<td>'+skuList.price+'</td>'+
                '</tr>'+
                '<tr>'+
                '<td>库存:</td>'+
                '<td>'+skuList.stock+'</td>'+
                '</tr>'+
                '<tr>'+
                '<td>图片:</td>'+
                '<td><img src="'+skuList.image+'" width="100px" height="100px"></td>'+
                '</tr>'+
                '</table>';
        }
        v_html += '</div>'
        return v_html;

    }

    function editSpu(id) {
        location.href="/spu/toEdit.jhtml?id="+id;

    }

    //删除
    function deleteSpu(id) {
        bootbox.confirm({
            title:"删除商品",
            message:"您确定要删除吗？",
            callback:function(result){

                if(result){
                    $.post({
                        url:"<%=request.getContextPath()%>/spu/deleteSpu.jhtml",
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
                        v_idArr.join(",");
                        $.ajax({
                            type:"post",
                            url:"<%=request.getContextPath()%>/spu/deleteBatch.jhtml",
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



    function initCate(id,obj){
        if(obj){
            $(obj).parent().nextAll().remove();
        }

        if(id > 0){
            //该向后台发送请求
            $.post({
                url:"/brand/findBrandList.jhtml?cateId="+id,
                success:function (result) {

                        var v_brandList = result.data;
                        var v_html ='<select class="form-control" id="brandSelect"><option value="-1">===请选择===</option>';
                        for(let brand of v_brandList){
                            v_html+='<option value="'+brand.id+'" data-brand-name="'+brand.brandName+'">'+brand.brandName+'</option>';
                        }
                        v_html+='</select>';
                        $("#brandDiv").html(v_html);

                    }

            })
        }
        $.post({
            url:"/cate/findCateChilds.jhtml?id="+id,
            success:function (result) {
                console.log(result);
                if(result.code==200) {
                    var v_cateList = result.data;
                    if(v_cateList.length == 0){
                        //最后一级的分类
                        // var v_typeId = $(obj).find("option:checked").data("type-id");
                        // initSpuInfo(v_typeId);
                        return ;
                    }
                    var v_html = '<div class="col-sm-4"> <select class="form-control" name="cateSelect" onchange="initCate(this.value,this)"><option value="-1">===请选择===</option>';
                    for (let v_cate of v_cateList) {
                        v_html+='<option value="'+v_cate.id+'" data-type-id="'+v_cate.typeId+'" data-cate-name="'+v_cate.cateName+'">'+v_cate.cateName+'</option>';
                    }
                    v_html += '</select></div>';
                    $("#cateDiv").append(v_html);
                }
            }
        })
    }

    function initBrand(){
        //$("#brandDiv").remove();
        $.ajax({
            type:"post",
            url:"/brand/findBrandList.jhtml",
            success:function (result) {
                if (result.code==200){

                    var v_brandList = result.data;
                    var v_html ='<select class="form-control" id="brandSelect"><option value="-1">===请选择===</option>';
                    for(let brand of v_brandList){
                        v_html+='<option value="'+brand.id+'" data-brand-name="'+brand.brandName+'">'+brand.brandName+'</option>';
                    }
                    v_html+='</select>';
                    $("#brandDiv").html(v_html);
                }
            }

        })

    }


</script>

</body>
</html>
