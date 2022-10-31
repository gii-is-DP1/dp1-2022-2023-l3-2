<%@ page session="false" trimDirectiveWhitespaces="true" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
                <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
                    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
                        <%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

                            <petclinic:layout pageName="tablero">
                                <h2>Tablero</h2>
                                <c:forEach items="${tablero1}" var="pos">
                                    <tr>

                                        <td>
                                            <spring:url value="${pos.posicion}" var="dwarf"/>
                                            <img class="img-responsive" src="${dwarf}" height="160" width="160"/>
                                        </td>

                                    </tr>
                                    
                                </c:forEach>
                                <br/>
                                <c:forEach items="${tablero2}" var="pos">
                                    <tr>
                                        
                                        <td>

                                            <spring:url value="${pos.posicion}" htmlEscape="true" var="dwarf"/>
                                            <img class="img-responsive" src="${dwarf}" height="160" width="160"/>
                                        </td>

                                    </tr>
                                
                                </c:forEach>
                                <br/>
                                <c:forEach items="${tablero3}" var="pos">
                                    <tr>

                                        <td>
                                            <spring:url value="${pos.posicion}" htmlEscape="true" var="dwarf"/>
                                            <img class="img-responsive" src="${dwarf}" height="160" width="160"/>
                                        </td>

                                    </tr>
                                    
                                </c:forEach>
                            </petclinic:layout>