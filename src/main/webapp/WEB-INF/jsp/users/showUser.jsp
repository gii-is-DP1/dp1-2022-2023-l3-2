<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="Usuario">
    <h2>
         Usuario:  ${usuario.username}
    </h2>


    <br>
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
            <tr>
                <c:forEach items="${jugadores}" var="jugador">
                    <td>

                        <c:out value="${jugador.firstName}"></c:out>

                    </td>
                    <td>
                        Oro: ${jugador.oro}, Acero: ${jugador.acero}, Hierro: ${jugador.hierro}, Medallas: ${jugador.medalla}, Objetos: ${jugador.objeto}
                    </td>
                    <td>

                    </td>
                </c:forEach>
            </tr>
        </tbody>
    </table>
    <br>
    Tabla de logros
    <table id="logrosTable" class="table table-striped" style="width: 1100px;">
        <thead>
            <tr>
                <th style="width: 200px; text-align: center;">Nombre de Logro</th>
                <th style="width: 200px; text-align: center;">Dificultad</th>
                <th style="width: 200px; text-align: center;">Descripcion</th>
            </tr>
        </thead>
        <tbody>
            <tr>

                    <td>
                        Ejemplo de nombre de Logro
                    </td>
                    <td>
                        Ejemplo de dificultad (2 estrellas)
                    </td>
                    <td>
                        Este es un logro de prueba
                    </td>
            </tr>
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
</petclinic:layout>
