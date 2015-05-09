/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Services;

import dao.UsuarioDao;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author esther.alvarez0
 */
@WebService()
public class UserPassValidation {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "checkValidation")
    public Boolean checkValidation(@WebParam(name = "user")
    String user, @WebParam(name = "pass")
    String pass) {
        UsuarioDao dao = new UsuarioDao();
       
        Boolean validation= false;
        if(dao.existstUsuario(user, pass)){
            validation=true;
        }

        return validation;
    }

}
