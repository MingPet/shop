package com.fh.shop.admin.controller.spec;

import com.fh.shop.admin.annotation.LogInfo;
import com.fh.shop.admin.biz.spec.ISpecService;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.controller.BaseController;
import com.fh.shop.admin.param.SpecParam;
import com.fh.shop.admin.param.SpecQueryParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class SpecController extends BaseController {

    @Resource(name = "specService")
    private ISpecService specService;

    //展示跳转
    @RequestMapping(value = "spec/toList",method = RequestMethod.GET)
    public String toList(){

        return "/spec/list";
    }

    //新增跳转
    @RequestMapping(value = "spec/toAdd",method = RequestMethod.GET)
    public String toAdd(){

        return "/spec/add";
    }

    //修改跳转
    @RequestMapping(value = "spec/toUpdate",method = RequestMethod.GET)
    public String toUpdate(){

        return "/spec/update";
    }


    //新增
    @LogInfo(info = "新增方法")
    @RequestMapping(value = "spec/add",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse add(SpecParam specParam){

        return specService.addSpec(specParam);
    }

    //查询
    @LogInfo(info = "查询方法")
    @RequestMapping(value = "spec/findList",method = RequestMethod.POST)
    @ResponseBody
    public DataTableResult findList(SpecQueryParam specQueryParam){
        return specService.findList(specQueryParam);
    }

    //回显
    @LogInfo(info = "回显方法")
    @RequestMapping(value = "spec/findSpec",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse findSpec(Long id){
        return  specService.findSpec(id);
    }

    //修改
    @LogInfo(info = "修改方法")
    @RequestMapping(value = "spec/update",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateSpec(SpecParam specParam){
        return  specService.updateSpec(specParam);
    }

    //单个删除
    @LogInfo(info = "单个删除方法")
    @RequestMapping(value = "spec/delete",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteSpec(Long id){
        return  specService.deleteSpec(id);
    }

    //批量删除
    @LogInfo(info = "批量删除方法")
    @RequestMapping(value = "spec/deleteBatch",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteBatch(String ids){
        return  specService.deleteBatch(ids);
    }

}
