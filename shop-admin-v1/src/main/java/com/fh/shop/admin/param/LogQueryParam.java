package com.fh.shop.admin.param;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class LogQueryParam extends  Page implements Serializable {

    private String userName;

    private String realName;

    private String info;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date minDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date maxDate;



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }
}
