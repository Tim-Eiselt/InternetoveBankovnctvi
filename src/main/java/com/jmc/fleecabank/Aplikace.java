package com.jmc.fleecabank;

import com.jmc.fleecabank.Modely.Model;
import javafx.application.Application;
import javafx.stage.Stage;
public class Aplikace extends Application {

    @Override
    public void start(Stage stejdz) {

        Model.davajPriklad().davajNahled().ukazPrihlasovaciOkno();
    }
}
