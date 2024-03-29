<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>


<dwarf:layout pageName="tablero">
    <style>
        .ficha {
            width: 80px;
            height: 80px;
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
        }
        .ronda_container {
            position: absolute;
            top: 200px;
            right: 20px;
            width: 200px;
            height: 100px;
            background-color: #34302d;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            border-radius: 5px;
            gap: 10px;
        }
        .turno_container {
            position: absolute;
            top: 350px;
            right: 20px;
            width: 200px;
            height: 100px;
            background-color: #34302d;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            border-radius: 5px;
            gap: 10px;
        }

        .enanos_container {
            position: absolute;
            top: 500px;
            right: 20px;
            width: 200px;
            min-height: 200px;
            background-color: #34302d;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            border-radius: 5px;
            gap: 10px;
        }

        .chat_container {
            position: absolute;
            top: 750px;
            right: 20px;
            width: 400px;
            min-height: 200px;
            background-color: #34302d;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            border-radius: 5px;
            gap: 10px;
            padding: 20px 0;
        }

        .chat_container>div {
            width: 90%;
            overflow-y: scroll;
            padding: 0 20px;
            height: 300px;
        }

        .chat_container label {
            display: none;
        }

        .chat_container h2 {
            color: white;
        }
        .chat_container p {
            color: white;
        }

        .ronda_container h2 {
            color: white;
            display: block;
            margin: 0;
            padding: 0;
        }
        .turno_container h2 {
            color: white;
            display: block;
            margin: 0;
            padding: 0;
        }

        .enanos_container h2 {
            color: white;
            display: block;
            text-align: center;
            padding: 0;
        }
        .enanos_container div {
            list-style: none;
            display: flex;
            flex-direction: column;
            align-items: flex-start;
            justify-content: center;
        }
        .enanos_container p {
            color: white;
            font-size: 16px;
            padding: 0;
        }

        .cartas-restantes-container {
            position: absolute;
            right: 0;
            top: 20%;
            transform: translate(0, -50%);
            width: 160px;
            background-color: #34302d;
            padding: 5px 0;
        }
        .cartas-restantes-container h2 {
            display: block;
            margin: auto;
            text-align: center;
            color: white;
        }

        .container-fin {
            position: fixed;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            width: 160px;
            background-color: #34302d;
            width: 800px;
            height: 500px;
            z-index: 9999;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            border-radius: 20px;
        }
        .container-fin :nth-child(1) {
            color: yellow;
            font-size: 60px;
            margin: 0 0 35px 0;
            padding: 0;
        }
        .container-fin :nth-child(2) {
            color: green;
            font-size: 50px;
            margin: 0 0 25px 0;
            padding: 0;
        }
        .container-fin :nth-child(3) {
            color: blue;
            font-size: 40px;
            margin: 0;
            padding: 0;
        }
        .container-fin input {
            color: white;
        }
        .blur {
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            width: 100%;
            height: 2490px;
            z-index: 9998;

            background: rgba(255, 255, 255, 0.2);
            border-radius: 16px;
            box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
            backdrop-filter: blur(5px);
            -webkit-backdrop-filter: blur(5px);
            border: 1px solid rgba(255, 255, 255, 0.3);
        }
        .button-fin {
            display: block;
            width: 200px;
            background: #4E9CAF;
            padding: 15px;
            text-align: center;
            border-radius: 5px;
            color: white;
            font-size: 20px;
            font-weight: bold;
            line-height: 25px;
            margin-top: 30px;
        }
        .button-fin:hover {
            color: rgb(0, 0, 0);
            cursor: pointer;
            text-decoration: none;
            transform: scale(1.1);
            transition: transform .1s;
        }
    </style>
    <h2>${partida.getFormattedDuration()}</h2>
    <c:if test="${jugadores.get(0).posicionFinal != null}">


        <div class="blur">

        </div>
        <div class="container-fin">
            <c:forEach items="${jugadoresOrdenados}" var="jugador">
                <h2>${jugador.posicionFinal}. ${jugador.getUser().username}</h2>
            </c:forEach>
            <a class="button-fin" href="/">
                Finalizar partida
            </a>
        </div>
    </c:if>
    <h1>${nombrePartida.toUpperCase()}</h1>
    <div class="ronda_container">
        <h2>RONDA</h2>
        <h2 style="font-size: 30px;">${ronda}</h2>
    </div>
    <div class="turno_container">
        <h2>TURNO</h2>
        <h2 style="font-size: 30px;">${jugadores.stream().filter(j -> j.turno).toList().get(0).getUser().username}</h2>
    </div>
    <div class="enanos_container">
        <h2>ENANOS DISPONIBLES</h2>
        <div>
            <c:forEach items="${jugadores}" var="jugador">
                <p>${jugador.getUser().username}: ${jugador.enanosDisponibles}</p>
            </c:forEach>
        </div>

    </div>
    <div class="chat_container">
        <h2>CHAT</h2>
        <div id="chat_lines">
            <!-- <c:forEach items="${chat}" var="chatLine">
                <p>(${chatLine.username}): ${chatLine.mensaje}</p>
            </c:forEach> -->
        </div>

        <form:form modelAttribute="chatLine" action="/partida/${id_partida}/chatline" class="form-horizontal" id="add-owner-form" >
            <div class="form-group has-feedback">
                <dwarf:inputField label="Mensaje" name="mensaje"/>
            </div>
            <div class="col-sm-offset-2 col-sm-10">

                <button class="btn btn-default" type="submit">Enviar</button>

            </div>
        </form:form>

    </div>
    <table id="tableroTable" class="table table-striped" style="width: 1100px;">
        <thead>
            <tr>
                <th style="width: 200px; text-align: center;">1</th>
                <th style="width: 200px; text-align: center;">2</th>
                <th style="width: 200px; text-align: center;">3</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <c:forEach items="${tablero1}" var="pos">

                        <td style="position: relative;">
                            <c:if test="${mazosConEnanoEncima.stream().filter(i -> pos.id.equals(i)).toList().size() != 0}">
                                <img class="ficha" src="/resources/images/Dimensionadas/${asociacionesColores.get(asociacionesUsernameMazo.get(pos.id))}.png">
                            </c:if>
                            <spring:url value="${pos.getFirstCarta().imagen}" var="dwarf" />
                            <a href="/partida/${id_partida}/coloca?username=${username}&posicion=${pos.posicion}">
                                <img class="img-responsive" src="${dwarf}" height="160"
                                    width="160" style="margin: auto;"/>
                            </a>
                        </td>

                </c:forEach>
            </tr>
            <tr>
                <c:forEach items="${tablero2}" var="pos">

                    <td style="position: relative;">
                        <c:if test="${mazosConEnanoEncima.stream().filter(i -> pos.id.equals(i)).toList().size() != 0}">
                            <img class="ficha" src="/resources/images/Dimensionadas/${asociacionesColores.get(asociacionesUsernameMazo.get(pos.id))}.png">
                        </c:if>
                        <spring:url value="${pos.getFirstCarta().imagen}" var="dwarf" />
                        <a href="/partida/${id_partida}/coloca?username=${username}&posicion=${pos.posicion}">
                            <img class="img-responsive" src="${dwarf}" height="160"
                                width="160" style="margin: auto;"/>
                        </a>
                    </td>

                </c:forEach>
            </tr>
            <tr>
                <c:forEach items="${tablero3}" var="pos">

                    <td style="position: relative;">
                        <c:if test="${mazosConEnanoEncima.stream().filter(i -> pos.id.equals(i)).toList().size() != 0}">
                            <img class="ficha" src="/resources/images/Dimensionadas/${asociacionesColores.get(asociacionesUsernameMazo.get(pos.id))}.png">
                        </c:if>
                        <spring:url value="${pos.getFirstCarta().imagen}" var="dwarf" />
                        <a href="/partida/${id_partida}/coloca?username=${username}&posicion=${pos.posicion}">
                            <img class="img-responsive" src="${dwarf}" height="160"
                                width="160" style="margin: auto;"/>
                        </a>
                    </td>
            </c:forEach>
            </tr>
            <tr>
                <c:forEach items="${tablero4}" var="pos">

                        <td style="position: relative;">
                            <c:if test="${mazosConEnanoEncima.stream().filter(i -> pos.id.equals(i)).toList().size() != 0}">
                                <img class="ficha" src="/resources/images/Dimensionadas/${asociacionesColores.get(asociacionesUsernameMazo.get(pos.id))}.png" >

                            </c:if>
                            <spring:url value="${pos.getFirstCarta().imagen}" var="dwarf" />
                            <a href="/partida/${id_partida}/coloca?username=${username}&posicion=${pos.posicion}">
                                <img class="img-responsive" src="${dwarf}" height="160"
                                    width="160" style="margin: auto;"/>
                            </a>
                        </td>

                </c:forEach>
                </tr>
        </tbody>
    </table>
    <table>
        <tbody>
            <tr>
                <c:forEach items="${jugadores}" var="jugador">
                <td>

                    <ul>
                        <c:if test="${jugador.primerjugador}">
                            <li style="list-style-type: none;">
                                <img src="/resources/images/FichaDeInicio.png" height="18" width="20"/>
                                <c:out value="${jugador.user.username}" />
                            </li>
                        </c:if>
                        <c:if test="${!jugador.primerjugador}">
                            <li style="list-style-type: none;">
                                <img src="/resources/images/FichaDeInicioVacia.png" height="18" width="20"/>
                                <c:out value="${jugador.user.username}" />
                            </li>
                        </c:if>
                        <li style="list-style-type: none;">
                            <img src="/resources/images/HierroRecortado.png" height="20" width="20"/>
                            Hierro:
                            <c:out value="${jugador.hierro}" />
                        </li>
                        <li style="list-style-type: none;">
                            <img src="/resources/images/MedallaRecortada.png" height="20" width="20"/>
                            Medallas:
                            <c:out value="${jugador.medalla}" />
                        </li>
                        <li style="list-style-type: none;">
                            <img src="/resources/images/AceroRecortado.png" height="20" width="20"/>
                            Acero:
                            <c:out value="${jugador.acero}" />
                        </li>
                        <li style="list-style-type: none;">
                            <img src="/resources/images/OroRecortado.png" height="20" width="20"/>
                            Oro:
                            <c:out value="${jugador.oro}" />
                        </li>
                        <li style="list-style-type: none;">
                            <img src="/resources/images/ObjetoRecortado.png" height="16" width="20"/>
                            Objetos:
                            <c:out value="${jugador.objeto}" />
                        </li>
                    </ul>

                </td>
            </c:forEach>
            <br/>
                <td style="position: relative;">
                    <spring:url value="/resources/images/Dimensionadas/000.png" var="img"/>
                    <img src="${img}" height="223"
                    width="160" style="margin-left: 80px;"/>
                    <div class="cartas-restantes-container">
                        <h2>${cartasRestantesBaraja}</h2>
                    </div>
                </td>
            </tr>


        </tbody>
    </table>
    <script>
        // Scroll chat hacia abajo por defecto
        var objDiv = document.getElementById("chat_lines");
        objDiv.scrollTop = objDiv.scrollHeight;
    </script>

    <script>
        function renderChat() {

            const loggedUsername = document.querySelector("#main-navbar > ul.nav.navbar-nav.navbar-right > li > a > strong").textContent;

            const idPartida = [[${id_partida}]]

            fetch('http://localhost:8080/partida/' + idPartida + '/chatline')
                .then(res => res.json())
                .then(obj => {
                    chatLines1 = obj.messages;

                    const chat = document.getElementById("chat_lines");
                    let htmlString = "";
                    for(const chatLine1 of chatLines1) {
                        if(chatLine1.username !== loggedUsername) {
                            htmlString += "<p style=\"text-align: right\">(" + chatLine1.username + "): " + chatLine1.message + "</p>";
                            continue
                        }
                        htmlString += "<p>(" + chatLine1.username + "): " + chatLine1.message + "</p>";
                    }
                    chat.innerHTML = htmlString;

                    chat.scrollTop = chat.scrollHeight;
                })


        }
        renderChat();
        setInterval('renderChat()', 500);
    </script>

    <script>
        // Si hacemos focus en el input del chat bloqueamos el refresh
        const chatInput = document.getElementsByName("mensaje")[0]
        chatInput.addEventListener('focus', (event) => {
            window.stop()
        });
        chatInput.addEventListener('blur', (event) => {
            location.reload()
        });
    </script>

</dwarf:layout>
