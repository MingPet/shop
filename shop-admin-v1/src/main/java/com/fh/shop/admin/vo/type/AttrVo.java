package com.fh.shop.admin.vo.type;

import java.io.Serializable;

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
