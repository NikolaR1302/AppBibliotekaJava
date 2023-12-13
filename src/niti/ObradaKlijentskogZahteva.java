/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import domen.Bibliotekar;
import domen.Clan;
import domen.Izdavanje;
import domen.Knjiga;
import domen.pomocna.Pretraga;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import konstante.Operacije;
import logika.Kontroler;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Nikola
 */
public class ObradaKlijentskogZahteva extends Thread {

    Socket klijentskiSoket;
    boolean kraj = false;

    public ObradaKlijentskogZahteva() {
    }

    public ObradaKlijentskogZahteva(Socket klijentskiSoket) {
        this.klijentskiSoket = klijentskiSoket;
    }

    @Override
    public void run() {
        while (!kraj) {
            try {

                KlijentskiZahtev kz = primiZahtev();
                ServerskiOdgovor so = new ServerskiOdgovor();

                try {
                    switch (kz.getOperacija()) {
                        case Operacije.LOGIN:
//                            String[] podaci = (String[]) kz.getParametar();
//                            String korisnickoIme = podaci[0];
//                            String lozinka = podaci[1];
                            Bibliotekar b= (Bibliotekar) kz.getParametar();
                            Bibliotekar bbl = Kontroler.getInstance().login(b);
                            so.setOdgovor(bbl);
                            break;
                        case Operacije.SACUVAJ_CLANA:
                            Clan clan = (Clan) kz.getParametar();
                            boolean uspesnoSacuvanClan = Kontroler.getInstance().sacuvajClana(clan);
                            so.setOdgovor(uspesnoSacuvanClan);
                            if (uspesnoSacuvanClan) {
                                so.setPoruka("Uspesno ste sacuvali novog clana");
                            } else {
                                so.setPoruka("Clan nije uspesno sacuvan");
                            }
                            break;
                        case Operacije.SACUVAJ_KNJIGU:
                            Knjiga knjiga = (Knjiga) kz.getParametar();
                            boolean uspesnoSacuvanaKnjiga = Kontroler.getInstance().sacuvajKnjigu(knjiga);
                            so.setOdgovor(uspesnoSacuvanaKnjiga);
                            if (uspesnoSacuvanaKnjiga) {
                                so.setPoruka("Uspesno ste sacuvali knjigu");
                            } else {
                                so.setPoruka("Knjiga nije uspesno sacuvana");
                            }
                            break;
                        case Operacije.UCITAJ_CLANOVE:
                            ArrayList<Clan> listaClanova = Kontroler.getInstance().vratiSveClanove();
                            so.setOdgovor(listaClanova);
                            break;
                        case Operacije.UCITAJ_KNJIGE:
                            ArrayList<Knjiga> listaKnjiga = Kontroler.getInstance().vratiSveKnjige();
                            so.setOdgovor(listaKnjiga);
                            break;
                        case Operacije.ZAPAMTI_IZDAVANJE:
                            Izdavanje i = (Izdavanje) kz.getParametar();
                            boolean uspesnoSacuvanoIzdavanje = Kontroler.getInstance().sacuvajIzdavanje(i);
                            so.setOdgovor(uspesnoSacuvanoIzdavanje);
                            if (uspesnoSacuvanoIzdavanje) {
                                so.setPoruka("Uspešno ste sačuvali potvrdu o izdavanju!");
                            } else {
                                so.setPoruka("Potvrda o izdavanju nije sačuvana!");
                            }
                            break;
                        case Operacije.PRONADJI_CLANA:
//                    String[] parametri = (String[]) kz.getParametar();
//                    String ime = parametri[0];
//                    String prezime = parametri[1];
                            Clan pronadjiClana = (Clan) kz.getParametar();
                            Clan clanPretraga = Kontroler.getInstance().pronadjiClana(pronadjiClana);
                            so.setOdgovor(clanPretraga);
                            break;
                        case Operacije.IZMENI_CLANA:
                            Clan clanZaImenu = (Clan) kz.getParametar();
                            boolean izmenjenClan = Kontroler.getInstance().izmeniClana(clanZaImenu);
                            so.setOdgovor(izmenjenClan);
                            if (izmenjenClan) {
                                so.setPoruka("Uspešno ste izmenili podatke o članu!");
                            } else {
                                so.setPoruka("Podaci nisu uspešno izmenjeni");
                            }
                            break;
                        case Operacije.OBRISI_CLANA:
                            Clan clanZaBrisanje = (Clan) kz.getParametar();
                            boolean uspesnoObrisanClan = Kontroler.getInstance().obrisiClana(clanZaBrisanje);
                            so.setOdgovor(uspesnoObrisanClan);
                            if (uspesnoObrisanClan) {
                                so.setPoruka("Uspešno ste obrisali člana!");
                            } else {
                                so.setPoruka("Člana nije moguće obrisati");
                            }
                            break;
                        case Operacije.PRONADJI_KNJIGU:
//                    String naziv = (String) kz.getParametar();
//                    Knjiga knjigaPretraga = Kontroler.getInstance().pronadjiKnjigu(naziv);
//                    so.setOdgovor(knjigaPretraga);
//                    break;
                            Knjiga pronadjiKnjigu = (Knjiga) kz.getParametar();
                            Knjiga knjigaPretraga = Kontroler.getInstance().pronadjiKnjigu(pronadjiKnjigu);
                            so.setOdgovor(knjigaPretraga);
                            break;

                        case Operacije.IZMENI_KNJIGU:
                            Knjiga knjigaZaImenu = (Knjiga) kz.getParametar();
                            boolean izmenjenaKnjiga = Kontroler.getInstance().izmeniKnjigu(knjigaZaImenu);
                            so.setOdgovor(izmenjenaKnjiga);
                            if (izmenjenaKnjiga) {
                                so.setPoruka("Uspešno ste izmenili podatke o knjizi!");
                            } else {
                                so.setPoruka("Podaci nisu uspešno izmenjeni");
                            }
                            break;

                        case Operacije.OBRISI_KNJIGU:
                            Knjiga knjigaZaBrisanje = (Knjiga) kz.getParametar();
                            boolean uspesnoObrisanaKnjiga = Kontroler.getInstance().obrisiKnjigu(knjigaZaBrisanje);
                            so.setOdgovor(uspesnoObrisanaKnjiga);
                            if (uspesnoObrisanaKnjiga) {
                                so.setPoruka("Uspešno ste obrisali knjigu!");
                            } else {
                                so.setPoruka("Knjigu nije moguće obrisati");
                            }
                            break;
                        case Operacije.PRONADJI_IZDAVANJE:
                            Izdavanje izdavanjePretraga = (Izdavanje) kz.getParametar();
                            Izdavanje pronadjenoIzdavanje = Kontroler.getInstance().pronadjiIzdavanje(izdavanjePretraga);
                            so.setOdgovor(pronadjenoIzdavanje);
                            break;
                            
//                        case Operacije.PRETRAGA_PO_KNJIZI:
//                            String nazivKnjige = (String) kz.getParametar();
//                            ArrayList<Pretraga> listaIzdavanjaKnjiga = Kontroler.getInstance().pretraziIzdavanjaPoKnjizi(nazivKnjige);
//                            so.setOdgovor(listaIzdavanjaKnjiga);
//                            break;
//                        case Operacije.PRETRAGA_PO_CLANU:
//                            int idClana = (int) kz.getParametar();
//                            ArrayList<Pretraga> listaIzdavanjaClan = Kontroler.getInstance().pretraziIzdavanjaPoClanu(idClana);
//                            so.setOdgovor(listaIzdavanjaClan);
//                            break;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    so.setException(e);
                    System.out.println(e.getMessage());
                }

                posaljiOdgovor(so);
            } catch (Exception ex) {
                Logger.getLogger(ObradaKlijentskogZahteva.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private KlijentskiZahtev primiZahtev() {
        try {
            ObjectInputStream ois = new ObjectInputStream(klijentskiSoket.getInputStream());
            try {
                return (KlijentskiZahtev) ois.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ObradaKlijentskogZahteva.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(ObradaKlijentskogZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void posaljiOdgovor(ServerskiOdgovor so) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(klijentskiSoket.getOutputStream());
            oos.writeObject(so);
        } catch (IOException ex) {
            Logger.getLogger(ObradaKlijentskogZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
