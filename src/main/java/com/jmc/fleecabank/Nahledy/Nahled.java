package com.jmc.fleecabank.Nahledy;

import com.jmc.fleecabank.Ovladani.Admin.OvladaniAdmin;
import com.jmc.fleecabank.Ovladani.Klient.OvladaniKlient;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Nahled {
    private TypUctu prihlaseniTypUctu;
    private final ObjectProperty<KlientMenuMoznosti> klientVybranaMenuVec;
    private AnchorPane nahledOvladaciPanel;
    private AnchorPane nahledTransakci;
    private AnchorPane nahledUctu;
    private final ObjectProperty<AdminMenuMoznosti> adminVybranaMenuVec;
    private AnchorPane vytvorKlientOkno;
    private AnchorPane nahledKlient;
    private AnchorPane nahledVklad;

    public Nahled(){
        this.prihlaseniTypUctu = TypUctu.KLIENT;
        this.klientVybranaMenuVec = new SimpleObjectProperty<>();
        this.adminVybranaMenuVec = new SimpleObjectProperty<>();
    }
    //vrací typ účtu přihlášeného uživatele
    public TypUctu davajPrihlaseniTypUctu() {
        return prihlaseniTypUctu;
    }
    //nastavuje privátní proměnnou prihlaseniTypUctu na hodnotu předanou jako argument.
    public void nastavPrihlaseniTypUctu(TypUctu prihlaseniTypUctu) {
        this.prihlaseniTypUctu = prihlaseniTypUctu;
    }
    public ObjectProperty<KlientMenuMoznosti> davajKlientVybranouMenuVec() {
        return klientVybranaMenuVec;
    }
    //vrací AnchorPane, přičemž pokud je daný objekt null, načte se z odpovídajícího FXML souboru
    public AnchorPane davajNahledOvladaciPanel() {
        if (nahledOvladaciPanel == null){
            try {
                nahledOvladaciPanel = new FXMLLoader(getClass().getResource("/FXml/Klient/OvladaciPanel.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return nahledOvladaciPanel;
    }
    public AnchorPane davajNahledTransakci() {
        if (nahledTransakci == null){
            try {
                nahledTransakci = new FXMLLoader(getClass().getResource("/FXml/Klient/Transakce.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return nahledTransakci;
    }
    public AnchorPane davajNahledUctu() {
        if (nahledUctu == null){
            try {
                nahledUctu = new FXMLLoader(getClass().getResource("/FXml/Klient/Ucty.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return nahledUctu;
    }
    //načte FXML soubor pro klienta a nastaví mu ovládací prvky pomocí instance třídy OvladaniKlient, poté vytvoří nové okno
    public void ukazKlientOkno() {
        FXMLLoader loaderos = new FXMLLoader(getClass().getResource("/FXml/Klient/Klient.fxml"));
        OvladaniKlient ovladaniKlient = new OvladaniKlient();
        loaderos.setController(ovladaniKlient);
        vytvorStage(loaderos);
    }
    //vrací hodnotu z objektového pole typu "AdminMenuMoznosti"
    public ObjectProperty<AdminMenuMoznosti> davajAdminVybranouMenuVec(){
        return adminVybranaMenuVec;
    }
    //načítá "FXML" soubor "VytvoreniKlienta.fxml" a vrací vytvořený uzel AnchorPane
    public AnchorPane davajVytvareniKlientOkna() {
        if (vytvorKlientOkno == null){
            try {
                vytvorKlientOkno = new FXMLLoader(getClass().getResource("/FXml/Admin/VytvoreniKlienta.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return vytvorKlientOkno;
    }
    //načítá "FXML" soubor "Klienti.fxml" a vrací vytvořený uzel AnchorPane
    public AnchorPane davajKlientOkno() {
        if (nahledKlient == null) {
            try {
                nahledKlient = new FXMLLoader(getClass().getResource("/FXml/Admin/Klienti.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return nahledKlient;
    }
    // načítá FXML soubor "Vklad.fxml" a vrací vytvořený uzel AnchorPane
    public AnchorPane davajNahledVkladu() {
        if (nahledVklad == null){
            try {
                nahledVklad = new FXMLLoader(getClass().getResource("/FXml/Admin/Vklad.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return nahledVklad;
    }
    //vytváří FXML načtením souboru "Admin.fxml" a nastavuje mu ovladač třídy "OvladaniAdmin", volá se metoda "vytvorStage", která vytváří nové okno aplikace s danou scénou a zobrazí ho
    public void ukazAdminOkno() {
        FXMLLoader loaderos = new FXMLLoader(getClass().getResource("/FXml/Admin/Admin.fxml"));
        OvladaniAdmin ovladac = new OvladaniAdmin();
        loaderos.setController(ovladac);
        vytvorStage(loaderos);
    }
    //načítá FXML soubor "Prihlaseni.fxml" a vytváří nové okno aplikace s danou scénou a zobrazí ho
    public void ukazPrihlasovaciOkno() {
        FXMLLoader loaderos = new FXMLLoader(getClass().getResource("/FXml/Prihlaseni.fxml"));
        vytvorStage(loaderos);
    }
    // vytváří nové okno aplikace s zobrazovanou zprávou, kterou přijímá jako vstupní parametr
    public void ukazZpravu(String adresaPrijemce, String textZpravy) {
        StackPane pane = new StackPane();
        HBox hBox = new HBox(5);
        hBox.setAlignment(Pos.CENTER);
        Label odesilatel = new Label(adresaPrijemce);
        Label zprava = new Label(textZpravy);
        hBox.getChildren().addAll(odesilatel, zprava);
        pane.getChildren().add(hBox);
        Scene scena = new Scene(pane, 300, 100);
        Stage stejdz = new Stage();
        stejdz.getIcons().add(new Image(String.valueOf(getClass().getResource("/Obrazky/fleeca.png"))));
        stejdz.setResizable(false);
        stejdz.initModality(Modality.APPLICATION_MODAL);
        stejdz.setTitle("Zpráva");
        stejdz.setScene(scena);
        stejdz.show();
    }
    //načítá "FXML" soubor z předaného parametru a vytváří nové okno aplikace s danou scénou a zobrazí ho
    private void vytvorStage(FXMLLoader loaderos) {
        Scene scena = null;
        try {
            scena = new Scene(loaderos.load());
        } catch (Exception e){
            e.printStackTrace();
        }
        Stage stejdz = new Stage();
        stejdz.setScene(scena);
        stejdz.getIcons().add(new Image(String.valueOf(getClass().getResource("/Obrazky/fleeca.png"))));
        stejdz.setResizable(false);
        stejdz.setTitle("Fleeca Bank");
        stejdz.show();
    }
    //uzavírá zadané okno aplikace
    public void zavirajStejdz(Stage stejdz) {
        stejdz.close();
    }
}