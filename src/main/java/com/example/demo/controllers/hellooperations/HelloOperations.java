package com.example.demo.controllers.hellooperations;

import com.example.demo.controllers.GenericErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api(value = "exampleApi")
public interface HelloOperations {

    @ApiOperation(value = "Get hello name greetings", response = HelloNameResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 500, message = "Server error. Something wrong happened!", response = GenericErrorResponse.class)})
    ResponseEntity<Object> helloName(@ApiParam(value = "Name to say hello", required = true) String name);

    @ApiOperation(value = "Get hello from a POST greetings", response = HelloNameResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 500, message = "Server error. Something wrong happened!", response = GenericErrorResponse.class)})
    ResponseEntity<Object> helloNamePost(@ApiParam(value = "Name to say hello in. json body request", required = true) HelloUsingPostRequest helloUsingPostRequest);

    @ApiOperation(value = "Get hello with callback using GET", response = HelloNameResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 500, message = "Server error. Something wrong happened!", response = GenericErrorResponse.class)})
    ResponseEntity<Object> helloNameCallback(@ApiParam(value = "Name to say hello", required = true) String name);

    @ApiOperation(value = "Get hello with callback using POST", response = HelloNameResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 500, message = "Server error. Something wrong happened!", response = GenericErrorResponse.class)})
    ResponseEntity<Object> helloNamePostCallback(@ApiParam(value = "Name to say hello in. json body request", required = true) HelloUsingPostRequest helloUsingPostRequest);

    @ApiOperation(value = "Adds a name to the list and responds")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Name added to the list"),
            @ApiResponse(code = 400, message = "Bad Request. Custom error code will be provided", response = GenericErrorResponse.class)})
    ResponseEntity<Object> addName(@ApiParam(value = "Add name request with the required name", required = true) AddOrRemoveNameRequest addOrRemoveNameRequest);

    @ApiOperation(value = "Remove name from the list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Name deleted to the list"),
            @ApiResponse(code = 400, message = "Bad Request. Custom error code will be provided", response = GenericErrorResponse.class),
            @ApiResponse(code = 500, message = "Server error. Something wrong happened!", response = GenericErrorResponse.class)})
    ResponseEntity<Object> deleteName(@ApiParam(value = "Remove name request with the required name", required = true) AddOrRemoveNameRequest addOrRemoveNameRequest);
}
