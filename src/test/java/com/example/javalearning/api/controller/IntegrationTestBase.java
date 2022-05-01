package com.example.javalearning.api.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpResponse;
import org.springframework.http.HttpMethod;
import org.springframework.util.SocketUtils;

import static org.mockserver.model.HttpRequest.request;

public abstract class IntegrationTestBase {

    protected static ClientAndServer mockServerJWT;

    @BeforeAll
    public static void startMockServer() {
        final int jwtPort = SocketUtils.findAvailableTcpPort(8000, 8599);
        mockServerJWT = ClientAndServer.startClientAndServer(jwtPort);
    }

    @BeforeEach
    public void setupJWTServer() {
        mockServerJWT.when(request().withMethod(HttpMethod.GET.name())
                .withPath("/jwks")).respond(HttpResponse.response().withBody("JWKS_RESPONSE"));
    }

    @AfterAll
    public static void stopMockServer() {
        mockServerJWT.stop();
    }

    @AfterEach
    public void resetServer() {
        mockServerJWT.reset();
    }

}
