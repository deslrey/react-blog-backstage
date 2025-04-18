package org.deslre.commons.utils;

import org.deslre.commons.entity.ArticleDetail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: FileReaderUtil
 * Description: 文件读取工具类
 * Author: Deslrey
 * Date: 2025-03-28 13:38
 * Version: 1.0
 */

public class FileReaderUtil {

    public static ArticleDetail parse(String filePath) {

        if (StringUtils.isEmpty(filePath)) {
            return null;
        }

        String markdownFile = readMarkdownFile(filePath);
        if (StringUtils.isEmpty(markdownFile)) {
            return null;
        }

        // 提取 metadata 部分
        Pattern pattern = Pattern.compile("---\\s*(.*?)\\s*---", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(markdownFile);
        String metadata = "";
        if (matcher.find()) {
            metadata = matcher.group(1);
        }

        // 提取正文内容
        String content = markdownFile.replaceFirst("---\\s*.*?\\s*---", "").trim();

        ArticleDetail articleDetail = new ArticleDetail();

        for (String line : metadata.split("\n")) {
            if (!line.contains(":")) continue;
            String[] parts = line.split(":", 2);
            String key = parts[0].trim();
            String value = parts[1].trim();

            switch (key) {
                case "title" -> articleDetail.setTitle(value);
                case "author" -> articleDetail.setAuthor(value);
                case "describe" -> articleDetail.setDescribe(value);
                case "update" -> {
                    // 统一将 / 和 . 替换为 -
                    String cleanDate = value.replaceAll("[./]", "-");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
                    LocalDate date = LocalDate.parse(cleanDate, formatter);
                    articleDetail.setUpdateDate(LocalDateTime.of(date, LocalTime.MIDNIGHT));
                }

                case "readTime" -> articleDetail.setReadTime(parseReadTime(value));
                case "type" -> articleDetail.setType(value);
                default -> {
                }
            }
        }

        String body = extractMarkdownBody(markdownFile);
        articleDetail.setContent(body);

        articleDetail.setWordCount(countWords(content));

        return articleDetail;
    }


    public static String readMarkdownFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return content.toString();
    }


    private static int parseReadTime(String value) {
        Matcher m = Pattern.compile("(\\d+)").matcher(value);
        return m.find() ? Integer.parseInt(m.group(1)) : 0;
    }

    private static int countWords(String content) {
        // 统计中文字符、英文单词、数字
        // 你也可以根据需求更细致处理
        return content.replaceAll("[\\s\\n]+", "") // 去掉换行和空格
                .length();
    }

    private static String extractMarkdownBody(String content) {
        // 去除 Front Matter（以 --- 开头和结束）
        if (content.startsWith("---")) {
            int endIndex = content.indexOf("---", 3); // 从第一个 --- 后再找一个 ---
            if (endIndex != -1) {
                return content.substring(endIndex + 3).trim(); // 跳过第二个 ---
            }
        }
        return content;
    }


    public static void main(String[] args) {

        String content = readMarkdownFile("E:\\blog\\react-blog\\public\\content\\3.md");
        System.out.println(content);

    }
}