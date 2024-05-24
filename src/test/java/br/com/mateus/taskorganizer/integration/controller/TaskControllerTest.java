package br.com.mateus.taskorganizer.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.io.IOException;
import java.time.LocalDate;

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
	private TaskResponseDTO taskReponse = new TaskResponseDTO(task);
	
	
	@Test
	@DisplayName("Return status code 400 when invalid informations")
	@WithMockUser
	void registerTaskTest01() throws Exception {
		var response = mvc.perform(post(this.incrementContextUrl("")))
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
    @Test
    @DisplayName("Return status code 200 and expetected json")
    @WithMockUser
    void registerTaskTest02() throws Exception {
    	//retorna um valor válido do service
		when(taskService.registerTask(any())).thenReturn(task);
        
    	var response = mvc.perform(MockMvcRequestBuilders
				.post(this.incrementContextUrl(""))
				.contentType(MediaType.APPLICATION_JSON)
				.content(taskRequestJson.write(taskCreate)
						.getJson())
			)
			.andReturn().getResponse();
    	
		var expectedJson = taskResponseJson.write(taskReponse).getJson();
		
		assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }
    
	@Test
	@DisplayName("Return status code 400 when invalid informations")
	@WithMockUser
	void updateTaskTest01() throws Exception {
		var response = mvc.perform(put(this.incrementContextUrl("/1")))
			.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
    @Test
    @DisplayName("Return status code 200 and expetected json")
    @WithMockUser
	public void updateTaskTest02() throws IOException, Exception {
		when(taskService.updateTask(anyLong(),any())).thenReturn(task);
		
		var response = mvc.perform(MockMvcRequestBuilders
				.put(this.incrementContextUrl("/1"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(taskUpdateJson.write(taskUpdate)
						.getJson())
			)
			.andReturn().getResponse();
		
		var expectedJson = taskResponseJson.write(taskReponse).getJson();
		assertThat(response.getContentAsString()).isEqualTo(expectedJson);
	}
	
    @Test
	@DisplayName("Return status code 400 when invalid informations")
	@WithMockUser
	void removeTaskTest() throws Exception {
		var response = mvc.perform(delete(this.incrementContextUrl("/1")))
			.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
	}
    
    public String incrementContextUrl(String url) {
        return this.contextUrl+url;
    }
}