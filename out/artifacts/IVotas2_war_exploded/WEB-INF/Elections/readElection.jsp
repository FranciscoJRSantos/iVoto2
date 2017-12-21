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
    <s:form action="updateEleicao" method="POST">
        <c:forEach items="${eleicaoToShow}" var="eleicao_data">
            <s:textfield name="${eleicao_data}" value="${eleicao_data}"/>
        </c:forEach>
    </s:form>
</div>
</body>
</html>
