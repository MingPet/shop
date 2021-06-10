package com.fh.shop.api.spec.param;

import com.fh.shop.common.DataTableResult;
import lombok.Data;

import java.io.Serializable;

@Data
public class SpecQueryParam  extends DataTableResult implements Serializable {

    private String specName;

     }
