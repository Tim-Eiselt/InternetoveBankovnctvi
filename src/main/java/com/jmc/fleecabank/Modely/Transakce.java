package com.jmc.fleecabank.Modely;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Transakce {
    private final StringProperty odesilatel;
    private final StringProperty prijemce;
    private final DoubleProperty castka;
    private final ObjectProperty<LocalDate> datum;
    private final StringProperty zprava;

    //vytváří novou instanci třídy a nastavuje hodnoty pro všechny vlastnosti transakce
    public Transakce(String odesilatel, String prijemce, double castka, LocalDate datum, String zprava) {
        this.odesilatel = new SimpleStringProperty(this, "sender", odesilatel);
        this.prijemce = new SimpleStringProperty(this, "Receiver", prijemce);
        this.castka = new SimpleDoubleProperty(this, "Amount", castka);
        this.datum = new SimpleObjectProperty<>(this, "Date", datum);
        this.zprava = new SimpleStringProperty(this, "Message", zprava);
    }
    public StringProperty odesilatelProperty() {return this.odesilatel;}

    public StringProperty prijemceProperty() {return this.prijemce;}

    public DoubleProperty castkaProperty() {return this.castka;}

    public ObjectProperty<LocalDate> datumProperty() {return this.datum;}

    public StringProperty zpravaProperty() {return this.zprava;}
}
