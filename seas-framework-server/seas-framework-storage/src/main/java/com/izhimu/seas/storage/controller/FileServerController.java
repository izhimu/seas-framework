package com.izhimu.seas.storage.controller;

import cn.hutool.core.util.IdUtil;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.core.utils.LogUtil;
import com.izhimu.seas.storage.entity.StoFile;
import com.izhimu.seas.storage.service.StoFileService;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
@RequestMapping("/sto/file")
public class FileServerController {

    @Resource
    private StoFileService service;

    /**
     * 下载文件
     *
     * @param id ID
     */
    @OperationLog(value = "文件服务-下载文件", enable = false)
    @GetMapping("/{id}")
    public void download(@PathVariable Long id, HttpServletResponse response) throws FileNotFoundException {
        StoFile stoFile = service.getFile(id);
        if (Objects.isNull(stoFile)) {
            throw new FileNotFoundException();
        }
        String fileName = stoFile.getFileName().concat(".").concat(stoFile.getFileSuffix());
        response.reset();
        response.setContentType(stoFile.getContentType());
        response.addHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        try (InputStream is = service.download(id); ServletOutputStream os = response.getOutputStream()) {
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) > 0) {
                os.write(b, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            log.error(LogUtil.format("FileStorage", "Error"), e);
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
            log.error(LogUtil.format("FileStorage", "Error"), e);
        }
    }


    /**
     * 文件上传
     *
     * @param file SysFile
     */
    @OperationLog(value = "文件服务-文件上传", enable = false)
    @PostMapping
    public List<StoFile> upload(StoFile file, MultipartHttpServletRequest request) {
        Collection<MultipartFile> values = request.getFileMap().values();
        List<StoFile> vos = new ArrayList<>();
        values.parallelStream().forEach(v -> {
            try {
                vos.add(service.putFile(wrapFile(file, v), v.getInputStream()));
            } catch (IOException e) {
                log.error(LogUtil.format("FileStorage", "Error"), e);
            }
        });
        return vos;
    }

    /**
     * 文件预览
     *
     * @param id ID
     */
    @OperationLog(value = "文件服务-文件预览", enable = false)
    @GetMapping("/preview/{id}")
    public void preview(@PathVariable Long id, HttpServletResponse response) throws FileNotFoundException {
        StoFile stoFile = service.getFile(id);
        if (Objects.isNull(stoFile)) {
            throw new FileNotFoundException();
        }
        response.reset();
        response.setContentType(stoFile.getContentType());
        try (InputStream is = service.download(id); ServletOutputStream os = response.getOutputStream()) {
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) > 0) {
                os.write(b, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            log.error(LogUtil.format("FileStorage", "Error"), e);
        }
    }

    private StoFile wrapFile(StoFile entity, MultipartFile file) {
        StoFile stoFile = new StoFile();
        stoFile.setBindId(entity.getBindId());
        if (Objects.nonNull(file.getOriginalFilename())) {
            int index = file.getOriginalFilename().lastIndexOf(".");
            stoFile.setFileName(file.getOriginalFilename().substring(0, index));
            stoFile.setFileSuffix(file.getOriginalFilename().substring(index + 1));
        }
        stoFile.setFileSize(file.getSize());
        stoFile.setContentType(file.getContentType());
        return stoFile;
    }
}
