<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="u" uri="util" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="lang" var="rb" />

<div class="row">
    <div class="col-md-3 col-md-offset-1">
        <h3>Online-store</h3>
    </div>
    <div class="col-md-3 col-md-offset-4">
        <c:if test="${not loggedIn}">

            <a class="btn btn-default" href="/login" role="button">
                <fmt:message key="auth" bundle="${rb}"/>
            </a>
            <a class="btn btn-default" href="/register" role="button">
                <fmt:message key="register" bundle="${rb}"/>
            </a>
        </c:if>
        <c:if test="${loggedIn}">
            <a class="btn btn-default" href="/logout" role="button">
                <fmt:message key="logout" bundle="${rb}"/>
            </a>
        </c:if>
    </div>
</div>