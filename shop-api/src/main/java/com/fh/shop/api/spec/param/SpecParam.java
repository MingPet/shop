package com.fh.shop.api.spec.param;

import lombok.Data;

import java.io.Serializable;


@Data
public class SpecParam implements Serializable {

    private Long id;

    private String specName;

    private String specNameSorts;

    private String specValueInfos;

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

    public String getSpecNameSorts() {
        return specNameSorts;
    }

    public void setSpecNameSorts(String specNameSorts) {
        this.specNameSorts = specNameSorts;
    }

    public String getSpecValueInfos() {
        return specValueInfos;
    }

    public void setSpecValueInfos(String specValueInfos) {
        this.specValueInfos = specValueInfos;
    }
}
