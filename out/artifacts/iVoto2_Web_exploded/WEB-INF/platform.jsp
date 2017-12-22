<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
    <h1>Eleicoes disponiveis</h1>
    <c:forEach items="${eleicoes}" var="eleicao">
        <c:forEach items="eleicao">
            ID: <c:out value="${eleicao[0]}"/>
            Nome: <c:out value="${eleicao[1]}"/>
            Localização <c:out value="${eleicao[2]}"/>
        </c:forEach>
    </c:forEach>
</div>
</body>
</html>
