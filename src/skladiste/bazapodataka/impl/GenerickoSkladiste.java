/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skladiste.bazapodataka.impl;

import domen.OpstiDomenskiObjekat;
import domen.StavkaIzdavanja;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import skladiste.bazapodataka.KonekcijaBP;
import skladiste.bazapodataka.SkladisteBP;

/**
 *
 * @author Nikola
 */
public class GenerickoSkladiste implements SkladisteBP<OpstiDomenskiObjekat, OpstiDomenskiObjekat> {

    @Override
    public ArrayList<OpstiDomenskiObjekat> vratiSve(OpstiDomenskiObjekat o, String where) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(o.vratiImeTabele());
        if (where != null) {
            sb.append(where);
        }

        String query = sb.toString();
        System.out.println(query);
        Connection connection = KonekcijaBP.getInstance().uzmiKonekciju();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        ArrayList<OpstiDomenskiObjekat> lista = new ArrayList<>(o.vratiIzResultSet(rs));

        stmt.close();
        rs.close();
        return lista;
    }

    @Override
    public OpstiDomenskiObjekat vrati(OpstiDomenskiObjekat o, String where) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(o.vratiImeTabele());
        sb.append(" WHERE ");
        if (where != null) {
            sb.append(where);
        } else {
            sb.append(o.whereUslovi());
        }

        String query = sb.toString();
        Connection connection = KonekcijaBP.getInstance().uzmiKonekciju();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        ArrayList<OpstiDomenskiObjekat> lista = new ArrayList<>(o.vratiIzResultSet(rs));
        stmt.close();
        rs.close();
        if (lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }

    @Override
    public boolean dodaj(OpstiDomenskiObjekat o) throws Exception {
        try {
            Connection connection = KonekcijaBP.getInstance().uzmiKonekciju();
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ");
            sb.append(o.vratiImeTabele());

            sb.append("(");
            sb.append(o.vratiImenaKolonaZaUnos());
            sb.append(")");

            sb.append(" VALUES(");
            sb.append(o.vratiVrednostiZaUnos());
            sb.append(")");

            String query = sb.toString();
            // System.out.println(query);
            Statement s = connection.createStatement();
            int ar = s.executeUpdate(query);

            s.close();

            return ar > 0;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public boolean obrisi(OpstiDomenskiObjekat o) throws Exception {
        try {
            Connection connection = KonekcijaBP.getInstance().uzmiKonekciju();
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ");
            sb.append(o.vratiImeTabele());
            sb.append(" WHERE ");
            sb.append(o.whereUslovi());

            String query = sb.toString();
            // System.out.println(query);
            Statement s = connection.createStatement();
            int ar = s.executeUpdate(query);

            s.close();

            return ar > 0;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public boolean promeni(OpstiDomenskiObjekat o) throws Exception {
        try {
            Connection connection = KonekcijaBP.getInstance().uzmiKonekciju();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ")
                    .append(o.vratiImeTabele())
                    .append(" SET ");
            sb.append(o.postaviAzuriraneVrednosti());
            sb.append(" WHERE ");
            sb.append(o.whereUslovi());

            String query = sb.toString();
            // System.out.println(query);
            Statement s = connection.createStatement();
            int ar = s.executeUpdate(query);

            s.close();

            return ar > 0;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public boolean dodajSlozeni(OpstiDomenskiObjekat o, List<OpstiDomenskiObjekat> lista) throws Exception {
        try {
            Connection connection = KonekcijaBP.getInstance().uzmiKonekciju();
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ");
            sb.append(o.vratiImeTabele());

            sb.append("(");
            sb.append(o.vratiImenaKolonaZaUnos());
            sb.append(")");

            sb.append(" VALUES(");
            sb.append(o.vratiVrednostiZaUnos());
            sb.append(")");

            String query = sb.toString();
            System.out.println(query);
            // System.out.println(query);
            Statement s = connection.createStatement();
            int ar = s.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rsKey = s.getGeneratedKeys();
            if (rsKey.next()) {

                for (OpstiDomenskiObjekat t:lista) {
                    int id = rsKey.getInt(1);
                    t.postaviId(id);
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("INSERT INTO ");
                    sb2.append(t.vratiImeTabele());
                    sb2.append("(");
                    sb2.append(t.vratiImenaKolonaZaUnos());
                    sb2.append(")");
                    sb2.append(" VALUES(");
                    sb2.append(t.vratiVrednostiZaUnos());
                    sb2.append(")");
                    String query2 = sb2.toString();
                    System.out.println(query2);
                    Statement s2 = connection.createStatement();
                    s2.executeUpdate(query2);
                }

            }

            s.close();

            return ar > 0;
        } catch (Exception e) {
            throw e;
        }
    }

}
