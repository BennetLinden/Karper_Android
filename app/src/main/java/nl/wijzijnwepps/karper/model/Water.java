package nl.wijzijnwepps.karper.model;

import java.io.Serializable;

/**
 * Created by Stephan on 15/01/15.
 */
public class Water implements Serializable {

    private String type;
    private String name;
    private String city;

    private boolean
        bootToegestaan,
        electrischeBoot,
        benzineBoot,
        roeiboot,
        Voerboot,
        nachtvissenToegestaan,
        vergunningOnlineVerkrijgbaar;
    private int aantalHengels;
    private int categorie; //1 or 2
    private String Hectare; //String because it can be 'n/a'
    private String beschrijving;
    private String beschrijvingEN;

    public Water(){}

    public Water(String name, String type, String city) {
        this.name = name;
        this.type = type;
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isBootToegestaan() {
        return bootToegestaan;
    }

    public void setBootToegestaan(boolean bootToegestaan) {
        this.bootToegestaan = bootToegestaan;
    }

    public boolean isElectrischeBoot() {
        return electrischeBoot;
    }

    public void setElectrischeBoot(boolean electrischeBoot) {
        this.electrischeBoot = electrischeBoot;
    }

    public boolean isBenzineBoot() {
        return benzineBoot;
    }

    public void setBenzineBoot(boolean benzineBoot) {
        this.benzineBoot = benzineBoot;
    }

    public boolean isRoeiboot() {
        return roeiboot;
    }

    public void setRoeiboot(boolean roeiboot) {
        this.roeiboot = roeiboot;
    }

    public boolean isVoerboot() {
        return Voerboot;
    }

    public void setVoerboot(boolean voerboot) {
        Voerboot = voerboot;
    }

    public boolean isNachtvissenToegestaan() {
        return nachtvissenToegestaan;
    }

    public void setNachtvissenToegestaan(boolean nachtvissenToegestaan) {
        this.nachtvissenToegestaan = nachtvissenToegestaan;
    }

    public int getAantalHengels() {
        return aantalHengels;
    }

    public void setAantalHengels(int aantalHengels) {
        this.aantalHengels = aantalHengels;
    }

    public int getCategorie() {
        return categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }

    public String getHectare() {
        return Hectare;
    }

    public void setHectare(String hectare) {
        Hectare = hectare;
    }

    public boolean isVergunningOnlineVerkrijgbaar() {
        return vergunningOnlineVerkrijgbaar;
    }

    public void setVergunningOnlineVerkrijgbaar(boolean vergunningOnlineVerkrijgbaar) {
        this.vergunningOnlineVerkrijgbaar = vergunningOnlineVerkrijgbaar;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public String getBeschrijvingEN() {
        return beschrijvingEN;
    }

    public void setBeschrijvingEN(String beschrijvingEN) {
        this.beschrijvingEN = beschrijvingEN;
    }
}
