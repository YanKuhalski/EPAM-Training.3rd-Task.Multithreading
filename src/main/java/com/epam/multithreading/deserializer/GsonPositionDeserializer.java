package com.epam.multithreading.deserializer;

import com.epam.multithreading.entity.Position;
import com.google.gson.*;

import java.lang.reflect.Type;

public class GsonPositionDeserializer implements JsonDeserializer<Position> {
    @Override
    public Position deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        int x = object.get("x").getAsInt();
        int y = object.get("y").getAsInt();
        return new Position(x, y);
    }
}
