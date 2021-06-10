package com.fh.shop.api.type.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttrParam  implements Serializable {

    private String attrName;

    private String attrValues;
}
