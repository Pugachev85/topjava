<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Users</h2>
<form method="post" action="users">
    <p><label>
        <select size="3" name="user">
            <option disabled>Select User</option>
            <option value="1" selected>User</option>
            <option value="2">Admin</option>
        </select>
    </label></p>
    <p><input type="submit" value="Submit"></p>
</form>
</body>
</html>