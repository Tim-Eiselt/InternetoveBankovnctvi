module com.jmc.fleecabank {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.jmc.fleecabank to javafx.fxml;
    exports com.jmc.fleecabank;
    exports com.jmc.fleecabank.Ovladani;
    exports com.jmc.fleecabank.Ovladani.Admin;
    exports com.jmc.fleecabank.Ovladani.Klient;
    exports com.jmc.fleecabank.Modely;
    exports com.jmc.fleecabank.Nahledy;
}