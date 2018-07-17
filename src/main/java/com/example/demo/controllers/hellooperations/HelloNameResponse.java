package com.example.demo.controllers.hellooperations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@EqualsAndHashCode
@ToString
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Response object for /hello/{name} controller")
class HelloNameResponse {

    @ApiModelProperty(notes = "Composed messsage. Contains hello + the provided name")
    @NotNull
    @NotBlank
    private final String message;
}
