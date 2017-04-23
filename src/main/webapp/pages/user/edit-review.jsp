<%--
  Created by IntelliJ IDEA.
  User: Yuriy
  Date: 4/22/2017
  Time: 9:10 PM
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
    <link rel="stylesheet" href="/resources/css/rating.css"/>
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

            <form name="category" method="post" action="/user/reviews" class="col-md-8">
                <fieldset>
                    <legend>
                        <fmt:message key="review.update" bundle="${rb}"/>
                    </legend>
                    <input type="hidden" name="__method" value="PUT"/>
                    <input type="hidden" name="id" value="${review.id}"/>
                    <input type="hidden" name="productId" value="${product.id}"/>
                    <%@include file="/resources/components/review-fieldset.jsp" %>
                </fieldset>

                <fmt:message key="update" var="update" bundle="${rb}"/>
                <br>
                <input type="submit" class="btn" value="${update}"/>
            </form>
        </div>
    </div>
</div>
<%--<c:import url="/resources/js/validation/categories_validation.jsp"/>--%>
</body>
</html>