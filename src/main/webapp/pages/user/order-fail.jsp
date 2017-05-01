<%--
  Created by IntelliJ IDEA.
  User: Yuriy
  Date: 5/1/2017
  Time: 6:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="u" uri="util" %>
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

            <h3><fmt:message key="order.fail_title" bundle="${rb}"/></h3>
            <p>
                <fmt:message key="order.fail_content" bundle="${rb}"/>
            </p>
        </div>
    </div>
</div>
</body>
</html>
