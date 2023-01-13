<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>

<dwarf:layout pageName="owners">

    <h2>Owner Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><b><c:out value="${jugador.firstName} ${jugador.lastName}"/></b></td>
        </tr>
        <tr>
            <th>Address</th>
            <td><c:out value="${jugador.address}"/></td>
        </tr>
        <tr>
            <th>City</th>
            <td><c:out value="${jugador.city}"/></td>
        </tr>
        <tr>
            <th>Telephone</th>
            <td><c:out value="${jugador.telephone}"/></td>
        </tr>
    </table>

    <spring:url value="{jugadorId}/edit" var="editUrl">
        <spring:param name="jugadorId" value="${jugador.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Owner</a>


    <br/>
    <br/>
    <br/>



</dwarf:layout>
