/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author Esther
 */
@Local
public interface CarroSessionLocalInteface {

    public ArrayList<ArticuloCompra> getArticulosComprados();
    
    /*Calcula el precio total del carro tras introducir los artículos*/
    public void setArticulosComprados(ArrayList<ArticuloCompra> articulosComprados);

    public void setFinalPrice(Float finalPrice);

    public Float getFinalPrice();

    /* Añade las unidades del artículo introducido al carro, 
     * creando dicho articulo en el carro si es necesario.
     * Calcula el precio final*/
    public void añadirDisco(Disco disco, Integer quantity);
    public void añadirDisco(String nombreArticulo, Integer quantity);

    /* Elimina el artículo del carro, y recalcula el precio final*/
    public void EliminarArticulo(String nombreArticulo);

    /* Elimina una unidad de un arículo del carro, eliminándolo si llega a 0.
     * Recalcula el precio final*/
    public void QuitarUnidad(String nombreArticulo);

}
