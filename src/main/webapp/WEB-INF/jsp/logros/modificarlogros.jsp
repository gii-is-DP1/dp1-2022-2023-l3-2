<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="logro">
    <h2>Logros</h2>
    <form:form modelAttribute="logro" class="form-horizontal" id="add-logro-form">
        <div class="form-group has-feedback">
            

            <p>${logros.name}</p><br/>
            <petclinic:inputField label="Name" name="logro.name" />            
  
            <p>${logros.descripcion}</p><br/>
            <petclinic:inputField label="Descripcion" name="logro.descripcion" />           
            <p>${logros.dificultad}</p><br/>
            <petclinic:inputField label="Dificultad" name="logro.dificultad" />            
            <p>${logros.requisito}</p><br/>
            <petclinic:inputField label="Requisito" name="logro.requisito" />            
            <p>${logros.tipo}</p>
            <petclinic:inputField label="Tipo" name="logro.tipo" />
          
            
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            <button class="btn btn-default" type="submit">Modificar Logro</button>
            </div>
        </div>
    </form:form>

</petclinic:layout>
