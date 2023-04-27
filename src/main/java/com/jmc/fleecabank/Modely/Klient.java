package com.jmc.fleecabank.Modely;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Klient {
    private final StringProperty krestni;
    private final StringProperty prijmeni;
    private final StringProperty adresaPrijemce;
    private final ObjectProperty<Ucet> beznyUcet;
    private final ObjectProperty<Ucet> sporiciUcet;
    private final ObjectProperty<LocalDate> datum;

    //vytváří novou instanci třídy "Klient" se zmiňovanýmy parametry, všechny tyto parametry jsou uloženy jako atributy objektu "Klient"
    public Klient(String krestni, String prijmeni, String adresaPrijemce, Ucet beznyUcet, Ucet sporiciUcet, LocalDate datum) {
        this.krestni = new SimpleStringProperty(this, "Křestní Jméno", krestni);
        this.prijmeni = new SimpleStringProperty(this, "Příjmení", prijmeni);
        this.adresaPrijemce = new SimpleStringProperty(this, "Adresa Příjemce", adresaPrijemce);
        this.beznyUcet = new SimpleObjectProperty<>(this, "Běžný Účet", beznyUcet);
        this.sporiciUcet = new SimpleObjectProperty<>(this, "Spořící Ůčet", sporiciUcet);
        this.datum = new SimpleObjectProperty<>(this, "Datum", datum);
    }
    //vrací křestní jméno klienta jako StringProperty
    public StringProperty krestniA() {
        return krestni;
    }
    //vrací příjmení klienta jako StringProperty
    public StringProperty prijmeniA() {
        return prijmeni;
    }
    //vrací adresu příjemce klienta jako StringProperty
    public StringProperty adresaPrijemceA() {
        return adresaPrijemce;
    }
    //vrací běžný účet klienta jako ObjectProperty<Ucet>
    public ObjectProperty<Ucet> beznyUcetA() {
        return beznyUcet;
    }
    //vrací spořicí účet klienta jako ObjectProperty<Ucet>
    public ObjectProperty<Ucet> sporiciUcetA() {
        return sporiciUcet;
    }
    //vrací datum klienta jako ObjectProperty<LocalDate>
    public ObjectProperty<LocalDate> datumA() {
        return datum;
    }
}