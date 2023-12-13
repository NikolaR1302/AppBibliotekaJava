/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operacija.clan;

import domen.Clan;
import java.util.ArrayList;
import java.util.List;
import operacija.GenerickaOperacija;

/**
 *
 * @author Nikola
 */
public class VratiClanove extends GenerickaOperacija{
    
    ArrayList<Clan> lista;

    @Override
    protected void preduslovi(Object param) throws Exception {
        
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        lista = skladiste.vratiSve((Clan)param, null);
    }

    public ArrayList<Clan> getLista() {
        return lista;
    }
    
    
    
}
