/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Esther
 */
public abstract class AbstractDao {
    private Session session = null;
    
    public AbstractDao(){
        try{
            session = HibernateUtil.getSessionFactory().openSession();
        }catch(HibernateException e){
            System.out.println("Erroooooooor: "+e.getMessage());
        }
    }
    
    protected void iniciaOperacion()
    {
        session.getTransaction().begin();
    }

    protected void terminaOperacion()
    {
        session.getTransaction().commit();
        session.close();
    }

    protected void manejaExcepcion(HibernateException he) throws HibernateException
    {
        System.out.println(he.getLocalizedMessage());
        session.getTransaction().rollback();
        throw he;
    }

    protected Session getSession()
    {
        return session;
    }
}
