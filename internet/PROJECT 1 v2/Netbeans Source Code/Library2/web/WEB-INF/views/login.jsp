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
        <h1 class="col-sm-offset-2 col-sm-10">Welcome to the Library System</p></h1>
    <hr>
    <h2 class="col-sm-offset-2 col-sm-10">Please Login</h2>        
<form:form modelAttribute="identifyUser" class="form-horizontal">
    <div class="form-group">
        <label class="control-label col-sm-2" for="userId">User ID:</label>
        <div class="col-sm-3">
            <form:input id="userId" path="userId" type="text" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-2" for="password">Password:</label>
        <div class="col-sm-3">
            <form:input id="password" path="password" type="password" class="form-control"/>
        </div>
    </div>
    <div class="form-group"> 
        <div class="col-sm-offset-2 col-sm-1">
            <input type="submit" id="btnLogin" value ="Login" class="btn btn-primary"/>
        </div>
    </div>   
</div>
</form:form>
<div class="alert alert-warning col-sm-offset-2 col-sm-4"  style="border-style: groove; background-color: antiquewhite">${ErrorMessage}</div>
</body>
</html>
