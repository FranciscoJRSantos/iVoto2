<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="Nome" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: franciscosantos
  Date: 13/12/2017
  Time: 00:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>IVotas</title>
</head>
<body>
<div class="container-fluid">
    <h2> Listas a Concorrer </h2>
    <div class="container-fluid">
        <c:forEach items="${listas}" var="nome">
            <br>
            ${nome}
            <br>
        </c:forEach>
        <br>
        <s:form action="vote" method="POST">
            <s:textfield name="listaToVote"/>
            <s:submit value="Votar"/>
        </s:form>
    </div>
</div>
</body>
</html>
