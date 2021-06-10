package com.fh.shop.admin.controller.type;

import com.fh.shop.admin.annotation.LogInfo;
import com.fh.shop.admin.biz.type.ITypeService;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.controller.BaseController;
import com.fh.shop.admin.param.TypeInfoParam;
import com.fh.shop.admin.param.TypeParam;
import com.fh.shop.admin.param.TypeQueryParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TypeController extends BaseController {

    @Resource(name = "typeService")
    private ITypeService typeService;
    //新增跳转
    @RequestMapping(value = "type/toAdd",method = RequestMethod.GET)
    public String toAdd(){

        return "/type/add";
    }




    @LogInfo(info = "新增方法")
    @RequestMapping(value = "type/findInfo",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse findInfo(){

        return typeService.findInfo();
    }

    //新增
    @RequestMapping(value = "type/addType",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addType(TypeParam typeParam){

        return typeService.addType(typeParam);
    }

    //requestBody新增跳转
    @RequestMapping(value = "type/toAddInfo",method = RequestMethod.GET)
    public String toAddInfo(){

        return "/type/addInfo";
    }
    //requestBody新增
    @RequestMapping(value = "type/addTypeInfo",method = RequestMethod.POST)
    public ServerResponse addTypeInfo(@RequestBody TypeInfoParam typeInfoParam){

        return typeService.addTypeInfo(typeInfoParam);
    }
    //列表跳转
    @RequestMapping(value = "type/toList",method = RequestMethod.GET)
    public String toList()
    {
        return "/type/list";
    }


    @RequestMapping(value = "type/findList",method = RequestMethod.POST)
    @ResponseBody
    public DataTableResult findList(TypeQueryParam typeQueryParam){

        return typeService.findList(typeQueryParam);
    }

    //修改跳转
    @RequestMapping(value = "type/toEditType",method = RequestMethod.GET)
    public String toEditType()
    {

        return "/type/editType";
    }

    //回显
    @RequestMapping(value = "type/findType",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse findType(Long id){

        return typeService.findEtidType(id);
    }

    @LogInfo(info = "修改方法")
    @RequestMapping(value = "type/updateType",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateType(TypeParam typeParam){

        return typeService.updateType(typeParam);
    }


    //单个删除
    @LogInfo(info = "单个删除方法")
    @RequestMapping(value = "type/delete",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteType(Long id){

        return  typeService.deleteType(id);
    }
    //批量删除
    @LogInfo(info = "批量删除方法")
    @RequestMapping(value = "type/deleteBatch",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteBatch(String ids){

        return  typeService.deleteBatch(ids);
    }

    //查询typelist集合
    @RequestMapping(value = "type/findAllTypeList",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse findAllTypeList(){
        return typeService.findAllTypeList();
    }

}
