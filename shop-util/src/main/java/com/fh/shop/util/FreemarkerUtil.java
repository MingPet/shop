package com.fh.shop.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerUtil {
	
	/**
	 * 生成excel和word文件
	 */
	public static File generateExcelAndWord(String foderName,String templateName,List dataList,HttpServletRequest request){
		OutputStreamWriter out = null;
		File file = null;
		try {
			// 创建配置类
			Configuration cfg = new Configuration();
			// 设置模板所在位置
			cfg.setClassForTemplateLoading(FreemarkerUtil.class, foderName);
			// 设置编码格式
			cfg.setDefaultEncoding("utf-8");
			// 加载具体的模板
			Template template = cfg.getTemplate(templateName);
			
			// 创建数据模型
			Map<String,Object> dataModel = new HashMap<>();
			dataModel.put("dataList", dataList);
			
			// 将生成出来的excel放到项目中
			String path = request.getServletContext().getRealPath("");
			file = new File(path + "/" + UUID.randomUUID() + ".xlsx");
			
			out = new OutputStreamWriter(new FileOutputStream(file));
			template.process(dataModel, out);
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	} 
	
	/**
	 * 生成pdf文件
	 */
	public static File generatePdf(String foderName,String templateName,List dataList,HttpServletRequest request){
		OutputStreamWriter out = null;
		File file = null;
		try {
			// 1.创建配置类
			Configuration cfg = new Configuration();
			// 2.设置模板所在位置
			cfg.setClassForTemplateLoading(FreemarkerUtil.class, foderName);
			// 3.设置编码格式
			cfg.setDefaultEncoding("utf-8");
			// 4.加载具体的模板
			Template template = cfg.getTemplate(templateName);
			
			// 5.创建Write对象
			Writer writer = new StringWriter();
			// 创建数据模型
			Map<String,Object> dataModel = new HashMap<>();
			dataModel.put("dataList", dataList);
			// 6.调用模板对象中的process方法填充数据并输出内容到指定的文件
            template.process(dataModel,writer);
            // 7.从Writer对象中得到生成的html内容
            String html = writer.toString();
            // 8.创建Pdf渲染器
            ITextRenderer renderer = new ITextRenderer();
            // 9.设置字体，目前只支持黑体和宋体，否则Pdf中的中文将不会显示
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont("/template/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            // 10.将html内容放到Pdf渲染器中
            renderer.setDocumentFromString(html);
            // 11.调用Pdf渲染器中layout方法
            renderer.layout();
			
			// 将生成出来的excel放到项目中
			String path = request.getServletContext().getRealPath("");
			file = new File(path + "/" + UUID.randomUUID() + ".pdf");
			
			FileOutputStream os = new FileOutputStream(file);
            // 13.调用PDF渲染器中创建PDF文件的方法
            renderer.createPDF(os);
            renderer.finishPDF();
            os.close();
            writer.close();
		} catch (IOException | TemplateException | DocumentException e) {
			e.printStackTrace();
		}finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	} 

}
