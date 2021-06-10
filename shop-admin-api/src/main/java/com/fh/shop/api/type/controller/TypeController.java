package com.fh.shop.api.type.controller;

import com.fh.shop.api.type.biz.ITypeService;
import com.fh.shop.api.type.param.TypeParam;
import com.fh.shop.api.type.param.TypeQueryParam;
import com.fh.shop.api.type.param.TypeUpdateParam;
import com.fh.shop.common.ServerResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/api/types")
public class TypeController {

    @Resource(name = "typeService")
    private ITypeService typeService;





    @RequestMapping(value = "/findInfo",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse findInfo(){

        return typeService.findInfo();
    }

    //新增
//   @PostMapping("/add")
//    public ServerResponse addType(@RequestBody TypeParam typeParam){
//
//        return typeService.addType(typeParam);
//    }


    @RequestMapping(value = "/addType",method = RequestMethod.POST)
    public ServerResponse addType(@RequestBody TypeParam typeParam){

        return typeService.addType(typeParam);
    }




    @GetMapping("/findList")
    public ServerResponse findList(TypeQueryParam typeQueryParam){

        return typeService.findList(typeQueryParam);
    }



    //回显
    @GetMapping("/{id}")
    public ServerResponse findType(@PathVariable Long id){

        return typeService.findEtidType(id);
    }

    //修改
    @PostMapping("/updateType")
    public ServerResponse updateType(@RequestBody TypeParam typeParam) {
        return typeService.updateType(typeParam);
    }




//    @RequestMapping(value = "/updateType",method = RequestMethod.POST)
//    @ResponseBody
//    public ServerResponse updateType(TypeParam typeParam){
//
//        return typeService.updateType(typeParam);
//    }


    //单个删除
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteType(Long id){

        return  typeService.deleteType(id);
    }
    //批量删除
    @RequestMapping(value = "/deleteBatch",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteBatch(String ids){

        return  typeService.deleteBatch(ids);
    }

    //查询typelist集合
    @RequestMapping(value = "/findAllTypeList",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse findAllTypeList(){
        return typeService.findAllTypeList();
    }

}
