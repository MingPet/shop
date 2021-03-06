package com.fh.shop.api.spec.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class Spec implements Serializable {

    private Long id;

    private String specName;

    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
