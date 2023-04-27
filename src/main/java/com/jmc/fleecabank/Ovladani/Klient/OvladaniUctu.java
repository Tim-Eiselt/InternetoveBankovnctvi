package com.jmc.fleecabank.Ovladani.Klient;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class OvladaniUctu implements Initializable {
    public Label beznyCisloUctu_stitek;
    public Label limitTransakce_stitek;
    public Label datumBeznyUcet_stitek;
    public Label zustatekBeznyUcet_stitek;
    public Label sporiciCisloUctu_stitek;
    public Label limitVyberu_stitek;
    public Label datumSporiciUcet_stitek;
    public Label zustatekSporiciUcet_stitek;
    public TextField castkaNaSporici_pole;
    public Button prevestNaSporici_tlacitko;
    public TextField castkaNaBezny_pole;
    public Button prevestNaBezny_tlacitko;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
