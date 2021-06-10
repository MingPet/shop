package com.fh.shop.admin.controller.cate;

import com.fh.shop.admin.annotation.LogInfo;
import com.fh.shop.admin.biz.cate.ICateService;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.controller.BaseController;
import com.fh.shop.admin.param.CateParam;
import com.fh.shop.admin.po.cate.Cate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class CateController extends BaseController {

    @Resource(name = "cateService")
    private ICateService cateService;
    //展示跳转
    @RequestMapping(value = "cate/toList",method = RequestMethod.GET)
    public String toList(){

        return "/cate/list";
    }


    @RequestMapping(value = "/cate/findList", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse findList() {
        return cateService.findAllList();
    }

    //新增
    @LogInfo(info = "新增方法")
    @RequestMapping(value = "/cate/addCate", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addCate(Cate cate){
        return cateService.addCate(cate);
    }

    //删除
    @LogInfo(info = "删除方法")
    @RequestMapping(value = "/cate/delete", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteCate(String ids){
        return cateService.deleteCate(ids);
    }


    //回显

    @RequestMapping(value = "/cate/findCate", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse findCate(Long id){
        return  cateService.findCate(id);
    }

    //更新
    @LogInfo(info = "更新方法")
    @RequestMapping(value = "/cate/updateCate", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateCate(CateParam cateParam){
        return cateService.updateCate(cateParam);
    }

    //无限级联动 根据当前id找孩子
    @RequestMapping(value = "/cate/findCateChilds", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse findCateChilds(Long id){

        return cateService.findCateChilds(id);
    }
}
