package com.fh.shop.api.type.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class TypeSpec implements Serializable {

    private Long id;
    private Long SpecId;
    private Long typeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpecId() {
        return SpecId;
    }

    public void setSpecId(Long specId) {
        SpecId = specId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}
