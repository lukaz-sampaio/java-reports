<%-- 
    Document   : view.cliente.report
    Created on : 07/03/2018, 10:50:31
    Author     : lucas
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>REPORT</h1>
        <form>
            <select name="tipoArquivo">
                <c:forEach var="tipo" items="${tipoArquivo}">
                    <option value="${tipo.key}">${tipo.value}</option>
                </c:forEach>
            </select>
            
            <button name="botao" value="export">Exportar</button>
        </form>
    </body>
</html>
