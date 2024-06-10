package com.lycodeing.chat.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用返回结果
 * @author xiaotianyu
 */
@Data
public class Result implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 成功状态码
     */
    private  static final int SUCCESS = 200;
    /**
     * 异常状态码
     */
    private static final int ERROR = 500;
    /**
     * 参数格式错误码
     */
    private static final int BUSINESS_ERROR = 400;

    private int code;
    private String msg;
    private Object data;

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(SUCCESS);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(ERROR);
        result.setMsg(msg);
        return result;
    }

    public static Result error(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result error(int code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }


}
