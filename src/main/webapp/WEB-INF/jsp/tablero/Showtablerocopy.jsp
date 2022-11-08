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
        </tbody>
    </table>
</petclinic:layout>
