package org.deslre.commons.utils;

/**
 * ClassName: NumberUtils
 * Description: TODO
 * Author: Deslrey
 * Date: 2025-04-11 16:19
 * Version: 1.0
 */
public class NumberUtils {

    public static boolean isZero(Integer number) {
        return !isNotZero(number);
    }


    public static boolean isNotZero(Integer number) {
        return number != null && number != 0;
    }

    public static boolean isNull(Integer number) {
        return number == null;
    }

    public static boolean isLessThanZero(Integer number) {
        return isNull(number) || number <= 0;
    }
}
