package com.fh.shop.api.brand.biz;


import com.fh.shop.api.brand.mapper.IBrandMapper;
import com.fh.shop.api.brand.mapper.ICateMapper;
import com.fh.shop.api.brand.param.BrandQueryParam;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.brand.po.Cate;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.common.TableResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("brandService")
@Transactional(rollbackFor = Exception.class)
public class IBrandServiceImpl implements IBrandService {

    @Autowired
    private IBrandMapper brandMapper;
    @Autowired
    private ICateMapper cateMapper;

    @Override
    public ServerResponse addBrand(Brand brand) {
        brandMapper.insert(brand);
       return ServerResponse.success();
    }

    @Transactional(readOnly = true)
    public ServerResponse findList(BrandQueryParam brandQueryParam) {
        //获取总条数
        Long totalCount = brandMapper.findListCount(brandQueryParam);
        //获取分页列数
        List<Brand> brands = brandMapper.findPageList(brandQueryParam);
        TableResult tableResult = new TableResult(totalCount,brands);
        return ServerResponse.success(tableResult);

    }

    @Override
    public void delete(Long id, String rootRealPath) {

        //查询图片的路径
        Brand brand = brandMapper.findBrandById(id);
        String logo = brand.getLogo();
        File file = new File(rootRealPath + logo);
        if (file.exists()) {
            file.delete();
        }
        brandMapper.delete(id);
    }


    @Override
    public ServerResponse findBrandById(Long id) {
        Brand brand = brandMapper.findBrandById(id);

        return ServerResponse.success(brand);
    }

    @Override
    public ServerResponse update(Brand brand, String rootRealPath) {
        String logo = brand.getLogo();
        if (StringUtils.isNotEmpty(logo)) {
            //上传新图片
            //删除图片
            String oldLogo = brand.getOldLogo();
            //删除图片  删除得是绝对路径
            String logoPath = rootRealPath + oldLogo;
            File file = new File(logoPath);
            if (file.exists()) {
                file.delete();
            }


        } else {
            //没有上传新图片
            brand.setLogo(brand.getOldLogo());
        }
        //更新数据信息
        brandMapper.update(brand);


        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBatch(String ids, String realPath) {
        if (StringUtils.isNotEmpty(ids)) {
            //将ids数组转换为list集合idsList
            String[] idArr = ids.split(",");
            List<Long> idList = new ArrayList<>();
            for (String s : idArr) {
                idList.add(Long.parseLong(s));
            }
            //根据id集合查询要删除的记录的图片路径
            List<String> logoList = brandMapper.findBrandListByIdList(idList);
            //删除硬盘上的文件
            for (String logo : logoList) {
                File file = new File(realPath + logo);
                if (file.exists()) {
                    file.delete();
                }

            }
            //删除记录
            brandMapper.deleteBatch(idList);
            return ServerResponse.success();

        }
        return ServerResponse.error();


    }


    @Override
    public ServerResponse findBrandList(Long cateId) {
        //如果没有传过来cateId 那么就获取所有的品牌信息
        if(cateId == null){
            List<Brand> brandAllList = brandMapper.findBrandAllList();

            return ServerResponse.success(brandAllList);
        }
        else {
            //如果传过来了cateId 那么就获取所有叶子节点对应的typeId
            //获取所有的cate
            List<Cate> cateList = cateMapper.selectList(null);
            //根据cateId找到对应的树叶的所有的typeId
            Set<Long> typeIdList = new HashSet<>();
            getSubTree(cateId,cateList,typeIdList);
            //根据typeId的集合找到对应的品牌信息
            List<Brand> brandList =  brandMapper.findBrandListByTypeIdList(typeIdList);
            return  ServerResponse.success(brandList);
        }

    }


    private void getSubTree(Long cateId,List<Cate> cateList,Set<Long> typeIdList){
        for (Cate cate : cateList) {
            if(cateId.longValue() == cate.getPid()){
                if(cate.getTypeId() != null && isLeaf(cate.getId(),cateList)){//不为空 并且还是叶子
                    typeIdList.add(cate.getTypeId());
                }
                getSubTree(cate.getId(),cateList,typeIdList);
            }
        }
    }

    private boolean isLeaf(Long cateId,List<Cate> cateList){
        for (Cate cate : cateList) {
            if(cateId.longValue() == cate.getPid().longValue()){
                return  false;
            }
        }
        //如果没子节点  return true；
        return true;
    }


}
