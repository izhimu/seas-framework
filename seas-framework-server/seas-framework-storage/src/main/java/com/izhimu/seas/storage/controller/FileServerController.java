package com.izhimu.seas.storage.controller;

import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.core.utils.LogUtil;
import com.izhimu.seas.storage.entity.FileInfo;
import com.izhimu.seas.storage.entity.StoFile;
import com.izhimu.seas.storage.service.FileService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

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
    private FileService service;

    /**
     * 下载文件
     *
     * @param id ID
     */
    @OperationLog(value = "文件服务-下载文件", enable = false)
    @GetMapping("/{id}")
    public void download(@PathVariable Long id, HttpServletResponse response) throws FileNotFoundException {
        service.downloadAsHttp(id, response);
    }

    /**
     * 批量下载文件
     *
     * @param bindId 绑定ID
     */
    @OperationLog(value = "文件服务-批量下载文件", enable = false)
    @GetMapping("/bind/{bindId}")
    public void downloads(@PathVariable Long bindId, HttpServletResponse response) throws FileNotFoundException {
        service.downloadZipAsHttp(bindId, response);
    }


    /**
     * 文件上传
     *
     * @param file SysFile
     */
    @OperationLog(value = "文件服务-文件上传", enable = false)
    @PostMapping
    public List<StoFile> upload(StoFile file, MultipartHttpServletRequest request) {
        Collection<List<MultipartFile>> values = request.getMultiFileMap().values();
        List<StoFile> vos = new ArrayList<>();
        values.parallelStream()
                .flatMap(Collection::stream)
                .forEach(v -> {
            try {
                vos.add(service.upload(wrapFile(file, v), v.getInputStream()));
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
    @GetMapping("/preview/{type}/{id}")
    public void preview(@PathVariable Long id, @PathVariable String type, Integer page, HttpServletResponse response) throws FileNotFoundException {
        Map<String, Object> param = new HashMap<>();
        if (Objects.nonNull(page)) {
            param.put("page", page);
        }
        service.previewAsHttp(id, type, response, param);
    }

    /**
     * 文件信息
     *
     * @param id ID
     */
    @OperationLog(value = "文件服务-文件信息", enable = false)
    @GetMapping("/preview/info/{id}")
    public FileInfo info(@PathVariable Long id) throws FileNotFoundException {
        return service.previewInfo(id);
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
