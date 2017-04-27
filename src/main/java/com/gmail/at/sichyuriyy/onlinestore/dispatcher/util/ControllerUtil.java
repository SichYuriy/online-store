package com.gmail.at.sichyuriyy.onlinestore.dispatcher.util;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Yuriy on 4/28/2017.
 */
public class ControllerUtil {

    public static final Logger LOGGER = LogManager.getLogger(ControllerUtil.class);

    public static void service(Controller controller, RequestService requestService, ResponseService responseService) {
        switch (requestService.getHttpMethod()) {
            case GET:
                controller.doGet(requestService, responseService);
                break;
            case POST:
                controller.doPost(requestService, responseService);
                break;
            case PUT:
                controller.doPut(requestService, responseService);
                break;
            case DELETE:
                controller.doDelete(requestService, responseService);
                break;
            default:
                LOGGER.error("Switch doesn't cover all the enum variants");
        }

        controller.doAny(requestService, responseService);
    }
}
