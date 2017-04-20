<%--
  Created by IntelliJ IDEA.
  User: Yuriy
  Date: 4/20/2017
  Time: 10:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="lang" var="rb" />

<html>
<head>
    <title>Online-Store</title>
    <c:import url="/resources/components/head.jsp"/>
</head>
<body>
<div class="container-fluid">
    <c:import url="/resources/components/header.jsp"/>
    <c:import url="/resources/components/navbar.jsp"/>

    <div class="row">
        <div class="col-md-10 col-md-offset-1 floating-plane">
            <c:if test="${not empty param.failed}">
                <p class="bg-danger">
                    <fmt:message key="form.error" bundle="${rb}"/>
                </p>
            </c:if>

            <hr>

            <form name="category" method="post" action="/admin/categories" class="col-md-8">
                <fieldset>
                    <legend>
                        <fmt:message key="category.update" bundle="${rb}"/>
                    </legend>
                    <input type="hidden" name="__method" value="PUT"/>
                    <input type="hidden" name="id" value="${category.id}"/>
                    <%@include file="/resources/components/categories-fieldset.jsp" %>
                </fieldset>

                <fmt:message key="update" var="update" bundle="${rb}"/>
                <br>
                <input type="submit" class="btn pull-right" value="${update}"/>
            </form>
        </div>
    </div>
</div>
<c:import url="/resources/js/validation/categories_validation.jsp"/>
</body>
</html>