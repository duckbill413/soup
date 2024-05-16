package io.ssafy.soupapi.global.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // Instant를 epoch value로 직렬화 하는 디폴트 세팅 off
        objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
//        objectMapper.configure(SerializationFeature.WRITE_DATES_WITH_CONTEXT_TIME_ZONE, false);
        objectMapper.registerModule(new JavaTimeModule());

        SimpleModule objectIdModule = new SimpleModule();
        objectIdModule.addSerializer(ObjectId.class, new JsonSerializer<>() {
            @Override
            public void serialize(ObjectId objectId, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(objectId.toString());
            }
        });
        objectIdModule.addDeserializer(ObjectId.class, new JsonDeserializer<>() {
            @Override
            public ObjectId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
                return new ObjectId(p.getValueAsString());
            }
        });
        objectMapper.registerModule(objectIdModule);

        return objectMapper;
    }

}
