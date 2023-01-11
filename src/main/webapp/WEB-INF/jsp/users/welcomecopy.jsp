<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="usuario">
    <h2>Perfil personal</h2>


                    <a href="/users/${perfil}" class="btn btn-success"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> Perfil</a>

                    <div class="alert alert-primary" role="alert">
                        <c:forEach items="${juegos}" var="juego">

                            <div class="alert alert-success" role="alert">
                                Se te ha invitado a un <a href="/lobby/${juego.lobbyId}">lobby</a>
                            </div>
                        </c:forEach>

                      </div>
                      <div class="alert alert-primary" role="alert">
                          <c:forEach items="${amigos}" var="notify">
                              <div class="alert alert-info" role="alert">
                                Te ha agregado <a href="/users/${notify.userenvia.username}">${notify.userenvia.username}</a> como amigo
                              </div>
                          </c:forEach>

                        </div>
    <h2>Ranking de usuarios</h2>


    <table id="jugadoresTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Username</th>
            <th style="width: 150px;">Puntuacion</th>
        </tr>
        </thead>
        <tbody>

            <c:forEach items="${usuarios}" var="usuario">
                <tr>
                <td>
                    ${usuario.username}
                </td>
                <td>
                    <c:out value="${usuario.estadistica.puntos}"></c:out>
                </td>

                </tr>
            </c:forEach>




        </tbody>
    </table>
    <style>
        .boton-pagina {
            margin: 0 auto;
            width: 100%;
            height: 100%;
            background-color: #34302D;
            outline: solid #6db33f;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
        }

        .boton-pagina-actual {
            margin: 0 auto;
            width: 100%;
            height: 100%;
            background-color: #6db33f;
            outline: solid #6db33f;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
        }

        .boton-pagina:hover {
            transform: scale(1.1);
            transition: transform .2s;
        }
    </style>

    <div style="display: flex; flex-direction: row; margin: 0 auto; width: fit-content; gap:15px;">
        <c:if test="${paginaActual != 0}">
            <a href="/users?page=${paginaActual-1}", style="width: 50px; height: 50px; text-decoration: none;" >
                <div class="boton-pagina">
                    <
                </div>
            </a>
        </c:if>
        <c:forEach items="${paginas}" var="pageNumber">

            <a href="/users?page=${pageNumber}", style="width: 50px; height: 50px; text-decoration: none;" >
                <c:if test="${paginaActual == pageNumber}">
                    <div class="boton-pagina-actual">
                        ${pageNumber}
                    </div>
                </c:if>

                <c:if test="${paginaActual != pageNumber}">
                    <div class="boton-pagina">
                        ${pageNumber}
                    </div>
                </c:if>
            </a>
        </c:forEach>
        <c:if test="${paginaActual != paginas.size() - 1}">
            <a href="/users?page=${paginaActual+1}", style="width: 50px; height: 50px; text-decoration: none;" >
                <div class="boton-pagina">
                    >
                </div>
            </a>
        </c:if>
    </div>
</petclinic:layout>
