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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <meta name="viewport" content="width=320px, initial-scale=1, shrink-to-fit=yes">
</head>
<body>
<div class="container-fluid">
    <h2> Eleições a decorrer </h2>
    <div class="eleicoes">
            <c:forEach items="${eleicoes_id}" var="eleicao_id" varStatus="i">
                <br>
                <a href="<s:url action="showListsFromElection"/>?toShowID=${eleicoes_id[i.index]}">  Eleicao: ${eleicoes_titulo[i.index]} Local: ${eleicoes_local[i.index]} </a>
                <br>
            </c:forEach>
    </div>
    <br>
    <div class="container-fluid">
        <s:form action="logout" method="GET">
            <s:submit value="Logout" class="btn btn-default"/>
        </s:form>
    </div>
</div>
</body>
</html>
