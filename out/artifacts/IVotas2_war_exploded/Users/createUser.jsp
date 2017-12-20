<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: franciscosantos
  Date: 12/12/2017
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>IVotas2</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
</head>
<body>
<div>
    <h2> Criar Utilizador </h2>
</div>

<div class="container-fluid">
    <s:form action="createUser" method="POST">
        Numero Cartao Cidadao: <s:textfield name="numero_cc"/>
        <br>
        Validade do Cartão de Cidadão: <s:textfield name="validade_cc"/>
        <br>
        Nome de Utilizador: <s:textfield name="username"/>
        <br>
        Password: <s:password name="password"/>
        <br>
        Unidade Organica: <s:textfield name="unidade_organica"/>
        <br>
        Morada: <s:textfield name="morada"/>
        <br>
        Contacto: <s:textfield name="contacto"/>
        <br>
        Tipo de Utilizador:
        <select name="tipo">
            <option value="0"> Estudante </option>
            <option value="1"> Docente </option>
            <option value="2"> Funcionário </option>
        </select>
        <br>
        <s:submit value="Criar Utilizador" class="btn btn-default"/>
    </s:form>
</div>

</body>
</html>
