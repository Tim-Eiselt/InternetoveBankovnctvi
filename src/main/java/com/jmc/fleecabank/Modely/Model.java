package com.jmc.fleecabank.Modely;

import com.jmc.fleecabank.Nahledy.Nahled;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.time.LocalDate;

public class Model {
    private static Model modelos;
    private final Nahled nahled;
    private final OvladacDatabaze ovladacDatabaze;
    private final Klient klient;
    private boolean indikatorUspesnehoPrihlaseniKlienta;
    private final ObservableList<Transakce> posledniTransakce;
    private final ObservableList<Transakce> vsechnyTransakce;
    private boolean indikatorUspesnehoPrihlaseniAdmina;
    private final ObservableList<Klient> klients;
    private Model() {
        this.nahled = new Nahled();
        this.ovladacDatabaze = new OvladacDatabaze();
        this.indikatorUspesnehoPrihlaseniKlienta = false;
        this.klient = new Klient("", "", "", null, null, null);
        this.posledniTransakce = FXCollections.observableArrayList();
        this.vsechnyTransakce = FXCollections.observableArrayList();
        this.indikatorUspesnehoPrihlaseniAdmina = false;
        this.klients = FXCollections.observableArrayList();
    }
    // vrací vždy stejnou instanci třídy Model, což zajišťuje, že v aplikaci bude vždy právě jedna instance této třídy
    public static synchronized Model davajPriklad() {
        if (modelos == null){
            modelos = new Model();
        }
        return modelos;
    }
    //vrací instanci třídy Nahled, která slouží k zobrazování náhledů různých elementů v aplikaci
    public Nahled davajNahled() {
        return nahled;
    }
    //vrací instanci třídy Nahled, která slouží k zobrazování náhledů různých elementů v aplikaci
    public OvladacDatabaze davajOvladacDatabaze() {
        return ovladacDatabaze;
    }
    //vrací hodnotu indikátoru úspěšného přihlášení klienta
    public boolean davajIndikatorUspesnehoPrihlaseniKlienta() {
        return this.indikatorUspesnehoPrihlaseniKlienta;
    }
    //nastavuje hodnotu indikátoru úspěšného přihlášení klienta na zadanou hodnotu
    public void nastavIndikatorUspesnehoPrihlaseniKlienta(boolean indikator) {
        this.indikatorUspesnehoPrihlaseniKlienta = indikator;
    }
    public Klient davajKlient() {
        return klient;
    }
    //slouží k vyhodnocení údajů klienta při jeho přihlášení, načte z databáze údaje klienta na základě zadané adresy a hesla, a následně je přiřadí do instance třídy Klient, pokud jsou údaje správné, nastaví se indikátor úspěšného přihlášení klienta na true
    public void vyhodnotKlientUdaje(String adresa, String heslo) {
        BeznyUcet beznyUcet;
        SporiciUcet sporiciUcet;
        ResultSet vysledek = ovladacDatabaze.davajDataKlienta(adresa, heslo);
        try {
            if (vysledek.isBeforeFirst()){
                this.klient.krestniA().set(vysledek.getString("Krestni"));
                this.klient.prijmeniA().set(vysledek.getString("Prijmeni"));
                this.klient.adresaPrijemceA().set(vysledek.getString("UzivatelskaAdresa"));
                String[] datumA = vysledek.getString("Datum").split("-");
                LocalDate datum = LocalDate.of(Integer.parseInt(datumA[0]), Integer.parseInt(datumA[1]), Integer.parseInt(datumA[2]));
                this.klient.datumA().set(datum);
                beznyUcet = davajBeznyUcet(adresa);
                sporiciUcet = davajSporiciUcet(adresa);
                this.klient.beznyUcetA().set(beznyUcet);
                this.klient.sporiciUcetA().set(sporiciUcet);
                this.indikatorUspesnehoPrihlaseniKlienta = true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    //získává data o transakcích z databáze a přidává je do předaného seznamu transakcí
    private void pripravaTransakci(ObservableList<Transakce> transakceA, int limit) {
        ResultSet nastaveniVysledku = ovladacDatabaze.davajTransakce(this.klient.adresaPrijemceA().get(), limit);
        try {
            while (nastaveniVysledku.next()){
                String odesilatel = nastaveniVysledku.getString("Odesilatel");
                String prijemce = nastaveniVysledku.getString("Prijemce");
                double castka = nastaveniVysledku.getDouble("Castka");
                String[] datumA = nastaveniVysledku.getString("Datum").split("-");
                LocalDate datum = LocalDate.of(Integer.parseInt(datumA[0]), Integer.parseInt(datumA[1]), Integer.parseInt(datumA[2]));
                String zprava = nastaveniVysledku.getString("Zprava");
                transakceA.add(new Transakce(odesilatel, prijemce, castka, datum, zprava));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void nastavPoslednitransakce() {
        pripravaTransakci(this.posledniTransakce, 4);
    }
    public ObservableList<Transakce> davajPosledniTransakce() {
        return posledniTransakce;
    }
    public void nastavVsechnyTransakce() {
        pripravaTransakci(this.vsechnyTransakce, -1);
    }
    //vrátí seznam všech transakcí
    public ObservableList<Transakce> davajVsechnyTransakce() {
        return vsechnyTransakce;
    }
    //vrátí hodnotu, zda byl správně zadán přihlašovací údaj pro administrátora
    public boolean davajIndikatorUspesnehoPrihlaseniAdmina() {
        return this.indikatorUspesnehoPrihlaseniAdmina;
    }
    //nastaví hodnotu indikatorUspesnehoPrihlaseniAdmina na zadanou hodnotu
    public void setIndikatorUspesnehoPrihlaseniAdmina(boolean indikatorUspesnehoPrihlaseniAdmina) {
        this.indikatorUspesnehoPrihlaseniAdmina = indikatorUspesnehoPrihlaseniAdmina;
    }
    //získává data o administrátorovi z databáze a nastavuje hodnotu indikatorUspesnehoPrihlaseniAdmina na true, pokud jsou zadané přihlašovací údaje správné
    public void vyhodnotAdminUdaje(String uzivatelskeJmeno, String heslo) {
        ResultSet nastaveniVysledku = ovladacDatabaze.davajAdminData(uzivatelskeJmeno, heslo);
        try {
            if (nastaveniVysledku.isBeforeFirst()){
                this.indikatorUspesnehoPrihlaseniAdmina = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ObservableList<Klient> davajKlienty() {
        return klients;
    }
    //získává data o klientech z databáze a přidává je do seznamu klientů
    public void nastavKlienty() {
        BeznyUcet beznyUcet;
        SporiciUcet sporiciUcet;
        ResultSet nastaveniVysledku = ovladacDatabaze.davajDataVsechKlientu();
        try {
            while (nastaveniVysledku.next()){
                String krestni = nastaveniVysledku.getString("Krestni");
                String prijmeni = nastaveniVysledku.getString("Prijmeni");
                String adresaPrijemce = nastaveniVysledku.getString("UzivatelskaAdresa");
                String[] datumA = nastaveniVysledku.getString("Datum").split("-");
                LocalDate datum = LocalDate.of(Integer.parseInt(datumA[0]), Integer.parseInt(datumA[1]), Integer.parseInt(datumA[2]));
                beznyUcet = davajBeznyUcet(adresaPrijemce);
                sporiciUcet = davajSporiciUcet(adresaPrijemce);
                klients.add(new Klient(krestni, prijmeni, adresaPrijemce, beznyUcet, sporiciUcet, datum));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //hledá klienta v databázi podle zadané adresy příjemce vrací seznam klientů odpovídajících zadaným kritériím, případně provádí jiné operace: vytváření, úpravu nebo mazání záznamů.
    public ObservableList<Klient> hledejKlienta(String uzivatelskaAdresa){
        ObservableList<Klient> vysledkyHledani = FXCollections.observableArrayList();
        ResultSet nastaveniVysledku = ovladacDatabaze.hledejKlienta(uzivatelskaAdresa);
        try {
            BeznyUcet beznyUcet = davajBeznyUcet(uzivatelskaAdresa);
            SporiciUcet sporiciUcet = davajSporiciUcet(uzivatelskaAdresa);
            String krestni = nastaveniVysledku.getString("Krestni");
            String prijmeni = nastaveniVysledku.getString("Prijmeni");
            String[] datumA = nastaveniVysledku.getString("Datum").split("-");
            LocalDate datum = LocalDate.of(Integer.parseInt(datumA[0]), Integer.parseInt(datumA[1]), Integer.parseInt(datumA[2]));
            vysledkyHledani.add(new Klient(krestni, prijmeni, uzivatelskaAdresa, beznyUcet, sporiciUcet, datum));
        }catch (Exception e){
            e.printStackTrace();
        }
        return vysledkyHledani;
    }
    //přijímá řetězec uzivatelskaAdresa jako vstup a vrátí objekt třídy BeznyUcet, získává data účtu z databáze, data jsou přečtena z ResultSet a vytvořen nový objekt BeznyUcet s příslušnými vlastnostmi a následně vrácen
    public BeznyUcet davajBeznyUcet(String uzivatelskaAdresa){
        BeznyUcet ucet = null;
        ResultSet nastaveniVysledku = ovladacDatabaze.davajDataSporicihoUctu(uzivatelskaAdresa);
        try {
            String cisloUctu = nastaveniVysledku.getString("CisloUctu");
            int limitTransakce = (int) nastaveniVysledku.getDouble("LimitTransakce");
            double zustatek = nastaveniVysledku.getDouble("Zustatek");
            ucet = new BeznyUcet(uzivatelskaAdresa, cisloUctu, zustatek, limitTransakce);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ucet;
    }
    //přijímá řetězec adresa jako vstup a vrátí objekt třídy SporiciUcet, získává data účtu z databáze, data jsou přečtena z ResultSet a vytvořen nový objekt SporiciUcet s příslušnými vlastnostmi a následně vrácen.
    public SporiciUcet davajSporiciUcet(String adresa){
        SporiciUcet ucet = null;
        ResultSet nastaveniVysledku = ovladacDatabaze.davajDataSporiciUcet(adresa);
        try {
            String cisloUctu = nastaveniVysledku.getString("CisloUctu");
            double limitVyberu = nastaveniVysledku.getDouble("LimitVyberu");
            double zustatek = nastaveniVysledku.getDouble("Zustatek");
            ucet = new SporiciUcet(adresa, cisloUctu, zustatek, limitVyberu);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ucet;
    }
}