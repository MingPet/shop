package com.fh.shop.admin.biz.goods;

import com.fh.shop.admin.param.SpuParam;
import com.fh.shop.admin.param.SpuQueryParam;
import com.fh.shop.admin.param.SpuStatusParam;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;

public interface ISpuService  {
    ServerResponse findSpuInfo(Long typeId);

    ServerResponse addSpu(SpuParam spuParam);

    DataTableResult findList(SpuQueryParam spuQueryParam);

    ServerResponse findSpu(Long id);

    ServerResponse deleteImage(Long key, String  rootPath);

    ServerResponse updateSpu(SpuParam spuParam);

    ServerResponse deleteSpu(Long id, String rootPatch);

    ServerResponse deleteBatch(String ids, String rootPatch);

    ServerResponse updateStatus(SpuStatusParam spuStatusParam);
}
