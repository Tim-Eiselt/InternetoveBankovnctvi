package com.jmc.fleecabank.Ovladani.Klient;

import com.jmc.fleecabank.Modely.Model;
import com.jmc.fleecabank.Nahledy.KlientMenuMoznosti;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
public class OvladaniKlientMenu implements Initializable {
    public Button prehled_tlacitko;
    public Button transakce_tlacitko;
    public Button ucty_tlacitko;
    public Button profil_tlacitko;
    public Button odhlaseni_tlacitko;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pridejSnimac();
    }
    //přidává funkce "OvladaciPanel", "Transakce", "Ucty" a "Odhlaseni" jako obslužné funkce pro kliknutí na tlačítka
    //tyto funkce jsou implementovány tak, aby změnily stav modelu aplikace v závislosti na tom, na které tlačítko bylo kliknuto
    private void pridejSnimac() {
        prehled_tlacitko.setOnAction(event -> OvladaciPanel());
        transakce_tlacitko.setOnAction(event -> Transakce());
        ucty_tlacitko.setOnAction(event -> Ucty());
        odhlaseni_tlacitko.setOnAction(event -> Odhlaseni());
    }
    //nastavují v modelu aplikace vybranou možnost z klientova menu
    //funkce "Odhlaseni" pak zavírá aktuální okno aplikace, zobrazí přihlašovací okno a nastaví indikátor úspěšného přihlášení na "false"
    private void OvladaciPanel() {
        Model.davajPriklad().davajNahled().davajKlientVybranouMenuVec().set(KlientMenuMoznosti.OVLADACIPANEL);
    }
    private void Transakce() {
        Model.davajPriklad().davajNahled().davajKlientVybranouMenuVec().set(KlientMenuMoznosti.TRANSAKCE);
    }
    private void Ucty() {
        Model.davajPriklad().davajNahled().davajKlientVybranouMenuVec().set(KlientMenuMoznosti.UCTY);
    }
    //zajišťuje odhlášení uživatele
    private void Odhlaseni() {
        Stage stejdz = (Stage) prehled_tlacitko.getScene().getWindow();
        Model.davajPriklad().davajNahled().zavirajStejdz(stejdz);
        Model.davajPriklad().davajNahled().ukazPrihlasovaciOkno();
        Model.davajPriklad().nastavIndikatorUspesnehoPrihlaseniKlienta(false);
    }
}
