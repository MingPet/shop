package com.fh.shop.api.spec.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_specValue")
public class SpecValue implements Serializable {

    private Long id;

    private String specValue;

    private Long specId;

    private Integer sort;


}
