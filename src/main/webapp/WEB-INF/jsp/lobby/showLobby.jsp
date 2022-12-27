<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="Lobby">
    <h2>
         Lobby: ${lobbyName}
    </h2>


    <br>
    Tabla de Usuarios
    <table id="usuariosTable" class="table table-striped" style="width: 400px; margin: auto;">
        <thead>
            <tr>
                <th style="width: 200px; text-align: center;">Usuarios</th>
                <th style="width: 200px; text-align: center;">Listo</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${usuarios}" var="usuario">
            <tr>

                    <td>
                        <a href="/users/${usuario.username}">${usuario.username}</a>
                        <c:if test="${isAdmin && !lobbyAdmin.equals(usuario.username)}">
                            <a href="/lobby/${lobbyId}/delete-user?username=${usuario.username}">
                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                            </a>
                        </c:if>
                    </td>
                    <td>
                        Pendiente
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <form:form modelAttribute="user" action="/lobby/${lobbyId}/add-user" method="post" class="form-horizontal"
               id="search-jugador-form">
        <div class="form-group">
            <div class="control-group" id="username">
                <label class="col-sm-2 control-label">Username </label>
                <div class="col-sm-10">
                    <form:input class="form-control" path="username" size="30" maxlength="80"/>
                    <span class="help-inline"><form:errors path="*"/></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Add User</button>
            </div>
        </div>

    </form:form>

    <c:if test="${usernames.size() > 1}">
        <h2>Tablero</h2>
        <c:if test="${usernames.size() == 2}">
            <form:form modelAttribute="tablero" class="form-horizontal" id="add-owner-form" action="/partida/?username1=${usernames.get(0)}&username2=${usernames.get(1)}" method="POST">
                <div class="form-group has-feedback">
                    <petclinic:inputField label="Name" name="name" />




                </div>
                <div class="col-sm-offset-2 col-sm-10">

                    <button class="btn btn-default" type="submit">Crear Partida</button>

                </div>
            </form:form>
        </c:if>
        <c:if test="${usernames.size() == 3}">
            <form:form modelAttribute="tablero" class="form-horizontal" id="add-owner-form" action="/partida/?username1=${usernames.get(0)}&username2=${usernames.get(1)}&username3=${usernames.get(2)}" method="POST">
                <div class="form-group has-feedback">
                    <petclinic:inputField label="Name" name="name" />




                </div>
                <div class="col-sm-offset-2 col-sm-10">

                    <button class="btn btn-default" type="submit">Crear Partida</button>

                </div>
            </form:form>
        </c:if>
    </c:if>




</petclinic:layout>
