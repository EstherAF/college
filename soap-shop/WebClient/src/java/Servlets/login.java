/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author esther.alvarez0
 */
@WebServlet(name="login", urlPatterns={"/login.jsp"})
public class login extends HttpServlet {
   
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher dispatcher;

        String login = request.getParameter("login");
        String pass = request.getParameter("pass");

        //llamada a servicio web
        Boolean validacion = soa.checkValidation(login, pass);
        
        
        if(!validacion){    //Si validación incorrecta, se muestra error en login.jsp
            String errorLogin="Los datos introducidos no son correctos";
            request.setAttribute("errorLogin",errorLogin);
            dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            
        }else{  //Si validacion correcta, se muestra el catalogo
             dispatcher = getServletContext().getRequestDispatcher("/catalogo.jsp");
        }

        dispatcher.forward(request, response);
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
