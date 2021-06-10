package com.fh.shop.api.spec.vo;



import com.fh.shop.api.spec.po.Spec;
import com.fh.shop.api.spec.po.SpecValue;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SpecVo implements Serializable {

    private Spec spec=new Spec();

    private List<SpecValue>specValueList=new ArrayList<>();

    }
