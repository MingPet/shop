package com.fh.shop.api.book.param;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel
public class BookQueryParam extends Page {

    @ApiModelProperty(value = "图书名")
    private String bookName;

    @ApiModelProperty(value = "图书最低价格" ,example = "0")
    private BigDecimal minPrice;

    @ApiModelProperty(value = "图书最高价格" ,example = "0")
    private BigDecimal maxPrice;

    @ApiModelProperty(value = "图书作者")
    private String autho;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private Date minDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private Date maxDate;

}
