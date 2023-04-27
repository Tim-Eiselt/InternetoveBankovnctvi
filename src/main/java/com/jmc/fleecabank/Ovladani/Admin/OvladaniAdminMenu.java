package com.jmc.fleecabank.Ovladani.Admin;

import com.jmc.fleecabank.Modely.Model;
import com.jmc.fleecabank.Nahledy.AdminMenuMoznosti;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class OvladaniAdminMenu implements Initializable {
    public Button vytvorKlienta_tlacitko;
    public Button klienti_tlacitko;
    public Button vklad_tlacitko;
    public Button odhlasitSe_tlacitko;

    @Override
    // nastavuje akce pro tlačítka v aplikaci voláním metody pridejSnimace
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pridejSnimace();
    }
    //nastavuje akce pro tlačítka v aplikaci. Pokud uživatel klikne na tlačítko pro vytvoření klienta, nastaví se příznak pro výběr této možnosti v administrátorském menu. Stejně tak, pokud uživatel klikne na tlačítko pro zobrazení klientů nebo pro vklad peněz, nastaví se příslušný příznak. Pokud uživatel klikne na tlačítko pro odhlášení, zavře se okno aplikace, zobrazí se přihlašovací okno a příznak pro úspěšné přihlášení administrátora se nastaví na false
    private void pridejSnimace(){
        vytvorKlienta_tlacitko.setOnAction(event -> naVytvoreniKlienta());
        klienti_tlacitko.setOnAction(event -> naKlienty());
        vklad_tlacitko.setOnAction(event -> naVklad());
        odhlasitSe_tlacitko.setOnAction(event -> naOdhlaseni());
    }
    //nastavuje příznak pro výběr možnosti vytvoření klienta v administrátorském menu
    private void naVytvoreniKlienta() {
        Model.davajPriklad().davajNahled().davajAdminVybranouMenuVec().set(AdminMenuMoznosti.VYTVOR_KLIENTA);
    }
    //nastavuje příznak pro výběr možnosti zobrazení klientů v administrátorském menu
    private void naKlienty() {
        Model.davajPriklad().davajNahled().davajAdminVybranouMenuVec().set(AdminMenuMoznosti.KLIENTI);
    }
    //nastavuje příznak pro výběr možnosti vkladu peněz na účet klienta v administrátorském menu.
    private void naVklad() {
        Model.davajPriklad().davajNahled().davajAdminVybranouMenuVec().set(AdminMenuMoznosti.VKLAD);
    }
    //se spouští po kliknutí na tlačítko pro odhlášení, nejprve získá okno aplikace, poté je zavřeno
    //poté se zobrazí přihlašovací okno a příznak pro úspěšné přihlášení administrátora se nastaví na false
    private void naOdhlaseni() {
        // Get Stage
        Stage stejdz = (Stage) klienti_tlacitko.getScene().getWindow();
        // Close the Admin window
        Model.davajPriklad().davajNahled().zavirajStejdz(stejdz);
        // Show Login Window
        Model.davajPriklad().davajNahled().ukazPrihlasovaciOkno();
        // Set Admin Login Success Flag To False
        Model.davajPriklad().setIndikatorUspesnehoPrihlaseniAdmina(false);
    }
}
