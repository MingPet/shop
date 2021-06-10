package com.fh.shop.api.addr.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.common.Constants;
import com.fh.shop.api.addr.biz.IOrderService;
import com.fh.shop.api.addr.po.Order;
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
@RequestMapping("/api/order")
@Api(tags = "收件人接口")
public class OrderController {
    @Autowired
    private HttpServletRequest request;

    @Resource(name = "orderService")
    private IOrderService orderService;

    //添加收货人
    @Check
    @PostMapping("/addConsignee")
    @ApiOperation("收货人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth", value = "头信息", dataType = "java.lang.String", required = true, paramType = "header")
    })
    public ServerResponse addConsignee(Order order){
        //获取登录后的会员id
        MemberVo memberVo = (MemberVo) request.getAttribute(Constants.CURR_MEMBER);
        Long memberId = memberVo.getId();
        return orderService.addConsignee(memberId,order);
    }

    //查询收件人地址信息
    @Check
    @PostMapping("/findAddConsignee")
    @ApiOperation("查询收件人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth", value = "头信息", dataType = "java.lang.String", required = true, paramType = "header")
    })
    public ServerResponse findAddConsignee(){
        MemberVo memberVo = (MemberVo) request.getAttribute(Constants.CURR_MEMBER);
        Long memberId = memberVo.getId();
        return orderService.findAddConsignee(memberId);
    }

    //更改数据库状态
    @Check
    @PostMapping("/updateStatus")
    @ApiOperation("更改数据库状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth", value = "头信息", dataType = "java.lang.String", required = true, paramType = "header")
    })
    public ServerResponse updateStatus(Long id){
        MemberVo memberVo = (MemberVo) request.getAttribute(Constants.CURR_MEMBER);
        Long memberId = memberVo.getId();
        return orderService.updateStatus(memberId,id);
    }

    //删除收件人信息
    @Check
    @DeleteMapping("/delete")
    @ApiOperation("删除收件人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth", value = "头信息", dataType = "java.lang.String", required = true, paramType = "header")
    })
    public ServerResponse delete(Long id){
        return orderService.delete(id);
    }

    //回显
    @Check
    @GetMapping("/findListById")
    @ApiOperation("回显收件人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth", value = "头信息", dataType = "java.lang.String", required = true, paramType = "header")
    })
    public ServerResponse findListById(Long id){
        return orderService.findListById(id);
    }

    //修改
    @Check
    @PostMapping("/updateOrder")
    @ApiOperation("修改收件人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth", value = "头信息", dataType = "java.lang.String", required = true, paramType = "header")
    })
    public ServerResponse updateOrder(Order order){
        return orderService.updateOrder(order);
    }



}
