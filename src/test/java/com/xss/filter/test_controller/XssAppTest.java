package com.xss.filter.test_controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.net.URI;

@AutoConfigureJsonTesters
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestXssApp.class)
public class XssAppTest {

    private final static String EXPECTED_USER_NAME = "Test";

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testShouldReturnResultWithFilterXssFromPathParameter() {

        // Given
        String evalUserName = EXPECTED_USER_NAME + "<script>alert(‘XSS’)</script>";
        // When

        String result = this.restTemplate.getForObject(String.format("http://localhost:%s/users/%s", port, EXPECTED_USER_NAME), String.class);

        // Then
        Assertions.assertEquals(String.format("Hello %s", EXPECTED_USER_NAME), result);
    }

    @Test
    public void testShouldReturnResultWithFilterXssFromRequestBody() throws JsonProcessingException {
        // Given
        User user = new User();
        user.setName(String.format("%s%s", EXPECTED_USER_NAME, "<script>alert(‘XSS’)</script>"));
        user.setEmail("<div><h1>Bold</h1></div>");
        RequestEntity<User> requestEntity = RequestEntity.method(HttpMethod.POST, URI.create(String.format("http://localhost:%s/users", port)))
            .contentType(MediaType.APPLICATION_JSON)
            .body(user);

        // When
        User response = this.restTemplate.exchange(requestEntity, User.class).getBody();

        // Then
        Assertions.assertEquals(EXPECTED_USER_NAME, response.getName());
        Assertions.assertEquals("", response.getEmail());
    }

}
