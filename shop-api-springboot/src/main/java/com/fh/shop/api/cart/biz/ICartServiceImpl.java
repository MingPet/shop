package com.fh.shop.api.cart.biz;

import com.alibaba.fastjson.JSON;
import com.fh.shop.vo.CartVo;
import com.fh.shop.api.common.Constants;
import com.fh.shop.api.common.KeyUtil;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.mapper.ISkuMapper;
import com.fh.shop.po.Sku;
import com.fh.shop.util.BigdecimalUtil;
import com.fh.shop.util.RedisUtil;
import com.fh.shop.vo.CartItemVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service("cartService")
@Transactional(rollbackFor = Exception.class)
public class ICartServiceImpl implements ICartService {

    @Autowired
    private ISkuMapper skuMapper;

    @Value("${sku.limit}")
    private int skuLimit;
    @Override
    public ServerResponse addCartItem(Long memberId,Long skuId, Long count) {

        //商品限购
        if(count > skuLimit){
            return ServerResponse.error(ResponseEnum.CART_SKU_IS_LIMIT);
        }
        //<0 非法操作
//        if(count <= 0){
//            return ServerResponse.error(ResponseEnum.CART_SKU_ERROR);
//        }

        //商品是否存在
        Sku sku = skuMapper.selectById(skuId);
        if(sku == null){
            return ServerResponse.error(ResponseEnum.CART_SKU_IS_NULL);
        }
        //商品是否上架
        if(sku.getIsUp().equals(Constants.ISUP_DOWN)){
            return ServerResponse.error(ResponseEnum.CART_IS_UP_DOWN);
        }
        //库存是否大于够买数量
        Integer stock = sku.getStock();
        if(stock.intValue() < count){
            return ServerResponse.error(ResponseEnum.CART_SKU_STOCK_IS_ERROR);
        }

        //会员是否有购物车
        String key = KeyUtil.buildCartkey(memberId);
        boolean exist = RedisUtil.exist(key);
        //如果没有购物车，创建一个购物车，把商品直接添加进去
        if(!exist){
            //如果没有购物车
            if(count < 0){
                return ServerResponse.error(ResponseEnum.CART_SKU_ERROR);
            }
            CartVo cartVo = new CartVo();
            CartItemVo cartItemVo = new CartItemVo();
            cartItemVo.setSkuImage(sku.getImage());
            cartItemVo.setSkuName(sku.getSkuName());
            String price = sku.getPrice().toString();
            cartItemVo.setPrice(price);
            cartItemVo.setCount(count);
            cartItemVo.setSkuId(sku.getId());
            BigDecimal subPrice = BigdecimalUtil.mul(price, count+"");
            cartItemVo.setSubPrice(subPrice.toString());

            cartVo.getCartItemVoList().add(cartItemVo);
            cartVo.setTotalCount(count);
            cartVo.setTotalPrice(cartItemVo.getSubPrice());
            //更新购物车
            //RedisUtil.set(key,JSON.toJSONString(cartVo));
            RedisUtil.hset(key,Constants.CART_JSON_FIELD,JSON.toJSONString(cartVo));
            RedisUtil.hset(key,Constants.CART_COUNT_FIELD,cartVo.getTotalCount()+"");
        }else{
            //如果有购物车
            String cartJson = RedisUtil.hget(key, Constants.CART_JSON_FIELD);
            CartVo cartVo = JSON.parseObject(cartJson,CartVo.class);
            List<CartItemVo> cartItemVoList = cartVo.getCartItemVoList();
            Optional<CartItemVo> cartItem = cartItemVoList.stream().filter(x -> x.getSkuId().longValue() == skuId.longValue()).findFirst();
            if(cartItem.isPresent()){
                //购物车中如果有这款商品，找到这款商品，更新数量,小计
                CartItemVo cartItemVo = cartItem.get();
                long itemCount = cartItemVo.getCount() + count;
                //限购
                if(itemCount > skuLimit){
                    return ServerResponse.error(ResponseEnum.CART_SKU_IS_LIMIT);
                }

                //
                if(itemCount <= 0){
                    //从购物车中删除该商品
                    cartItemVoList.removeIf(x -> x.getSkuId().longValue() == cartItemVo.getSkuId().longValue());
                    if(cartItemVoList.size() == 0){
                        //删除购物车
                        RedisUtil.delete(key);
                        return ServerResponse.success();
                    }
                    //更新购物车redis
                    updateCart(key, cartVo);
                    return ServerResponse.success();
                }
                cartItemVo.setCount(itemCount);
                BigDecimal subPrice = new BigDecimal(cartItemVo.getSubPrice());
                String subPriceStr = subPrice.add(BigdecimalUtil.mul(cartItemVo.getPrice(), count + "")).toString();
                cartItemVo.setSubPrice(subPriceStr);

                //更新购物车redis
                updateCart(key, cartVo);

            }else {
                //购物车中没有这款商品，直接将商品加入购物车
                CartItemVo cartItemVo = new CartItemVo();
                cartItemVo.setSkuImage(sku.getImage());
                cartItemVo.setSkuName(sku.getSkuName());
                String price = sku.getPrice().toString();
                cartItemVo.setPrice(price);
                cartItemVo.setCount(count);
                cartItemVo.setSkuId(sku.getId());
                BigDecimal subPrice = BigdecimalUtil.mul(price, count+"");
                cartItemVo.setSubPrice(subPrice.toString());

                cartVo.getCartItemVoList().add(cartItemVo);
                updateCart(key, cartVo);

            }

        }

        return ServerResponse.success();
    }

