<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<dwarf:layout pageName="estadisticas">

    <c:choose>
        <c:when test="${global == true}">
            <h2>ESTADISTICA GLOBAL</h2>
            <a href="/estadistica/${authenticatedUser.username}"
            class="btn btn-primary">Ver mi estadistica</a>
        </c:when>
        <c:otherwise>
            <h2>MI ESTADISTICA</h2>
            <a href="/estadistica"
            class="btn btn-primary">Ver estadistica global</a>
        </c:otherwise>
    </c:choose>


    <h2>Ultimas 10 partidas</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 150px;">Creador</th>
            <th style="width: 150px;">Jugadores</th>
            <th style="width: 150px;">Duracion</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${partidas}" var="partida">
            <tr>
                <td>
                    <a href="/partida/${partida.id}">${partida.id} - ${partida.name}</a>
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

    <table class="table table-striped" style="margin: 0 auto; width: 600px;">
        <thead>
            <tr>
                <th style="width: 150px;">Propiedad</th>
                <th style="width: 150px;">Valor</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Duracion maxima</td>
                <td>${duracionMaxima}</td>
            </tr>
            <tr>
                <td>Duracion minima</td>
                <td>${duracionMinima}</td>
            </tr>
            <tr>
                <td>Duracion media</td>
                <td>${duracionMedia}</td>
            </tr>
        </tbody>
    </table>



</dwarf:layout>
