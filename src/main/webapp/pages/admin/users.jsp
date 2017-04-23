<%--
  Created by IntelliJ IDEA.
  User: Yuriy
  Date: 4/23/2017
  Time: 2:13 PM
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
            <a class="btn btn-primary" href="/admin/newAdmin" role="button">
                <fmt:message key="admin.new_admin" bundle="${rb}"/>
            </a>

            <hr>

            <table class="table table-hover table-bordered">
                <thead>
                <th><fmt:message key="user.login" bundle="${rb}"/></th>
                <th></th>
                <th></th>
                </thead>
                <tbody>
                <c:forEach var="user" items="${users}">
                <tr>
                    <td>
                        <c:out value="${user.login}" />
                        <c:if test="${user.blackList}">(<fmt:message key="user.blackList" bundle="${rb}" /> )</c:if>
                    </td>
                    <td>
                        <c:if test="${!user.blackList}">
                            <a class="btn btn-danger" onclick="update_ajax(${user.id}, ${!user.blackList})" role="button">
                                <fmt:message key="user.move_to_black_list" bundle="${rb}"/>
                            </a>
                        </c:if>
                        <c:if test="${user.blackList}">
                            <a class="btn btn-primary" onclick="update_ajax(${user.id}, ${!user.blackList})" role="button">
                                <fmt:message key="user.move_from_black_list" bundle="${rb}"/>
                            </a>
                        </c:if>
                    </td>
                    <td>
                        <a class="btn btn-success" href="/admin/orders?userId=${user.id}" role="button">
                            <fmt:message key="admin.user_orders" bundle="${rb}"/>
                        </a>
                    </td>

                </tr>
                </tbody>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
<script src="/resources/js/users.js"></script>
</body>
</html>
