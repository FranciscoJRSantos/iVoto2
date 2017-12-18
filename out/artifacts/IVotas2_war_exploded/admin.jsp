<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%--
  Created by IntelliJ IDEA.
  User: franciscosantos
  Date: 27/11/2017
  Time: 12:41
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>IVotas</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
</head>
<body>

<c:if test="${session.loggedIn != true && session.username == null}">
    <c:redirect url="index.jsp" />
</c:if>
<h2> Consola de Administração </h2>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-1 col-md-1">
        <s:form action="createUser" method="GET">
            <s:submit value="Criar Utilizador" class="btn btn-default"/>
        </s:form>
        </div>

        <div class="col-xs-6 col-md-6 col-md-offset-2">
        <s:form action="showUsers" method="GET">
            <s:submit value="Editar Utilizador" class="btn btn-default"/>
        </s:form>
        </div>

    </div>

    <div class="row">
        <div class="col-xs-6 col-md-1">
        <s:form action="createUO" method="GET">
            <s:submit value="Criar Unidade Organica" class="btn btn-default"/>
        </s:form>
        </div>

        <div class="col-xs-6 col-md-6 col-md-offset-2">
        <s:form action="showUOs" method="GET">
            <s:submit value="Editar Unidade Organica" class="btn btn-default"/>
        </s:form>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-6 col-md-1">
        <s:form action="createEleicao" method="GET">
            <s:submit value="Criar Eleicao" class="btn btn-default"/>
        </s:form>
        </div>

        <div class="col-xs-6 col-md-6 col-md-offset-2">
        <s:form action="showEleicoes" method="GET">
            <s:submit value="Editar Eleicao" class="btn btn-default"/>
        </s:form>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-6 col-md-1">
        <s:form action="createLista" method="GET">
            <s:submit value="Criar Lista" class="btn btn-default"/>
        </s:form>
        </div>

        <div class="col-xs-6 col-md-6 col-md-offset-2">
        <s:form action="showListas" method="GET">
            <s:submit value="Editar Lista" class="btn btn-default"/>
        </s:form>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-6 col-md-1">
        <s:form action="createMesaVoto" method="GET">
            <s:submit value="Criar Mesa de Voto" class="btn btn-default"/>
        </s:form>
        </div>

        <div class="col-xs-6 col-md-6 col-md-offset-2">
        <s:form action="showMesasVoto" method="GET">
            <s:submit value="Editar Mesa de Voto" class="btn btn-default"/>
        </s:form>
        </div>
    </div>
</div>
</body>
</html>