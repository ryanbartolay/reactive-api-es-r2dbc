package com.peplink.ecommerce.workflow.reactive.api.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.peplink.ecommerce.workflow.reactive.api.util.DateUtility;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CommonErrorResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Message> errors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String rootCause;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtility.DISPLAY_DATETIME_FORMAT_ISO8601)
    private Date timestamp;

    public CommonErrorResponse() {
        this.timestamp = new Date();
    }

    public CommonErrorResponse(Integer errorCode) {
        this.timestamp = new Date();
    }

    public CommonErrorResponse(List<Message> errors, Integer errorCode) {
        this.errors = errors;
        this.timestamp = new Date();
    }

    public CommonErrorResponse(List<Message> errors, Integer errorCode, String rootCause) {
        this.errors = errors;
        this.rootCause = rootCause;
        this.timestamp = new Date();
    }

}
