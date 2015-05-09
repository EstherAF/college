/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import bean.CarroSession;
import bean.CarroSessionLocalInteface;
import bean.CatalogoSession;
import bean.CatalogoSessionLocalInterface;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author esther.alvarez0
 */
@WebServlet(name = "MainController", urlPatterns = {"/index.jsp", "/MainController"})
public class MainController extends HttpServlet {

    @EJB private CatalogoSessionLocalInterface catalogoSession;
    
    @EJB private CarroSessionLocalInteface carroSession;
    
    /*Procesar petición*/
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /*Recoger la acción a realizar*/
        String action = Helper.getAction(request);
        
        
        if(action.equals("null")){
            /*Página inicial*/
            goToPage("/catalogo.jsp", request, response);
        
        }else if(action.equals("Ver Factura Final")){
            /*Ver factura*/
            goToPage("/factura.jsp", request, response);

        }else if(action.equals("Pagar y Salir")){
            /*Eliminar sesión*/
            request.getSession(true).invalidate();
            goToPage("/catalogo.jsp", request, response);
            
        }else if(action.equals("Selecciona Producto")) {
            //Almacenar datos entrada
            String nameArticulo = request.getParameter("nameArticulo").toString();
            String sQuantity = request.getParameter("quantity");
            
            try{
                Integer quantity = Integer.parseInt(sQuantity);
                if(quantity<1)
                    throw new NumberFormatException();

                //procesamiento
                carroSession.añadirDisco(nameArticulo, quantity);

                //Datos de salida
                goToPage("/carrito.jsp", request, response);
            }
            catch(NumberFormatException e){
                request.setAttribute("Error", Error.NUMERO_INVALIDO+": "+sQuantity);
                goToPage("/error.jsp", request, response);
            }
            
           
        } else if(action.equals("Ver carrito")){
            
            goToPage("/carrito.jsp", request, response);
            
        } else if(action.equals("Quitar uno")){
            String nameArticulo = (String) request.getParameter("nameArticulo");
            
            //Procesamiento
            carroSession.QuitarUnidad(nameArticulo);
            
            goToPage("/carrito.jsp", request, response);
            
        } else if(action.equals("Eliminar")){
            String nameArticulo = (String) request.getParameter("nameArticulo");
            if(nameArticulo!=null)
                //Procesamiento
                 carroSession.EliminarArticulo(nameArticulo);
            
            goToPage("/carrito.jsp", request, response);
            
        } 
        else {
            request.setAttribute("Error", Error.ACCION_NO_RECONOCIDA+": "+action);
            goToPage("/error.jsp", request, response);
        }
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

    private void goToPage(String page, HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException
    {
        if(page.equals("/catalogo.jsp"))
            request.setAttribute("catalogo", catalogoSession.getCatalogo());
        
        else if(page.equals("/carrito.jsp")){
            
            request.setAttribute("carrito", this.carroSession.getArticulosComprados());
            request.setAttribute("finalPrice", this.carroSession.getFinalPrice());
        }
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    public CatalogoSessionLocalInterface getCatalogoSession() {
        return catalogoSession;
    }

    public void setCatalogoSession(CatalogoSessionLocalInterface catalogoSession) {
        this.catalogoSession = catalogoSession;
    }

    public CarroSessionLocalInteface getCarroSession() {
        return carroSession;
    }

    public void setCarroSession(CarroSessionLocalInteface carroSession) {
        this.carroSession = carroSession;
    }
    
    
    

}
