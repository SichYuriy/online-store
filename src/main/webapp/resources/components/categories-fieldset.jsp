<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="row">
    <label class="col-md-3 control-label">
        <fmt:message key="category.title" bundle="${rb}"/>
    </label>

    <div class="col-md-9">
        <input  name="title" class="form-control input-md" required=""
                type="text" value="${category.title}">
    </div>
</div>
