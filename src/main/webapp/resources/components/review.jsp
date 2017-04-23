<%--
  Created by IntelliJ IDEA.
  User: Yuriy
  Date: 4/22/2017
  Time: 2:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="u" uri="util" %>
<%@ page isELIgnored="false" %>


<div class="review">
    <div class="review-header clearfix">
        <div class="pull-left">
            <div class="vcentered-container inline">
                <span class="rating-box vcentered">${review.rating}</span>
                <span class="push-right-sm"></span>
            </div>
        </div>
        <div class="review-author pull-right clearfix">
            <c:out value="${review.author.login}" />
            <c:if test="${user.id == review.author.id || admin}">
                <div class="submit danger" onclick="delete_ajax(${review.id})">
                    <i class="glyphicon glyphicon-remove submit"></i>
                </div>
            </c:if>
        </div>
    </div>
    <c:if test="${not empty review.description}">
        <div class="review-content">
            <c:out value="${review.description}"/>
        </div>
    </c:if>
</div>
<script src="/resources/js/reviews.js"></script>