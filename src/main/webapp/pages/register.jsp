<%--
  Created by IntelliJ IDEA.
  User: Yuriy
  Date: 4/21/2017
  Time: 1:46 PM
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

    <div class="col-md-offset-4 col-md-8">

        <form class="col-md-6 floating-plane" name="user" method="post" action="/register">
            <c:if test="${not empty param.failed}">
                <p class="bg-danger"><fmt:message key="${error}" bundle="${rb}"/></p>
            </c:if>

            <fieldset>

                <legend><fmt:message key="register.register" bundle="${rb}"/></legend>
                <input type="hidden" name="blackList" value="false"/>
                <div class="form-group">
                    <label class="control-label">
                        <fmt:message key="login.login" bundle="${rb}"/>
                    </label>

                    <input  name="username" placeholder="vasya_pupkin" class="form-control input-md"
                            type="text" value="${username}">
                </div>

                <div class="form-group">
                    <label class="control-label">
                        <fmt:message key="login.password" bundle="${rb}"/>
                    </label>

                    <input  name="password" class="form-control input-md" type="password">
                </div>

            </fieldset>

            <fmt:message key="register.do" bundle="${rb}" var="register"/>
            <input type="submit" class="btn btn-contrast pull-right" value="${register}"/>
        </form>
    </div>
</div>
<c:import url="/resources/js/validation/users_validation.jsp"/>
</body>
</html>
