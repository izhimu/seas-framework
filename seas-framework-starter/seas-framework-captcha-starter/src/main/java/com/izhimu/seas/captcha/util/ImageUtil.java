package com.izhimu.seas.captcha.util;

import com.izhimu.seas.core.utils.LogUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@UtilityClass
public class ImageUtil {

    /**
     * base64 字符串转图片
     *
     * @param base64String String
     * @return BufferedImage
     */
    public static BufferedImage getBase64StrToImage(String base64String) {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] bytes = decoder.decode(base64String);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            log.error(LogUtil.format("ImageUtil", "Error"), e);
        }
        return null;
    }
}
