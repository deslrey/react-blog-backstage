package org.deslre.commons.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

/**
 * ClassName: FileWriteUtil
 * Description: 文件写工具类 - 自动根据日期生成路径并写入 .md 文件
 * Author: Deslrey
 * Date: 2025-05-07 16:16
 * Version: 1.2
 */
public class FileWriteUtil {

    private final static String BASE_PATH = "E:\\md\\";

    /**
     * 写入 Markdown 文件（文件路径自动生成）
     * 路径结构：E:\md\<年份>\<月份>\<fileName>.md
     *
     * @param content  要写入的 Markdown 内容
     * @param fileName 文件名（不需要加后缀）
     * @param append   是否追加写入（true 表示追加，false 表示覆盖）
     * @return 成功则返回文件完整路径，失败返回 null
     */
    public static String writeMarkdown(String content, String fileName, boolean append) {
        // 获取当前日期
        LocalDate now = LocalDate.now();
        String year = String.valueOf(now.getYear());
        String month = String.format("%02d", now.getMonthValue());

        // 生成完整文件夹路径
        String dirPath = BASE_PATH + year + File.separator + month + File.separator;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 构建完整文件路径
        String filePath = dirPath + fileName + ".md";
        File file = new File(filePath);

        // 写入文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
            writer.write(content);
            return filePath; // 返回路径
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
