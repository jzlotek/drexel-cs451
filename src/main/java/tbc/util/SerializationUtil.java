package tbc.util;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public abstract class SerializationUtil {

    public abstract String serialize(Object obj) throws JsonProcessingException;

    public abstract Object deserialize(String json, Class clazz) throws IOException;
}
