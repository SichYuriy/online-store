<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb" />

<html>
<head>
    <title>Error</title>
    <c:import url="/resources/components/head.jsp"/>
</head>
<body>

<c:import url="/resources/components/header.jsp"/>

<div class="container-fluid">

    <div class="col-md-offset-1 col-md-10">
        <div class="hero-unit">
            <h1>Error ${pageContext.errorData.statusCode}</h1>
            <hr/>
            <p><b>URL:</b> ${pageContext.request.scheme}://${header.host}${pageContext.errorData.requestURI}</p>

            <p><b>
                <fmt:message key="error.home_help" bundle="${rb}"/>
            </b></p>
            <a href="/" class="btn btn-default">
                <fmt:message key="go_home" bundle="${rb}"/>
            </a>
        </div>
    </div>
</div>

</body>
</html>