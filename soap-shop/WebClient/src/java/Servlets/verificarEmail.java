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
@WebServlet(name = "verificarEmail", urlPatterns = {"/verificar_email.jsp"})
public class verificarEmail extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String email = request.getParameter("email");
        String validation=new String();
        if(email!=null){
            validation="No válido";
            if(!email.isEmpty()){
                if(soa.verifyEmail(email, "").isGoodEmail()) {
                    validation="Válido";
                }
            }
        }

        request.setAttribute("verificacionEmail", validation);
        request.setAttribute("email", email);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/catalogo.jsp");  //para generar la lista
        dispatcher.forward(request, response);
    }

    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
}
