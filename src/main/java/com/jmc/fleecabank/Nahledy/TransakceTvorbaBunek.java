package com.jmc.fleecabank.Nahledy;

import com.jmc.fleecabank.Ovladani.Klient.TransakceOvladacBunek;
import com.jmc.fleecabank.Modely.Transakce;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
public class TransakceTvorbaBunek extends ListCell<Transakce> {
    protected void updateItem(Transakce transakce, boolean prazdny) {
        super.updateItem(transakce, prazdny);
        if (prazdny){
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader nacteni = new FXMLLoader(getClass().getResource("/FXml/Klient/TransakcePolicka.fxml"));
            TransakceOvladacBunek ovladac = new TransakceOvladacBunek(transakce);
            nacteni.setController(ovladac);
            setText(null);
            try {
                setGraphic(nacteni.load());
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}