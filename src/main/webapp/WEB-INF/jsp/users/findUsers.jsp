<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->

<petclinic:layout pageName="owners">

    <h2>Find Users</h2>


    <form:form modelAttribute="user" action="/users/find" method="post" class="form-horizontal"
               id="search-jugador-form">
        <div class="form-group">
            <div class="control-group" id="username">
                <label class="col-sm-2 control-label">Username </label>
                <div class="col-sm-10">
                    <!-- <form:input class="form-control" path="username" size="30" maxlength="80"/> -->
                    <input class="form-control" label="Username" name="username" required="true" minlength="3" maxlength="50"/>
                    <span class="help-inline"><form:errors path="*"/></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Find User</button>
            </div>
            <a href="/user?page=0">
                <div class="col-sm-offset-2 col-sm-10">
                    <div class="btn btn-default">Find All Users</div>
                </div>
            </a>
        </div>

    </form:form>

    <c:if test="${mensaje != null}">
        <div class="alert alert-danger" role="alert">
            ${mensaje}
        </div>
    </c:if>

    <br/>
    <sec:authorize access="hasAuthority('admin')">
		<a class="btn btn-default" href='<spring:url value="/jugador/new" htmlEscape="true"/>'>Add Owner</a>
	</sec:authorize>

</petclinic:layout>
