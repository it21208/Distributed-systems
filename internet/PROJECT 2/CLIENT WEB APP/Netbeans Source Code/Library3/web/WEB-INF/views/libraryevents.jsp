<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet"
              href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" pageEncoding="UTF-8">
        <title>LIBRARY 3 APPLICATION</title>
    </head>
    <body style="background-color: moccasin">
        <div class="col-sm-offset-1 col-sm-11">
            <h1>Collections - Seminars - Events</h1>         
        </div>
        <hr>
        <span class=" col-sm-offset-1 col-sm-8">
            <table class="table table-bordered" style="background-color: aliceblue">
                <thead style="background-color: cornflowerblue; color: whitesmoke">
                    <tr>
                        <th class="col-sm-1" style="text-align: center">Type</th>
                        <th class="col-sm-3" style="text-align: center">Title</th>
                        <th class="col-sm-2" style="text-align: center">Start Date</th>
                        <th class="col-sm-2" style="text-align: center">End Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${libraryevents}" var="libraryevent">
                        <tr>
                            <td>${libraryevent.type}</td>
                            <td>${libraryevent.title}</td>
                            <td >${libraryevent.startdate}</td>
                            <td >${libraryevent.enddate}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </span>
        <div id="buttons" class="col-sm-offset-1 col-sm-8">
            <hr>
            <p>
                <a href="<spring:url value="/events/register"/>"  class="btn btn-primary">
                            <span></span>Register to newsletter</a>
                <a href="<spring:url value="/${backBtnPath}"/>"  class="btn btn-primary">
                    <span></span>Back
                </a>
            </p>            
        </div>
    </body>
</html>
