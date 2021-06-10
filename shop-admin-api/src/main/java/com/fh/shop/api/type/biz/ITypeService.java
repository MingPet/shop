package com.fh.shop.api.type.biz;

import com.fh.shop.api.type.param.TypeInfoParam;
import com.fh.shop.api.type.param.TypeParam;
import com.fh.shop.api.type.param.TypeQueryParam;
import com.fh.shop.api.type.param.TypeUpdateParam;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;

public interface ITypeService  {

    ServerResponse findInfo();



    ServerResponse findList(TypeQueryParam typeQueryParam);

    ServerResponse findEtidType(Long id);



    ServerResponse deleteType(Long id);

    ServerResponse deleteBatch(String ids);

    ServerResponse findAllTypeList();


    ServerResponse addTypeInfo(TypeInfoParam typeInfoParam);


    ServerResponse addType(TypeParam typeParam);

    ServerResponse updateType(TypeParam typeParam);
}
