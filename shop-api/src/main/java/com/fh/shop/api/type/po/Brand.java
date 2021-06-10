package com.fh.shop.api.type.po;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Brand {

    private Long id;

    private String brandName;

    private Integer createYear;

    private String logo;

    @TableField(exist = false)
    private String oldLogo;

    public Brand(String oldLogo) {
        this.oldLogo = oldLogo;
    }

    public String getOldLogo() {
        return oldLogo;
    }

    public void setOldLogo(String oldLogo) {
        this.oldLogo = oldLogo;
    }

    public Brand(){}
    public Brand(Long id, String brandName, Integer createYear, String logo) {
        this.id = id;
        this.brandName = brandName;
        this.createYear = createYear;
        this.logo = logo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getCreateYear() {
        return createYear;
    }

    public void setCreateYear(Integer createYear) {
        this.createYear = createYear;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
