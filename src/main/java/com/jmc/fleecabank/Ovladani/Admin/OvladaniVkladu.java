package com.jmc.fleecabank.Ovladani.Admin;

import com.jmc.fleecabank.Modely.Klient;
import com.jmc.fleecabank.Modely.Model;
import com.jmc.fleecabank.Nahledy.TvorbaBunekKlientu;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class OvladaniVkladu implements Initializable {

    public TextField adresaUzivatele_pole;
    public Button vyhledavani_tlacitko;
    public ListView<Klient> vysledky_seznam;
    public TextField castka_pole;
    public Button vklad_tlacitko;

    private Klient klient;

    @Override
    //je volána, když se scéna načte, tato metoda nastavuje akce pro dva tlačítka
    //pokud uživatel klikne na tlačítko "vyhledávání", zavolá se metoda "naVyhledavaniKlienta"
    //pokud uživatel klikne na tlačítko "vklad", zavolá se metoda "naVklad"
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vyhledavani_tlacitko.setOnAction(event -> naVyhledavaniKlienta());
        vklad_tlacitko.setOnAction(event -> naVklad());
    }
    //vyhledává klienta na základě adresy a zobrazuje výsledky ve vysledky_seznam
    //tato metoda také nastavuje buňky seznamu pomocí TvorbaBunekKlientu a vybere prvního klienta ze seznamu
    private void naVyhledavaniKlienta() {
        ObservableList<Klient> vysledkyHledani = Model.davajPriklad().hledejKlienta(adresaUzivatele_pole.getText());
        vysledky_seznam.setItems(vysledkyHledani);
        vysledky_seznam.setCellFactory(e -> new TvorbaBunekKlientu());
        klient = vysledkyHledani.get(0);
    }
    //provádí vklad na účet klienta, nejprve získá částku, kterou chce uživatel vložit, a poté spočítá nový zůstatek na účtu klienta
    //pokud je částka kladná, uloží se nový zůstatek v databázi
    private void naVklad() {
        double castka = Double.parseDouble(castka_pole.getText());
        double novyZustatek = castka + klient.sporiciUcetA().get().zustatekA().get();
        if (castka_pole.getText() != null){
            Model.davajPriklad().davajOvladacDatabaze().ulozitUspory(klient.adresaPrijemceA().get(), novyZustatek);
        }
        prazdnePole();
    }
    //vynuluje pole pro adresu a částku
    private void prazdnePole() {
        adresaUzivatele_pole.setText("");
        castka_pole.setText("");
    }
}