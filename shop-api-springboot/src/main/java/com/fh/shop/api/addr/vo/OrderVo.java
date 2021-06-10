package com.fh.shop.api.addr.vo;

import com.fh.shop.api.addr.po.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderVo implements Serializable {
    private List<Order> orderVoList = new ArrayList<>();
}
