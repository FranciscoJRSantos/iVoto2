<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
    <title>IVotas2</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
</head>
<body>
<h2> Criar Mesa de Voto </h2>
<div class="container-fluid">
    Unidade Organica:
    <select name="unidade_organica">
        <c:forEach items="${unidades_organicas}" var="uo">
            <option value="${uo}"> ${uo}</option>
        </c:forEach>
    </select>
    <br>
    Eleicao:
    <select name="eleicao_id">
        <c:forEach items="${eleicoes_id}" var="eleicao_id" varStatus="i">
            <option value="${eleicoes_id[i.index]}">Eleicao: ${eleicoes_titulo[i.index]} Local: ${eleicoes_local[i.index]}</option>
        </c:forEach>
    </select>
    <br>
    Primeiro Utilizador na Mesa:
    <select name="numero_cc">
        <c:forEach items="${utilizadores}" var="item" step="2" varStatus="i">
           <!-- <option value="${item[i.index]}"> Numero_CC: "${item[i.index]}" Nome: "${item[i.index+1]}" </option> -->
        </c:forEach>
    </select>
    <br>
    <s:submit value="Criar Mesa de Voto" class="btn btn-default"/>
</div>

</body>
</html>
