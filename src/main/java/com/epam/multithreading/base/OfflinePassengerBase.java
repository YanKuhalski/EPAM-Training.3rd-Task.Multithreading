package com.epam.multithreading.base;

import com.epam.multithreading.deserializer.GsonPassengerDeserializer;
import com.epam.multithreading.entity.Passenger;
import com.epam.multithreading.entity.Passengers;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OfflinePassengerBase extends OfflineJsonBase<Passenger> {
    private static final Logger log = Logger.getLogger(OfflinePassengerBase.class);

    public OfflinePassengerBase(String path) {
        super(path);
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Passenger.class, new GsonPassengerDeserializer())
                .create();
    }

    public List<Passenger> getAllInfo() {
        List<Passenger> passengerList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            Passengers passengers = gson.fromJson(bufferedReader, Passengers.class);
            passengerList.addAll(passengers.getAllPassengers());
        } catch (IOException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return passengerList;
    }
}
