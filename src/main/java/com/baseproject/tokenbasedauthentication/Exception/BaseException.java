package com.baseproject.tokenbasedauthentication.Exception;

import lombok.Getter;

@Getter
public class BaseException extends Exception{

    private int resultCode;
    private String msg;

    public BaseException(){
        this.resultCode = 400;
        this.msg = "Error";
    }

    public BaseException(int resultCode){
        this.resultCode = resultCode;
        this.msg = "Error";
    }

    public BaseException(String msg){
        this.resultCode = 400;
        this.msg = msg;
    }

    public BaseException(int resultCode, String msg){
        this.resultCode = resultCode;
        this.msg = msg;
    }

}
