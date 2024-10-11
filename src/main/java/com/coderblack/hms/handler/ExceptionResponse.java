package com.coderblack.hms.handler;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.*;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {
    private boolean success;
    private Integer businessErrorCode;
    private String businessErrorDescription;
    private String error;
    private Map<String, String> errors;
    private Set<String> validationErrors;

    public void setStatus(boolean success) {
        this.success = true;
    }
}
