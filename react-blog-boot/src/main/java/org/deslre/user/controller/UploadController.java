package org.deslre.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.deslre.annotation.AuthCheck;
import org.deslre.result.ResultCodeEnum;
import org.deslre.result.Results;
import org.deslre.utils.StaticUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {

    @PostMapping("/image")
    @AuthCheck(admin = true, checkLogin = true, log = "上传编辑文章图片", category = "upload")
    public Results<Map<String, Object>> upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        try {
//            String currentYear = DateUtil.getCurrentYear();
//            String currentMonth = DateUtil.getCurrentMonth();
//            String currentDay = DateUtil.getCurrentDay();

            // 构建目录路径
//            String relativePath = currentYear + "/" + currentMonth + "/" + currentDay;
//            String fullDirPath = StaticUtil.RESOURCE_IMAGE + relativePath;
            String fullDirPath = StaticUtil.RESOURCE_DRAFT_PATH;

            File dir = new File(fullDirPath);
            if (!dir.exists()) {
                boolean created = dir.mkdirs(); // 创建多级目录
                if (!created) {
                    log.error("目录创建失败: " + fullDirPath);
                    return Results.fail(ResultCodeEnum.CODE_500);
                }
            }
            String savePath = fullDirPath + File.separator + fileName;

            // 保存文件
            file.transferTo(new File(savePath));
//            String imageUrl = StaticUtil.RESOURCE_URL_IMAGE + relativePath + "/" + fileName;
            String imageUrl = StaticUtil.RESOURCE_DRAFT + fileName;
            Map<String, Object> result = new HashMap<>();
            result.put("url", imageUrl);
            return Results.ok(result);
        } catch (Exception e) {
            log.error("图片保存失败", e);
            return Results.fail("图片上传失败");
        }
    }
}