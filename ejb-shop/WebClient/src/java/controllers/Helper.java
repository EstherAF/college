/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Esther
 */
public class Helper {

    static public Float extractPrice(String descripcionCD) {
        String precioString;

        StringTokenizer t = new StringTokenizer(descripcionCD, "|");
        t.nextToken();
        t.nextToken();
        t.nextToken();
        precioString = t.nextToken();
        precioString = precioString.replace('$', ' ').trim();

        Float price = Float.parseFloat(precioString);
        return price;
    }
    
    static public String getAction(HttpServletRequest request){
        Object reqAction = request.getParameter("action");
        String action;
        
        if(reqAction != null){
             action = reqAction.toString();
        }else{
            action = "null";
        }
        
        return action;
    }
}
