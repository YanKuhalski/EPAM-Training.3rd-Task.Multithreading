package com.epam.multithreading.entity;

import java.util.List;

public class Ubers {
    private List<Uber> ubers;

    public Ubers(List<Uber> ubers) {
        this.ubers = ubers;
    }

    public List<Uber> getAllUbers() {
        return ubers;
    }
}
