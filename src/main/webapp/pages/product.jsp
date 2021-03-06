<%--
  Created by IntelliJ IDEA.
  User: Yuriy
  Date: 4/21/2017
  Time: 8:51 PM
  To change this template use File | Settings | File Templates.
--%>
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
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="/resources/css/review.css">
</head>
<body>
<div class="container-fluid">
    <c:import url="/resources/components/header.jsp"/>
    <c:import url="/resources/components/navbar.jsp"/>

    <div class="row">

        <div class="col-md-10 col-md-offset-1 floating-plane">

            <u:if-user role="ADMINISTRATOR">
                <a href="/admin/editProduct.jsp?id=${product.id}" class="btn btn-sm">
                    <i class="glyphicon glyphicon-edit"></i>
                    <fmt:message key="product.edit" bundle="${rb}"/>
                </a>
            </u:if-user>

            <div class="col-md-12">
                <div class="pull-left">
                    <h3>
                        <c:out value="${product.title}"/>
                    </h3>
                    <div class="price">
                        $${product.price.doubleValue()}
                    </div>
                </div>
                <div class="pull-right">
                    <fmt:message key="product.rating" bundle="${rb}"/>
                    <div class="vcentered-container">
                        <div id="rating-static" class="vcentered" style="display: inline-block"></div>
                        ${product.avgRating.doubleValue()} (${product.votesCount})
                    </div>
                </div>
            </div>



            <div class="col-md-12">
                <br/>
                <div class="col-md-12"><c:out value="${product.description}"/></div>

            </div>



            <div class="col-md-12 galery">
                <hr/>
                <div class="w3-content w3-display-container">
                    <img class="mySlides" src="${product.mainImageUrl}" style="width:100%">
                    <c:forEach var="productImage" items="${productImages}">
                        <img class="mySlides" src="${productImage.imageUrl}" style="width:100%">
                    </c:forEach>

                    <button class="w3-button w3-black w3-display-left" onclick="plusDivs(-1)">&#10094;</button>
                    <button class="w3-button w3-black w3-display-right" onclick="plusDivs(1)">&#10095;</button>
                </div>
            </div>

            <fmt:message key="product.edit_images" var="edit_images" bundle="${rb}"/>

            <u:if-user role="ADMINISTRATOR">
                <div class="col-md-12">
                    <hr/>
                    <a class="btn btn-warning" href="/admin/images?productId=${product.id}">${edit_images}</a>
                </div>
            </u:if-user>

            <fmt:message key="product.toCart" var="buy" bundle="${rb}"/>

            <c:if test="${loggedIn}" >
                <div class="col-md-12">
                    <hr/>
                    <c:if test="${product.count > 0}" >
                        <a class="btn btn-success" onclick="toCart(${product.id})">${buy}</a>
                    </c:if>
                      <fmt:message key="product.left" bundle="${rb}" /> ${product.count}
                    <hr/>
                    <div class="alert alert-danger" id="add_error_alert" style="display:none">
                        <fmt:message key="cart.add_product_error_alert" bundle="${rb}" />
                    </div>
                    <div class="alert alert-success" id="add_success_alert" style="display:none">
                        <fmt:message key="cart.add_product_success_alert" bundle="${rb}" />
                    </div>
                </div>
            </c:if>
            <c:if test="${!loggedIn}" >
                <div class="col-md-12 alert-warning">
                    <hr/>
                    <fmt:message key="product.to_cart_auth" bundle="${rb}" />
                </div>
            </c:if>


            <div class="col-md-12">
                <h3>
                    <fmt:message key="reviews.title" bundle="${rb}"/>
                </h3>

                <c:if test="${canVote || oldReview != null }">
                    <center>
                        <div id="new-review-btn">

                            <c:if test="${oldReview != null}">
                                <a href="/user/editReview.jsp?id=${oldReview.id}" class="btn btn-md">
                                    <i class="glyphicon glyphicon-record"></i>
                                    <fmt:message key="review.old" bundle="${rb}"/>
                                </a>
                            </c:if>
                            <c:if test="${oldReview == null}">
                                <a href="/user/newReview.jsp?productId=${product.id}" class="btn btn-md">
                                    <i class="glyphicon glyphicon-edit"></i>
                                    <fmt:message key="review.new" bundle="${rb}"/>
                                </a>
                            </c:if>
                        </div>
                    </center>
                </c:if>

                <br/>

                <div class="reviews">
                    <c:forEach var="review" items="${reviews}">
                        <%@include file="/resources/components/review.jsp" %>
                    </c:forEach>
                </div>
            </div>

        </div>
    </div>
</div>
<script src="/resources/js/components/galery.js"></script>
<script src="/resources/js/product.js"></script>
</body>
</html>