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
                        <a href="/lobby/${lobbyId}/delete-user?username=${usuario.username}">Borrar</a>

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


</petclinic:layout>
