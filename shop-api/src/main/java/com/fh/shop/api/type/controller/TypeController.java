package com.fh.shop.api.type.controller;

import com.fh.shop.api.type.biz.ITypeService;
import com.fh.shop.api.type.param.TypeParam;
import com.fh.shop.api.type.param.TypeQueryParam;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("type")
@CrossOrigin
public class TypeController {

    @Resource(name = "typeService")
    private ITypeService typeService;

    @RequestMapping(value = "/findList",method = RequestMethod.POST)
    public DataTableResult findList(TypeQueryParam typeQueryParam){

        return typeService.findList(typeQueryParam);
    }

    @RequestMapping(value = "/findInfo",method = RequestMethod.GET)
    public ServerResponse findInfo(){

        return typeService.findInfo();
    }


    //增加
    @RequestMapping(value = "/addTypeInfo",method = RequestMethod.POST)
    public ServerResponse addType(@RequestBody TypeParam typeParam){

        return typeService.addType(typeParam);
    }
}
