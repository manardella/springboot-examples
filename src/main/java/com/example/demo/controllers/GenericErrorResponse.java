package com.example.demo.controllers;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
@ApiModel("Generic error response class")
public class GenericErrorResponse {

    @ApiModelProperty("Human readeable error message")
    private final String errorMessage;

    @ApiModelProperty("Internal system error code")
    private final int errorCode;

    @ApiModelProperty("Internal Spring MDC instrumentalization info. Useful for tracing the logs and for the support team")
    private final String mdc;

}
