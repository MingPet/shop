package com.fh.shop.api.order.biz;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fh.shop.api.addr.mapper.IOrderMapper;
import com.fh.shop.api.addr.po.Order;
import com.fh.shop.api.mq.OrderProducer;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.vo.CartVo;
import com.fh.shop.api.common.Constants;
import com.fh.shop.api.common.KeyUtil;
import com.fh.shop.api.exception.ShopException;
import com.fh.shop.api.order.param.OrderInfoParam;
import com.fh.shop.api.order.vo.OrderInfoVo;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.mapper.IOrderInfoMapper;
import com.fh.shop.mapper.IOrderItemMapper;
import com.fh.shop.mapper.ISkuMapper;
import com.fh.shop.po.OrderInfo;
import com.fh.shop.po.OrderItem;
import com.fh.shop.po.Sku;
import com.fh.shop.util.DateUtil;
import com.fh.shop.util.RedisUtil;
import com.fh.shop.vo.CartItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("orderInfoService")
@Transactional(rollbackFor = Exception.class)
public class IOrderInfoServiceImpl implements IOrderInfoService {

    @Autowired
    private IOrderInfoMapper orderInfoMapper;
    @Autowired
    private IOrderItemMapper orderItemMapper;
    @Autowired
    private IOrderMapper orderMapper;
    @Autowired
    private ISkuMapper skuMapper;
    @Autowired
    private OrderProducer orderProducer;


    @Override
    public ServerResponse addOrder(OrderInfoParam orderInfoParam) {

        Long orderId1 = orderInfoParam.getOrderId();
        if(orderId1 == null){
            return ServerResponse.error(ResponseEnum.ORDER_SJR_IS_NULL);
        }

        //先减库存 sku表中的库存  库存不足 就不要再下单
        //update t_sku set stock=stock -#{count} where id = skuId and stock >= #{count}

        //根据会员id从redis中取出购物车数据

        Long memberId = orderInfoParam.getMemberId();



        String cartJson = RedisUtil.hget(KeyUtil.buildCartkey(memberId), Constants.CART_JSON_FIELD);
        CartVo cartVo = JSON.parseObject(cartJson, CartVo.class);
        List<CartItemVo> cartItemVoList = cartVo.getCartItemVoList();
        for (CartItemVo cartItemVo : cartItemVoList) {
            //先查数据库
            Sku sku = skuMapper.selectById(cartItemVo.getSkuId());
            if(sku.getStock() < cartItemVo.getCount()){//库存小于传过来的数量  说明商品库存不足
                //抛异常 回滚  并给前台提示
                throw new ShopException(ResponseEnum.ORDER_INFO_STOCK_IS_ERROR);
            }
            //更新库存
            int count = skuMapper.updateStock(cartItemVo);
            if(count == 0 ){//受影响的行数
                throw new ShopException(ResponseEnum.ORDER_INFO_STOCK_IS_ERROR);
            }
        }

        //插入订单表
        OrderInfo orderInfo = new OrderInfo();

        //雪花生成算法
        String orderInfoId = IdWorker.getIdStr();
        orderInfo.setId(orderInfoId);
        orderInfo.setMemberId(memberId);
        orderInfo.setCreateTime(new Date());
        orderInfo.setPayType(1);
        orderInfo.setStatus(SystemConstant.ORDER_STATUS.WATT_PAY);
        orderInfo.setTotalCount(cartVo.getTotalCount());
        orderInfo.setTotalPrice(new BigDecimal(cartVo.getTotalPrice()));

        Long orderId = orderInfoParam.getOrderId();//收件人id
        Order order = orderMapper.selectById(orderId);
        orderInfo.setRecipientAddr(order.getReceiverAddress());//收件人地址
        orderInfo.setRecipientName(order.getAddressee());//收件人
        orderInfo.setRecipientPhone(order.getPhone());//收件人手机号

        orderInfoMapper.insert(orderInfo);

        //插入订单详细表


        List<OrderItem> OrderItemList = new ArrayList<>();
        cartItemVoList.stream().forEach(x -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderInfoId(orderInfoId);
            orderItem.setMemberId(memberId);
            orderItem.setSkuCount(x.getCount());
            orderItem.setSkuId(x.getSkuId());
            orderItem.setSkuImage(x.getSkuImage());
            orderItem.setSkuName(x.getSkuName());
            orderItem.setSkuPrice(new BigDecimal(x.getPrice()));
            orderItem.setSubPrice(new BigDecimal(x.getSubPrice()));

            OrderItemList.add(orderItem);
        });
        if(OrderItemList.size() > 0){
            orderItemMapper.addItem(OrderItemList);
        }
        //插入成功之后  清空购物车中的数据redis
        RedisUtil.delete(KeyUtil.buildCartkey(memberId));
        //发送消息到中间件
        orderProducer.sendOrderMsg(orderInfoId,30*1000+"");


        return ServerResponse.success();
    }

    @Override
    public ServerResponse findOrderInfo(Long memberId) {
        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        orderInfoQueryWrapper.eq("memberId",memberId);
        List<OrderInfo> orderInfos = orderInfoMapper.selectList(orderInfoQueryWrapper);
        List<OrderInfoVo> orderInfoVoList = orderInfos.stream().map(x -> {
            OrderInfoVo orderInfoVo = new OrderInfoVo();
            orderInfoVo.setId(x.getId());
            orderInfoVo.setCreateTime(DateUtil.date2str(x.getCreateTime(),DateUtil.FULL_YEAR));
            orderInfoVo.setRecipientName(x.getRecipientName());
            orderInfoVo.setRecipientAddr(x.getRecipientAddr());
            orderInfoVo.setRecipientPhone(x.getRecipientPhone());
            orderInfoVo.setStatus(x.getStatus());
            orderInfoVo.setTotalCount(x.getTotalCount());
            orderInfoVo.setTotalPrice(x.getTotalPrice().toString());
            return orderInfoVo;
        }).collect(Collectors.toList());
        return ServerResponse.success(orderInfoVoList);
    }


}
