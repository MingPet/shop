package com.fh.shop.api.spec.controller;

import com.fh.shop.api.common.BaseController;
import com.fh.shop.api.spec.biz.ISpecService;
import com.fh.shop.api.spec.param.SpecInfoParam;
import com.fh.shop.api.spec.param.SpecParam;
import com.fh.shop.api.spec.param.SpecQueryParam;
import com.fh.shop.common.ServerResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/specs")
public class SpecController extends BaseController {

    @Resource(name = "specService")
    private ISpecService specService;


    @RequestMapping(value = "/addSpec",method=RequestMethod.POST)
    public ServerResponse addSpec(@RequestBody List<SpecInfoParam> specInfoParamList){

        return specService.addSpecList(specInfoParamList);

    }


    @GetMapping("/queryList")
    public ServerResponse queryList(SpecQueryParam specQueryParam){
        return specService.queryList(specQueryParam);

    }

    @GetMapping("/{id}")
    public ServerResponse findSpec(@PathVariable Long id){
        return  specService.findSpec(id);

    }

    @PutMapping
    public ServerResponse updateSpec(@RequestBody SpecInfoParam specInfoParam){
        return  specService.updateSpec(specInfoParam);
    }

    @DeleteMapping
    public ServerResponse deleteSpec(Long id){
        return  specService.deleteSpec(id);

    }
    @RequestMapping(value="/deleteBatch",method = RequestMethod.POST)
    public ServerResponse deleteBatch(String  ids){
        return  specService.deleteBatch(ids);

    }

}
