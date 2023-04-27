package com.jmc.fleecabank.Modely;

import java.sql.*;
import java.time.LocalDate;

//konstruktor, který se připojí k databázi a inicializuje proměnnou "pripojeni"
public class OvladacDatabaze {
    private Connection pripojeni;
    public OvladacDatabaze() {
        try {
            this.pripojeni = DriverManager.getConnection("jdbc:sqlite:fleecabank2.db");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    //vrací ResultSet obsahující data klienta podle adresy příjemce a hesla
    public ResultSet davajDataKlienta(String uzivatelskaAdresa, String heslo) {
        Statement prohlaseni;
        ResultSet nastaveniVysledku = null;
        try {
            prohlaseni = this.pripojeni.createStatement();
            nastaveniVysledku = prohlaseni.executeQuery("SELECT * FROM Klienti WHERE UzivatelskaAdresa='"+uzivatelskaAdresa+"' AND Heslo='"+heslo+"';");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return nastaveniVysledku;
    }
    //vrací ResultSet obsahující transakce spojené s danou adresou a počtem
    public ResultSet davajTransakce(String uzivatelskaAdresa, int limit) {
        Statement prohlaseni;
        ResultSet nastaveniVysledku = null;
        try {
            prohlaseni = this.pripojeni.createStatement();
            nastaveniVysledku = prohlaseni.executeQuery("SELECT * FROM Transakce WHERE Odesilatel='"+uzivatelskaAdresa+"' OR Prijemce='"+uzivatelskaAdresa+"' LIMIT "+limit+";");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return nastaveniVysledku;
    }
    //vrací zůstatek úspor klienta na základě jeho adresy
    public double davajZustatekSporicihoUctu(String uzivatelskaAdresa) {
        Statement prohlaseni;
        ResultSet nastaveniVysledku;
        double zustatek = 0;
        try {
            prohlaseni = this.pripojeni.createStatement();
            nastaveniVysledku = prohlaseni.executeQuery("SELECT * FROM SporiciUcty WHERE Vlastnik='"+uzivatelskaAdresa+"';");
            zustatek = nastaveniVysledku.getDouble("Zustatek");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return zustatek;
    }
    //aktualizuje zůstatek úspor klienta na základě jeho adresy a částky transakce
    public void aktualizujZustatek(String uzivatelskaAdresa, double castka, String operace) {
        Statement prohlaseni;
        ResultSet nastaveniVysledku;
        try{
            prohlaseni = this.pripojeni.createStatement();
            nastaveniVysledku = prohlaseni.executeQuery("SELECT * FROM SporiciUcty WHERE Vlastnik='"+uzivatelskaAdresa+"';");
            double novyZustatek;
            if (operace.equals("Přidej")){
                novyZustatek = nastaveniVysledku.getDouble("Zustatek") + castka;
                prohlaseni.executeUpdate("UPDATE SporiciUcty SET Zustatek="+novyZustatek+" WHERE Vlastnik='"+uzivatelskaAdresa+"';");
            } else {
                if (nastaveniVysledku.getDouble("Zustatek") >= castka) {
                    novyZustatek = nastaveniVysledku.getDouble("Zustatek") - castka;
                    prohlaseni.executeUpdate("UPDATE SporiciUcty SET Zustatek="+novyZustatek+" WHERE Vlastnik='"+uzivatelskaAdresa+"';");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //vytvoří novou transakci mezi odesílatelem a příjemcem s danou částkou a zprávou
    public void novaTransakce(String odesilatel, String prijemce, double castka, String zprava) {
        Statement prohlaseni;
        try {
            prohlaseni = this.pripojeni.createStatement();
            LocalDate datum = LocalDate.now();
            prohlaseni.executeUpdate("INSERT INTO " + "Transakce(Odesilatel, Prijemce, Castka, Datum, Zprava) " + "VALUES ('"+odesilatel+"', '"+prijemce+"', "+castka+", '"+datum+"', '"+zprava+"');");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //vrací ResultSet obsahující údaje administrátora na základě uživatelského jména a hesla
    public ResultSet davajAdminData(String uzivatelskeJmeno, String heslo) {
        Statement prohlaseni;
        ResultSet nastaveniVysledku = null;
        try {
            prohlaseni = this.pripojeni.createStatement();
            nastaveniVysledku = prohlaseni.executeQuery("SELECT * FROM Admini WHERE UzivatelskeJmeno='"+uzivatelskeJmeno+"' AND Heslo='"+heslo+"';");
        }catch (Exception e){
            e.printStackTrace();
        }
        return nastaveniVysledku;
    }
    //vytvoří nového klienta s danými údaji a datem registrace
    public void vytvorKlienta(String krestni, String prijmeni, String uzivatelskaAdresa, String heslo, LocalDate datum) {
        Statement prohlaseni;
        try {
            prohlaseni = this.pripojeni.createStatement();
            prohlaseni.executeUpdate("INSERT INTO " + "Klienti (Krestni, Prijmeni, UzivatelskaAdresa, Heslo, Datum)" + "VALUES ('"+krestni+"', '"+prijmeni+"', '"+uzivatelskaAdresa+"', '"+heslo+"', '"+datum.toString()+"');");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //vytváří nový běžný účet a přidává ho do databáze, vytváří SQL příkaz pro vložení těchto informací do databáze a provede ho
    public void vytvorBeznyUcet(String vlastnik, String cislo, double limitTransakce, double zustatek) {
        Statement prohlaseni;
        try {
            prohlaseni = this.pripojeni.createStatement();
            prohlaseni.executeUpdate("INSERT INTO " + "BezneUcty (Vlastnik, CisloUctu, LimitTransakce, Zustatek)" + " VALUES ('"+vlastnik+"', '"+cislo+"', "+limitTransakce+", "+zustatek+")");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //vytváří nový spořicí účet a přidává ho do databáze, vytváří SQL příkaz pro vložení těchto informací do databáze a provede ho
    public void vytvorSporiciUcet(String vlastnik, String cislo, double limitVyberu, double zustatek) {
        Statement prohlaseni;
        try {
            prohlaseni = this.pripojeni.createStatement();
            prohlaseni.executeUpdate("INSERT INTO " + "SporiciUcty (Vlastnik, CisloUctu, LimitVyberu, Zustatek)" + " VALUES ('"+vlastnik+"', '"+cislo+"', "+limitVyberu+", "+zustatek+")");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //vrací ResultSet obsahující všechny záznamy klientů v databázi, vytváří SQL příkaz pro výběr všech záznamů z tabulky "Klienti" a provede ho
    public ResultSet davajDataVsechKlientu() {
        Statement prohlaseni;
        ResultSet nastaveniVysledku = null;
        try {
            prohlaseni = this.pripojeni.createStatement();
            nastaveniVysledku = prohlaseni.executeQuery("SELECT * FROM Klienti;");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return nastaveniVysledku;
    }
    //aktualizuje zůstatek na spořicím účtu daného uživatele, vytváří SQL příkaz pro aktualizaci zůstatku na spořicím účtu a provede ho
    public void ulozitUspory(String uzivatelskaAdresa, double castka) {
        Statement prohlaseni;
        try {
            prohlaseni = this.pripojeni.createStatement();
            prohlaseni.executeUpdate("UPDATE SporiciUcty SET Zustatek="+castka+" WHERE Vlastnik='"+uzivatelskaAdresa+"';");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //vrací ResultSet obsahující informace o klientovi s daným uživatelským jménem, vytváří SQL příkaz pro výběr informací o klientovi s daným uživatelským jménem a provede ho
    public ResultSet hledejKlienta(String uzivatelskaAdresa) {
        Statement prohlaseni;
        ResultSet nastaveniVysledku = null;
        try {
            prohlaseni = this.pripojeni.createStatement();
            nastaveniVysledku = prohlaseni.executeQuery("SELECT * FROM Klienti WHERE UzivatelskaAdresa='"+uzivatelskaAdresa+"';");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return nastaveniVysledku;
    }
    //získává ID posledního klienta v databázi, používá SQL dotaz k vyhledání posledního řádku v tabulce "Klienti"
    public int davajIdPoslednihoKlienta() {
        Statement prohlaseni;
        ResultSet nastaveniVysledku;
        int id = 0;
        try {
            prohlaseni = this.pripojeni.createStatement();
            nastaveniVysledku = prohlaseni.executeQuery("SELECT * FROM sqlite_sequence WHERE name='Klienti';");
            id = nastaveniVysledku.getInt("seq");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }
    //získává data o spořicím účtu pro daného klienta
    public ResultSet davajDataSporicihoUctu(String uzivatelskaAdresa) {
        Statement prohlaseni;
        ResultSet nastaveniVysledku = null;
        try {
            prohlaseni = this.pripojeni.createStatement();
            nastaveniVysledku = prohlaseni.executeQuery("SELECT * FROM BezneUcty WHERE Vlastnik='"+uzivatelskaAdresa+"';");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return nastaveniVysledku;
    }
    //vrací výsledky dotazu na databázi, který vyhledává všechny záznamy v tabulce "SporiciUcty", které mají vlastníka shodného s hodnotou "uzivatelskaAdresa"
    public ResultSet davajDataSporiciUcet(String uzivatelskaAdresa) {
        Statement prohlaseni;
        ResultSet nastaveniVysledku = null;
        try {
            prohlaseni = this.pripojeni.createStatement();
            nastaveniVysledku = prohlaseni.executeQuery("SELECT * FROM SporiciUcty WHERE Vlastnik='"+uzivatelskaAdresa+"';");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return nastaveniVysledku;
    }
}