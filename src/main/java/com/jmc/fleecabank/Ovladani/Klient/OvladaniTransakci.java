package com.jmc.fleecabank.Ovladani.Klient;

import com.jmc.fleecabank.Modely.Model;
import com.jmc.fleecabank.Modely.Transakce;
import com.jmc.fleecabank.Nahledy.TransakceTvorbaBunek;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class OvladaniTransakci implements Initializable {
    public ListView<Transakce> transakce_seznam;
    //inicializuje seznam transakcí v případě, že je prázdný, metoda získá seznam transakcí z modelu a pokud je prázdný, tak přidá příklad transakce
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializujVsechnyTransakce();
        transakce_seznam.setItems(Model.davajPriklad().davajVsechnyTransakce());
        transakce_seznam.setCellFactory(e -> new TransakceTvorbaBunek());
    }
    //volá se po vytvoření instance třídy a inicializuje seznam transakcí, nastavuje seznam transakcí pro zobrazení v uživatelském rozhraní a nastavuje továrnu buněk pro zobrazení jednotlivých transakcí v seznamu
    private void inicializujVsechnyTransakce() {
        if (Model.davajPriklad().davajVsechnyTransakce().isEmpty()){
            Model.davajPriklad().nastavVsechnyTransakce();
        }
    }
}