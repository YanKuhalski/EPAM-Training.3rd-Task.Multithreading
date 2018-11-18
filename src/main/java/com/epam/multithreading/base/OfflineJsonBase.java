package com.epam.multithreading.base;

import com.google.gson.Gson;

public abstract class OfflineJsonBase<T> implements Base<T> {
    /*package*/ Gson gson;
    /*package*/ final String path;

    protected OfflineJsonBase(String path) {
        this.path = path;
    }
}
