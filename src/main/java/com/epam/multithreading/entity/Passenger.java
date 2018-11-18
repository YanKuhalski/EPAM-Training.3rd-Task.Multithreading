package com.epam.multithreading.entity;

import com.epam.multithreading.taxis.Taxis;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class Passenger implements Runnable {
    private static final Logger log = Logger.getLogger(Passenger.class);
    private String name;
    private Position currentPosition, endPosition;
    private Uber uber = null;

    public Passenger(String name, Position currentPosition, Position endPosition) {
        this.currentPosition = currentPosition;
        this.endPosition = endPosition;
        this.name = name;
    }

    public void run() {
        Taxis taxis = Taxis.getInstance();
        while (uber == null) {
            log.info(this + " try to find car");
            uber = taxis.askForCar(this);
            if (uber == null) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
                }
            }
        }
        uber.toTake();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o.getClass() == Passenger.class)) return false;

        Passenger passenger = (Passenger) o;

        if (getName() != null ? !getName().equals(passenger.getName()) : passenger.getName() != null) return false;
        if (getCurrentPosition() != null ? !getCurrentPosition().equals(passenger.getCurrentPosition()) : passenger.getCurrentPosition() != null)
            return false;
        if (getEndPosition() != null ? !getEndPosition().equals(passenger.getEndPosition()) : passenger.getEndPosition() != null)
            return false;
        return uber != null ? uber.equals(passenger.uber) : passenger.uber == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getCurrentPosition() != null ? getCurrentPosition().hashCode() : 0);
        result = 31 * result + (getEndPosition() != null ? getEndPosition().hashCode() : 0);
        result = 31 * result + (uber != null ? uber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Passenger :" + " name = " + name + ", currentPosition = " + currentPosition + ", endPosition =" + endPosition;
    }

    public String getName() {
        return name;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }
}
