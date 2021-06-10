package com.fh.shop.api.order.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.annotation.Token;
import com.fh.shop.api.brand.biz.order.IOrderInfoTwoService;
import com.fh.shop.api.common.Constants;
import com.fh.shop.api.order.biz.IOrderInfoService;
import com.fh.shop.api.order.param.OrderInfoParam;
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

@RestController
@RequestMapping("/api/orders")
@Api(tags = "订单接口")
public class OrderInfoController {

    @Resource(name = "orderInfoService")
    private IOrderInfoService orderInfoService;
    @Autowired
    private HttpServletRequest request;

    @Resource(name = "orderInfoServiceTwo")
    private IOrderInfoTwoService orderInfoServiceTwo;

    @PostMapping("/addOrderInfo")
    @Check
    @ApiOperation("创建订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth", value = "头信息", dataType = "java.lang.String", required = true, paramType = "header")
    })
    @Token
    public ServerResponse addOrder(OrderInfoParam orderInfoParam){
        MemberVo memberVo = (MemberVo) request.getAttribute(Constants.CURR_MEMBER);
        Long memberId = memberVo.getId();
        orderInfoParam.setMemberId(memberId);
        return orderInfoService.addOrder(orderInfoParam);
    }

    @PostMapping("/findOrderInfo")
    @Check
    @ApiOperation("查询订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth", value = "头信息", dataType = "java.lang.String", required = true, paramType = "header")
    })
    public ServerResponse findOrderInfo(){
        MemberVo memberVo = (MemberVo) request.getAttribute(Constants.CURR_MEMBER);
        Long memberId = memberVo.getId();
        return orderInfoService.findOrderInfo(memberId);

    }

    @PostMapping("/cancelOrder/{id}")
    @Check
    public ServerResponse cancelOrder(@PathVariable String id){

        return orderInfoServiceTwo.cancelOrder(id);
    }





}
