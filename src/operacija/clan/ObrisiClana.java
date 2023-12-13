/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operacija.clan;

import domen.Clan;
import operacija.GenerickaOperacija;

/**
 *
 * @author Nikola
 */
public class ObrisiClana extends GenerickaOperacija{
    
    boolean uspesno;

    @Override
    protected void preduslovi(Object param) throws Exception {
       
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        try{
            uspesno = skladiste.obrisi((Clan)param);
        }catch(Exception ex) {
            throw new Exception("Sistem ne moze da obrise clana");
        }
        
    }

    public boolean isUspesno() {
        return uspesno;
    }

    
    
    
}
