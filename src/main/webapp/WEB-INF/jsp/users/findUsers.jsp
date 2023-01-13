<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->

<dwarf:layout pageName="owners">

    <h2>Busqueda de usuarios</h2>


    <form:form modelAttribute="user" action="/users/find" method="post" class="form-horizontal"
               id="search-jugador-form">
        <div class="form-group">
            <div class="control-group" id="username">
                <label class="col-sm-2 control-label">Username </label>
                <div class="col-sm-10">
                    <input class="form-control" label="Username" name="username" required="true" minlength="3" maxlength="50"/>
                    <span class="help-inline"><form:errors path="*"/></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Buscar usuario</button>
            </div>
            <a href="/user?page=0">
                <div class="col-sm-offset-2 col-sm-10">
                    <div class="btn btn-default">Buscar todos los usuarios</div>
                </div>
            </a>
        </div>

    </form:form>

    <c:if test="${mensaje != null}">
        <div class="alert alert-danger" role="alert">
            ${mensaje}
        </div>
    </c:if>

</dwarf:layout>
