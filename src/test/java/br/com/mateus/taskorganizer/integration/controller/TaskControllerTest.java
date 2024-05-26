package br.com.mateus.taskorganizer.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.mateus.taskorganizer.model.task.StatusTask;
import br.com.mateus.taskorganizer.model.task.Task;
import br.com.mateus.taskorganizer.model.task.TaskCreateDTO;
import br.com.mateus.taskorganizer.model.task.TaskResponseDTO;
import br.com.mateus.taskorganizer.model.task.TaskService;
import br.com.mateus.taskorganizer.model.task.TaskUpdateDTO;
import br.com.mateus.taskorganizer.util.TaskBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class TaskControllerTest {

	private String contextUrl = "/tasks";
	@Autowired
	private MockMvc mvc;
	@Autowired 
	private JacksonTester<TaskCreateDTO> taskRequestJson;
	@Autowired 
	private JacksonTester<TaskUpdateDTO> taskUpdateJson;
	@Autowired 
	private JacksonTester<TaskResponseDTO> taskResponseJson;
	
    @MockBean
    private TaskService taskService;

    
    private final LocalDate date = LocalDate.now().plusDays(1);
	private Task task = new TaskBuilder()
							.id(1L)
							.title("Test-title")
							.description("Description-test")
							.dueDate(date)
							.status(StatusTask.CONCLUDED)
							.build();
	private TaskCreateDTO taskCreate = new TaskCreateDTO("Test-title", "Description-test", date);
	private TaskUpdateDTO taskUpdate = new TaskUpdateDTO("Test-title", "Description-test", date, StatusTask.CONCLUDED.toString());
	private TaskResponseDTO taskResponse = new TaskResponseDTO(task);
	
	
	@Test
	@DisplayName("Return status code 400 when invalid informations")
	@WithMockUser
	void registerTaskTest01() throws Exception {
	    // arrange
	    String invalidTaskJson = "{}";

	    // act
	    var response = mvc.perform(post(this.incrementContextUrl(""))
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(invalidTaskJson))
	                .andReturn().getResponse();
	    
	    // assert
	    Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus(), "Expected HTTP status 400 (BAD_REQUEST) when invalid information is provided");
	}
    
    @Test
    @DisplayName("Return status code 201 and expected json")
    @WithMockUser
    void registerTaskTest02() throws Exception {
        // arrange
        when(taskService.registerTask(any())).thenReturn(task);

        // act
        var result = mvc.perform(MockMvcRequestBuilders
                .post(this.incrementContextUrl(""))
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskRequestJson.write(taskCreate).getJson()))
                .andReturn();
        
        // assert
        Assertions.assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus(), "Expected HTTP status 201 (Created)");
        Assertions.assertEquals(taskResponseJson.write(taskResponse).getJson(), result.getResponse().getContentAsString(), "Expected JSON response");
    }
    
    @Test
    @DisplayName("Return status code 400 when invalid information")
    @WithMockUser
    void updateTaskTest01() throws Exception {
        // act
        MvcResult result = mvc.perform(put(this.incrementContextUrl("/1")))
                .andReturn();

        // assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus(), "Expected HTTP status 400 (BAD REQUEST)");
    }

    @Test
    @DisplayName("Return status code 200 and expected JSON")
    @WithMockUser
    public void updateTaskTest02() throws Exception {
        // arrange
        when(taskService.updateTask(anyLong(), any())).thenReturn(task);

        // act
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .put(this.incrementContextUrl("/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskUpdateJson.write(taskUpdate).getJson()))
                .andReturn();

        // assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus(), "Expected HTTP status 200 (OK)");
        assertEquals(taskResponseJson.write(taskResponse).getJson(), result.getResponse().getContentAsString(), "Expected JSON response");
    }

	
    @Test
    @DisplayName("Return status code 400 when invalid informations")
    @WithMockUser
    void removeTaskTest() throws Exception {
        // act
        MvcResult result = mvc.perform(delete(this.incrementContextUrl("/1")))
                .andReturn();

        // assert
        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus(), "Expected HTTP status 400 (Bad Request)");
    }
    
    public String incrementContextUrl(String url) {
        return this.contextUrl+url;
    }
}