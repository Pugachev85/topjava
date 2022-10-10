<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="ru">
<head>
    <title>Meal list</title>
    <style>
        table,td,th,tr {
            border: solid black;
            border-collapse: collapse;
            padding: 10px;
        }
        #excess {
            color: red;
        }
        #not_excess{
            color: green;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<br>
<a href="#">Add meal</a>
<br>
<br>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${mealsList}" var="meal">
        <fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
        <tr >
            <c:if test="${meal.excess}">
                <td id="excess"><fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/></td>
                <td id="excess"><c:out value="${meal.description}"/></td>
                <td id="excess"><c:out value="${meal.calories}"/></td>
            </c:if>
            <c:if test="${!meal.excess}">
                <td id="not_excess"><fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/></td>
                <td id="not_excess"><c:out value="${meal.description}"/></td>
                <td id="not_excess"><c:out value="${meal.calories}"/></td>
            </c:if>
            <td><a href="#">Update</a></td>
            <td><a href="#">Delete</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>