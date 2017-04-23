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

            <h3><fmt:message key="home.categories" bundle="${rb}"/></h3>


            <hr/>
            <c:forEach var="category" items="${categories}" >
                <a class="btn btn-primary" href="/products?categoryId=${category.id}" role="button">
                    <c:out value="${category.title}" />
                </a>
                <u:if-user role="ADMINISTRATOR">
                    <a class="btn btn-success" href="/products?categoryId=${category.id}" role="button">
                        <fmt:message key="admin.new_product" bundle="${rb}"/>
                    </a>
                </u:if-user>

                <hr/>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>