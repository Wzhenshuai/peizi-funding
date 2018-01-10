package com.icaopan.util;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;

import com.icaopan.admin.util.LoggerUtil;
import com.icaopan.common.util.DataUtils;
import com.icaopan.log.LogUtil;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档 转载时请保留以下信息，注明出处！
 * 
 * @author leno
 * @version v1.0
 * @param <T>
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 */
public class ExcelUtil { 

	private static Logger logger=com.icaopan.util.LogUtil.getLogger(ExcelUtil.class);
	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */
	@SuppressWarnings("unchecked")
	public static void exportExcel(String title, String[] headers, String[] cols,
			JSONArray dataset, HttpServletResponse response, String datePattern) {
		
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 18);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		// style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		// style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		// font2.setFontName("仿宋_GB2312");
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		HSSFFont font3 = workbook.createFont();
		font3.setColor(HSSFColor.BLUE.index);
		// 声明一个画图的顶级管理器
		//HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		// HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
		// 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		// comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		// comment.setAuthor("leno");

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		int index1 = 0;
		for (int i = 0; i < dataset.size(); i++) {
			index1++;
			row = sheet.createRow(index1);
			JSONObject jn = dataset.getJSONObject(i);
			for (int j = 0; j < cols.length; j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellStyle(style2);
				try {
					Object value = jn.get(cols[j]);
					String textValue = null;
					if (value instanceof Boolean) {
						boolean bValue = (Boolean) value;
						textValue = "是";
						if (!bValue) {
							textValue = "否";
						}
						HSSFRichTextString richString = new HSSFRichTextString(
								textValue);
						richString.applyFont(font3);
						cell.setCellValue(richString);
					} else if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
						textValue = sdf.format(date);
						HSSFRichTextString richString = new HSSFRichTextString(
								textValue);
						richString.applyFont(font3);
						cell.setCellValue(richString);
					} else if (value instanceof BigDecimal) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(((BigDecimal) value).doubleValue());
						continue;
					} else {
						if(value==null){
							value="";
						}
						// 其它数据类型都当作字符串简单处理
						textValue = value.toString();
						HSSFRichTextString richString = new HSSFRichTextString(
								textValue);
						richString.applyFont(font3);
						cell.setCellValue(richString);
					}
				} catch (SecurityException e) {
					e.printStackTrace();
					logger.error("",e);
				} catch (IllegalArgumentException e) {
					logger.error("",e);
				} 
			}
		}
		OutputStream out = null;
		try { 
			out = response.getOutputStream();
			title=title+"-"+DateUtils.getDate();
			response.addHeader("Content-Disposition", "attachment;filename="
					+ java.net.URLEncoder.encode(title+".xls", "UTF-8"));
			workbook.write(out);
		} catch (IOException e) {
			logger.error("",e);
		}finally{
			try {
				if(out!=null){
					out.close();
				}
			} catch (IOException e) {
				logger.error("",e);
			}
		}
	}
}
