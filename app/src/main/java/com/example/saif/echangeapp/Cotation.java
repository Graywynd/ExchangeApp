package com.example.saif.echangeapp;

/**
 * Created by saif on 19/04/2017.
 */
public class Cotation {

    public String Nom;
    public String Ouverture;
    public String haut;
    public String bas;
    public String volume;
    public String dernier;
    public String variation;

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getOuverture() {
        return Ouverture;
    }

    public void setOuverture(String ouverture) {
        Ouverture = ouverture;
    }

    public String getHaut() {
        return haut;
    }

    public void setHaut(String haut) {
        this.haut = haut;
    }

    public String getBas() {
        return bas;
    }

    public void setBas(String bas) {
        this.bas = bas;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getDernier() {
        return dernier;
    }

    public void setDernier(String dernier) {
        this.dernier = dernier;
    }

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    @Override
    public String toString() {
        return this.Nom + "   : ouverture"+ this.Ouverture+ "  variation "+this.variation;
    }
}
