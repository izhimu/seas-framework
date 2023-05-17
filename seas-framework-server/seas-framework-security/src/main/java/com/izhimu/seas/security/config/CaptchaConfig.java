package com.izhimu.seas.security.config;

import com.anji.captcha.model.common.CaptchaTypeEnum;
import com.anji.captcha.model.common.Const;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.service.impl.CaptchaServiceFactory;
import com.anji.captcha.util.Base64Utils;
import com.anji.captcha.util.FileCopyUtils;
import com.anji.captcha.util.ImageUtils;
import com.anji.captcha.util.StringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Data
@ComponentScan("com.anji.captcha")
@Configuration
@ConfigurationProperties("seas.captcha")
public class CaptchaConfig {

    /**
     * 验证码类型.
     */
    private CaptchaTypeEnum type = CaptchaTypeEnum.BLOCKPUZZLE;

    /**
     * 滑动拼图底图路径.
     */
    private String jigsaw = "";

    /**
     * 点选文字底图路径.
     */
    private String picClick = "";


    /**
     * 右下角水印文字(我的水印).
     */
    private String waterMark = "";

    /**
     * 右下角水印字体(文泉驿正黑).
     */
    private String waterFont = "WenQuanZhengHei.ttf";

    /**
     * 点选文字验证码的文字字体(文泉驿正黑).
     */
    private String fontType = "WenQuanZhengHei.ttf";

    /**
     * 校验滑动拼图允许误差偏移量(默认5像素).
     */
    private String slipOffset = "5";

    /**
     * aes加密坐标开启或者禁用(true|false).
     */
    private Boolean aesStatus = true;

    /**
     * 滑块干扰项(0/1/2)
     */
    private String interferenceOptions = "0";

    /**
     * local缓存的阈值
     */
    private String cacheNumber = "1000";

    /**
     * 定时清理过期local缓存(单位秒)
     */
    private String timingClear = "180";

    /**
     * 缓存类型redis/local/....
     */
    private StorageType cacheType = StorageType.local;
    /**
     * 历史数据清除开关
     */
    private boolean historyDataClearEnable = false;

    /**
     * 一分钟内接口请求次数限制 开关
     */
    private boolean reqFrequencyLimitEnable = false;

    /***
     * 一分钟内check接口失败次数
     */
    private int reqGetLockLimit = 5;
    /**
     *
     */
    private int reqGetLockSeconds = 300;

    /***
     * get接口一分钟内限制访问数
     */
    private int reqGetMinuteLimit = 100;
    private int reqCheckMinuteLimit = 100;
    private int reqVerifyMinuteLimit = 100;

    /**
     * 点选字体样式
     */
    private int fontStyle = Font.BOLD;

    /**
     * 点选字体大小
     */
    private int fontSize = 25;

    /**
     * 点选文字个数，存在问题，暂不要使用
     */
    private int clickWordCount = 4;

    public enum StorageType {
        /**
         * 内存.
         */
        local,
        /**
         * redis.
         */
        redis,
        /**
         * 其他.
         */
        other,
    }

    @Bean
    @ConditionalOnMissingBean
    public CaptchaService captchaService(CaptchaConfig prop) {
        log.info("加载验证码配置 -> {}", prop);
        Properties config = new Properties();
        config.put(Const.CAPTCHA_CACHETYPE, prop.getCacheType().name());
        config.put(Const.CAPTCHA_WATER_MARK, prop.getWaterMark());
        config.put(Const.CAPTCHA_FONT_TYPE, prop.getFontType());
        config.put(Const.CAPTCHA_TYPE, prop.getType().getCodeValue());
        config.put(Const.CAPTCHA_INTERFERENCE_OPTIONS, prop.getInterferenceOptions());
        config.put(Const.ORIGINAL_PATH_JIGSAW, prop.getJigsaw());
        config.put(Const.ORIGINAL_PATH_PIC_CLICK, prop.getPicClick());
        config.put(Const.CAPTCHA_SLIP_OFFSET, prop.getSlipOffset());
        config.put(Const.CAPTCHA_AES_STATUS, String.valueOf(prop.getAesStatus()));
        config.put(Const.CAPTCHA_WATER_FONT, prop.getWaterFont());
        config.put(Const.CAPTCHA_CACAHE_MAX_NUMBER, prop.getCacheNumber());
        config.put(Const.CAPTCHA_TIMING_CLEAR_SECOND, prop.getTimingClear());

        config.put(Const.HISTORY_DATA_CLEAR_ENABLE, prop.isHistoryDataClearEnable() ? "1" : "0");

        config.put(Const.REQ_FREQUENCY_LIMIT_ENABLE, prop.isReqFrequencyLimitEnable() ? "1" : "0");
        config.put(Const.REQ_GET_LOCK_LIMIT, String.valueOf(prop.getReqGetLockLimit()));
        config.put(Const.REQ_GET_LOCK_SECONDS, String.valueOf(prop.getReqGetLockSeconds()));
        config.put(Const.REQ_GET_MINUTE_LIMIT, String.valueOf(prop.getReqGetMinuteLimit()));
        config.put(Const.REQ_CHECK_MINUTE_LIMIT, String.valueOf(prop.getReqCheckMinuteLimit()));
        config.put(Const.REQ_VALIDATE_MINUTE_LIMIT, String.valueOf(prop.getReqVerifyMinuteLimit()));

        config.put(Const.CAPTCHA_FONT_SIZE, String.valueOf(prop.getFontSize()));
        config.put(Const.CAPTCHA_FONT_STYLE, String.valueOf(prop.getFontStyle()));
        config.put(Const.CAPTCHA_WORD_COUNT, String.valueOf(prop.getClickWordCount()));

        if ((StringUtils.isNotBlank(prop.getJigsaw()) && prop.getJigsaw().startsWith("classpath:"))
                || (StringUtils.isNotBlank(prop.getPicClick()) && prop.getPicClick().startsWith("classpath:"))) {
            //自定义resources目录下初始化底图
            config.put(Const.CAPTCHA_INIT_ORIGINAL, "true");
            initializeBaseMap(prop.getJigsaw(), prop.getPicClick());
        }
        return CaptchaServiceFactory.getInstance(config);
    }

    private static void initializeBaseMap(String jigsaw, String picClick) {
        ImageUtils.cacheBootImage(getResourcesImagesFile(jigsaw + "/original/*.png"),
                getResourcesImagesFile(jigsaw + "/slidingBlock/*.png"),
                getResourcesImagesFile(picClick + "/*.png"));
    }

    public static Map<String, String> getResourcesImagesFile(String path) {
        Map<String, String> imgMap = new HashMap<>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources(path);
            for (Resource resource : resources) {
                byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
                String string = Base64Utils.encodeToString(bytes);
                String filename = resource.getFilename();
                imgMap.put(filename, string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgMap;
    }

    @Bean
    public CaptchaCacheService captchaCacheService(CaptchaConfig captchaConfig) {
        //缓存类型redis/local/....
        return CaptchaServiceFactory.getCache(captchaConfig.getCacheType().name());
    }
}
