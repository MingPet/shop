package com.fh.shop.admin.biz.goods;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.admin.param.SpuStatusParam;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.mapper.brand.IBrandMapper;
import com.fh.shop.admin.mapper.cate.ICateMapper;
import com.fh.shop.admin.mapper.goods.ISkuImageMapper;
import com.fh.shop.admin.mapper.goods.ISkuMapper;
import com.fh.shop.admin.mapper.goods.ISpuMapper;
import com.fh.shop.admin.mapper.spec.ISpecMapper;
import com.fh.shop.admin.mapper.spec.ISpecValueMapper;
import com.fh.shop.admin.mapper.type.IAttrMapper;
import com.fh.shop.admin.mapper.type.IAttrValueMapper;
import com.fh.shop.admin.param.SpuParam;
import com.fh.shop.admin.param.SpuQueryParam;
import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.po.cate.Cate;
import com.fh.shop.admin.po.goods.Sku;
import com.fh.shop.admin.po.goods.SkuImage;
import com.fh.shop.admin.po.spec.Spec;
import com.fh.shop.admin.po.spec.SpecValue;
import com.fh.shop.admin.po.goods.Spu;
import com.fh.shop.admin.po.type.Attr;
import com.fh.shop.admin.po.type.AttrValue;
import com.fh.shop.util.OSSUtil;
import com.fh.shop.admin.vo.goods.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("spuService")
public class ISpuServiceImpl implements ISpuService {
    @Autowired
    private ISpuMapper spuMapper;

    @Autowired
    private IBrandMapper brandMapper;

    @Autowired
    private IAttrMapper attrMapper;

    @Autowired
    private IAttrValueMapper attrValueMapper;

    @Autowired
    private ISpecMapper specMapper;

    @Autowired
    private ISpecValueMapper specValueMapper;

    @Autowired
    private ISkuMapper skuMapper;

    @Autowired
    private ICateMapper cateMapper;

    @Autowired
    private ISkuImageMapper skuImageMapper;


    @Override
    public ServerResponse findSpuInfo(Long typeId) {

        //????????????id???????????????????????????
        List<Brand> brandList =  brandMapper.findBrandListByTypeId(typeId);

        //????????????????????????id???????????????spuAttrVoList
        //????????????id???????????????????????????
        QueryWrapper<Attr> attrQueryWrapper = new QueryWrapper<>();
        attrQueryWrapper.eq("typeId", typeId);
        List<Attr> attrList = attrMapper.selectList(attrQueryWrapper);
        List<Long> idList = attrList.stream().map(x -> x.getId()).collect(Collectors.toList());
        //?????????????????????????????????????????????
        QueryWrapper<AttrValue> attrValueQueryWrapper = new QueryWrapper<>();
        attrValueQueryWrapper.in("attrId",idList);

        List<AttrValue> attrValueList = attrValueMapper.selectList(attrValueQueryWrapper);
        //?????????????????????????????????
        List<SpuAttrVo> spuAttrVoList = attrList.stream().map(x -> {
            SpuAttrVo spuAttrVo = new SpuAttrVo();
            String attrName = x.getAttrName();
            Long id = x.getId();
            List<AttrValue> attrValueList1 = attrValueList.stream().filter(y -> y.getAttrId().longValue() == id.longValue()).collect(Collectors.toList());
            spuAttrVo.setAttrName(attrName);
            spuAttrVo.setAttrValueList(attrValueList1);
            spuAttrVo.setId(id);
            return spuAttrVo;

        }).collect(Collectors.toList());


        //????????????id???????????????????????????spuSpecVoList
        List<Spec> specList =  specMapper.findListByTypeId(typeId);

        List<Long> specIdList = specList.stream().map(x -> x.getId()).collect(Collectors.toList());

        //????????????id??????????????????????????????
        QueryWrapper<SpecValue> specValueQueryWrapper = new QueryWrapper<>();
        specValueQueryWrapper.in("specId",specIdList);
        List<SpecValue> specValueList = specValueMapper.selectList(specValueQueryWrapper);

        //??????
        List<SpuSpecVo> spuSpecVoList = specList.stream().map(x -> {
            SpuSpecVo spuSpecVo = new SpuSpecVo();
            spuSpecVo.setSpecName(x.getSpecName());
            spuSpecVo.setId(x.getId());
            spuSpecVo.setSpecValueList(specValueList.stream().filter(y -> y.getSpecId().longValue() == x.getId().longValue()).collect(Collectors.toList()));
            return spuSpecVo;
        }).collect(Collectors.toList());


        //
        SpuVo spuVo = new SpuVo();
        spuVo.setBrandList(brandList);
        spuVo.setSpuAttrVoList(spuAttrVoList);
        spuVo.setSpuSpecVoList(spuSpecVoList);

        return ServerResponse.success(spuVo);
    }

