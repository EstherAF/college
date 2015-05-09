<%-- 
    Document   : index
    Created on : 21-feb-2013, 16:10:53
    Author     : esther.alvarez0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

  <html>
    <head>
      <title>Musica para DAA</title>
    </head>
    <body bgcolor="#FDF5E6">
          <center>
              <font face="Times New Roman,Times" size="+3">Música para DAA</font>
          </center>
      <hr>
	<p>
	  <center>
              
	    <form action="MainController" method="GET">
	      
              <b>CD:</b>
	      
              <select name="nameArticulo">
                  <c:forEach items="${catalogo}" var="disco">
                        <option value="${disco.name}">${disco.name}</option>
                  </c:forEach>
	      </select>
	      
              <b>Cantidad:</b>
	      
              <input type="text" name="quantity" value="1">
              
              <p><center><input style="width: 200px; height: 40px;" type="submit" name="action" value="Selecciona Producto" /></center>
              <center><input style="width: 200px; height: 40px;" type="submit" name="action" value="Ver carrito" /></center></p>
	    </form>
              
	  </center>
	  <hr>
    </body>
</html>