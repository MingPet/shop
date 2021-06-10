package com.fh.shop.admin.vo.goods;

import com.fh.shop.admin.po.goods.Sku;
import com.fh.shop.admin.po.goods.Spu;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class GoodsVo implements Serializable {


    private Spu spu = new Spu();

    private List<Sku> skuList = new ArrayList<>();
}
