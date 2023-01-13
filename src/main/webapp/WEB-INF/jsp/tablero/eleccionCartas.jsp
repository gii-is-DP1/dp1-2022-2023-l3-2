<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>


<dwarf:layout pageName="Eleccion materiales">

<c:forEach items="${cartas}" var="carta">
        <a href="/partida/${id_partida}/eleccion-carta?carta=${carta.id}&username=${username}" ><img src="${carta.imagen}" height="224"
            width="160"/></a>
</c:forEach>
</dwarf:layout>
