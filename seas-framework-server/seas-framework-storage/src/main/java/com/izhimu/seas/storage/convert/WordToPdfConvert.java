package com.izhimu.seas.storage.convert;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * Word转PDF
 *
 * @author haoran
 * @version v1.0
 */
public class WordToPdfConvert extends SimpleFileConvert {

    @Override
    public void convert(InputStream is, OutputStream os, Map<String, Object> param) {
        PDDocument pdfDocument = null;
        XWPFDocument document = null;
        try {
            // 创建新的PDF文档
            pdfDocument = new PDDocument();
            PDPage page = new PDPage();
            pdfDocument.addPage(page);
            
            // 读取Word文档
            document = new XWPFDocument(is);
            
            // 创建内容流
            PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page);
            
            // 设置字体
            PDFont font = PDType1Font.HELVETICA;
            float fontSize = 12;
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.newLineAtOffset(20, 700);
            
            // 提取Word文档中的段落并写入PDF
            List<String> lines = new ArrayList<>();
            document.getParagraphs().forEach(paragraph -> {
                String text = paragraph.getText();
                if (text != null && !text.isEmpty()) {
                    // 简单处理换行
                    String[] splitLines = text.split("\n");
                    for (String line : splitLines) {
                        if (!line.trim().isEmpty()) {
                            lines.add(line);
                        }
                    }
                }
            });
            
            float leading = 1.5f * fontSize;
            contentStream.setLeading(leading);
            
            for (String line : lines) {
                contentStream.showText(line);
                contentStream.newLine();
            }
            
            contentStream.endText();
            contentStream.close();
            
            // 保存PDF到输出流
            pdfDocument.save(os);
        } catch (Exception e) {
            log.error("Word转PDF转换失败", e);
        } finally {
            // 关闭资源
            try {
                if (pdfDocument != null) {
                    pdfDocument.close();
                }
                if (document != null) {
                    document.close();
                }
            } catch (Exception e) {
                log.error("关闭资源时出错", e);
            }
        }
    }
}