<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>

<dwarf:layout pageName="user">

    <form:form modelAttribute="logro" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
            <h2>Nombre </h2>
            <input label="Nombre" name="name" placeholder="Es necesario poner un valor" value="" required="true" size="80px"/>
            <h2>Requisito</h2>
            <input label="requisito" name="requisito" placeholder="Es necesario poner un valor numerico positivo" value="0" required="true"size="80px"/>


        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            <button class="btn btn-default" type="submit">Add User</button>
            </div>
        </div>
    </form:form>
</dwarf:layout>
