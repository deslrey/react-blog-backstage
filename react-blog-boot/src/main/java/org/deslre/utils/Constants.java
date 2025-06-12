package org.deslre.utils;

/**
 * ClassName: Constants
 * Description: redis存储
 * Author: Deslrey
 * Date: 2025-06-08 21:56
 * Version: 1.0
 */
public class Constants {
    public static final String CHECK_CODE_KEY = "check_code_key";
    public static final String CHECK_CODE_KEY_EMAIL = "check_code_key_email";
    public static final Integer LENGTH_5 = 5;
    public static final Integer ZERO = 0;
    public static final Integer LENGTH_15 = 15;
    public static final Integer LENGTH_10 = 10;
    public static final Long MB = 1024 * 1024L;
    public static final String SESSION_KEY = "session_key";
    public static final Integer REDIS_KEY_EXPIRES_DAY = 60 * 60 * 24;
    public static final Integer LENGTH_30 = 30;
    public static final Integer REDIS_KEY_EXPIRES_THREE_HOURS = 60 * 60 * 3;
    public static final Integer LENGTH_150 = 150;
    public static final String ZERO_STR = "0";
    public static final Integer LENGTH_50 = 50;
    public static final Integer REDIS_KEY_EXPIRES_ONE_MIN = 60;
    public static final Integer REDIS_KEY_EXPIRES_FIVE_MIN = REDIS_KEY_EXPIRES_ONE_MIN * 5;
    public static final String EMAIL_CODE = "email:verification:code:";

    public static final String LOGIN_EMAIL = "login:email:";

}
