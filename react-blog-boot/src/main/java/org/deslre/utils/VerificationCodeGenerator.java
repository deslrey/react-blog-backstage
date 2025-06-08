package org.deslre.utils;

import java.security.SecureRandom;

/**
 * ClassName: VerificationCodeGenerator
 * Description: 随机验证码
 * Author: Deslrey
 * Date: 2025-06-08 20:27
 * Version: 1.0
 */
public class VerificationCodeGenerator {
    private static final SecureRandom random = new SecureRandom();

    /**
     * 生成六位数字验证码
     *
     * @return 六位数字验证码
     */
    public static String generateCode() {
        // 生成一个6位数的随机整数，范围在100000到999999
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public static void main(String[] args) {
        // 测试生成验证码
        String verificationCode = generateCode();
        System.out.println("生成的验证码是: " + verificationCode);
    }
}