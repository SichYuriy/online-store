<%--
  Created by IntelliJ IDEA.
  User: Yuriy
  Date: 5/1/2017
  Time: 1:59 PM
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
            <h3><fmt:message key="shopping_cart" bundle="${rb}" /></h3>
            <hr />
            <div class="alert alert-danger" id="add_error_alert" style="display:none">
                <fmt:message key="cart.add_product_error_alert" bundle="${rb}" />
            </div>
            <table class="table table-hover table-bordered">
                <thead>
                <th><fmt:message key="line_item.product" bundle="${rb}"/></th>
                <th><fmt:message key="line_item.price" bundle="${rb}"/></th>
                <th><fmt:message key="line_item.count" bundle="${rb}"/></th>
                <th><fmt:message key="line_item.item_total" bundle="${rb}"/></th>
                <th></th>
                </thead>
                <tbody>
                <c:forEach var="line_item" items="${shoppingCart.cartItems}">
                <tr>
                    <td>
                        <c:out value="${line_item.product.title}" />
                    </td>
                    <td>
                        $<c:out value="${line_item.product.price.doubleValue()}" />
                    </td>
                    <td>
                        <c:out value="${line_item.count}" />
                    </td>
                    <td>
                        $<c:out value="${line_item.product.price.doubleValue() * line_item.count}" />
                    </td>
                    <td>
                        <a class="btn btn-success" onclick="addProduct(${line_item.product.id})" role="button">
                            <fmt:message key="cart.add_product_btn" bundle="${rb}"/>
                        </a>
                        <a class="btn btn-danger" onclick="removeProduct(${line_item.product.id})" role="button">
                            <fmt:message key="cart.remove_product_btn" bundle="${rb}"/>
                        </a>
                    </td>

                </tr>
                </tbody>
                </c:forEach>
            </table>

            <hr />
            <p>
                <fmt:message key="order.total" bundle="${rb}" /> $${shoppingCart.total.doubleValue()}
            </p>
            <hr />
            <c:if test="${shoppingCart.cartItems.size() > 0}">
                <form class="col-md-6" method="post" action="/user/shoppingCart">
                    <fmt:message key="cart.make_order" bundle="${rb}" var="new_order"/>
                    <input type="submit" class="btn btn-success" value="${new_order}"/>
                </form>
            </c:if>
            <form class="col-md-6" method="post" action="/user/shoppingCart">
                <input type="hidden" name="__method" value="DELETE"/>
                <fmt:message key="cart.clear" bundle="${rb}" var="new_order"/>
                <input type="submit" class="btn btn-danger" value="${new_order}"/>
            </form>

        </div>
    </div>
</div>
<script src="/resources/js/cart.js" ></script>

</body>
</html>
