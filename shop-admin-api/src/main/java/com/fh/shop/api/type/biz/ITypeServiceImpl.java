package com.fh.shop.api.type.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.brand.mapper.IBrandMapper;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.spec.mapper.ISpecMapper;
import com.fh.shop.api.spec.po.Spec;
import com.fh.shop.api.type.mapper.*;
import com.fh.shop.api.type.param.*;
import com.fh.shop.api.type.po.*;
import com.fh.shop.api.type.vo.AttrVo;
import com.fh.shop.api.type.vo.EditTypeVo;
import com.fh.shop.api.type.vo.TypeInfoVo;
import com.fh.shop.api.type.vo.TypeVo;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.common.TableResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("typeService")
@Transactional(rollbackFor = Exception.class)
public class ITypeServiceImpl implements ITypeService {

    @Autowired
    private IBrandMapper brandMapper;

    @Autowired
    private ISpecMapper specMapper;

    @Autowired
    private ITypeMapper typeMapper;

    @Autowired
    private ITypeBrandMapper typeBrandMapper;

    @Autowired
    private ITypeSpecMapper typeSpecMapper ;

    @Autowired
    private IAttrMapper attrMapper;

    @Autowired
    private IAttrValueMapper attrValueMapper;

    @Override
    @Transactional(readOnly = true)
    public ServerResponse findInfo() {
        //获取所有的品牌
        List<Brand> brandList =  brandMapper.selectList(null);

        //获取所有的规格
        List<Spec> specList = specMapper.selectList(null);

        //组装
        TypeVo typeVo = new TypeVo();
        typeVo.setBrandList(brandList);

        typeVo.setSpecList(specList);
        return ServerResponse.success(typeVo);
    }



    @Override
    public ServerResponse findList(TypeQueryParam typeQueryParam) {

        Long totalCount = typeMapper.findCount(typeQueryParam);

        List<Type> findList = typeMapper.findList(typeQueryParam);

        TableResult tableResult = new TableResult(totalCount,findList);

        return  ServerResponse.success(tableResult);

    }

    @Override
    public ServerResponse findEtidType(Long id) {


        //获取品牌列表
        List<Brand> brands = brandMapper.selectList(null);
        //获取规格列表
        List<Spec> specs = specMapper.selectList(null);

        //获取类型信息
        Type type = typeMapper.selectById(id);

        //获取选中的品牌列表
        QueryWrapper<TypeBrand> typeBrandQueryWrapper = new QueryWrapper<>();
        typeBrandQueryWrapper.eq("typeId",id);
        List<TypeBrand> typeBrands = typeBrandMapper.selectList(typeBrandQueryWrapper);

        //获取选中的规格列表
        QueryWrapper<TypeSpec> typeSpecQueryWrapper = new QueryWrapper<>();
        typeSpecQueryWrapper.eq("typeId",id);
        List<TypeSpec> typeSpecs = typeSpecMapper.selectList(typeSpecQueryWrapper);


        //获取对应的属性
        //批量插入
        QueryWrapper<Attr> attrQueryWrapper = new QueryWrapper<>();
        attrQueryWrapper.eq("typeId",id);

        List<Attr> attrs = attrMapper.selectList(attrQueryWrapper);
        List<Long> attrIdList = attrs.stream().map(x -> x.getId()).collect(Collectors.toList());

        //获取对应的属性值
        QueryWrapper<AttrValue> attrValueQueryWrapper = new QueryWrapper<>();

        attrValueQueryWrapper.in("attrId",attrIdList);
        List<AttrValue> attrValues = attrValueMapper.selectList(attrValueQueryWrapper);
        //关联
        List<AttrParam> attrParams = new ArrayList<>();
        attrs.forEach(x ->{
            Long attrId = x.getId();
            List<AttrValue> attrValueList = attrValues.stream().filter(y -> y.getAttrId().longValue() == attrId.longValue()).collect(Collectors.toList());
            AttrParam attrParam = new AttrParam();
            attrParam.setAttrName(x.getAttrName());
            //获取到值  不然前端接收的是对象
            attrParam.setAttrValues(StringUtils.join(attrValueList.stream().map(xx -> xx.getAttrValue()).collect(Collectors.toList()), ","));

            attrParams.add(attrParam);

        });
        TypeInfoVo typeInfoVo = new TypeInfoVo();
        typeInfoVo.setId(id);
        typeInfoVo.setTypeName(type.getTypeName());
        typeInfoVo.setBrandList(brands);
        typeInfoVo.setSpecList(specs);
        typeInfoVo.setCheckedBrandList(typeBrands.stream().map(x -> x.getBrandId()).collect(Collectors.toList()));
        typeInfoVo.setCheckedSpecList(typeSpecs.stream().map(x -> x.getSpecId()).collect(Collectors.toList()));
        //获取对应的属性值
        typeInfoVo.setAttrParamList(attrParams);



        return ServerResponse.success(typeInfoVo);
    }

