<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <link rel="stylesheet"
              href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LIBRARY 3 APPLICATION</title>
    </head>
    <body style="background-color: moccasin">
        <h1 class="col-sm-offset-2 col-sm-10">Library Event Registration Form</p></h1>
    <hr>
    <h2 class="col-sm-offset-2 col-sm-10">Please enter your details</h2>        
<form:form modelAttribute="newEventEmailRecipient"  class="form-horizontal">
    <div class="form-group">
        <label class="control-label col-sm-2" for="name">Name:</label>
        <div class="col-sm-3">
            <form:input id="name" path="name" type="text" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-2" for="email">Email:</label>
        <div class="col-sm-3">
            <form:input id="email" path="email" type="text" class="form-control"/>
        </div>
    </div>
    <div class="form-group"> 
        <div class="col-sm-offset-2 col-sm-6">
            <input type="submit" id="btnRegister" value ="Register" class="btn btn-primary"/>
            <a href="<spring:url value="/events"/>"  class="btn btn-primary">
                    <span></span>Back
                </a>
        </div>
    </div>   
</div>
</form:form>
<div class="alert alert-warning col-sm-offset-2 col-sm-6">${errorMessage}</div>
</body>
</html>
