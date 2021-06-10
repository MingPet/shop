package com.fh.shop.api.member.biz;

import com.fh.shop.api.member.param.MemberParam;
import com.fh.shop.common.ServerResponse;

public interface IMemberService {
    ServerResponse addMember(MemberParam memberParam);

    ServerResponse login(String memberName, String password);

    ServerResponse checkMemberName(String memberName);

    ServerResponse checkPhone(String phone);

    ServerResponse checkMail(String mail);

    ServerResponse checkMaiRetrievePassword(String mail);

    ServerResponse findPassword(String mail, String code, String imgKey);

    ServerResponse updatePassword(Long id, String oldPassword, String newPassword, String confirmPassword);

    int activeMember(String id);

    ServerResponse sendActiveMail(String mail, String id);
}
