package com.jmc.fleecabank.Ovladani.Admin;

import com.jmc.fleecabank.Modely.Klient;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class KlientOvladaniBunek implements Initializable {
    public Label krestni_stitek;
    public Label prijmeni_stitek;
    public Label uzivatelskaAdresa_stitek;
    public Label beznyUcet_stitek;
    public Label sporiciUcet_stitek;
    public Label datum_stitek;
    public Button smazani_tlacitko;

    private final Klient klient;

    public KlientOvladaniBunek(Klient klient){
        this.klient = klient;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        krestni_stitek.textProperty().bind(klient.krestniA());
        prijmeni_stitek.textProperty().bind(klient.prijmeniA());
        uzivatelskaAdresa_stitek.textProperty().bind(klient.adresaPrijemceA());
        beznyUcet_stitek.textProperty().bind(klient.beznyUcetA().asString());
        sporiciUcet_stitek.textProperty().bind(klient.sporiciUcetA().asString());
        datum_stitek.textProperty().bind(klient.datumA().asString());
    }
}
