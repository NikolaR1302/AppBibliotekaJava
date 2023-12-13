/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operacija.bibliotekar;

import domen.Bibliotekar;
import operacija.GenerickaOperacija;

/**
 *
 * @author Nikola
 */
public class Logovanje extends GenerickaOperacija {

    Bibliotekar bibliotekar;

    @Override
    protected void preduslovi(Object param) throws Exception {

    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        try {
            String s = "korisnickoIme='" + ((Bibliotekar) param).getKorisnickoIme() + "' AND lozinka='" + ((Bibliotekar) param).getLozinka() + "'";
            bibliotekar = (Bibliotekar) skladiste.vrati((Bibliotekar) param, s);
        } catch (Exception e) {
            throw new Exception("Nije uspesno prijava na sistem");
        }
    }

    public Bibliotekar getBibliotekar() {
        return bibliotekar;
    }

}
