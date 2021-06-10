package com.fh.shop.admin.param;

import java.io.Serializable;

public class SpecParam implements Serializable {

    private Long id;
    private String specNames;
    private String specNameSorts;
    private String specValueInfos;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecNames() {
        return specNames;
    }

    public void setSpecNames(String specNames) {
        this.specNames = specNames;
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
