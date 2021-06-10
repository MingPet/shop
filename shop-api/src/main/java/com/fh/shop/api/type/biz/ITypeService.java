package com.fh.shop.api.type.biz;

import com.fh.shop.api.type.param.TypeParam;
import com.fh.shop.api.type.param.TypeQueryParam;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;

public interface ITypeService {

    ServerResponse findInfo();

    ServerResponse addType(TypeParam typeParam);

    DataTableResult findList(TypeQueryParam typeQueryParam);
}