    @Override
    public ServerResponse addSpu(SpuParam spuParam) {
        //??????spu
        Spu spu = spuParam.getSpu();
        spuMapper.insert(spu);
        Long id = spu.getId();
        String spuName = spu.getSpuName();

        String[] pricesArr = spuParam.getPrices().split(",");
        String[] stocksArr = spuParam.getStocks().split(",");
        String[] specInfosArr = spuParam.getSpecInfos().split(";");

        //14:a,b,c;15:d,e,f
        String[] skuImageArr = spuParam.getSkuImages().split(";");
        List<Sku> skuList = new ArrayList<>();
        for (int i = 0; i < pricesArr.length; i++) {


            String specInfo = specInfosArr[i];

            Sku sku = new Sku();

            sku.setSpuId(id);
            sku.setPrice(new BigDecimal(pricesArr[i]));
            sku.setStock(Integer.parseInt(stocksArr[i]));

            sku.setSpecInfo(specInfo);
            //??????colorId
            Long colorId = Long.parseLong(specInfo.split(",")[0].split(":")[0]);
            sku.setColorId(colorId);
            String image = Arrays.stream(skuImageArr).filter(x -> Long.parseLong(x.split("=")[0]) == colorId.longValue()).map(y -> y.split("=")[1].split(",")[0]).findFirst().get();

            sku.setImage(image);

            //sku?????????spu??????+sku?????????
            List<String> specValueNameList = Arrays.stream(specInfo.split(",")).map(x -> x.split(":")[1]).collect(Collectors.toList());
            String skuName = spuName +" "+StringUtils.join(specValueNameList," ");
            sku.setSkuName(skuName);

            skuList.add(sku);


        }
        //????????????
        if(skuList.size()>0){
            skuMapper.addBatch(skuList);
        }
        //??????????????????//14:a,b,c;15:d,e,f
        List<SkuImage> skuImageList = new ArrayList<>();
        Arrays.stream(skuImageArr).forEach(x ->{
            long colorId = Long.parseLong(x.split("=")[0]);
            Arrays.stream(x.split("=")[1].split(",")).forEach(y ->{
                SkuImage skuImage = new SkuImage();
                skuImage.setColorId(colorId);
                skuImage.setImage(y);
                skuImage.setSpuId(id);
                skuImageList.add(skuImage);

            });
        });
        //????????????
        if(skuImageList.size() >0){
            skuImageMapper.addBatch(skuImageList);
        }


        return ServerResponse.success();
    }


    //??????
    @Override
    public DataTableResult findList(SpuQueryParam spuQueryParam) {

        Long count = spuMapper.findListCount(spuQueryParam);
        List<Spu> spuList = spuMapper.findPageList(spuQueryParam);

        if(spuList.size() > 0){
            //??????spuIdLise
            List<Long> idList = spuList.stream().map(Spu::getId).collect(Collectors.toList());
            //???????????????skuList
            QueryWrapper<Sku> skuQueryWrapper = new QueryWrapper<>();
            skuQueryWrapper.in("spuId", idList);
            List<Sku> skuList = skuMapper.selectList(skuQueryWrapper);
            //????????????
            List<GoodsVo> goodsVoList = spuList.stream().map(x -> {
                GoodsVo goodsVo = new GoodsVo();
                goodsVo.setSpu(x);
                goodsVo.setSkuList(skuList.stream().filter(y -> y.getSpuId().longValue() == x.getId().longValue()).collect(Collectors.toList()));
                return goodsVo;
            }).collect(Collectors.toList());


            return new DataTableResult(spuQueryParam.getDraw(),count,count,goodsVoList);
        }
        return new DataTableResult(spuQueryParam.getDraw(),count,count,new ArrayList());

    }

