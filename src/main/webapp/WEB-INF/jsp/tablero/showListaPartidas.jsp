<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="partidas">

    <c:choose>
        <c:when test="${terminadas == false}">
            <h2>Partidas en curso</h2>
            <a href="/partida/all"
            class="btn btn-primary">Ver partidas terminadas</a>
        </c:when>
        <c:otherwise>
            <h2>Partidas terminadas</h2>
            <a href="/partida/en-curso"
            class="btn btn-primary">Ver partidas en curso</a>
        </c:otherwise>
    </c:choose>

    <c:choose>
        <c:when test="${partidas.isEmpty()}">
            <div class="alert alert-warning" role="alert">
                No hay ninguna partida
            </div>
        </c:when>
        <c:otherwise>
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
        </c:otherwise>
    </c:choose>





</petclinic:layout>
