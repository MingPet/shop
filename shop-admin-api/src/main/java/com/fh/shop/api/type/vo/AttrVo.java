package com.fh.shop.api.type.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttrVo implements Serializable {

    private String attrName;

    private String attrValues;

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValues() {
        return attrValues;
    }

    public void setAttrValues(String attrValues) {
        this.attrValues = attrValues;
    }
}
