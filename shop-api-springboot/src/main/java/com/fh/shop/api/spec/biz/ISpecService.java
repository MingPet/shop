package com.fh.shop.api.spec.biz;


import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;

import java.util.List;

public interface ISpecService {

    public ServerResponse addSpec(SpecParam specParam);

    DataTableResult queryList(SpecQueryParam specQueryParam);

    ServerResponse findSpec(Long id);


    ServerResponse updateSpec(SpecParam specParam);

    ServerResponse deleteSpec(Long id);

    ServerResponse deleteBatch(String ids);

    ServerResponse addSpecList(List<SpecInfoParam> specInfoParamList);
}
