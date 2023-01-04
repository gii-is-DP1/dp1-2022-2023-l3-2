<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="partidas">

    <h2>Partidas</h2>

    <table class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 150px;">Creador</th>
            <th style="width: 150px;">Jugadores</th>
            <th style="width: 150px;">Tiempo transcurrido</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${partidas}" var="partida">
            <tr>
                <td>
                    <a href="/partida/${partida.id}">${partida.id}</a>
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
                    ${partida.getFormattedDateSinceCreatedAt()}

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>



</petclinic:layout>
