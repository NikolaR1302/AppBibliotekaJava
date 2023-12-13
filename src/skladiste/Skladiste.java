/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skladiste;

import domen.Izdavanje;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nikola
 */
public interface Skladiste<T, K> {
    
    ArrayList<T> vratiSve(T param,String where) throws Exception;

    T vrati(T t, String where) throws Exception;

    boolean dodaj(T t) throws Exception;

    boolean obrisi(T t) throws Exception;

    boolean promeni(T t) throws Exception;
    
    boolean dodajSlozeni(T t, List<K> k) throws Exception;


    
}
