<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="user">
    <style>
        .alert {
            width: 400px;
            height: 50px;
            padding: 5px 20px;
            border: 1px red;
            background-color: rgb(255, 205, 205);
            color: rgb(255, 105, 105);
            border-radius: 5px;
            display: flex;
            align-items: center;
        }
    </style>

    <form:form modelAttribute="user" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
            <h2>Nombre de Usuario</h2>
            <input label="Username" name="username" required="true"/>
            <h2>Contraseña</h2>
            <input label="Password" name="password" required="true"/>
            <h2>URL imagen</h2>
            <input label="URL imagen" name="imgperfil" required="false"/>

        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            <button class="btn btn-default" type="submit">Add User</button>
            </div>
        </div>
        <c:if test="${mensaje != null}">
            <div class="alert">
                <c:out value="${mensaje}"></c:out>
            </div>
        </c:if>
    </form:form>
</petclinic:layout>
