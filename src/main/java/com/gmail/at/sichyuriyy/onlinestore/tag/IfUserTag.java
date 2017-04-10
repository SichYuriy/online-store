package com.gmail.at.sichyuriyy.onlinestore.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by Yuriy on 4/10/2017.
 */
public class IfUserTag extends TagSupport {

    private String role;

    @Override
    public int doStartTag() throws JspException {

        boolean loggedIn = role == null && getRequest().getUserPrincipal() != null;

        if(role == null) {
            if(getRequest().getUserPrincipal() != null) {
                return EVAL_BODY_INCLUDE;
            }
        } else {
            if(getRequest().isUserInRole(role.toUpperCase())) {
                return EVAL_BODY_INCLUDE;
            }
        }

        return SKIP_BODY;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) pageContext.getRequest();
    }
}
