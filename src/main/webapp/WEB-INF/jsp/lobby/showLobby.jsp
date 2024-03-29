<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>

<dwarf:layout pageName="Lobby">

    <style>
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

    <c:choose>
    <c:when test="${isAdmin}">
        <a href="/lobby/${lobbyId}/delete"><button type="button" class="btn btn-primary">Eliminar lobby</button></a>
    </c:when>
    <c:otherwise>
        <a href="/lobby/${lobbyId}/delete-user?username=${currentUser.username}"><button type="button" class="btn btn-primary">Dejar lobby</button></a>
    </c:otherwise>
</c:choose>



    <h2>
        Lobby: ${lobbyName}
    </h2>


    <br>
    <h2>Usuarios</h2>
    <table id="usuariosTable" class="table table-striped"
        style="width: 400px; margin: auto;">
        <thead>
            <tr>
                <th style="width: 200px; text-align: center;">Usuarios</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${usuarios}" var="usuario">
                <tr>

                    <td style="text-align: center;">
                        <a href="/users/${usuario.username}">${usuario.username}</a>
                        <c:if test="${isAdmin && !lobbyAdmin.equals(usuario.username)}">
                            <a
                                href="/lobby/${lobbyId}/delete-user?username=${usuario.username}">
                                <span class="glyphicon glyphicon-trash"
                                    aria-hidden="true"></span>
                            </a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${isAdmin}">
        <h2>Anyadir usuarios</h2>
        <form onsubmit="" action="/lobby/${lobbyId}/add-user" method="post"
            class="form-horizontal" id="search-jugador-form">
            <div class="form-group">
                <div class="control-group" id="username">
                    <label class="col-sm-2 control-label">Username </label>
                    <div class="col-sm-10">
                        <input  class="form-control" size="30"
                            maxlength="80" id="user-input"/>
                        <ul id="input-dropdown">
                        </ul>
                    </div>
                </div>
            </div>

        </form>
    </c:if>

    <c:if test="${usernames.size() == 1}">
        <div class="alert alert-warning" role="alert">
            Debe haber minimo 2 usuarios en el Lobby para empezar una partida.
        </div>
    </c:if>

    <c:if test="${usernames.size() > 1 && isAdmin}">
        <h2>Tablero</h2>
        <c:if test="${usernames.size() == 2}">
            <form:form modelAttribute="tablero" class="form-horizontal" id="add-owner-form"
                action="/partida/?username1=${usernames.get(0)}&username2=${usernames.get(1)}&lobby-id=${lobbyId}"
                method="POST">
                <div class="form-group">
                    <div class="control-group" id="nombre">
                        <label class="col-sm-2 control-label">Nombre partida </label>
                        <input style="width: 500px;" class="form-control" label="Name" name="name" required="true" minlength="3" maxlength="50"/>
                    </div>
                </div>
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Crear Partida</button>
                </div>
            </form:form>
        </c:if>
        <c:if test="${usernames.size() == 3 && isAdmin}">
            <form:form modelAttribute="tablero" class="form-horizontal" id="add-owner-form"
                action="/partida/?username1=${usernames.get(0)}&username2=${usernames.get(1)}&username3=${usernames.get(2)}&lobby-id=${lobbyId}"
                method="POST">
                <div class="form-group">
                    <div class="control-group" id="nombre">
                        <label class="col-sm-2 control-label">Nombre partida </label>
                        <input style="width: 500px;" class="form-control" label="Name" name="name" required="true" minlength="3" maxlength="50"/>
                    </div>
                </div>
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Crear Partida</button>
                </div>
            </form:form>
        </c:if>
    </c:if>

    <script>
        const input = document.getElementById("user-input")
        const lobbyId = [[${lobbyId}]]
        input.addEventListener("keyup", (e) => {
            fetch(`http://localhost:8080/lobby/users?` + new URLSearchParams({ q: e.target.value, id: lobbyId }).toString())
                    .then(res => res.json())
                    .then(obj => {
                        const usernames = obj.data
                        const dropdown = document.getElementById("input-dropdown")
                        let html = ""
                        for (const username of usernames) {
                            html += '<li><a href="/lobby/' + lobbyId + '/add-user?exactUsername=' + username + '">' + username + '</a></li>'
                        }
                        dropdown.innerHTML = html
                    })
        })

        // No ENTER post al añadir amigos
        const form = document.getElementById("search-jugador-form")
        form.addEventListener('submit', event => {
            event.preventDefault();
            console.log('Form submission cancelled.');
        });
    </script>



</dwarf:layout>

