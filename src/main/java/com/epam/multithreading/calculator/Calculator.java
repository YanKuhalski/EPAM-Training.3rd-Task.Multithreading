package com.epam.multithreading.calculator;

import com.epam.multithreading.entity.Position;

import java.util.List;

public class Calculator {
    public double calculateDistance(Position firstPosition, Position secondPosition) {
        int firstX, secondX, firstY, secondY;
        firstX = firstPosition.getX();
        firstY = firstPosition.getY();
        secondX = secondPosition.getX();
        secondY = secondPosition.getY();
        int xDistance = Math.abs(firstX - secondX);
        int yDistance = Math.abs(firstY - secondY);
        double firstDistancePow = Math.pow(xDistance, 2);
        double secondDistancePow = Math.pow(yDistance, 2);
        return Math.sqrt(firstDistancePow + secondDistancePow);
    }

    public int findSmallest(List<Double> list) {
        int smallestIndex = 0;
        double smallest = list.get(smallestIndex);
        for (int i = 1; i < list.size(); i++) {
            double value = list.get(i);
            if (value < smallest) {
                smallestIndex = i;
                smallest = value;
            }
        }
        return smallestIndex;
    }
}
