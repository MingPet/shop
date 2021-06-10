package com.fh.shop.api.mq;

import lombok.Data;

@Data
public class MailMessage {

    public String to;
    private String title;
    private String conent;
}
