<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="Eleccion materiales">

    <a href="/partida/${id_partida}/eleccion-material?material=oro&username=${username}"><button>Oro</button></a>
    <a href="/partida/${id_partida}/eleccion-material?material=hierro&username=${username}"><button>Hierro</button></a>
    <a href="/partida/${id_partida}/eleccion-material?material=acero&username=${username}"><button>Acero</button></a>

</petclinic:layout>
