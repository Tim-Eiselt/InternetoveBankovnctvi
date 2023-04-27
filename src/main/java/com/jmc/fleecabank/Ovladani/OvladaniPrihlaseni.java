package com.jmc.fleecabank.Ovladani;

import com.jmc.fleecabank.Modely.Model;
import com.jmc.fleecabank.Nahledy.TypUctu;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
public class OvladaniPrihlaseni implements Initializable {
    public ChoiceBox <TypUctu> vyberUctu;
    public Label adresaUzivatele_stitek;
    public TextField adresaUzivatele_pole;
    public TextField heslo_pole;
    public Button prihlaseni_tlacitko;
    public Label error_stitek;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vyberUctu.setItems(FXCollections.observableArrayList(TypUctu.KLIENT, TypUctu.ADMIN));
        vyberUctu.setValue(Model.davajPriklad().davajNahled().davajPrihlaseniTypUctu());
        vyberUctu.valueProperty().addListener(observable -> nastavVolbuUctu());
        prihlaseni_tlacitko.setOnAction(event -> behemPrihlaseni());
    }
    private void behemPrihlaseni() {
        Stage stejdz = (Stage) error_stitek.getScene().getWindow();
        if (Model.davajPriklad().davajNahled().davajPrihlaseniTypUctu() == TypUctu.KLIENT){
            //Vyhodnocení přihlašovacích údajů klienta
            Model.davajPriklad().vyhodnotKlientUdaje(adresaUzivatele_pole.getText(), heslo_pole.getText());
            if (Model.davajPriklad().davajIndikatorUspesnehoPrihlaseniKlienta()){
                Model.davajPriklad().davajNahled().ukazKlientOkno();
                // Zavření příhlašovací stage
                Model.davajPriklad().davajNahled().zavirajStejdz(stejdz);
            } else {
                adresaUzivatele_pole.setText("");
                heslo_pole.setText("");
                error_stitek.setText("Tyto přihlašovací údaje nejsou správně.");
            }
        } else {
            // Vyhodnocení přihlašovacích údajů admina
            Model.davajPriklad().vyhodnotAdminUdaje(adresaUzivatele_pole.getText(), heslo_pole.getText());
            if (Model.davajPriklad().davajIndikatorUspesnehoPrihlaseniAdmina()){
                Model.davajPriklad().davajNahled().ukazAdminOkno();
                // Zavření přihlašovací stage
                Model.davajPriklad().davajNahled().zavirajStejdz(stejdz);
            } else {
                adresaUzivatele_pole.setText("");
                heslo_pole.setText("");
                error_stitek.setText("Tyto přihlašovací údaje nejsou správně.");
            }
        }
    }

    private void nastavVolbuUctu() {
        Model.davajPriklad().davajNahled().nastavPrihlaseniTypUctu(vyberUctu.getValue());
        /// Změnění labelu s adresou příjemce správným a odpovídajícím způsobem
        if (vyberUctu.getValue() == TypUctu.ADMIN){
            adresaUzivatele_stitek.setText("Přezdívka:");
        } else {
            adresaUzivatele_stitek.setText("Adresa uživatele:");
        }
    }
}
