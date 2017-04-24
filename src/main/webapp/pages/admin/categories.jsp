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
<script src="/resources/js/categories.js"></script>
<body>

<div class="container-fluid">
    <c:import url="/resources/components/header.jsp"/>
    <c:import url="/resources/components/navbar.jsp"/>

    <div class="row">
        <div class="col-md-10 col-md-offset-1 floating-plane">
            <a class="btn btn-primary" href="/admin/newCategory" role="button">
                <fmt:message key="admin.new_category" bundle="${rb}"/>
            </a>

            <hr>
            <c:if test="${not empty param.delete_failed}">
                <div class="alert alert-danger">
                  <fmt:message key="admin.delete_category_error" bundle="${rb}"/>
                </div>
            </c:if>

            <table class="table table-hover table-bordered">
                <thead>
                    <th><fmt:message key="category.title" bundle="${rb}"/></th>
                    <th></th>
                    <th></th>
                </thead>
                <tbody>
                    <c:forEach var="category" items="${categories}">
                        <tr>
                            <td>
                                <c:out value="${category.title}"/>
                            </td>
                            <td>
                                <a class="btn btn-primary" href="/admin/editCategory?id=${category.id}" role="button">
                                    <fmt:message key="admin.edit_category" bundle="${rb}"/>
                                </a>
                                <a class="btn btn-danger" data-method="delete" onclick="delete_ajax(${category.id})" role="button">
                                    <fmt:message key="admin.delete_category" bundle="${rb}"/>
                                </a>
                            </td>
                            <td>
                                <a class="btn btn-success" role="button">
                                    <fmt:message key="admin.products_btn" bundle="${rb}"/>
                                </a>
                            </td>

                        </tr>
                    </tbody>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>