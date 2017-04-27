package com.gmail.at.sichyuriyy.onlinestore.dispatcher;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.util.UrlUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yuriy on 4/28/2017.
 */
public enum ControllerResolver {

    INSTANCE;

    private Map<String, Controller> controllerMap = new HashMap<>();

    public void addMapping(String url, Controller controller) {
        controllerMap.put(url, controller);
    }

    public Controller getController(String url) {
        return controllerMap.get(UrlUtil.getControllerUrl(url));
    }
}
