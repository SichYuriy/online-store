<%--
  Created by IntelliJ IDEA.
  User: Yuriy
  Date: 5/1/2017
  Time: 11:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
            <a class="btn btn-primary" href="/admin/newProductImage?productId=${product.id}" role="button">
                <fmt:message key="admin.new_image" bundle="${rb}"/>
            </a>

            <hr>
            <c:if test="${not empty param.delete_failed}">
                <div class="alert alert-danger">
                    <fmt:message key="admin.delete_category_error" bundle="${rb}"/>
                </div>
            </c:if>

            <table class="table table-hover table-bordered">
                <thead>
                <th><fmt:message key="product_image.img" bundle="${rb}"/></th>
                <th></th>
                </thead>
                <tbody>
                <c:forEach var="image" items="${images}">
                <tr>
                    <td>
                        <image src="${fn:escapeXml(image.imageUrl)}" style="max-width:300px"></image>
                    </td>
                    <td>

                        <a class="btn btn-danger" data-method="delete" onclick="delete_ajax(${image.id}, ${product.id})" role="button">
                            <fmt:message key="admin.delete_image" bundle="${rb}"/>
                        </a>
                    </td>


                </tr>
                </tbody>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
<script src="/resources/js/images.js" ></script>
</body>
</html>