    @Override
    public ServerResponse findSpu(Long id) {
        //??????spu??????
        Spu spu = spuMapper.selectById(id);
        //??????typeId ???????????????

        Long cate3 = spu.getCate3();
        Cate cate = cateMapper.selectById(cate3);

        //???????????????skuList
        QueryWrapper<Sku> skuQueryWrapper = new QueryWrapper<>();
        skuQueryWrapper.eq("spuId",id);//spuId
        List<Sku> skuList = skuMapper.selectList(skuQueryWrapper);

        //??????????????? skuImageVoList
        //??????spuId?????????spu????????????????????????
        QueryWrapper<SkuImage> skuImageQueryWrapper = new QueryWrapper<>();
        skuImageQueryWrapper.eq("spuId",id);
        List<SkuImage> skuImageList = skuImageMapper.selectList(skuImageQueryWrapper);
        //?????????????????????Id??????
        Set<Long> colorIdSet = skuImageList.stream().map(x -> x.getColorId()).collect(Collectors.toSet());//set??????
        //?????????????????????specValueList
        List<SpecValue> specValueList = specValueMapper.selectBatchIds(colorIdSet);
        //???specValueList ??? skuImageList ????????????
        List<SkuImageVo> skuImageVoList = specValueList.stream().map(x -> {
            SkuImageVo skuImageVo = new SkuImageVo();
            skuImageVo.setColorId(x.getId());
            skuImageVo.setColorName(x.getSpecValue());
            skuImageVo.setSkuImageList(skuImageList.stream().filter(y -> y.getColorId().longValue() == x.getId().longValue()).collect(Collectors.toList()));
            return skuImageVo;
        }).collect(Collectors.toList());


        //????????????
        SpuEditVo spuEditVo = new SpuEditVo();
        spuEditVo.setSpu(spu);
        spuEditVo.setTypeId(cate.getTypeId());
        spuEditVo.setSkuList(skuList);
        spuEditVo.setSkuImageVoList(skuImageVoList);

        return ServerResponse.success(spuEditVo);
    }

    @Override
    public ServerResponse deleteImage(Long key,String rootPath) {
        SkuImage skuImage = skuImageMapper.selectById(key);
        String image = skuImage.getImage();
        //?????????????????????????????????
//        File file = new File(rootPath + image);
//        if(file.exists()){
//            file.delete();
//        }
        OSSUtil.deleteFile(image);
        //????????????????????????
        skuImageMapper.deleteById(key);
        //??????????????????????????????
        return ServerResponse.success(image);
    }

    @Override
    public ServerResponse updateSpu(SpuParam spuParam) {
        Spu spu = spuParam.getSpu();
        Long spuId = spu.getId();
        //??????spu??????
        spuMapper.updateById(spu);

        //??????sku??????
        //?????? delete????????? insert????????? update
        //specInfos: "51_44:??????,31:8G,41:7??????;52_44:??????,32:16G,41:7??????;53_45:??????,31:8G,41:7??????;54_45:??????,32:16G,41:7??????"
        String[] stockArr = spuParam.getStocks().split(",");
        String[] priceArr = spuParam.getPrices().split(",");
        String[] specItemArr = spuParam.getSpecInfos().split(";");
        //16:a,b,c;18:x,y,z
        String[] skuImageArr = spuParam.getSkuImages().split(";");


        List<Sku> insertSkuList = new ArrayList<>();


        //???????????????skuList
        QueryWrapper<Sku> skuQueryWrapper = new QueryWrapper<>();
        skuQueryWrapper.eq("spuId",spuId);
        List<Sku> skuList = skuMapper.selectList(skuQueryWrapper);

        //?????????
        List<Long> updateIdList = new ArrayList<>();



        for (int i = 0; i <specItemArr.length ; i++) {
            //51_44:??????,31:8G,41:7??????
            String specItem = specItemArr[i];
            long skuId = Long.parseLong(specItem.split("_")[0]);
            String specInfo = specItem.split("_")[1];
            //??????== -1 ??????

            //skuname = spu + ???????????? "??????s21 ?????? 16G ?????????"
            List<String> specValueNameList = Arrays.stream(specInfo.split(",")).map(x -> x.split(":")[1]).collect(Collectors.toList());
            String skuName = spu.getSpuName()+""+StringUtils.join(specValueNameList,"");
            //skuImage ?????????????????????????????????
            String colorId = specInfo.split(",")[0].split(":")[0];
            String skuImage = Arrays.stream(skuImageArr).filter(x -> Long.parseLong(x.split("=")[0]) == Long.parseLong(colorId)).map(y -> y.split("=")[1].split(",")[0]).findFirst().get();


            if(skuId == -1){
                Sku sku = getSku(spuId, stockArr[i], priceArr[i], specInfo, skuName, colorId, skuImage);
                insertSkuList.add(sku);
            }else {
                //??????
                Sku sku = getSku(spuId, stockArr[i], priceArr[i], specInfo, skuName, colorId, skuImage);
                sku.setId(skuId);
                updateIdList.add(skuId);
                skuMapper.updateById(sku);
            }
        }

        if(insertSkuList.size() > 0){
            //????????????sku
            skuMapper.addBatch(insertSkuList);
        }


        //???????????????sku??????
        //??????skuList?????????????????? ?????????????????????skuId
        //??????skuId????????????updateList????????? ??????????????????
        List<Long> deleteIdList = skuList.stream().filter(x -> !updateIdList.contains(x.getId())).map(y -> y.getId()).collect(Collectors.toList());
        if(deleteIdList.size() > 0){
            skuMapper.deleteBatchIds(deleteIdList);
        }



        //??????id????????????????????? ???id????????????????????? ?????????????????????????????????

        //??????skuImage??????
        //?????????
        QueryWrapper<SkuImage> skuImageQueryWrapper = new QueryWrapper<>();
        skuImageQueryWrapper.eq("spuId",spuId);
        skuImageMapper.delete(skuImageQueryWrapper);
        //?????????
        //16:a,b,c;18:x,y,z
        List<SkuImage> skuImageList = new ArrayList<>();
        Arrays.stream(skuImageArr).forEach(x ->{
            Long colorId = Long.parseLong(x.split("=")[0]);
            Arrays.stream(x.split("=")[1].split(",")).forEach(y -> {
                SkuImage skuImage = new SkuImage();
                skuImage.setSpuId(spuId);
                skuImage.setImage(y);
                skuImage.setColorId(colorId);
                skuImageList.add(skuImage);
            });
        });
        if(skuImageList.size() > 0){
            //??????????????????
            skuImageMapper.addBatch(skuImageList);
        }



        return ServerResponse.success();
    }

