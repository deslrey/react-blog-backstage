package org.deslre.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FolderInitializer implements CommandLineRunner {

    private static final String folderPath = "E:\\images"; // 你想保存图片的路径

    @Override
    public void run(String... args) throws Exception {
        File dir = new File(folderPath);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) {
                System.out.println("图片目录创建成功：" + folderPath);
            } else {
                System.out.println("图片目录创建失败：" + folderPath);
            }
        } else {
            System.out.println("图片目录已存在：" + folderPath);
        }
    }
}
