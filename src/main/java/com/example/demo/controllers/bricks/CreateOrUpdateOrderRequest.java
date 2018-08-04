package com.example.demo.controllers.bricks;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateOrUpdateOrderRequest {

    @NotNull
    @Min(value = 1, message = "Value must be positive, at least one")
    private Integer numberOfBricks = 0;

}