    private Sku getSku(Long spuId, String s, String val, String specInfo, String skuName, String colorId, String skuImage) {
        Sku sku = new Sku();
        sku.setPrice(new BigDecimal(val));
        sku.setStock(Integer.parseInt(s));
        sku.setSpecInfo(specInfo);
        sku.setSpuId(spuId);
        sku.setSkuName(skuName);
        sku.setColorId(Long.parseLong(colorId));
        sku.setImage(skuImage);
        return sku;
    }

    @Override
    public ServerResponse deleteSpu(Long id,String rootpatch) {
        //??????spu
        spuMapper.deleteById(id);
        //??????spu?????????sku
        QueryWrapper<Sku> skuQueryWrapper = new QueryWrapper<>();
        skuQueryWrapper.eq("spuId",id);
        skuMapper.delete(skuQueryWrapper);
        //??????spu?????????skuImage
        QueryWrapper<SkuImage> skuImageQueryWrapper = new QueryWrapper<>();
        skuImageQueryWrapper.eq("spuId",id);
        List<SkuImage> skuImageList = skuImageMapper.selectList(skuImageQueryWrapper);

        //??????????????????????????????
        deleteImage(rootpatch, skuImageList);

        //??????spu?????????skuImage
        skuImageMapper.delete(skuImageQueryWrapper);

        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBatch(String ids, String rootPatch) {

        if(StringUtils.isEmpty(ids)){
            return ServerResponse.error();
        }
        List<Long> idList = Arrays.stream(ids.split(",")).map(x -> Long.parseLong(x)).collect(Collectors.toList());

        //????????????spu
        spuMapper.deleteBatchIds(idList);
        //????????????sku
        QueryWrapper<Sku> skuQueryWrapper = new QueryWrapper<>();
        skuQueryWrapper.in("spuId",idList);
        skuMapper.delete(skuQueryWrapper);
        //????????????spu?????????????????????
        QueryWrapper<SkuImage> skuImageQueryWrapper = new QueryWrapper<>();
        skuImageQueryWrapper.in("spuId",idList);
        List<SkuImage> skuImageList = skuImageMapper.selectList(skuImageQueryWrapper);
        deleteImage(rootPatch, skuImageList);
        //????????????image
        skuImageMapper.delete(skuImageQueryWrapper);

        return ServerResponse.success();
    }

    @Override
    public ServerResponse updateStatus(SpuStatusParam spuStatusParam) {
        Long spuId = spuStatusParam.getSpuId();
        String status = spuStatusParam.getStatus();
        String flag = spuStatusParam.getFlag();

        Spu spu = new Spu();
        spu.setId(spuId);
        if(flag.equals("isUp")){
            spu.setIsUp(status);
        }else if(flag.equals("isNew")){
            spu.setIsNew(status);
        }else if(flag.equals("isRec")){
            spu.setIsRec(status);
        }
        spuMapper.updateById(spu);
        //???????????????sku
        Sku sku = new Sku();
        if(flag.equals("isUp")){
            sku.setIsUp(status);
        }else if(flag.equals("isNew")){
            sku.setIsNew(status);
        }else if(flag.equals("isRec")){
            sku.setIsRec(status);
        }

        QueryWrapper<Sku> updateWrapper = new QueryWrapper<>();
        updateWrapper.eq("spuId",spuId);
        skuMapper.update(sku,updateWrapper);


        return ServerResponse.success();
    }

    private void deleteImage(String rootPatch, List<SkuImage> skuImageList) {
//        skuImageList.stream().forEach(x ->{
//            String image = x.getImage();
//            File file = new File(rootPatch + image);
//            if(file.exists()){
//                file.delete();
//            }
//
//        });
        List<String> imageList = skuImageList.stream().map(x -> x.getImage()).collect(Collectors.toList());
        OSSUtil.deleteFiles(imageList);
    }

}
