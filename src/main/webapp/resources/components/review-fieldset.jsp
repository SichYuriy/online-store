<%--
  Created by IntelliJ IDEA.
  User: Yuriy
  Date: 4/22/2017
  Time: 9:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="form-group">
    <label class="col-md-3 control-label">
        <fmt:message key="review.description" bundle="${rb}"/>
    </label>
    <input  name="description" class="form-control input-md"
            type="text" value="${fn:escapeXml(review.description)}"
            pattern="^[\wа-яА-ЯіІїЇ@#$%^&+\-= ]{1,255}$">
</div>

<div class="form-group">
    <label class="col-md-3 control-label">
        <fmt:message key="review.rating" bundle="${rb}"/>
    </label>
    <div class="rating">
        <input type="radio" name="rating" value="0" checked /><span id="hide"></span>
        <input type="radio" name="rating" value="1" ${review.rating.intValue() == 1 ? "checked" : ""} /><span></span>
        <input type="radio" name="rating" value="2" ${review.rating.intValue() == 2 ? "checked" : ""}/><span></span>
        <input type="radio" name="rating" value="3" ${review.rating.intValue() == 3 ? "checked" : ""}/><span></span>
        <input type="radio" name="rating" value="4" ${review.rating.intValue() == 4 ? "checked" : ""}/><span></span>
        <input type="radio" name="rating" value="5" ${review.rating.intValue() == 5 ? "checked" : ""}/><span></span>
    </div>
</div>