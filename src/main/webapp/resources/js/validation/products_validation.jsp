<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="lang" var="rb" />

<fmt:message key="validation.products.title" var="products_title" bundle="${rb}"/>
<fmt:message key="validation.products.description" var="products_description" bundle="${rb}"/>
<fmt:message key="validation.products.mainImageUrl" var="products_mainImageUrl" bundle="${rb}"/>
<fmt:message key="validation.products.count" var="products_count" bundle="${rb}"/>
<fmt:message key="validation.products.price" var="products_price" bundle="${rb}"/>
<script>
    $(function() {
        $("form[name='product']").validate({
            rules: {
                title: {
                    required: true,
                    minlength: 3,
                    maxlength: 30,
                    pattern: /^[/\\:,.\wа-яА-ЯіІїЇ@#$%^&+\-= ]{1,64}$/
                },
                categoryId: {
                    required: true
                },
                enabled: {
                    required: true
                },
                description: {
                    required: true,
                    maxlength: 255,
                    pattern: /^[/\\:,.()\s\wа-яА-ЯіІїЇ@#$%^&+\-= ]{1,255}$/
                },
                mainImageUrl: {
                    required: true,
                    maxlength: 255,
                    pattern: /^[_:./\\\s\wа-яА-ЯіІїЇ@#$%^&+\-= ]{1,255}$/
                },
                count: {
                    required: true,
                    min: 0
                },
                price: {
                    required: true,
                    min: 0
                }

            },
            messages: {
                title: "${products_title}",
                description: "${products_description}",
                mainImageUrl: "${products_mainImageUrl}",
                count: "${products_count}",
                price: "${products_price}"
            }
        });
    });
</script>