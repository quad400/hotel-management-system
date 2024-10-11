package com.coderblack.hms.common.response;

import lombok.*;

@Getter
@Setter
public class DefaultResponse {
    private boolean success;
    private String message;
    private String data;

    public DefaultResponse(String message){
        this.success = true;
        this.message = message;
    }

    public DefaultResponse(String message, String data){
        this.success = true;
        this.message = message;
        this.data = data;
    }

}
