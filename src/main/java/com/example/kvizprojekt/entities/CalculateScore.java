package com.example.kvizprojekt.entities;

public record CalculateScore<T extends Number>(T x, T y) {
    public double getResult() {
        return x.doubleValue() / y.doubleValue();
    }

}
