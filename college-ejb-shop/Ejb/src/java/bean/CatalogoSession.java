/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Esther
 */
@Stateless(name = "Catalogo")
public class CatalogoSession implements CatalogoSessionLocalInterface {

    private List<Disco> discos;

    public CatalogoSession() {
        this.generarCatalogo();
    }

    @Override
    public List<Disco> getCatalogo() {
        if(discos==null)
            this.generarCatalogo();    
        else if (discos.isEmpty())    
            this.generarCatalogo();
        
        return this.discos;
    }

    @Override
    public void setCatalogo(List<Disco> catalogo) {
        this.discos = catalogo;
    }

    @Override
    public Disco getDisco(String nombre) {
        Disco found = null;

        for (Disco d : this.getCatalogo()) {
            if (nombre.equals(d.getName())) {
                found = d;
            }
        }

        return found;
    }
    
    private void generarCatalogo(){
        discos = new ArrayList<Disco>();
        discos.add(new Disco("Yuan | The Guo Brothers | China | $14.95", new Float(14.95)));
        discos.add(new Disco("Drums of Passion | Babatunde Olatunji | Nigeria | $16.95", new Float(16.95)));
        discos.add(new Disco("Kaira | Tounami Diabate| Mali | $16.95", new Float(16.95)));
        discos.add(new Disco("The Lion is Loose | Eliades Ochoa | Cuba | $13.95", new Float(13.95)));
        discos.add(new Disco("Dance the Devil Away | Outback | Australia | $14.95", new Float(14.95)));
        discos.add(new Disco("Record of Changes | Samulnori | Korea | $12.95", new Float(12.95)));
        discos.add(new Disco("Djelika | Tounami Diabate | Mali | $14.95", new Float(14.95)));
        discos.add(new Disco("Rapture | Nusrat Fateh Ali Khan | Pakistan | $12.95", new Float(12.95)));
        discos.add(new Disco("Cesaria Evora | Cesaria Evora | Cape Verde | $16.95", new Float(16.95)));
        discos.add(new Disco("DAA | GSTIC | Spain | $50.00", new Float(50.00)));
    }
}
