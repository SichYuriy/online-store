package com.gmail.at.sichyuriyy.onlinestore.dispatcher;

import com.gmail.at.sichyuriyy.onlinestore.domain.Role;
import com.gmail.at.sichyuriyy.onlinestore.filter.StaticResourceFilter;
import com.gmail.at.sichyuriyy.onlinestore.security.SecurityContext;
import com.gmail.at.sichyuriyy.onlinestore.util.Pair;

import javax.servlet.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by Yuriy on 4/8/2017.
 */
public class DispatcherServletBuilder {

    List<Pair<String, Controller>> urlControllerMap = new ArrayList<>();

    public DispatcherServletBuilder() {
    }

    public DispatcherServletBuilder addMapping(String url, Controller controller) {
        urlControllerMap.add(new Pair<>(url, controller));
        return this;
    }

    public SecuredMethodsBuilder withSecurity(String url, Controller controller) {
        urlControllerMap.add(new Pair<>(url, controller));
        return new SecuredMethodsBuilder(url);
    }

    public DispatcherServlet build() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        for (Pair<String, Controller> entry: urlControllerMap) {
            dispatcherServlet.addMapping(entry.getFirst(), entry.getSecond());
        }
        return dispatcherServlet;
    }

    public DispatcherServlet buildAndRegister(String name, String mapping, ServletContext servletContext) {
        DispatcherServlet servlet = build();

        ServletRegistration.Dynamic dynamic = servletContext.addServlet(name, servlet);
        dynamic.setMultipartConfig(new MultipartConfigElement(""));
        dynamic.addMapping(mapping);

        StaticResourceFilter filter =
                new StaticResourceFilter(mapping.replace("/*", ""));

        FilterRegistration.Dynamic filterDynamic =
                servletContext.addFilter("Static Resource Filter", filter);
        filterDynamic.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), true, "/*");

        return servlet;
    }


    public class SecuredMethodsBuilder {

        private String url;

        public SecuredMethodsBuilder(String url) {
            this.url = url;
        }

        public SecuredRolesBuilder httpMethods(List<HttpMethod> methods) {
            return new SecuredRolesBuilder(url, methods);
        }

        public DispatcherServletBuilder endConstraints() {
            return DispatcherServletBuilder.this;
        }


    }

    public class SecuredRolesBuilder {

        private String url;
        private List<HttpMethod> methods;

        public SecuredRolesBuilder(String url, List<HttpMethod> methods) {
            this.url = url;
            this.methods = methods;
        }

        public SecuredMethodsBuilder roles(List<Role> roles) {
            SecurityContext.INSTANCE.addConstraint(url, methods, roles);
            return new SecuredMethodsBuilder(url);
        }
    }
}
