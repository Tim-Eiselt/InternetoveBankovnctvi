package com.jmc.fleecabank.Ovladani.Klient;

import com.jmc.fleecabank.Modely.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class OvladaniKlient implements Initializable {
    public BorderPane klient_rodic;

    @Override
    //volá se při inicializaci okna
    //funkce přidává posluchač událostí, který reaguje na změnu hodnoty vybrané menu voláním jedné z uváděných funkcí
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.davajPriklad().davajNahled().davajKlientVybranouMenuVec().addListener((hodnota, staraHodnota, novaHodnota) -> {
            switch (novaHodnota){
                case TRANSAKCE -> klient_rodic.setCenter(Model.davajPriklad().davajNahled().davajNahledTransakci());
                case UCTY -> klient_rodic.setCenter(Model.davajPriklad().davajNahled().davajNahledUctu());
                default -> klient_rodic.setCenter(Model.davajPriklad().davajNahled().davajNahledOvladaciPanel());
            }
        });
    }
}
