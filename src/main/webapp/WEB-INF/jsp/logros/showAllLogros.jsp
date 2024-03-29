<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<dwarf:layout pageName="Logros">
    <h2>Logros</h2>
    <sec:authorize access="hasAuthority('admin')">
        <h2><a href="/logros/create">Crear nuevo logro</a></h2>
                </sec:authorize>


    <table id="logrosTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 150px;">Descripcion</th>
            <th style="width: 150px;">Dificultad</th>
            <th style="width: 150px;">Requisito</th>
            <th style="width: 150px;">Tipo</th>
            <sec:authorize access="hasAuthority('admin')">
                <th style="width: 150px;">Modificar</th>
            </sec:authorize>
            <sec:authorize access="hasAuthority('admin')">
                <th style="width: 150px;">Eliminar</th>
            </sec:authorize>
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
                <sec:authorize access="hasAuthority('admin')">
                    <td><a href="/logros/mod?logro=${logro.id}">Modificar</a></td>
                </sec:authorize>
                <sec:authorize access="hasAuthority('admin')">
                    <td><a href="/logros/del?logro=${logro.id}">Eliminar</a></td>
                </sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</dwarf:layout>
