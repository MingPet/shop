package com.fh.shop.admin.param;

import com.fh.shop.admin.po.goods.Spu;
import lombok.Data;

import java.io.Serializable;
@Data
public class SpuParam implements Serializable {

    private Spu spu = new Spu();

    private String prices;

    private String stocks;

    private String specInfos;

    //14:a,b,c;15:d,e,f
    private String skuImages;

}
