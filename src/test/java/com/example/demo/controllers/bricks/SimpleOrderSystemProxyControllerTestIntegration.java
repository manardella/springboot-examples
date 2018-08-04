package com.example.demo.controllers.bricks;

import com.example.demo.DemoApplication;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = DemoApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:test.properties")
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class SimpleOrderSystemProxyControllerTestIntegration {

    // Read https://dzone.com/articles/wiremock-mock-your-rest-apis
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);

    Gson gson = new Gson();

    // post example
    @Test
    public void shouldCreateNewOrderAndReturnTheOrderReference() {

//        - RestAssured send a request to this app controlller
//        - This controller reads the request an calls to the simple ordering service
//        - This service is mocked by wireMockRule
//        - Wiremock intercepts the call and returns a response, fixed one
//        - The response is passed to rest assured as it is the client of this service (this app)
//        - the response is matched
//        - this app, at the end, routes the controller
//        localhost:8080 is the REMOTE service, the service this controller calls
//        localhost:9080 is this app, the controller we are testing


        // The request to this project controller
        final CreateOrUpdateOrderRequest request = CreateOrUpdateOrderRequest.builder().numberOfBricks(1).build();
        final String jsonRequest = gson.toJson(request);

        // The expected order system message
        final String simpleOrderingSystemResponse = "{ \"orderReference\": \"15f1eaa1-0637-403f-b3a2-1c93c8bb4a6f\" }\n";

        // Mock the order system service.
        stubFor(WireMock.post(urlEqualTo("/orders"))
                .withRequestBody(equalToJson(jsonRequest))
                .willReturn(aResponse()
                        .withHeader("Content-Type", ContentType.JSON.toString())
                        .withBody(simpleOrderingSystemResponse)));


        // Now we trigger the request to the current controller
        given()
                .port(9080)
                .contentType(ContentType.JSON)
                .body(jsonRequest)   // use jsonObj toString method
            .when()
                // this is going to th
                .post("http://localhost:9080/orders")
            .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .body("orderReference", org.hamcrest.Matchers.equalTo("15f1eaa1-0637-403f-b3a2-1c93c8bb4a6f"));


        // Formally, this is not needed because we are passing the request to
        // In case of different request, yes, we need to verify request that has been passes to the remote service is correct
        // We check the remote service, stubed by wiremock, has received the
        // the expected request to the remote server.
        verify(postRequestedFor(urlEqualTo("http://localhost:8090/orders"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson(jsonRequest)));
    }

    // get example
    @Test
    public void shouldRetrieveOrderDetails() {

        // The request to this project controller
        final CreateOrUpdateOrderRequest request = CreateOrUpdateOrderRequest.builder().numberOfBricks(1).build();
        final String jsonRequest = gson.toJson(request);

        // The expected order system message
        final String orderDetailsResponse = "{\"order\":{\"orderReference\":\"15f1eaa1-0637-403f-b3a2-1c93c8bb4a6f\",\"numberOfBricks\":1}}";

        // Mock the order system service.
        stubFor(WireMock.get(urlEqualTo("/orders/15f1eaa1-0637-403f-b3a2-1c93c8bb4a6f"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", ContentType.JSON.toString())
                        .withBody(orderDetailsResponse)));


        // Now we trigger the request to the current controller
        given()
                .port(9080)
                .when()
                // this is going to th
                .get("http://localhost:9080/orders/15f1eaa1-0637-403f-b3a2-1c93c8bb4a6f")
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .body("order.orderReference", org.hamcrest.Matchers.equalTo("15f1eaa1-0637-403f-b3a2-1c93c8bb4a6f"))
                .body("order.numberOfBricks", org.hamcrest.Matchers.equalTo(1));


        // Formally, this is not needed because we are passing the request to
        // In case of different request, yes, we need to verify request that has been passes to the remote service is correct
        // We check the remote service, stubed by wiremock, has received the
        // the expected request to the remote server.
        verify(postRequestedFor(urlEqualTo("http://localhost:8090/orders/15f1eaa1-0637-403f-b3a2-1c93c8bb4a6f"))
                .withHeader("Content-Type", equalTo("application/json")));
    }

}