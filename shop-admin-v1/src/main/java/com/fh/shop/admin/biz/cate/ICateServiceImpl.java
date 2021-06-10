package com.fh.shop.admin.biz.cate;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.admin.mapper.cate.ICateMapper;
import com.fh.shop.admin.mapper.type.ITypeMapper;
import com.fh.shop.admin.param.CateParam;
import com.fh.shop.admin.po.cate.Cate;
import com.fh.shop.admin.po.type.Type;
import com.fh.shop.admin.vo.cate.CateVo;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("cateService")
public class ICateServiceImpl implements ICateService {

    @Autowired
    private ICateMapper cateMapper;

    @Autowired
    private ITypeMapper typeMapper;
    @Override
    public ServerResponse findAllList() {
        List<Cate> cateList =  cateMapper.findAllList();
        return ServerResponse.success(cateList);
    }

    @Override
    public ServerResponse addCate(Cate cate) {
        cateMapper.addCate(cate);
        //删除缓存
        RedisUtil.delete("cateList");

        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteCate(String ids) {
        if(StringUtils.isEmpty(ids)){
            return ServerResponse.error(ResponseEnum.DELETE_IS_ERROR);
        }
        String[] idArr = ids.split(",");
        List<Long> idList = new ArrayList<>();
        for (String s : idArr) {
            idList.add(Long.parseLong(s));
        }
        cateMapper.deleteCate(idList);
        //删除缓存
        RedisUtil.delete("cateList");
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findCate(Long id) {
        Cate cate = cateMapper.findCate(id);
        //获取所有的类型信息
        List<Type> allTypeList = typeMapper.findAllTypeList();
        CateVo cateVo = new CateVo();
        cateVo.setCate(cate);
        cateVo.setTypeList(allTypeList);
        return ServerResponse.success(cateVo);
    }

    @Override
    public ServerResponse updateCate(CateParam cateParam) {
        Cate cate = cateParam.getCate();
        String ids = cateParam.getIds();
        if (StringUtils.isNoneEmpty(ids)){

            String[] idArr = ids.split(",");
            List<Long> idList = new ArrayList<>();
            for (String id : idArr) {
                idList.add(Long.parseLong(id));

            }
            cateParam.setIdList(idList);
            //将多个java对象组装到一个对象中
            cateMapper.updateTypeInfo(cateParam);
        }
        //更新分类信息
        cateMapper.updateCate(cate);

        //把当前分类下的所有子节点的typeId以及typeName进行更新


        //删除缓存
        RedisUtil.delete("cateList");
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findCateChilds(Long id) {
        //将当前id作为父id寻找孩子
        QueryWrapper<Cate> cateQueryWrapper = new QueryWrapper<>();
        cateQueryWrapper.eq("pid",id);
        List<Cate> cateList = cateMapper.selectList(cateQueryWrapper);
        return ServerResponse.success(cateList);
    }

}
