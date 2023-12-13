/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operacija.izdavanje;

import domen.Bibliotekar;
import domen.Clan;
import domen.Izdavanje;
import domen.Knjiga;
import domen.StavkaIzdavanja;
import java.util.ArrayList;
import operacija.GenerickaOperacija;

/**
 *
 * @author Nikola
 */
public class VratiIzdavanje extends GenerickaOperacija{

    Izdavanje izdavanje;
    
    @Override
    protected void preduslovi(Object param) throws Exception {
        
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        
        try{
        
        izdavanje = (Izdavanje) skladiste.vrati((Izdavanje)param, null);
        
        Bibliotekar b = (Bibliotekar) skladiste.vrati(new Bibliotekar(), "id="+izdavanje.getBibliotekarID());
        
        izdavanje.setBibliotekar(b);
        Clan c = (Clan) skladiste.vrati(new Clan(), "id="+izdavanje.getClanID());
        
        izdavanje.setClan(c);
        ArrayList<Knjiga> knjige = skladiste.vratiSve(new Knjiga(), null);
        
        ArrayList<StavkaIzdavanja> stavke = skladiste.vratiSve(new StavkaIzdavanja(), " WHERE izdavanjeID="+izdavanje.getId());
        
        for (StavkaIzdavanja stavkaIzdavanja : stavke) {
            stavkaIzdavanja.setIzdavanje(izdavanje);
        }
        for (StavkaIzdavanja stavkaIzdavanja : stavke) {
            for(Knjiga knjiga:knjige){
            if(stavkaIzdavanja.getKnjigaID()==knjiga.getId()){
                stavkaIzdavanja.setKnjiga(knjiga);
            }
        }
        }
        
        izdavanje.setStavke(stavke);
        }catch(Exception e){
            throw new Exception("Sistem ne moze da pronadje potvrdu");
        }
    }

    public Izdavanje getIzdavanje() {
        return izdavanje;
    }
    
    
    
}
