package org.deslre.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * ClassName: FileReaderUtil
 * Description: 文件读取工具类
 * Author: Deslrey
 * Date: 2025-03-28 13:38
 * Version: 1.0
 */
public class FileReaderUtil {
    public static String readMarkdownFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    public static void main(String[] args) {
        try {
            String content = readMarkdownFile("E:\\blog\\react-blog\\public\\content\\3.md");
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}