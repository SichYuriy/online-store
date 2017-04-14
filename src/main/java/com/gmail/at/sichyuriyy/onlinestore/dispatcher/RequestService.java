package com.gmail.at.sichyuriyy.onlinestore.dispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Yuriy on 4/6/2017.
 */
public class RequestService {

    private HttpMethod httpMethod;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Page that will be rendered as the result of the request
     */
    private String renderPage = null;

    /**
     * If not null than the dispatcher server will redirect the request to this path
     */
    private String redirectPath = null;

    public RequestService(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        httpMethod = HttpMethod.valueOf(request.getMethod().toUpperCase());
    }

    /**
     * Get request parameter(uri parameter or form data)
     */
    private Optional<String> getParameter(String parameter) {
        String s = request.getParameter(parameter);
        return Optional.ofNullable(s);
    }

    public Long getLong(String param) {
        return getParameter(param).map(Long::valueOf).orElse(null);
    }

    public Integer getInt(String param) {
        return getParameter(param).map(Integer::valueOf).orElse(null);
    }

    public String getString(String param) {
        return getParameter(param).orElse("");
    }

    public Boolean getBool(String param) {
        return getParameter(param).map((s) -> s.equals("1") || s.equals("true")).orElse(null);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public boolean isRedirect() {
       return redirectPath != null;
    }

    public boolean isRender() {
        return renderPage != null;
    }

    public String getRenderPage() {
        return renderPage;
    }

    public void setRenderPage(String renderPage) {
        this.renderPage = renderPage;
    }

    public String getRedirectPath() {
        return redirectPath;
    }

    public void setRedirectPath(String redirectPath) {
        this.redirectPath = redirectPath;
    }


    public void putFlashParameter(String param, Object o) {
        Map<String, Object> flash = (Map<String, Object>) request.getSession()
                .getAttribute(DispatcherServlet.FLASH_KEY);
        if(flash == null) {
            flash = new HashMap<>();
        }

        flash.put(param, o);

        request.getSession().setAttribute(DispatcherServlet.FLASH_KEY, flash);
    }

    public Object getFlashParameter(String param) {
        Map<String, Object> flash = (Map<String, Object>) request.getSession()
                .getAttribute(DispatcherServlet.FLASH_KEY);
        if(flash == null) {
            return null;
        }

        return flash.get(param);
    }

    public void clearFlash() {
        Map<String, Object> flash =
                (Map<String, Object>) request.getSession().getAttribute(DispatcherServlet.FLASH_KEY);

        if(flash == null) {
            flash = new HashMap<>();
        }

        flash.clear();

        request.getSession().setAttribute(DispatcherServlet.FLASH_KEY, flash);
    }

    public void setPageAttribute(String key, Object val) {
        request.setAttribute(key, val);
    }

    public void setResponseStatus(int status) {
        response.setStatus(status);
    }

    public HttpServletResponse getResponse() {
        return this.response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
}
