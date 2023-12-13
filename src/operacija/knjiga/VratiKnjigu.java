/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operacija.knjiga;

import domen.Knjiga;
import operacija.GenerickaOperacija;

/**
 *
 * @author Nikola
 */
public class VratiKnjigu extends GenerickaOperacija{

    Knjiga knjiga;
    
    @Override
    protected void preduslovi(Object param) throws Exception {
        
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        knjiga=(Knjiga) skladiste.vrati((Knjiga)param, "naziv='"+((Knjiga)param).getNaziv()+"'");
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }
    
    
    
}
