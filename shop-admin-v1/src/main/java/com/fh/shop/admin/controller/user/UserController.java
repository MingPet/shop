package com.fh.shop.admin.controller.user;

import com.auth0.jwt.internal.org.apache.commons.lang3.time.DateUtils;
import com.fh.shop.admin.annotation.LogInfo;
import com.fh.shop.admin.biz.user.IUserService;
import com.fh.shop.admin.controller.BaseController;
import com.fh.shop.admin.param.UserQueryParam;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.util.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class UserController extends BaseController {

    @Resource(name="userService")
    private IUserService userService;

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(info = "登录方法")
    public ServerResponse login(User user, HttpServletRequest request){
        System.out.println("login");

        //非空
        String userName = user.getUserName();
        String password = user.getPassword();
        if(StringUtils.isEmpty(userName)){
            //如果为空 返回的状态码 1000 和提示信息“用户不存在/为空”
            return ServerResponse.error(ResponseEnum.USERNAME_IS_EMPTY);
        }
        if(StringUtils.isEmpty(password)){
            //如果为空 返回的状态码 1001 和提示信息“密码为空”
            return ServerResponse.error(ResponseEnum.PASSWORD_IS_EMPTY);
        }

        //判断用户是否存在
        User userDB = userService.findUserByUserName(userName);
        if (userDB==null){
            return ServerResponse.error(ResponseEnum.USERNAME_IS_ERROR);
        }



        //判断密码是否正确

        String salt = userDB.getSalt();
        if(!userDB.getPassword().equals(Md5Util.md5(password+","+salt))){
            return ServerResponse.error(ResponseEnum.PASSWORD_IS_ERROR);
        }

        //设置登录次数
        //设置登录时间和上次登录时间
        if (userDB.getLoginCount() == null || DateUtils.isSameDay(userDB.getLoginThisTime(), new Date()) ==false) {
            userDB.setLoginCount(1);
        } else {
            userDB.setLoginCount(userDB.getLoginCount() + 1);
        }
        //获取上次登录时间
        Date userDBLastTime = userDB.getLoginThisTime();
        userDB.setLoginLastTime(userDBLastTime);
        //设置当前登录时间
        userDB.setLoginThisTime(new Date());
        userService.update(userDB);






        //登录成功  将用户信息放入session中
        request.getSession().setAttribute(SystemConstant.CURR_USER,userDB);
        return ServerResponse.success();
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        // 清除session
        request.getSession().invalidate();
        // 跳转页面
        return "redirect:/index.jsp";
    }


    @RequestMapping(value = "/user/toList", method = RequestMethod.GET)
    public String toList(){
        return "/user/list";
    }


    @RequestMapping(value = "/user/findList",method = RequestMethod.POST)
    @ResponseBody
    public DataTableResult findList(UserQueryParam userQueryParam) {
        return userService.findList(userQueryParam);
    }

    @RequestMapping(value = "/user/addUser",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addUser(User user) {
        return userService.addUser(user);
    }

    @RequestMapping(value = "/user/deleteBatch",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteBatch(String ids){
        return  userService.deleteBatch(ids);

    }

    @RequestMapping(value = "/user/delete",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse delete(Long  id){
        return userService.delete(id);

    }
    @ResponseBody
    @RequestMapping(value = "/user/findUserById",method = RequestMethod.POST)
    public ServerResponse findUserById(Long id){

        return userService.findUserById(id);
    }


    //导入excel
    @ResponseBody
    @RequestMapping(value = "/user/importExcel",method = RequestMethod.POST)
    public ServerResponse importExcel (String filePath){

        return  userService.importExcel(filePath);
    }

}
