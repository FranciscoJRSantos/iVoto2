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
</head>
<body>

<h2> Login </h2>
<div>
    <ul>
        <s:form action="login" method="POST">
            <li> Numero Cartao Cidadao: <s:textfield name="numero_cc"/> </li>
            <li> Nome de Utilizador: <s:textfield name="username"/> </li>
            <li> Password: <s:password name="password"/> </li>
            <s:submit value="Login"/>
        </s:form>
    </ul>
</div>
<h2> Create New Account Here</h2>
</body>
</html>
