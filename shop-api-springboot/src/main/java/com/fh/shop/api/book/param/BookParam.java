package com.fh.shop.api.book.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fh.shop.common.DataTableResult;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class BookParam extends DataTableResult implements Serializable {

    private String bookName;

    private String zuoZhe;


    private BigDecimal minPrice;


    private BigDecimal maxPrice;

    @DateTimeFormat(pattern = "yyyy-MM-ss HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date minDate;

    @DateTimeFormat(pattern = "yyyy-MM-ss HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date maxDate;
}
