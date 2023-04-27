package com.jmc.fleecabank.Ovladani.Klient;

import com.jmc.fleecabank.Modely.Model;
import com.jmc.fleecabank.Modely.Transakce;
import com.jmc.fleecabank.Nahledy.TransakceTvorbaBunek;
import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class OvladaniOvladaciPanel implements Initializable {
    public Text uzivatelskeJmeno;
    public Label datum_stitek;
    public Label beznyZustatek_stitek;
    public Label beznyUcetCislo_stitek;
    public Label sporiciZustatek_stitek;
    public Label sporiciUcetCislo_stitek;
    public Label prijem_stitek;
    public Label vydaje_stitek;
    public ListView<Transakce> seznamTransakci;
    public TextField adresaPrijemce_pole;
    public TextField castka_pole;
    public TextArea zprava_pole;
    public Button platba_tlacitko;

    //zavolá metodu spojData, která propojuje data klienta s grafickými prvky na obrazovce
    //poté volá metodu inicializujPosledniTransakce, která zajistí, že poslední transakce klienta jsou načteny z databáze
    //poté nastaví seznam transakcí pro zobrazení v GUI pomocí metody setItems, která bere seznam transakcí z modelu aplikace
    //nakonec nastavuje tlačítko pro provedení platby pomocí metody setOnAction
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spojData();
        inicializujPosledniTransakce();
        seznamTransakci.setItems(Model.davajPriklad().davajPosledniTransakce());
        seznamTransakci.setCellFactory(e -> new TransakceTvorbaBunek());
        platba_tlacitko.setOnAction(event -> naPoslaniPenez());
        shrnutiUctu();
    }
    // propojuje textová pole a štítky s daty klienta v modelu, využívá třídy Bindings, aby sestavila textový řetězec pro uvítání klienta
    private void spojData() {
        uzivatelskeJmeno.textProperty().bind(Bindings.concat("Dobrý den, ").concat(Model.davajPriklad().davajKlient().krestniA()));
        datum_stitek.setText("Dnes, " + LocalDate.now());
        beznyZustatek_stitek.textProperty().bind(Model.davajPriklad().davajKlient().beznyUcetA().get().zustatekA().asString());
        beznyUcetCislo_stitek.textProperty().bind(Model.davajPriklad().davajKlient().beznyUcetA().get().cisloUctuA());
        sporiciZustatek_stitek.textProperty().bind(Model.davajPriklad().davajKlient().sporiciUcetA().get().zustatekA().asString());
        sporiciUcetCislo_stitek.textProperty().bind(Model.davajPriklad().davajKlient().sporiciUcetA().get().cisloUctuA());
    }
    //zajišťuje, že jsou poslední transakce klienta načteny z databáze a uloženy v modelu
    //pokud seznam transakcí ještě nebyl načten, zavolá metodu nastavPoslednitransakce v modelu, která získá poslední transakce z databáze a uloží je v modelu
    private void inicializujPosledniTransakce() {
        if (Model.davajPriklad().davajPosledniTransakce().isEmpty()){
            Model.davajPriklad().nastavPoslednitransakce();
        }
    }
    //spustí se, když uživatel stiskne tlačítko pro odeslání peněz.
    //metoda získá údaje z textových polí pro příjemce, částku a zprávu, poté zkontroluje, zda příjemce již existuje v databázi, a pokud ne, vytvoří nový účet
    //poté aktualizuje zůstatek na účtu odesilatele a příjemce a vytvoří novou transakci v databázi
    // nakonec vymaže obsah textových polí
    private void naPoslaniPenez() {
        String prijemce = adresaPrijemce_pole.getText();
        double castka = Double.parseDouble(castka_pole.getText());
        String zprava = zprava_pole.getText();
        String odesilatel = Model.davajPriklad().davajKlient().adresaPrijemceA().get();
        ResultSet nastaveniVysledku = Model.davajPriklad().davajOvladacDatabaze().hledejKlienta(prijemce);
        try {
            if (nastaveniVysledku.isBeforeFirst()){
                Model.davajPriklad().davajOvladacDatabaze().aktualizujZustatek(prijemce, castka, "PRIDEJ");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Model.davajPriklad().davajOvladacDatabaze().aktualizujZustatek(odesilatel, castka, "SUB");
        Model.davajPriklad().davajKlient().sporiciUcetA().get().setZustatek(Model.davajPriklad().davajOvladacDatabaze().davajZustatekSporicihoUctu(odesilatel));
        Model.davajPriklad().davajOvladacDatabaze().novaTransakce(odesilatel, prijemce, castka, zprava);
        adresaPrijemce_pole.setText("");
        castka_pole.setText("");
        zprava_pole.setText("");
    }
    //vypočítá celkové příjmy a výdaje klienta na základě jeho transakcí v modelu
    //prochází seznam transakcí a přidává částky k příjmům nebo výdajům na základě toho, zda klient platil nebo přijal platbu
    // nakonec aktualizuje štítky s celkovými příjmy a výdaji
    private void shrnutiUctu() {
        double prijem = 0;
        double vydaje = 0;
        if (Model.davajPriklad().davajVsechnyTransakce().isEmpty()){
            Model.davajPriklad().nastavVsechnyTransakce();
        }
        for (Transakce transakce : Model.davajPriklad().davajVsechnyTransakce()) {
            if (transakce.odesilatelProperty().get().equals(Model.davajPriklad().davajKlient().adresaPrijemceA().get())){
                vydaje = vydaje + transakce.castkaProperty().get();
            } else {
                prijem = prijem + transakce.castkaProperty().get();
            }
        }
        prijem_stitek.setText("+ €" + prijem);
        vydaje_stitek.setText("- €" + vydaje);
    }
}
