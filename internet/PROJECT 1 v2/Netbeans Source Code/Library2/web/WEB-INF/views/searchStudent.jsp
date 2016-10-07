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
        <div class="col-sm-offset-2 col-sm-10">
            <h1>Search Student</h1>         
        </div>
    <form method="post" class="form-horizontal">
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">User ID:</label>
            <div class="col-sm-1">
                <input id="userId" name="userId" type="text" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-8">
                <input type="submit" id="btnSearch" value ="Search" name="search" class="btn btn-primary"/>
                <a href="<spring:url value="/${classRequestPath}/dissertations"/>"  class="btn btn-primary">
                    <span></span>Back
                </a>
            </div>
        </div>        
    </form>
    <div class="alert alert-warning col-sm-offset-2 col-sm-6" style=" border-style: groove">${errorMessage}</div>
    </body>
</html>

