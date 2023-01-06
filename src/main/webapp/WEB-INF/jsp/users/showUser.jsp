<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="Usuario">
    <style>
        #avatar {
            vertical-align: middle;
            width: 150px;
            height: 150px;
            border-radius: 50%;
        }
    </style>
    <h2>
         Usuario:  ${usuario.username}
    </h2>

    <h3>Avatar</h3>
    <img id="avatar" src="${imagen}"></img>


    <br/>

    Tabla de Jugadores
    <table id="jugadoresTable" class="table table-striped" style="width: 1100px;">
        <thead>
            <tr>
                <th style="width: 200px; text-align: center;">Nombre de Jugador</th>
                <th style="width: 200px; text-align: center;">Materiales</th>
                <th style="width: 200px; text-align: center;">Cosa</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${jugadores}" var="jugador">
                <tr>
                    <td>

                        <c:out value="${jugador.user.username}"></c:out>

                    </td>
                    <td>
                        Oro: ${jugador.oro}, Acero: ${jugador.acero}, Hierro: ${jugador.hierro}, Medallas: ${jugador.medalla}, Objetos: ${jugador.objeto}
                    </td>
                    <td>

                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <br>
    Tabla de logros
    <table id="logrosTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 150px;">Descripcion</th>
            <th style="width: 150px;">Dificultad</th>
            <th style="width: 150px;">Requisito</th>
            <th style="width: 150px;">Tipo</th>
        </tr>
        </thead>
        <tbody>


        <c:forEach items="${logros}" var="logro">


            <tr>
                <td>${logro.name}</td>
                <td>${logro.descripcion}</td>
                <td>${"&#11088".repeat(logro.dificultad)}</td>
                <td>${logro.requisito}</td>
                <td>${logro.tipo.name.replace("_", " ").toUpperCase()}</td>
            </tr>
        </c:forEach>


        </tbody>
    </table>
    <table id="estadisticas-table" class="table table-striped" style="width: 1100px;">
        <thead>
            <tr>
                <th style="width: 200px; text-align: center;">Nombre estadistica</th>
                <th style="width: 200px; text-align: center;">Valor</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Partidas ganadas</td>
                <td>${usuario.estadistica.partidasGanadas}</td>
            </tr>
            <tr>
                <td>Partidas perdidas</td>
                <td>${usuario.estadistica.partidasPerdidas}</td>
            </tr>
        </tbody>
    </table>

    <h2>Ultimas partidas</h2>
    <table class="table table-striped" style="width: 1100px;">
        <thead>
            <tr>
                <th style="width: 200px; text-align: center;">Nombre</th>
                <th style="width: 200px; text-align: center;">Creador</th>
                <th style="width: 200px; text-align: center;">Jugadores</th>
                <th style="width: 200px; text-align: center;">Duracion</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${partidas}" var="partida">
                <tr>
                    <td>
                        <a href="/partida/${partida.id}">${partida.name}</a>
                    </td>
                    <td>
                        <a href="/users/${partida.jugadores.get(0).user.username}">
                            ${partida.jugadores.get(0).user.username}
                        </a>
                    </td>
                    <td>
                        <c:forEach items="${partida.jugadores}" var="jugador">
                            <a href="/users/${jugador.user.username}">${jugador.user.username}</a>
                        </c:forEach>
                    </td>
                    <td>
                        ${partida.getFormattedDuration()}

                    </td>
                </tr>
            </c:forEach>
            </tbody>
    </table>

    <form:form modelAttribute="user" action="/users/friend" method="post"
        class="form-horizontal" id="search-jugador-form">
        <div class="form-group">
            <div class="control-group" id="username">
                <label class="col-sm-2 control-label">Username </label>
                <div class="col-sm-10">
                    <form:input class="form-control" path="username" size="30"
                        maxlength="80" id="user-input"/>

                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Add User</button>
            </div>
        </div>

    </form:form>
    <table id="jugadoresTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Username</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${usuarios}" var="usuario">
            <tr>
                <td>
                    <a href="/users/${usuario.username}">${usuario.username}</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


</petclinic:layout>
<script>

    const input = document.getElementById("user-input")
    const lobbyId = [[${lobbyId}]]

    input.addEventListener("keyup", (e) => {
        fetch(`http://localhost:8080/lobby/users?` + new URLSearchParams({ q: e.target.value }).toString())
                .then(res => res.json())
                .then(obj => {
                    const usernames = obj.data
                    const dropdown = document.getElementById("input-dropdown")
                    let html = ""

                    for (const username of usernames) {
                        html += '<li><a href="/users/friend'
                    }
                    dropdown.innerHTML = html
                })
    })
</script>
