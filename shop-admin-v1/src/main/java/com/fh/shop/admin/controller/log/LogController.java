package com.fh.shop.admin.controller.log;

import com.fh.shop.admin.annotation.LogInfo;
import com.fh.shop.admin.biz.log.ILogService;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.admin.controller.BaseController;
import com.fh.shop.admin.param.LogQueryParam;
import com.fh.shop.admin.po.log.Log;
import com.fh.shop.util.DateUtil;
import com.fh.shop.util.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;


@Controller
public class LogController extends BaseController {



    @Resource(name = "logService")
    private ILogService logService;


    @RequestMapping(value = "/log/index",method = RequestMethod.GET)
    public String index(){
        return "/log/log";
    }
    //查询


    @RequestMapping(value = "/log/findList", method = RequestMethod.POST)
    @ResponseBody
    public DataTableResult findList(LogQueryParam logQueryParam){

        return logService.findList(logQueryParam);
    }

    //导出excel
    @LogInfo(info = "导出excel方法")
    @RequestMapping(value = "/log/exportExcel", method = RequestMethod.POST)
    public void exportExcel (LogQueryParam logQueryParam, HttpServletResponse response){
        //查询符合条件的数据
        List<Log> logList =  logService.findAllList(logQueryParam);

        //将数据转换为对应的格式
        //创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建sheet页
        XSSFSheet sheet = workbook.createSheet();


        // 创建日期样式
        XSSFCellStyle dateStyle = workbook.createCellStyle();
        short format = workbook.createDataFormat().getFormat(DateUtil.FULL_YEAR);
        dateStyle.setDataFormat(format);
        // 创建标题行的样式
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font titleFont = workbook.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        titleStyle.setFont(titleFont);



        int rowIndex = 0;
        int cellIndex = 0;
        String[] heads = {"id","用户名","真实姓名","操作信息","操作时间"};

        //创建标题
        XSSFRow titleRow = sheet.createRow(rowIndex++);
        XSSFCell titleRowCell = titleRow.createCell(0);
        titleRowCell.setCellValue("日志列表");
        titleRowCell.setCellStyle(titleStyle);
        titleRow.setHeightInPoints(30); // 设置行的高度
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, heads.length-1);
        sheet.addMergedRegion(cellRangeAddress);
        //创建表头
        XSSFRow headRow = sheet.createRow(rowIndex++);

        for (int i = 0; i < heads.length; i++) {
            String head = heads[i];
            XSSFCell headRowCell = headRow.createCell(i);
            headRowCell.setCellValue(head);
            //设置列的宽度
            sheet.setColumnWidth(i,256*20);//下标从i开始
        }
        //创建表格内容
        for (Log log : logList) {
            //创建行
            XSSFRow bodyRow = sheet.createRow(rowIndex++);

            //创建单元格
            bodyRow.createCell(cellIndex++).setCellValue(log.getId());
            bodyRow.createCell(cellIndex++).setCellValue(log.getUserName());
            bodyRow.createCell(cellIndex++).setCellValue(log.getRealName());
            bodyRow.createCell(cellIndex++).setCellValue(log.getInfo());

            XSSFCell cell = bodyRow.createCell(cellIndex++);
            cell.setCellStyle(dateStyle);
            cell.setCellValue(log.getInsertTime());
            //还原
            cellIndex = 0;

        }



