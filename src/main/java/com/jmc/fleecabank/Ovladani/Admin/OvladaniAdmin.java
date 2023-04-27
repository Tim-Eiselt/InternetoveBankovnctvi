package com.jmc.fleecabank.Ovladani.Admin;

import com.jmc.fleecabank.Modely.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class OvladaniAdmin implements Initializable {
    public BorderPane admin_rodic;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.davajPriklad().davajNahled().davajAdminVybranouMenuVec().addListener((hodnota, staraHodnota, novaHodnota) -> {
            switch (novaHodnota) {
                case KLIENTI -> admin_rodic.setCenter(Model.davajPriklad().davajNahled().davajKlientOkno());
                case VKLAD -> admin_rodic.setCenter(Model.davajPriklad().davajNahled().davajNahledVkladu());
                default -> admin_rodic.setCenter(Model.davajPriklad().davajNahled().davajVytvareniKlientOkna());
            }
        });
    }
}
