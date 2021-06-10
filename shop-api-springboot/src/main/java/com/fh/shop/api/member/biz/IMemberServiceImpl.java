package com.fh.shop.api.member.biz;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.Constants;
import com.fh.shop.api.common.KeyUtil;
import com.fh.shop.api.member.param.MemberParam;
import com.fh.shop.api.mq.MailProducer;
import com.fh.shop.api.util.MailUtil;
import com.fh.shop.api.vo.MemberVo;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.mapper.IMemberMapper;
import com.fh.shop.mq.MailMessage;
import com.fh.shop.po.Member;
import com.fh.shop.util.Md5Util;
import com.fh.shop.util.RedisUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("memberService")
@Transactional(rollbackFor = Exception.class)
public class IMemberServiceImpl implements IMemberService {

    @Autowired
    private IMemberMapper memberMapper;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private MailProducer mailProducer;

    @Override
    public ServerResponse addMember(MemberParam memberParam) {

        //判断字段非空
        String memberName = memberParam.getMemberName();
        String password = memberParam.getPassword();
        String confirmPassword = memberParam.getConfirmPassword();
        String nickName = memberParam.getNickName();
        String code = memberParam.getCode();
        String phone = memberParam.getPhone();
        String mail = memberParam.getMail();

            if(StringUtils.isEmpty(memberName) || StringUtils.isEmpty(password)
                                                || StringUtils.isEmpty(confirmPassword)
                                                || StringUtils.isEmpty(nickName)
                                                || StringUtils.isEmpty(code)
                                                || StringUtils.isEmpty(phone)
                                                || StringUtils.isEmpty(mail)

                    ){
                return ServerResponse.error(ResponseEnum.MEMBER_INFO_IS_NULL);

            }


        //判断手机格式
        Pattern pattern = Pattern.compile("^[1]\\d{10}$");
        boolean matches = pattern.matcher(phone).matches();
        if(matches != true){
            return ServerResponse.error(ResponseEnum.MEMBER_PHONE_IS_ERROR);
        }

        //两次密码是否一致
        if(!confirmPassword.equals(password)){
            return ServerResponse.error(ResponseEnum.TWO_PASSWORD_IS_ERROR);
        }

        //判断短信验证码是否正确（前台输入的验证码是否和redis的一样）
        String result = RedisUtil.get(phone);
        if(StringUtils.isEmpty(result) || !result.equals(code)){
            //缓存中的验证码为空  或者不一致
            return ServerResponse.error(ResponseEnum.MEMBER_SMS_CODE_IS_ERROR);
        }

        //账号是否唯一（是否已存在）
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("memberName",memberName);
        Member member = memberMapper.selectOne(queryWrapper);
        if(member!=null){
            return ServerResponse.error(ResponseEnum.MEMBER_INFO_IS_EXIST);
        }

        //添加会员
        Member memberInfo = new Member();
        memberInfo.setMail(mail);
        memberInfo.setMemberName(memberName);
        memberInfo.setNickName(nickName);
        memberInfo.setPassword(Md5Util.md5(password));
        memberInfo.setPhone(phone);
        memberMapper.insert(memberInfo);
        //移除redis缓存 释放资源
        RedisUtil.delete(phone);
        //注册成功后 发送验证码
        //将随机生成的uuid作为唯一标识 当作key 存到redis中
        String uuid = UUID.randomUUID().toString();
        String key = KeyUtil.buildActiveMemberkey(uuid);
        //获取id
        Long id = memberInfo.getId();
        RedisUtil.setEx(key,id+"",10*60);

        //mailUtil.sendMailHtml(mail,"飞狐乐购平台通知-激活邮件","<h1>请您进行<a href='http://localhost:8089/api/member/activeMember?id="+id+"'>激活</a>后，再登录</h1>");
        //异步发送邮件
        MailMessage mailMessage = new MailMessage();
        mailMessage.setTo(mail);
        mailMessage.setTitle("飞狐乐购平台通知-激活邮件");
        mailMessage.setConent("<h1>请您进行<a href='http://localhost:8089/api/member/activeMember?id="+id+"'>激活</a>后，再登录</h1>");
        mailProducer.sendMail(mailMessage);
        return ServerResponse.success();
    }

