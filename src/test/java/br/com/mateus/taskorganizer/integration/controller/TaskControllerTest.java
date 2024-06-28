package br.com.mateus.taskorganizer.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import br.com.mateus.taskorganizer.application.dto.task.input.TaskCreateDTO;
import br.com.mateus.taskorganizer.application.dto.task.input.TaskUpdateDTO;
import br.com.mateus.taskorganizer.application.usecases.task.SaveTask;
import br.com.mateus.taskorganizer.application.usecases.user.SaveUser;
import br.com.mateus.taskorganizer.domain.task.StatusTask;
import br.com.mateus.taskorganizer.domain.task.Task;
import br.com.mateus.taskorganizer.domain.user.User;
import br.com.mateus.taskorganizer.domain.user.UserRole;
import br.com.mateus.taskorganizer.infra.persistence.user.UserEntity;
import br.com.mateus.taskorganizer.infra.security.TokenService;
import br.com.mateus.taskorganizer.tests_utils.TaskUtils;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureWebMvc
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureTestDatabase
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class TaskControllerTest {

    @Autowired
	private MockMvc mvc;
	
    @Autowired 
	private JacksonTester<TaskCreateDTO> taskCreateDTOJson;
	@Autowired 
	private JacksonTester<TaskUpdateDTO> taskUpdateDTOJson;

    private final static LocalDate VALID_DATE = LocalDate.now().plusDays(20);
    private final static LocalDate INVALID_DATE = LocalDate.now().minusDays(20);

    private final static String PASSWORD = "password";
    private final static User user = new User("login", new BCryptPasswordEncoder().encode(PASSWORD), UserRole.ADMIN);
    
    private final String baseURL = "/tasks";
    private static String TOKEN = "Bearer ";
    
    private static Task task1 = TaskUtils.getRandomDefaultTaskWithoutId();
    private static Task task2 = TaskUtils.getRandomDefaultTaskWithoutId();
    
    @BeforeAll
    @Transactional
    static void setup(
        @Autowired SaveUser saveUser, 
        @Autowired SaveTask saveTask, 
        @Autowired TokenService tokenService
        ) {
        TOKEN = TOKEN + tokenService.generateToken(new UserEntity(user));

        saveUser.registerUser(user);
        saveTask.registerTask(task1);
        saveTask.registerTask(task2);
    }


    @Test
    void registerTaskWithValidDataTest() throws Exception {
        // arrange
        TaskCreateDTO requestBody = new TaskCreateDTO("Test-title", "Description-test", VALID_DATE);

        // act and assert
        this.postRequest(this.baseURL, taskCreateDTOJson.write(requestBody).getJson())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.title").value(requestBody.title()))
            .andExpect(jsonPath("$.description").value(requestBody.description()))
            .andExpect(jsonPath("$.dueDate").value(requestBody.dueDate().toString()))
            .andExpect(jsonPath("$.status").value(StatusTask.PENDING.toString()));
    }
    @Test
    void taskMustNotBeCreateWithInvalidTitleTest() throws Exception {
        // arrange
        TaskCreateDTO requestBodyWithTitleBlank = new TaskCreateDTO("", "Description-test", VALID_DATE);
        TaskCreateDTO requestBodyWithTitleNull = new TaskCreateDTO(null, "Description-test", VALID_DATE);

        // act and assert
        this.postRequest(this.baseURL, taskCreateDTOJson.write(requestBodyWithTitleBlank).getJson())
            .andExpect(status().isBadRequest());

        this.postRequest(this.baseURL, taskCreateDTOJson.write(requestBodyWithTitleNull).getJson())
            .andExpect(status().isBadRequest());
    }
    @Test
    void taskMustBeCreatedWithDueDateNullTest() throws Exception {
        // arrange
        TaskCreateDTO requestBodyWithTitleNull = new TaskCreateDTO("title", "Description-test", null);

        // act and assert
        this.postRequest(this.baseURL, taskCreateDTOJson.write(requestBodyWithTitleNull).getJson())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.dueDate").isEmpty());
    }
    @Test
    void taskMustNotBeCreatedWithPastDueDate() throws Exception {
        // arrange
        TaskCreateDTO requestBody = new TaskCreateDTO("Test-title", "Description-test", INVALID_DATE);

        // act and assert
        this.postRequest(this.baseURL, taskCreateDTOJson.write(requestBody).getJson())
            .andExpect(status().isBadRequest());
    }

    @Test
    void listTasksTest() throws Exception {
        this.getRequest(this.baseURL)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$.[0].id").exists())
            .andExpect(jsonPath("$.[0].title").exists())
            .andExpect(jsonPath("$.[0].description").exists())
            .andExpect(jsonPath("$.[0].dueDate").exists())
            .andExpect(jsonPath("$.[0].status").exists());
    }

    @Test
    void showTaskTest() throws Exception {
        this.getRequest(this.baseURL + "/1")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.title").exists())
            .andExpect(jsonPath("$.description").exists())
            .andExpect(jsonPath("$.dueDate").exists())
            .andExpect(jsonPath("$.status").exists());
    }

    @Test
    void updateTask() throws Exception {
        // arrange
        TaskUpdateDTO requestBody = new TaskUpdateDTO("update", "update", VALID_DATE.plusDays(1), StatusTask.CONCLUDED);

        // act and assert
        this.putRequest(this.baseURL + "/1", taskUpdateDTOJson.write(requestBody).getJson())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value(requestBody.title()))
            .andExpect(jsonPath("$.description").value(requestBody.description()))
            .andExpect(jsonPath("$.dueDate").value(requestBody.dueDate().toString()))
            .andExpect(jsonPath("$.status").value(requestBody.status().toString()));
    }
    @Test
    void updateTaskWithNullValuesMustBeReturnStatus200() throws Exception {
        // arrange
        TaskUpdateDTO requestBodyWithBlank = new TaskUpdateDTO("", "", null, null);
        TaskUpdateDTO requestBodyFullNull = new TaskUpdateDTO(null, null, null, null);

        // act and assert
        this.putRequest(this.baseURL + "/1", taskUpdateDTOJson.write(requestBodyWithBlank).getJson())
            .andExpect(status().isOk());

        this.putRequest(this.baseURL + "/1", taskUpdateDTOJson.write(requestBodyFullNull).getJson())
            .andExpect(status().isOk());
    }
    @Test
    void updateTaskWithNullDoesNotUpdateFields() throws Exception {
        // arrange
        TaskUpdateDTO requestBodyWithBlank = new TaskUpdateDTO("", "", null, null);
        TaskUpdateDTO requestBodyFullNull = new TaskUpdateDTO(null, null, null, null);

        // act and assert
        this.putRequest(this.baseURL + "/1", taskUpdateDTOJson.write(requestBodyWithBlank).getJson())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").isNotEmpty())
            .andExpect(jsonPath("$.description").isNotEmpty())
            .andExpect(jsonPath("$.dueDate").isNotEmpty())
            .andExpect(jsonPath("$.status").isNotEmpty());

        this.putRequest(this.baseURL + "/1", taskUpdateDTOJson.write(requestBodyFullNull).getJson())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").isNotEmpty())
            .andExpect(jsonPath("$.description").isNotEmpty())
            .andExpect(jsonPath("$.dueDate").isNotEmpty())
            .andExpect(jsonPath("$.status").isNotEmpty());
    }

    @Test
    @Rollback
    void deleteTaskTest() throws Exception {
        mvc.perform(
            delete(this.baseURL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TOKEN)
            )
            .andExpect(status().isNoContent());
    }


    private ResultActions postRequest(String url, String requestBody) throws Exception {
        return mvc.perform(
            post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .header("Authorization", TOKEN));
    }
    private ResultActions putRequest(String url, String requestBody) throws Exception {
        return mvc.perform(
            put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .header("Authorization", TOKEN));
    }
    private ResultActions getRequest(String url) throws Exception {
        return mvc.perform(
            get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TOKEN));
    }
}