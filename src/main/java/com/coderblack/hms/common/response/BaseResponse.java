package com.coderblack.hms.common.response;

import lombok.*;

@Getter
@Setter
public class BaseResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public BaseResponse(String message){
        this.success = true;
        this.message = message;
    }

    public BaseResponse(String message, T data){
        this.success = true;
        this.message = message;
        this.data = data;
    }

}
