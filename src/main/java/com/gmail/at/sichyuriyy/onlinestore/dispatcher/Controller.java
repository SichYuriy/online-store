package com.gmail.at.sichyuriyy.onlinestore.dispatcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Yuriy on 4/6/2017.
 */
public abstract class Controller {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    public void execute(RequestService reqService) {
        HttpMethod method = reqService.getHttpMethod();

        switch (method) {
            case GET:
                doGet(reqService);
                break;
            case POST:
                doPost(reqService);
                break;
            case PUT:
                doPut(reqService);
                break;
            case DELETE:
                doDelete(reqService);
                break;
            default:
                LOGGER.error("Switch doesn't cover all the enum variants");
        }

        doAny(reqService);
    }

    public void doGet(RequestService reqService){}
    public void doPost(RequestService reqService){}
    public void doDelete(RequestService reqService){}
    public void doPut(RequestService reqService){}
    public void doAny(RequestService reqService){}


    protected void useDefaultRenderPage(RequestService requestService) {
        requestService.setRenderPage("/pages" +
                withSuffix(requestService.getRequest().getPathInfo()));
    }

    protected void checkId() {

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
