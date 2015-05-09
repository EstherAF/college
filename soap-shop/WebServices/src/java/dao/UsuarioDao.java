/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Usuario;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 *
 * @author Esther
 */
public class UsuarioDao extends AbstractDao{
    
    public UsuarioDao(){
        super();
    }   
    
    public boolean existstUsuario(String username, String password) throws HibernateException
    {
        Boolean exists = false;

        try
        {
            iniciaOperacion();
            Query query = getSession().createSQLQuery("select * FROM Usuarios WHERE login = :nombreUsuario AND pass = :password");
            query.setParameter("nombreUsuario", username);
            query.setParameter("password", password);
            
            List lista = query.list();
            
            if(lista!=null){
                if(!lista.isEmpty()){
                    exists=true;
                }
            }
        }
        catch (HibernateException he)
        {
            System.out.println(he.getMessage());
            manejaExcepcion(he);
        }
        finally
        {
            terminaOperacion();
        }

        return exists;
    }
}
