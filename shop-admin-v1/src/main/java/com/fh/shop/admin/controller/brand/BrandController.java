package com.fh.shop.admin.controller.brand;

import com.fh.shop.admin.annotation.LogInfo;
import com.fh.shop.admin.biz.brand.IBrandService;
import com.fh.shop.admin.controller.BaseController;
import com.fh.shop.admin.param.BrandQueryParam;
import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Controller
public class BrandController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrandController.class);

    @Resource(name="brandService")
    private IBrandService brandService;

    @RequestMapping(value = "/brand/toAdd" , method = RequestMethod.GET)
    public String toAdd(){

        return "/brand/add";
    }


//    新增
    @RequestMapping(value = "/brand/addBrand", method = RequestMethod.POST)
    @LogInfo(info = "新增方法")
    public String addBrand(Brand brand) {
        brandService.addBrand(brand);
        return "redirect:/brand/toList.jhtml";
    }

    //弹框新增
    @ResponseBody
    @RequestMapping(value = "/brand/addBrandB",method = RequestMethod.POST)
    @LogInfo(info = "弹框新增方法")
    public ServerResponse addBrandB(Brand brand){
        //LOGGER.info("执行了com.fh.shop.admin.controller.brand.BrandController的addBrandB()方法");
        brandService.addBrand(brand);
        return ServerResponse.success();
    }


    @RequestMapping(value = "/brand/toList" , method = RequestMethod.GET)
    public String toList(){

        return "/brand/list";
    }


    //  查询
    @ResponseBody
    @RequestMapping(value = "/brand/findList",method = RequestMethod.POST)
    @LogInfo(info = "查询方法")
    public DataTableResult findList(BrandQueryParam brandQueryParam) {
        return brandService.findList(brandQueryParam);
    }

    //  删除
    @ResponseBody
    @RequestMapping(value = "/brand/delete",method = RequestMethod.POST)
    @LogInfo(info = "删除方法")
    public ServerResponse delete(Long id,HttpServletRequest request){

        String realPath = getRealPath("/",request);
        brandService.delete(id,realPath);
        return ServerResponse.success();
    }


    //回显
    @ResponseBody
    @RequestMapping(value = "/brand/findBrandById",method = RequestMethod.GET)
    public ServerResponse findBrandById(Long id){
        return brandService.findBrandById(id);
    }

    //修改
     @ResponseBody
     @RequestMapping(value = "/brand/update",method = RequestMethod.POST)
     @LogInfo(info = "修改方法")
     public ServerResponse update(Brand brand, HttpServletRequest request){
         String rootRealPath = request.getServletContext().getRealPath("/");
         return brandService.update(brand,rootRealPath);
     }

     //批量删除
     @ResponseBody
     @RequestMapping(value = "/brand/deleteBatch",method = RequestMethod.POST)
     @LogInfo(info = "批量删除方法")
     public ServerResponse deleteBatch(String ids,HttpServletRequest request){
         String realPath = getRealPath("/",request);
         return brandService.deleteBatch(ids,realPath);
     }

     //
    @ResponseBody
    @RequestMapping(value = "/brand/findBrandList",method = RequestMethod.POST)

    public ServerResponse findBrandList(Long cateId){
       return brandService.findBrandList(cateId);


    }


}

