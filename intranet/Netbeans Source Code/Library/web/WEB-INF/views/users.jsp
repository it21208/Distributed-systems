<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet"
href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" pageEncoding="UTF-8">
        <title>LIBRARY Application</title>
    </head>
    <body style="background-color: antiquewhite">
        <div class="col-sm-offset-1 col-sm-11">
            <h1>Users</h1>         
        </div>
        <hr>
        <table class="table table-bordered col-sm-offset-1 col-sm-8" style="background-color: aliceblue">
                <tr>
                    <th class="col-sm-1" style="text-align: center">User ID</th>
                    <th class="col-sm-2" style="text-align: center">Name</th>
                    <th class="col-sm-2" style="text-align: center">Role</th>
                    <th class="col-sm-2" style="text-align: center">Actions</th>
                </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.userId}</td>
                    <td>${user.name}</td>
                    <td>${user.roleName}</td>
                    <td> <a href=" <spring:url value= "/admin/users/modify?userId=${user.userId}"/>" class="btn btn-warning">
                       Modify</a>
                       <a href=" <spring:url value= "/admin/users/delete?userId=${user.userId}"/>" class="btn btn-warning">
                       Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </table>
        <div id="buttons" class="col-sm-offset-1 col-sm-8">
            <p>                       
                <a href="<spring:url value="/admin/users/add"/>"  class="btn btn-primary">
                    <span></span>New
                </a>
                <a href="<spring:url value="/admin"/>"  class="btn btn-primary">
                    <span></span>Back
                </a>
            </p>            
        </div>
        <div class="alert alert-warning col-sm-offset-1 col-sm-8">${errorMessage}</div>
    </body>
</html>
