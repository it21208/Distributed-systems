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
            <h1>Roles</h1>         
        </div>
        <hr>
        <table class="table table-bordered col-sm-offset-1 col-sm-8" style="background-color: aliceblue">
                <tr>
                    <th class="col-sm-2" style="text-align: center">Name</th>
                    <th class="col-sm-1" style="text-align: center">Min Users</th>
                    <th class="col-sm-1" style="text-align: center">Max Users</th>
                    <th class="col-sm-2" style="text-align: center">Web View</th>
                    <th class="col-sm-2" style="text-align: center">Actions</th>
                </tr>
            <c:forEach items="${roles}" var="role">
                <tr>
                    <td >${role.name}</td>
                    <td >${role.minUsers}</td>
                    <td >${role.maxUsers}</td>
                    <td>${role.webview}</td>
                    <td> <a href=" <spring:url value= "/admin/roles/delete?id=${role.id}"/>" class="btn btn-warning">
                       Delete</a>
                       <a href=" <spring:url value= "/admin/roles/services?id=${role.id}"/>" class="btn btn-warning">
                       Services</a>
                    </td>
                </tr>
            </c:forEach>
            </table>
        <div id="buttons" class="col-sm-offset-1 col-sm-8">
            <p>                       
                <a href="<spring:url value="/admin/roles/add"/>"  class="btn btn-primary">
                    <span></span>New
                </a>
                <a href="<spring:url value="/admin"/>"  class="btn btn-primary">
                    <span></span>Back
                </a>
            </p>            
        </div>
        <div class="alert alert-warning col-sm-offset-1 col-sm-8">${ErrorMessage}</div>
    </body>
</html>
