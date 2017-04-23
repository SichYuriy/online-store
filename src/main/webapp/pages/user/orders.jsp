<%--
  Created by IntelliJ IDEA.
  User: Yuriy
  Date: 4/23/2017
  Time: 10:41 PM
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
            <hr>

            <table class="table table-hover table-bordered">
                <thead>
                <th><fmt:message key="order.id" bundle="${rb}"/></th>
                <th><fmt:message key="order.owner" bundle="${rb}"/></th>
                <th><fmt:message key="order.date" bundle="${rb}"/></th>
                <th><fmt:message key="order.status" bundle="${rb}"/></th>
                <th></th>
                <th></th>
                </thead>
                <tbody>
                <c:forEach var="order" items="${orders}">
                <tr>
                    <td>
                        <c:out value="${order.id}" />
                    </td>
                    <td>
                        <c:out value="${order.user.login}" />
                    </td>
                    <td>
                        <c:out value="${order.date}" />
                    </td>
                    <td>
                        <c:out value="${order.status}" />
                    </td>
                    <td>
                        <a class="btn btn-success" role="button" target="_blank" href="/order?id=${order.id}">
                            <fmt:message key="order.view" bundle="${rb}"/>
                        </a>
                    </td>
                    <td>
                        <c:if test="${order.status == 'CREATED'}" >
                            <a class="btn btn-primary" onclick="cancel_ajax(${order.id})" role="button">
                                <fmt:message key="user.cancelOrder" bundle="${rb}"/>
                            </a>
                        </c:if>
                    </td>

                </tr>
                </tbody>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
<script src="/resources/js/orders.js"></script>
</body>
</html>