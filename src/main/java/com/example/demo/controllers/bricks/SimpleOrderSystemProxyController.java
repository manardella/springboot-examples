/*
 * This controller uses the service https://github.com/manardella/simple-ordering-system
 * Start the service using the default configuration.
 * <p>
 * This controller is a proxy for that service. It is an super simple example or services integration.
 */
package com.example.demo.controllers.bricks;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping
public class SimpleOrderSystemProxyController {



    @PostMapping(value = "/orders", consumes = "application/json", produces = "application/json")
    public ResponseEntity createOrder(@Validated @RequestBody CreateOrUpdateOrderRequest createOrUpdateOrderRequest) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            Object serviceResponse = restTemplate.postForObject(
                    "http://localhost:8080/orders", createOrUpdateOrderRequest, Object.class);
            return new ResponseEntity<>(serviceResponse, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity getAllOrders() {
        RestTemplate restTemplate = new RestTemplate();

        try {
            Object serviceResponse = restTemplate.getForObject(
                    "http://localhost:8080/orders/", Object.class);
            return new ResponseEntity<>(serviceResponse, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/orders/{orderReference}", produces = "application/json")
    public ResponseEntity getOrder(@PathVariable(name = "orderReference") @Validated String orderReference) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            Object serviceResponse = restTemplate.getForObject(
                    "http://localhost:8080/orders/" + orderReference, Object.class);
            return new ResponseEntity<>(serviceResponse, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
