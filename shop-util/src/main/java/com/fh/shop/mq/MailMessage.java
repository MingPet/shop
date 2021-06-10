package com.fh.shop.mq;

import lombok.Data;

@Data
public class MailMessage {

    public String to;
    private String title;
    private String conent;
    private String msgId;
}
