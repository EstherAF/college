/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aostienda_prueba;

/**
 *
 * @author Esther
 */
public class soa {


    static java.util.List<services.Articulo> getCatalog() {
        services.CatalogService service = new services.CatalogService();
        services.Catalog port = service.getCatalogPort();
        return port.getCatalog();
    }

    static double conversionRate(net.webservicex.Currency fromCurrency, net.webservicex.Currency toCurrency) {
        net.webservicex.CurrencyConvertor service = new net.webservicex.CurrencyConvertor();
        net.webservicex.CurrencyConvertorSoap port = service.getCurrencyConvertorSoap();
        return port.conversionRate(fromCurrency, toCurrency);
    }

    static Boolean checkValidation(java.lang.String user, java.lang.String pass) {
        services.UserPassValidationService service = new services.UserPassValidationService();
        services.UserPassValidation port = service.getUserPassValidationPort();
        return port.checkValidation(user, pass);
    }
    
}
