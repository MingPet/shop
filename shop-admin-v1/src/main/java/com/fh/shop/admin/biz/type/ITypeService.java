package com.fh.shop.admin.biz.type;

import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.param.TypeInfoParam;
import com.fh.shop.admin.param.TypeParam;
import com.fh.shop.admin.param.TypeQueryParam;

public interface ITypeService  {

    public ServerResponse findInfo();

    ServerResponse addType(TypeParam typeParam);

    DataTableResult findList(TypeQueryParam typeQueryParam);

    ServerResponse findEtidType(Long id);

    ServerResponse updateType(TypeParam typeParam);

    ServerResponse deleteType(Long id);

    ServerResponse deleteBatch(String ids);

    ServerResponse findAllTypeList();


    ServerResponse addTypeInfo(TypeInfoParam typeInfoParam);


}