    //属性和属性值对应的方法
    private String buildAttrValue(Long attrId,List<AttrValue> attrValueList){
        String result = "";
        List<String> attrValues = new ArrayList<>();
        for (AttrValue attrValue : attrValueList) {
            if(attrValue.getAttrId().longValue() == attrId.longValue()){
                attrValues.add(attrValue.getAttrValue());
            }
        }

         result = StringUtils.join(attrValues, ",");
        //如果属性的id等于属性值的attrid 那么属性值对应 返回字符串类型的数据
        return result;
    }
    



    @Override
    public ServerResponse deleteType(Long id) {
        //删除类型表
        typeMapper.deleteTypeById(id);
        //删除类型品牌表
        typeBrandMapper.deleteBrandByTypeId(id);
        //删除类型规格表
        typeSpecMapper.deleteSpecByTypeId(id);

        //删除属性值表
        // attrValueMapper.deleteByAttrId(id);
        List<Attr> attrList = attrMapper.findAattrListByTypeId(id);
        ArrayList<Long> attrIdList = new ArrayList<>();
        for (Attr attr : attrList) {
            attrIdList.add(attr.getId());
        }
        //删除属性表
        attrMapper.deleteByTypeId(id);

        attrValueMapper.deleteByAttrIdList(attrIdList);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBatch(String ids) {
        //非空验证
        if(StringUtils.isEmpty(ids)){
            return ServerResponse.error();
        }

        String[] idArr = ids.split(",");
        List<Long> idList = new ArrayList<>();
        for (String id : idArr) {
            idList.add(Long.parseLong(id));
        }
        //批量删除类型
        typeMapper.deleteBatch(idList);

        //批量删除类型品牌关联表
        typeBrandMapper.deleteBatch(idList);

        //批量删除类型规格关联表
        typeSpecMapper.deleteBatch(idList);


        //批量删除属性值表

        List<Attr> attrList = attrMapper.findAttrBatchId(idList);
        List<Long>  attrIdLIst=new ArrayList<>();
        for (Attr attr : attrList) {
            attrIdLIst.add(attr.getId());
        }


        // List<Long> attrIds = attrMapper.findAttrIds(idList);
      //  attrValueMapper.deleteByAttrIdList((ArrayList<Long>) attrIds);
        attrMapper.deleteBatch(idList);

        attrValueMapper.deleteBatch(attrIdLIst);






        return ServerResponse.success();






    }

    @Override
    public ServerResponse findAllTypeList() {
        List<Type> typeList =  typeMapper.findAllTypeList();
        return ServerResponse.success(typeList);
    }

    @Override
    public ServerResponse addTypeInfo(TypeInfoParam typeInfoParam) {


        //插入类型表
        Type type = typeInfoParam.getType();
        typeMapper.insert(type);
        //获取type的Id
        Long id = type.getId();
        //插入类型品牌关联表
        List<TypeBrand> typeBrandList = typeInfoParam.getTypeBrandList();
        typeBrandList.stream().forEach(x ->x.setTypeId(id));
        typeBrandMapper.addTypeBrand(typeBrandList);
        //插入类型规格关联表
        List<TypeSpec> typeSpecList = typeInfoParam.getTypeSpecList();
        typeSpecList.stream().forEach(x ->x.setTypeId(id));
        typeSpecMapper.addTypeSpec(typeSpecList);
        //插入属性表
        List<TypeAttrParam> typeAttrParamList = typeInfoParam.getTypeAttrParamList();
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
    public ServerResponse addType(TypeParam typeParam) {
        Type type = new Type();

        Long id1 = typeParam.getId();
        Long id;

        if(id1 == null){
            //类型表
            String typeName = typeParam.getTypeName();

            type.setTypeName(typeName);
            typeMapper.insert(type);
            id = type.getId();
        }else{
            id = id1;
        }



        //类型品牌关联表

        List<Long> checkedBrandList = typeParam.getCheckedBrandList();

        if(checkedBrandList.size()>0){
            List<TypeBrand> typeBrandList = checkedBrandList.stream().map(x -> {
                TypeBrand typeBrand = new TypeBrand();
                typeBrand.setBrandId(x);
                typeBrand.setTypeId(id);
                return typeBrand;
            }).collect(Collectors.toList());
            typeBrandMapper.addTypeBrand(typeBrandList);
        }

        //类型规格关联表
        List<Long> checkedSpecList = typeParam.getCheckedSpecList();
        if(checkedSpecList.size()>0){
            List<TypeSpec> typeSpecList = checkedSpecList.stream().map(x -> {
                TypeSpec typeSpec = new TypeSpec();
                typeSpec.setSpecId(x);
                typeSpec.setTypeId(id);
                return typeSpec;
            }).collect(Collectors.toList());
            typeSpecMapper.addTypeSpec(typeSpecList);
        }

        //属性表
        List<AttrParam> attrParamList = typeParam.getAttrParamList();
        if(attrParamList.size()>0){
            attrParamList.forEach(z ->{
                Attr attr = new Attr();
                attr.setAttrName(z.getAttrName());
                attr.setTypeId(id);
                attrMapper.insert(attr);

                //属性值表
                Long attrId = attr.getId();
                String attrValues = z.getAttrValues();
                if(StringUtils.isNotEmpty(attrValues)){
                    List<AttrValue> attrValueList = Arrays.stream(attrValues.split(",")).map(y -> {
                        AttrValue attrValue = new AttrValue();
                        attrValue.setAttrId(attrId);
                        attrValue.setAttrValue(y);
                        return attrValue;
                    }).collect(Collectors.toList());
                    attrValueMapper.addBatch(attrValueList);
                }

            });
        }

        return ServerResponse.success();
    }

    @Override
    public ServerResponse updateType(TypeParam typeParam) {
        //更新类型信息
        Type type = new Type();
        Long id = typeParam.getId();

        type.setId(typeParam.getId());
        type.setTypeName(typeParam.getTypeName());
        typeMapper.updateById(type);
        //删除关联信息
        QueryWrapper<TypeBrand> typeBrandQueryWrapper = new QueryWrapper<>();
        typeBrandQueryWrapper.eq("typeId",id);
        typeBrandMapper.delete(typeBrandQueryWrapper);

        QueryWrapper<TypeSpec> typeSpecQueryWrapper = new QueryWrapper<>();
        typeSpecQueryWrapper.eq("typeId",id);
        typeSpecMapper.delete(typeSpecQueryWrapper);

        //先删除属性值  在删除属性
        QueryWrapper<Attr> attrQueryWrapper = new QueryWrapper<>();
        attrQueryWrapper.eq("typeId",id);

        List<Attr> attrs = attrMapper.selectList(attrQueryWrapper);
        List<Long> attrIdList = attrs.stream().map(x -> x.getId()).collect(Collectors.toList());

        //删除对应的属性值
        QueryWrapper<AttrValue> attrValueQueryWrapper = new QueryWrapper<>();

        attrValueQueryWrapper.in("attrId",attrIdList);
        attrValueMapper.delete(attrValueQueryWrapper);

        //再删除属性
        QueryWrapper<Attr> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("typeId",id);
        attrMapper.delete(queryWrapper);


        //重新插入
        return addType(typeParam);//调本类的插入方法
    }


}
