package com.fh.shop.api.spec.controller;


import com.fh.shop.api.spec.biz.ISpecService;
import com.fh.shop.api.spec.param.SpecInfoParam;
import com.fh.shop.api.spec.param.SpecParam;
import com.fh.shop.api.spec.param.SpecQueryParam;
import com.fh.shop.common.BaseController;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/spec")
@CrossOrigin
public class SpecController extends BaseController {


    @Resource(name="specService")
    private ISpecService specService;


    @RequestMapping(value = "/add",method=RequestMethod.POST)
    public ServerResponse add(SpecParam specParam){

        return specService.addSpec(specParam);

    }

    @RequestMapping(value = "/addSpec",method=RequestMethod.POST)
    public ServerResponse addSpec(@RequestBody List<SpecInfoParam> specInfoParamList){

        return specService.addSpecList(specInfoParamList);

    }


    @RequestMapping(value="/queryList",method = RequestMethod.POST)
    public DataTableResult queryList(SpecQueryParam specQueryParam){
        DataTableResult dataTableResult = specService.queryList(specQueryParam);
        return  dataTableResult;
    }

    @RequestMapping(value="/findSpec",method = RequestMethod.POST)
    public ServerResponse findSpec(Long id){
        return  specService.findSpec(id);

    }
    @RequestMapping(value="/updateSpec",method = RequestMethod.POST)
    public ServerResponse updateSpec(SpecParam specParam){
        return  specService.updateSpec(specParam);

    }
    @RequestMapping(value="/deleteSpec",method = RequestMethod.POST)
    public ServerResponse deleteSpec(Long id){
        return  specService.deleteSpec(id);

    }
    @RequestMapping(value="/deleteBatch",method = RequestMethod.POST)
    public ServerResponse deleteBatch(String  ids){
        return  specService.deleteBatch(ids);

    }




}
