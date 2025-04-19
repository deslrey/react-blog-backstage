package org.deslre.commons.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * ClassName: EncryptUtil
 * Description: TODO
 * Author: Deslrey
 * Date: 2025-04-19 17:29
 * Version: 1.0
 */
public class EncryptUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // 加密
    public static String encrypt(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    // 验证密码是否匹配
    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
