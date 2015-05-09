/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

/**
 *
 * @author esther.alvarez0
 */
public class ArticuloCompra implements java.io.Serializable {

    private Disco disco;
    private Integer quantity;
    private Float totalPrice;

    public ArticuloCompra(){
            disco = null;
            quantity = new Integer(0);
            totalPrice = new Float(0);
    }

    public ArticuloCompra(Disco disco, Integer quantity){
            this.disco = disco;
            this.quantity = quantity;
            this.calculateTotalPrice();
    }

    public Disco getDisco() {
        return disco;
    }

    public void setDisco(Disco disco) {
        this.disco = disco;
        calculateTotalPrice();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        calculateTotalPrice();
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    /*Otros métodos*/
    public void setDecreaseQuantity(){
        this.setQuantity(this.getQuantity()-1);
        calculateTotalPrice();
    }

    public void setIncreaseQuantity(){
        this.setQuantity(this.getQuantity()+1);
        calculateTotalPrice();
    }
    
    public void setIncreaseQuantity(Integer add){
        this.setQuantity(this.getQuantity()+add);
        calculateTotalPrice();
    }

    private void calculateTotalPrice(){
        this.setTotalPrice(this.disco.getPrice()*this.quantity);
    }
}
