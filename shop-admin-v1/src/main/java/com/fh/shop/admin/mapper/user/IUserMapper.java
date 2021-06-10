package com.fh.shop.admin.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.param.UserQueryParam;
import com.fh.shop.admin.po.user.User;

import java.util.List;

public interface IUserMapper extends BaseMapper<User> {
    User findUserByUserName(String userName);

    Long findListCount(UserQueryParam userQueryParam);

    List<User> findPageList(UserQueryParam userQueryParam);

    User findUserById(Long id);

    void addBatch(List<User> userList);
}
