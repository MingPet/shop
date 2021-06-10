package com.fh.shop.common;

public enum SystemConfigEnum {
    CURR_USER("user"),
    UPLOAD_PATH("/upload/");

    private String info;

    private SystemConfigEnum(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return info;
    }
}
