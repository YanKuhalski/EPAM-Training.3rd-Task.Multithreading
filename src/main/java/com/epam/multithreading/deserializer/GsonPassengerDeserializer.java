package com.epam.multithreading.deserializer;

import com.epam.multithreading.entity.Passenger;
import com.epam.multithreading.entity.Position;
import com.google.gson.*;

import java.lang.reflect.Type;

public class GsonPassengerDeserializer implements JsonDeserializer<Passenger> {
    @Override
    public Passenger deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        Position position = jsonDeserializationContext.deserialize(jsonObject.get("currentPosition"), Position.class);
        Position endPosition = jsonDeserializationContext.deserialize(jsonObject.get("endPosition"), Position.class);
        return new Passenger(name, position, endPosition);
    }
}
