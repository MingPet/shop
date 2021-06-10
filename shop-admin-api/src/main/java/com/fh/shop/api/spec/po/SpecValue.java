package com.fh.shop.api.spec.po;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("t_specValue")
public class SpecValue implements Serializable {

    private Long id;

    private String specValue;

    private int sort;

    private Long specId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecValue() {
        return specValue;
    }

    public void setSpecValue(String specValue) {
        this.specValue = specValue;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Long getSpecId() {
        return specId;
    }

    public void setSpecId(Long specId) {
        this.specId = specId;
    }
}
