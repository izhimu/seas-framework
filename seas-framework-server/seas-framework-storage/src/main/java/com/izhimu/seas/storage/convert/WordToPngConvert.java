package com.izhimu.seas.storage.convert;

import com.izhimu.seas.storage.entity.FileInfo;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * Word转图片
 *
 * @author haoran
 * @version v1.0
 */
public class WordToPngConvert implements IFileConvert {

    private static final int IMAGE_WIDTH = 800;
    private static final int IMAGE_HEIGHT = 1000;
    private static final int MARGIN = 50;
    private static final int LINE_HEIGHT = 20;

    @Override
    public void convert(InputStream is, OutputStream os, Map<String, Object> param) {
        try {
            // 使用Apache POI读取Word文档
            XWPFDocument document = new XWPFDocument(is);
            
            // 创建一个BufferedImage
            BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            
            // 设置渲染提示，提高图像质量
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            // 设置背景为白色
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
            
            // 设置文本属性
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            
            // 获取Word文档中的段落
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            int yPosition = MARGIN;
            
            // 绘制每个段落的文本
            for (XWPFParagraph paragraph : paragraphs) {
                String text = paragraph.getText();
                if (text != null && !text.trim().isEmpty()) {
                    // 处理文本换行
                    String[] lines = wrapText(text);
                    for (String line : lines) {
                        if (yPosition >= IMAGE_HEIGHT - MARGIN) {
                            // 如果当前图片空间不足，可以考虑创建新图片（这里简化处理）
                            break;
                        }
                        g2d.drawString(line, MARGIN, yPosition);
                        yPosition += LINE_HEIGHT;
                    }
                }
            }
            
            g2d.dispose();
            
            // 将BufferedImage写入输出流
            ImageIO.write(image, "PNG", os);
            
            // 关闭文档
            document.close();
        } catch (Exception e) {
            log.error("Word转PNG转换失败", e);
        }
    }

    /**
     * 根据指定宽度折行文本
     *
     * @param text 原始文本
     * @return 折行后的文本数组
     */
    private String[] wrapText(String text) {
        // 简化处理，按字符数折行
        // 实际应用中可以根据字体度量信息精确计算
        int charsPerLine = 700 / 8; // 估算每行字符数
        if (text.length() <= charsPerLine) {
            return new String[]{text};
        }
        
        // 简单的折行处理
        java.util.List<String> lines = new java.util.ArrayList<>();
        int start = 0;
        while (start < text.length()) {
            int end = Math.min(start + charsPerLine, text.length());
            lines.add(text.substring(start, end));
            start = end;
        }
        return lines.toArray(new String[0]);
    }

    @Override
    public FileInfo getFileInfo(InputStream is) {
        try {
            XWPFDocument document = new XWPFDocument(is);
            FileInfo fileInfo = new FileInfo();
            // 由于Apache POI不直接提供页面计数等信息，这里设置默认值
            fileInfo.setPageCount(1);
            fileInfo.setWidth((double) IMAGE_WIDTH);
            fileInfo.setHeight((double) IMAGE_HEIGHT);
            document.close();
            return fileInfo;
        } catch (Exception e) {
            log.error("获取Word文件信息失败", e);
            return null;
        }
    }
}