    private void updateCart(String key, CartVo cartVo) {
        List<CartItemVo> cartItemVos = cartVo.getCartItemVoList();
        long totalCount = 0;
        BigDecimal totalPrice = new BigDecimal(0);
        for (CartItemVo itemVo : cartItemVos) {
            totalCount += itemVo.getCount();
            totalPrice = totalPrice.add(new BigDecimal(itemVo.getSubPrice()));
        }
        cartVo.setTotalCount(totalCount);
        cartVo.setTotalPrice(totalPrice.toString());
        //更新购物车 redis
        RedisUtil.hset(key,Constants.CART_JSON_FIELD,JSON.toJSONString(cartVo));
        RedisUtil.hset(key,Constants.CART_COUNT_FIELD,cartVo.getTotalCount()+"");
    }

    @Override
    public ServerResponse findCart(Long memberId) {
        String cartJson = RedisUtil.hget(KeyUtil.buildCartkey(memberId),Constants.CART_JSON_FIELD);
        CartVo cartVo = JSON.parseObject(cartJson, CartVo.class);
        return ServerResponse.success(cartVo);
    }

    @Override
    public ServerResponse findCartCount(Long memberId) {

        String count = RedisUtil.hget(KeyUtil.buildCartkey(memberId),Constants.CART_COUNT_FIELD);
        return ServerResponse.success(count);
    }

    @Override
    public ServerResponse deleteCartItem(Long memberId, Long skuId) {
        //获取会员对应的购物车

        String key = KeyUtil.buildCartkey(memberId);
        String cartJson = RedisUtil.hget(key, Constants.CART_JSON_FIELD);
        CartVo cartVo = JSON.parseObject(cartJson, CartVo.class);
        List<CartItemVo> cartItemVoList = cartVo.getCartItemVoList();
        Optional<CartItemVo> itemVo = cartItemVoList.stream().filter(x -> x.getSkuId().longValue() == skuId.longValue()).findFirst();
        if(!itemVo.isPresent()){//如果不存在
            return ServerResponse.error(ResponseEnum.CART_SKU_ERROR);
        }

        cartItemVoList.removeIf(x -> x.getSkuId().longValue() == skuId.longValue());
        if(cartItemVoList.size() == 0){
            RedisUtil.delete(key);
            return ServerResponse.success();
        }
        //更新购物车
        updateCart(key,cartVo);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBatchCartItem(Long memberId, String ids) {
        if(StringUtils.isEmpty(ids)){
            return ServerResponse.error(ResponseEnum.CART_BATCH_DELETE_NO_SELECT);
        }
        String key = KeyUtil.buildCartkey(memberId);
        String cartJson = RedisUtil.hget(key, Constants.CART_JSON_FIELD);
        CartVo cartVo = JSON.parseObject(cartJson, CartVo.class);
        List<CartItemVo> cartItemVoList = cartVo.getCartItemVoList();
        Arrays.stream(ids.split(",")).forEach(x -> cartItemVoList.removeIf(y -> y.getSkuId().longValue() == Long.parseLong(x)));
        if(cartItemVoList.size() == 0){
            RedisUtil.delete(key);
            return ServerResponse.success();
        }
        //更新购物车
        updateCart(key,cartVo);
        return ServerResponse.success();
    }
}
