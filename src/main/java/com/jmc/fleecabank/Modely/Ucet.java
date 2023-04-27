package com.jmc.fleecabank.Modely;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public abstract class Ucet {
    private final StringProperty vlastnik;
    private final StringProperty cisloUctu;
    private final DoubleProperty zustatek;
    //inicializuje atributy vlastníka, čísla účtu a zůstatku na účtu
    public Ucet(String vlastnik, String cisloUctu, double zustatek) {
        this.vlastnik = new SimpleStringProperty(this, "Vlastník", vlastnik);
        this.cisloUctu = new SimpleStringProperty(this, "Číslo Účtu", cisloUctu);
        this.zustatek = new SimpleDoubleProperty(this, "Zůstatek", zustatek);
    }

    public StringProperty vlastnikA() {
        return vlastnik;}
    public StringProperty cisloUctuA() {
        return cisloUctu;}
    public DoubleProperty zustatekA() {
        return zustatek;}
    //nastavuje aktuální zůstatek na účtu
    public void setZustatek(double zustatek) {
        this.zustatek.set(zustatek);
    }
}
