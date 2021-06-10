package com.fh.shop.api.addr.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.Constants;
import com.fh.shop.api.addr.mapper.IOrderMapper;
import com.fh.shop.api.addr.po.Order;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

@Service("orderService")
@Transactional(rollbackFor = Exception.class)
public class IOrderServiceImpl implements IOrderService {


    @Autowired
    private IOrderMapper orderMapper;


    @Override
    public ServerResponse addConsignee(Long memberId, Order order) {
        // 判断字段是否非空
        String addressee = order.getAddressee();
        String receiverAddress = order.getReceiverAddress();
        String phone = order.getPhone();
        if(StringUtils.isEmpty(addressee) || StringUtils.isEmpty(receiverAddress) || StringUtils.isEmpty(phone) ){
            return ServerResponse.error(ResponseEnum.ORDER_ADD_CONSIGNEE_IS_NULL);
        }
        //判断手机格式
        Pattern pattern = Pattern.compile("^[1]\\d{10}$");
        boolean matches = pattern.matcher(phone).matches();
        if(matches != true){
            return ServerResponse.error(ResponseEnum.MEMBER_PHONE_IS_ERROR);
        }

        Order orderInfo = new Order();
        orderInfo.setAddressee(addressee);
        orderInfo.setReceiverAddress(receiverAddress);
        orderInfo.setPhone(phone);
        orderInfo.setMemberId(memberId);
        orderMapper.insert(orderInfo);
        return ServerResponse.success();
    }

    @Transactional(readOnly = true)
    @Override
    public ServerResponse findAddConsignee(Long memberId) {
        //OrderVo orderVo = new OrderVo();
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("memberId",memberId);
        List<Order> orderList = orderMapper.selectList(orderQueryWrapper);
        return ServerResponse.success(orderList);
    }

    @Override
    public ServerResponse updateStatus(Long memberId, Long id) {
        //先重置
        Order order = new Order();
        order.setStatus(Constants.STATUS_NO);//非默认
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("memberId",memberId);
        orderMapper.update(order,orderQueryWrapper);

        //再更新
        Order order1 = new Order();
        order1.setId(id);
        order1.setStatus(Constants.STATUS_YES);
        orderMapper.updateById(order1);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse delete(Long id) {
        //删除收件人信息
        orderMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findListById(Long id) {
        Order order = orderMapper.selectById(id);
        return ServerResponse.success(order);
    }

    @Override
    public ServerResponse updateOrder(Order order) {
        orderMapper.updateById(order);
        return ServerResponse.success();
    }
}
