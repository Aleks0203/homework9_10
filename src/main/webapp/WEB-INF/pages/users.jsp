<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<meta charset="UTF-8">
<title>Users</title>
</head>
<body>
<h2>Users List</h2>
<p><a href='<c:url value="/add" />'>Add user</a></p>
<table class="table table-dark">
<tr><th>UserName</th><th>Password</th><th>Age</th><th>Is_Active</th><th>Telephone</th><th>Address</th><th></th></tr>
<c:forEach var="user" items="${users}">
 <tr><td>${user.username}</td>
    <td>${user.password}</td>
    <td>${user.age}</td>
        <td><c:if test="${user.active == true}">
                 <p>I am a superman</p>
             </c:if>
             <c:if test="${user.active == false}">
                 <p>Staying at shadow</p>
             </c:if></td>
    <td>${user.telephone}</td>
    <td>${user.address}</td>
    <td>
      <form method="post" action='<c:url value="/delete" />' style="display:inline;">
        <input type="hidden" name="user_id" value="${user.id}">
        <input type="submit" value="Delete">
    </form>
 </td></tr>
</c:forEach>
</table>
</body>
</html>

