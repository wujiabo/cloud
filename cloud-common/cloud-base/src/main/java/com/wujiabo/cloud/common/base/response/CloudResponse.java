package com.wujiabo.cloud.common.base.response;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CloudResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;


    @Getter
    @Setter
    private T data;

    public static <T> CloudResponse<T> ok() {
        return restResult(null, ResponseConstants.SUCCESS, null);
    }

    public static <T> CloudResponse<T> ok(T data) {
        return restResult(data, ResponseConstants.SUCCESS, null);
    }

    public static <T> CloudResponse<T> ok(T data, String msg) {
        return restResult(data, ResponseConstants.SUCCESS, msg);
    }

    public static <T> CloudResponse<T> failed() {
        return restResult(null, ResponseConstants.FAIL, null);
    }

    public static <T> CloudResponse<T> failed(String msg) {
        return restResult(null, ResponseConstants.FAIL, msg);
    }

    public static <T> CloudResponse<T> failed(T data) {
        return restResult(data, ResponseConstants.FAIL, null);
    }

    public static <T> CloudResponse<T> failed(T data, String msg) {
        return restResult(data, ResponseConstants.FAIL, msg);
    }

    private static <T> CloudResponse<T> restResult(T data, int code, String msg) {
        CloudResponse<T> apiResult = new CloudResponse<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}
