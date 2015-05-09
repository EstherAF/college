package Servlets;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/*import emailVerification.EmailVerNoTestEmail;
import emailVerification.ReturnIndicator;*/
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import services.Articulo;

/**
 *
 * @author esther.alvarez0
 */
@WebServlet(name = "catalogo", urlPatterns = {"/catalogo.jsp"})
public class catalogo extends HttpServlet {    
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //Consulta al catálogo
        List<Articulo> articulos = soa.getCatalogShop();
           
        //Conversión de moneda
        String moneda;
        if(request!=null){
            moneda = request.getParameter("moneda");
            if(moneda==null){
                   moneda="euro";
            }
        }else{
            moneda="euro";
        }
        
        Double conversionRate=null;
        if(moneda.equals("dolar")){
            conversionRate=soa.conversionRate(net.webservicex.Currency.EUR, net.webservicex.Currency.USD);
            for(Articulo a:articulos ){
                a.setPrecioE(new Double(a.getPrecioE()*conversionRate));
            }
        }

        //Conservar verificacion Email
        Object verificacionEmail = request.getAttribute("verificacionEmail");
        String email;
        if(verificacionEmail==null)
            email=new String();
        else
            email=verificacionEmail.toString();
        
        
        
        request.setAttribute("articulos", articulos);
        request.setAttribute("moneda", moneda);
        request.setAttribute("verificacionEmail",email);
  
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/tienda.jsp");
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
    }// </editor-fold>
    
}
