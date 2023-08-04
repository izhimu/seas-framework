package com.izhimu.seas.captcha.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.izhimu.seas.cache.entity.EncryptKey;
import com.izhimu.seas.cache.service.EncryptService;
import com.izhimu.seas.cache.service.RedisService;
import com.izhimu.seas.captcha.config.CaptchaConfig;
import com.izhimu.seas.captcha.model.Captcha;
import com.izhimu.seas.captcha.model.Point;
import com.izhimu.seas.captcha.service.CaptchaService;
import com.izhimu.seas.captcha.util.ImageUtils;
import com.izhimu.seas.core.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Objects;
import java.util.Random;

/**
 * 滑动验证码服务实现
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
@Service
public class SlideCaptchaServiceImpl implements CaptchaService {
    private static final String IMAGE_TYPE_PNG = "png";
    private static final String CACHE_CAPTCHA_KEY = "seas:captcha:";
    private static final String CACHE_CAPTCHA_RECEIPT_KEY = "seas:captcha:receipt:";

    @Resource
    private CaptchaConfig config;

    @Resource
    private RedisService redisService;

    @Resource
    private EncryptService<EncryptKey, String> encryptService;

    @Override
    public Captcha get(Captcha captcha) {
        //原生图片
        String originalImageBase64 = loadImage(config.getOriginalPath(), config.getOriginalSize());
        BufferedImage originalImage = ImageUtils.getBase64StrToImage(originalImageBase64);
        if (Objects.isNull(originalImage)) {
            log.error("底图初始化失败！");
            return captcha;
        }
        //抠图图片
        String blockImageBase64 = loadImage(config.getBlockPath(), config.getBlockSize());
        BufferedImage blockImage = ImageUtils.getBase64StrToImage(blockImageBase64);
        if (Objects.isNull(blockImage)) {
            log.error("滑动底图初始化失败！");
            return captcha;
        }
        Captcha newCaptcha = pictureTemplatesCut(originalImage, blockImage, blockImageBase64);
        // 将坐标信息存入redis中
        redisService.set(CACHE_CAPTCHA_KEY.concat(newCaptcha.getToken()), newCaptcha.getPoint(), config.getCaptchaExpire());
        return newCaptcha.toView();
    }

    @Override
    public Captcha check(Captcha captcha) {
        captcha.setResult(false);
        // 取坐标信息
        String token = captcha.getToken();
        if (!redisService.hasKey(CACHE_CAPTCHA_KEY.concat(token))) {
            return captcha;
        }
        Point point = redisService.get(CACHE_CAPTCHA_KEY.concat(token), Point.class);
        if (Objects.isNull(point)) {
            return captcha;
        }
        redisService.del(CACHE_CAPTCHA_KEY.concat(token));
        // 解密
        String pointJson = captcha.getPointJson();
        pointJson = encryptService.decrypt(token, pointJson);
        if (StrUtil.isBlank(pointJson)) {
            return captcha;
        }
        Point checkPoint = JsonUtil.toObject(pointJson, Point.class);
        if (Objects.isNull(checkPoint) ||
                Math.abs(point.getX() - checkPoint.getX()) > config.getSlipOffset() ||
                point.getY() != checkPoint.getY()) {
            return captcha;
        }
        // 校验成功，将信息存入缓存
        String base64Key = Base64.encode(token.concat(pointJson));
        redisService.set(CACHE_CAPTCHA_RECEIPT_KEY.concat(base64Key), 0, config.getReceiptExpire());
        captcha.setResult(true);
        return captcha;
    }

    @Override
    public boolean verification(Captcha captcha) {
        try {
            String key = captcha.getCaptchaVerification();
            key = encryptService.decrypt(captcha.getToken(), key);
            key = Base64.encode(key);
            if (!redisService.hasKey(CACHE_CAPTCHA_RECEIPT_KEY.concat(key))) {
                return false;
            }
            redisService.del(CACHE_CAPTCHA_RECEIPT_KEY.concat(key));
            return true;
        } catch (Exception e) {
            log.error("验证码坐标解析失败", e);
            return false;
        }
    }

    /**
     * 根据模板切图
     *
     * @param originalImage    BufferedImage
     * @param blockImage       BufferedImage
     * @param blockImageBase64 String
     * @return Captcha
     */
    public Captcha pictureTemplatesCut(BufferedImage originalImage, BufferedImage blockImage, String blockImageBase64) {
        try {
            Captcha captcha = new Captcha();

            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();
            int blockWidth = blockImage.getWidth();
            int blockHeight = blockImage.getHeight();

            // 随机生成拼图坐标
            Point point = generateBlockPoint(originalWidth, originalHeight, blockWidth, blockHeight);
            captcha.setPoint(point);
            int x = point.getX();

            // 生成新的拼图图像
            BufferedImage newBlockImage = new BufferedImage(blockWidth, blockHeight, blockImage.getType());
            Graphics2D graphics = newBlockImage.createGraphics();

            int bold = 5;
            // 如果需要生成RGB格式，需要做如下配置,Transparency 设置透明
            newBlockImage = graphics.getDeviceConfiguration().createCompatibleImage(blockWidth, blockHeight, Transparency.TRANSLUCENT);
            // 新建的图像根据模板颜色赋值,源图生成遮罩
            cutByTemplate(originalImage, blockImage, newBlockImage, x);
            if (config.getInterferenceOptions() == 1) {
                int position;
                if (originalWidth - x - 5 > blockWidth * 2) {
                    //在原扣图右边插入干扰图
                    position = RandomUtil.randomInt(x + blockWidth + 5, originalWidth - blockWidth);
                } else {
                    //在原扣图左边插入干扰图
                    position = RandomUtil.randomInt(100, x - blockWidth - 5);
                }
                while (true) {
                    String s = loadImage(config.getBlockPath(), config.getBlockSize());
                    if (!blockImageBase64.equals(s)) {
                        interferenceByTemplate(originalImage, Objects.requireNonNull(ImageUtils.getBase64StrToImage(s)), position);
                        break;
                    }
                }
            } else if (config.getInterferenceOptions() > 1) {
                while (true) {
                    String s = loadImage(config.getBlockPath(), config.getBlockSize());
                    if (!blockImageBase64.equals(s)) {
                        int randomInt = RandomUtil.randomInt(blockWidth, 100 - blockWidth);
                        interferenceByTemplate(originalImage, Objects.requireNonNull(ImageUtils.getBase64StrToImage(s)), randomInt);
                        break;
                    }
                }
            }


            // 设置“抗锯齿”的属性
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setStroke(new BasicStroke(bold, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            graphics.drawImage(newBlockImage, 0, 0, null);
            graphics.dispose();

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(newBlockImage, IMAGE_TYPE_PNG, os);
            byte[] jigsawImages = os.toByteArray();

            ByteArrayOutputStream oriImagesOs = new ByteArrayOutputStream();
            ImageIO.write(originalImage, IMAGE_TYPE_PNG, oriImagesOs);
            byte[] oriCopyImages = oriImagesOs.toByteArray();
            captcha.setOriginalImage(Base64.encode(oriCopyImages).replaceAll("[\r\n]", ""));
            captcha.setBlockImage(Base64.encode(jigsawImages).replaceAll("[\r\n]", ""));
            captcha.setToken(point.getKey());
            captcha.setSecretKey(point.getPublicKey());
            return captcha;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }


    /**
     * 随机生成拼图坐标
     *
     * @param originalWidth  int
     * @param originalHeight int
     * @param blockWidth     int
     * @param blockHeight    int
     * @return PointDTO
     */
    private Point generateBlockPoint(int originalWidth, int originalHeight, int blockWidth, int blockHeight) {
        Random random = new Random();
        int widthDifference = originalWidth - blockWidth;
        int heightDifference = originalHeight - blockHeight;
        int x, y;
        if (widthDifference <= 0) {
            x = 5;
        } else {
            x = random.nextInt(originalWidth - blockWidth - 100) + 100;
        }
        if (heightDifference <= 0) {
            y = 5;
        } else {
            y = random.nextInt(originalHeight - blockHeight) + 5;
        }
        EncryptKey encryptKey = encryptService.createEncryptKey(config.getCaptchaExpire());
        String key = encryptKey.getKey();
        String publicKey = encryptKey.getPublicKey();
        return new Point(x, y, key, publicKey);
    }

    /**
     * 加载随机图片
     *
     * @param path String
     * @param size int
     * @return String
     */
    private String loadImage(String path, int size) {
        int i = RandomUtil.randomInt(0, size);
        i = i + 1;
        try (InputStream stream = ResourceUtil.getStream(path.concat(String.valueOf(i)).concat(".png"))) {
            return Base64.encode(stream);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * 生成遮罩
     *
     * @param oriImage      原图
     * @param templateImage 模板图
     * @param newImage      新抠出的小图
     * @param x             随机扣取坐标X
     */
    private static void cutByTemplate(BufferedImage oriImage, BufferedImage templateImage, BufferedImage newImage, int x) {
        // 临时数组遍历用于高斯模糊存周边像素值
        int[][] matrix = new int[3][3];
        int[] values = new int[9];

        int xLength = templateImage.getWidth();
        int yLength = templateImage.getHeight();
        // 模板图像宽度
        for (int i = 0; i < xLength; i++) {
            // 模板图片高度
            for (int j = 0; j < yLength; j++) {
                // 如果模板图像当前像素点不是透明色 copy源文件信息到目标图片中
                int rgb = templateImage.getRGB(i, j);
                if (rgb < 0) {
                    newImage.setRGB(i, j, oriImage.getRGB(x + i, j));

                    //抠图区域高斯模糊
                    readPixel(oriImage, x + i, j, values);
                    fillMatrix(matrix, values);
                    oriImage.setRGB(x + i, j, avgMatrix(matrix));
                }

                //防止数组越界判断
                if (i == (xLength - 1) || j == (yLength - 1)) {
                    continue;
                }
                int rightRgb = templateImage.getRGB(i + 1, j);
                int downRgb = templateImage.getRGB(i, j + 1);
                //描边处理，,取带像素和无像素的界点，判断该点是不是临界轮廓点,如果是设置该坐标像素是白色
                if ((rgb >= 0 && rightRgb < 0) || (rgb < 0 && rightRgb >= 0) || (rgb >= 0 && downRgb < 0) || (rgb < 0 && downRgb >= 0)) {
                    newImage.setRGB(i, j, Color.white.getRGB());
                    oriImage.setRGB(x + i, j, Color.white.getRGB());
                }
            }
        }
    }


    /**
     * 干扰抠图处理
     *
     * @param oriImage      原图
     * @param templateImage 模板图
     * @param x             随机扣取坐标X
     */
    private static void interferenceByTemplate(BufferedImage oriImage, BufferedImage templateImage, int x) {
        //临时数组遍历用于高斯模糊存周边像素值
        int[][] matrix = new int[3][3];
        int[] values = new int[9];

        int xLength = templateImage.getWidth();
        int yLength = templateImage.getHeight();
        // 模板图像宽度
        for (int i = 0; i < xLength; i++) {
            // 模板图片高度
            for (int j = 0; j < yLength; j++) {
                // 如果模板图像当前像素点不是透明色 copy源文件信息到目标图片中
                int rgb = templateImage.getRGB(i, j);
                if (rgb < 0) {
                    //抠图区域高斯模糊
                    readPixel(oriImage, x + i, j, values);
                    fillMatrix(matrix, values);
                    oriImage.setRGB(x + i, j, avgMatrix(matrix));
                }
                //防止数组越界判断
                if (i == (xLength - 1) || j == (yLength - 1)) {
                    continue;
                }
                int rightRgb = templateImage.getRGB(i + 1, j);
                int downRgb = templateImage.getRGB(i, j + 1);
                //描边处理，,取带像素和无像素的界点，判断该点是不是临界轮廓点,如果是设置该坐标像素是白色
                if ((rgb >= 0 && rightRgb < 0) || (rgb < 0 && rightRgb >= 0) || (rgb >= 0 && downRgb < 0) || (rgb < 0 && downRgb >= 0)) {
                    oriImage.setRGB(x + i, j, Color.white.getRGB());
                }
            }
        }

    }

    /**
     * 读取像素
     *
     * @param img    BufferedImage
     * @param x      int
     * @param y      int
     * @param pixels int[]
     */
    private static void readPixel(BufferedImage img, int x, int y, int[] pixels) {
        int xStart = x - 1;
        int yStart = y - 1;
        int current = 0;
        for (int i = xStart; i < 3 + xStart; i++) {
            for (int j = yStart; j < 3 + yStart; j++) {
                int tx = i;
                if (tx < 0) {
                    tx = -tx;
                } else if (tx >= img.getWidth()) {
                    tx = x;
                }
                int ty = j;
                if (ty < 0) {
                    ty = -ty;
                } else if (ty >= img.getHeight()) {
                    ty = y;
                }
                pixels[current++] = img.getRGB(tx, ty);
            }
        }
    }

    /**
     * 填充矩阵
     *
     * @param matrix int[][]
     * @param values int[]
     */
    private static void fillMatrix(int[][] matrix, int[] values) {
        int filled = 0;
        for (int[] x : matrix) {
            for (int j = 0; j < x.length; j++) {
                x[j] = values[filled++];
            }
        }
    }

    /**
     * 平均矩阵
     *
     * @param matrix int[][]
     * @return int
     */
    private static int avgMatrix(int[][] matrix) {
        int r = 0;
        int g = 0;
        int b = 0;
        for (int[] x : matrix) {
            for (int j = 0; j < x.length; j++) {
                if (j == 1) {
                    continue;
                }
                Color c = new Color(x[j]);
                r += c.getRed();
                g += c.getGreen();
                b += c.getBlue();
            }
        }
        return new Color(r / 8, g / 8, b / 8).getRGB();
    }
}
