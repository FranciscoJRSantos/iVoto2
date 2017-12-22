<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="Nome" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: franciscosantos
  Date: 13/12/2017
  Time: 00:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>IVotas</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <meta name="viewport" content="width=320px, initial-scale=1, shrink-to-fit=yes">
</head>
<body>
<div class="container-fluid">
    <div>
        <p>You are logged in as <s:property value="%{#session.numero_cc}"/></p>
    </div>
    <h2> Eleições a decorrer </h2>
    <div class="eleicoes">
        <s:if test="eleicoes!=null">
            <c:forEach items="${eleicoes_id}" var="eleicao_id" varStatus="i">
                <br>
                <a href="<s:url action="showListsFromElection"/>?eleicaoToVote=${eleicoes_id[i.index]}">
                    Eleicao: ${eleicoes_titulo[i.index]} Local: ${eleicoes_local[i.index]} </a>
                <s:if test="%{#session.facebookID!=null }">
                    <button type="submit" class="btn btn-default"
                            onclick="location.href='<s:url
                                    action="postElectionFacebook"/>?electionID=${eleicoes_id[i.index]}'">
                        Share this election on Facebook
                    </button>
                </s:if>
                <br>
            </c:forEach>
        </s:if>
        <s:else>
            Não há eleições disponíveis!
        </s:else>
    </div>
    <br>
    <div class="container-fluid">
        <s:form action="logout" method="GET">
            <s:submit value="Logout" class="btn btn-default"/>
        </s:form>
    </div>
    <div>
        <h2> Facebook linking </h2>
        <s:if test="%{#session.facebookID==null }">
            <p>Your account isn't linked to any Facebook Account. If you want to enable Facebook Login click the button
                below</p>
            <button type="submit" class="btn btn-default"
                    onclick="location.href='https://www.facebook.com/v2.2/dialog/oauth?client_id=157491105017602&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2FlinkFacebook.action&scope=publish_actions'">
                Link Account with Facebook
            </button>
        </s:if>
        <s:else>
            <p>Your account is currently linked to the Facebook Account ID <a
                    href="http://facebook.com/<s:property value="%{#session.facebookID}"/>">
                <s:property value="%{#session.facebookID}"/></a>
            </p>
            <button type="submit" class="btn btn-default" onclick="location.href='/unlinkFacebook.action'">
                Unlink Account
            </button>
        </s:else>
    </div>
</div>
</body>
</html>
