package com.example.task01;

public interface Storage {
    void save(String data);
    String retrieve(int id);
}