package com.fh.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.param.SkuParam;
import com.fh.shop.po.Sku;
import com.fh.shop.vo.CartItemVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISkuMapper extends BaseMapper<Sku>{

    List<Sku> findList();

    List<SkuParam> findListSku();

    int updateStock(CartItemVo cartItemVo);

    void updateStockItem(@Param("skuId") Long skuId, @Param("count") Long count);

    void updateSalesVolume(@Param("skuCount")Long skuCount, @Param("skuId")Long skuId);
}
