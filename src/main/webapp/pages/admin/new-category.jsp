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

            <form method="post" action="/admin/categories" class="col-md-8">
                <fieldset>
                    <legend>
                        <fmt:message key="category.create" bundle="${rb}"/>
                    </legend>
                    <%@include file="/resources/components/categories-fieldset.jsp" %>
                </fieldset>

                <fmt:message key="create" var="create" bundle="${rb}"/>
                <br>
                <input type="submit" class="btn pull-right" value="${create}"/>
            </form>
        </div>
    </div>
</div>
</body>
</html>