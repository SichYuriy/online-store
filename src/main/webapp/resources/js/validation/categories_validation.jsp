<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="lang" var="rb" />

<fmt:message key="validation.categories.title" var="categories_title" bundle="${rb}"/>
<script>
    $(function() {
        $("form[name='category']").validate({
            rules: {
                title: {
                    minlength: 4,
                    maxlength: 25
                },
            },
            messages: {
                title: "${categories_title}",
            }
        });
    });
</script>