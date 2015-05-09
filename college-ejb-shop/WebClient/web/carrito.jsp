<%-- 
    Document   : carrito
    Created on : 21-feb-2013, 16:53:07
    Author     : esther.alvarez0
--%>

<%@page import="bean.ArticuloCompra"%>
<%@page import="bean.Disco"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Musica para DAA</title>
    </head>
    <body bgcolor="#FDF5E6">

        <!--Carrito-->

        <center>
            <font face="Times New Roman,Times" size="+3">Carrito de la compra</font>
        </center>
        <hr>
        <p><center>

            <form action="MainController" method="GET">
                <table>
                    <tr>
                        <th>TITULO DEL CD</th><th>Cantidad</th><th>Importe</th><th>Selección</th>
                    </tr>

                    <c:forEach items="${carrito}" var="articulo">
                        <tr style="text-align: left;">
                            <td>
                                ${articulo.disco.name}
                            </td><td>
                                ${articulo.quantity}
                            </td><td>
                                ${articulo.totalPrice}
                            </td><td style="text-align: center;">
                                <input type="radio" name="nameArticulo" value="${articulo.disco.name}">                                        
                            </td>
                        </tr>    
                    </c:forEach>

                    <tr>
                        <td></td>
                        <td>Importe total</td><td>${finalPrice}</td>
                        <td></td>
                    </tr><tr>
                        <td colspan="3"></td>
                        <td>
                            <c:if test="${carrito.size() != 0}">
                                <input style="width: 100px;" name="action" type="submit" value="Quitar uno"><br>
                                <input style="width: 100px;" name="action" type="submit" value="Eliminar">
                            </c:if>
                        </td>
                    </tr>

                </table>

            </form>
            <form action="index.jsp">            
                <input style="width: 200px; height: 40px;" name="boton" type="submit" value="Seguir comprando"><br>
            </form>
            <c:if test="${carrito.size() != 0}">
                <form action="MainController">            
                    <input style="width: 200px; height: 40px;" name="action" type="submit" value="Ver Factura Final"><br>
                </form>
            </c:if>
        </center>
        <hr>
    </body>
</html>
