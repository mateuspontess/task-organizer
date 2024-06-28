package br.com.mateus.taskorganizer.infra.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import br.com.mateus.taskorganizer.domain.task.StatusTask;


public class StatusTaskDeserializer extends StdDeserializer<StatusTask> {

    public StatusTaskDeserializer() {
        super(StatusTask.class);
    }

    @Override
    public StatusTask deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        try {
            String value = p.getText();
            return StatusTask.fromString(value);

        } catch (IOException | IllegalArgumentException ex) {
            return null;
        }
    }
}