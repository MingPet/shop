package com.fh.shop.api.brand.po;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Brand {

    private Long id;

    private String brandName;

    private Integer createYear;

    private String logo;

    @TableField(exist = false)
    private String oldLogo;

}
