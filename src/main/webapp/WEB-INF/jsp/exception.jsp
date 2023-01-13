<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>

<dwarf:layout pageName="error">

    <spring:url value="/resources/images/saddwarf.jpg" var="dwarfImage"/>

    <center>
    <img src="${dwarfImage}" width="200" height="300"/>
    </center>

    <h2></h2>
    <h2 style="text-align: center;">Vaya, la has liado...</h2>

    <p>${exception.message}</p>

</dwarf:layout>
