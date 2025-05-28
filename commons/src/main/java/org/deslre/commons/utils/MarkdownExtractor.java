package org.deslre.commons.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: MarkdownExtractor
 * Description: 提取markdown内的信息工具类
 * Author: Deslrey
 * Date: 2025-05-25 22:42
 * Version: 1.0
 */
@Slf4j
public class MarkdownExtractor {

    private static final Pattern IMAGE_URL_PATTERN = Pattern.compile("!\\[[^]]*]\\(([^)]+)\\)");

    public static List<String> extractImageFileNames(String markdown) {
        List<String> fileNames = new ArrayList<>();
        Matcher matcher = IMAGE_URL_PATTERN.matcher(markdown);
        String url;
        String fileName;
        while (matcher.find()) {
            url = matcher.group(1); // 提取 URL
            System.out.println("url = " + url);
            if (StringUtils.isEmpty(url))
                continue;
            if (url.startsWith(StaticUtil.RESOURCE_DRAFT)) {
                fileName = StaticUtil.RESOURCE_DRAFT_PATH + File.separator + url.substring(url.lastIndexOf('/') + 1); // 提取文件名
                fileNames.add(fileName);
            }
        }
        return fileNames;
    }

    //  将图片移动到指定子目录
    public static void moveImagesToTargetDir(List<String> sourcePaths, String targetSubDir) {
        String targetBasePath = StaticUtil.RESOURCE_IMAGE;
        File targetDir = new File(targetBasePath, targetSubDir);

        if (!targetDir.exists() && !targetDir.mkdirs()) {
            System.err.println("无法创建目标目录：" + targetDir.getAbsolutePath());
            return;
        }

        for (String sourcePathStr : sourcePaths) {
            File sourceFile = new File(sourcePathStr);
            if (!sourceFile.exists()) {
                System.err.println("源文件不存在：" + sourcePathStr);
                continue;
            }

            File targetFile = new File(targetDir, sourceFile.getName());
            try {
                Files.move(sourceFile.toPath(), targetFile.toPath());
                System.out.println("已移动: " + sourceFile.getAbsolutePath() + " -> " + targetFile.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("移动失败: " + sourceFile.getAbsolutePath());
                log.error("图片移动失败 ======> {}", e.getMessage());
            }
        }
    }

    //  替换 markdown 中的图片路径为目标路径
    public static String replaceImagePaths(String markdown, String datePath) {
        Matcher matcher = IMAGE_URL_PATTERN.matcher(markdown);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String originalUrl = matcher.group(1); // 原始 URL
            System.out.println("originalUrl = " + originalUrl);
            if (StringUtils.isEmpty(originalUrl)) {
                continue;
            }
            if (originalUrl.startsWith(StaticUtil.RESOURCE_DRAFT)) {
                String fileName = originalUrl.substring(originalUrl.lastIndexOf('/') + 1);
                String newUrl = StaticUtil.RESOURCE_URL_IMAGE + datePath + "/" + fileName;
                matcher.appendReplacement(sb, "![](" + Matcher.quoteReplacement(newUrl) + ")");
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        String markdown = """
                **1111**

                ![](http://localhost:8080/deslre/staticSource/draft/3a6a68cf-4af6-44ef-9e0f-c4d5dba4127c_ikun.jpg)
                ![](http://localhost:8080/deslre/staticSource/draft/4916fd44-7c65-4349-a685-749905d4784e_image.png)""";

        List<String> fileNames = extractImageFileNames(markdown);
        fileNames.forEach(System.out::println);
        moveImagesToTargetDir(fileNames, "2025" + File.separator + "05");
        String newMarkdown = replaceImagePaths(markdown, "2025/05");
        System.out.println("\n替换后的 Markdown:\n" + newMarkdown);

    }
}
