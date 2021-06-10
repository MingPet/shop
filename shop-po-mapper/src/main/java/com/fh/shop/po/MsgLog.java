package com.fh.shop.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class MsgLog implements Serializable {

    @TableId(type = IdType.INPUT,value = "msgId")
    private String msgId;

    private String message;

    private Integer retryCount;

    private Date insertTime;

    private Date updateTime;

    private Date retryTime;

    private String exchangeName;

    private String rountKey;

    private Integer status;
}
