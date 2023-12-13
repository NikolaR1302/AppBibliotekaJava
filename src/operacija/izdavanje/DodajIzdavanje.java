/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operacija.izdavanje;

import domen.Izdavanje;
import domen.StavkaIzdavanja;
import operacija.GenerickaOperacija;

/**
 *
 * @author Nikola
 */
public class DodajIzdavanje extends GenerickaOperacija{

    boolean rezultat;
    
    @Override
    protected void preduslovi(Object param) throws Exception {
        
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        try{
            rezultat = skladiste.dodajSlozeni((Izdavanje)param,((Izdavanje)param).getStavke());
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("Nije moguce uneti izdavanje u sistem");
        }
    }

    public boolean isRezultat() {
        return rezultat;
    }
    
    
    
}
