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

    <div class="col-md-offset-4 col-md-8">

        <form class="col-md-6 floating-plane" method="post" action="/login">
            <c:if test="${not empty param.failed}">
                <p class="bg-danger"><fmt:message key="login.error" bundle="${rb}"/></p>
            </c:if>

            <fieldset>

                <legend><fmt:message key="login.login" bundle="${rb}"/></legend>

                <div class="form-group">
                    <label class="control-label" for="">
                        <fmt:message key="login.login" bundle="${rb}"/>
                    </label>

                    <input  name="username" placeholder="vasya_pupkin" class="form-control input-md"
                            required="" type="text" value="${username}">
                </div>

                <div class="form-group">
                    <label class="control-label" for="">
                        <fmt:message key="login.password" bundle="${rb}"/>
                    </label>

                    <input  name="password" class="form-control input-md" required="" type="password">
                </div>

            </fieldset>

            <fmt:message key="auth" bundle="${rb}" var="auth"/>
            <input type="submit" class="btn btn-contrast pull-right" value="${auth}"/>
        </form>
    </div>
</div>

</body>
</html>