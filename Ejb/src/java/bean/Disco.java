/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

/**
 *
 * @author esther.alvarez0
 */
public class Disco implements java.io.Serializable{
    private String name;
    private Float price;

    public Disco(){
            name = new String();
            price = new Float(0);
    }

    public Disco(String name, Float price){
            this.name = name;
            this.price = price;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

}
