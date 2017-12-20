<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: franciscosantos
  Date: 18/12/2017
  Time: 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>IVotas</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <meta name="viewport" content="width=320px, initial-scale=1, shrink-to-fit=yes">
</head>
<body>
<h2> Criar Eleição </h2>
<div class="container-fluid">
    <s:form action="createEleicao" method="POST">
        Nome da Eleição: <s:textfield name="nome"/>
        <br>
        Descrição da Eleicão: <s:textfield name="descricao"/>
        <br>
        Tipo da Eleicao:
        <select name="tipo">
            <option value="0"> Nucleo de Estudantes </option>
            <option value="1"> Concelho Geral </option>
            <option value="2"> Direção de Departamento </option>
            <option value="3"> Direção de Faculdade </option>
        </select>
        <br>
        Incio: <input type="datetime-local" name="inicio"/>
        <br>
        Fim: <input type="datetime-local" name="fim"/>
        <br>
        <br>
        Unidade Organica:
        <select name="unidade_organica_nome">
            <c:forEach items="${unidades_organicas}" var="uo">
                <option value="${uo}"> ${uo}</option>
            </c:forEach>
        </select>
        <br>
        <s:submit value="Criar Eleição" class="btn btn-default"/>
    </s:form>
</div>
</body>
</html>
