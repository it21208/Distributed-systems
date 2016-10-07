<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <style>
            #header {
                background-color:  #f3a65a;
                color:black;
                height:15%;
                text-align:center;
                padding:1px;
            }
            #nav {
                line-height:30px;
                background-color: blanchedalmond;
                height:75%;
                width:20%;
                float:left;
                padding:1px;	      
            }
            #section {
                background-color:moccasin;
                height:75%;
                width:79%;
                float:left;
                padding:1px;	 	 
            }
            #footer {
                background-color:#f3a65a;
                color:black;
                height:15%;
                clear:both;
                text-align:center;
                padding:1px;
            }

            table, td {
                border: 1px solid black;
                border-collapse: collapse;
            }
            td {
                padding: 15px;
            }
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LIBRARY 3 APPLICATION</title>
    </head>
    <body>

        <div id="header">
            <h1>Welcome to the ${library.name} System</h1>
        </div>   

        <div id="nav">
            <p>
                <a href=" <spring:url value= "/events"/>" class="btn btn-warning">
                    Collections/Seminars/Exhibitions</a>
            </p>
            <p>
                <a href=" <spring:url value= "/login"/>" class="btn btn-warning">
                    Student Login</a>
            </p>
        </div>  
        <div id="section">
            <table>
                <thead>
                    <tr>
                        <th style="text-align: center"></th>
                        <th style="text-align: center"></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Address :</td><td>${library.address}</td>
                    </tr>
                    <tr>
                        <td>Telephone :</td><td>${library.telephone}</td>
                    </tr>
                    <tr>
                        <td>Email :</td><td >${library.email}</td>
                    </tr>
                    <tr>
                        <td>Open Hours :</td><td >${library.openhours}</td>
                    </tr>
                </tbody>
            </table>
        </div>



        <div id="footer">
            Copyright © Alexandros Ioannidis
        </div>                    
    </body>
</html>
