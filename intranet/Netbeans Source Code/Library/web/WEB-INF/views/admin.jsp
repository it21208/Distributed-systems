<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <link rel="stylesheet"
              href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LIBRARY Application</title>
    </head>
    <body style="background-color: antiquewhite">
        <h1 class="col-sm-offset-2 col-sm-10">Administrative Console</p></h1>
    <hr>   
    <div id="buttons" class="col-sm-offset-2 col-sm-8">
         <p>                       
            <a href="<spring:url value="/admin/users"/>"  class="btn btn-primary">USERS</a>
        </p>            
        <p>                       
            <a href="<spring:url value="/admin/roles"/>"  class="btn btn-primary">ROLES</a>
        </p>            
        <p>                       
            <a href="<spring:url value="/admin/servicerights"/>"  class="btn btn-primary">SERVICES</a>
        </p>  
        <p>                       
            <a href="<spring:url value="/admin/logout"/>"  class="btn btn-warning">Logout</a>
        </p> 
    </div>
</body>
</html>
