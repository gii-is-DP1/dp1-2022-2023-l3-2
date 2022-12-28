<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="user">

    <form:form modelAttribute="user" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
            <h2>Nombre de Usuario</h2>
            <input label="Username" name="username" required="true"/>
            <h2>Contraseña</h2>
            <input label="Password" name="password" required="true"/>
            
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            <button class="btn btn-default" type="submit">Add Owner</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>
