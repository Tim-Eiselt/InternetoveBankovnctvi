package com.jmc.fleecabank.Modely;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
public class BeznyUcet extends Ucet {
    // tady je pocet transakci co je uzivatel povolen za den udelat
    private final IntegerProperty limitTransakce;
    public BeznyUcet(String vlastnik, String cisloUctu, double zustatek, int limitTransakce) {
        super(vlastnik, cisloUctu, zustatek);
        this.limitTransakce = new SimpleIntegerProperty(this, "Limit Transakce", limitTransakce);
    }

    public IntegerProperty limitTransakce() {
        return limitTransakce;}

    @Override
    public String toString() {
        return cisloUctuA().get();
    }
}
