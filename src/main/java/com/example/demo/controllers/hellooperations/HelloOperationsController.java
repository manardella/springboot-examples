package com.example.demo.controllers.hellooperations;

import com.example.demo.common.AppConstants;
import com.example.demo.controllers.GenericErrorResponse;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@RestController
public class HelloOperationsController implements HelloOperations {

    private final Set names = new HashSet();

    @Override
    @GetMapping(value = "/hello/{name}", produces = "application/json")
    public ResponseEntity<Object> helloName(
            @Validated
            @NotNull
            @NotBlank
            @Size(min = 1, max = 256)
            @PathVariable("name") final String name) {

        return new ResponseEntity<>(HelloNameResponse.builder().message("hello " + name).build(), HttpStatus.OK);
    }

    @Override
    @PostMapping(value = "/hello/names/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addName(
            @Validated @RequestBody final AddOrRemoveNameRequest addOrRemoveNameRequest) {

        if (names.contains(addOrRemoveNameRequest.getName())) {

            final GenericErrorResponse errorResponse = GenericErrorResponse.builder()
                    .errorMessage("The name" + addOrRemoveNameRequest.getName() + " already exists!")
                    .errorCode(AppConstants.ERROR_NAME_ALREADY_EXISTS).build();

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Add the name
        this.names.add(addOrRemoveNameRequest.getName());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/hello/names/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> deleteName(@Validated @RequestBody final AddOrRemoveNameRequest addOrRemoveNameRequest) {

        MDC.put("this-context-thread-request", addOrRemoveNameRequest.toString());


        if (!names.contains(addOrRemoveNameRequest.getName())) {
            // thrown error as example instead of handling the scenario in the same way as the previous example.
            throw new NameIsNotInTheListException("The name " + addOrRemoveNameRequest.getName() + " is not in the list!", new Exception("this is the inner exception"));
        }

        names.remove(addOrRemoveNameRequest.getName());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
