<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: franciscosantos
  Date: 18/12/2017
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>IVotas</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
</head>
<body>
<h2> Criar Lista </h2>
<div class="container-fluid">
    <s:form action="createLista" method="POST">
        Nome da Lista: <s:textfield name="nome"/>
        <br>
        Numero do Cartao de Cidadão do cabeça de lista: <s:textfield name="numero_cc"/>
        <br>
        Tipo de Utilizador:
        <select name="tipo">
            <option value="0"> Estudante </option>
            <option value="1"> Docente </option>
            <option value="2"> Funcionário </option>
        </select>
        <br>
        Eleicao:
        <select name="eleicao_id">
            <c:forEach items="${eleicoes_id}" var="eleicao_id" varStatus="i">
                <option value="${eleicoes_id[i.index]}">Eleicao: ${eleicoes_titulo[i.index]} Local: ${eleicoes_local[i.index]}</option>
            </c:forEach>
        </select>
        <br>
        <s:submit value="Criar Lista" class="btn btn-default"/>
    </s:form>
</div>
</body>
</html>
