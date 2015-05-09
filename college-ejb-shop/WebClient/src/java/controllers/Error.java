/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author Esther
 */
public enum Error {

    /**
     *
     */
    ACCION_NO_RECONOCIDA("La acción introducida no está reconocida"),
    NUMERO_INVALIDO("El valor introducido no es un número válido"),
    ERROR_DESCONOCIDO("Ocurrió un error desconocido");
    
    private String message;
    
    private Error(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message=message;
    }

    public void setArgs(String[] args) {
        if(args!=null){
            if(args.length!=0){
                message+= ": ";
                for(String arg: args){
                    message+= arg + " ";
                }
            }
        }
        
        message+= "\n";
    }
}
