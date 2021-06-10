package com.fh.shop.admin.biz.user;

import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.param.UserQueryParam;
import com.fh.shop.admin.po.user.User;

public interface IUserService  {
    User findUserByUserName(String userName);


    DataTableResult findList(UserQueryParam user);

    ServerResponse addUser(User user);

    ServerResponse delete(Long id);

    ServerResponse deleteBatch(String ids);

    ServerResponse findUserById(Long id);

    ServerResponse importExcel(String filePath);

    void update(User userDB);
}
