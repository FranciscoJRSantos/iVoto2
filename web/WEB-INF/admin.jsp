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
    <title>IVotas2</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <meta name="viewport" content="width=320px, initial-scale=1, shrink-to-fit=yes">
</head>
<body>

<h2> Consola de Administração </h2>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-1 col-md-1">
        <s:form action="addUser" method="GET">
            <s:submit value="Criar Utilizador" class="btn btn-default"/>
        </s:form>
        </div>

        <div class="col-xs-6 col-md-6 col-md-offset-2">
        <s:form action="showAllUsers" method="GET">
            <s:submit value="Editar Utilizador" class="btn btn-default"/>
        </s:form>
        </div>

    </div>

    <div class="row">
        <div class="col-xs-1 col-md-1">
        <s:form action="addUO" method="GET">
            <s:submit value="Criar Unidade Organica" class="btn btn-default"/>
        </s:form>
        </div>

        <div class="col-xs-6 col-md-6 col-md-offset-2">
        <s:form action="showAllUO" method="GET">
            <s:submit value="Editar Unidade Organica" class="btn btn-default"/>
        </s:form>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-1 col-md-1">
        <s:form action="addEleicao" method="GET">
            <s:submit value="Criar Eleicao" class="btn btn-default"/>
        </s:form>
        </div>

        <div class="col-xs-6 col-md-6 col-md-offset-2">
        <s:form action="showAllEleicoes" method="GET">
            <s:submit value="Editar Eleicao" class="btn btn-default"/>
        </s:form>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-1 col-md-1">
        <s:form action="addLista" method="GET">
            <s:submit value="Criar Lista" class="btn btn-default"/>
        </s:form>
        </div>

        <div class="col-xs-6 col-md-6 col-md-offset-2">
        <s:form action="showAllListas" method="GET">
            <s:submit value="Editar Lista" class="btn btn-default"/>
        </s:form>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-1 col-md-1">
        <s:form action="addMesaVoto" method="GET">
            <s:submit value="Criar Mesa de Voto" class="btn btn-default"/>
        </s:form>
        </div>

        <div class="col-xs-6 col-md-6 col-md-offset-2">
        <s:form action="showAllMesas" method="GET">
            <s:submit value="Editar Mesa de Voto" class="btn btn-default"/>
        </s:form>
        </div>
    </div>

    <div class="container-fluid">
        <s:form action="logout" method="GET">
            <s:submit value="Logout" class="btn btn-default"/>
        </s:form>
    </div>
</div>
</body>
</html>