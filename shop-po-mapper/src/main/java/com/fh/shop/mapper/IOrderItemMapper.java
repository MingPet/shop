package com.fh.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.po.OrderItem;

import java.util.List;

public interface IOrderItemMapper extends BaseMapper<OrderItem> {

    void addItem(List<OrderItem> OrderItemList);
}
