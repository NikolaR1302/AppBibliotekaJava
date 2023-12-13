/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logika;

import db.DBBroker;
import domen.Bibliotekar;
import domen.Clan;
import domen.Izdavanje;
import domen.Knjiga;
import domen.pomocna.Pretraga;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import operacija.GenerickaOperacija;
import operacija.bibliotekar.Logovanje;
import operacija.bibliotekar.VratiBibliotekare;
import operacija.clan.DodajClana;
import operacija.clan.IzmeniClana;
import operacija.clan.ObrisiClana;
import operacija.clan.VratiClana;
import operacija.clan.VratiClanove;
import operacija.izdavanje.DodajIzdavanje;
import operacija.izdavanje.VratiIzdavanje;
import operacija.knjiga.DodajKnjigu;
import operacija.knjiga.IzmeniKnjigu;
import operacija.knjiga.ObrisiKnjigu;
import operacija.knjiga.VratiKnjige;
import operacija.knjiga.VratiKnjigu;

/**
 *
 * @author Nikola
 */
public class Kontroler {

    private static Kontroler instance;
    DBBroker db;
    Bibliotekar bibliotekar;

    private Kontroler() {
        db = new DBBroker();
    }

    public static Kontroler getInstance() {
        if (instance == null) {
            instance = new Kontroler();
        }
        return instance;
    }

    public Bibliotekar login(Bibliotekar b) throws Exception {
        GenerickaOperacija o = new Logovanje();
        o.izvrsi(b);
        return ((Logovanje) o).getBibliotekar();
    }

    public boolean sacuvajClana(Clan clan) throws Exception {
        GenerickaOperacija o = new DodajClana();
        o.izvrsi(clan);
        return ((DodajClana) o).getUspesno();
    }

    public boolean sacuvajKnjigu(Knjiga knjiga) throws Exception {
        GenerickaOperacija o = new DodajKnjigu();
        o.izvrsi(knjiga);
        return ((DodajKnjigu) o).isUspesno();
    }

    public ArrayList<Clan> vratiSveClanove() throws Exception {
        GenerickaOperacija o = new VratiClanove();
        o.izvrsi(new Clan());
        return ((VratiClanove) o).getLista();
    }

    public ArrayList<Knjiga> vratiSveKnjige() throws Exception {
        GenerickaOperacija o = new VratiKnjige();
        o.izvrsi(new Knjiga());
        return ((VratiKnjige) o).getLista();
    }

    public Clan pronadjiClana(Clan pronadjiClana) throws Exception {
        GenerickaOperacija o = new VratiClana();
        o.izvrsi(pronadjiClana);
        return ((VratiClana) o).getClan();
    }

    public boolean izmeniClana(Clan clanZaImenu) throws Exception {
        GenerickaOperacija o = new IzmeniClana();
        o.izvrsi(clanZaImenu);
        return ((IzmeniClana) o).getUspesno();
    }

    public boolean obrisiClana(Clan clanZaBrisanje) throws Exception {
        GenerickaOperacija o = new ObrisiClana();
        o.izvrsi(clanZaBrisanje);
        return ((ObrisiClana) o).isUspesno();
    }

    public Knjiga pronadjiKnjigu(Knjiga knjigaPretraga) throws Exception {
        GenerickaOperacija o = new VratiKnjigu();
        o.izvrsi(knjigaPretraga);
        return ((VratiKnjigu) o).getKnjiga();
    }

    public boolean izmeniKnjigu(Knjiga knjigaZaIzmenu) throws Exception {
        GenerickaOperacija o = new IzmeniKnjigu();
        o.izvrsi(knjigaZaIzmenu);
        return ((IzmeniKnjigu) o).isUspesno();
    }

    public boolean obrisiKnjigu(Knjiga knjigaZaBrisanje) throws Exception {
        GenerickaOperacija o = new ObrisiKnjigu();
        o.izvrsi(knjigaZaBrisanje);
        return ((ObrisiKnjigu) o).isUspesno();
    }

    public boolean sacuvajIzdavanje(Izdavanje i) throws Exception {
        GenerickaOperacija o = new DodajIzdavanje();
        o.izvrsi(i);
        return ((DodajIzdavanje) o).isRezultat();
    }
    
