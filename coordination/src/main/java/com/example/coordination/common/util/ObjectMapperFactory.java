package com.example.coordination.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectMapperFactory {
    private static ObjectMapperFactory instance;
    private ObjectMapper mapper = null;

    public static ObjectMapper getInstance() {
        if (instance == null) {
            instance = new ObjectMapperFactory();
            instance.mapper = new ObjectMapper();
            instance.mapper.registerModule(new JavaTimeModule());
            return instance.mapper;
        }
        else {
            return instance.mapper;
        }
    }
}
