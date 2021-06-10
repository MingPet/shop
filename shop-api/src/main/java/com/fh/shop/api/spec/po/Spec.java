package com.fh.shop.api.spec.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class Spec implements Serializable {

    private Long id;

    private String specName;

    private Integer sort;


}
