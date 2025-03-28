package org.deslre.commons.result;

import lombok.Data;

/**
 * ClassName: Results
 * Description: 全局统一返回结果类
 * Author: Deslrey
 * Date: 2025-03-28 13:16
 * Version: 1.0
 */

@Data
public class Results<T> {

    //返回码
    private Integer code;

    //返回消息
    private String message;

    //返回数据
    private T data;


    // 返回数据
    protected static <T> Results<T> build(T data) {
        Results<T> results = new Results<T>();
        if (data != null)
            results.setData(data);
        return results;
    }

    public static <T> Results<T> build(T body, Integer code, String message) {
        Results<T> results = build(body);
        results.setCode(code);
        results.setMessage(message);
        return results;
    }

    public static <T> Results<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Results<T> results = build(body);
        results.setCode(resultCodeEnum.getCode());
        results.setMessage(resultCodeEnum.getMessage());
        return results;
    }

    public static <T> Results<T> ok() {
        return Results.ok(null);
    }

    /**
     * 操作成功
     */
    public static <T> Results<T> ok(T data) {
        return build(data, ResultCodeEnum.SUCCESS);
    }

    public static <T> Results<T> fail() {
        return Results.fail(null);
    }

    /**
     * 操作失败
     */
    public static <T> Results<T> fail(T data) {
        return build(data, ResultCodeEnum.FAIL);
    }

    public Results<T> message(String msg) {
        this.setMessage(msg);
        return this;
    }

    public Results<T> code(Integer code) {
        this.setCode(code);
        return this;
    }
}