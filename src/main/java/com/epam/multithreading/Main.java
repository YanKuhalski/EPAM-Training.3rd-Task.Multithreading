package com.epam.multithreading;

import com.epam.multithreading.base.Base;
import com.epam.multithreading.base.OfflinePassengerBase;
import com.epam.multithreading.entity.Passenger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Base<Passenger> passengerBase = new OfflinePassengerBase("./src/main/resources/passengers.json");
        List<Passenger> passengers = passengerBase.getAllInfo();
        ExecutorService service = Executors.newCachedThreadPool();
        for (Passenger passenger : passengers) {
            service.submit(passenger);
        }
    }
}
