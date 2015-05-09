<%-- 
    Document   : login
    Created on : 30-oct-2012, 13:36:48
    Author     : esther.alvarez0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>  
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/style.css" rel="stylesheet" type="text/css" media="all">
        <link href="css/login.css" rel="stylesheet" type="text/css" media="all">
        <title>Tienda AOS</title>
    </head>
    <body>
        <header>
            
            <h2>Tienda</h2>
            <h3>Arquitecturas Orientadas a Servicios</h3>
        </header>
        
        <div id="middle">
            <div id="login">
                <h1>Acceso</h1>
                
                <form action="login.jsp" method="post" class="form_web">

                    <p><label>Usuario</label><input type="text" name="login"></p>
                    <p><label>Contraseña</label><input type="password" name="pass"></p>
                    <input class="buttom" type="submit" value="Entrar">
                    <span style="margin-left:5px;font-weight: bold;" >${errorLogin}</span>
                </form>
            </div>
        </div>
                
        <footer>
            
            
        </footer>        
    </body>
</html>
