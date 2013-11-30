package com.loveplusplus.demo.print.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfAppearance;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RadioCheckField;
import com.loveplusplus.demo.print.util.DateUtil;
import com.loveplusplus.demo.print.util.FileUtils;
@SmallTest
public class RadioTest extends AndroidTestCase {

	public void test() {
		try {
			pdf(getContext());
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void pdf(Context context) throws DocumentException, IOException {
		
		
		File pdf=FileUtils.createPDFToSDCard(context,"radiotest");
		/*
		 * // 设置title 华文中宋 18号 Font stzhongs18 =
		 * FontUtil.getStzhongFont(context, 18); // 设置内容 仿宋gb2312 12号 Font
		 * simfang12 = FontUtil.getSimfangFont(context, 12); Paragraph
		 * titleParagraph = new Paragraph("食品等产品生产质量安全监督抽查/复查抽样单", stzhongs18);
		 * titleParagraph.setAlignment(Element.ALIGN_CENTER);
		 * document.add(titleParagraph); Paragraph nullParagraph = new
		 * Paragraph(" "); nullParagraph.setAlignment(1);
		 * document.add(nullParagraph);
		 */
		Document document = new Document();
		// step 2
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream(pdf));
		// step 3
		document.open();
		// step 4
		// add the JavaScript
		// writer.addJavaScript(Utilities.readFileToString(RESOURCE));
		// add the radio buttons
		PdfContentByte canvas = writer.getDirectContent();
		Font font = new Font(FontFamily.HELVETICA, 18);
		//Font stzhongs18 = FontUtil.getStzhongFont(context, 18);
		

		// Add the check boxes
		PdfAppearance[] onOff = new PdfAppearance[2];
		onOff[0] = canvas.createAppearance(20, 20);
		onOff[0].rectangle(1, 1, 18, 18);
		onOff[0].stroke();
		onOff[1] = canvas.createAppearance(20, 20);
		onOff[1].setRGBColorFill(255, 128, 128);
		onOff[1].rectangle(1, 1, 18, 18);
		onOff[1].fillStroke();
		onOff[1].moveTo(1, 1);
		onOff[1].lineTo(19, 19);
		onOff[1].moveTo(1, 19);
		onOff[1].lineTo(19, 1);
		onOff[1].stroke();
		
		
		Rectangle rect = new Rectangle(180, 806, 200, 788);
		
		RadioCheckField checkbox = new RadioCheckField(writer, rect, "Hello", "on");
		
		PdfFormField field = checkbox.getCheckField();
		
		field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "Off", onOff[0]);
		
		field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "On", onOff[1]);
		
		writer.addAnnotation(field);
		
		
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("Hello", font), 210, 790, 0);

		// ⑤关闭文档。
		document.close();

	}

}