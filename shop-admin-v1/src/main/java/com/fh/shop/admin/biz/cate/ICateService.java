package com.fh.shop.admin.biz.cate;

import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.param.CateParam;
import com.fh.shop.admin.po.cate.Cate;

public interface ICateService {

    ServerResponse findAllList();


    ServerResponse addCate(Cate cate);

    ServerResponse deleteCate(String ids);


    ServerResponse findCate(Long id);

    ServerResponse updateCate(CateParam cateParam);

    ServerResponse findCateChilds(Long id);

}
