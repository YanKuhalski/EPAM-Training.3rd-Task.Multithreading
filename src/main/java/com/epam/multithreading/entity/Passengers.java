package com.epam.multithreading.entity;

import java.util.List;

public class Passengers {
    private List<Passenger> passengers;

    public Passengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public List<Passenger> getAllPassengers() {
        return passengers;
    }
}
