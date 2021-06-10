package com.fh.shop.api.type.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class TypeUpdateParam extends TypeParam implements Serializable {

    private  Long id;
}
