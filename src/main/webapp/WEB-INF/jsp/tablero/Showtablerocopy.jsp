<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="tablero">
    <style>
        .ficha {
            width: 80px;
            height: 80px;
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
        }
    </style>
    <h2>Tablero</h2>
    <table id="tableroTable" class="table table-striped" style="width: 1100px;">
        <thead>
            <tr>
                <th style="width: 200px; text-align: center;">1</th>
                <th style="width: 200px; text-align: center;">2</th>
                <th style="width: 200px; text-align: center;">3</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <c:forEach items="${tablero1}" var="pos">

                        <td style="position: relative;">
                            <c:if test="${mazosConEnanoEncima.stream().filter(i -> pos.id.equals(i)).toList().size() != 0}">
                                <img class="ficha" src="/resources/images/Dimensionadas/Rojo.png">
                            </c:if>
                            <spring:url value="${pos.getFirstCarta().imagen}" var="dwarf" />
                            <a href="/partida/${id_partida}/coloca?username=${username}&posicion=${pos.posicion}">
                                <img class="img-responsive" src="${dwarf}" height="160"
                                    width="160" style="margin: auto;"/>
                            </a>
                        </td>

                </c:forEach>
            </tr>
            <tr>
                <c:forEach items="${tablero2}" var="pos">

                    <td style="position: relative;">
                        <c:if test="${mazosConEnanoEncima.stream().filter(i -> pos.id.equals(i)).toList().size() != 0}">
                            <img class="ficha" src="/resources/images/Dimensionadas/Rojo.png">
                        </c:if>
                        <spring:url value="${pos.getFirstCarta().imagen}" var="dwarf" />
                        <a href="/partida/${id_partida}/coloca?username=${username}&posicion=${pos.posicion}">
                            <img class="img-responsive" src="${dwarf}" height="160"
                                width="160" style="margin: auto;"/>
                        </a>
                    </td>

                </c:forEach>
            </tr>
            <tr>
                <c:forEach items="${tablero3}" var="pos">

                    <td style="position: relative;">
                        <c:if test="${mazosConEnanoEncima.stream().filter(i -> pos.id.equals(i)).toList().size() != 0}">
                            <img class="ficha" src="/resources/images/Dimensionadas/Rojo.png">
                        </c:if>
                        <spring:url value="${pos.getFirstCarta().imagen}" var="dwarf" />
                        <a href="/partida/${id_partida}/coloca?username=${username}&posicion=${pos.posicion}">
                            <img class="img-responsive" src="${dwarf}" height="160"
                                width="160" style="margin: auto;"/>
                        </a>
                    </td>
            </c:forEach>
            </tr>
            <tr>
                <c:forEach items="${tablero4}" var="pos">

                        <td>
                            <spring:url value="${pos.getFirstCarta().imagen}" var="dwarf" />
                            <img class="img-responsive" src="${dwarf}" height="160"
                                width="160" style="transform: rotate(90deg); margin: auto;"/>
                        </td>

                </c:forEach>
                </tr>
        </tbody>
    </table>
    <table>
        <tbody>
            <tr>
                <c:forEach items="${jugadores}" var="jugador">
                <td>

                    <ul>
                        <li>
                            Nombre:
                            <c:out value="${jugador.firstName}" />
                        </li>
                        <li>
                            Hierro:
                            <c:out value="${jugador.hierro}" />
                        </li>
                        <li>
                            Medallas:
                            <c:out value="${jugador.medalla}" />
                        </li>
                        <li>
                            Acero:
                            <c:out value="${jugador.acero}" />
                        </li>
                        <li>
                            Oro:
                            <c:out value="${jugador.oro}" />
                        </li>
                        <li>
                            Objetos:
                            <c:out value="${jugador.objeto}" />
                        </li>
                    </ul>

                </td>
            </c:forEach>
            <br/>
                <td>
                    <spring:url value="/resources/images/Dimensionadas/000.png" var="img"/>
                    <img src="${img}" height="223"
                    width="160" style="margin-left: 80px;"/>

                </td>
            </tr>


        </tbody>
    </table>

</petclinic:layout>
