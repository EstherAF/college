/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author Esther
 */
@Stateful( name = "Carro")
public class CarroSession implements CarroSessionLocalInteface {

    @EJB 
    private CatalogoSessionLocalInterface catalogoSession;
    
    ArrayList<ArticuloCompra> articulosComprados;
    Float finalPrice;

    public CarroSession() {
        articulosComprados = new ArrayList<ArticuloCompra>();
        finalPrice = new Float(0);
    }

    @Override
    public ArrayList<ArticuloCompra> getArticulosComprados() {
        return articulosComprados;
    }

    @Override
    public void setArticulosComprados(ArrayList<ArticuloCompra> articulosComprados) {
        this.articulosComprados = articulosComprados;
        calculateFinalPrice();
    }

    @Override
    public void setFinalPrice(Float finalPrice) {
        this.finalPrice = finalPrice;
    }

    @Override
    public Float getFinalPrice() {
        return finalPrice;
    }

    @Override
    public void añadirDisco(String nombreArticulo, Integer quantity) {

        ArticuloCompra articulo = null;

        /*Busco en el carrito si no está vacío*/
        if (!this.getArticulosComprados().isEmpty()) {
            articulo = this.searchArticuloCompra(nombreArticulo);
        }

        /*Si hay un articulo igual ya en el carrito...*/
        if (articulo != null) {
            articulo.setIncreaseQuantity(quantity); /*Incrementamos el numero de cds*/
            this.calculateFinalPrice();
        } else {

            Disco nuevoDisco = catalogoSession.getDisco(nombreArticulo);   /*Si no, lo creo*/
            articulo = new ArticuloCompra(nuevoDisco, quantity);
            this.añadirArticuloCompra(articulo);
        }

    }
    
    @Override
    public void añadirDisco(Disco disco, Integer quantity) {
        this.añadirDisco(disco.getName(), quantity);
    }

    @Override
    public void EliminarArticulo(String nombreArticulo) {
        ArticuloCompra articulo = this.searchArticuloCompra(nombreArticulo);
        this.eliminarArticuloCompra(articulo);
        this.calculateFinalPrice();
    }

    @Override
    public void QuitarUnidad(String nombreArticulo) {
        ArticuloCompra articulo = this.searchArticuloCompra(nombreArticulo);

        if (articulo != null) {
            if (articulo.getQuantity() > 1) {
                articulo.setDecreaseQuantity();
                this.calculateFinalPrice();
            } else {
                this.eliminarArticuloCompra(articulo);
            }
        }
    }

    /*Private*/
    private ArticuloCompra searchArticuloCompra(String name) {
        ArticuloCompra search = null;
        for (ArticuloCompra i : this.articulosComprados) {
            if (i.getDisco().getName().equals(name)) {
                search = i;
                break;
            }
        }
        return search;
    }
    
    private ArticuloCompra searchArticuloCompra(Disco disco) {
        ArticuloCompra search = this.searchArticuloCompra(disco.getName());
        return search;
    }

    private void calculateFinalPrice() {
        this.finalPrice = new Float(0);
        for (ArticuloCompra i : this.articulosComprados) {
            this.finalPrice += i.getTotalPrice();
        }
    }

    private void añadirArticuloCompra(ArticuloCompra articuloCompra) {
        this.getArticulosComprados().add(articuloCompra);
        this.setFinalPrice(this.getFinalPrice() + articuloCompra.getTotalPrice());
    }

    private void eliminarArticuloCompra(ArticuloCompra articuloCompra) {
        this.getArticulosComprados().remove(articuloCompra);
        this.calculateFinalPrice();
    }
}