    public Izdavanje pronadjiIzdavanje(Izdavanje izdavanjePretraga) throws Exception {
        GenerickaOperacija o = new VratiIzdavanje();
        o.izvrsi(izdavanjePretraga);
        return ((VratiIzdavanje) o).getIzdavanje();
    }

//    public ArrayList<Pretraga> pretraziIzdavanjaPoKnjizi(String nazivKnjige) {
//        db.otvoriKonekciju();
//        ArrayList<Pretraga> listaIzdavanjaKnjiga = db.vratiIzdavanjaPoKnjizi(nazivKnjige);
//        db.zatvoriKonekciju();
//        return listaIzdavanjaKnjiga;
//    }
//
//    public ArrayList<Pretraga> pretraziIzdavanjaPoClanu(int idClana) {
//        db.otvoriKonekciju();
//        ArrayList<Pretraga> listaIzdavanjaClan = db.vratiIzdavanjaPoClanu(idClana);
//        db.zatvoriKonekciju();
//        return listaIzdavanjaClan;
//    }

    //    public Bibliotekar login(String korisnickoIme, String lozinka) {
//        db.otvoriKonekciju();
//        ArrayList<Bibliotekar> lista = db.vratiSveBibliotekare();
//        for (Bibliotekar b : lista) {
//            if(b.getKorisnickoIme().equals(korisnickoIme) && b.getLozinka().equals(lozinka)){
//                this.bibliotekar = b;
//                db.zatvoriKonekciju();
//                return b;
//            }
//        }
//        db.zatvoriKonekciju();
//        return null;
//    }
    ////    public int sacuvajClana(Clan clan) {
//////        db.otvoriKonekciju();
//////        int uspesno = db.sacuvajClana(clan);
//////        db.zatvoriKonekciju();
//////        return uspesno;
//            
//    }
    //    public int sacuvajKnjigu(Knjiga knjiga) {
//        db.otvoriKonekciju();
//        int uspesno = db.sacuvajKnjigu(knjiga);
//        db.zatvoriKonekciju();
//        return uspesno;
//    }
    //    public ArrayList<Clan> vratiSveClanove() {
//        db.otvoriKonekciju();
//        ArrayList<Clan> listaClanova = db.vratiSveClanove();
//        db.zatvoriKonekciju();
//        return listaClanova;
//    }
    //    public ArrayList<Knjiga> vratiSveKnjige() {
//        db.otvoriKonekciju();
//        ArrayList<Knjiga> listaKnjiga = db.vratiSveKnjige();
//        db.zatvoriKonekciju();
//        return listaKnjiga;
//    }
    //    public Clan pronadjiClana(String ime, String prezime) {
//        db.otvoriKonekciju();
//        Clan clan = db.pronadjiClana(ime,prezime);
//        db.zatvoriKonekciju();
//        return clan;
//    }
    //    public boolean izmeniClana(Clan clanZaImenu) {
//        db.otvoriKonekciju();
//        boolean uspesno = db.izmeniClana(clanZaImenu);
//        db.zatvoriKonekciju();
//        return uspesno;
//    }
    //    public boolean obrisiClana(Clan clanZaBrisanje) {
//        db.otvoriKonekciju();
//        boolean uspesno =db.obrisiClana(clanZaBrisanje);
//        db.zatvoriKonekciju();
//        return uspesno;
//    }
    //    
//    public boolean izmeniKnjigu(Knjiga knjigaZaImenu) {
//        db.otvoriKonekciju();
//        boolean uspesno = db.izmeniKnjigu(knjigaZaImenu);
//        db.zatvoriKonekciju();
//        return uspesno;
//    }
    //    public boolean obrisiKnjigu(Knjiga knjigaZaBrisanje) {
//        db.otvoriKonekciju();
//        boolean uspesno =db.obrisiKnjigu(knjigaZaBrisanje);
//        db.zatvoriKonekciju();
//        return uspesno;
//    }
    //    public boolean sacuvajIzdavanje(Izdavanje i) {
//        db.otvoriKonekciju();
//        boolean uspesno = db.sacuvajIzdavanje(i);
//        db.zatvoriKonekciju();
//        return uspesno;
//    }

    
}
