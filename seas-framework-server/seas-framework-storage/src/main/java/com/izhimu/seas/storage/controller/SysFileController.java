package com.izhimu.seas.storage.controller;

import cn.hutool.core.util.IdUtil;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.storage.entity.SysFile;
import com.izhimu.seas.storage.service.SysFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 文件服务控制层
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
@RestController
@RequestMapping("/sys/file")
public class SysFileController {

    @Resource
    private SysFileService service;

    /**
     * 获取文件信息
     *
     * @param id ID
     * @return 文件信息
     */
    @OperationLog("文件服务-获取文件信息")
    @GetMapping("/info/{id}")
    public SysFile getInfo(@PathVariable Long id) {
        return service.getFile(id);
    }

    /**
     * 根据绑定ID获取文件信息
     *
     * @param bindId 绑定ID
     * @return 文件信息
     */
    @OperationLog("文件服务-获取文件信息")
    @GetMapping("/info/bind/{bindId}")
    public List<SysFile> getInfos(@PathVariable Long bindId) {
        return service.getFiles(bindId);
    }

    /**
     * 根据绑定ID获取文件信息
     *
     * @param bindId 绑定ID
     * @return 文件信息
     */
    @OperationLog("文件服务-获取文件信息")
    @GetMapping("/info/bind/compression/{bindId}")
    public SysFile getInfosToCompression(@PathVariable Long bindId) {
        return service.getFilesToCompression(bindId);
    }

    /**
     * 下载文件
     *
     * @param id ID
     */
    @OperationLog(value = "文件服务-下载文件", enable = false)
    @GetMapping("/{id}")
    public void download(@PathVariable Long id, HttpServletResponse response) throws FileNotFoundException {
        SysFile sysFile = service.getFile(id);
        if (Objects.isNull(sysFile)) {
            throw new FileNotFoundException();
        }
        String fileName = sysFile.getFileName().concat(".").concat(sysFile.getFileSuffix());
        response.reset();
        response.setContentType(sysFile.getContentType());
        response.addHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        try (InputStream is = service.download(id); ServletOutputStream os = response.getOutputStream()) {
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) > 0) {
                os.write(b, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /**
     * 批量下载文件
     *
     * @param bindId 绑定ID
     */
    @OperationLog(value = "文件服务-批量下载文件", enable = false)
    @GetMapping("/bind/{bindId}")
    public void downloads(@PathVariable Long bindId, HttpServletResponse response) {
        response.reset();
        response.setContentType("application/x-zip-compressed");
        response.addHeader("Content-Disposition", "attachment; filename=" + IdUtil.getSnowflakeNextIdStr() + ".zip");
        try (InputStream is = service.downloads(bindId); ServletOutputStream os = response.getOutputStream()) {
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) > 0) {
                os.write(b, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            log.error("", e);
        }
    }


    /**
     * 文件上传
     *
     * @param file SysFile
     */
    @OperationLog(value = "文件服务-文件上传", enable = false)
    @PostMapping
    public List<SysFile> upload(SysFile file, MultipartHttpServletRequest request) {
        Collection<MultipartFile> values = request.getFileMap().values();
        List<SysFile> vos = new ArrayList<>();
        values.parallelStream().forEach(v -> {
            try {
                vos.add(service.putFile(wrapFile(file, v), v.getInputStream()));
            } catch (IOException e) {
                log.error("", e);
            }
        });
        return vos;
    }

    /**
     * 删除文件
     *
     * @param id Long
     */
    @OperationLog("文件服务-删除")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delFile(id);
    }

    /**
     * 删除文件
     *
     * @param bindId Long
     */
    @OperationLog("文件服务-删除")
    @DeleteMapping("/bind/{bindId}")
    public void deletes(@PathVariable Long bindId) {
        service.delFiles(bindId);
    }

    private SysFile wrapFile(SysFile entity, MultipartFile file) {
        SysFile sysFile = new SysFile();
        sysFile.setBindId(entity.getBindId());
        if (Objects.nonNull(file.getOriginalFilename())) {
            int index = file.getOriginalFilename().lastIndexOf(".");
            sysFile.setFileName(file.getOriginalFilename().substring(0, index));
            sysFile.setFileSuffix(file.getOriginalFilename().substring(index + 1));
        }
        sysFile.setFileSize(file.getSize());
        sysFile.setContentType(file.getContentType());
        return sysFile;
    }

    /**
     * 获取雪花ID
     *
     * @return 雪花ID
     */
    @OperationLog("文件服务-获取雪花ID")
    @GetMapping("/snowflake")
    public Long snowflake() {
        return IdUtil.getSnowflakeNextId();
    }
}
