package com.gmail.at.sichyuriyy.onlinestore.dispatcher;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseResolver.RenderResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseResolver.ResponseResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Yuriy on 4/6/2017.
 */
public abstract class Controller {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    public void doGet(RequestService reqService, ResponseService respService){}
    public void doPost(RequestService reqService, ResponseService respService){}
    public void doDelete(RequestService reqService, ResponseService respService){}
    public void doPut(RequestService reqService, ResponseService respService){}
    public void doAny(RequestService reqService, ResponseService respService){}


    protected void useDefaultRenderPage(RequestService requestService, ResponseService responseService) {
        responseService.setResponseResolver(
                new RenderResolver("/pages" +
                        withSuffix(requestService.getRequest().getPathInfo()))
        );
    }

    private boolean hasPageSuffix(String pathInfo) {
        int index = pathInfo.lastIndexOf(DispatcherServlet.PAGE_SUFFIX);
        return index + DispatcherServlet.PAGE_SUFFIX.length() == pathInfo.length();
    }

    private String withSuffix(String pathInfo) {
        if (!hasPageSuffix(pathInfo))
            return pathInfo + DispatcherServlet.PAGE_SUFFIX;
        return pathInfo;
    }

}
