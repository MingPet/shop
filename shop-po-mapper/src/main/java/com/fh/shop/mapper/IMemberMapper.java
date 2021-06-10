package com.fh.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.po.Member;
import org.apache.ibatis.annotations.Param;

public interface IMemberMapper extends BaseMapper<Member> {

    void updateIntegral(@Param("memberId") Long memberId, @org.apache.ibatis.annotations.Param("totalPrice") double totalPrice);
}
