package com.fh.shop.api.type.param;

import com.fh.shop.common.Page;

public class TypeQueryParam extends Page {
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
