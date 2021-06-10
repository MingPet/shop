package com.fh.shop.api.type.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.spec.mapper.ISpecMapper;
import com.fh.shop.api.spec.po.Spec;
import com.fh.shop.api.type.mapper.*;
import com.fh.shop.api.type.param.TypeAttrParam;
import com.fh.shop.api.type.param.TypeParam;
import com.fh.shop.api.type.param.TypeQueryParam;
import com.fh.shop.api.type.po.*;
import com.fh.shop.api.type.vo.TypeVo;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("typeService")
public class ITypeServiceImpl implements ITypeService {

    @Autowired
    private IBrandMapper brandMapper;
    @Autowired
    private ISpecMapper specMapper;

    @Autowired
    private IAttrMapper attrMapper;
    @Autowired
    private IAttrValueMapper attrValueMapper;
    @Autowired
    private ITypeBrandMapper typeBrandMapper;
    @Autowired
    private ITypeMapper typeMapper;
    @Autowired
    private ITypeSpecMapper typeSpecMapper;

    @Override
    public ServerResponse findInfo() {
        List<Brand> brandList = brandMapper.selectList(new QueryWrapper<>());
        List<Spec> specList = specMapper.findAllSpecList();
        TypeVo typeVo = new TypeVo();
        typeVo.setBrandList(brandList);
        typeVo.setSpecList(specList);
        return ServerResponse.success(typeVo);
    }

    @Override
    public ServerResponse addType(TypeParam typeParam) {
        //插入类型表
        Type type = typeParam.getType();
        typeMapper.insert(type);
        //获取type的Id
        Long id = type.getId();
        //插入类型品牌关联表
        List<TypeBrand> typeBrandList = typeParam.getTypeBrandList();
        typeBrandList.stream().forEach(x ->x.setTypeId(id));
        typeBrandMapper.addTypeBrand(typeBrandList);
        //插入类型规格关联表
        List<TypeSpec> typeSpecList = typeParam.getTypeSpecList();
        typeSpecList.stream().forEach(x ->x.setTypeId(id));
        typeSpecMapper.addTypeSpec(typeSpecList);
        //插入属性表
        List<TypeAttrParam> typeAttrParamList = typeParam.getTypeAttrParamList();
        typeAttrParamList.stream().forEach(x ->{
            Attr attr = x.getAttr();
            attr.setTypeId(id);
            List<AttrValue> attrValueList = x.getAttrValueList();
            attrMapper.insert(attr);
            //插入之后获取属性Id
            Long attrId = attr.getId();
            attrValueList.stream().forEach(y ->y.setAttrId(attrId));
            attrValueMapper.addBatch(attrValueList);

        });
        /*{
        * type：{"typeName":"***"},
        * typeBrandList:[{"brandId":1},{"brandId":2}],
        * typeSpecList:[{"specId":2},{"specId":3}],
        * typeAttrParamList:[
                             {attr:{"attrName":"aaa"},attrValueList:[{"attrValue":"cdcd"},{"attrValue":"dddd"}]},
                             {attr:{"attrName":"bbb"},attrValueList:[{"attrValue":"wwww"},{"attrValue":"rrr"}]},
                             {attr:{"attrName":"ccc"},attrValueList:[{"attrValue":"eee"},{"attrValue":"ttt"}]},
                             ],
        * }
        * */

        return ServerResponse.success();
    }

    @Override
    public DataTableResult findList(TypeQueryParam typeQueryParam) {
        Long typeCount = typeMapper.findCount(typeQueryParam);

        List<Type> findList = typeMapper.findList(typeQueryParam);

        return new DataTableResult(typeQueryParam.getDraw(),typeCount,typeCount,findList);
    }

}


