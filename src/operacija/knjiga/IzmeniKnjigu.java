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
public class IzmeniKnjigu extends GenerickaOperacija{

    boolean uspesno;
    
    @Override
    protected void preduslovi(Object param) throws Exception {
        
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        uspesno = skladiste.promeni((Knjiga)param);
    }

    public boolean isUspesno() {
        return uspesno;
    }
    
    
    
}
