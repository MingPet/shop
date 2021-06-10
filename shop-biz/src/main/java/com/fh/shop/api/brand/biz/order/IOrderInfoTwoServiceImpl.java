package com.fh.shop.api.brand.biz.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.mapper.IOrderInfoMapper;
import com.fh.shop.mapper.IOrderItemMapper;
import com.fh.shop.mapper.ISkuMapper;
import com.fh.shop.po.OrderInfo;
import com.fh.shop.po.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderInfoServiceTwo")
public class IOrderInfoTwoServiceImpl implements IOrderInfoTwoService {

    @Autowired
    private IOrderInfoMapper orderInfoMapper;

    @Autowired
    private IOrderItemMapper orderItemMapper;

    @Autowired
    private ISkuMapper skuMapper;

    @Override
    public ServerResponse cancelOrder(String id) {

        OrderInfo orderInfo1 = orderInfoMapper.selectById(id);
        if(orderInfo1.getStatus() != SystemConstant.ORDER_STATUS.WATT_PAY){//如果是其它状态（不是未支付状态）不让操作
            return ServerResponse.error(ResponseEnum.ORDER_STATUS_IS_ERROR);
        }
        //更新状态
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(id);
        orderInfo.setStatus(SystemConstant.ORDER_STATUS.TRAND_CLOSE);
        orderInfoMapper.updateById(orderInfo);
        //更新库存

        QueryWrapper<OrderItem> orderItemQueryWrapper = new QueryWrapper<>();
        orderItemQueryWrapper.eq("orderInfoId",id);
        List<OrderItem> orderItemList = orderItemMapper.selectList(orderItemQueryWrapper);
        orderItemList.stream().forEach(x ->{
            Long skuId = x.getSkuId();
            Long skuCount = x.getSkuCount();
            skuMapper.updateStockItem(skuId,skuCount);
        });
        return ServerResponse.success();
    }
}
