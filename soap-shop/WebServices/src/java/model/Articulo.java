/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

public class Articulo implements Serializable {
    
    private Double precio_E;
    private String id;
    private String nombre;
    private String descripcion;

    public Articulo(Double precio_E, String id, String nombre, String descripcion) {
        this.precio_E = precio_E;
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Articulo(Double precio_E, String nombre) {
        this.precio_E = precio_E;
        this.nombre = nombre;
    }

    public Articulo() {
        this.precio_E = new Double(0);
        this.nombre = new String();
    }

    
    public Double getPrecio_e() {
        return precio_E;
    }

    public void setPrecio_e(Double precio_E) {
        this.precio_E = precio_E;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
}
