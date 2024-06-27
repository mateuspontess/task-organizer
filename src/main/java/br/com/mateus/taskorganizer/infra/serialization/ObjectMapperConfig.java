package br.com.mateus.taskorganizer.infra.serialization;

import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import br.com.mateus.taskorganizer.domain.task.StatusTask;


@Configuration
public class ObjectMapperConfig {

    ObjectMapper configObjectMapper(ObjectMapper objectMapper) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(StatusTask.class, new StatusTaskSerializer());
        module.addDeserializer(StatusTask.class, new StatusTaskDeserializer());
        objectMapper.registerModule(module);

        return objectMapper;
    }
}