    //登录
    @Override
    public ServerResponse login(String memberName, String password) {

        //非空验证
        if(StringUtils.isEmpty(memberName) || StringUtils.isEmpty(password)){
            return ServerResponse.error(ResponseEnum.MEMBER_LOGIN_INFO_IS_EXIST);
        }


        //验证用户名是否正确
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("memberName",memberName);
        Member member = memberMapper.selectOne(memberQueryWrapper);
        if(member == null){
            return ServerResponse.error(ResponseEnum.MEMBER_LOGIN_MEMBER_NAME_NOT_EXIST);
        }

        //验证密码是否正确
        if(!Md5Util.md5(password).equals(member.getPassword())){
            return ServerResponse.error(ResponseEnum.MEMBER_LOGIN_PASSWORD_IS_ERROR);
        }

        //
        String status = member.getStatus();
        //未激活
        if(status.equals(Constants.INACTIVE_STATUS)){
            //获取mail 和 生成uuid 作为错误信息传递
            String mail = member.getMail();

            Map<String,String> result = new HashMap<>();
            result.put("mail",mail);
            result.put("id",member.getId()+"");
            return ServerResponse.error(ResponseEnum.MEMBER_OLD_INACTIVE,result);
        }

        //创建MemberVo
        MemberVo memberVo = new MemberVo();
        Long id = member.getId();
        memberVo.setId(id);
        memberVo.setMemberName(member.getMemberName());
        memberVo.setNickName(member.getNickName());
        //将用户信息转为json字符串
        String memberVoJson = com.alibaba.fastjson.JSON.toJSONString(memberVo);
        //生成签名
        String sign = Md5Util.sign(memberVoJson,Constants.SECRET);

        //将用户信息进行base64编码
        String memberVoJsonBase64 = Base64.getEncoder().encodeToString(memberVoJson.getBytes());
        //将签名进行base64编码
        String signBase64 = Base64.getEncoder().encodeToString(sign.getBytes());

        //将信息存入redis中
        RedisUtil.setEx(KeyUtil.buildMemberKey(id),"",Constants.TOKEN_EXPIRE);

        //把用户信息和签名相应给前台 相应给前台的信息中不能有密码
        //将用户信息和签名通过","分割 组成一个字符串传给前台
        return ServerResponse.success(memberVoJsonBase64 +"," +signBase64);
    }

