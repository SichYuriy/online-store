<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="form-group">
    <label class="col-md-3 control-label">
        <fmt:message key="product.title" bundle="${rb}"/>
    </label>
    <input  name="title" class="form-control input-md"
            type="text" value="${fn:escapeXml(product.title)}"
            pattern="^[\wа-яА-ЯіІїЇ@#$%^&+\-= ]{1,64}$">
</div>
<div class="form-group">
    <label class="col-md-3 control-label">
        <fmt:message key="product.count" bundle="${rb}"/>
    </label>
    <input  name="count" class="form-control input-md"
            type="number" value="${fn:escapeXml(product.count)}" min="0">
</div>
<div class="form-group">
    <label class="col-md-3 control-label">
        <fmt:message key="product.description" bundle="${rb}"/>
    </label>
    <textarea  name="description" class="form-control input-md"
            type="text" pattern="^[\wа-яА-ЯіІїЇ@#$%^&+\-= ]{1,255}$"
            rows="4" ><c:out value="${product.description}" /></textarea>
</div>
<div class="form-group">
    <label class="col-md-3 control-label">
        <fmt:message key="product.mainImageUrl" bundle="${rb}"/>
    </label>
    <input  name="mainImageUrl" class="form-control input-md"
            type="text" value="${fn:escapeXml(product.mainImageUrl)}"
            pattern="^[\wа-яА-ЯіІїЇ@#$%^&+\-= ]{1,255}$">
</div>
<div class="form-group">
    <label class="col-md-3 control-label">
        <fmt:message key="product.enabled" bundle="${rb}"/>
    </label>
    <input  name="enabled" data-toggle="toggle"
            type="checkbox" value="1"
    <c:if test="${product.enabled}"> checked </c:if> >
</div>
<div class="form-group">
    <label class="col-md-3 control-label">
        <fmt:message key="product.price" bundle="${rb}"/>
    </label>
    <input  name="price" class="form-control input-md"
            type="number" value="${fn:escapeXml(product.price)}">
</div>

