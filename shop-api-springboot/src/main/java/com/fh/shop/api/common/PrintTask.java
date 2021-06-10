package com.fh.shop.api.common;

import com.fh.shop.api.util.MailUtil;
import com.fh.shop.mapper.ISkuMapper;
import com.fh.shop.param.SkuParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class PrintTask {


    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private ISkuMapper skuMapper;




    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");





    @Scheduled(fixedRate = 80000)
    public void reportCurrentTime() {

        List<SkuParam> skuList = skuMapper.findListSku();
        String tab = "<table border=\"1px\">" +
                "<tr bgcolor=\"FFCC00\">" +
                "<th>商品名</th>" +
                "<th>价格</th>" +
                "<th>库存</th>" +
                "<th>品牌名</th>" +
                "<th>分类名</th></tr>" ;

        for (SkuParam skuParam:skuList){
                tab+="<tr>\n" +
                        "    <td>"+skuParam.getSkuName()+"</td>\n" +
                        "    <td>"+skuParam.getPrice()+"</td>\n" +
                        "    <td>"+skuParam.getStock()+"</td>\n" +
                        "    <td>"+skuParam.getBrandName()+"</td>\n" +
                        "    <td>"+skuParam.getCateName()+"</td>\n" +

                        "</tr>";
        }
        tab +="</table>";
        String mail = "2861373589@qq.com";
        System.out.println("NOW：" + sdf.format(new Date()));
      /// mailUtil.sendMailHtml(mail, "库存警告", tab);
        //mailUtil.sendMailHtml(mail,"库存警告",tab);

    }
}
