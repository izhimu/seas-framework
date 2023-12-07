package com.izhimu.seas.storage.convert;

import com.aspose.words.Document;
import com.aspose.words.PdfSaveOptions;
import com.izhimu.seas.core.log.LogWrapper;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Word转PDF
 *
 * @author haoran
 * @version v1.0
 */
public class WordToPdfConvert extends SimpleFileConvert {

    private static final LogWrapper log = LogWrapper.build("FileConvert");

    @Override
    public void convert(InputStream is, OutputStream os, Map<String, Object> param) {
        try {
            Document doc = new Document(is);
            PdfSaveOptions option = new PdfSaveOptions();
            doc.save(os, option);
        } catch (Exception e) {
            log.error("Word to pdf error", e);
        }
    }
}
