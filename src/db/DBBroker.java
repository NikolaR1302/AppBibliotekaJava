/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import domen.Bibliotekar;
import domen.Clan;
import domen.Izdavanje;
import domen.Knjiga;
import domen.StavkaIzdavanja;
import domen.pomocna.Pretraga;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nikola
 */
public class DBBroker {

    Connection konekcija;

    public void otvoriKonekciju() {
        try {
            konekcija = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteka", "user", "user");
            konekcija.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void zatvoriKonekciju() {
        try {
            konekcija.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void commit() {
        try {
            konekcija.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rollback() {
        try {
            konekcija.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Bibliotekar> vratiSveBibliotekare() {
        ArrayList<Bibliotekar> lista = new ArrayList<>();
        String sql = "SELECT * FROM bibliotekar";
        try {
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                Bibliotekar b = new Bibliotekar();
                b.setId(rs.getInt("id"));
                b.setIme(rs.getString("ime"));
                b.setPrezime(rs.getString("prezime"));
                b.setKorisnickoIme(rs.getString("korisnickoIme"));
                b.setLozinka(rs.getString("lozinka"));
                lista.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public int sacuvajClana(Clan clan) {
        int uspesno = 0;
        try {
            String sql = "INSERT INTO clan(ime,prezime) VALUES(?,?)";
            PreparedStatement ps = konekcija.prepareStatement(sql);
            ps.setString(1, clan.getIme());
            ps.setString(2, clan.getPrezime());
            ps.executeUpdate();
            System.out.println("Sacuvan clan");
            uspesno = 1;
            commit();
        } catch (SQLException ex) {
            System.out.println("nije sacuvan clan");
            rollback();
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return uspesno;
    }

    public int sacuvajKnjigu(Knjiga knjiga) {
        int uspesno = 0;
        try {
            String sql = "INSERT INTO knjiga(naziv,autor,zanr) VALUES(?,?,?)";
            PreparedStatement ps = konekcija.prepareStatement(sql);
            ps.setString(1, knjiga.getNaziv());
            ps.setString(2, knjiga.getAutor());
            ps.setString(3, knjiga.getZanr());
            ps.executeUpdate();
            System.out.println("Sacuvana knjiga");
            uspesno = 1;
            commit();
        } catch (SQLException ex) {
            System.out.println("nije sacuvana knjiga");
            rollback();
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return uspesno;
    }

    public ArrayList<Clan> vratiSveClanove() {
        ArrayList<Clan> clanovi = new ArrayList<>();

        String sql = "SELECT * FROM clan";
        try {
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                Clan c = new Clan();
                c.setId(rs.getInt("id"));
                c.setIme(rs.getString("ime"));
                c.setPrezime(rs.getString("prezime"));
                clanovi.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clanovi;
    }

    public ArrayList<Knjiga> vratiSveKnjige() {
        ArrayList<Knjiga> knjige = new ArrayList<>();

        String sql = "SELECT * FROM knjiga";
        try {
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                Knjiga k = new Knjiga();
                k.setId(rs.getInt("id"));
                k.setNaziv(rs.getString("naziv"));
                k.setAutor(rs.getString("autor"));
                k.setZanr(rs.getString("zanr"));
                knjige.add(k);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return knjige;
    }

    public boolean sacuvajIzdavanje(Izdavanje i) {
        boolean izvrseno = false;

        try {
            String sqlIzdavanje = "INSERT INTO izdavanje(datum, clanID, bibliotekarID) VALUES(?,?,?)";
            PreparedStatement ps = konekcija.prepareStatement(sqlIzdavanje, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, new Date(i.getDatum().getTime()));
            ps.setInt(2, i.getClan().getId());
            ps.setInt(3, i.getBibliotekar().getId());
            ps.executeUpdate();
            ResultSet rsKey = ps.getGeneratedKeys();
            if (rsKey.next()) {
                i.setId(rsKey.getInt(1));
                String sqlStavka = "INSERT INTO stavkaizdavanja(izdavanjeID,rbstavke,knjigaID) VALUES (?,?,?)";
                ps = konekcija.prepareStatement(sqlStavka);
                for (StavkaIzdavanja stavkaIzdavanja : i.getStavke()) {
                    ps.setInt(1, rsKey.getInt(1));
                    ps.setInt(2, stavkaIzdavanja.getRedniBroj());
                    ps.setInt(3, stavkaIzdavanja.getKnjiga().getId());
                    ps.executeUpdate();
                }
            }
            commit();
            izvrseno = true;
        } catch (SQLException ex) {
            rollback();
            System.out.println("Greska u upitu");
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return izvrseno;
    }

    public Clan pronadjiClana(String ime, String prezime) {
        String sql = "SELECT * FROM clan WHERE ime='" + ime + "' AND prezime='" + prezime + "'";
        try {
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                Clan c = new Clan();
                c.setId(rs.getInt("id"));
                c.setIme(rs.getString("ime"));
                c.setPrezime(rs.getString("prezime"));
                commit();
                return c;

            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean izmeniClana(Clan clanZaImenu) {
        boolean izvrseno = false;
        String sql = "UPDATE clan SET ime='" + clanZaImenu.getIme() + "', prezime='" + clanZaImenu.getPrezime() + "' WHERE id=" + clanZaImenu.getId();
        try {
            Statement s = konekcija.createStatement();
            s.executeUpdate(sql);
            commit();
            izvrseno = true;
        } catch (SQLException ex) {
            rollback();
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return izvrseno;
    }

    public boolean obrisiClana(Clan clanZaBrisanje) {
        boolean izvrseno = false;
        String sql = "DELETE FROM clan WHERE id=" + clanZaBrisanje.getId();
        try {
            Statement s = konekcija.createStatement();
            s.executeUpdate(sql);
            commit();
            izvrseno = true;
        } catch (SQLException ex) {
            rollback();
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return izvrseno;
    }

    public Knjiga pronadjiKnjigu(String naziv) {
        String sql = "SELECT * FROM knjiga WHERE naziv='" + naziv + "'";
        try {
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                Knjiga k = new Knjiga();
                k.setId(rs.getInt("id"));
                k.setNaziv(rs.getString("naziv"));
                k.setAutor(rs.getString("autor"));
                k.setZanr(rs.getString("zanr"));
                commit();

                return k;

            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean izmeniKnjigu(Knjiga knjigaZaImenu) {
        boolean izvrseno = false;
        String sql = "UPDATE knjiga SET naziv='" + knjigaZaImenu.getNaziv() + "', autor='" + knjigaZaImenu.getAutor() + "', zanr='" + knjigaZaImenu.getZanr() + "' WHERE id=" + knjigaZaImenu.getId();
        try {
            Statement s = konekcija.createStatement();
            s.executeUpdate(sql);
            commit();
            izvrseno = true;
        } catch (SQLException ex) {
            rollback();
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return izvrseno;
    }

    public boolean obrisiKnjigu(Knjiga knjigaZaBrisanje) {
        boolean izvrseno = false;
        String sql = "DELETE FROM knjiga WHERE id=" + knjigaZaBrisanje.getId();
        try {
            Statement s = konekcija.createStatement();
            s.executeUpdate(sql);
            commit();
            izvrseno = true;
        } catch (SQLException ex) {
            rollback();
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return izvrseno;
    }

    public ArrayList<Pretraga> vratiIzdavanjaPoKnjizi(String nazivKnjige) {
        ArrayList<Pretraga> lista = new ArrayList<>();
        String sql = "SELECT i.datum AS datum, k.naziv AS knjiga, CONCAT(b.ime,' ',b.prezime) AS bibliotekar, CONCAT(c.ime,' ',c.prezime) AS clan\n"
                + "FROM stavkaizdavanja st JOIN izdavanje i ON st.izdavanjeID = i.id JOIN knjiga k ON st.knjigaID = k.id JOIN clan c ON i.clanID=c.id JOIN bibliotekar b ON i.bibliotekarID=b.id\n"
                + "WHERE k.naziv='" + nazivKnjige + "'";
        try {
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                Pretraga p = new Pretraga();

                p.setBibliotekar(rs.getString("bibliotekar"));
                p.setClan(rs.getString("clan"));
                p.setDatum(rs.getDate("datum"));
                p.setKnjiga(rs.getString("knjiga"));
                lista.add(p);
            }
            commit();
            return lista;

        } catch (SQLException ex) {
            rollback();
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Pretraga> vratiIzdavanjaPoClanu(int idClana) {
        ArrayList<Pretraga> lista = new ArrayList<>();
        String sql = "SELECT i.datum AS datum, k.naziv AS knjiga, CONCAT(b.ime,' ',b.prezime) AS bibliotekar, CONCAT(c.ime,' ',c.prezime) AS clan\n"
                + "FROM stavkaizdavanja st JOIN izdavanje i ON st.izdavanjeID = i.id JOIN knjiga k ON st.knjigaID = k.id JOIN clan c ON i.clanID=c.id JOIN bibliotekar b ON i.bibliotekarID=b.id\n"
                + "WHERE c.id="+idClana;
        try {
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                Pretraga p = new Pretraga();

                p.setBibliotekar(rs.getString("bibliotekar"));
                p.setClan(rs.getString("clan"));
                p.setDatum(rs.getDate("datum"));
                p.setKnjiga(rs.getString("knjiga"));
                lista.add(p);
            }
            commit();
            return lista;

        } catch (SQLException ex) {
            rollback();
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
