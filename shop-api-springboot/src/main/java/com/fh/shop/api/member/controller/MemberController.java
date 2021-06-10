package com.fh.shop.api.member.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.common.Constants;
import com.fh.shop.api.common.KeyUtil;
import com.fh.shop.api.member.biz.IMemberService;
import com.fh.shop.api.member.param.MemberParam;
import com.fh.shop.api.vo.MemberVo;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Api(tags = "会员注册接口")
public class MemberController {

    @Resource(name = "memberService")
    private IMemberService memberService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Value("${active.success}")
    private String success;

    @Value("${active.error}")
    private String error;

    @PostMapping("/member/addMember")
    public ServerResponse addMember(MemberParam memberParam) {

        return memberService.addMember(memberParam);
    }

    @PostMapping("/member/login")
    @ApiOperation("登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberName",value = "会员名",dataType = "java.lang.String",required = true),
            @ApiImplicitParam(name = "password",value = "密码",dataType = "java.lang.String",required = true)
    })

    public ServerResponse login(String memberName, String password) {
        return memberService.login(memberName, password);
    }

    //验证会员名
    @GetMapping("/member/checkMemberName")
    @ApiOperation("验证会员名")
    public ServerResponse checkMemberName(String memberName) {
        return memberService.checkMemberName(memberName);
    }

    //验证手机号
    @GetMapping("/member/checkPhone")
    @ApiOperation("验证手机号")
    public ServerResponse checkPhone(String phone) {
        return memberService.checkPhone(phone);
    }

    //验证邮箱
    @GetMapping("/member/checkMail")
    @ApiOperation("验证邮箱")
    public ServerResponse checkMail(String mail) {
        return memberService.checkMail(mail);
    }

    //获取用户信息
    @GetMapping("/member/findMember")
    @Check
    @ApiOperation("获取用户信息")
    public ServerResponse findMember() {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constants.CURR_MEMBER);
        return ServerResponse.success(memberVo);
    }

    //注销
    @GetMapping("/member/logout")
    @ApiOperation("注销")
    @Check
    public ServerResponse logout() {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constants.CURR_MEMBER);

        RedisUtil.delete(KeyUtil.buildMemberKey(memberVo.getId()));
        return ServerResponse.success();
    }

    //验证邮箱
    @GetMapping("/member/checkMaiRetrievePassword")
    public ServerResponse checkMaiRetrievePassword(String mail) {

        return memberService.checkMaiRetrievePassword(mail);
    }

    @ApiOperation("找回密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mail",value = "邮箱",dataType = "java.lang.String",required = true),
            @ApiImplicitParam(name = "code",value = "验证码",dataType = "java.lang.String",required = true),
            @ApiImplicitParam(name = "imgKey",value = "图片key",dataType = "java.lang.String",required = true),
    })
    @PostMapping("/member/findPassword")
    public  ServerResponse findPassword(String mail,String code,String imgKey){
        return memberService.findPassword(mail,code,imgKey);
    }

    //修改密码
    @PostMapping ("/member/updatePassword")
    @ApiOperation("修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword",value = "旧密码",dataType = "java.lang.String",required = true),
            @ApiImplicitParam(name = "newPassword",value = "新密码",dataType = "java.lang.String",required = true),
            @ApiImplicitParam(name = "confirmPassword",value = "确认密码",dataType = "java.lang.String",required = true),
            @ApiImplicitParam(name = "x-auth",value = "请求头信息",dataType = "java.lang.String",required = true,paramType = "header"),
    })
    @Check
    public ServerResponse updatePassword(String oldPassword,String newPassword,String confirmPassword){
        //获取id
        MemberVo memberVo = (MemberVo) request.getAttribute(Constants.CURR_MEMBER);
        Long id = memberVo.getId();
        return memberService.updatePassword(id,oldPassword,newPassword,confirmPassword);
    }

    //激活账号
    @ApiOperation("激活账号")
    @GetMapping("/member/activeMember")
    public void activeMember(String id) throws IOException {
        int result = memberService.activeMember(id);

        if(result == Constants.REQUEST_ERROR){
            //错误 重定向到指定的页面
            response.sendRedirect(error);
        }else {
            //成功 重定向到指定的页面
            response.sendRedirect(success);
        }

    }

    //登录未激活发送邮箱
    @PostMapping("/member/sendActiveMail")
    public ServerResponse sendActiveMail(@RequestBody Map<String,String> map){
        String id = map.get("id");
        String mail = map.get("mail");
        return memberService.sendActiveMail(mail,id);
    }

    //登录未激活发送邮箱
    @PostMapping("/member/sendActiveMail2")
    public ServerResponse sendActiveMail2(String id,String mail){
        return memberService.sendActiveMail(id,mail);
    }


}
