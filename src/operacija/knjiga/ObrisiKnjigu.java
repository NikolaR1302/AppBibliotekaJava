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
public class ObrisiKnjigu extends GenerickaOperacija{

    boolean uspesno;
    
    @Override
    protected void preduslovi(Object param) throws Exception {
        
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        try{
        uspesno = skladiste.obrisi((Knjiga)param);
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("Sistem ne moze da obrise knjigu");
        }
    }

    public boolean isUspesno() {
        return uspesno;
    }
    
    
    
}
