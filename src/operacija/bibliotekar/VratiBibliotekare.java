/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operacija.bibliotekar;

import domen.Bibliotekar;
import java.util.List;
import operacija.GenerickaOperacija;

/**
 *
 * @author Nikola
 */
public class VratiBibliotekare extends GenerickaOperacija{
    
    List<Bibliotekar> bibliotekari;
    Bibliotekar bibliotekar;

    @Override
    protected void preduslovi(Object param) throws Exception {
        
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        bibliotekari = skladiste.vratiSve((Bibliotekar)param, null);
    }

    public List<Bibliotekar> getBibliotekari() {
        return bibliotekari;
    }
    
    
    
}
