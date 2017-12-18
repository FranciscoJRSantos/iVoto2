<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: franciscosantos
  Date: 18/12/2017
  Time: 11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container-fluid">
    <s:form action="createUO" method="POST">
        Nome: <s:textfield value="nome"/>
        <br>
        Percente à Faculdade: <s:textfield value="pertence"/>
        <br>
        <s:submit value="Criar Unidade Organica" class="btn btn-default"/>
    </s:form>
</div>
</body>
</html>
