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
    <h2> Utilizadores </h2>
</div>
<div class="container-fluid">
    <c:forEach items="${cc}" varStatus="i" >
        <a href="<s:url action="showUserDetails"/>?userToShow=${cc[i.index]}">CC:${cc[i.index]}</a> Nome: ${nome[i.index]}
        <a href="<s:url action="showUserVoteDetails"/>?userToShow=${cc[i.index]}"> <button> Ver Informação de Voto </button> </a>

        <br>
    </c:forEach>
</div>

</body>
</html>
