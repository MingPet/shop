package com.fh.shop.admin.biz.type;

import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.mapper.brand.IBrandMapper;
import com.fh.shop.admin.mapper.spec.ISpecMapper;
import com.fh.shop.admin.mapper.type.*;
import com.fh.shop.admin.param.TypeAttrParam;
import com.fh.shop.admin.param.TypeInfoParam;
import com.fh.shop.admin.param.TypeParam;
import com.fh.shop.admin.param.TypeQueryParam;
import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.po.spec.Spec;
import com.fh.shop.admin.po.type.*;
import com.fh.shop.admin.vo.type.AttrVo;
import com.fh.shop.admin.vo.type.EditTypeVo;
import com.fh.shop.admin.vo.type.TypeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("typeService")
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
    public ServerResponse findInfo() {
        //获取所有的品牌
        List<Brand> brandList =  brandMapper.findAllBrand();

        //获取所有的规格
        List<Spec> specList = specMapper.findAllSpec();

        //组装
        TypeVo typeVo = new TypeVo();
        typeVo.setBrandList(brandList);

        typeVo.setSpecList(specList);
        return ServerResponse.success(typeVo);
    }

    @Override
    public ServerResponse addType(TypeParam typeParam) {

        String typeName = typeParam.getTypeName();

        String specIds = typeParam.getSpecIds();

        String brandIds = typeParam.getBrandIds();

        String attrNames = typeParam.getAttrNames();

        String attrValues = typeParam.getAttrValues();

        if(StringUtils.isEmpty(typeName) || StringUtils.isEmpty(specIds)
                || StringUtils.isEmpty(brandIds) || StringUtils.isEmpty(attrNames)
                || StringUtils.isEmpty(attrValues)
                ){
            return ServerResponse.error();
        }


            //插入类型表
            Type type = new Type();
            type.setTypeName(typeName);
            typeMapper.addType(type);
            Long id = type.getId();



            //插入类型品牌关联表
            List<TypeBrand> typeBrandList = new ArrayList<>();
            String[] brandIdsArr = brandIds.split(",");
            for (String  brandId : brandIdsArr) {
                TypeBrand typeBrand = new TypeBrand();

                typeBrand.setTypeId(id);
                typeBrand.setBrandId(Long.parseLong(brandId));
                typeBrandList.add(typeBrand);

            }
            typeBrandMapper.addTypeBrand(typeBrandList);

            //插入类型规格关联表
            List<TypeSpec> typeSpecList = new ArrayList<>();
            String[] specIdsArr = specIds.split(",");
            for (String specid : specIdsArr) {

                TypeSpec typeSpec = new TypeSpec();

                typeSpec.setTypeId(id);
                typeSpec.setSpecId(Long.parseLong(specid));
                typeSpecList.add(typeSpec);
            }
        typeSpecMapper.addTypeSpec(typeSpecList);

            //插入属性表
        String[] attrNameArr = attrNames.split(",");
        String[] attrValueArr = attrValues.split(";");
        int count = 0;
        for (String attrName : attrNameArr) {
            Attr attr = new Attr();
            attr.setAttrName(attrName);
            attr.setTypeId(id);

            attrMapper.addAttr(attr);
            Long attrId = attr.getId();
            List<AttrValue> attrValueList = new ArrayList<>();
            String[] attrValueStringArr = attrValueArr[count++].split(",");
            for (String attValueString : attrValueStringArr) {
                AttrValue attrValue = new AttrValue();
                attrValue.setAttrId(attrId);
                attrValue.setAttrValue(attValueString);
                attrValueList.add(attrValue);

            }
            //插入属性值表
            attrValueMapper.addBatch(attrValueList);
        }




        return ServerResponse.success();

    }

    @Override
    public DataTableResult findList(TypeQueryParam typeQueryParam) {

        Long typeCount = typeMapper.findCount(typeQueryParam);

        List<Type> findList = typeMapper.findList(typeQueryParam);

        return new DataTableResult(typeQueryParam.getDraw(),typeCount,typeCount,findList);
    }

    @Override
    public ServerResponse findEtidType(Long id) {
        //根据类型id获取对应的类型信息
        Type findType = typeMapper.findType(id);
        //根据类型id获取对应的类型品牌信息
        List<Long> brandIds = typeBrandMapper.findIds(id);
        //根据类型id获取对应的类型规格信息
        List<Long> specIds = typeSpecMapper.findIds(id);
        //获取所有的规格信息
        List<Spec> specList = specMapper.findSpecAllList();
        //获取所有的品牌信息
        List<Brand> brandList = brandMapper.findBrandAllList();
        //获取类型对应的属性名信息
        List<Attr> attrList =  attrMapper.findAattrListByTypeId(id);

        List<Long> attrIdList = new ArrayList<>();
        List<AttrVo> attrVoList = new ArrayList<>();
        for (Attr attr : attrList) {
            attrIdList.add(attr.getId());
        }
        //获取属性对应的属性值信息
        List<AttrValue> attrValueList =  attrValueMapper.findAttrValueListByAttrIdList(attrIdList);
        //属性和属性值对应

        for (Attr attr : attrList) {
            AttrVo attrVo = new AttrVo();
            attrVo.setAttrName(attr.getAttrName());
            attrVo.setAttrValues(buildAttrValue(attr.getId(),attrValueList));
            attrVoList.add(attrVo);
        }
        EditTypeVo editTypeVo = new EditTypeVo();

        editTypeVo.setTypeName(findType.getTypeName());

        editTypeVo.setBrandIdList(brandIds);

        editTypeVo.setSpecIdList(specIds);

        editTypeVo.setBrandList(brandList);

        editTypeVo.setSpecList(specList);

        editTypeVo.setAttrVoList(attrVoList);




        return ServerResponse.success(editTypeVo);
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
    public ServerResponse updateType(TypeParam typeParam) {
        Long id = typeParam.getId();

        String typeName = typeParam.getTypeName();

        String specIds = typeParam.getSpecIds();

        String brandIds = typeParam.getBrandIds();

        String attrNames = typeParam.getAttrNames();

        String attrValues = typeParam.getAttrValues();


        Type type = new Type();
        type.setId(id);
        type.setTypeName(typeName);
        typeMapper.updateType(type);

        typeBrandMapper.deleteBrandByTypeId(id);



        String[] brandIdsArr = brandIds.split(",");

        List<TypeBrand> typeBrandList = new ArrayList<>();
        for (String  brandArr : brandIdsArr) {
            TypeBrand typeBrand = new TypeBrand();
            typeBrand.setTypeId(type.getId());
            typeBrand.setBrandId(Long.parseLong(brandArr));
            typeBrandList.add(typeBrand);

        }
        typeBrandMapper.addTypeBrand(typeBrandList);



        typeSpecMapper.deleteSpecByTypeId(id);
        String[] specIdsArr = specIds.split(",");

        List<TypeSpec> specIdsList = new ArrayList<>();
        for (String specArr : specIdsArr) {
            TypeSpec typeSpec = new TypeSpec();
            typeSpec.setTypeId(type.getId());
            typeSpec.setSpecId(Long.parseLong(specArr));
            specIdsList.add(typeSpec);
        }

        typeSpecMapper.addTypeSpec(specIdsList);

        //查询类型对应的属性id集合
        List<Attr> attrList = attrMapper.findAattrListByTypeId(id);

        ArrayList<Long> attrIdList = new ArrayList<>();
        for (Attr attr : attrList) {
            attrIdList.add(attr.getId());
        }
        //删除属性
        attrMapper.deleteByTypeId(id);
        //删除属性对应的属性值
        attrValueMapper.deleteByAttrIdList(attrIdList);
        //批量插入属性
        String[] attrNameArr = attrNames.split(",");
        String[] attrValueArr = attrValues.split(";");
        int count = 0;
        for (String attrName : attrNameArr) {
            Attr attr = new Attr();
            attr.setTypeId(id);
            attr.setAttrName(attrName);
           attrMapper.addAttr(attr);
            Long attrId = attr.getId();
            //批量插入属性值
            ArrayList attrValueList = new ArrayList<>();

            String[] attrValArr = attrValueArr[count++].split(",");
            for (String attrVal : attrValArr) {
                AttrValue attrValue = new AttrValue();
                attrValue.setAttrValue(attrVal);
                attrValue.setAttrId(attrId);
                attrValueList.add(attrValue);
            }

            attrValueMapper.addBatch(attrValueList);
        }



        return ServerResponse.success();
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



}
