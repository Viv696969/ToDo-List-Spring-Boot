package com.vivekempire.TodoList.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.vivekempire.TodoList.entities.CustomUser;

import java.io.IOException;

public class CustomUserSerializer extends JsonSerializer<CustomUser>{

    @Override
    public void serialize(CustomUser customUser, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id",customUser.getId());
        jsonGenerator.writeEndObject();
    }
}
