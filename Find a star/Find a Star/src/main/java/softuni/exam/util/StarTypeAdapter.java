package softuni.exam.util;

import com.google.gson.*;
import softuni.exam.models.enums.StarType;

import java.lang.reflect.Type;

public class StarTypeAdapter implements JsonSerializer<String>, JsonDeserializer<StarType> {
   
   
    @Override
    public StarType deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return StarType.valueOf(jsonElement.getAsString());
    }
    
    @Override
    public JsonElement serialize(String s, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(s);
    }
}
