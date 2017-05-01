<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="form-group">
    <label class="col-md-3 control-label">
        <fmt:message key="product_image.image_url" bundle="${rb}"/>
    </label>
    <input  name="imageUrl" class="form-control input-md"
            type="text" value="${fn:escapeXml(productImage.imageUrl)}"
            required />
</div>
