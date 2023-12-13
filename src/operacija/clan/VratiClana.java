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
public class VratiClana extends GenerickaOperacija{

    Clan clan;
    
    @Override
    protected void preduslovi(Object param) throws Exception {
        
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        String s = "ime='"+((Clan)param).getIme()+"' AND prezime='"+((Clan)param).getPrezime()+"'";
        clan=(Clan) skladiste.vrati((Clan)param, s);
    }

    public Clan getClan() {
        return clan;
    }
    
    
    
}
