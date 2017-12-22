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

<h2> Login </h2>
<div class="container-fluid">
    <div class="center">
        <s:form action="login" method="POST">
            Numero Cartao Cidadao: <s:textfield name="numero_cc"/>
            <br>
            Nome de Utilizador: <s:textfield name="username"/>
            <br>
            Password: <s:password name="password"/>
            <br>
            <s:submit value="Login" class="btn btn-default"/>
        </s:form>
    </div>
</div>

<div class="container-fluid">
    <h2> Login with Facebook</h2>
</div>
</body>
</html>
