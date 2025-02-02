package com.appsmith.server.controllers;

import com.appsmith.server.configurations.CommonConfig;
import com.appsmith.server.configurations.SecurityTestConfig;
import com.appsmith.server.services.WorkspaceService;
import com.appsmith.server.services.UserWorkspaceService;
import com.appsmith.server.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@RunWith(SpringRunner.class)
@WebFluxTest(WorkspaceController.class)
@Import(SecurityTestConfig.class)
public class WorkspaceControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private WorkspaceService workspaceService;

    @MockBean
    private UserWorkspaceService userWorkspaceService;

    @MockBean
    private UserService userService;

    @MockBean
    private CommonConfig commonConfig;

    @Test
    @WithMockUser
    public void getWorkspaceNoName() {
        webTestClient.post().uri("/api/v1/workspaces").
                contentType(MediaType.APPLICATION_JSON).
                body(BodyInserters.fromValue("{}")).
                exchange().
                expectStatus().isEqualTo(400).
                expectBody().json("{\n" +
                "    \"responseMeta\": {\n" +
                "        \"status\": 400,\n" +
                "        \"success\": false,\n" +
                "        \"error\": {\n" +
                "            \"code\": 4028,\n" +
                "            \"message\": \"Validation Failure(s): {name=Name is mandatory}\"\n" +
                "        }\n" +
                "    }\n" +
                "}");
    }
}
