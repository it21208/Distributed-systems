<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <link rel="stylesheet"
              href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LIBRARY 2 Application</title>
    </head>
    <body style="background-color: antiquewhite">
        <h1 class="col-sm-offset-2 col-sm-10">Manager Console</p></h1>
    <hr>   
    <div id="buttons" class="col-sm-offset-2 col-sm-8">
         <p>                       
            <a href="<spring:url value="/manager/dissertations"/>"  class="btn btn-primary">DISSERTATIONS</a>
        </p>            
        <p>                       
            <a href="<spring:url value="/manager/logout"/>"  class="btn btn-warning">Logout</a>
        </p> 
    </div>
</body>
</html>
