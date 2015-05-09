/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aostienda_prueba;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.webservicex.Currency;
import services.Articulo;

/**
 *
 * @author Esther
 */
public class AOSTienda_prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String user, pass;
        Boolean validacion=false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        do{
            try {
                System.out.print("Usuario: ");
                user = br.readLine();

                System.out.print("Contraseña:  ");
                pass=br.readLine();

                System.out.println("Validando...");
                validacion = soa.checkValidation(user, pass);
                if(!validacion)
                    throw new IOException();
                else
                    System.out.println("Validado '"+user+"'");
            } catch (IOException e) {
                System.out.println("Validacion incorrecta");
            }
        }while(!validacion);
        
        Currency moneda;
        
        try {
            System.out.println();
            System.out.println("Monedas disponibles");
            Currency[] monedas = Currency.values();
            for(Currency m:monedas){
                System.out.println(m.toString());
            }
            
            System.out.println("Selecciona la moneda en la que se mostrarán los precios: ");
            String seleccion = br.readLine();
            moneda = Currency.valueOf(seleccion);
            
            
        } catch (IOException ex){
            System.out.println("La moneda no existe, por defecto Euros");
            moneda = Currency.EUR;
        } catch(IllegalArgumentException ex2){
            System.out.println("La moneda no existe, por defecto Euros");
            moneda = Currency.EUR;
        }
        
        Double rate = soa.conversionRate(Currency.EUR, moneda);
        
        System.out.println();
        System.out.println("-----Catálogo-----");
        System.out.println("Id\tNombre\tDescripcion\tPrecio "+moneda.name());
        List<Articulo> articulos = soa.getCatalog();
        
        for(Articulo a:articulos){
            System.out.println(a.getId()+"\t"+a.getNombre()+"\t"+a.getDescripcion()+"\t"+a.getPrecioE()*rate);
        }
        
    }
}
