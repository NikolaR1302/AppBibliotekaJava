/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operacija;

import skladiste.Skladiste;
import skladiste.bazapodataka.SkladisteBP;
import skladiste.bazapodataka.impl.GenerickoSkladiste;

/**
 *
 * @author Nikola
 */
public abstract class GenerickaOperacija {
    
    protected final Skladiste skladiste;

    public GenerickaOperacija() {
        this.skladiste = new GenerickoSkladiste();
    }
    
    public final void izvrsi(Object param) throws Exception{
        try {
            preduslovi(param);
            pokreniTransakciju();
            izvrsiOperaciju(param);
            commitTransakcije();
        } catch (Exception ex) {
            ex.printStackTrace();
            rollbackTransakcije();
            throw ex;
        } finally {
            diskonektuj();
        }
    }
    
    protected abstract void preduslovi(Object param) throws Exception;
    
    private void pokreniTransakciju() throws Exception{        
        ((SkladisteBP) skladiste).konektujSe();
    }
    
    protected abstract void izvrsiOperaciju(Object param) throws Exception;
    
    private void commitTransakcije() throws Exception {
        ((SkladisteBP) skladiste).potvrdi();
    }
    
    private void rollbackTransakcije() throws Exception {
        ((SkladisteBP) skladiste).ponisti();
    }
    
    private void diskonektuj() throws Exception {
        ((SkladisteBP) skladiste).diskonektujSe();
    }
    

    
}
