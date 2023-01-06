<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="usuario">
    <h2>Perfil personal</h2>

				
					<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
					<a href="/users/${perfil}"><span>Perfil</span></a>
				
    <h2>Ranking de usuarios</h2>
	

    <table id="jugadoresTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Username</th>
            <th style="width: 150px;">Puntuacion</th>
        </tr>
        </thead>
        <tbody>
            
            <c:forEach items="${usuarios}" var="usuario">
                <tr>
                <td>
                    ${usuario.username}
                </td>
                <td>
                    <c:out value="${puntuacion.get(usuario)}"></c:out>
                </td>

                </tr>
            </c:forEach>
            
                      
            
        
        </tbody>
    </table>
    <p>A menor sea la puntuacion mejor se considera</p>
</petclinic:layout>
