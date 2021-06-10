package com.fh.shop.api.type.param;

import com.fh.shop.api.type.po.Attr;
import com.fh.shop.api.type.po.AttrValue;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TypeAttrParam implements Serializable {

    private Attr attr = new Attr();

    private List<AttrValue> attrValueList = new ArrayList<>();
}
