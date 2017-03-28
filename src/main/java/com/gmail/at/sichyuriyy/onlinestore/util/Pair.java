package com.gmail.at.sichyuriyy.onlinestore.util;

/**
 * Created by Yuriy on 3/27/2017.
 */
public class Pair<K, V> {

    private K first;
    private V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    public K getFirst() {
        return first;
    }

    public void setFirst(K first) {
        this.first = first;
    }

    public V getSecond() {
        return second;
    }

    public void setSecond(V second) {
        this.second = second;
    }
}
