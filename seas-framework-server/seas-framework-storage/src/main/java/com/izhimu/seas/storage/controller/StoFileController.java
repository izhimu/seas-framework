package com.izhimu.seas.storage.controller;

import cn.hutool.core.util.IdUtil;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.storage.entity.StoFile;
import com.izhimu.seas.storage.service.StoFileService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文件信息控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/sto/file/info")
public class StoFileController {

    @Resource
    private StoFileService service;

    /**
     * 获取文件信息
     *
     * @param id ID
     * @return 文件信息
     */
    @OperationLog("文件服务-获取文件信息")
    @GetMapping("/{id}")
    public StoFile getInfo(@PathVariable Long id) {
        return service.getFile(id);
    }

    /**
     * 根据绑定ID获取文件信息
     *
     * @param bindId 绑定ID
     * @return 文件信息
     */
    @OperationLog("文件服务-获取文件信息")
    @GetMapping("/bind/{bindId}")
    public List<StoFile> getInfos(@PathVariable Long bindId) {
        return service.getFiles(bindId);
    }

    /**
     * 根据绑定ID获取文件信息
     *
     * @param bindId 绑定ID
     * @return 文件信息
     */
    @OperationLog("文件服务-获取文件信息")
    @GetMapping("/bind/compression/{bindId}")
    public StoFile getInfosToCompression(@PathVariable Long bindId) {
        return service.getFilesToCompression(bindId);
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
