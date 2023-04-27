package com.jmc.fleecabank.Ovladani.Admin;

import com.jmc.fleecabank.Modely.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

public class OvladaniVytvoreniKlienta implements Initializable {
    public TextField krestni_pole;
    public TextField prijmeni_pole;
    public TextField heslo_pole;
    public CheckBox uzivatelskaAdresa_box;
    public Label uzivatelskaAdresa_stitek;
    public CheckBox beznyUcet_box;
    public TextField beznaCastka_pole;
    public CheckBox sporiciUcet_box;
    public TextField sporiciCastka_pole;
    public Button vytvorKlienta_tlacitko;
    public Label error_stitek;
    private String uzivatelskaAdresa;
    private boolean vytvorBeznyIndikator = false;
    private boolean vytvorSporiciIndikator = false;

    @Override
    //volá se při inicializaci daného controlleru. Zde se přiřazují funkce k různým událostem:
    //při kliknutí na tlačítko vytvorKlienta_tlacitko se zavolá metoda vytvorKlienta
    //při změně hodnoty checkboxu uzivatelskaAdresa_box se zavolá metoda vytvorUzivatelskouAdresu a naVytvoreniUzivatelskeAdresy
    //při změně hodnoty checkboxu beznyUcet_box se nastaví indikátor vytvorBeznyIndikator na hodnotu true
    //při změně hodnoty checkboxu sporiciUcet_box se nastaví indikátor vytvorSporiciIndikator na hodnotu true
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vytvorKlienta_tlacitko.setOnAction(event -> vytvorKlienta());
        uzivatelskaAdresa_box.selectedProperty().addListener((hodnota, staraHodnota, novaHodnota) -> {
            if (novaHodnota){
                uzivatelskaAdresa = vytvorUzivatelskouAdresu();
                naVytvoreniUzivatelskeAdresy();
            }
        });
        beznyUcet_box.selectedProperty().addListener((hodnota, staraHodnota, novaHodnota) -> {
            if (novaHodnota){
                vytvorBeznyIndikator = true;
            }
        });
        sporiciUcet_box.selectedProperty().addListener((hodnota, staraHodnota, novaHodnota) -> {
            if (novaHodnota){
                vytvorSporiciIndikator = true;
            }
        });
    }
    //vytváří klienta na základě zadaných informací z textových polí a volá metodu vytvorUcet pro vytvoření bankovního účtu klienta
    //poté se přidává klienta do databáze
    private void vytvorKlienta() {
        if (vytvorBeznyIndikator){
            vytvorUcet("Běžný");
        }
        if (vytvorSporiciIndikator){
            vytvorUcet("Spořící");
        }
        String krestni = krestni_pole.getText();
        String prijmeni = prijmeni_pole.getText();
        String heslo = heslo_pole.getText();
        Model.davajPriklad().davajOvladacDatabaze().vytvorKlienta(krestni, prijmeni, uzivatelskaAdresa, heslo, LocalDate.now());
        error_stitek.setStyle("-fx-text-fill: green; -fx-font-size: 1.3em; -fx-font-weight: bold");
        error_stitek.setText("Klient úspěšně vytvořen");
        prazdnePole();
    }
    //vytváří buď běžný nebo spořicí účet pro klienta na základě typu účtu a volá odpovídající metodu v databázi pro vytvoření účtu
    private void vytvorUcet(String typUctu) {
        double zustatek = Double.parseDouble(beznaCastka_pole.getText());
        String prvniSekce = "5741";
        String posledniSekce = Integer.toString((new Random()).nextInt(9999) + 1000);
        String cisloUctu = prvniSekce + " " + posledniSekce;
        if (typUctu.equals("Běžný")) {
            Model.davajPriklad().davajOvladacDatabaze().vytvorBeznyUcet(uzivatelskaAdresa, cisloUctu, 10, zustatek);
        } else {
            Model.davajPriklad().davajOvladacDatabaze().vytvorSporiciUcet(uzivatelskaAdresa, cisloUctu, 2000, zustatek);
        }
    }
    //nastavuje text popisku pro uživatelskou adresu na základě informací z textových polí
    private void naVytvoreniUzivatelskeAdresy() {
        if (krestni_pole.getText() != null & prijmeni_pole.getText() != null){
            uzivatelskaAdresa_stitek.setText(uzivatelskaAdresa);
        }
    }
    //vytváří uživatelskou adresu pro klienta na základě jeho křestního jména, příjmení a ID v databázi
    private String vytvorUzivatelskouAdresu() {
        int id = Model.davajPriklad().davajOvladacDatabaze().davajIdPoslednihoKlienta() + 1;
        char znak = Character.toLowerCase(krestni_pole.getText().charAt(0));
        return "@"+znak+ prijmeni_pole.getText()+id;
    }
    //vymaže všechna textová pole a nastaví hodnoty checkboxů na false
    private void prazdnePole() {
        krestni_pole.setText("");
        prijmeni_pole.setText("");
        heslo_pole.setText("");
        uzivatelskaAdresa_box.setSelected(false);
        uzivatelskaAdresa_stitek.setText("");
        beznyUcet_box.setSelected(false);
        beznaCastka_pole.setText("");
        sporiciUcet_box.setSelected(false);
        sporiciCastka_pole.setText("");
    }
}