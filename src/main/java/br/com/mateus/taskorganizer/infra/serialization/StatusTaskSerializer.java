package br.com.mateus.taskorganizer.infra.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import br.com.mateus.taskorganizer.domain.task.StatusTask;


public class StatusTaskSerializer extends StdSerializer<StatusTask> {

    public StatusTaskSerializer() {
        super(StatusTask.class);
    }

    @Override
    public void serialize(StatusTask value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.getStatus());
    }
}