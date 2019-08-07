package tbc.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Base64;

public class SerializationUtilJSON {

    public static String serialize(Serializable obj) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        objectOutputStream.close();
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    public static Object deserialize(String string) throws Exception {
        byte []data = Base64.getDecoder().decode(string);
        ObjectInputStream objectInputStream =  new ObjectInputStream(new ByteArrayInputStream(data));
        Object object = objectInputStream.readObject();
        objectInputStream.close();
        return object;
    }

    public static String serializeJSON(Object obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.writeValueAsString(obj);
    }

    public static Object deserializeJSON(String json, Class clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(json, clazz);
    }
}
