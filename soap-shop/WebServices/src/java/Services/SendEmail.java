/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


/**
 *
 * @author Esther
 */
@WebService(serviceName = "SendEmail")
public class SendEmail {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "sendEmail")
    public Boolean sendEmail(@WebParam(name = "recipient") String recipient, @WebParam(name = "subject") String subject, @WebParam(name = "text") String text) {

        try{
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
                props.setProperty("mail.smtp.starttls.enable", "true");
                props.setProperty("mail.smtp.port", "587");
                props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);
            session.setDebug(true);

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("alvarezfeijoo.esther@gmail.com"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            msg.setSubject(subject);
            msg.setText(text);

            Transport t = session.getTransport("smtp");
            t.connect("alvarezfeijoo.esther@gmail.com","prueba1234");
            t.sendMessage(msg,msg.getAllRecipients());
            return true;
        } catch(Exception f){
            return false;
        }
    }
}

    

