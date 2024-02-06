package br.com.mateus.taskorganizer.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
import br.com.mateus.taskorganizer.model.task.TaskRepository;
import br.com.mateus.taskorganizer.model.task.TaskRequestDTO;
import br.com.mateus.taskorganizer.model.task.TaskResponseDTO;
import br.com.mateus.taskorganizer.model.task.TaskService;
import br.com.mateus.taskorganizer.util.TaskBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class TaskControllerTest {

	@Autowired
	private MockMvc mvc;
	@Autowired 
	private JacksonTester<TaskRequestDTO> taskRequestJson;
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
	private TaskRequestDTO taskRequest = new TaskRequestDTO(1l, "Test-title", "Description-test", date, StatusTask.CONCLUDED);
	private TaskResponseDTO taskReponse = new TaskResponseDTO(task);
	
	
	@Test
	@DisplayName("Return status code 400 when invalid informations")
	@WithMockUser
	void registerTaskTest01() throws Exception {
		var response = mvc.perform(post(this.contextUrl("/register-task")))
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
				.post(this.contextUrl("/register-task"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(taskRequestJson.write(taskRequest)
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
		var response = mvc.perform(put(this.contextUrl("/update")))
			.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
    @Test
    @DisplayName("Return status code 200 and expetected json")
    @WithMockUser
	public void updateTaskTest02() throws IOException, Exception {
		when(taskService.updateTask(any())).thenReturn(task);
		
		var response = mvc.perform(MockMvcRequestBuilders
				.put(this.contextUrl("/update"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(taskRequestJson.write(taskRequest)
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
		var response = mvc.perform(delete(this.contextUrl("/remove/"+1l)))
			.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
	}
    
    public String contextUrl(String url) {
        return "/organizer/tasks"+url;
    }
}
