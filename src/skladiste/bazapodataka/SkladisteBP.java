/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skladiste.bazapodataka;

import skladiste.Skladiste;

/**
 *
 * @author Nikola
 */
public interface SkladisteBP<T, K> extends Skladiste<T, K>{

    default public void konektujSe() throws Exception {
        KonekcijaBP.getInstance().uzmiKonekciju();
    }

    default public void diskonektujSe() throws Exception {
        KonekcijaBP.getInstance().uzmiKonekciju().close();
    }

    default public void potvrdi() throws Exception {
        KonekcijaBP.getInstance().uzmiKonekciju().commit();
    }

    default public void ponisti() throws Exception {
        KonekcijaBP.getInstance().uzmiKonekciju().rollback();
    }
}
