package com.epam.multithreading.base;

import com.epam.multithreading.deserializer.GsonUberDeserializer;
import com.epam.multithreading.entity.Uber;
import com.epam.multithreading.entity.Ubers;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OfflineUberBase extends OfflineJsonBase<Uber> {
    private static final Logger log = Logger.getLogger(OfflineUberBase.class);

    public OfflineUberBase(String path) {
        super(path);
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Uber.class, new GsonUberDeserializer())
                .create();
    }

    public List<Uber> getAllInfo() {
        List<Uber> uberList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            Ubers ubers = gson.fromJson(bufferedReader, Ubers.class);
            uberList.addAll(ubers.getAllUbers());
        } catch (IOException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return uberList;
    }
}
