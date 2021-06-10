package com.fh.shop.vo;

import com.fh.shop.vo.CartItemVo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CartVo implements Serializable {

    private List<CartItemVo> cartItemVoList = new ArrayList<>();

    private Long totalCount;//已选择几件商品

    private String totalPrice;//总价
}
