package com.fh.shop.api.order.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class OrderInfoParam implements Serializable {

    private Integer payType;

    private Long orderId;//收件人表Id
    @ApiModelProperty(hidden = true)
    private Long memberId;
}
