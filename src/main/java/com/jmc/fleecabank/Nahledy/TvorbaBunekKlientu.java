package com.jmc.fleecabank.Nahledy;

import com.jmc.fleecabank.Ovladani.Admin.KlientOvladaniBunek;
import com.jmc.fleecabank.Modely.Klient;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

public class TvorbaBunekKlientu extends ListCell<Klient> {
    @Override
    protected void updateItem(Klient klient, boolean prazdny) {
        super.updateItem(klient, prazdny);
        if (prazdny){
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loaderos = new FXMLLoader(getClass().getResource("/FXml/Admin/KlientPolicka.fxml"));
            KlientOvladaniBunek ovladani = new KlientOvladaniBunek(klient);
            loaderos.setController(ovladani);
            setText(null);
            try {
                setGraphic(loaderos.load());
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
