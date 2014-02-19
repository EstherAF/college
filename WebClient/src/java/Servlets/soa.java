/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import com.cdyne.ws.ReturnIndicator;

/**
 *
 * @author Esther
 */
public class soa {

    public static java.util.List<services.Articulo> getCatalogShop() {
        services.CatalogService service = new services.CatalogService();
        services.Catalog port = service.getCatalogPort();
        return port.getCatalog();
    }

    public static double conversionRate(net.webservicex.Currency fromCurrency, net.webservicex.Currency toCurrency) {
        net.webservicex.CurrencyConvertor service = new net.webservicex.CurrencyConvertor();
        net.webservicex.CurrencyConvertorSoap port = service.getCurrencyConvertorSoap();
        return port.conversionRate(fromCurrency, toCurrency);
    }

    public static ReturnIndicator verifyEmail(java.lang.String email, java.lang.String licenseKey) {
        com.cdyne.ws.EmailVerNoTestEmail service = new com.cdyne.ws.EmailVerNoTestEmail();
        com.cdyne.ws.EmailVerNoTestEmailSoap port = service.getEmailVerNoTestEmailSoap();
        return port.verifyEmail(email, licenseKey);
    }

    static Boolean checkValidation(java.lang.String user, java.lang.String pass) {
        services.UserPassValidationService service = new services.UserPassValidationService();
        services.UserPassValidation port = service.getUserPassValidationPort();
        return port.checkValidation(user, pass);
    }
    
    
    
}
