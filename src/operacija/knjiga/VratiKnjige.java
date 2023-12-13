/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operacija.knjiga;

import domen.Knjiga;
import java.util.ArrayList;
import operacija.GenerickaOperacija;

/**
 *
 * @author Nikola
 */
public class VratiKnjige extends GenerickaOperacija{
    
    ArrayList<Knjiga> lista;

    @Override
    protected void preduslovi(Object param) throws Exception {
        
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        lista = skladiste.vratiSve((Knjiga)param, null);
    }

    public ArrayList<Knjiga> getLista() {
        return lista;
    }
    
    
    
}
