<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>

<dwarf:layout pageName="jugador">
    <h2>
        <c:if test="${jugadores['new']}">New </c:if> Jugador
    </h2>
    <form:form modelAttribute="jugador" class="form-horizontal" id="add-jugador-form">
        <div class="form-group has-feedback">
            <dwarf:inputField label="First Name" name="firstName"/>
            <dwarf:inputField label="Last Name" name="lastName"/>
            <dwarf:inputField label="Username" name="user.username"/>
            <dwarf:inputField label="Password" name="user.password"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${jugadores['new']}">
                        <button class="btn btn-default" type="submit">Add Jugador</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Jugador</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</dwarf:layout>
