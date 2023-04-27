package com.jmc.fleecabank.Ovladani.Admin;

import com.jmc.fleecabank.Modely.Klient;
import com.jmc.fleecabank.Modely.Model;
import com.jmc.fleecabank.Nahledy.TvorbaBunekKlientu;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class OvladaniKlientu implements Initializable {
    public ListView<Klient> klientiSeznam;

    @Override
    // je volána, když se scéna načte, a nastavuje seznam klientů a buňky v seznamu klientů pomocí setItems a setCellFactory
    public void initialize(URL url, ResourceBundle resourceBundle) {
        seznamKlientu();
        klientiSeznam.setItems(Model.davajPriklad().davajKlienty());
        klientiSeznam.setCellFactory(e -> new TvorbaBunekKlientu());
    }
    // kontroluje, zda seznam klientů není prázdný a v případě prázdného seznamu přidá několik klientů pomocí nastavKlienty
    private void seznamKlientu() {
        if (Model.davajPriklad().davajKlienty().isEmpty()){
            Model.davajPriklad().nastavKlienty();
        }
    }
}
