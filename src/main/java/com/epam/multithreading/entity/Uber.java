package com.epam.multithreading.entity;

import com.epam.multithreading.calculator.Calculator;
import com.epam.multithreading.generator.IdGenerator;
import com.epam.multithreading.taxis.Taxis;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Uber {
    private static final Logger log = Logger.getLogger(Uber.class);
    private static final Calculator calculator = new Calculator();
    private final long Id;
    private Position currentPosition;
    private Passenger betterPassenger;
    private List<Passenger> passengers = new ArrayList<>();

    public Uber(Position currentPosition) {
        this.currentPosition = currentPosition;
        Id = IdGenerator.generateUberId();
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void toTake() {
        log.info("Uber " + Id + " " + currentPosition + " to take passenger " +
                betterPassenger.getName() + " " + betterPassenger.getCurrentPosition() + " --> " + betterPassenger.getEndPosition());
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        Position endPosition = betterPassenger.getEndPosition();
        currentPosition = endPosition;
        betterPassenger.setCurrentPosition(endPosition);
        returnToUber();
    }

    private void returnToUber() {
        betterPassenger = null;
        Taxis uberTaxis = Taxis.getInstance();
        uberTaxis.returnUber(this);
    }

    public void choosePassenger() {
        List<Double> distances = new ArrayList<>();
        for (Passenger passenger : passengers) {
            distances.add(calculator.calculateDistance(passenger.getCurrentPosition(), currentPosition));
        }
        if (distances.size() > 0) {
            int passengerIndex = calculator.findSmallest(distances);
            betterPassenger = passengers.get(passengerIndex);
            passengers.clear();
            Taxis.getInstance().removeUber(this);
        }
    }

    public void askForRide(Passenger passenger) {
        passengers.add(passenger);
    }

    @Override
    public String toString() {
        return "Uber " +
                "Id = " + Id +
                ", currentPosition = " + currentPosition +
                ", betterPassenger = " + betterPassenger +
                ", passengers = " + passengers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o.getClass() == Uber.class)) return false;

        Uber uber = (Uber) o;

        if (getId() != uber.getId()) return false;
        if (getCurrentPosition() != null ? !getCurrentPosition().equals(uber.getCurrentPosition()) : uber.getCurrentPosition() != null)
            return false;
        if (getBetterPassenger() != null ? !getBetterPassenger().equals(uber.getBetterPassenger()) : uber.getBetterPassenger() != null)
            return false;
        return passengers != null ? passengers.equals(uber.passengers) : uber.passengers == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getCurrentPosition() != null ? getCurrentPosition().hashCode() : 0);
        result = 31 * result + (getBetterPassenger() != null ? getBetterPassenger().hashCode() : 0);
        result = 31 * result + (passengers != null ? passengers.hashCode() : 0);
        return result;
    }

    public long getId() {
        return Id;
    }

    public Passenger getBetterPassenger() {
        return betterPassenger;
    }
}
