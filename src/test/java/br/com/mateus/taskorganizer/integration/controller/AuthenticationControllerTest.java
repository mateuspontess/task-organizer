package br.com.mateus.taskorganizer.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import br.com.mateus.taskorganizer.application.dto.user.input.AuthenticationDTO;
import br.com.mateus.taskorganizer.application.usecases.user.SaveUser;
import br.com.mateus.taskorganizer.domain.user.User;
import br.com.mateus.taskorganizer.domain.user.UserRole;
import br.com.mateus.taskorganizer.infra.persistence.user.UserEntity;
import br.com.mateus.taskorganizer.infra.security.TokenService;
import br.com.mateus.taskorganizer.integration.config.ControllerE2ETest;

@ControllerE2ETest
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<AuthenticationDTO> authenticationDTOJson;

    private final String baseURL = "/auth";
    private static String TOKEN = "Bearer ";
    private final static String PASSWORD = "password";
    private final static User user = new User("login", new BCryptPasswordEncoder().encode(PASSWORD), UserRole.ADMIN);

    @BeforeAll
    @Transactional
    static void setup(@Autowired SaveUser saveUser, @Autowired TokenService tokenService) {
        TOKEN = TOKEN + tokenService.generateToken(new UserEntity(user));
        saveUser.registerUser(user);
    }

    @AfterAll
    static void clean(@Autowired MongoTemplate template) {
        var collections = template.getCollectionNames();
        collections.forEach(collection -> template.dropCollection(collection));
    }


    @Test
    void loginTest() throws Exception {
        // arrange
        String requestBody = authenticationDTOJson.write(new AuthenticationDTO("login", "password")).getJson();

        // act and assert
        this.postRequest("/login" , requestBody)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").exists());
    }
    @Test
    void loginTestMustReturnStatus400WhenPassingInvalidDataTest() throws Exception {
        // arrange
        String requestBodyBlank = authenticationDTOJson.write(new AuthenticationDTO("", "")).getJson();
        String requestBodyNull = authenticationDTOJson.write(new AuthenticationDTO(null, null)).getJson();

        // act and assert
        this.postRequest("/login" , requestBodyBlank)
            .andExpect(status().isBadRequest());
        this.postRequest("/login" , requestBodyNull)
            .andExpect(status().isBadRequest());
    }

    @Test
    void registerTest() throws Exception {
        // arrange
        String requestBody = authenticationDTOJson.write(new AuthenticationDTO("otherLogin", "password")).getJson();

        // act and assert
        this.postRequest("/register" , requestBody)
            .andExpect(status().isOk());
    }
    @Test
    void registerTestMustReturnStatus400WhenPassingInvalidDataTest() throws Exception {
        // arrange
        String requestBodyBlank = authenticationDTOJson.write(new AuthenticationDTO("", "")).getJson();
        String requestBodyNull = authenticationDTOJson.write(new AuthenticationDTO(null, null)).getJson();

        // act and assert
        this.postRequest("/register" , requestBodyBlank)
            .andExpect(status().isBadRequest());
        this.postRequest("/register" , requestBodyNull)
            .andExpect(status().isBadRequest());
    }

    @Test
    void adminRegisterTest() throws Exception {
        // arrange
        String requestBody = authenticationDTOJson.write(new AuthenticationDTO("adminLogin", "password")).getJson();

        // act and assert
        this.postRequestWithAuthorizationHeader("/register" , requestBody)
            .andExpect(status().isOk());
    }
    @Test
    void adminRegisterTestMustReturnStatus400WhenPassingInvalidDataTest() throws Exception {
        // arrange
        String requestBodyBlank = authenticationDTOJson.write(new AuthenticationDTO("", "")).getJson();
        String requestBodyNull = authenticationDTOJson.write(new AuthenticationDTO(null, null)).getJson();

        // act and assert
        this.postRequestWithAuthorizationHeader("/admin/register" , requestBodyBlank)
            .andExpect(status().isBadRequest());
        this.postRequestWithAuthorizationHeader("/admin/register" , requestBodyNull)
            .andExpect(status().isBadRequest());
    }


    private ResultActions postRequest(String url, String requestBody) throws Exception {
        url = url == null ? "" : url;
        url = this.baseURL + url;
        return mvc.perform(
            post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
    }
    private ResultActions postRequestWithAuthorizationHeader(String url, String requestBody) throws Exception {
        url = url == null ? "" : url;
        url = this.baseURL + url;
        return mvc.perform(
            post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .header("Authorization", TOKEN));
    }
}
