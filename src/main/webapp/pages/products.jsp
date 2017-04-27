<%--
  Created by IntelliJ IDEA.
  User: Yuriy
  Date: 4/24/2017
  Time: 12:32 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

            <h3><c:out value="${category.title}" />: </h3>


            <hr/>
            <c:forEach var="product" items="${products}" >
                <div class="col-md-4 col-md-offset-4">
                    <h4><c:out value="${product.title}"/> (${product.count})
                        <c:if test="${!product.enabled}">
                            <fmt:message key="product.not_enabled" bundle="${rb}"/>
                        </c:if>
                    </h4>
                    <img src="${fn:escapeXml(product.mainImageUrl)}" style="max-height: 350px;width:100%">

                    <a class="btn btn-success" href="/product?id=${product.id}" role="button">
                        <fmt:message key="product.view" bundle="${rb}"/>
                    </a>
                      $<c:out value="${product.price.doubleValue()}"/>
                    <hr/>
                </div>
            </c:forEach>
            <div class="col-md-12" >
                <hr/>
                <fmt:message key="pageNum" bundle="${rb}" />

                <a class="btn btn-primary" href="/products?categoryId=${category.id}" role="button">
                    1
                </a>
                <c:if test="${pageNumber > 1}">
                    <a class="btn btn-primary" href="/products?categoryId=${category.id}&pageNum=${pageNumber - 1}" role="button">
                        <-
                    </a>
                </c:if>
                ${pageNumber}
                <c:if test="${pageNumber < pagesCount}">
                    <a class="btn btn-primary" href="/products?categoryId=${category.id}&pageNum=${pageNumber + 1}" role="button">
                        ->
                    </a>
                </c:if>
                <c:if test="${pagesCount > 1}">
                    <a class="btn btn-primary" href="/products?categoryId=${category.id}&pageNum=${pagesCount}" role="button">
                        ${pagesCount}
                    </a>
                </c:if>


        </div>
    </div>
</div>
</body>
</html>
