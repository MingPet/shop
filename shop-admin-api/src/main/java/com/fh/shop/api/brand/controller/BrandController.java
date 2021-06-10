package com.fh.shop.api.brand.controller;

import com.fh.shop.api.brand.biz.IBrandService;
import com.fh.shop.api.common.BaseController;
import com.fh.shop.api.brand.param.BrandQueryParam;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.util.OssFileUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin
@RequestMapping("/api/brands")
@Api(tags = "品牌接口")
public class BrandController extends BaseController {

   // private static final Logger LOGGER = LoggerFactory.getLogger(BrandController.class);

    @Resource(name="brandService")
    private IBrandService brandService;




    //   新增
    @PostMapping("/addBrand")
    public ServerResponse addBrand(@RequestBody Brand brand) {
        return brandService.addBrand(brand);
    }


    //  查询
    @GetMapping("/findList")
    public ServerResponse findList(BrandQueryParam brandQueryParam) {
        return brandService.findList(brandQueryParam);
    }

    //  删除
    @DeleteMapping
    public ServerResponse delete(Long id,HttpServletRequest request){

        String realPath = getRealPath("/",request);
        brandService.delete(id,realPath);
        return ServerResponse.success();
    }


    //回显
    @GetMapping("/{id}")
    public ServerResponse findBrandById(@PathVariable Long id){
        return brandService.findBrandById(id);
    }

    //修改
     @PutMapping
     public ServerResponse update(@RequestBody Brand brand, HttpServletRequest request){
         String rootRealPath = request.getServletContext().getRealPath("/");
         return brandService.update(brand,rootRealPath);
     }

     //批量删除
     @PostMapping("/deleteBatch")
     public ServerResponse deleteBatch(String ids,HttpServletRequest request){
         String realPath = getRealPath("/",request);
         return brandService.deleteBatch(ids,realPath);
     }

     //
    @RequestMapping(value = "/findBrandList",method = RequestMethod.POST)

    public ServerResponse findBrandList(Long cateId){
       return brandService.findBrandList(cateId);


    }

    //上传图片
    @PostMapping("/upload")
    public ServerResponse upload(MultipartFile file){
        String res = OssFileUtil.uploadFile(file);
        return ServerResponse.success(res);
    }


}

