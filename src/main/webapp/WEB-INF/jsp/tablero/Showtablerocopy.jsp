<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="tablero">
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

                        <td >
                            <spring:url value="${pos.getFirstCarta().imagen}" var="dwarf" />
                            <img class="img-responsive" src="${dwarf}" height="160"
                                width="160" style="margin: auto;"/>
                        </td>

                </c:forEach>
            </tr>
            <tr>
                <c:forEach items="${tablero2}" var="pos">

                    <td >
                        <spring:url value="${pos.getFirstCarta().imagen}" var="dwarf" />
                        <img class="img-responsive" src="${dwarf}" height="160"
                            width="160" style="margin: auto;"/>
                    </td>

                </c:forEach>
            </tr>
            <tr>
                <c:forEach items="${tablero3}" var="pos">

                    <td >
                        <spring:url value="${pos.getFirstCarta().imagen}" var="dwarf" />
                        <img class="img-responsive" src="${dwarf}" height="160"
                            width="160" style="margin: auto;"/>
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
            </tr>
        
        </tbody>
    </table>
  
</petclinic:layout>
