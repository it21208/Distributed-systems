<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <link rel="stylesheet"
              href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LIBRARY 3 Application</title>
    </head>
    <body style="background-color: moccasin">
        <h1 class="col-sm-offset-2 col-sm-10">Student Console</p></h1>
    <hr>   
    <div class="alert alert-warning col-sm-offset-2 col-sm-8">${dissertationStatusMessage}</div>      
    <div id="buttons" class="col-sm-offset-2 col-sm-8">
        <c:choose>
            <c:when test="${enableAddDissertation}">
                <p>                       
                    <a href=" <spring:url value= "/${classRequestPath}/dissertations/loadpdf?userId=${dissertation.userId}&id=-1"/>" class="btn btn-warning">
                        New Dissertation</a>
                </p>               
            </c:when>
            <c:otherwise>                        
            </c:otherwise>
        </c:choose>        
        <c:choose>
            <c:when test="${enableAddSubmission}">
                <p>                       
                    <a href=" <spring:url value= "/${classRequestPath}/dissertations/loadpdf?userId=${dissertation.userId}&id=${dissertation.id}"/>" class="btn btn-warning">
                        New Submission</a>
                </p>               
            </c:when>
            <c:otherwise>                        
            </c:otherwise>
        </c:choose>        
        <p>                       
            <a href="<spring:url value="/student/logout"/>"  class="btn btn-warning">Logout</a>
        </p> 
    </div>
</body>
</html>
