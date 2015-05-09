<%@page import="java.text.DecimalFormat"%>
<%@page import="services.Articulo"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/style.css" rel="stylesheet" type="text/css" media="all">
        <link href="css/catalogo.css" rel="stylesheet" type="text/css" media="all">
        <title>Tienda AOS</title>
    </head>
    <body>
        <header>
            
            <h2>Tienda</h2>
            <h3>Arquitecturas Orientadas a Servicios</h3>
           
        </header>
        
        <div id="middle">
            <div id="content">
            
                <div id="email" class="entrada">
                   <label>Valida tu email</label>
                   <div class="text">
                        <form action="verificar_email.jsp" method="post" class="form_web">

                            <input type="text" name="email">
                            <input class="buttom" type="submit" value="Validar email">
                            <input type="hidden" name="moneda" value="${moneda}">
                            <span style="margin-left:5px;font-weight: bold;" >${verificacionEmail}</span>
                        </form>   
                    </div>
                </div>


               <div id="shop" class="entrada">
                   <label>Productos disponibles</label>
                   <div class="text">
                        <form action="catalogo.jsp" method="post" class="form_web">
                            <SELECT NAME="moneda">
                            <option <%if (request.getAttribute("moneda").equals("euro")) {%>selected<%}%> 
                                    value="euro">Euros</option>
                            <option <%if (request.getAttribute("moneda").equals("dolar")) {%>selected<%}%> 
                                    value="dolar">Dolar</option>
                            </SELECT>
                            <input class="buttom "type="submit" value="Cambiar de moneda">
                        </form>

                        <table>
                            <tr><TH class="id">Id</TH><TH class>Nombre</TH><TH>Descripción</TH><TH>Importe</TH></tr>
                            <%DecimalFormat df = new DecimalFormat("0.00"); 
                            for (Articulo articulo : (ArrayList<Articulo>) request.getAttribute("articulos")) { %>
                                <tr><td><%= articulo.getId() %></td>
                                    <td><%= articulo.getNombre() %></td>
                                    <td><%= articulo.getDescripcion() %></td>
                                    <td><%= df.format(articulo.getPrecioE()) %><%if (request.getAttribute("moneda").equals("euro")) {%> €  <%}else{%> $ <%}%></td>
                                </tr>
                            <%}%>
                        </table>
                     </div>
               </div>
            </div>
        </div>
            
                        
        <footer>
            
            
        </footer>     
    </body>
</html>
