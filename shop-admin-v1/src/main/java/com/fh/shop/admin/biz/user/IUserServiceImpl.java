package com.fh.shop.admin.biz.user;

import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.mapper.user.IUserMapper;
import com.fh.shop.admin.param.UserQueryParam;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.util.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("userService")
public class IUserServiceImpl implements IUserService {
    @Autowired
    private IUserMapper userMapper;


    @Override
    public User findUserByUserName(String userName) {
        User user = userMapper.findUserByUserName(userName);
        return user;
    }

    @Override
    public DataTableResult findList(UserQueryParam userQueryParam) {
        Long count = userMapper.findListCount(userQueryParam);
        List<User> userList = userMapper.findPageList(userQueryParam);

        return new DataTableResult(userQueryParam.getDraw(),count,count,userList);
    }

    @Override
    public ServerResponse addUser(User user) {
        String password = user.getPassword();
        String confirmPassword = user.getConfirmPassword();
        if(StringUtils.isEmpty(password) || StringUtils.isEmpty(confirmPassword)){
            return ServerResponse.error(ResponseEnum.PASSWORD_IS_EMPTY);
        }
        if(!confirmPassword.equals(password)){
            return ServerResponse.error(ResponseEnum.PASSWORD_IS_ERROR);
        }
        //生成salt（盐）
        String salt = UUID.randomUUID().toString();

        //将明文密码进行加密
        String md5Password = Md5Util.md5(Md5Util.md5(password)+","+salt);
        user.setPassword(md5Password);
        user.setSalt(salt);
        userMapper.insert(user);

        return ServerResponse.success();
    }

    @Override
    public ServerResponse delete(Long id) {
        userMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBatch(String ids) {
        if(StringUtils.isEmpty(ids)){
            return ServerResponse.error();
        }
        String[] idArr = ids.split(",");
        List<Long> idList = new ArrayList<>();
        for (String s : idArr) {
            idList.add(Long.parseLong(s));
        }
        userMapper.deleteBatchIds(idList);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findUserById(Long id) {
        User user =  userMapper.findUserById(id);
        //User user1 = userMapper.selectById(id);
        return ServerResponse.success(user);
    }

    @Override
    public ServerResponse importExcel(String filePath) {

        FileInputStream fileInputStream = null;
        try {
            //通过poi解析服务端硬盘上的文件  获取数据
             fileInputStream = new FileInputStream(filePath);
            XSSFWorkbook workbook  = new XSSFWorkbook(fileInputStream);
            //获取sheet页
            XSSFSheet sheet = workbook.getSheetAt(0);
            //获取该sheet页  数据的最后一行
            int lastRowNum = sheet.getLastRowNum();
            //循环获取每行数据


            List<User> userList = new ArrayList<>();
            for (int i = 1; i <= lastRowNum ; i++) {
                //读取行中的每列数据
                int count = 0;
                XSSFRow row = sheet.getRow(i);
                String userName = row.getCell(count++).getStringCellValue();
                String realName = row.getCell(count++).getStringCellValue();
                String sex = row.getCell(count++).getStringCellValue();
                String phone = row.getCell(count++).getStringCellValue();
                String mail = row.getCell(count++).getStringCellValue();
                Date birthDay = row.getCell(count++).getDateCellValue();

                User user = new User();
                user.setUserName(userName);
                user.setRealName(realName);
                user.setSex(sex.equals("男")?1:2);
                user.setPhone(phone);
                user.setMail(mail);
                user.setBirthDay(birthDay);
                userList.add(user);
            }
            userMapper.addBatch(userList);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                    fileInputStream = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            File file = new File(filePath);
            if(file.exists()){
                file.delete();
            }

        }
        //将数据插入数据库
        return ServerResponse.success();
    }

    @Override
    public void update(User userDB) {
        userMapper.updateById(userDB);
    }


}
