package br.com.mateus.taskorganizer.infra.serialization;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.mateus.taskorganizer.domain.task.StatusTask;


@Configuration
public class ObjectMapperCreationConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            SimpleModule module = new SimpleModule();
            module.addSerializer(StatusTask.class, new StatusTaskSerializer());
            module.addDeserializer(StatusTask.class, new StatusTaskDeserializer());
            builder.modules(module);

            // Configura para aceitar valores vazios como nulo apenas para o enum StatusTask
            builder.featuresToEnable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            builder.modules(new JavaTimeModule());
            builder.deserializerByType(StatusTask.class, new StatusTaskDeserializer());
            builder.serializerByType(StatusTask.class, new StatusTaskSerializer());
        };
    }
}