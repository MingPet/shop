package com.fh.shop.admin.vo.cate;

import com.fh.shop.admin.po.cate.Cate;
import com.fh.shop.admin.po.type.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CateVo implements Serializable {

    private Cate cate = new Cate();

    private List<Type> typeList = new ArrayList<>();

    public Cate getCate() {
        return cate;
    }

    public void setCate(Cate cate) {
        this.cate = cate;
    }

    public List<Type> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Type> typeList) {
        this.typeList = typeList;
    }
}

