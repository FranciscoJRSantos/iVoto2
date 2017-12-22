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
    <script>

        var websocket = null;

        window.onload = function() { // URI = ws://10.16.0.165:8080/WebSocket/ws
            connect('ws://' + window.location.host + '/ws');
        }

        function connect(host) { // connect to the host websocket
            if ('WebSocket' in window) {
                console.log("Connect to WebSocket");
                websocket = new WebSocket(host);
            }

            else if ('MozWebSocket' in window) {
                console.log("Connect to WebSocket");
                websocket = new MozWebSocket(host);
            }

            else {
                writeToHistory('Get a real browser which supports WebSocket.');
                return;
            }

            websocket.onopen    = onOpen; // set the event listeners below
            websocket.onclose   = onClose;
            websocket.onmessage = onMessage;
            websocket.onerror   = onError;
        }

        function onOpen(event) {
            /*writeToHistory('Connected to ' + window.location.host + '.');
             document.getElementById('chat').onkeydown = function(key) {
             if (key.keyCode == 13)
             doSend(); // call doSend() on enter key
             };*/
            console.log("onopenjs");

        }

        function onClose(event) {
            /*writeToHistory('WebSocket closed.');
             document.getElementById('chat').onkeydown = null;*/
            console.log("onclosejs")
        }

        function onMessage(message) { // print the received message
            writeToHistory(message.data);
        }

        function onError(event) {
            /*writeToHistory('WebSocket error (' + event.data + ').');
             document.getElementById('chat').onkeydown = null;*/
        }

        function doSend() {
            var message = document.getElementById('chat').value;
            if (message != '')
                websocket.send(message); // send the message
            document.getElementById('chat').value = '';
        }

        function writeToHistory(text) {
            var info = text.split("|");
            console.log(info[0]);
            if(info[0]=="user") {
                var notifications = document.getElementById('notifications');
                var node = document.createElement("LI");
                var textnode = document.createTextNode(info[1]);
                node.appendChild(textnode);                              // Append the text to <li>
                document.getElementById("myList").appendChild(node);     // Append <li> to <ul> with id="myList"
                var userson = document.getElementById('usersontext');
                userson.innerHTML=info[2]

            }
            else if(info[0]=="mesa"){
                var mesason = document.getElementById('mesason');
                mesason.innerHTML = info[1];
            }
            else if(info[0]=="voto"){
                var notifications = document.getElementById('notificationseleicoes');
                var node = document.createElement("LI");
                var textnode = document.createTextNode(info[1]);
                node.appendChild(textnode);                              // Append the text to <li>
                document.getElementById("myListeleicoes").appendChild(node);
            }
        }
    </script>
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
            <s:submit value="Ver Utilizador" class="btn btn-default"/>
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
            <s:submit value="Ver Eleicao" class="btn btn-default"/>
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

    <div class="row">
        <div class="col-xs-1 col-md-1">
            <s:form action="showEleicoesPassadas" method="GET">
                <s:submit value="Ver Eleiçoes Passadas" class="btn btn-default"/>
            </s:form>
        </div>
    </div>
    <div class="container-fluid">
        <s:form action="logout" method="GET">
            <s:submit value="Logout" class="btn btn-default"/>
        </s:form>
    <div style="display: flex"></div>
        <div id="notifications">
            <h1>Notificações Utilizadores:</h1>
            <ul id="myList">
            </ul>

        </div>
        <div id="userson">
            <h1>Users Online:</h1>
            <div id="usersontext">
            </div>
        </div>
    </div>
    <div style="display: flex">
        <div id="mesason:" style="margin-right: 10%">
            <h1>Mesas Online:</h1>
        </div>
        <div id="notificationseleicoes">
            <h1>Notificações Eleições:</h1>
            <ul id="myListeleicoes">
            </ul>
        </div>
    </div>
</div>

