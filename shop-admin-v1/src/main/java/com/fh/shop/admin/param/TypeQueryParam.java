package com.fh.shop.admin.param;

import java.io.Serializable;

public class TypeQueryParam extends  Page implements Serializable {
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
