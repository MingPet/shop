package com.fh.shop.api.sku.controller;


import com.fh.shop.api.sku.biz.ISkuService;
import com.fh.shop.common.BaseController;
import com.fh.shop.common.ServerResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin
public class SkuController extends BaseController {

    @Resource(name="skuService")
    private ISkuService skuService;

    @GetMapping("/skus")
    public ServerResponse findList(){

        return skuService.findList();
    }
}
