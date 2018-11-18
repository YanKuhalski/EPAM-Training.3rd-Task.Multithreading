package com.epam.multithreading.taxis;

import com.epam.multithreading.base.OfflineUberBase;
import com.epam.multithreading.calculator.Calculator;
import com.epam.multithreading.entity.Passenger;
import com.epam.multithreading.entity.Position;
import com.epam.multithreading.entity.Uber;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Taxis {
    private static final String JSON_PATH = "./src/main/resources/ubers.json";
    private static final Calculator calculator = new Calculator();
    private static final Logger log = Logger.getLogger(Taxis.class);
    private static Taxis instance;
    private static AtomicBoolean isInit = new AtomicBoolean(false);
    private static Lock initLock = new ReentrantLock();
    private static Lock lock = new ReentrantLock();
    private static List<Uber> freeUbers;

    public static Taxis getInstance() {
        if (!isInit.get()) {
            try {
                initLock.lock();
                synchronized (Taxis.class) {
                    if (!isInit.get()) {
                        final Taxis local = new Taxis();
                        instance = local;
                        instance.init();
                        isInit.set(true);
                    }
                }
            } finally {
                initLock.unlock();
            }
        }
        return instance;
    }

    private static void init() {
        freeUbers = new ArrayList<>();
        OfflineUberBase base = new OfflineUberBase(JSON_PATH);
        List<Uber> ubers = base.getAllInfo();
        freeUbers.addAll(ubers);
        log.info("Taxis was initialized");
    }

    public Uber askForCar(Passenger passenger) {
        if (freeUbers.size() > 0) {
            Uber betterUber;
            lock.lock();
            betterUber = chooseCarsForPassenger(passenger);
            betterUber.askForRide(passenger);
            lock.unlock();

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lock.lock();
            betterUber.choosePassenger();
            lock.unlock();

            if (betterUber.getBetterPassenger() == passenger) {
                return betterUber;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    public Uber chooseCarsForPassenger(Passenger passenger) {
        Uber betterUber;
        List<Double> distances = new ArrayList<>();

        Position passengerPosition = passenger.getCurrentPosition();
        for (Uber uber : freeUbers) {
            Position uberPosition = uber.getCurrentPosition();
            double distance = calculator.calculateDistance(passengerPosition, uberPosition);
            distances.add(distance);
        }

        int betterChoice = calculator.findSmallest(distances);
        betterUber = freeUbers.get(betterChoice);
        return betterUber;
    }

    public void returnUber(Uber uber) {
        lock.lock();
        freeUbers.add(uber);
        log.info("Uber  " + uber.getId() + " is ready for the next ride");
        lock.unlock();
    }

    public void removeUber(Uber uber) {
        lock.lock();
        freeUbers.remove(uber);
        lock.unlock();
    }
}
