package com.jmc.fleecabank.Ovladani.Klient;

import com.jmc.fleecabank.Modely.Model;
import com.jmc.fleecabank.Modely.Transakce;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class TransakceOvladacBunek implements Initializable {
    public FontAwesomeIconView ikonaSem;
    public FontAwesomeIconView ikonaVen;
    public Label datumTransakce_stitek;
    public Label odesilatel_stitek;
    public Label prijemce_stitek;
    public Button zprava_tlacitko;
    public Label castka_stitek;

    private final Transakce transakce;
    //vytváří novou instanci třídy TransakceOvladacBunek a přiřazuje ji předanou transakci
    public TransakceOvladacBunek(Transakce transakce){
        this.transakce = transakce;
    }
    //je volána po vytvoření instance třídy a slouží k inicializaci tlačítek a popisků v buněčném zobrazení transakce
    // metoda také nastavuje funkci tlačítka zprava_tlacitko, které zobrazuje zprávu transakce
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        odesilatel_stitek.textProperty().bind(transakce.odesilatelProperty());
        prijemce_stitek.textProperty().bind(transakce.prijemceProperty());
        castka_stitek.textProperty().bind(transakce.castkaProperty().asString());
        datumTransakce_stitek.textProperty().bind(transakce.datumProperty().asString());
        zprava_tlacitko.setOnAction(event -> Model.davajPriklad().davajNahled().ukazZpravu(transakce.odesilatelProperty().get(), transakce.zpravaProperty().get()));
        transakceIkony();
    }
    //je volána z metody initialize() a slouží k nastavení barev ikon v buněčném zobrazení transakce podle toho, zda je odesílatel stejný jako adresa příjemce A
    private void transakceIkony() {
        ikonaSem=new FontAwesomeIconView();
        ikonaVen=new FontAwesomeIconView();
        if (transakce.odesilatelProperty().get().equals(Model.davajPriklad().davajKlient().adresaPrijemceA().get())){
            ikonaSem.setFill(Color.rgb(240, 240, 240));
            ikonaVen.setFill(Color.RED);
        } else {
            ikonaSem.setFill(Color.GREEN);
            ikonaVen.setFill(Color.rgb(240, 240, 240));
        }
    }
}
