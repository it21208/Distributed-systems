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
            <h1>Delete User</h1>         
        </div>
    <form:form modelAttribute="deleteUser" class="form-horizontal">
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">#ID:</label>
            <div class="col-sm-1">
                <form:input id="id" path="id" type="text" class="form-control" readonly="true"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">User ID:</label>
            <div class="col-sm-1">
                <form:input id="userId" path="userId" type="text" class="form-control" readonly="true"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Name:</label>
            <div class="col-sm-3">
                <form:input id="name" path="name" type="text" class="form-control" readonly="true"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Password:</label>
            <div class="col-sm-2">
                <form:input id="password" path="password" type="text" class="form-control" readonly="true"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Police ID:</label>
            <div class="col-sm-1">
                <form:input id="adt" path="adt" type="text" class="form-control" readonly="true"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Email:</label>
            <div class="col-sm-3">
                <form:input id="email" path="email" type="text" class="form-control" readonly="true"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Telephone:</label>
            <div class="col-sm-2">
                <form:input id="telephone" path="telephone" type="text" class="form-control" readonly="true"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Role:</label>
            <div class="col-sm-2">
                <form:select id="roleId" path="roleId" class="form-control" readonly="true">
                    ${roleOptionSet}
                </form:select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">AM:</label>
            <div class="col-sm-1">
                <form:input id="am" path="am" type="text" class="form-control" readonly="true"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Can submit online:</label>
            <div class="col-sm-1">
                True <form:radiobutton path="onlinesubmit" value="true" disabled="true"/>
                False <form:radiobutton path="onlinesubmit" value="false" disabled="true"/>                
            </div>
        </div>            
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-8">
                <input type="submit" id="btnDelete" value ="Delete" class="btn btn-primary"/>
                <a href="<spring:url value="/admin/users"/>"  class="btn btn-primary">
                    <span></span>Back
                </a>
            </div>
        </div>        
    </form:form>
</body>
</html>


