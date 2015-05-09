/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Articulo;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 *
 * @author Esther
 */
public class ArticuloDao extends AbstractDao {
    
    public ArticuloDao(){
        super();
    }
    
    public List<Articulo> listArticulos() throws HibernateException{
        List<Articulo> articulos = null;

        try
        {
            iniciaOperacion();
            Query query = getSession().createSQLQuery("SELECT * FROM Articulos").addEntity(model.Articulo.class);
            articulos = query.list();
        }
        catch (HibernateException he)
        {
            manejaExcepcion(he);
        }
        finally
        {
            terminaOperacion();
        }
        
        return articulos;
    }
}