    @Override
    public ServerResponse checkMemberName(String memberName) {
        //会员名是否为空
        if(StringUtils.isEmpty(memberName)){
            return ServerResponse.error(ResponseEnum.MEMBER_MEMBER_NAME_IS_NULL);
        }
        //会员名是否已存在 唯一
        QueryWrapper<Member> memberNameQueryWrapper = new QueryWrapper<>();
        memberNameQueryWrapper.eq("memberName", memberName);
        Member member = memberMapper.selectOne(memberNameQueryWrapper);
        if(member != null){
            return ServerResponse.error(ResponseEnum.MEMBER_INFO_IS_EXIST);
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse checkPhone(String phone) {
        //判断手机格式
        Pattern pattern = Pattern.compile("^[1]\\d{10}$");
        boolean matches = pattern.matcher(phone).matches();
        if(matches != true){
            return ServerResponse.error(ResponseEnum.MEMBER_PHONE_IS_ERROR);
        }
        //手机非空
        if(StringUtils.isEmpty(phone)){
            return ServerResponse.error(ResponseEnum.MEMBER_PHONE_IS_NULL);
        }
        //手机号是否已存在  唯一
        QueryWrapper<Member> phoneQueryWrapper = new QueryWrapper<>();
        phoneQueryWrapper.eq("phone", phone);
        Member member = memberMapper.selectOne(phoneQueryWrapper);
        if(member != null){
            return ServerResponse.error(ResponseEnum.MEMBER_PHONE_IS_EXIST);
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse checkMail(String mail) {
        //邮箱非空
        if (StringUtils.isEmpty(mail)) {
            return ServerResponse.error(ResponseEnum.MEMBER_MAIL_IS_NULL);
        }
        //邮箱是否已存在  唯一
        QueryWrapper<Member> mailQueryWrapper = new QueryWrapper<>();
        mailQueryWrapper.eq("mail", mail);
        Member member = memberMapper.selectOne(mailQueryWrapper);
        if (member != null) {
            return ServerResponse.error(ResponseEnum.MEMBER_MAIL_IS_EXIST);
        }

        //验证邮箱格式
        String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(mail);
        if(!matcher.matches()){
            //邮箱格式不正确
            return ServerResponse.error(ResponseEnum.MEMBER_MAIL_FORMAT_ERROR);
        }

        return ServerResponse.success();
    }

    @Override
    public ServerResponse checkMaiRetrievePassword(String mail) {
        //邮箱非空
        if (StringUtils.isEmpty(mail)) {
            return ServerResponse.error(ResponseEnum.MEMBER_MAIL_IS_NULL);
        }


        //验证邮箱格式
        String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(mail);
        if(!matcher.matches()){
            //邮箱格式不正确
            return ServerResponse.error(ResponseEnum.MEMBER_MAIL_FORMAT_ERROR);
        }

        //验证邮箱是否存在
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("mail",mail);
        Member member = memberMapper.selectOne(memberQueryWrapper);
        if(member == null){
            return ServerResponse.error(ResponseEnum.MEMBER_FIND_PASSWORD_MAIL_ERROR);
        }

       // String code = RandomStringUtils.randomAlphanumeric(4);
        //发送邮件
        //mailUtil.sendMailHtml(mail,"飞狐乐购平台通知","您的验证码为："+code+",请登录之后尽快修改！");


        return ServerResponse.success();
    }

    //找回密码
    @Override
    public ServerResponse findPassword(String mail, String code, String imgKey) {
        //信息非空
        if(StringUtils.isEmpty(mail) || StringUtils.isEmpty(code) || StringUtils.isEmpty(imgKey)){
            return ServerResponse.error(ResponseEnum.MEMBER_FIND_PASSWORD_IS_NULL);
        }

        //先验证验证码
        String imgCode = RedisUtil.get(KeyUtil.buildImageCodekey(imgKey));
        if(!code.equals(imgCode)){
            return ServerResponse.error(ResponseEnum.MEMBER_FIND_PASSWORD_CODE_ERROR);
        }


        //验证邮箱格式
        String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(mail);
        if(!matcher.matches()){
            //邮箱格式不正确
            return ServerResponse.error(ResponseEnum.MEMBER_MAIL_FORMAT_ERROR);
        }
        //验证邮箱是否存在
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("mail",mail);
        Member member = memberMapper.selectOne(memberQueryWrapper);
        if(member == null){
            return ServerResponse.error(ResponseEnum.MEMBER_FIND_PASSWORD_MAIL_ERROR);
        }


        String newPassword = RandomStringUtils.randomAlphanumeric(6);
        Member member1 = new Member();
        member1.setId(member.getId());
        member1.setPassword(Md5Util.md5(newPassword));
        memberMapper.updateById(member1);

        //发送邮件
        //mailUtil.sendMailHtml(mail,"飞狐乐购平台通知-找回密码","您的临时密码为："+newPassword+",请登录之后尽快修改！");
        MailMessage mailMessage = new MailMessage();
        mailMessage.setTo(mail);
        mailMessage.setTitle("飞狐乐购平台通知-找回密码");
        mailMessage.setConent("您的临时密码为："+newPassword+",请登录之后尽快修改！");
        mailProducer.sendMail(mailMessage);


        //清除redis
        RedisUtil.delete(KeyUtil.buildImageCodekey(imgKey));
        return ServerResponse.success();
    }

    @Override
    public ServerResponse updatePassword(Long id, String oldPassword, String newPassword, String confirmPassword) {
        //非空验证
        if(StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(confirmPassword)){
            return ServerResponse.error(ResponseEnum.MEMBER_UPDATE_PASSWORD_IS_NULL);
        }
        //验证新密码和确认密码是否相同
        if(!newPassword.equals(confirmPassword)){
            return ServerResponse.error(ResponseEnum.MEMBER_UPDATE_PASSWORD_TWO_IS_ERROR);
        }
        //判断旧密码是否和数据库中一致 需要查询数据库所以代码放在后面验证
        Member member = memberMapper.selectById(id);//根据id查询用户信息
        //将前端传过来的密码 进行md5加密后与数据库存的密码进行对比
        if(!Md5Util.md5(oldPassword).equals(member.getPassword())){
            return ServerResponse.error(ResponseEnum.MEMBER_OLD_PASSWORD_IS_ERROR);
        }
        //更新密码
        Member member1 = new Member();
        member1.setId(id);
        member1.setPassword(Md5Util.md5(newPassword));
        memberMapper.updateById(member1);
        //注销  删除redis
        RedisUtil.delete(KeyUtil.buildMemberKey(id));
        //响应
        return ServerResponse.success();
    }

    @Override
    public int activeMember(String id) {
        //判断redis中是否有该key
        String result = RedisUtil.get(KeyUtil.buildActiveMemberkey(id));
        if(StringUtils.isEmpty(result)){
            //错误：提示错误信息 请求错误
            return Constants.REQUEST_ERROR;

        }
        //更新数据库
        Member member = new Member();
        member.setId(Long.parseLong(result));
        member.setStatus(Constants.ACTIVE_STATUS);
        memberMapper.updateById(member);
        //删除redis
        RedisUtil.delete(KeyUtil.buildActiveMemberkey(id));
        return Constants.REQUEST_SUCCESS;


    }

    @Override
    public ServerResponse sendActiveMail(String mail, String id) {
        //非空验证
        if(StringUtils.isEmpty(mail) || StringUtils.isEmpty(id)){
            return ServerResponse.error(ResponseEnum.MEMBER_ACTIVE_MAIL_IS_NULL);
        }
        //存到redis 然后再发邮件
        String uuid = UUID.randomUUID().toString();
        RedisUtil.setEx(KeyUtil.buildActiveMemberkey(uuid),id,10*60);
        //发送邮件
        mailUtil.sendMailHtml(mail,"飞狐乐购平台通知-激活邮件","<h1>请您进行<a href='http://localhost:8089/api/member/activeMember?id="+id+"'>激活</a>后，再登录</h1>");
        return ServerResponse.success();

    }

}
