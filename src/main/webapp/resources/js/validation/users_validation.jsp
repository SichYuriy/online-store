<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="lang" var="rb" />

<fmt:message key="validation.user.login" var="user_login" bundle="${rb}"/>
<fmt:message key="validation.user.password" var="user_password" bundle="${rb}"/>
<script>
    $(function() {
        $("form[name='user']").validate({
            rules: {
                username: {
                    required: true,
                    minlength: 5,
                    maxlength: 25,
                    pattern: /^[\wа-яА-ЯіІїЇ@#$%^&+\-= ]{1,64}$/
                },
                password : {
                    required: true,
                    minlength: 6,
                    maxlength: 25,
                    pattern: /^[\wа-яА-ЯіІїЇ@#$%^&+\-= ]{1,64}$/
                }
            },
            messages: {
                login: "${user_login}",
                password: "${user_password}"
            }
        });
    });
</script>