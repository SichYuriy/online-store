package com.gmail.at.sichyuriyy.onlinestore.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yuriy on 4/6/2017.
 */
public enum ServiceLocator {
    INSTANCE;

    Map<String, Object> objects = new ConcurrentHashMap<>();

    public void add(String name, Object object) {
        objects.put(name, object);
    }

    public <T> void add(Class<T> clazz, T object) {
        add(clazz.getName(), object);
    }

    public Object get(String name) {
        return objects.get(name);
    }

    public <T> T get(Class<T> clazz) {
        return (T) get(clazz.getName());
    }

    public void remove(String name) {
        objects.remove(name);
    }

    public <T> void remove(Class<T> clazz) {
        objects.remove(clazz.getName());
    }

    public void clear() {
        objects.clear();
    }
}
