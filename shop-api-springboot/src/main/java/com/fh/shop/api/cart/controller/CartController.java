package com.fh.shop.api.cart.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.cart.biz.ICartService;
import com.fh.shop.api.common.Constants;
import com.fh.shop.api.vo.MemberVo;
import com.fh.shop.common.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/api/carts")
@RestController
@Api(tags = "购物车接口")
public class CartController {

    @Autowired
    private HttpServletRequest request;

    @Resource(name = "cartService")
    private ICartService cartService;

    //添加购物车
    @Check
    @PostMapping("/addCartItem")
    @ApiOperation("添加商品到购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "skuId", value = "商品id", dataType = "java.lang.Long", required = true),
            @ApiImplicitParam(name = "count", value = "商品数量", dataType = "java.lang.Long", required = true),
            @ApiImplicitParam(name = "x-auth", value = "头信息", dataType = "java.lang.String", required = true, paramType = "header")
    })
    public ServerResponse addCartItem(Long skuId,Long count){

        //获取登录后的会员id
        MemberVo memberVo = (MemberVo) request.getAttribute(Constants.CURR_MEMBER);
        Long memberId = memberVo.getId();
        return cartService.addCartItem(memberId,skuId,count);
    }


    //查询
    @Check
    @GetMapping("/findCart")
    @ApiOperation("查找购物车商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth", value = "头信息", dataType = "java.lang.String", required = true, paramType = "header")
    })
    public ServerResponse findCart() {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constants.CURR_MEMBER);
        Long memberId = memberVo.getId();
        return cartService.findCart(memberId);
    }

    @Check
    @GetMapping("/findCartCount")
    @ApiOperation("查找购物车商品数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth", value = "头信息", dataType = "java.lang.String", required = true, paramType = "header")
    })
    public ServerResponse findCartCount() {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constants.CURR_MEMBER);
        Long memberId = memberVo.getId();
        return cartService.findCartCount(memberId);
    }


    @Check
    @DeleteMapping("/deleteCartItem")
    @ApiOperation("删除购物车商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth", value = "头信息", dataType = "java.lang.Long", required = true, paramType = "header")
    })
    public ServerResponse deleteCartItem(Long skuId) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constants.CURR_MEMBER);
        Long memberId = memberVo.getId();
        return cartService.deleteCartItem(memberId,skuId);
    }

    @Check
    @DeleteMapping("/deleteBatchCartItem")
    @ApiOperation("批量删除购物车商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth", value = "头信息", dataType = "java.lang.String", required = true, paramType = "header")
    })
    public ServerResponse deleteBatchCartItem(String ids) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constants.CURR_MEMBER);
        Long memberId = memberVo.getId();
        return cartService.deleteBatchCartItem(memberId,ids);
    }
}
