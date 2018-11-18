package com.epam.multithreading.deserializer;

import com.epam.multithreading.entity.Position;
import com.epam.multithreading.entity.Uber;
import com.google.gson.*;

import java.lang.reflect.Type;

public class GsonUberDeserializer implements JsonDeserializer<Uber> {
    @Override
    public Uber deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Position position = jsonDeserializationContext.deserialize(jsonObject.get("currentPosition"), Position.class);
        return new Uber(position);
    }
}
