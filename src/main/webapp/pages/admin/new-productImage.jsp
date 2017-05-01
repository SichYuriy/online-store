<%--
  Created by IntelliJ IDEA.
  User: Yuriy
  Date: 5/1/2017
  Time: 11:19 PM
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
    <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
    <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
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
            <h3>
                <fmt:message key="product_image.product" bundle="${rb}" /> <c:out value="${product.title}" />
            </h3>
            <hr>
            <form name="product" method="post" action="/admin/images" class="col-md-8">
                <fieldset>
                    <legend>
                        <fmt:message key="product_image.create" bundle="${rb}"/>
                    </legend>

                    <input type="hidden" name="productId" value="${product.id}"/>
                    <%@include file="/resources/components/product-image-fieldset.jsp" %>
                </fieldset>

                <fmt:message key="create" var="create" bundle="${rb}"/>
                <br>
                <input type="submit" class="btn" value="${create}"/>
            </form>
        </div>
    </div>
</div>
<c:import url="/resources/js/validation/products_validation.jsp"/>
</body>
</html>
