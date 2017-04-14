<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<body>
<c:forEach var="j" begin="1" end="3">
   Item <c:out value="${j}"/><p>
</c:forEach>
<c:forEach var="category" items="${categories}">
    <p><c:out value="${category.title}"/></p>
</c:forEach>
</body>
</html>
