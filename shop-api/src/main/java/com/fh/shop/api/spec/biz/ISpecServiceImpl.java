package com.fh.shop.api.spec.biz;


import com.fh.shop.api.spec.mapper.ISpecMapper;
import com.fh.shop.api.spec.mapper.ISpecValueMapper;
import com.fh.shop.api.spec.param.SpecInfoParam;
import com.fh.shop.api.spec.param.SpecParam;
import com.fh.shop.api.spec.param.SpecQueryParam;
import com.fh.shop.api.spec.po.Spec;
import com.fh.shop.api.spec.po.SpecValue;
import com.fh.shop.api.spec.vo.SpecVo;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service("specService")
public class ISpecServiceImpl implements ISpecService {


    @Resource
    private ISpecMapper specMapper;
    @Resource
    private ISpecValueMapper specValueMapper;
    @Override
    public ServerResponse addSpec(SpecParam specParam) {
        //获取参数信息
        //颜色
        String specName = specParam.getSpecName();
        //1,2
        String specNameSorts = specParam.getSpecNameSorts();
        //黑色=1，红色=2
        String specVlueInfos = specParam.getSpecValueInfos();
        if (StringUtils.isEmpty(specName)||StringUtils.isEmpty(specNameSorts)
                                         ||StringUtils.isEmpty(specVlueInfos)){
            return ServerResponse.error();

        }
        //对参数进行拆分
        String[] specNameArr = specName.split(",");
        String[] specNameSortArr = specNameSorts.split(",");
        String[] specValueInfoArr = specVlueInfos.split(";");
        for (int i = 0; i < specNameArr.length; i++) {
            String same = specNameArr[i];
            String spSort = specNameSortArr[i];
            Spec spec =new Spec();
            spec.setSpecName(same);
            spec.setSort(Integer.parseInt(spSort));
            //调用mapper层插入规格信息
            specMapper.addSpec(spec);
            Long id = spec.getId();

            //插入每个规格对应的多个规格值
            String specValueInfo = specValueInfoArr[i];
            String[] specValueInfoList = specValueInfo.split(",");
            List<SpecValue> specValueList=new ArrayList<>();
            for (String s : specValueInfoList) {
                String[] tempArr = s.split("=");
                String specValue = tempArr[0];
                String specValueSort = tempArr[1];
                SpecValue specVal=new SpecValue();
                specVal.setSpecValue(specValue);
                specVal.setSort(Integer.parseInt(specValueSort));
                specVal.setSpecId(id);
                specValueList.add(specVal);
                // specValueMapper.addSpecValue(specVal);
            }
                //批量加入
                specValueMapper.addBatch(specValueList);


        }
        return ServerResponse.success();
    }

    @Override
    public DataTableResult queryList(SpecQueryParam specQueryParam) {
        // 业务逻辑
        // 计算总条数
        Long  count  =  specMapper.queryListCount(specQueryParam);
        //获取分页类表
        List<Spec> specList =  specMapper.queryListPage(specQueryParam);
        //组装数据
        return new DataTableResult(specQueryParam.getDraw(),count ,count ,specList);

    }

    @Override
    public ServerResponse findSpec(Long id) {
       //获取对应的规格信息
        Spec spec=specMapper.findSpec(id);
        //根据规格id获取规格值表中的对应规格值
        List<SpecValue>specValueList=specValueMapper.findSpecValueByid(id);
        //组装数据
        SpecVo specVo=new SpecVo();
        specVo.setSpec(spec);
        specVo.setSpecValueList(specValueList);
        return ServerResponse.success(specVo);
    }

    @Override
    public ServerResponse updateSpec(SpecParam specParam) {
        //获得参数信息
        //颜色
        Long id = specParam.getId();
        String specName = specParam.getSpecName();
        //1,2
        String specNameSorts = specParam.getSpecNameSorts();
        //黑色=1，红色=2
        String specVlueInfos = specParam.getSpecValueInfos();
        if (StringUtils.isEmpty(specName)||StringUtils.isEmpty(specNameSorts)
                ||StringUtils.isEmpty(specVlueInfos)){
            return ServerResponse.error();

        }
        //修改规格
        Spec spec =new Spec();
        spec.setId(id);
        spec.setSort(Integer.parseInt(specNameSorts));
        spec.setSpecName(specName);
        specMapper.updateSpec(spec);
        //删除规格对应的规格值
        specValueMapper.deleteSpecValueById(id);
        //插入每个规格对应的多个规格值
        String[] specValueInfoList = specVlueInfos.split(",");
        for (String s : specValueInfoList) {
            String[] tempArr = s.split("=");
            String specValue = tempArr[0];
            String specValueSort = tempArr[1];
            SpecValue specVal=new SpecValue();
            specVal.setSpecValue(specValue);
            specVal.setSort(Integer.parseInt(specValueSort));
            specVal.setSpecId(id);
            specValueMapper.addSpecValue(specVal);
        }

        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteSpec(Long id) {
        //删除规格信息
        specMapper.deleteSpec(id);
        //删除规格值信息
        specValueMapper.deleteSpecValueById(id);


        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBatch(String ids) {
        if (StringUtils.isEmpty(ids)){
            return ServerResponse.error();
        }
        String[] split = ids.split(",");
        List<Long>idList=new ArrayList<>();
        for (String s : split) {
           idList.add(Long.parseLong(s));

        }
        //批量删除
        specMapper.deleteBatch(idList);
        //批量删除规格值
        specValueMapper.deleteBatch(idList);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse addSpecList(List<SpecInfoParam> specInfoParamList) {
        specInfoParamList.stream().forEach(x ->{
            Spec spec = x.getSpec();
            List<SpecValue> specValueList = x.getSpecValueList();
            //插入规格
            specMapper.insert(spec);
            Long id = spec.getId();
            //规格值设置规格Id
            specValueList.stream().forEach(y -> y.setSpecId(id));
            specValueMapper.addBatch(specValueList);
        });
        return ServerResponse.success();
    }

}
