<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>

<dwarf:layout pageName="lobby">
    <h2>Lobby</h2>
    <form:form modelAttribute="lobby" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
            <dwarf:inputField label="Nombre" name="name" />




        </div>
        <div class="col-sm-offset-2 col-sm-10">

             <button class="btn btn-default" type="submit">Crear Lobby</button>

        </div>
    </form:form>
</dwarf:layout>
