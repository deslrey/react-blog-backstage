package org.deslre.commons.utils;

import java.io.File;
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
public class MarkdownExtractor {

    private static final Pattern IMAGE_URL_PATTERN = Pattern.compile("!\\[[^]]*]\\(([^)]+)\\)");

    public static List<String> extractImageFileNames(String markdown) {
        List<String> fileNames = new ArrayList<>();
        Matcher matcher = IMAGE_URL_PATTERN.matcher(markdown);
        String url;
        String fileName;
        while (matcher.find()) {
            url = matcher.group(1); // 提取 URL
            fileName = StaticUtil.RESOURCE_DRAFT_PATH + File.separator + url.substring(url.lastIndexOf('/') + 1); // 提取文件名
            fileNames.add(fileName);
        }
        return fileNames;
    }

    public static void main(String[] args) {
        String markdown = """
                **1111**

                ![](http://localhost:8080/deslre/staticSource/draft/3a6a68cf-4af6-44ef-9e0f-c4d5dba4127c_ikun.jpg)
                ![](http://localhost:8080/deslre/staticSource/draft/4916fd44-7c65-4349-a685-749905d4784e_image.png)""";

        List<String> fileNames = extractImageFileNames(markdown);
        fileNames.forEach(System.out::println);
    }
}