        //下载
        FileUtil.xlsDownloadFile(response,workbook);
    }

    //导出PDF
    @LogInfo(info = "查询pdf方法")
    @RequestMapping(value = "/log/exportPdf", method = RequestMethod.POST)
    public void exportPdf(LogQueryParam logQueryParam,HttpServletResponse response){
        //查询符合条件的数据
        List<Log> logList =  logService.findAllList(logQueryParam);
        //将数据转换为指定的格式
        Configuration configuration = new Configuration();
        //设置编码格式 utf-8
        configuration.setDefaultEncoding("utf-8");
        //模板文件夹所在的位置
        configuration.setClassForTemplateLoading(this.getClass(),"/template");

        //获取模板
        try {
            Template template = configuration.getTemplate("LogPdfTemplate.html");
            //往map里面设置数据
            Map data = new HashMap();

            //key value
            data.put("logs",logList);//"logs"和模板里面对应
            data.put("title","日志列表");//title写在这里方便后续修改
            String date2str = DateUtil.date2str(new Date(), DateUtil.FULL_Y_M_D);
            data.put("date",date2str);//日期

            //将数据和模板结合
            StringWriter result = new StringWriter();
            template.process(data,result);

            //导出pdf
            String htmlCon = result.toString();//将StringWriter转成String
            FileUtil.pdfDownloadFile(response,htmlCon);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        //下载
    }



//    //导出word
//    @RequestMapping(value = "/log/exportWord", method = RequestMethod.POST)
//    public void exportWord(LogQueryParam logQueryParam,HttpServletRequest request,HttpServletResponse response){
//        //查询符合条件的数据
//        List<Log> logList =  logService.findAllList(logQueryParam);
//        //将数据转换为指定的格式
//        Configuration configuration = new Configuration();
//        //设置编码
//        configuration.setDefaultEncoding("utf-8");
//        //设置模板文件夹的位置
//        configuration.setClassForTemplateLoading(this.getClass(),"/template");
//
//
//
//        FileOutputStream fileOutputStream = null;
//        OutputStreamWriter outputStreamWriter = null;
//        String fileName = "D:/test/"+UUID.randomUUID().toString()+".docx";
//        try {
//            //获取模板文件
//            Template template = configuration.getTemplate("LogExportWord.xml");
//            //组装数据
//            HashMap data = new HashMap();
//            data.put("logs",logList);
//            //将数据和模板文件合成
//
//             fileOutputStream = new FileOutputStream(fileName);
//            //utf-8必须写
//             outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
//            template.process(data,outputStreamWriter);
//
//
//
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TemplateException e) {
//            e.printStackTrace();
//        }finally {
//            if(outputStreamWriter != null){
//                try {
//                    outputStreamWriter.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(fileOutputStream!=null){
//                try {
//                    fileOutputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        //下载
//        File file = new File(fileName);
//        FileUtil.downloadFile(request,response,file);
//        //删除服务端垃圾文件
//        FileUtil.deleteFile(file);
//    }


    //导出word
    @LogInfo(info = "导出word方法")
    @RequestMapping(value = "/log/exportWord", method = RequestMethod.POST)
    public void exportWord(LogQueryParam logQueryParam, HttpServletResponse response, HttpServletRequest request) {
        // 查询符合条件的数据
        List<Log> logList = logService.findAllList(logQueryParam);
        // 创建Freemarker的配置
        Configuration configuration = new Configuration();
        // 设置编码
        configuration.setDefaultEncoding("utf-8");
        // 设置模板文件夹的位置
        configuration.setClassForTemplateLoading(this.getClass(), "/template");
        OutputStreamWriter outputStreamWriter = null;
        FileOutputStream fileOutputStream = null;
        String fileName = "d:/"+UUID.randomUUID().toString()+".docx";
        try {
            // 获取模板文件
            Template template = configuration.getTemplate("LogWordTemplate.xml");
            // 组装数据
            HashMap data = new HashMap();
            data.put("logs", logList);
            // 将数据和模板文件进行合成
            fileOutputStream = new FileOutputStream(fileName);
            // 特别重要，要指定utf-8的编码格式，否则效果出不来
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
            template.process(data, outputStreamWriter);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            if (null != outputStreamWriter) {
                try {
                    outputStreamWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fileOutputStream) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 下载和删除垃圾文件的操作必须放到文件流关闭后，再进行
        // 下载
        File file = new File(fileName);
        FileUtil.downloadFile(request, response, file);
        // 删除垃圾文件
        FileUtil.deleteFile(file);
    }

}
