<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="Logros">
    <h2>Logros</h2>

    <table id="logrosTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 150px;">Descripcion</th>
            <th style="width: 150px;">Dificultad</th>
            <th style="width: 150px;">Requisito</th>
            <th style="width: 150px;">Tipo</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${logros}" var="logro">
            <tr>
                <td>${logro.name}</td>
                <td>${logro.descripcion}</td>
                <td>${"&#11088".repeat(logro.dificultad)}</td>
                <td>${logro.requisito}</td>
                <td>${logro.tipo.name.replace("_", " ").toUpperCase()}</td>
                <!-- logro(id, name, descripcion, dificultad, requisito, tipo) -->

<!--
                <td>
                    <c:out value="${owner.user.username}"/>
                </td>
                <td>
                   <c:out value="${owner.user.password}"/>
                </td>
-->

            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
