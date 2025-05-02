package org.deslre.user.controller;

import org.deslre.commons.result.Results;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ClassName: UploadController
 * Description: 文件控制器
 * Author: Deslrey
 * Date: 2025-05-02 15:26
 * Version: 1.0
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @PostMapping("/image")
    public Results<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) throws IOException {
        // 你可以保存到本地、OSS 或数据库
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String savePath = "E:\\images\\" + filename;
        file.transferTo(new File(savePath));

        // 返回给前端的 URL
        String url = "http://localhost:8080/deslre/images/" + filename;

        Map<String, Object> result = new HashMap<>();
        result.put("url", url);
        return Results.ok(result);
    }
}