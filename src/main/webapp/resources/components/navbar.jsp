<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="u" uri="util" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="lang" var="rb" />

<div class="row">
    <div class="col-md-10 col-md-offset-1">
        <a class="btn btn-default" href="/index" role="button">
            <fmt:message key="home" bundle="${rb}"/>
        </a>
        <a class="btn btn-default" href="/cart" role="button">
            <fmt:message key="cart" bundle="${rb}"/>
        </a>
        <c:if test="loggedIn">
            <a class="btn btn-info" href="/orders" role="button">
                <fmt:message key="myOrders" bundle="${rb}"/>
            </a>
        </c:if>
        <u:if-user role="ADMINISTRATOR">
            <a class="btn btn-warning" href="/admin/orders" role="button">
                <fmt:message key="adminOrders" bundle="${rb}"/>
            </a>
            <a class="btn btn-warning" href="/admin/products" role="button">
                <fmt:message key="adminProducts" bundle="${rb}"/>
            </a>
            <a class="btn btn-warning" href="/admin/users" role="button">
                <fmt:message key="adminUsers" bundle="${rb}"/>
            </a>
            <a class="btn btn-warning" href="/admin/categories" role="button">
                <fmt:message key="adminCategories" bundle="${rb}"/>
            </a>

        </u:if-user>
    </div>
</div>