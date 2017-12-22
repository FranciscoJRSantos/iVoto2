<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
    <title> IVotas2</title>
</head>
<body>
<div class="container-fluid">
    <h2> Alterar Eleicao </h2>
    <s:form action="updateEleicao" method="POST">
        <s:hidden name="toShowID"/>
        <br>
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
        <s:submit value="Alterar Eleição" class="btn btn-default"/>
    </s:form>
</div>
</body>
</html>
