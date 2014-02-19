/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Esther
 */
@Local
public interface CatalogoSessionLocalInterface {

    Disco getDisco(String nombre);

    List<Disco> getCatalogo();

    void setCatalogo(List<Disco> catalogo);
}
