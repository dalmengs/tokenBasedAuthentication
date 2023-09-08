package com.baseproject.tokenbasedauthentication;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BaseResponse<T> {

    private final Boolean success;
    private final int resultCode;
    private final String msg;
    private final T data;

    public static BaseResponse OK(){
        return new BaseResponse(true, 200, "Succeed", null);
    }

    public static <T> BaseResponse OK(T data){
        return new BaseResponse(true, 200, "Succeed", data);
    }

    public static BaseResponse FAILED(){
        return new BaseResponse(false, 400, "Failed", null);
    }

    public static <T> BaseResponse FAILED(T data){
        return new BaseResponse(false, 400, "Failed", data);
    }

    public static <T> BaseResponse FAILED(int resultCode, T data){
        return new BaseResponse(false, resultCode, "Failed", data);
    }

    public static <T> BaseResponse FAILED(int resultCode, String msg, T data){
        return new BaseResponse(false, resultCode, msg, data);
    }

    public static <T> BaseResponse FAILED(String msg, T data){
        return new BaseResponse(false, 400, msg, data);
    }

}
