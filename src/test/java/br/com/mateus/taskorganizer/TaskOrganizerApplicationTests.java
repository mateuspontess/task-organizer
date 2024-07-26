package br.com.mateus.taskorganizer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import br.com.mateus.taskorganizer.integration.config.MongoDBTestContainer;

@SpringBootTest
@Import(MongoDBTestContainer.class)
class TaskOrganizerApplicationTests {

	@Test
	void contextLoads() {
	}
}