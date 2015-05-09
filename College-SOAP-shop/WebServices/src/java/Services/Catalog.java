/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Services;

import dao.ArticuloDao;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import model.Articulo;

/**
 *
 * @author esther.alvarez0
 */
@WebService()
public class Catalog {

    /**
     * Web service operation
     */
    private ArticuloDao articuloDao;
    
    @WebMethod(operationName = "getCatalog")
    public List<Articulo> getCatalogEuros() {

        articuloDao  = new ArticuloDao();
        
        List<Articulo> Articulos = articuloDao.listArticulos();

        return Articulos;
        
    }
}
