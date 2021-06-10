package com.fh.shop.admin.controller.goods;

import com.fh.shop.admin.biz.goods.ISpuService;
import com.fh.shop.admin.param.SpuStatusParam;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.controller.BaseController;
import com.fh.shop.admin.param.SpuParam;
import com.fh.shop.admin.param.SpuQueryParam;
import com.fh.shop.util.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Controller
public class SpuController extends BaseController {

    @Resource(name = "spuService")
    private ISpuService spuService;
    //展示跳转
    @RequestMapping(value = "spu/toList",method = RequestMethod.GET)
    public String toList(){

        return "/goods/list2";
    }

    //新增跳转
    @RequestMapping(value = "spu/toAdd",method = RequestMethod.GET)
    public String toAdd(){

        return "goods/add3";
    }

    //
    @RequestMapping(value = "spu/findSpuInfo",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse findSpuInfo(Long typeId){
        return spuService.findSpuInfo(typeId);
    }

    //新增
    @RequestMapping(value = "spu/addSpu",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addSpu(SpuParam spuParam){
        return spuService.addSpu(spuParam);
    }

    //查询
    @RequestMapping(value = "spu/findList" ,method = RequestMethod.POST)
    @ResponseBody
    public DataTableResult findList(SpuQueryParam spuQueryParam){
        return spuService.findList(spuQueryParam);
    }

    //修改跳转
    @RequestMapping(value = "spu/toEdit",method = RequestMethod.GET)
    public String toEdit(){
        return "goods/edit3";
    }


    @RequestMapping(value = "spu/findSpu",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse findSpu(Long id){
        return spuService.findSpu(id);
    }

    //删除图片
    @RequestMapping(value = "spu/deleteImage",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteImage(Long key, HttpServletRequest request){
        String rootPath = getRealPath("/", request);
        return  spuService.deleteImage(key,rootPath);
    }

    //更新
    @RequestMapping(value = "spu/updateSpu",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateSpu(SpuParam spuParam){
        return spuService.updateSpu(spuParam);
    }




    //删除
    @RequestMapping(value = "spu/deleteSpu",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteSpu(Long id,HttpServletRequest request){
        String rootPatch = getRealPath("/", request);
        return spuService.deleteSpu(id,rootPatch);
    }

    //批量删除
    @RequestMapping(value = "spu/deleteBatch",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteBatch(String ids,HttpServletRequest request){
        String rootPatch = getRealPath("/", request);
        return spuService.deleteBatch(ids,rootPatch);

    }

    //状态更新
    @RequestMapping(value = "spu/updateStatus",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateStatus(SpuStatusParam spuStatusParam){

        return spuService.updateStatus(spuStatusParam);

    }

    //刷新商品缓存
    @RequestMapping(value = "spu/clearCache",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse clearCache(){
        RedisUtil.delete("skuList");
        return ServerResponse.success();
    }
}
