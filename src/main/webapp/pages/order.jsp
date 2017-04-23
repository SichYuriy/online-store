<%--
  Created by IntelliJ IDEA.
  User: Yuriy
  Date: 4/23/2017
  Time: 7:10 PM
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
                <th><fmt:message key="line_item.product" bundle="${rb}"/></th>
                <th><fmt:message key="line_item.price" bundle="${rb}"/></th>
                <th><fmt:message key="line_item.count" bundle="${rb}"/></th>
                <th><fmt:message key="line_item.item_total" bundle="${rb}"/></th>
                </thead>
                <tbody>
                <c:forEach var="line_item" items="${lineItems}">
                <tr>
                    <td>
                        <c:out value="${line_item.product.title}" />
                    </td>
                    <td>
                        $<c:out value="${line_item.tempPrice.doubleValue()}" />
                    </td>
                    <td>
                        <c:out value="${line_item.count}" />
                    </td>
                    <td>
                        $<c:out value="${line_item.tempPrice.doubleValue() * line_item.count}" />
                    </td>

                </tr>
                </tbody>
                </c:forEach>
            </table>

            <hr />
            <p>
                <fmt:message key="order.total" bundle="${rb}" /> $${total}
            </p>
            <hr />
        </div>
    </div>
</div>
</body>
</html>
