package com.loveplusplus.demo.print.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.loveplusplus.demo.print.Sampling;

public class PDFUtils {

	private static final String TAG = "PDFUtils";

	public static boolean createSamplingPdf(Context mContext, File file,
			Map<String, String> map) {
		try {

			Document document = new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();

			// 标题
			Font simhei18 = FontUtil.getFont(mContext, 18, "simhei.ttf");
			Paragraph title = new Paragraph("河南省重点工业产品风险监测采样单", simhei18);
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);

			document.add(new Chunk(Chunk.NEWLINE));

			// 编号
			Font simfang12 = FontUtil.getFont(mContext, 12, "simfang.ttf");
			Paragraph sno = new Paragraph("编号：" + map.get(Sampling.SNO),
					simfang12);
			sno.setAlignment(Element.ALIGN_RIGHT);
			document.add(sno);

			// 创建表格
			int cols = 100;
			int m = 7;
			int n = 28;
			int mn = m + n;// 35
			int r = cols - mn;// 65
			// r
			int o = 27;
			int p = 10;
			int q = r - o - p;// 28
			// 100
			int x = mn;// 34
			int y = 33;
			int z = cols - x - y;// 33

			PdfPTable table1 = new PdfPTable(cols);
			// 表格默认占页面可编辑空间的80%，这里设置为100%
			table1.setWidthPercentage(100);
			// table1.setSpacingBefore(2f);
			// table1.setSpacingAfter(5f);

			table1.addCell(getCellWithCols(simfang12, "样品类型", mn));
			table1.addCell(getCellWithCols(simfang12,
					"√原料 ×辅料 √半成品 ×待检成品 √成品 ×其他____", r));
			table1.addCell(getCellWithCols(simfang12, "采样地点", mn));
			table1.addCell(getCellWithCols(simfang12,
					"√原辅料库所 ×生产线 √半成品库 ×待检区 √成品库 ×超市 √经销店 ×商场 √其他_____", r));

			table1.addCell(getCellWithRows(simfang12, "采样地点", 3, m));

			table1.addCell(getCellWithCols(simfang12, "采样地点（或企业）名称", n));
			table1.addCell(getCellWithCols(simfang12, map.get(Sampling.NAME), r));
			table1.addCell(getCellWithCols(simfang12, "采样地点（或企业）地址", n));
			table1.addCell(getCellWithCols(simfang12,
					map.get(Sampling.ADDRESS), r));
			table1.addCell(getCellWithCols(simfang12, "联系人及电话", n));
			table1.addCell(getCellWithCols(simfang12, map.get(Sampling.PEOPLE),
					r));

			table1.addCell(getCellWithRows(simfang12, "产品信息", 8, m));

			table1.addCell(getCellWithCols(simfang12, "产品名称", n));
			table1.addCell(getCellWithCols(simfang12,
					map.get(Sampling.PRODUCT_NAME), r));

			table1.addCell(getCellWithCols(simfang12, "规格型号", n));
			table1.addCell(getCellWithCols(simfang12,
					map.get(Sampling.PRODUCT_TYPE), o));
			table1.addCell(getCellWithCols(simfang12, "产品等级", p));
			table1.addCell(getCellWithCols(simfang12,
					map.get(Sampling.PRODUCT_LEVEL), q));

			table1.addCell(getCellWithCols(simfang12, "商标", n));
			table1.addCell(getCellWithCols(simfang12,
					map.get(Sampling.PRODUCT_BRAND), o));
			table1.addCell(getCellWithCols(simfang12, "执行标准", p));
			table1.addCell(getCellWithCols(simfang12,
					map.get(Sampling.PRODUCT_STANDARD), q));

			table1.addCell(getCellWithCols(simfang12, "生产许可证编号", n));
			table1.addCell(getCellWithCols(simfang12,
					map.get(Sampling.PRODUCT_SNO), r));

			table1.addCell(getCellWithCols(simfang12, "生产日期/批号", n));
			table1.addCell(getCellWithCols(simfang12,
					map.get(Sampling.PRODUCT_DATE), r));

			table1.addCell(getCellWithCols(simfang12, "保质期", n));
			table1.addCell(getCellWithCols(simfang12,
					map.get(Sampling.PRODUCT_EXPIRED), r));
			table1.addCell(getCellWithCols(simfang12, "采样数量", n));
			table1.addCell(getCellWithCols(simfang12, map.get(Sampling.NUMBER),
					r));
			table1.addCell(getCellWithCols(simfang12, "采样日期", n));
			table1.addCell(getCellWithCols(simfang12, map.get(Sampling.DATE), r));

			table1.addCell(getCellWithCols(simfang12,
					"生产企业是否同采样地点：√是	√否　（如果是，以下生产企业信息可不填）", cols));

			table1.addCell(getCellWithRows(simfang12, "生产企业", 3, m));

			table1.addCell(getCellWithCols(simfang12, "企业名称", n));
			table1.addCell(getCellWithCols(simfang12,
					map.get(Sampling.COMPANY_NAME), r));
			table1.addCell(getCellWithCols(simfang12, "企业地址", n));
			table1.addCell(getCellWithCols(simfang12,
					map.get(Sampling.COMPANY_ADDRESS), r));
			table1.addCell(getCellWithCols(simfang12, "联系人及电话", n));
			table1.addCell(getCellWithCols(simfang12,
					map.get(Sampling.COMPANY_PHONE), r));

			table1.addCell(getCellWithRows(simfang12, "采样机构", 4, m));

			table1.addCell(getCellWithCols(simfang12, "机构名称", n));
			table1.addCell(getCellWithCols(simfang12,
					map.get(Sampling.DEPART_NAME), r));
			table1.addCell(getCellWithCols(simfang12, "联系人", n));
			table1.addCell(getCellWithCols(simfang12,
					map.get(Sampling.DEPART_PEOPLE), r));
			table1.addCell(getCellWithCols(simfang12, "联系电话", n));
			table1.addCell(getCellWithCols(simfang12,
					map.get(Sampling.DEPART_PHONE), r));
			table1.addCell(getCellWithCols(simfang12, "传真/Email", n));
			table1.addCell(getCellWithCols(simfang12,
					map.get(Sampling.DEPART_EMAIL), r));

			 Font blackFont = FontUtil.getFont(mContext, 12,"simfang.ttf",Font.NORMAL, BaseColor.WHITE);
			//Font blackFont = FontUtil.getFont(mContext, 12, "simfang.ttf");

			table1.addCell(getMultiCell(simfang12, blackFont, "备注（需要说明其他问题）："
					+ map.get(Sampling.REMARK), cols, 25 + 43 * 0));

			table1.addCell(getMultiCell3(simfang12, blackFont, "受检企业盖章：",
					"（买样不用盖章）", "2013年05月31日", x, 7, 6 + 14 * 2 + 7));
			table1.addCell(getMultiCell2(simfang12, blackFont, "采样机构（盖章）：",
					"2013年05月31日", y, 5 + 14 * 3 + 7));
			table1.addCell(getMultiCell2(simfang12, blackFont, "采样人（签名）：",
					"2013年05月31日", z, 5 + 13 * 3 + 6));
			document.add(table1);

			// 注
//			Font simfang10 = FontUtil.getFont(mContext, 10, "simfang.ttf");
//
//			Paragraph r1 = new Paragraph(
//					"注：1、采集原料样品时，“采样地点”填写生产企业信息，“生产企业”填写原料的生产企业信息。", simfang10);
//			document.add(r1);
//			Paragraph r2 = new Paragraph("2、采样人须各自签名。", simfang10);
//			document.add(r2);
//			Paragraph r3 = new Paragraph("3、市场买样需要附购物小票、发票。", simfang10);
//			document.add(r3);
//
			// 空行
			Paragraph nullParagraph = new Paragraph("");
			document.add(nullParagraph);

			document.add(Chunk.NEWLINE);
			// 步骤 5:关闭文档
			document.close();
			return true;
		} catch (Exception e) {
			Log.e(TAG, "", e);
			return false;
		}
	}

	public static boolean test(Context mContext, File file,
			Map<String, String> map) {
		try {
			Document document = new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();

			// 标题
			Font simhei18 = FontUtil.getFont(mContext, 18, "simhei.ttf");
			Paragraph title = new Paragraph("产品质量监督检查/复查抽样单", simhei18);
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);

			document.add(new Chunk(Chunk.NEWLINE));

			// 编号
			Font simfang12 = FontUtil.getFont(mContext, 12, "simfang.ttf");
			Paragraph sno = new Paragraph("编号：" + "201306012001", simfang12);
			sno.setAlignment(Element.ALIGN_RIGHT);
			document.add(sno);

			// 创建表格
			int cols = 100;
			int m = 5;
			int n = 12;
			int o1 = 11;
			int o2 = 11;
			int o3 = 11;
			int p = 5;
			int q = 5;
			int r = 20;
			int s = 20;

			PdfPTable table1 = new PdfPTable(cols);
			// 表格默认占页面可编辑空间的80%，这里设置为100%
			table1.setWidthPercentage(100);

			table1.addCell(getCellWithCols(simfang12, "任务来源", m + n));
			table1.addCell(getCellWithCols(simfang12, "", o1 + o2 + o3));
			table1.addCell(getCellWithCols(simfang12, "检查类别", p + q));
			table1.addCell(getCellWithCols(simfang12, "", r + s));

			table1.addCell(getCellWithRows(simfang12, "受检单位", 3, m));
			table1.addCell(getCellWithRows(simfang12, "名称及通讯地址", 3, n));

			table1.addCell(getCellWithCols(simfang12, "A", o1 + o2 + o3));
			table1.addCell(getCellWithCols(simfang12, "法人代表", p + q));
			table1.addCell(getCellWithCols(simfang12, "B", r + s));

			table1.addCell(getCellWithCols(simfang12, "C", o1 + o2 + o3));
			table1.addCell(getCellWithRows(simfang12, "联系人及电话", 2, p + q));
			table1.addCell(getCellWithRows(simfang12, "D", 2, r + s));

			table1.addCell(getCellWithCols(simfang12, "E", o1 + o2 + o3));

			table1.addCell(getCellWithRows(simfang12, "生产单位", 10, m));

			table1.addCell(getCellWithCols(simfang12, "单位名称", n));
			table1.addCell(getCellWithCols(simfang12, "河南xx有限公司", o1 + o2 + o3));

			table1.addCell(getCellWithRows(simfang12, "经济类型", 10, p));

			table1.addCell(getCellWithRows(simfang12, "内资", 4, q));

			table1.addCell(getCellWithCols(simfang12, "√国有", r));
			table1.addCell(getCellWithCols(simfang12, "√私营", s));

			table1.addCell(getCellWithCols(simfang12, "单位地址", n));
			table1.addCell(getCellWithCols(simfang12, "xxx路xx号", o1 + o2 + o3));

			table1.addCell(getCellWithCols(simfang12, "√集体", r));
			table1.addCell(getCellWithCols(simfang12, "√有限公司", s));

			table1.addCell(getCellWithCols(simfang12, "邮政编码", n));
			table1.addCell(getCellWithCols(simfang12, "450000", o1 + o2 + o3));

			table1.addCell(getCellWithCols(simfang12, "√联营", r));
			table1.addCell(getCellWithCols(simfang12, "√股份有限公司", s));
			table1.addCell(getCellWithCols(simfang12, "法人代表", n));
			table1.addCell(getCellWithCols(simfang12, "张三", o1 + o2 + o3));

			table1.addCell(getCellWithCols(simfang12, "√股份合作", r));
			table1.addCell(getCellWithCols(simfang12, "√其他企业", s));

			table1.addCell(getCellWithCols(simfang12, "联系人", n));
			table1.addCell(getCellWithCols(simfang12, "李四", o1 + o2 + o3));

			table1.addCell(getCellWithRows(simfang12, "港澳台", 3, q));

			table1.addCell(getCellWithCols(simfang12, "√合资经营", r));
			table1.addCell(getCellWithCols(simfang12, "√合作经营", s));

			table1.addCell(getCellWithCols(simfang12, "联系电话", n));
			table1.addCell(getCellWithCols(simfang12, "15512345678", o1 + o2
					+ o3));

			table1.addCell(getCellWithRows(simfang12, "√港澳台独资经营", 2, r));
			table1.addCell(getCellWithRows(simfang12, "√港澳投资股份有限公司", 2, s));

			table1.addCell(getCellWithCols(simfang12, "营业执照", n));
			table1.addCell(getCellWithCols(simfang12, "xxxxxxxxxx", o1 + o2
					+ o3));
			table1.addCell(getCellWithCols(simfang12, "机构代码", n));
			table1.addCell(getCellWithCols(simfang12, "nnnnnnnnnnnn", o1 + o2
					+ o3));

			table1.addCell(getCellWithRows(simfang12, "外资", 3, q));
			table1.addCell(getCellWithCols(simfang12, "√中外合资", r));
			table1.addCell(getCellWithCols(simfang12, "√中外合作", s));

			table1.addCell(getCellWithRows(simfang12, "企业规模", 2, n));
			table1.addCell(getCellWithCols(simfang12, "人数", o1));
			table1.addCell(getCellWithCols(simfang12, "产值", o2));
			table1.addCell(getCellWithCols(simfang12, "产量", o3));

			table1.addCell(getCellWithRows(simfang12, "√外资企业", 2, r));
			table1.addCell(getCellWithRows(simfang12, "√外商投资股份有限公司", 2, s));

			table1.addCell(getCellWithCols(simfang12, "500", o1));
			table1.addCell(getCellWithCols(simfang12, "600", o2));
			table1.addCell(getCellWithCols(simfang12, "700", o3));

			table1.addCell(getCellWithRows(simfang12, "受检产品信息", 8, m));

			table1.addCell(getCellWithCols(simfang12,
					"√工业产品生产许可证 √QS √CCC √其他", n + o1 + o2 + o3 + p + q));
			table1.addCell(getCellWithCols(simfang12, "证书编号", r));
			table1.addCell(getCellWithCols(simfang12, "eeeeeeeeeee", s));

			table1.addCell(getCellWithCols(simfang12, "产品名称", n + o1));
			table1.addCell(getCellWithCols(simfang12, "I", o2 + o3));

			table1.addCell(getCellWithCols(simfang12, "规格型号", p + q + r));
			table1.addCell(getCellWithCols(simfang12, "K", s));

			table1.addCell(getCellWithCols(simfang12, "生产日期/批号", n + o1));
			table1.addCell(getCellWithCols(simfang12, "M", o2 + o3));

			table1.addCell(getCellWithCols(simfang12, "商标", p + q + r));
			table1.addCell(getCellWithCols(simfang12, "E", s));

			table1.addCell(getCellWithCols(simfang12, "抽样数量", n + o1));
			table1.addCell(getCellWithCols(simfang12, "G", o2 + o3));

			table1.addCell(getCellWithCols(simfang12, "产品等级", p + q + r));
			table1.addCell(getCellWithCols(simfang12, "I", s));

			table1.addCell(getCellWithCols(simfang12, "抽样基数/批量", n + o1));
			table1.addCell(getCellWithCols(simfang12, "G", o2 + o3));

			table1.addCell(getCellWithCols(simfang12, "标注执行标准/技术文件", p + q + r));
			table1.addCell(getCellWithCols(simfang12, "I", s));

			table1.addCell(getCellWithCols(simfang12, "抽样日期", n + o1));
			table1.addCell(getCellWithCols(simfang12, "G", o2 + o3));

			table1.addCell(getCellWithCols(simfang12, "封样状态", p + q + r));
			table1.addCell(getCellWithCols(simfang12, "I", s));

			table1.addCell(getCellWithCols(simfang12, "备样量及封存地点", n + o1));
			table1.addCell(getCellWithCols(simfang12, "G", o2 + o3));

			table1.addCell(getCellWithCols(simfang12, "寄送样地点", p + q + r));
			table1.addCell(getCellWithCols(simfang12, "I", s));

			table1.addCell(getCellWithCols(simfang12, "是否为出口产品", n + o1));
			table1.addCell(getCellWithCols(simfang12, "√是 √否", o2 + o3));

			table1.addCell(getCellWithCols(simfang12, "寄送样截止日期", p + q + r));
			table1.addCell(getCellWithCols(simfang12, "I", s));

			 Font blackFont = FontUtil.getFont(mContext, 12,"simfang.ttf",Font.NORMAL, BaseColor.WHITE);

			table1.addCell(getMultiCell(simfang12, blackFont, "备注（需要说明其他问题）："
					+ "", cols, 30 + 43 * 4));

			table1.addCell(getMultiCell3(simfang12, blackFont, "受检单位对上述内容无异议",
					"受检单位签名（盖章）：", "2013年05月31日", 32, 1, 2 + 13 * 3 + 6));
			table1.addCell(getMultiCell3(simfang12, blackFont, "生产单位对上述内容无异议",
					"生产单位签名（盖章）：", "2013年05月31日", 32, 1, 2 + 13 * 3 + 6));
			table1.addCell(getMultiCell3(simfang12, blackFont, "抽样人（签名）：",
					"抽样单位（盖章）", "2013年05月31日", 36, 7 + 15 * 3 + 6, 9));

			document.add(table1);

			//表格右侧
			PdfContentByte cb = writer.getDirectContent();
			BaseFont simfang = FontUtil.getBaseFont(mContext, "simfang.ttf");
			String[] ss = new String[] { "第", "一", "联", "：", "抽","样","单","位","留","存" };
			cb.beginText();
			cb.setFontAndSize(simfang, 10);
			for(int i=0;i<ss.length;i++){
				cb.setTextMatrix(560f, 500f-i*8);
				cb.showText(ss[i]);
			}
			cb.endText();
			
//			Font simfang10 = FontUtil.getFont(mContext, 10, "simfang.ttf");
//			Paragraph r1 = new Paragraph(
//					"注：1.技术文件指执行标准外的图纸、技术合同、产品说明书等有关产品技术的文件。", simfang10);
//			document.add(r1);
//			Paragraph r2 = new Paragraph("2.选择许可证、QS、CCC等类别后，填写相应证书编号。",
//					simfang10);
//			document.add(r2);
//			Paragraph r3 = new Paragraph("3.从生产单位抽样受检单位栏不填。", simfang10);
//			document.add(r3);
//
//			// 空行
//			Paragraph nullParagraph = new Paragraph(" ");
//			nullParagraph.setAlignment(1);
//			document.add(nullParagraph);
//			// 空行
//			document.add(new Chunk(Chunk.NEWLINE));
			
			// 步骤 5:关闭文档
			document.close();
			return true;
		} catch (Exception e) {
			Log.e(TAG, "", e);
			return false;
		}
	}
	public static boolean test2(Context mContext, File file,
			Map<String, String> map) {
		try {
			Document document = new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			
			// 标题
			Font simhei18 = FontUtil.getFont(mContext, 18, "simhei.ttf");
			Paragraph title = new Paragraph("食品等产品生产质量安全监督抽查/复查抽样单", simhei18);
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);
			
			document.add(new Chunk(Chunk.NEWLINE));
			
			// 编号
			Font simfang12 = FontUtil.getFont(mContext, 12, "simfang.ttf");
			Paragraph sno = new Paragraph("编号：" + "201306012001", simfang12);
			sno.setAlignment(Element.ALIGN_RIGHT);
			document.add(sno);
			
			// 创建表格
			int cols = 100;
			
			int m = 5;
			
			int n = 12;
			
			int o1 = 11;
			int o2 = 11;
			int o3 = 11;
			
			int p = 5;
			int q = 5;
			int r = 20;
			int s = 20;
			
			PdfPTable table1 = new PdfPTable(cols);
			// 表格默认占页面可编辑空间的80%，这里设置为100%
			table1.setWidthPercentage(100);
			
			table1.addCell(getCellWithCols(simfang12, "任务来源", m + n));
			table1.addCell(getCellWithCols(simfang12, "", o1 + o2 + o3));
			table1.addCell(getCellWithCols(simfang12, "检查类别", p + q));
			table1.addCell(getCellWithCols(simfang12, "", r + s));
			
			table1.addCell(getCellWithRows(simfang12, "受检单位", 3, m));
			table1.addCell(getCellWithRows(simfang12, "名称及通讯地址", 3, n));
			
			table1.addCell(getCellWithCols(simfang12, "A", o1 + o2 + o3));
			table1.addCell(getCellWithCols(simfang12, "法人代表", p + q));
			table1.addCell(getCellWithCols(simfang12, "B", r + s));
			
			table1.addCell(getCellWithCols(simfang12, "C", o1 + o2 + o3));
			table1.addCell(getCellWithRows(simfang12, "联系人及电话", 2, p + q));
			table1.addCell(getCellWithRows(simfang12, "D", 2, r + s));
			
			table1.addCell(getCellWithCols(simfang12, "E", o1 + o2 + o3));
			
			table1.addCell(getCellWithRows(simfang12, "生产单位", 10, m));
			
			table1.addCell(getCellWithCols(simfang12, "单位名称", n));
			table1.addCell(getCellWithCols(simfang12, "河南xx有限公司", o1 + o2 + o3));
			
			table1.addCell(getCellWithRows(simfang12, "经济类型", 10, p));
			
			table1.addCell(getCellWithRows(simfang12, "内资", 4, q));
			
			table1.addCell(getCellWithCols(simfang12, "√国有", r));
			table1.addCell(getCellWithCols(simfang12, "√私营", s));
			
			table1.addCell(getCellWithCols(simfang12, "单位地址", n));
			table1.addCell(getCellWithCols(simfang12, "xxx路xx号", o1 + o2 + o3));
			
			table1.addCell(getCellWithCols(simfang12, "√集体", r));
			table1.addCell(getCellWithCols(simfang12, "√有限公司", s));
			
			table1.addCell(getCellWithCols(simfang12, "邮政编码", n));
			table1.addCell(getCellWithCols(simfang12, "450000", o1 + o2 + o3));
			
			table1.addCell(getCellWithCols(simfang12, "√联营", r));
			table1.addCell(getCellWithCols(simfang12, "√股份有限公司", s));
			table1.addCell(getCellWithCols(simfang12, "法人代表", n));
			table1.addCell(getCellWithCols(simfang12, "张三", o1 + o2 + o3));
			
			table1.addCell(getCellWithCols(simfang12, "√股份合作", r));
			table1.addCell(getCellWithCols(simfang12, "√其他企业", s));
			
			table1.addCell(getCellWithCols(simfang12, "联系人", n));
			table1.addCell(getCellWithCols(simfang12, "李四", o1 + o2 + o3));
			
			table1.addCell(getCellWithRows(simfang12, "港澳台", 3, q));
			
			table1.addCell(getCellWithCols(simfang12, "√合资经营", r));
			table1.addCell(getCellWithCols(simfang12, "√合作经营", s));
			
			table1.addCell(getCellWithCols(simfang12, "联系电话", n));
			table1.addCell(getCellWithCols(simfang12, "15512345678", o1 + o2
					+ o3));
			
			table1.addCell(getCellWithRows(simfang12, "√港澳台独资经营", 2, r));
			table1.addCell(getCellWithRows(simfang12, "√港澳投资股份有限公司", 2, s));
			
			table1.addCell(getCellWithCols(simfang12, "营业执照", n));
			table1.addCell(getCellWithCols(simfang12, "xxxxxxxxxx", o1 + o2
					+ o3));
			table1.addCell(getCellWithCols(simfang12, "机构代码", n));
			table1.addCell(getCellWithCols(simfang12, "nnnnnnnnnnnn", o1 + o2
					+ o3));
			
			table1.addCell(getCellWithRows(simfang12, "外资", 3, q));
			table1.addCell(getCellWithCols(simfang12, "√中外合资", r));
			table1.addCell(getCellWithCols(simfang12, "√中外合作", s));
			
			table1.addCell(getCellWithRows(simfang12, "企业规模", 2, n));
			table1.addCell(getCellWithCols(simfang12, "产值： 产量：人数：", o1+o2+o3));
			
			table1.addCell(getCellWithRows(simfang12, "√外资企业", 2, r));
			table1.addCell(getCellWithRows(simfang12, "√外商投资股份有限公司", 2, s));
			
			table1.addCell(getCellWithCols(simfang12, "√大型 √中型 √小型", o1+o2+o3));
			
			table1.addCell(getCellWithRows(simfang12, "受检产品信息", 9, m));
			
			table1.addCell(getCellWithCols(simfang12,
					"√食品 √食品相关产品 √化妆品 √小作坊", n + o1 + o2 + o3 + p + q));
			table1.addCell(getCellWithCols(simfang12, "证书编号", r));
			table1.addCell(getCellWithCols(simfang12, "eeeeeeeeeee", s));
			
			table1.addCell(getCellWithCols(simfang12, "产品名称", n + o1));
			table1.addCell(getCellWithCols(simfang12, "I", o2 + o3));
			
			table1.addCell(getCellWithCols(simfang12, "规格型号", p + q + r));
			table1.addCell(getCellWithCols(simfang12, "K", s));
			
			table1.addCell(getCellWithCols(simfang12, "生产日期/批号", n + o1));
			table1.addCell(getCellWithCols(simfang12, "M", o2 + o3));
			
			table1.addCell(getCellWithCols(simfang12, "商标", p + q + r));
			table1.addCell(getCellWithCols(simfang12, "E", s));
			
			table1.addCell(getCellWithCols(simfang12, "抽样数量", n + o1));
			table1.addCell(getCellWithCols(simfang12, "G", o2 + o3));
			
			table1.addCell(getCellWithCols(simfang12, "产品等级", p + q + r));
			table1.addCell(getCellWithCols(simfang12, "I", s));
			
			table1.addCell(getCellWithCols(simfang12, "抽样基数/批量", n + o1));
			table1.addCell(getCellWithCols(simfang12, "G", o2 + o3));
			
			table1.addCell(getCellWithCols(simfang12, "标注执行标准/技术文件", p + q + r));
			table1.addCell(getCellWithCols(simfang12, "I", s));
			
			table1.addCell(getCellWithCols(simfang12, "抽样日期", n + o1));
			table1.addCell(getCellWithCols(simfang12, "G", o2 + o3));
			
			table1.addCell(getCellWithCols(simfang12, "封样状态", p + q + r));
			table1.addCell(getCellWithCols(simfang12, "I", s));
			
			table1.addCell(getCellWithCols(simfang12, "备样量及封存地点", n + o1));
			table1.addCell(getCellWithCols(simfang12, "G", o2 + o3));
			
			table1.addCell(getCellWithCols(simfang12, "寄送样地点", p + q + r));
			table1.addCell(getCellWithCols(simfang12, "I", s));
			
			table1.addCell(getCellWithCols(simfang12, "寄送样截止日期", n + o1));
			table1.addCell(getCellWithCols(simfang12, "G", o2 + o3));
			
			table1.addCell(getCellWithCols(simfang12, "√中国名牌 √省名牌", p + q + r+ s));
			
			table1.addCell(getCellWithCols(simfang12, "采样地点", n ));
			table1.addCell(getCellWithCols(simfang12, "√企业成品库 √原料库 √生产线", o1+o2 + o3+p + q));
			table1.addCell(getCellWithCols(simfang12, "√城市 √城乡结合部 √农村", r+s));
			
			
			table1.addCell(getCellWithRows(simfang12, "抽样单位", 3, m));
			
			table1.addCell(getCellWithCols(simfang12, "单位名称", n));
			table1.addCell(getCellWithCols(simfang12, "A", o1 + o2 + o3+p+q));
			table1.addCell(getCellWithCols(simfang12, "联系人", r));
			table1.addCell(getCellWithCols(simfang12, "B", s));
			
			table1.addCell(getCellWithCols(simfang12, "单位地址", n));
			table1.addCell(getCellWithCols(simfang12, "A", o1 + o2 + o3+p+q));
			table1.addCell(getCellWithCols(simfang12, "联系电话", r));
			table1.addCell(getCellWithCols(simfang12, "B", s));
			
			table1.addCell(getCellWithCols(simfang12, "邮政编码", n));
			table1.addCell(getCellWithCols(simfang12, "A", o1 + o2 + o3+p+q));
			table1.addCell(getCellWithCols(simfang12, "传真/Email", r));
			table1.addCell(getCellWithCols(simfang12, "B", s));
			
			
			table1.addCell(getCellWithRows(simfang12, "现场抽查情况", 2, m+n));
			
			table1.addCell(getCellWithRows(simfang12, "出厂检验落实情况：√检验 √未检验 食品添加剂使用情况：√正常√违规  \n出产场所卫生状况：√好 √一般 √差 微生物指标不合格不得复查 √",2, o1+o2+o3+p+q+r+s));
			
			Font blackFont = FontUtil.getFont(mContext, 12,"simfang.ttf",Font.NORMAL, BaseColor.WHITE);
			
			table1.addCell(getCellWithRows(simfang12, "备注", 2, m+n));
			table1.addCell(getCellWithRows(simfang12, "《企业监督检查记录》登记情况：登记；未登记\n其他情况：",2, o1+o2+o3+p+q+r+s));
			
			table1.addCell(getMultiCell3(simfang12, blackFont, "受检单位对上述内容无异议",
					"受检单位签名（盖章）：", "2013年05月31日", 32, 1, 2 + 13 * 3 + 6));
			table1.addCell(getMultiCell3(simfang12, blackFont, "生产单位对上述内容无异议",
					"生产单位签名（盖章）：", "2013年05月31日", 32, 1, 2 + 13 * 3 + 6));
			table1.addCell(getMultiCell3(simfang12, blackFont, "抽样人（签名）：",
					"抽样单位（盖章）", "2013年05月31日", 36, 7 + 15 * 3 + 6, 9));
			
			document.add(table1);
			
			Font simfang10 = FontUtil.getFont(mContext, 10, "simfang.ttf");
			Paragraph r1 = new Paragraph("注：1、此单一式4联（在生产领域抽样时使用）。抽样单位、生产单位、组织抽查的部门、负责监督抽查后处理的质量技术监督部门各一份。2、产量，填写上年度的实际年产量；证书编号，填写食品等产品生产许可证编号。3、抽样人员将抽样信息登记到《企业监督检查记录》上。", simfang10);
			document.add(r1);
			
			// 空行
			Paragraph nullParagraph = new Paragraph(" ");
			nullParagraph.setAlignment(1);
			document.add(nullParagraph);
			// 空行
			document.add(new Chunk(Chunk.NEWLINE));
			
			
			//表格右侧
			PdfContentByte cb = writer.getDirectContent();
			BaseFont simfang = FontUtil.getBaseFont(mContext, "simfang.ttf");
			String[] ss = new String[] { "第", "一", "联", "：", "抽","样","单","位","留","存" };
			cb.beginText();
			cb.setFontAndSize(simfang, 10);
			for(int i=0;i<ss.length;i++){
				cb.setTextMatrix(560f, 500f-i*8);
				cb.showText(ss[i]);
			}
			cb.endText();
			
			
			// 步骤 5:关闭文档
			document.close();
			return true;
		} catch (Exception e) {
			Log.e(TAG, "", e);
			return false;
		}
	}

	

	private static PdfPCell getCell(Font font, String content) {
		Paragraph p = new Paragraph(content, font);
		PdfPCell cell = new PdfPCell(p);
		return cell;
	}

	private static PdfPCell getCellWithCols(Font font, String content, int i) {
		Paragraph p = new Paragraph(content, font);
		PdfPCell cell = new PdfPCell(p);
		cell.setColspan(i);
		return cell;
	}

	private static PdfPCell getMultiCell(Font font, Font blackFont,
			String content, int colspan, int blackspace) {
		Phrase phrase = new Phrase();

		Chunk c1 = new Chunk(content, font);
		phrase.add(c1);

		Chunk c2 = new Chunk(getBlackStr(blackspace), blackFont);
		phrase.add(c2);

		Paragraph p = new Paragraph(phrase);

		PdfPCell cell = new PdfPCell(p);
		cell.setColspan(colspan);
		return cell;
	}

	private static PdfPCell getMultiCell3(Font font, Font blackFont,
			String contextPrefix, String contextMiddle, String contextSuffix,
			int colspan, int blackspace, int blackspace2) {
		Phrase phrase = new Phrase();
		Chunk c1 = new Chunk(contextPrefix, font);
		phrase.add(c1);
		Chunk c2 = new Chunk(getBlackStr(blackspace), blackFont);
		phrase.add(c2);
		Chunk c3 = new Chunk(contextMiddle, font);
		phrase.add(c3);
		Chunk c4 = new Chunk(getBlackStr(blackspace2), blackFont);
		phrase.add(c4);
		Chunk c5 = new Chunk(contextSuffix, font);
		phrase.add(c5);
		Paragraph p = new Paragraph(phrase);
		PdfPCell cell = new PdfPCell(p);
		cell.setColspan(colspan);
		return cell;
	}

	private static PdfPCell getMultiCell2(Font font, Font blackFont,
			String contextPrefix, String contextSuffix, int colspan,
			int blackspace) {
		Phrase phrase = new Phrase();

		Chunk c1 = new Chunk(contextPrefix, font);
		phrase.add(c1);

		Chunk c2 = new Chunk(getBlackStr(blackspace), blackFont);
		phrase.add(c2);

		Chunk c3 = new Chunk(contextSuffix, font);
		phrase.add(c3);

		Paragraph p = new Paragraph(phrase);
		PdfPCell cell = new PdfPCell(p);
		cell.setColspan(colspan);
		return cell;
	}

	private static PdfPCell getCellWithRows(Font font, String content, int i,
			int m) {
		Paragraph p = new Paragraph(content, font);
		PdfPCell cell = new PdfPCell(p);
		cell.setRowspan(i);
		cell.setColspan(m);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 水平
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直
		return cell;
	}

	// 得到n个正字，通过把正设置为白色，即可实现空格的效果
	private static String getBlackStr(int num) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < num; i++) {
			sb.append("正");
		}
		return sb.toString();
	}
}
