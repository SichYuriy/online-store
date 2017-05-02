package com.gmail.at.sichyuriyy.onlinestore.dispatcher;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.util.UrlUtil;
import com.gmail.at.sichyuriyy.onlinestore.domain.User;
import com.gmail.at.sichyuriyy.onlinestore.security.SecurityContext;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Yuriy on 4/6/2017.
 */
public class RequestService {

    private HttpMethod httpMethod;
    private HttpServletRequest request;

    /**
     * Page that will be rendered as the result of the request
     */

    public RequestService(HttpServletRequest request) {
        this.request = request;
        this.httpMethod = UrlUtil.getMethod(request);
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

    public Double getDouble(String param) {
        return getParameter(param).map(Double::valueOf).orElse(null);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
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



    public void setPageAttribute(String key, Object val) {
        request.setAttribute(key, val);
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public User getUser() {
        return SecurityContext.INSTANCE.getCurrentUser(request);
    }
}
