package com.izhimu.seas.storage.convert;

import com.aspose.words.Document;
import com.aspose.words.PdfSaveOptions;
import com.izhimu.seas.core.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Word转PDF
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
public class WordToPdfConvert extends SimpleFileConvert {

    @Override
    public void convert(InputStream is, OutputStream os, Map<String, Object> param) {
        try {
            Document doc = new Document(is);
            PdfSaveOptions option = new PdfSaveOptions();
            doc.save(os, option);
        } catch (Exception e) {
            log.error(LogUtil.format("FileConvert", "word to pdf error"), e);
        }
    }
}
