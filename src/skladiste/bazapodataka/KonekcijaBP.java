/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skladiste.bazapodataka;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Nikola
 */
public class KonekcijaBP {
    private Connection konekcija;
    private static KonekcijaBP konekcijaBP;

    private KonekcijaBP() {

    }

    public static KonekcijaBP getInstance() {
        if (konekcijaBP == null) {
            konekcijaBP = new KonekcijaBP();
        }
        return konekcijaBP;
    }

    public Connection uzmiKonekciju() throws SQLException, FileNotFoundException, IOException {

        if (konekcija == null || konekcija.isClosed()) {
            Properties properties = new Properties();
            properties.load(new FileInputStream("config/dbconfig.properties"));
            String url = properties.getProperty("url");
            String korisnickoIme = properties.getProperty("korisnickoIme");
            String sifra = properties.getProperty("sifra");
            konekcija = DriverManager.getConnection(url, korisnickoIme, sifra);
            konekcija.setAutoCommit(false);
        }
        return konekcija;
    }
}
