<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<dwarf:layout pageName="Usuario">
    <style>
        #avatar {
            vertical-align: middle;
            width: 150px;
            height: 150px;
            border-radius: 50%;
        }
        #input-dropdown {
            width: 200px;
            background-color: white;
            list-style: none;
            padding: 0;
            margin:0;
            position: absolute;
            z-index: 999;
        }
        #input-dropdown li {
            width: 100%;
            height: 30px;
            border: 0.5px solid grey;
        }
        #input-dropdown a {
            width: 100%;
            height: 100%;
            padding: 0;
            margin: 0;
            display: flex;
            align-items: center;
            justify-content: center;
        }
    </style>

    <h2>
         Usuario:  ${usuario.username}
    </h2>
    <c:if test="${condicion}">
		<a class="btn btn-default" href='<spring:url value="/users/mod?user=${usuario.username}" htmlEscape="true"/>'>Modificar Mi perfil</a>
	</c:if>

    <a class="btn ${seccion.equals("amigos") ? 'btn-success' : 'btn-primary'}" href="/users/${usuario.username}?seccion=amigos" role="button">Amigos</a>
    <a class="btn ${seccion.equals("logros") ? 'btn-success' : 'btn-primary'}" href="/users/${usuario.username}?seccion=logros" role="button">Logros</a>
    <a class="btn ${seccion.equals("estadistica") ? 'btn-success' : 'btn-primary'}" href="/users/${usuario.username}?seccion=estadistica" role="button">Estadistica</a>
    <a class="btn ${seccion.equals("partidas-en-curso") ? 'btn-success' : 'btn-primary'}" href="/users/${usuario.username}?seccion=partidas-en-curso" role="button">Partidas en curso</a>
    <a class="btn ${seccion.equals("lobbies") ? 'btn-success' : 'btn-primary'}" href="/users/${usuario.username}?seccion=lobbies" role="button">Lobbies</a>
    <a class="btn ${seccion.equals("jugadores") ? 'btn-success' : 'btn-primary'}" href="/users/${usuario.username}?seccion=jugadores" role="button">Jugadores</a>



    <h3>Avatar</h3>
    <img id="avatar" src="${imagen}"></img>

    <c:if test="${seccion eq 'jugadores'}">
        <h2>Mis jugadores</h2>
        <c:choose>
            <c:when test="${jugadores.isEmpty()}">
                <div class="alert alert-warning" role="alert">
                    No tienes ningun jugador
                </div>
            </c:when>
            <c:otherwise>
                <table id="jugadoresTable" class="table table-striped" style="width: 1100px;">
                    <thead>
                        <tr>
                            <th style="width: 200px; text-align: center;">Nombre de Jugador</th>
                            <th style="width: 200px; text-align: center;">Materiales</th>

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
            </c:otherwise>
        </c:choose>

    </c:if>

    <c:if test="${seccion eq 'logros'}">
        <h2>Mis logros</h2>

        <c:choose>
            <c:when test="${logros.isEmpty()}">
                <div class="alert alert-warning" role="alert">
                    No tienes ningun logro
                </div>
            </c:when>
            <c:otherwise>
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
            </c:otherwise>
        </c:choose>

    </c:if>
    <c:if test="${seccion eq 'estadistica'}">
        <table id="estadisticas-table" class="table table-striped" style="width: 600px;">
            <thead>
                <tr>
                    <th style="text-align: center;">Nombre estadistica</th>
                    <th style="text-align: center;">Valor</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Puntuacion total</td>
                    <td>${usuario.estadistica.puntos}</td>
                </tr>
                <tr>
                    <td>Partidas ganadas</td>
                    <td>${usuario.estadistica.partidasGanadas}</td>
                </tr>
                <tr>
                    <td>Partidas perdidas</td>
                    <td>${usuario.estadistica.partidasPerdidas}</td>
                </tr>
                <tr>
                    <td>Partidas jugadas</td>
                    <td>${usuario.estadistica.partidasPerdidas + usuario.estadistica.partidasGanadas}</td>
                </tr>
                <tr>
                    <td>Oro total</td>
                    <td>${usuario.estadistica.oro} - (${promedios.oro} por partida)</td>
                </tr>
                <tr>
                    <td>Objetos totales</td>
                    <td>${usuario.estadistica.objetos} - (${promedios.objetos} por partida)</td>
                </tr>
                <tr>
                    <td>Acero total</td>
                    <td>${usuario.estadistica.acero} - (${promedios.acero} por partida)</td>
                </tr>
                <tr>
                    <td>Medallas totales</td>
                    <td>${usuario.estadistica.medallas} - (${promedios.medallas} por partida)</td>
                </tr>
                <tr>
                    <td>Hierro total</td>
                    <td>${usuario.estadistica.hierro} - (${promedios.hierro} por partida)</td>
                </tr>
            </tbody>
        </table>

        <h2>Ultimas partidas</h2>
        <c:choose>
            <c:when test="${partidas.isEmpty()}">
                <div class="alert alert-warning" role="alert">
                    No tienes ninguna partida
                </div>
            </c:when>
            <c:otherwise>
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
            </c:otherwise>
        </c:choose>

    </c:if>

    <!-- <form:form modelAttribute="user" action="/users/friend" method="post"
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

    </form:form> -->

    <c:if test="${seccion eq 'amigos'}">
        <h2>Mis amigos</h2>

        <form
            class="form-horizontal" id="search-jugador-form">
            <div class="form-group">
                <div class="control-group" id="username">
                    <label class="col-sm-2 control-label">Add friend</label>
                    <div class="col-sm-10">
                        <input class="form-control" size="30"
                            maxlength="80" id="user-input"/>
                        <ul id="input-dropdown">
                        </ul>
                    </div>
                </div>
            </div>

        </form>

        <c:choose>
            <c:when test="${amigos.isEmpty()}">
                <div class="alert alert-warning" role="alert">
                    No tienes ningun amigo
                </div>
            </c:when>
            <c:otherwise>
                <table id="jugadoresTable" class="table table-striped">
                    <thead>
                    <tr>
                        <th style="width: 150px;">Username</th>
                        <th style="width: 150px;">Disponibilidad</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${amigos}" var="usuario">
                        <tr>
                            <td>
                                <a href="/users/${usuario.username}">${usuario.username}</a>

                            </td>
                            <td>
                                <c:choose>
                                <c:when test="${amigosEnLinea.get(usuario.username)}"><p>Disponible</p></c:when>
                                <c:otherwise><p>
                                    En Partida
                                </p></c:otherwise>
                            </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>


    </c:if>

    <c:if test="${seccion eq 'partidas-en-curso'}">
        <h2>Partidas en curso</h2>
        <c:choose>
            <c:when test="${partidasEnCurso.isEmpty()}">
                <div class="alert alert-warning" role="alert">
                    No estas jugando ninguna partida
                </div>
            </c:when>
            <c:otherwise>
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
                        <c:forEach items="${partidasEnCurso}" var="partida">
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
            </c:otherwise>
        </c:choose>

    </c:if>

        <c:if test="${seccion eq 'lobbies'}">
            <h2>Lobbies</h2>
            <c:choose>
                <c:when test="${lobbies.isEmpty()}">
                    <div class="alert alert-warning" role="alert">
                        No perteneces a ninguna Lobby
                    </div>
                </c:when>
                <c:otherwise>
                    <table class="table table-striped" style="width: 1100px;">
                        <thead>
                            <tr>
                                <th style="width: 200px; text-align: center;">Nombre</th>
                                <th style="width: 200px; text-align: center;">Admin</th>
                                <th style="width: 200px; text-align: center;">Usuarios</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${lobbies}" var="lobby">
                                <tr>
                                    <td>
                                        <a href="/lobby/${lobby.id}">${lobby.name}</a>
                                    </td>
                                    <td>
                                        <a href="/users/${lobby.admin}">
                                            ${lobby.admin}
                                        </a>
                                    </td>
                                    <td>
                                        <c:forEach items="${lobby.usuarios}" var="lobbyUser">
                                            <a href="/users/${lobbyUser.username}">${lobbyUser.username}</a>
                                        </c:forEach>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </c:if>

    <script>
        const input = document.getElementById("user-input")

        const BASE_URL = `http://localhost:8080/users/${currentUsername}/search-friends?`

        input.addEventListener("keyup", (e) => {
            fetch(BASE_URL + new URLSearchParams({ q: e.target.value }).toString())
                    .then(res => res.json())
                    .then(obj => {
                        const usernames = obj.data
                        const dropdown = document.getElementById("input-dropdown")
                        let html = ""
                        console.log(obj)
                        for (const username of usernames) {
                            html += '<li><a href="/users/' + `${currentUsername}` + '/add-friend?dest-username=' + username + '">' + username + '</a></li>'

                        }
                        dropdown.innerHTML = html
                    })
        })
    </script>

</dwarf:layout>

