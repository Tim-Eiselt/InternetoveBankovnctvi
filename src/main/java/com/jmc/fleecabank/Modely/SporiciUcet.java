package com.jmc.fleecabank.Modely;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
public class SporiciUcet extends Ucet {
    private final DoubleProperty limitVyberu;
    //vytváří nový objekt SporiciUcet s vlastnostmi děděnými z rodičovské třídy Ucet a novou vlastností limitVyberu
    public SporiciUcet(String vlastnik, String cisloUctu, double zustatek, double limitVyberu) {
        super(vlastnik, cisloUctu, zustatek);
        this.limitVyberu = new SimpleDoubleProperty(this, "Limit Výběru", limitVyberu);
    }

    public DoubleProperty limitVyberu() {
        return limitVyberu;}

    @Override
    //vrátila řetězec s hodnotou čísla účtu
    public String toString() {
        return cisloUctuA().get();
    }
}
