<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <title>${melForUpdate != null ? "Edit Meal": "Add meal"}</title>

</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit Meal</h2>
<br>
<form method="POST" name="frmEditMeal">
    DateTime: <input type="datetime-local" name="dateTime"
        <c:if test="${melForUpdate != null}"> value="<c:out value="${melForUpdate.dateTime}"/>"
        </c:if>/> <br/>
    Description: <input type="text" name="description"
                        <c:if test="${melForUpdate != null}">value="<c:out value="${melForUpdate.description}"/>"
</c:if>/> <br/>
    Calories: <input type="number" name="calories"
                     <c:if test="${melForUpdate != null}">value="<c:out value="${melForUpdate.calories}"/>"
</c:if>/> <br/>
    <input type="submit" value="Save"/> <button onclick="document.location='/'">Cancel</button>
</form>
</body>
</html>