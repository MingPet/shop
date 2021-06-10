package com.fh.shop.admin.biz.spec;

import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.mapper.spec.ISpecMapper;
import com.fh.shop.admin.mapper.spec.ISpecValueMapper;
import com.fh.shop.admin.param.SpecParam;
import com.fh.shop.admin.param.SpecQueryParam;
import com.fh.shop.admin.po.spec.Spec;
import com.fh.shop.admin.po.spec.SpecValue;
import com.fh.shop.admin.vo.spec.SpecVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("specService")
public class ISpecServiceImpl implements ISpecService {

    @Autowired
    private ISpecMapper specMapper;
    @Autowired
    private ISpecValueMapper specValueMapper;


    public ServerResponse addSpec(SpecParam specParam){

        //获取参数信息
        //白色=1,黑色=2;3=1,6=2
        String specNames = specParam.getSpecNames();
        String specNameSorts = specParam.getSpecNameSorts();
        String specValueInfos = specParam.getSpecValueInfos();
        //对参数进行拆分
        String[] specNameArr = specNames.split(",");
        String[] specNameSortArr = specNameSorts.split(",");
        String[] specValueInfoArr = specValueInfos.split(";");
        //循环
        for (int i = 0; i <specNameArr.length ; i++) {
            String specName = specNameArr[i];
            String specNameSort = specNameSortArr[i];
            Spec spec = new Spec();

            spec.setSpecName(specName);
            spec.setSort(Integer.parseInt(specNameSort));

            //调用mapper层插入规格信息
            specMapper.addSpec(spec);

            Long id = spec.getId();
            //插入每个规格对应的规格值
            String specValueInfo = specValueInfoArr[i];
            String[] strings = specValueInfo.split(",");
            for (String s : strings) {
                String[] stringsArr = s.split("=");
                String specValue = stringsArr[0];
                String specSortValue = stringsArr[1];

                SpecValue specVal = new SpecValue();
                specVal.setSpecId(id);
                specVal.setSort(Integer.parseInt(specSortValue));
                specVal.setSpecValue(specValue);

                specValueMapper.addSpecValue(specVal);
            }
        }


        return ServerResponse.success();
    }

    @Override
    public DataTableResult findList(SpecQueryParam specQueryParam) {
        Long count = specMapper.findListCount(specQueryParam);
        List<Spec> specList = specMapper.findPageList(specQueryParam);
        return new DataTableResult(specQueryParam.getDraw(),count,count,specList);
    }

    @Override
    public ServerResponse findSpec(Long id) {
        //获取对应的规格信息
        Spec spec = specMapper.findSpec(id);
        //根据规格id获取到规格值中的对应的规格值
        List<SpecValue> specValueList = specValueMapper.findSpecValueBySpecId(id);
        //组装数据
        SpecVo specVo = new SpecVo();
        specVo.setSpec(spec);
        specVo.setSpecValueList(specValueList);


        return ServerResponse.success(specVo);
    }

    @Override
    public ServerResponse updateSpec(SpecParam specParam) {

        //获取对应的规格信息
        Long id = specParam.getId();
        String specNames = specParam.getSpecNames();
        String specNameSorts = specParam.getSpecNameSorts();
        String specValueInfos = specParam.getSpecValueInfos();
        //非空验证
        if(StringUtils.isEmpty(specNames) || StringUtils.isEmpty(specNameSorts) || StringUtils.isEmpty(specValueInfos)){
            return ServerResponse.error();
        }
        //修改规格
        Spec spec = new Spec();
        spec.setId(id);
        spec.setSpecName(specNames);
        spec.setSort(Integer.parseInt(specNameSorts));
        specMapper.updateSpec(spec);
        //删除规格对应的规格值
        specValueMapper.deleteSpecValueBySpecId(id);
        //重新插入新的规格值
        String[] strings = specValueInfos.split(",");
        List<SpecValue> specValueList = new ArrayList<>();
        for (String s : strings) {
            String[] stringsArr = s.split("=");
            String specValue = stringsArr[0];
            String specSortValue = stringsArr[1];

            SpecValue specVal = new SpecValue();
            specVal.setSpecId(id);
            specVal.setSort(Integer.parseInt(specSortValue));
            specVal.setSpecValue(specValue);

           // specValueMapper.addSpecValue(specVal);
            specValueList.add(specVal);
        }
        specValueMapper.addBatch(specValueList);



        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteSpec(Long id) {
        //删除规格信息
        specMapper.deleteSpec(id);

        //删除规格对应的规格值信息
        specValueMapper.deleteSpecValueBySpecId(id);

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
        //删除规格
        specMapper.deleteBatch(idList);
        //批量删除规格值
        specValueMapper.deleteBatch(idList);

        return ServerResponse.success();
    }



}
