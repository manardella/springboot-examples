package com.example.demo.controllers.hellooperations;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
class HelloUsingPostRequest {

    @NotNull
    @NotBlank
    @Size(min = 1, max = 256)
    @ApiModelProperty("Name to add or remove from the list")
    private String name;
}
