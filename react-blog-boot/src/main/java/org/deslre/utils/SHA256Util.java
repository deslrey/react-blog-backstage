package org.deslre.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ClassName: SHA256Util
 * Description: 加密工具类
 * Author: Deslrey
 * Date: 2025-06-08 20:15
 * Version: 1.0
 */
public class SHA256Util {

    // 将密码进行SHA-256加密
    public static String encrypt(String password) {
        try {
            // 获取SHA-256的MessageDigest实例
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            // 传入密码并进行哈希
            byte[] hashedBytes = messageDigest.digest(password.getBytes());

            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256算法不可用", e);
        }
    }

    public static void main(String[] args) {
        // 测试加密
        String password = "mySecurePassword";
        String encryptedPassword = encrypt(password);
        System.out.println("原始密码: " + password);
        System.out.println("加密后的密码: " + encryptedPassword);
    }
}
