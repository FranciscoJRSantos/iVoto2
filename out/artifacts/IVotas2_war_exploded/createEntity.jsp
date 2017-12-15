<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="Bean" scope="request" type="Actions.Action"/>
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
    <title>Title</title>
</head>
<body>
<div>
    <h2>New user</h2>
</div>

<div>
    <s:form action="create${Bean}" method="POST">
        <c:forEach items="${Bean}" var="value">
            <c:out value="$value"/>
        </c:forEach>
    </s:form>
</div>

</body>
</html>
